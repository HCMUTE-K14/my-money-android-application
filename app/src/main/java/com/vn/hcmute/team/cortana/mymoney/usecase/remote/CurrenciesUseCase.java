package com.vn.hcmute.team.cortana.mymoney.usecase.remote;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.vn.hcmute.team.cortana.mymoney.data.DataRepository;
import com.vn.hcmute.team.cortana.mymoney.model.Currencies;
import com.vn.hcmute.team.cortana.mymoney.ui.base.listener.BaseCallBack;
import com.vn.hcmute.team.cortana.mymoney.usecase.base.Action;
import com.vn.hcmute.team.cortana.mymoney.usecase.base.UseCase;
import com.vn.hcmute.team.cortana.mymoney.usecase.remote.CurrenciesUseCase.CurrenciesRequest;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;
import javax.inject.Inject;

/**
 * Created by kunsubin on 8/22/2017.
 */

public class CurrenciesUseCase extends UseCase<CurrenciesRequest> {
    private DataRepository mDataRepository;
    private Context mContext;
    
    private Disposable mDisposable;
    private CompositeDisposable mCompositeDisposable;
    private DisposableSingleObserver<Object> mDisposableSingleObserver;
    
    @Inject
    public CurrenciesUseCase(Context context, DataRepository dataRepository) {
        this.mDataRepository = dataRepository;
        this.mCompositeDisposable = new CompositeDisposable();
    }
    
    
    @Override
    public void subscribe(CurrenciesRequest requestValues) {
        String action = requestValues.getAction();
    
        switch (action) {
            case Action.ACCTION_GET_CURRENCIES:
                doGetCurrencies(requestValues.getCallBack());
                break;
            default:
                break;
        }
    }
    
    @Override
    public void unSubscribe() {
        if (!mCompositeDisposable.isDisposed()) {
            mCompositeDisposable.remove(mDisposable);
        }
    }
    
    private void doGetCurrencies(final BaseCallBack<Object> callBack){
        String userid = mDataRepository.getUserId();
        String token = mDataRepository.getUserToken();
    
        this.mDisposableSingleObserver = new DisposableSingleObserver<Object>() {
            @Override
            public void onSuccess(@io.reactivex.annotations.NonNull Object user) {
            
                callBack.onSuccess(user);
            }
        
            @Override
            public void onError(@io.reactivex.annotations.NonNull Throwable e) {
                callBack.onFailure(e);
            }
        };
    
        if (!this.mCompositeDisposable.isDisposed()) {
        
            mDisposable = mDataRepository.getCurrencies()
                      .subscribeOn(Schedulers.io())
                      .observeOn(AndroidSchedulers.mainThread())
                      .doOnSubscribe(new Consumer<Disposable>() {
                          @Override
                          public void accept(Disposable disposable) throws Exception {
                              callBack.onLoading();
                          }
                      })
                      .cacheWithInitialCapacity(10)
                      .singleOrError()
                      .subscribeWith(this.mDisposableSingleObserver);
            this.mCompositeDisposable.add(mDisposable);
        
        }
    }
    
    
    public static class CurrenciesRequest implements UseCase.RequestValue {
        
        private String action;
        private BaseCallBack<Object> callBack;
        private Currencies mCurrencies;
        private String[] params;
        
        public CurrenciesRequest(@NonNull String action, @Nullable BaseCallBack<Object> callBack,
                  @Nullable Currencies currencies, @Nullable String[] params) {
            this.action = action;
            this.callBack = callBack;
            this.mCurrencies = currencies;
            this.params = params;
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
        
        public Currencies getData() {
            return mCurrencies;
        }
        
        public String[] getParam() {
            return params;
        }
        
        public void setData(Currencies object) {
            this.mCurrencies = object;
        }
    }
}
