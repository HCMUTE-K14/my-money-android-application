package com.vn.hcmute.team.cortana.mymoney.data.local.service;

import com.vn.hcmute.team.cortana.mymoney.model.Budget;
import com.vn.hcmute.team.cortana.mymoney.model.Currencies;
import com.vn.hcmute.team.cortana.mymoney.model.DebtLoan;
import com.vn.hcmute.team.cortana.mymoney.model.Event;
import com.vn.hcmute.team.cortana.mymoney.model.Icon;
import com.vn.hcmute.team.cortana.mymoney.model.Person;
import com.vn.hcmute.team.cortana.mymoney.model.Saving;
import com.vn.hcmute.team.cortana.mymoney.model.Transaction;
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
        
        Callable<Integer> updateStatusSaving(List<Saving> savingList);
        
        Saving getSavingById(String id);
        
        int deleteSavingByWallet(String wallet_id);
    }
    
    interface WalletLocalService {
        
        Callable<List<Wallet>> getListWallet(String userId);
        
        Callable<Long> addWallet(Wallet wallet);
        
        Callable<Integer> updateWallet(Wallet wallet);
        
        Callable<Integer> deleteWallet(String idWallet);
        
        Callable<Integer> moveWallet(String userid, String wallet_id_from, String wallet_id_to,
                  String moneyMinus,
                  String moneyPlus, String date_created);
        
        int updateMoneyWallet(String idWallet, String money);
        
        Wallet getWalletById(String idWallet);
        
        Callable<Integer> takeInWallet(String wallet_id, String money);
        
        Callable<Integer> takeOutWallet(String wallet_id, String money);
    }
    
    interface EventLocalService {
        
        Callable<List<Event>> getListEvent(String userId);
        
        Callable<Long> addEvent(Event event);
        
        Callable<Integer> updateEvent(Event event);
        
        Callable<Integer> deleteEvent(String idEvent);
        
        Event getEventById(String id);
        
        Callable<Integer> updateStatusEvent(List<Event> eventList);
        
        int deleteEventByWallet(String wallet_id);
    }
    
    interface BudgetLocalService {
        
        Callable<List<Budget>> getListBudget(String userId);
        
        Callable<Long> addBudet(Budget budget);
        
        Callable<Integer> updateBudget(Budget budget);
        
        Callable<Integer> deleteBudget(String idBudget);
        
        Callable<Integer> updateStatusBudget(List<Budget> budgetList);
        
        int deleteBudgetFromWallet(String wallet_id);
    }
    
    interface PersonLocalService {
        
        Callable<List<Person>> getListPerson(String userId);
        
        Callable<Long> addPerson(Person person);
        
        Callable<Integer> updatePerson(Person person);
        
        Callable<Integer> deletePerson(String idPerson);
        
        Person getPersonById(String person_id);
    }
    
    interface TransactionLocalService {
        
        Callable<Long> addTransaction(Transaction transaction);
        
        Callable<Integer> updateTransaction(Transaction transaction);
        
        Callable<Integer> deleteTransaction(Transaction transaction);
        
        Callable<List<Transaction>> getTransactionByTime(String user_id, String start, String end,
                  String wallet_id);
        
        Transaction getTransactionById(String trans_id);
        
        Callable<Transaction> getTransactionByIdUseCallable(String trans_id);
        
        Callable<List<Transaction>> getAllTransaction(String user_id);
        
        //Callable<List<Transaction>> getTransactionBySaving(String user_id,String saving_id);
        
        Callable<List<Transaction>> getTransactionByEvent(String user_id, String event_id);
        
        Callable<List<Transaction>> getTransactionByBudget(String user_id, String start, String end,
                  String cate_id, String wallet_id);
        
        Callable<List<Transaction>> getTransactionBySaving(final String user_id,
                  final String saving_id);
    }
    
    interface TransPersonLocalService {
        
        List<Person> getPersonByTransactionId(String trans_id);
        
        long add(String[] values);
        
        int update(String[] values);
        
        int delete(String[] values);
    }
    
    interface DebtLoanLocalService {
        
        Callable<List<DebtLoan>> getDebtLoanByWalletId(String wallet_id);
        
        Callable<List<DebtLoan>> getDebtLoanByType(String wallet_id, String type);
        
        Callable<Long> addDebtLoan(DebtLoan debtLoan);
        
        Callable<Integer> updateDebtLoan(DebtLoan debtLoan);
        
        Callable<Integer> deleteDebtLoan(DebtLoan debtLoan);
        
        int deleteDebtLoanByTransaction(String trans_id);
    }
}
