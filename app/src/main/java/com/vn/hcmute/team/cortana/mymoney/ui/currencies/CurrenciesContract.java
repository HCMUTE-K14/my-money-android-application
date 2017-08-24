package com.vn.hcmute.team.cortana.mymoney.ui.currencies;

import com.vn.hcmute.team.cortana.mymoney.model.Currencies;
import com.vn.hcmute.team.cortana.mymoney.ui.base.view.BaseView;
import java.util.List;

/**
 * Created by kunsubin on 8/22/2017.
 */

public interface CurrenciesContract {
    
    public interface View extends BaseView {
        
        void showCurrencies(List<Currencies> list);
        
        void showEmpty();
        
        void showError(String message);
        
        void loading(boolean isLoading);
    }
    
    public interface Presenter {
        
        void getCurrencies();
        
        void unSubscribe();
    }
}
