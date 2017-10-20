package com.vn.hcmute.team.cortana.mymoney.data;

import com.vn.hcmute.team.cortana.mymoney.model.Budget;
import com.vn.hcmute.team.cortana.mymoney.model.Category;
import com.vn.hcmute.team.cortana.mymoney.model.Currencies;
import com.vn.hcmute.team.cortana.mymoney.model.DebtLoan;
import com.vn.hcmute.team.cortana.mymoney.model.Event;
import com.vn.hcmute.team.cortana.mymoney.model.Icon;
import com.vn.hcmute.team.cortana.mymoney.model.Image;
import com.vn.hcmute.team.cortana.mymoney.model.Person;
import com.vn.hcmute.team.cortana.mymoney.model.RealTimeCurrency;
import com.vn.hcmute.team.cortana.mymoney.model.ResultConvert;
import com.vn.hcmute.team.cortana.mymoney.model.Saving;
import com.vn.hcmute.team.cortana.mymoney.model.Transaction;
import com.vn.hcmute.team.cortana.mymoney.model.User;
import com.vn.hcmute.team.cortana.mymoney.model.UserCredential;
import com.vn.hcmute.team.cortana.mymoney.model.Wallet;
import io.reactivex.Observable;
import java.util.List;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * Created by infamouSs on 8/10/17.
 */

public interface DataSource {
    
    public interface RemoteDataSource {
        
        Observable<User> login(UserCredential userCredential);
        
        Observable<User> login(User user);
        
        Observable<String> isExistFacebookAccount(User user);
        
        Observable<String> register(User user);
        
        Observable<String> forgetPassword(String email);
        
        Observable<String> changePassword(String userid, String token, String oldPassword,
                  String newPassword);
        
        Observable<String> changeProfile(String userid, String token, User user);
        
        Observable<List<Image>> getImage(String userid, String token);
        
        Observable<List<Image>> uploadImage(RequestBody userid, RequestBody token,
                  RequestBody detail,
                  List<MultipartBody.Part> files);
        
        Observable<Image> getImageById(String userid, String token, String imageid);
        
        Observable<String> removeImage(String userid, String token, String imageid);
        
        Observable<String> updateImage(String userid, String token, String imageid);
        
        
        Observable<String> createWallet(Wallet wallet, String userid, String token);
        
        Observable<String> updateWallet(Wallet wallet, String userid, String token);
        
        Observable<String> deleteWallet(String userid, String token, String idwallet);
        
        Observable<List<Wallet>> getAllWallet(String userid, String token);
        
        Observable<String> moveWallet(String userid, String token, String wallet1, String wallet2,
                  String money);
        
        Observable<List<Currencies>> getCurrencies();
        
        Observable<ResultConvert> convertCurrency(String amount, String from, String to);
        
        Observable<RealTimeCurrency> updateRealTimeCurrency();
        
        Observable<List<Event>> getEvent(String uerid, String token);
        
        Observable<String> createEvent(Event event, String userid, String token);
        
        Observable<String> updateEvent(Event event, String userid, String token);
        
        Observable<String> deleteEvent(String userid, String token, String idEvent);
        
        Observable<List<Saving>> getSaving(String userid, String token);
        
        Observable<String> createSaving(Saving saving, String userid, String token);
        
        Observable<String> updateSaving(Saving saving, String userid, String token);
        
        Observable<String> deleteSaving(String userid, String token, String idSaving);
        
        Observable<String> takeInSaving(String userid, String token, String idWallet,
                  String idSaving, String moneyUpdateWallet, String moneyUpdateSaving);
        
        Observable<String> takeOutSaving(String userid, String token, String idWallet,
                  String idSaving, String moneyUpdateWallet, String moneyUpdateSaving);
        
        Observable<List<Person>> getPerson(String userid, String token);
        
        Observable<String> addPerson(Person person, String userid, String token);
        
        Observable<String> removePerson(String userid, String token, String personid);
        
        Observable<String> updatePerson(Person person, String userid, String token);
        
        Observable<String> syncPerson(List<Person> persons, String userid, String token);
        
        Observable<List<Budget>> getBudget(String userid, String token);
        
        Observable<String> createBudget(Budget budget, String userid, String token);
        
        Observable<String> updateBudget(Budget budget, String userid, String token);
        
        Observable<String> deleteBudget(String userid, String token, String budgetId);
        
        Observable<List<Category>> getListCategory(String userid, String token, String type);
        
