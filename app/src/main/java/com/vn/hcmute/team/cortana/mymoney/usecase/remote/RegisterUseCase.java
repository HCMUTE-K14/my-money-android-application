package com.vn.hcmute.team.cortana.mymoney.usecase.remote;

import android.content.Context;
import android.support.annotation.NonNull;
import com.vn.hcmute.team.cortana.mymoney.R;
import com.vn.hcmute.team.cortana.mymoney.data.DataRepository;
import com.vn.hcmute.team.cortana.mymoney.exception.UserValidateException;
import com.vn.hcmute.team.cortana.mymoney.model.User;
import com.vn.hcmute.team.cortana.mymoney.ui.base.listener.BaseCallBack;
import com.vn.hcmute.team.cortana.mymoney.usecase.base.Action;
import com.vn.hcmute.team.cortana.mymoney.usecase.base.UseCase;
import com.vn.hcmute.team.cortana.mymoney.usecase.remote.RegisterUseCase.RegisterRequest;
import com.vn.hcmute.team.cortana.mymoney.utils.validate.UserValidate;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;
import javax.inject.Inject;

/**
 * Created by infamouSs on 8/11/17.
 */

public class RegisterUseCase extends UseCase<RegisterRequest> {
    
    public static final String TAG = RegisterUseCase.class.getSimpleName();
    
    private DataRepository mDataRepository;
    private Context mContext;
    
    private Disposable mDisposable;
    private CompositeDisposable mCompositeDisposable;
    private DisposableSingleObserver<Object> mDisposableSingleObserver;
    
    @Inject
    public RegisterUseCase(Context context, DataRepository dataRepository) {
        this.mDataRepository = dataRepository;
        this.mContext = context;
        this.mCompositeDisposable = new CompositeDisposable();
    }
    
    @Override
    public void subscribe(RegisterRequest requestValues) {
        String action = requestValues.getAction();
        switch (action) {
            case Action.ACTION_REGISTER:
                doRegister(requestValues.getCallBack(), requestValues.getData());
                break;
            default:
                break;
        }
    }
    
    private void doRegister(final BaseCallBack<Object> callBack, final User data) {
        if (!UserValidate.getInstance().validateUser(data)) {
            callBack.onFailure(new UserValidateException(
                      mContext.getString(R.string.message_validate_user_register)));
            return;
        }
        this.mDisposableSingleObserver = new DisposableSingleObserver<Object>() {
            @Override
            public void onSuccess(@io.reactivex.annotations.NonNull Object o) {
                callBack.onSuccess((String) o);
            }
            
            @Override
            public void onError(@io.reactivex.annotations.NonNull Throwable e) {
                callBack.onFailure(e);
            }
        };
        if (!this.mCompositeDisposable.isDisposed()) {
            
            mDisposable = mDataRepository.register(data)
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
    
    @Override
    public void unSubscribe() {
        if (!mCompositeDisposable.isDisposed()) {
            mCompositeDisposable.remove(mDisposable);
        }
    }
    
    public static class RegisterRequest implements UseCase.RequestValue {
        
        private String action;
        private BaseCallBack<Object> callBack;
        private User data;
        
        public RegisterRequest(@NonNull String action, @NonNull BaseCallBack<Object> callBack,
                  @NonNull User data) {
            this.action = action;
            this.callBack = callBack;
            this.data = data;
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
        
        public void setCallBack(
                  BaseCallBack<Object> callBack) {
            this.callBack = callBack;
        }
        
        public User getData() {
            return data;
        }
        
        public void setData(User data) {
            this.data = data;
        }
    }
}
