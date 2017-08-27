package com.vn.hcmute.team.cortana.mymoney.ui.login;

import com.vn.hcmute.team.cortana.mymoney.model.UserCredential;
import com.vn.hcmute.team.cortana.mymoney.ui.base.BasePresenter;
import com.vn.hcmute.team.cortana.mymoney.ui.base.listener.BaseCallBack;
import com.vn.hcmute.team.cortana.mymoney.usecase.base.Action;
import com.vn.hcmute.team.cortana.mymoney.usecase.remote.UserManager;
import com.vn.hcmute.team.cortana.mymoney.usecase.remote.UserManager.ForgetPasswordRequest;
import com.vn.hcmute.team.cortana.mymoney.usecase.remote.UserManager.LoginRequest;
import com.vn.hcmute.team.cortana.mymoney.utils.logger.MyLogger;
import javax.inject.Inject;

/**
 * Created by infamouSs on 8/11/17.
 */

public class LoginPresenter extends BasePresenter<LoginContract.View> implements
                                                                      LoginContract.Presenter {
    
    public static final String TAG = LoginPresenter.class.getSimpleName();
    
    private UserManager mUserManager;
    
    @Inject
    public LoginPresenter(UserManager userManager) {
        this.mUserManager = userManager;
    }
    
    
    @Override
    public void forgetPassword(String email) {
        ForgetPasswordRequest forgetPasswordRequest = new ForgetPasswordRequest(
                  Action.ACTION_FORGET_PASSWORD, new BaseCallBack<Object>() {
            @Override
            public void onSuccess(Object value) {
                MyLogger.d("Success");
            }
            
            @Override
            public void onFailure(Throwable throwable) {
                
            }
            
            @Override
            public void onLoading() {
                
            }
        }, email);
        
        mUserManager.subscribe(forgetPasswordRequest);
    }
    
    @Override
    public void login(UserCredential userCredential) {
        LoginRequest loginRequest = new LoginRequest(Action.ACTION_LOGIN_NORMAL,
                  new BaseCallBack<Object>() {
                      @Override
                      public void onSuccess(Object value) {
                          getView().loading(false);
                          getView().loginSuccessful();
                      }
                      
                      @Override
                      public void onFailure(Throwable throwable) {
                          getView().loading(false);
                          getView().loginFailure(throwable.getMessage());
                      }
                      
                      @Override
                      public void onLoading() {
                          getView().loading(true);
                      }
                  },
                  userCredential);
        mUserManager.subscribe(loginRequest);
    }
    
    @Override
    public void loginWithFacebook() {
        
    }
    
    @Override
    public void unSubscribe() {
        mUserManager.unSubscribe();
    }
}
