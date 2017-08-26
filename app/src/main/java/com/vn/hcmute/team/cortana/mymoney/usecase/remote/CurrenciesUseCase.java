package com.vn.hcmute.team.cortana.mymoney.usecase.remote;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.vn.hcmute.team.cortana.mymoney.data.DataRepository;
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
import javax.inject.Singleton;

/**
 * Created by kunsubin on 8/22/2017.
 */
@Singleton
public class CurrenciesUseCase extends UseCase<CurrenciesRequest> {
    
    private DataRepository mDataRepository;
    private Context mContext;
    
    private Disposable mDisposable;
    private CompositeDisposable mCompositeDisposable;
    private DisposableSingleObserver<Object> mDisposableSingleObserver;
    
    @Inject
    public CurrenciesUseCase(Context context, DataRepository dataRepository) {
        this.mContext = context;
        this.mDataRepository = dataRepository;
        this.mCompositeDisposable = new CompositeDisposable();
    }
    
    
    @Override
    public void subscribe(CurrenciesRequest requestValues) {
        String action = requestValues.getAction();
        
        switch (action) {
            case Action.ACTION_GET_CURRENCIES:
                doGetCurrencies(requestValues.getCallBack());
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
    
    private void doGetCurrencies(final BaseCallBack<Object> callBack) {
        
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
        
        public CurrenciesRequest(@NonNull String action, @Nullable BaseCallBack<Object> callBack) {
            this.action = action;
            this.callBack = callBack;
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
        
        
    }
}