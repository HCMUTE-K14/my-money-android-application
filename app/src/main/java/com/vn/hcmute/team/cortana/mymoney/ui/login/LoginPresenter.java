package com.vn.hcmute.team.cortana.mymoney.ui.login;

import com.vn.hcmute.team.cortana.mymoney.model.User;
import com.vn.hcmute.team.cortana.mymoney.model.UserCredential;
import com.vn.hcmute.team.cortana.mymoney.ui.base.BasePresenter;
import com.vn.hcmute.team.cortana.mymoney.ui.base.listener.BaseCallBack;
import com.vn.hcmute.team.cortana.mymoney.usecase.base.Action;
import com.vn.hcmute.team.cortana.mymoney.usecase.remote.LoginUseCase;
import com.vn.hcmute.team.cortana.mymoney.usecase.remote.LoginUseCase.LoginRequest;
import com.vn.hcmute.team.cortana.mymoney.utils.logger.MyLogger;
import javax.inject.Inject;

/**
 * Created by infamouSs on 8/11/17.
 */

public class LoginPresenter extends BasePresenter<LoginContract.View> implements
                                                                      LoginContract.Presenter {
    
    public static final String TAG = LoginPresenter.class.getSimpleName();
    
    private LoginUseCase mLoginUseCase;
    private BaseCallBack<Object> callBack = new BaseCallBack<Object>() {
        @Override
        public void onSuccess(Object value) {
            getView().loading(false);
            getView().loginSuccessful();
            
            //Save USER
            MyLogger.d((User) value, true);
        }
        
        @Override
        public void onFailure(Throwable throwable) {
            getView().loginFailure(throwable.getMessage());
            MyLogger.d(throwable.getMessage());
        }
        
        @Override
        public void onLoading() {
            getView().loading(true);
            MyLogger.d("loading");
        }
    };
    
    @Inject
    public LoginPresenter(LoginUseCase loginUseCase) {
        this.mLoginUseCase = loginUseCase;
    }
    
    @Override
    public void login(UserCredential userCredential) {
        LoginRequest loginRequest = new LoginRequest(Action.ACTION_LOGIN_NORMAL, callBack,
                  userCredential);
        mLoginUseCase.subscribe(loginRequest);
    }
    
    @Override
    public void unSubscribe() {
        mLoginUseCase.unSubscribe();
    }
}
