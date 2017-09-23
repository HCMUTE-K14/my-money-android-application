package com.vn.hcmute.team.cortana.mymoney.ui.iconshop;

import com.vn.hcmute.team.cortana.mymoney.model.Icon;
import com.vn.hcmute.team.cortana.mymoney.ui.base.view.BaseView;
import java.util.List;

/**
 * Created by infamouSs on 9/10/17.
 */

public interface SelectIconContract {
    
    interface View extends BaseView {
        
        void initializeView();
        
        void showListIcon(List<Icon> icons);
        
        void showEmpty(String message);
        
        void onFailure(String message);
        
        void loading(boolean isLoading);
    }
    
    interface Presenter {
        
        void getListIcon();
        
        void getListIcon(int page);
        
        void unSubscribe();
    }
}
