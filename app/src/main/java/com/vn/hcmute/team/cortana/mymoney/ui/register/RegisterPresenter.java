package com.vn.hcmute.team.cortana.mymoney.ui.register;

import com.vn.hcmute.team.cortana.mymoney.model.User;
import com.vn.hcmute.team.cortana.mymoney.ui.base.BasePresenter;
import com.vn.hcmute.team.cortana.mymoney.ui.base.listener.BaseCallBack;
import com.vn.hcmute.team.cortana.mymoney.usecase.base.Action;
import com.vn.hcmute.team.cortana.mymoney.usecase.remote.RegisterUseCase;
import com.vn.hcmute.team.cortana.mymoney.usecase.remote.RegisterUseCase.RegisterRequest;
import javax.inject.Inject;

/**
 * Created by infamouSs on 8/11/17.
 */

public class RegisterPresenter extends BasePresenter<RegisterContract.View> implements
                                                                            RegisterContract.Presenter {
    
    private RegisterUseCase mRegisterUseCase;
    
    private BaseCallBack<Object> callBack = new BaseCallBack<Object>() {
        @Override
        public void onSuccess(Object value) {
            getView().loading(false);
            getView().registerSuccess();
        }
        
        @Override
        public void onFailure(Throwable throwable) {
            getView().registerFailure(throwable.getMessage());
        }
        
        @Override
        public void onLoading() {
            getView().loading(true);
        }
    };
    
    @Inject
    public RegisterPresenter(RegisterUseCase registerUseCase) {
        this.mRegisterUseCase = registerUseCase;
    }
    
    @Override
    public void register(User user) {
        RegisterRequest registerRequest = new RegisterRequest(Action.ACTION_REGISTER, callBack,
                  user);
        
        mRegisterUseCase.subscribe(registerRequest);
    }
    
    @Override
    public void unSubscribe() {
        
    }
}
