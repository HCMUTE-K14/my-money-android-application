package com.vn.hcmute.team.cortana.mymoney.ui.currencies;

import com.vn.hcmute.team.cortana.mymoney.model.Currencies;
import com.vn.hcmute.team.cortana.mymoney.ui.base.view.BaseView;
import java.util.List;

/**
 * Created by kunsubin on 8/22/2017.
 */

public interface CurrenciesContract {
    interface View extends BaseView {
        
        void onSuccessGetCurrencies(List<Currencies> list);
        void onFailureGetCurrencies(String message);
    }
    
    interface Presenter {
        void getCurrencies();
    }
}
