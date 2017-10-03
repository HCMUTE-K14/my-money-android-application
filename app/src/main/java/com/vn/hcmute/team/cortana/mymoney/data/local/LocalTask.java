package com.vn.hcmute.team.cortana.mymoney.data.local;

import com.vn.hcmute.team.cortana.mymoney.model.Category;
import com.vn.hcmute.team.cortana.mymoney.model.Currencies;
import com.vn.hcmute.team.cortana.mymoney.model.Icon;
import com.vn.hcmute.team.cortana.mymoney.model.Saving;
import com.vn.hcmute.team.cortana.mymoney.model.Wallet;
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
    
    interface CategoryTask {
        
        Observable<List<Category>> getListCategory(String transType);
        
        Observable<List<Category>> getListCategoryByType(String type, String transType);
        
        Observable<String> addCategory(Category category);
        
        Observable<String> updateCategory(Category category);
        
        Observable<String> deleteCategory(Category category);
    }
    interface SavingTask{
        
        Observable<List<Saving>> getListSaving();
    
        Observable<String> addSaving(Saving saving);
    
        Observable<String> updateSaving(Saving saving);
    
        Observable<String> deleteSaving(String saving_id);
    
        Observable<String> takeInSaving(String idWallet, String idSaving, String moneyWallet,
                  String moneySaving);
    
        Observable<String> takeOutSaving(String idWallet, String idSaving, String moneyWallet,
                  String moneySaving);
    }
    interface WalletTask{
        Observable<List<Wallet>> getListWallet();
        Observable<String> addWallet(Wallet wallet);
        Observable<String> updateWallet(Wallet wallet);
        Observable<String> deleteWallet(String idWallet);
    }
}
