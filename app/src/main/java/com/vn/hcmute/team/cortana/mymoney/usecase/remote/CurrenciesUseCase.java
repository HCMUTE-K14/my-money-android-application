package com.vn.hcmute.team.cortana.mymoney.usecase.remote;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.vn.hcmute.team.cortana.mymoney.data.DataRepository;
import com.vn.hcmute.team.cortana.mymoney.data.cache.PreferencesHelper;
import com.vn.hcmute.team.cortana.mymoney.model.RealTimeCurrency;
import com.vn.hcmute.team.cortana.mymoney.ui.base.listener.BaseCallBack;
import com.vn.hcmute.team.cortana.mymoney.usecase.base.Action;
import com.vn.hcmute.team.cortana.mymoney.usecase.base.TypeRepository;
import com.vn.hcmute.team.cortana.mymoney.usecase.base.UseCase;
import com.vn.hcmute.team.cortana.mymoney.usecase.remote.CurrenciesUseCase.CurrenciesRequest;
import com.vn.hcmute.team.cortana.mymoney.utils.NumberUtil;
import com.vn.hcmute.team.cortana.mymoney.utils.logger.MyLogger;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;
import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by kunsubin on 8/22/2017.
 */
@Singleton
public class CurrenciesUseCase extends UseCase<CurrenciesRequest> {
    
    public static final String TAG = CurrenciesUseCase.class.getSimpleName();
    @Inject
    PreferencesHelper mPreferencesHelper;
    private DataRepository mDataRepository;
    private Context mContext;
    private Disposable mDisposable;
    private CompositeDisposable mCompositeDisposable;
    private DisposableSingleObserver<Object> mDisposableSingleObserver;
    
    @Inject
    public CurrenciesUseCase(Context context, DataRepository dataRepository) {
        this.mContext = context.getApplicationContext();
        this.mDataRepository = dataRepository;
        this.mCompositeDisposable = new CompositeDisposable();
    }
    
    @Override
    public void subscribe(CurrenciesRequest requestValues) {
        String action = requestValues.getAction();
        
        switch (action) {
            case Action.ACTION_GET_CURRENCIES:
                doGetCurrencies(requestValues.getCallBack(), requestValues.getTypeRepository());
                break;
            case Action.ACTION_CONVERT_CURRENCY_ONLINE:
                doConvert_Online(requestValues.getCallBack(), requestValues.getParams());
                break;
            case Action.ACTION_CONVERT_CURRENCY_OFFLINE:
                doConvert_Offline(requestValues.getCallBack(), requestValues.getParams());
                break;
            case Action.ACTION_UPDATE_REAL_TIME_CURRENCY:
                doUpdateRealTimeCurrency(requestValues.getCallBack());
                break;
            default:
                break;
        }
    }
    
    @Override
    public void unSubscribe() {
        if (mCompositeDisposable != null && !mCompositeDisposable.isDisposed() &&
            mDisposable != null) {
            mCompositeDisposable.remove(mDisposable);
        }
    }
    
    private void doGetCurrencies(final BaseCallBack<Object> callBack,
              TypeRepository typeRepository) {
        
        this.mDisposableSingleObserver = new DisposableSingleObserver<Object>() {
            @Override
            public void onSuccess(@io.reactivex.annotations.NonNull Object currencies) {
                callBack.onSuccess(currencies);
            }
            
            @Override
            public void onError(@io.reactivex.annotations.NonNull Throwable e) {
                callBack.onFailure(e);
            }
        };
        
        if (!this.mCompositeDisposable.isDisposed()) {
            if (typeRepository == TypeRepository.LOCAL) {
                mDisposable = mDataRepository.getLocalListCurrency()
                          .subscribeOn(Schedulers.computation())
                          .observeOn(AndroidSchedulers.mainThread())
                          .doOnSubscribe(new Consumer<Disposable>() {
                              @Override
                              public void accept(Disposable disposable) throws Exception {
                                  MyLogger.d("Currencies Usecacse", "loading");
                                  callBack.onLoading();
                              }
                          })
                          .singleOrError()
                          .subscribeWith(this.mDisposableSingleObserver);
            } else if (typeRepository == TypeRepository.REMOTE) {
                mDisposable = mDataRepository.getCurrencies()
                          .subscribeOn(Schedulers.io())
                          .observeOn(AndroidSchedulers.mainThread())
                          .doOnSubscribe(new Consumer<Disposable>() {
                              @Override
                              public void accept(Disposable disposable) throws Exception {
                                  MyLogger.d("Currencies Usecacse", "loading");
                                  callBack.onLoading();
                              }
                          })
                          .singleOrError()
                          .subscribeWith(this.mDisposableSingleObserver);
            }
            
            this.mCompositeDisposable.add(mDisposable);
        }
    }
    
