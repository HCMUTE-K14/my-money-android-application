package com.vn.hcmute.team.cortana.mymoney.data.local;

import android.content.Context;
import com.vn.hcmute.team.cortana.mymoney.R;
import com.vn.hcmute.team.cortana.mymoney.data.local.base.DatabaseHelper;
import com.vn.hcmute.team.cortana.mymoney.data.local.service.BudgetLocalService;
import com.vn.hcmute.team.cortana.mymoney.data.local.service.CategoryLocalService;
import com.vn.hcmute.team.cortana.mymoney.data.local.service.CurrencyLocalService;
import com.vn.hcmute.team.cortana.mymoney.data.local.service.DebtLoanLocalService;
import com.vn.hcmute.team.cortana.mymoney.data.local.service.EventLocalService;
import com.vn.hcmute.team.cortana.mymoney.data.local.service.ImageLocalService;
import com.vn.hcmute.team.cortana.mymoney.data.local.service.PersonLocalService;
import com.vn.hcmute.team.cortana.mymoney.data.local.service.SavingLocalService;
import com.vn.hcmute.team.cortana.mymoney.data.local.service.TransactionLocalService;
import com.vn.hcmute.team.cortana.mymoney.data.local.service.WalletLocalService;
import com.vn.hcmute.team.cortana.mymoney.exception.BudgetException;
import com.vn.hcmute.team.cortana.mymoney.exception.CategoryException;
import com.vn.hcmute.team.cortana.mymoney.exception.DebtLoanException;
import com.vn.hcmute.team.cortana.mymoney.exception.EventException;
import com.vn.hcmute.team.cortana.mymoney.exception.PersonException;
import com.vn.hcmute.team.cortana.mymoney.exception.SavingException;
import com.vn.hcmute.team.cortana.mymoney.exception.TransactionException;
import com.vn.hcmute.team.cortana.mymoney.exception.WalletException;
import com.vn.hcmute.team.cortana.mymoney.model.Budget;
import com.vn.hcmute.team.cortana.mymoney.model.Category;
import com.vn.hcmute.team.cortana.mymoney.model.Currencies;
import com.vn.hcmute.team.cortana.mymoney.model.DebtLoan;
import com.vn.hcmute.team.cortana.mymoney.model.Event;
import com.vn.hcmute.team.cortana.mymoney.model.Icon;
import com.vn.hcmute.team.cortana.mymoney.model.Person;
import com.vn.hcmute.team.cortana.mymoney.model.Saving;
import com.vn.hcmute.team.cortana.mymoney.model.Transaction;
import com.vn.hcmute.team.cortana.mymoney.model.Wallet;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;
import java.util.List;
import java.util.concurrent.Callable;
import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by infamouSs on 8/10/17.
 */

