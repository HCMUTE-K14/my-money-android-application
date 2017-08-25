package com.vn.hcmute.team.cortana.mymoney.ui.forgetpassword;

import com.vn.hcmute.team.cortana.mymoney.ui.base.view.BaseView;

/**
 * Created by infamouSs on 8/25/17.
 */

public interface ForgetPasswordContract {
    
    public interface View extends BaseView {
        
        void initializeView();
        
        void successForgetPassword(String message);
        
        void showError(String message);
        
        void loading(boolean isLoading);
    }
    
    public interface Presenter {
        
        void forgetPassword(String email);
        
        void unSubscribe();
    }
}
