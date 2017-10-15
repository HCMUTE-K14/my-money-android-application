package com.vn.hcmute.team.cortana.mymoney.ui.login;

import com.vn.hcmute.team.cortana.mymoney.model.User;
import com.vn.hcmute.team.cortana.mymoney.model.UserCredential;
import com.vn.hcmute.team.cortana.mymoney.ui.base.view.BaseView;

/**
 * Created by infamouSs on 8/11/17.
 */

public interface LoginContract {
    
    public interface View extends BaseView {
        
        void initializeView();
        
        void loginSuccessful();
        
        void loginFailure(String message);
        
        void loading(boolean isLoading);
    }
    
    public interface Presenter {
        
        boolean isLogin();
        
        void login(UserCredential userCredential);
        
        void login(User user);
        
        void loginWithFacebook();
        
        void unSubscribe();
    }
}