@Singleton
public class LocalRepository implements LocalTask.IconTask, LocalTask.CurrencyTask,
                                        LocalTask.CategoryTask, LocalTask.SavingTask,
                                        LocalTask.WalletTask, LocalTask.EventTask,
                                        LocalTask.BudgetTask, LocalTask.PersonTask,
                                        LocalTask.TransactionTask, LocalTask.DebtLoanTask {
    
    public static final String TAG = LocalRepository.class.getSimpleName();
    
    private DatabaseHelper mDatabaseHelper;
    private Context mContext;
    
    @Inject
    public LocalRepository(Context context) {
        mContext = context.getApplicationContext();
        this.mDatabaseHelper = DatabaseHelper.getInstance(mContext.getApplicationContext());
    }
    
    @Override
    public Observable<List<Icon>> getListIcon() {
        ImageLocalService imageLocalService = ImageLocalService.getInstance(mDatabaseHelper);
        
        Callable<List<Icon>> callable = imageLocalService.getListIcon();
        
        return makeObservable(callable);
    }
    
    @Override
    public Observable<List<Currencies>> getListCurrency() {
        CurrencyLocalService currencyLocalService = CurrencyLocalService
                  .getInstance(mDatabaseHelper);
        
        Callable<List<Currencies>> callable = currencyLocalService.getListCurrency();
        
        return makeObservable(callable);
    }
    
    private <T> Observable<T> makeObservable(final Callable<T> callable) {
        return Observable.create(new ObservableOnSubscribe<T>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<T> e) throws Exception {
                try {
                    e.onNext(callable.call());
                    e.onComplete();
                } catch (Exception ex) {
                    e.onError(ex);
                }
            }
        });
    }
    
    private <T> Observable<T> makeObservable(final T object) {
        return Observable.create(new ObservableOnSubscribe<T>() {
            @Override
            public void subscribe(ObservableEmitter<T> e) throws Exception {
                try {
                    e.onNext(object);
                    e.onComplete();
                } catch (Exception ex) {
                    e.onError(ex);
                }
            }
        });
    }
    
    @Override
    public Observable<List<Category>> getListCategory(String transType) {
        CategoryLocalService categoryLocalService = CategoryLocalService
                  .getInstance(mDatabaseHelper);
        
        String selection = "trans_type = ?";
        String[] selectionArg = new String[]{transType};
        
        Callable<List<Category>> callable = categoryLocalService
                  .getListCategory(selection, selectionArg);
        
        return makeObservable(callable);
    }
    
    @Override
    public Observable<List<Category>> getListCategoryByType(String type, String transType) {
        CategoryLocalService categoryLocalService = CategoryLocalService
                  .getInstance(mDatabaseHelper);
        
        String selection = "type = ? and trans_type = ?";
        String[] selectionArg = new String[]{type, transType};
        
        Callable<List<Category>> callable = categoryLocalService
                  .getListCategory(selection, selectionArg);
        
        return makeObservable(callable);
    }
    
    /*Area saving*/
    @Override
    public Observable<String> addCategory(Category category) {
        CategoryLocalService categoryLocalService = new CategoryLocalService
                  (mDatabaseHelper);
        
        Callable<Long> callable = categoryLocalService
                  .addCategory(category);
        
        return makeObservable(callable).map(new Function<Long, String>() {
            @Override
            public String apply(@NonNull Long aLong) throws Exception {
                if (aLong > 0) {
                    return mContext.getString(R.string.message_add_category_success);
                } else {
                    throw new Exception(mContext.getString(R.string.message_category_fail));
                }
            }
        });
    }
    
    @Override
    public Observable<String> updateCategory(Category category) {
        CategoryLocalService categoryLocalService = CategoryLocalService
                  .getInstance(mDatabaseHelper);
        
        Callable<Integer> callable = categoryLocalService
                  .updateCategory(category);
        
        return makeObservable(callable).map(new Function<Integer, String>() {
            @Override
            public String apply(@NonNull Integer aLong) throws Exception {
                if (aLong > 0) {
                    return mContext.getString(R.string.message_update_category_success);
                } else {
                    throw new CategoryException(mContext.getString(R.string.message_category_fail));
                }
            }
        });
    }
    
    @Override
    public Observable<String> deleteCategory(Category category) {
        CategoryLocalService categoryLocalService = CategoryLocalService
                  .getInstance(mDatabaseHelper);
        Callable<Integer> callable = categoryLocalService
                  .deleteCategory(category);
        
        return makeObservable(callable).map(new Function<Integer, String>() {
            @Override
            public String apply(@NonNull Integer aLong) throws Exception {
                if (aLong > 0) {
                    return mContext.getString(R.string.message_delete_category_success);
                } else {
                    throw new CategoryException(mContext.getString(R.string.message_category_fail));
                }
            }
        });
    }
    
    @Override
    public Observable<List<Saving>> getListSaving(String userId) {
        SavingLocalService savingLocalService = SavingLocalService.getInstance(mDatabaseHelper);
        Callable<List<Saving>> list = savingLocalService.getListSaving(userId);
        return makeObservable(list);
    }
    
    @Override
    public Observable<String> addSaving(Saving saving) {
        SavingLocalService savingLocalService = SavingLocalService.getInstance(mDatabaseHelper);
        Callable<Long> callable = savingLocalService.addSaving(saving);
        return makeObservable(callable).map(new Function<Long, String>() {
            @Override
            public String apply(@NonNull Long aLong) throws Exception {
                if (aLong > 0) {
                    return mContext.getString(R.string.txt_add_saving_success);
                } else {
                    throw new SavingException(mContext.getString(R.string.txt_add_saving_fail));
                }
            }
        });
    }
    
    @Override
    public Observable<String> updateSaving(Saving saving) {
        SavingLocalService savingLocalService = SavingLocalService.getInstance(mDatabaseHelper);
        Callable<Integer> callable = savingLocalService.updateSaving(saving);
        return makeObservable(callable).map(new Function<Integer, String>() {
            @Override
            public String apply(@NonNull Integer integer) throws Exception {
                if (integer > 0) {
                    return mContext.getString(R.string.txt_success);
                } else {
                    throw new SavingException(mContext.getString(R.string.txt_add_saving_fail));
                }
            }
        });
    }
    
    @Override
    public Observable<String> deleteSaving(String saving_id) {
        SavingLocalService savingLocalService = SavingLocalService.getInstance(mDatabaseHelper);
        Callable<Integer> callable = savingLocalService.deleteSaving(saving_id);
        return makeObservable(callable).map(new Function<Integer, String>() {
            @Override
            public String apply(@NonNull Integer integer) throws Exception {
                if (integer > 0) {
                    return mContext.getString(R.string.txt_success);
                } else {
                    throw new SavingException(mContext.getString(R.string.txt_add_saving_fail));
                }
            }
        });
    }
    
    @Override
    public Observable<String> updateStatusSaving(List<Saving> savingList) {
        SavingLocalService savingLocalService = SavingLocalService.getInstance(mDatabaseHelper);
        Callable<Integer> callable = savingLocalService.updateStatusSaving(savingList);
        return makeObservable(callable).map(new Function<Integer, String>() {
            @Override
            public String apply(@NonNull Integer integer) throws Exception {
                if (integer > 0) {
                    return mContext.getString(R.string.txt_success);
                } else {
                    throw new SavingException(mContext.getString(R.string.txt_add_saving_fail));
                }
            }
        });
    }
    
    @Override
    public Observable<String> takeInSaving(String idWallet, String idSaving, String moneyWallet,
              String moneySaving) {
        SavingLocalService savingLocalService = SavingLocalService.getInstance(mDatabaseHelper);
        Callable<Integer> callable = savingLocalService
                  .takeInSaving(idWallet, idSaving, moneyWallet, moneySaving);
        return makeObservable(callable).map(new Function<Integer, String>() {
            @Override
            public String apply(@NonNull Integer integer) throws Exception {
                if (integer > 0) {
                    return mContext.getString(R.string.txt_success);
                } else {
                    throw new SavingException(mContext.getString(R.string.txt_add_saving_fail));
                }
            }
        });
    }
    
    @Override
    public Observable<String> takeOutSaving(String idWallet, String idSaving, String moneyWallet,
              String moneySaving) {
        SavingLocalService savingLocalService = SavingLocalService.getInstance(mDatabaseHelper);
        Callable<Integer> callable = savingLocalService
                  .takeOutSaving(idWallet, idSaving, moneyWallet, moneySaving);
        return makeObservable(callable).map(new Function<Integer, String>() {
            @Override
            public String apply(@NonNull Integer integer) throws Exception {
                if (integer > 0) {
                    return mContext.getString(R.string.txt_success);
                } else {
                    throw new SavingException(mContext.getString(R.string.txt_add_saving_fail));
                }
            }
        });
    }
    
    /*Area wallet*/
    @Override
    public Observable<List<Wallet>> getListWallet(String userId) {
        WalletLocalService walletLocalService = WalletLocalService.getInstance(mDatabaseHelper);
        Callable<List<Wallet>> callable = walletLocalService.getListWallet(userId);
        return makeObservable(callable);
    }
    
    @Override
    public Observable<String> addWallet(Wallet wallet) {
        WalletLocalService walletLocalService = WalletLocalService.getInstance(mDatabaseHelper);
        Callable<Long> callable = walletLocalService.addWallet(wallet);
        return makeObservable(callable).map(new Function<Long, String>() {
            @Override
            public String apply(@NonNull Long aLong) throws Exception {
                if (aLong > 0) {
                    return mContext.getString(R.string.txt_success);
                } else {
                    throw new WalletException(mContext.getString(R.string.txt_add_saving_fail));
                }
            }
        });
    }
    
    @Override
    public Observable<String> updateWallet(Wallet wallet) {
        WalletLocalService walletLocalService = WalletLocalService.getInstance(mDatabaseHelper);
        Callable<Integer> callable = walletLocalService.updateWallet(wallet);
        return makeObservable(callable).map(new Function<Integer, String>() {
            @Override
            public String apply(@NonNull Integer integer) throws Exception {
                if (integer > 0) {
                    return mContext.getString(R.string.txt_success);
                } else {
                    throw new WalletException(mContext.getString(R.string.txt_add_saving_fail));
                }
            }
        });
    }
    
    @Override
    public Observable<String> deleteWallet(String idWallet) {
        WalletLocalService walletLocalService = WalletLocalService.getInstance(mDatabaseHelper);
        Callable<Integer> callable = walletLocalService.deleteWallet(idWallet);
        return makeObservable(callable).map(new Function<Integer, String>() {
            @Override
            public String apply(@NonNull Integer integer) throws Exception {
                if (integer > 0) {
                    return mContext.getString(R.string.txt_success);
                } else {
                    throw new WalletException(mContext.getString(R.string.txt_add_saving_fail));
                }
            }
        });
    }
    
    @Override
    public Observable<Wallet> getWalletById(String wallet_id) {
        WalletLocalService walletLocalService = WalletLocalService.getInstance(mDatabaseHelper);
        Wallet wallet = walletLocalService.getWalletById(wallet_id);
        return makeObservable(wallet);
    }
    
    @Override
    public Observable<String> moveWallet(String userid, String wallet_id_from, String wallet_id_to,
              String moneyMinus, String moneyPlus, String date_created) {
        WalletLocalService walletLocalService = WalletLocalService.getInstance(mDatabaseHelper);
        
        return makeObservable(walletLocalService
                  .moveWallet(userid, wallet_id_from, wallet_id_to, moneyMinus, moneyPlus,
                            date_created)).map(new Function<Integer, String>() {
            @Override
            public String apply(Integer integer) throws Exception {
                if (integer > 0) {
                    return mContext.getString(R.string.txt_success);
                } else {
                    throw new WalletException(mContext.getString(R.string.txt_add_saving_fail));
                }
            }
        });
    }
    
    
    /*Area Event*/
    @Override
    public Observable<List<Event>> getListEvent(String userId) {
        EventLocalService eventLocalService = EventLocalService.getInstance(mDatabaseHelper);
        Callable<List<Event>> callable = eventLocalService.getListEvent(userId);
        return makeObservable(callable);
    }
    
    @Override
    public Observable<String> addEvent(Event event) {
        EventLocalService eventLocalService = EventLocalService.getInstance(mDatabaseHelper);
        Callable<Long> callable = eventLocalService.addEvent(event);
        return makeObservable(callable).map(new Function<Long, String>() {
            @Override
            public String apply(@NonNull Long aLong) throws Exception {
                if (aLong > 0) {
                    return mContext.getString(R.string.txt_success);
                } else {
                    throw new EventException(mContext.getString(R.string.txt_add_saving_fail));
                }
            }
        });
    }
    
    @Override
    public Observable<String> updateEvent(Event event) {
        EventLocalService eventLocalService = EventLocalService.getInstance(mDatabaseHelper);
        Callable<Integer> callable = eventLocalService.updateEvent(event);
        return makeObservable(callable).map(new Function<Integer, String>() {
            @Override
            public String apply(@NonNull Integer integer) throws Exception {
                if (integer > 0) {
                    return mContext.getString(R.string.txt_success);
                } else {
                    throw new EventException(mContext.getString(R.string.txt_add_saving_fail));
                }
            }
        });
    }
    
    @Override
    public Observable<String> updateStatusEvent(List<Event> eventList) {
        EventLocalService eventLocalService = EventLocalService.getInstance(mDatabaseHelper);
        Callable<Integer> callable = eventLocalService.updateStatusEvent(eventList);
        return makeObservable(callable).map(new Function<Integer, String>() {
            @Override
            public String apply(@NonNull Integer integer) throws Exception {
                if (integer > 0) {
                    return mContext.getString(R.string.txt_success);
                } else {
                    throw new EventException(mContext.getString(R.string.txt_add_saving_fail));
                }
            }
        });
    }
    
    @Override
    public Observable<String> deleteEvent(String idEvent) {
        EventLocalService eventLocalService = EventLocalService.getInstance(mDatabaseHelper);
        Callable<Integer> callable = eventLocalService.deleteEvent(idEvent);
        return makeObservable(callable).map(new Function<Integer, String>() {
            @Override
            public String apply(@NonNull Integer integer) throws Exception {
                if (integer > 0) {
                    return mContext.getString(R.string.txt_success);
                } else {
                    throw new EventException(mContext.getString(R.string.txt_add_saving_fail));
                }
            }
        });
    }
    
    /*Area Budget*/
    @Override
    public Observable<List<Budget>> getListBudget(String userId) {
        BudgetLocalService budgetLocalService = BudgetLocalService.getInstance(mDatabaseHelper);
        Callable<List<Budget>> callable = budgetLocalService.getListBudget(userId);
        return makeObservable(callable);
    }
    
    @Override
    public Observable<String> addBudet(Budget budget) {
        BudgetLocalService budgetLocalService = BudgetLocalService.getInstance(mDatabaseHelper);
        Callable<Long> callable = budgetLocalService.addBudet(budget);
        return makeObservable(callable).map(new Function<Long, String>() {
            @Override
            public String apply(@NonNull Long aLong) throws Exception {
                if (aLong > 0) {
                    return mContext.getString(R.string.txt_success);
                } else {
                    throw new BudgetException(mContext.getString(R.string.txt_add_saving_fail));
                }
            }
        });
    }
    
    @Override
    public Observable<String> updateBudget(Budget budget) {
        BudgetLocalService budgetLocalService = BudgetLocalService.getInstance(mDatabaseHelper);
        Callable<Integer> callable = budgetLocalService.updateBudget(budget);
        return makeObservable(callable).map(new Function<Integer, String>() {
            @Override
            public String apply(@NonNull Integer integer) throws Exception {
                if (integer > 0) {
                    return mContext.getString(R.string.txt_success);
                } else {
                    throw new BudgetException(mContext.getString(R.string.txt_add_saving_fail));
                }
            }
        });
    }
    
    @Override
    public Observable<String> updateStatusBudget(List<Budget> budgetList) {
        BudgetLocalService budgetLocalService = BudgetLocalService.getInstance(mDatabaseHelper);
        Callable<Integer> callable = budgetLocalService.updateStatusBudget(budgetList);
        return makeObservable(callable).map(new Function<Integer, String>() {
            @Override
            public String apply(@NonNull Integer integer) throws Exception {
                if (integer > 0) {
                    return mContext.getString(R.string.txt_success);
                } else {
                    throw new BudgetException(mContext.getString(R.string.txt_add_saving_fail));
                }
            }
        });
    }
    
    @Override
    public Observable<String> deleteBudget(String idBudget) {
        BudgetLocalService budgetLocalService = BudgetLocalService.getInstance(mDatabaseHelper);
        Callable<Integer> callable = budgetLocalService.deleteBudget(idBudget);
        return makeObservable(callable).map(new Function<Integer, String>() {
            @Override
            public String apply(@NonNull Integer integer) throws Exception {
                if (integer > 0) {
                    return mContext.getString(R.string.txt_success);
                } else {
                    throw new BudgetException(mContext.getString(R.string.txt_add_saving_fail));
                }
            }
        });
    }
    
    /*Area person*/
    @Override
    public Observable<List<Person>> getListPerson(String userId) {
        PersonLocalService personLocalService = PersonLocalService.getInstance(mDatabaseHelper);
        Callable<List<Person>> callable = personLocalService.getListPerson(userId);
        return makeObservable(callable);
    }
    
    @Override
    public Observable<String> addPerson(Person person) {
        PersonLocalService personLocalService = PersonLocalService.getInstance(mDatabaseHelper);
        Callable<Long> callable = personLocalService.addPerson(person);
        return makeObservable(callable).map(new Function<Long, String>() {
            @Override
            public String apply(@NonNull Long aLong) throws Exception {
                if (aLong > 0) {
                    return mContext.getString(R.string.txt_success);
                } else {
                    throw new PersonException(mContext.getString(R.string.txt_fail));
                }
            }
        });
    }
    
    @Override
    public Observable<String> updatePerson(Person person) {
        PersonLocalService personLocalService = PersonLocalService.getInstance(mDatabaseHelper);
        Callable<Integer> callable = personLocalService.updatePerson(person);
        return makeObservable(callable).map(new Function<Integer, String>() {
            @Override
            public String apply(@NonNull Integer integer) throws Exception {
                if (integer > 0) {
                    return mContext.getString(R.string.txt_success);
                } else {
                    throw new PersonException(mContext.getString(R.string.txt_add_saving_fail));
                }
            }
        });
    }
    
    @Override
    public Observable<String> deletePerson(String isPerson) {
        PersonLocalService personLocalService = PersonLocalService.getInstance(mDatabaseHelper);
        Callable<Integer> callable = personLocalService.deletePerson(isPerson);
        return makeObservable(callable).map(new Function<Integer, String>() {
            @Override
            public String apply(@NonNull Integer integer) throws Exception {
                if (integer > 0) {
                    return mContext.getString(R.string.txt_success);
                } else {
                    throw new PersonException(mContext.getString(R.string.txt_add_saving_fail));
                }
            }
        });
    }
    
    @Override
    public Observable<String> addTransaction(Transaction transaction) {
        TransactionLocalService transactionLocalService = TransactionLocalService
                  .getInstance(mDatabaseHelper);
        Callable<Long> callable = transactionLocalService.addTransaction(transaction);
        
        return makeObservable(callable).map(new Function<Long, String>() {
            @Override
            public String apply(Long aLong) throws Exception {
                if (aLong > 0) {
                    return mContext.getString(R.string.txt_success);
                } else {
                    throw new TransactionException(
                              mContext.getString(R.string.txt_fail));
                }
            }
        });
    }
    
    @Override
    public Observable<String> updateTransaction(Transaction transaction) {
        TransactionLocalService transactionLocalService = TransactionLocalService
                  .getInstance(mDatabaseHelper);
        Callable<Integer> callable = transactionLocalService.updateTransaction(transaction);
        
        return makeObservable(callable).map(new Function<Integer, String>() {
            @Override
            public String apply(Integer aLong) throws Exception {
                if (aLong > 0) {
                    return mContext.getString(R.string.txt_success);
                } else {
                    throw new TransactionException(
                              mContext.getString(R.string.txt_fail));
                }
            }
        });
    }
    
    @Override
    public Observable<String> deleteTransaction(Transaction transaction) {
        TransactionLocalService transactionLocalService = TransactionLocalService
                  .getInstance(mDatabaseHelper);
        Callable<Integer> callable = transactionLocalService.deleteTransaction(transaction);
        
        return makeObservable(callable).map(new Function<Integer, String>() {
            @Override
            public String apply(Integer aLong) throws Exception {
                if (aLong > 0) {
                    return mContext.getString(R.string.txt_success);
                } else {
                    throw new TransactionException(
                              mContext.getString(R.string.txt_fail));
                }
            }
        });
    }
    
    @Override
    public Observable<List<Transaction>> getTransactionByTime(String user_id, String start,
              String end, String wallet_id) {
        TransactionLocalService transactionLocalService = TransactionLocalService
                  .getInstance(mDatabaseHelper);
        Callable<List<Transaction>> callable = transactionLocalService
                  .getTransactionByTime(user_id, start, end, wallet_id);
        
        return makeObservable(callable);
    }
    
    @Override
    public Observable<Transaction> getTransactionByIdUseCallable(String trans_id) {
        TransactionLocalService transactionLocalService = TransactionLocalService
                  .getInstance(mDatabaseHelper);
        Callable<Transaction> callable = transactionLocalService
                  .getTransactionByIdUseCallable(trans_id);
        
        return makeObservable(callable);
    }
    
    @Override
    public Observable<List<Transaction>> getAllTransaction(String user_id) {
        TransactionLocalService transactionLocalService = TransactionLocalService
                  .getInstance(mDatabaseHelper);
        Callable<List<Transaction>> callable = transactionLocalService
                  .getAllTransaction(user_id);
        
        return makeObservable(callable);
    }
    
    @Override
    public Observable<List<Transaction>> getTransactionBySaving(String user_id, String saving_id) {
        TransactionLocalService transactionLocalService = TransactionLocalService
                  .getInstance(mDatabaseHelper);
        Callable<List<Transaction>> callable = transactionLocalService
                  .getTransactionBySaving(user_id, saving_id);
    
        return makeObservable(callable);
    }
    
    @Override
    public Observable<List<Transaction>> getTransactionByEvent(String user_id, String event_id) {
        TransactionLocalService transactionLocalService = TransactionLocalService
                  .getInstance(mDatabaseHelper);
        Callable<List<Transaction>> callable = transactionLocalService
                  .getTransactionByEvent(user_id, event_id);
        return makeObservable(callable);
    }
    
    @Override
    public Observable<List<Transaction>> getTransactionByBudget(String user_id, String start,
              String end, String cate_id, String wallet_id) {
        TransactionLocalService transactionLocalService = TransactionLocalService
                  .getInstance(mDatabaseHelper);
        Callable<List<Transaction>> callable = transactionLocalService
                  .getTransactionByBudget(user_id, start, end, cate_id, wallet_id);
        
        return makeObservable(callable);
    }
    
    @Override
    public Observable<List<DebtLoan>> getDebtLoanByWalletId(String wallet_id) {
        DebtLoanLocalService debtLoanLocalService = DebtLoanLocalService
                  .getInstance(mDatabaseHelper);
        
        Callable<List<DebtLoan>> callable = debtLoanLocalService.getDebtLoanByWalletId(wallet_id);
        
        return makeObservable(callable);
    }
    
    @Override
    public Observable<List<DebtLoan>> getDebtLoanByType(String wallet_id, String type) {
        DebtLoanLocalService debtLoanLocalService = DebtLoanLocalService
                  .getInstance(mDatabaseHelper);
        
        Callable<List<DebtLoan>> callable = debtLoanLocalService.getDebtLoanByType(wallet_id, type);
        
        return makeObservable(callable);
    }
    
    @Override
    public Observable<String> addDebtLoan(DebtLoan debtLoan) {
        DebtLoanLocalService debtLoanLocalService = DebtLoanLocalService
                  .getInstance(mDatabaseHelper);
        
        Callable<Long> callable = debtLoanLocalService.addDebtLoan(debtLoan);
        
        return makeObservable(callable).map(new Function<Long, String>() {
            @Override
            public String apply(Long aLong) throws Exception {
                if (aLong > 0) {
                    return mContext.getString(R.string.txt_success);
                } else {
                    throw new DebtLoanException(mContext.getString(R.string.txt_fail));
                }
            }
        });
    }
    
    @Override
    public Observable<String> updateDebtLoan(DebtLoan debtLoan) {
        DebtLoanLocalService debtLoanLocalService = DebtLoanLocalService
                  .getInstance(mDatabaseHelper);
        
        Callable<Integer> callable = debtLoanLocalService.updateDebtLoan(debtLoan);
        
        return makeObservable(callable).map(new Function<Integer, String>() {
            @Override
            public String apply(Integer aLong) throws Exception {
                if (aLong > 0) {
                    return mContext.getString(R.string.txt_success);
                } else {
                    throw new DebtLoanException(mContext.getString(R.string.txt_fail));
                }
            }
        });
    }
    
    @Override
    public Observable<String> deleteDebtLoan(DebtLoan debtLoan) {
        DebtLoanLocalService debtLoanLocalService = DebtLoanLocalService
                  .getInstance(mDatabaseHelper);
        
        Callable<Integer> callable = debtLoanLocalService.deleteDebtLoan(debtLoan);
        
        return makeObservable(callable).map(new Function<Integer, String>() {
            @Override
            public String apply(Integer aLong) throws Exception {
                if (aLong > 0) {
                    return mContext.getString(R.string.txt_success);
                } else {
                    throw new DebtLoanException(mContext.getString(R.string.txt_fail));
                }
            }
        });
    }
}