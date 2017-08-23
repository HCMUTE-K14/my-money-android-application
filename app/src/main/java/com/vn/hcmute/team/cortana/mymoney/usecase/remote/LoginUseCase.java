package com.vn.hcmute.team.cortana.mymoney.usecase.remote;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.vn.hcmute.team.cortana.mymoney.R;
import com.vn.hcmute.team.cortana.mymoney.data.DataRepository;
import com.vn.hcmute.team.cortana.mymoney.exception.UserValidateException;
import com.vn.hcmute.team.cortana.mymoney.model.User;
import com.vn.hcmute.team.cortana.mymoney.model.UserCredential;
import com.vn.hcmute.team.cortana.mymoney.ui.base.listener.BaseCallBack;
import com.vn.hcmute.team.cortana.mymoney.usecase.base.Action;
import com.vn.hcmute.team.cortana.mymoney.usecase.base.UseCase;
import com.vn.hcmute.team.cortana.mymoney.usecase.remote.LoginUseCase.LoginRequest;
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

public class LoginUseCase extends UseCase<LoginRequest> {
    
    public static final String TAG = LoginUseCase.class.getSimpleName();
    
    private DataRepository mDataRepository;
    private Context mContext;
    
    private Disposable mDisposable;
    private CompositeDisposable mCompositeDisposable;
    private DisposableSingleObserver<Object> mDisposableSingleObserver;
    
    @Inject
    public LoginUseCase(Context context, DataRepository dataRepository) {
        this.mDataRepository = dataRepository;
        this.mCompositeDisposable = new CompositeDisposable();
    }
    
    @Override
    public void subscribe(LoginRequest requestValues) {
        String action = requestValues.getAction();
        
        switch (action) {
            case Action.ACTION_LOGIN_NORMAL:
                doLoginNormal(requestValues.getCallBack(), requestValues.getData());
                break;
            default:
                break;
        }
    }
    
    private void doLoginNormal(final BaseCallBack<Object> callBack,
              final UserCredential userCredential) {
        if (!UserValidate.getInstance().validateUser(userCredential)) {
            callBack.onFailure(new UserValidateException(
                      mContext.getString(R.string.message_validate_user_login)));
            return;
        }
        this.mDisposableSingleObserver = new DisposableSingleObserver<Object>() {
            @Override
            public void onSuccess(@io.reactivex.annotations.NonNull Object user) {
                mDataRepository.putLoginState((User) user);
                callBack.onSuccess((User) user);
            }
            
            @Override
            public void onError(@io.reactivex.annotations.NonNull Throwable e) {
                callBack.onFailure(e);
            }
        };
        if (!this.mCompositeDisposable.isDisposed()) {
            
            mDisposable = mDataRepository.login(userCredential)
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
    
    @Override
    public void unSubscribe() {
        if (!mCompositeDisposable.isDisposed()) {
            mCompositeDisposable.remove(mDisposable);
        }
    }
    
    
    public static class LoginRequest implements UseCase.RequestValue {
        
        private String action;
        private BaseCallBack<Object> callBack;
        private UserCredential userCredential;
        
        
        public LoginRequest(@NonNull String action, @Nullable BaseCallBack<Object> callBack,
                  @NonNull UserCredential userCredential) {
            this.action = action;
            this.callBack = callBack;
            this.userCredential = userCredential;
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
        
        public UserCredential getData() {
            return userCredential;
        }
        
        public void setData(UserCredential object) {
            this.userCredential = object;
        }
    }
    
    
}
