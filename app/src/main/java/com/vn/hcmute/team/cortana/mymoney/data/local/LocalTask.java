package com.vn.hcmute.team.cortana.mymoney.data.local;

import com.vn.hcmute.team.cortana.mymoney.model.Currencies;
import com.vn.hcmute.team.cortana.mymoney.model.Icon;
import io.reactivex.Observable;
import java.util.List;

/**
 * Created by infamouSs on 9/10/17.
 */

public interface LocalTask {
    
    interface IconTask {
        
        Observable<List<Icon>> getListIcon();
    }
    
    interface CurrencyTask {
        
        Observable<List<Currencies>> getListCurrency();
    }
}
