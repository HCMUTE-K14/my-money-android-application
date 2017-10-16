package com.vn.hcmute.team.cortana.mymoney.data.local;

import com.vn.hcmute.team.cortana.mymoney.model.Budget;
import com.vn.hcmute.team.cortana.mymoney.model.Category;
import com.vn.hcmute.team.cortana.mymoney.model.Currencies;
import com.vn.hcmute.team.cortana.mymoney.model.Event;
import com.vn.hcmute.team.cortana.mymoney.model.Icon;
import com.vn.hcmute.team.cortana.mymoney.model.Person;
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
        
        Observable<List<Saving>> getListSaving(String userId);
    
        Observable<String> addSaving(Saving saving);
    
        Observable<String> updateSaving(Saving saving);
    
        Observable<String> deleteSaving(String saving_id);
    
        Observable<String> takeInSaving(String idWallet, String idSaving, String moneyWallet,
                  String moneySaving);
    
        Observable<String> takeOutSaving(String idWallet, String idSaving, String moneyWallet,
                  String moneySaving);
    }
    interface WalletTask{
        Observable<List<Wallet>> getListWallet(String userId);
        Observable<String> addWallet(Wallet wallet);
        Observable<String> updateWallet(Wallet wallet);
        Observable<String> deleteWallet(String idWallet);
        Observable<String> moveWallet(String idWalletFrom,String idWalletTo, String Money);
    }
    interface EventTask{
        Observable<List<Event>> getListEvent(String userId);
        Observable<String> addEvent(Event event);
        Observable<String> updateEvent(Event event);
        Observable<String> deleteEvent(String idEvent);
    }
    interface BudgetTask{
        Observable<List<Budget>> getListBudget(String userId);
        Observable<String> addBudet(Budget budget);
        Observable<String> updateBudget(Budget budget);
        Observable<String> deleteBudget(String idBudget);
    }
    interface  PersonTask{
        Observable<List<Person>> getListPerson(String userId);
        Observable<String> addPerson(Person person);
        Observable<String> updatePerson(Person person);
        Observable<String> deletePerson(String idPerson);
    }
}
