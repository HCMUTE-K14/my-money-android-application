package com.vn.hcmute.team.cortana.mymoney.data.local.service;

import com.vn.hcmute.team.cortana.mymoney.model.Currencies;
import java.util.List;
import java.util.concurrent.Callable;

/**
 * Created by infamouSs on 9/11/17.
 */

public interface LocalService {
    interface CurrencyLocalRepository{
        Callable<List<Currencies>> getListCurrency();
    }
}
