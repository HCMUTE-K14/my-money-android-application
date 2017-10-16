package com.vn.hcmute.team.cortana.mymoney.data.local.service;

import com.vn.hcmute.team.cortana.mymoney.model.Budget;
import com.vn.hcmute.team.cortana.mymoney.model.Currencies;
import com.vn.hcmute.team.cortana.mymoney.model.Event;
import com.vn.hcmute.team.cortana.mymoney.model.Icon;
import com.vn.hcmute.team.cortana.mymoney.model.Person;
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
        
        Callable<List<Saving>> getListSaving(String userId);
    
        Callable<Long> addSaving(Saving saving);
    
        Callable<Integer> updateSaving(Saving saving);
    
        Callable<Integer> deleteSaving(String saving_id);
    
        Callable<Integer> takeInSaving(String idWallet, String idSaving, String moneyWallet,
                  String moneySaving);
    
        Callable<Integer> takeOutSaving(String idWallet, String idSaving, String moneyWallet,
                  String moneySaving);
    }
    interface WalletLocalService{
        Callable<List<Wallet>> getListWallet(String userId);
        Callable<Long> addWallet(Wallet wallet);
        Callable<Integer> updateWallet(Wallet wallet);
        Callable<Integer> deleteWallet(String idWallet);
        Callable<Integer> moveWallet(String idWalletFrom,String idWalletTo, String Money);
        int updateMoneyWallet(String idWallet,String money);
        Wallet getWalletById(String idWallet);
        
    }
    interface EventLocalService{
        Callable<List<Event>> getListEvent(String userId);
        Callable<Long> addEvent(Event event);
        Callable<Integer> updateEvent(Event event);
        Callable<Integer> deleteEvent(String idEvent);
    }
    interface BudgetLocalService{
        Callable<List<Budget>> getListBudget(String userId);
        Callable<Long> addBudet(Budget budget);
        Callable<Integer> updateBudget(Budget budget);
        Callable<Integer> deleteBudget(String idBudget);
    }
    interface PersonLocalService{
        Callable<List<Person>> getListPerson(String userId);
        Callable<Long> addPerson(Person person);
        Callable<Integer> updatePerson(Person person);
        Callable<Integer> deletePerson(String idPerson);
    }
}