    private void doConvert_Offline(final BaseCallBack<Object> callBack, final String[] params) {
        try {
            String amount = params[0];
            String from = params[1];
            String to = params[2];
            
            double result = NumberUtil
                      .exchangeMoney(mContext.getApplicationContext(), amount, from, to);
            callBack.onSuccess(result);
        } catch (Exception e) {
            callBack.onFailure(e);
        }
        
    }
    
    private void doUpdateRealTimeCurrency(final BaseCallBack<Object> callBack) {
        this.mDisposableSingleObserver = new DisposableSingleObserver<Object>() {
            @Override
            public void onSuccess(@io.reactivex.annotations.NonNull Object currencies) {
                mDataRepository.putRealTimeCurrency((RealTimeCurrency) currencies);
                MyLogger.d(TAG, (RealTimeCurrency) currencies, true);
                callBack.onSuccess(currencies);
            }
            
            @Override
            public void onError(@io.reactivex.annotations.NonNull Throwable e) {
                callBack.onFailure(e);
            }
        };
        
        if (!this.mCompositeDisposable.isDisposed()) {
            
            mDisposable = mDataRepository.updateRealTimeCurrency()
                      .subscribeOn(Schedulers.io())
                      .observeOn(AndroidSchedulers.mainThread())
                      .doOnSubscribe(new Consumer<Disposable>() {
                          @Override
                          public void accept(Disposable disposable) throws Exception {
                              callBack.onLoading();
                          }
                      })
                      .singleOrError()
                      .subscribeWith(this.mDisposableSingleObserver);
            this.mCompositeDisposable.add(mDisposable);
        }
    }
    
    private void doConvert_Online(final BaseCallBack<Object> callBack, final String[] params) {
        String amount = params[0];
        String from = params[1];
        String to = params[2];
        
        this.mDisposableSingleObserver = new DisposableSingleObserver<Object>() {
            @Override
            public void onSuccess(@io.reactivex.annotations.NonNull Object currencies) {
                callBack.onSuccess(currencies);
            }
            
            @Override
            public void onError(@io.reactivex.annotations.NonNull Throwable e) {
                callBack.onFailure(e);
            }
        };
        
        if (!this.mCompositeDisposable.isDisposed()) {
            
            mDisposable = mDataRepository.convertCurrency(amount, from, to)
                      .subscribeOn(Schedulers.io())
                      .observeOn(AndroidSchedulers.mainThread())
                      .doOnSubscribe(new Consumer<Disposable>() {
                          @Override
                          public void accept(Disposable disposable) throws Exception {
                              callBack.onLoading();
                          }
                      })
                      .singleOrError()
                      .subscribeWith(this.mDisposableSingleObserver);
            this.mCompositeDisposable.add(mDisposable);
        }
    }
    
    
    public static class CurrenciesRequest implements UseCase.RequestValue {
        
        private String action;
        private BaseCallBack<Object> callBack;
        private String[] params;
        private TypeRepository typeRepository;
        
        public CurrenciesRequest(String action, BaseCallBack<Object> callBack, String[] params) {
            this(action, callBack, params, TypeRepository.LOCAL);
        }
        
        public CurrenciesRequest(@NonNull String action, @Nullable BaseCallBack<Object> callBack,
                  String[] params, TypeRepository typeRepository) {
            this.action = action;
            this.callBack = callBack;
            this.params = params;
            this.typeRepository = typeRepository;
        }
        
        public String getAction() {
            return action;
        }
        
        public void setAction(String action) {
            this.action = action;
        }
        
        public BaseCallBack<Object> getCallBack() {
            return callBack;
        }
        
        public void setCallBack(BaseCallBack<Object> callBack) {
            this.callBack = callBack;
        }
        
        public String[] getParams() {
            return params;
        }
        
        public void setParams(String[] params) {
            this.params = params;
        }
        
        public TypeRepository getTypeRepository() {
            return typeRepository;
        }
        
        public void setTypeRepository(
                  TypeRepository typeRepository) {
            this.typeRepository = typeRepository;
        }
    }
}
