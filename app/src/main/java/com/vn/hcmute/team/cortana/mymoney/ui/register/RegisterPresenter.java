package com.vn.hcmute.team.cortana.mymoney.ui.register;

import com.vn.hcmute.team.cortana.mymoney.model.User;
import com.vn.hcmute.team.cortana.mymoney.ui.base.BasePresenter;
import com.vn.hcmute.team.cortana.mymoney.ui.base.listener.BaseCallBack;
import com.vn.hcmute.team.cortana.mymoney.usecase.base.Action;
import com.vn.hcmute.team.cortana.mymoney.usecase.remote.UserManager;
import com.vn.hcmute.team.cortana.mymoney.usecase.remote.UserManager.UserRequest;
import javax.inject.Inject;

/**
 * Created by infamouSs on 8/11/17.
 */

public class RegisterPresenter extends BasePresenter<RegisterContract.View> implements
                                                                            RegisterContract.Presenter {
    
    public static final String TAG = RegisterPresenter.class.getSimpleName();
    
    private UserManager mUserManager;
    
    private BaseCallBack<Object> callBack = new BaseCallBack<Object>() {
        @Override
        public void onSuccess(Object value) {
            getView().loading(false);
            getView().registerSuccess((String) value);
        }
        
        @Override
        public void onFailure(Throwable throwable) {
            getView().loading(false);
            getView().registerFailure(throwable.getMessage());
        }
        
        @Override
        public void onLoading() {
            getView().loading(true);
        }
    };
    
    @Inject
    public RegisterPresenter(UserManager userManagerUseCase) {
        this.mUserManager = userManagerUseCase;
    }
    
    @Override
    public void register(User user) {
        UserRequest registerRequest = new UserRequest(Action.ACTION_REGISTER, callBack,
                  user);
        
        mUserManager.subscribe(registerRequest);
    }
    
    @Override
    public void registerWithFacebook() {
        
    }
    
    @Override
    public void unSubscribe() {
        mUserManager.unSubscribe();
    }
}
