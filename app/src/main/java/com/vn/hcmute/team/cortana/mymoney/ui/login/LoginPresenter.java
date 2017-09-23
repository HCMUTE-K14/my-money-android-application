package com.vn.hcmute.team.cortana.mymoney.ui.login;

import com.vn.hcmute.team.cortana.mymoney.model.UserCredential;
import com.vn.hcmute.team.cortana.mymoney.ui.base.BasePresenter;
import com.vn.hcmute.team.cortana.mymoney.ui.base.listener.BaseCallBack;
import com.vn.hcmute.team.cortana.mymoney.usecase.base.Action;
import com.vn.hcmute.team.cortana.mymoney.usecase.remote.UserManager;
import com.vn.hcmute.team.cortana.mymoney.usecase.remote.UserManager.UserRequest;
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
    public boolean isLogin() {
        return mUserManager.isLogin();
    }
    

    @Override
    public void login(UserCredential userCredential) {
        UserRequest loginRequest = new UserRequest(Action.ACTION_LOGIN_NORMAL,
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
