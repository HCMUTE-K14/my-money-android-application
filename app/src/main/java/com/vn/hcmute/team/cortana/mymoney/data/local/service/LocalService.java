package com.vn.hcmute.team.cortana.mymoney.data.local.service;

import com.vn.hcmute.team.cortana.mymoney.model.Currencies;
import com.vn.hcmute.team.cortana.mymoney.model.Icon;
import com.vn.hcmute.team.cortana.mymoney.model.Saving;
import com.vn.hcmute.team.cortana.mymoney.model.Wallet;
import java.util.List;
import java.util.concurrent.Callable;

/**
 * Created by infamouSs on 9/11/17.
 */

public interface LocalService {
    
    interface CurrencyLocalRepository {
        
        Callable<List<Currencies>> getListCurrency();
        
        Currencies getCurrency(String id);
    }
    
    interface ImageLocalService {
        
        Callable<List<Icon>> getListIcon();
    }
    
    interface SavingLocalService {
        
        Callable<List<Saving>> getListSaving();
        
        Callable<Long> addSaving(Saving saving);
        
        Callable<Integer> updateSaving(Saving saving);
        
        Callable<Integer> deleteSaving(String saving_id);
        
        Callable<Integer> takeInSaving(String idWallet, String idSaving, String moneyWallet,
                  String moneySaving);
        
        Callable<Integer> takeOutSaving(String idWallet, String idSaving, String moneyWallet,
                  String moneySaving);
    }
    
    interface WalletLocalService {
        
        Callable<List<Wallet>> getListWallet();
        
        Callable<Long> addWallet(Wallet wallet);
        
        Callable<Integer> updateWallet(Wallet wallet);
        
        Callable<Integer> deleteWallet(String idWallet);
        
        int updateMoneyWallet(String idWallet, String money);
    }
}
