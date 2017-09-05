package com.vn.hcmute.team.cortana.mymoney.ui.forgetpassword;

import com.vn.hcmute.team.cortana.mymoney.ui.base.BasePresenter;
import com.vn.hcmute.team.cortana.mymoney.ui.base.listener.BaseCallBack;
import com.vn.hcmute.team.cortana.mymoney.usecase.base.Action;
import com.vn.hcmute.team.cortana.mymoney.usecase.remote.UserManager;
import com.vn.hcmute.team.cortana.mymoney.usecase.remote.UserManager.ForgetPasswordRequest;
import javax.inject.Inject;

/**
 * Created by infamouSs on 8/25/17.
 */

public class ForgetPasswordPresenter extends BasePresenter<ForgetPasswordContract.View> implements
                                                                                        ForgetPasswordContract.Presenter {
    
    public static final String TAG = ForgetPasswordActivity.class.getSimpleName();
    
    private UserManager mUserManager;
    
    @Inject
    public ForgetPasswordPresenter(UserManager userManager) {
        this.mUserManager = userManager;
    }
    
    
    @Override
    public void forgetPassword(String email) {
        ForgetPasswordRequest request = new ForgetPasswordRequest(Action.ACTION_FORGET_PASSWORD,
                  new BaseCallBack<Object>() {
                      @Override
                      public void onSuccess(Object value) {
                          getView().loading(false);
                          getView().successForgetPassword((String) value);
                      }
                      
                      @Override
                      public void onFailure(Throwable throwable) {
                          getView().loading(false);
                          getView().showError(throwable.getMessage());
                      }
                      
                      @Override
                      public void onLoading() {
                          getView().loading(true);
                      }
                  }, email);
        
        mUserManager.subscribe(request);
    }
    
    @Override
    public void unSubscribe() {
        mUserManager.unSubscribe();
    }
}