        Observable<List<Category>> getListCategoryByType(String userid, String token, String type,
                  String transType);
        
        Observable<String> createCategory(String userid, String token, String parentId,
                  Category category);
        
        Observable<String> updateCategory(String userid, String token, String oldParentId,
                  String newParentId, Category category);
        
        Observable<String> deleteCategory(String userid, String token, String parentId,
                  Category category);
        
        Observable<String> addTransaction(String userid, String token, Transaction transaction);
        
        Observable<String> updateTransaction(String userid, String token, Transaction transaction);
        
        Observable<List<Transaction>> getTransactionByCategory(String userid, String token,
                  String categoryId, String walletId);
        
        Observable<Transaction> getTransactionById(String id, String userid, String token);
        
        Observable<List<Transaction>> getTransaction(String userid, String token);
        
        Observable<List<Transaction>> getTransactionByType(String userid, String token,
                  String type, String walletId);
        
        Observable<List<Transaction>> getTransactionByTime(String userid, String token,
                  String startDate,
                  String endDate, String walletId);
        
        Observable<List<Transaction>> getTransactionByEvent(String userid, String token,
                  String eventid);
        
        Observable<List<DebtLoan>> getDebtLoanByType(String userid, String token, String wallet_id,
                  String type);
        
        Observable<String> addDebtLoan(String userid, String token, DebtLoan debtLoan);
        
        Observable<String> updateDebtLoan(String userid, String token, String walelt_id,
                  DebtLoan debtLoan);
        
        Observable<String> deleteDebtLoan(String userid, String token, DebtLoan debtLoan);
    }
    
    public interface CacheDataSource {
        
        void putUserId(String userid);
        
        void putUserToken(String token);
        
        void putUser(User user);
        
        void putLoginState(User user);
        
        void clearPreferenceCache();
        
        String getUserId();
        
        String getUserToken();
        
        User getUser();
        
        void removeUserId();
        
        void removeUserToken();
        
        void removeUser();
        
        void removeLoginStage();
        
        void putRealTimeCurrency(RealTimeCurrency realTimeCurrency);
        
        void removeRealTimeCurrency();
        
        RealTimeCurrency getRealTimeCurrency();
    }
    
    public interface LocalDataSource {
        
        Observable<List<Icon>> getListIcon();
        
        Observable<List<Currencies>> getLocalListCurrency();
        
        Observable<List<Category>> getListLocalCategory(String transType);
        
        Observable<List<Category>> getListLocalCategoryByType(String type, String transType);
        
        Observable<String> addLocalCategory(Category category);
        
        Observable<String> updateLocalCategory(Category category);
        
        Observable<String> deleteLocalCategory(Category category);
        
        /*Area saving*/
        
        Observable<List<Saving>> getLocalListSaving(String userId);
        
        Observable<String> addLocalSaving(Saving saving);
        
        Observable<String> updateLocalSaving(Saving saving);
        
        Observable<String> deleteLocalSaving(String saving_id);
        
        Observable<String> takeInLocalSaving(String idWallet, String idSaving, String moneyWallet,
                  String moneySaving);
        
        Observable<String> takeOutLocalSaving(String idWallet, String idSaving, String moneyWallet,
                  String moneySaving);
        
        /*Area wallet*/
        
        Observable<List<Wallet>> getListWallet(String userId);
        
        Observable<String> addLocalWallet(Wallet wallet);
        
        Observable<String> updateLocalWallet(Wallet wallet);
        
        Observable<String> deleteLocalWallet(String idWallet);
        
        Observable<String> moveLocalWallet(String idWalletFrom, String idWalletTo, String Money);
        
        /*Area Event*/
        Observable<List<Event>> getListEvent(String userId);
        
        Observable<String> addLocalEvent(Event event);
        
        Observable<String> updateLocalEvent(Event event);
        
        Observable<String> deleteLocalEvent(String idEvent);
        
        /*Area Budget*/
        Observable<List<Budget>> getLocalListBudget(String userId);
        
        Observable<String> addLocalBudet(Budget budget);
        
        Observable<String> updateLocalBudget(Budget budget);
        
        Observable<String> deleteLocalBudget(String idBudget);
        
        /*Area person*/
        Observable<List<Person>> getLocalListPerson(String userId);
        
        Observable<String> addLocalPerson(Person person);
        
        Observable<String> updateLocalPerson(Person person);
        
        Observable<String> deleteLocalPeron(String idPerson);
    }
}
