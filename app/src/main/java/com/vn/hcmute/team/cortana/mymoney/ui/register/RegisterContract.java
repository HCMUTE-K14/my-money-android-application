package com.vn.hcmute.team.cortana.mymoney.ui.register;

import com.vn.hcmute.team.cortana.mymoney.model.User;
import com.vn.hcmute.team.cortana.mymoney.ui.base.view.BaseView;

/**
 * Created by infamouSs on 8/11/17.
 */

public interface RegisterContract {
    
    public interface View extends BaseView {
        
        void initializeView();
        
        void registerSuccess(String message);
        
        void registerWithFacebookSuccess(String message);
        
        void registerFailure(String message);
        
        void loading(boolean isLoading);
    }
    
    public interface Presenter {
        
        void register(User user);
        
        void registerWithFacebook();
        
        void unSubscribe();
    }
    
}
