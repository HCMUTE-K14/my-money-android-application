package com.vn.hcmute.team.cortana.mymoney.data;

import com.vn.hcmute.team.cortana.mymoney.data.cache.CacheRepository;
import com.vn.hcmute.team.cortana.mymoney.data.local.LocalRepository;
import com.vn.hcmute.team.cortana.mymoney.data.remote.RemoteRepository;
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
import javax.inject.Inject;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * Created by infamouSs on 8/10/17.
 */

public class DataRepository implements DataSource.RemoteDataSource, DataSource.CacheDataSource,
                                       DataSource.LocalDataSource {
    
    public static final String TAG = DataRepository.class.getSimpleName();
    
    private RemoteRepository mRemoteRepository;
    private LocalRepository mLocalRepository;
    private CacheRepository mCacheRepository;
    
    @Inject
    public DataRepository(RemoteRepository remoteRepository, LocalRepository localRepository,
              CacheRepository cacheRepository) {
        this.mRemoteRepository = remoteRepository;
        this.mLocalRepository = localRepository;
        this.mCacheRepository = cacheRepository;
    }
    
    @Override
    public Observable<User> login(UserCredential userCredential) {
        return this.mRemoteRepository.login(userCredential);
    }
    
    @Override
    public Observable<User> login(User user) {
        return this.mRemoteRepository.login(user);
    }
    
    @Override
    public Observable<String> isExistFacebookAccount(User user) {
        return this.mRemoteRepository.isExistFacebookAccount(user);
    }
    
    @Override
    public Observable<String> register(User user) {
        return this.mRemoteRepository.register(user);
    }
    
    @Override
    public Observable<String> forgetPassword(String email) {
        return this.mRemoteRepository.forgetPassword(email);
    }
    
    @Override
    public Observable<String> changePassword(String userid, String token, String oldPassword,
              String newPassword) {
        return this.mRemoteRepository.changePassword(userid, token, oldPassword, newPassword);
    }
    
    @Override
    public Observable<String> changeProfile(String userid, String token, User user) {
        return this.mRemoteRepository.changeProfile(userid, token, user);
    }
    
    @Override
    public Observable<List<Image>> getImage(String userid, String token) {
        return mRemoteRepository.getImage(userid, token);
    }
    
    @Override
    public Observable<List<Image>> uploadImage(RequestBody userid, RequestBody token,
              RequestBody detail,
              List<MultipartBody.Part> files) {
        return mRemoteRepository.uploadImage(userid, token, detail, files);
    }
    
    @Override
    public Observable<List<Transaction>> getTransactionByCategory(String userid, String token,
              String categoryId, String walletId) {
        return mRemoteRepository.getTransactionByCategory(userid, token, categoryId, walletId);
    }
    
    @Override
    public Observable<Transaction> getTransactionById(String id, String userid, String token) {
        return mRemoteRepository.getTransactionById(id, userid, token);
    }
    
    @Override
    public Observable<List<Transaction>> getTransaction(String userid, String token) {
        return mRemoteRepository.getTransaction(userid, token);
    }
    
    @Override
    public Observable<List<Transaction>> getTransactionByType(String userid, String token,
              String type,
              String walletId) {
        return mRemoteRepository.getTransactionByType(userid, token, type, walletId);
    }
    
    @Override
    public Observable<List<Transaction>> getTransactionByTime(String userid, String token,
              String startDate,
              String endDate, String walletId) {
        return mRemoteRepository.getTransactionByTime(userid, token, startDate, endDate, walletId);
    }
    
    @Override
    public Observable<List<Transaction>> getTransactionByEvent(String userid, String token,
              String eventid) {
        return mRemoteRepository.getTransactionByEvent(eventid, userid, token);
    }
    
    @Override
    public Observable<List<Transaction>> getTransactionBySaving(String userid, String token,
              String savingid) {
        return mRemoteRepository.getTransactionBySaving(savingid, userid, token);
    }
    
    @Override
    public Observable<List<Transaction>> getTransactionByBudget(String userid, String token,
              String startDate, String endDate, String categoryId, String walletId) {
        return mRemoteRepository
                  .getTransactionByBudget(startDate, endDate, categoryId, walletId, userid, token);
    }
    
    
    public Observable<List<DebtLoan>> getDebtLoanByType(String userid, String token,
              String wallet_id, String type) {
        return mRemoteRepository.getDebtLoan(userid, token, wallet_id, type);
    }
    
    @Override
    public Observable<String> addDebtLoan(String userid, String token, DebtLoan debtLoan) {
        return mRemoteRepository.addDebtLoan(userid, token, debtLoan);
    }
    
    @Override
    public Observable<String> updateDebtLoan(String userid, String token, String wallet_id,
              DebtLoan debtLoan) {
        return mRemoteRepository.updateDebtLoan(userid, token, debtLoan, wallet_id);
    }
    
    @Override
    public Observable<String> deleteDebtLoan(String userid, String token, DebtLoan debtLoan) {
        return mRemoteRepository.deleteDebtLoan(userid, token, debtLoan);
    }
    
    @Override
    public Observable<Image> getImageById(String userid, String token, String imageid) {
        return mRemoteRepository.getImageByid(userid, token, imageid);
    }
    
    @Override
    public Observable<String> removeImage(String userid, String token, String imageid) {
        return mRemoteRepository.removeImage(userid, token, imageid);
    }
    
    @Override
    public Observable<String> updateImage(String userid, String token, String imageid) {
        return mRemoteRepository.updateImage(userid, token, imageid);
    }
    
    
    @Override
    public void putUserId(String userid) {
        mCacheRepository.putUserId(userid);
    }
    
    @Override
    public void putUserToken(String token) {
        mCacheRepository.putUserToken(token);
    }
    
    @Override
    public void putUser(User user) {
        mCacheRepository.putUser(user);
    }
    
    @Override
    public void putLoginState(User user) {
        
        mCacheRepository.putLoginState(user);
    }
    
    @Override
    public void clearPreferenceCache() {
        mCacheRepository.clearPreferences();
    }
    
    @Override
    public String getUserId() {
        return mCacheRepository.getUserId();
    }
    
    @Override
    public String getUserToken() {
        return mCacheRepository.getUserToken();
    }
    
    @Override
    public User getUser() {
        return mCacheRepository.getUser();
    }
    
    @Override
    public void removeUserId() {
        mCacheRepository.removeUserId();
    }
    
    @Override
    public void removeUserToken() {
        mCacheRepository.removeUserToken();
    }
    
    @Override
    public void removeUser() {
        mCacheRepository.removeUser();
    }
    
    @Override
    public void removeLoginStage() {
        mCacheRepository.removeLoginStage();
    }
    
    @Override
    public void putRealTimeCurrency(RealTimeCurrency realTimeCurrency) {
        mCacheRepository.putRealTimeCurrency(realTimeCurrency);
    }
    
    @Override
    public void removeRealTimeCurrency() {
        mCacheRepository.removeRealTimeCurrency();
    }
    
    @Override
    public RealTimeCurrency getRealTimeCurrency() {
        return mCacheRepository.getRealTimeCurrency();
    }
    
    //wallet
    @Override
    public Observable<String> createWallet(Wallet wallet, String userid, String token) {
        return mRemoteRepository.createWallet(wallet, userid, token);
    }
    
    @Override
    public Observable<String> updateWallet(Wallet wallet, String userid, String token) {
        return mRemoteRepository.updateWallet(wallet, userid, token);
    }
    
    @Override
    public Observable<String> deleteWallet(String userid, String token, String idwallet) {
        return mRemoteRepository.deleteWallet(userid, token, idwallet);
    }
    
    @Override
    public Observable<List<Wallet>> getAllWallet(String userid, String token) {
        return mRemoteRepository.getAllWallet(userid, token);
    }
    
    @Override
    public Observable<String> moveWallet(String userid, String token, String wallet1,
              String wallet2, String money) {
        return mRemoteRepository.moveWallet(userid, token, wallet1, wallet2, money);
    }
    
    @Override
    public Observable<List<Currencies>> getCurrencies() {
        
        return mRemoteRepository.getCurrencies();
    }
    
    @Override
    public Observable<ResultConvert> convertCurrency(String amount, String from, String to) {
        return mRemoteRepository.convertCurrency(amount, from, to);
    }
    
    @Override
    public Observable<RealTimeCurrency> updateRealTimeCurrency() {
        return mRemoteRepository.getRealTimeCurrency();
    }
    
    //event
    @Override
    public Observable<List<Event>> getEvent(String uerid, String token) {
        return mRemoteRepository.getEvent(uerid, token);
    }
    
    @Override
    public Observable<String> createEvent(Event event, String userid, String token) {
        return mRemoteRepository.createEvent(event, userid, token);
    }
    
    @Override
    public Observable<String> updateEvent(Event event, String userid, String token) {
        return mRemoteRepository.updateEvent(event, userid, token);
    }
    
    @Override
    public Observable<String> deleteEvent(String userid, String token, String idEvent) {
        return mRemoteRepository.deleteEvent(userid, token, idEvent);
    }
    
    //saving
    @Override
    public Observable<List<Saving>> getSaving(String userid, String token) {
        return mRemoteRepository.getSaving(userid, token);
    }
    
    @Override
    public Observable<String> createSaving(Saving saving, String userid, String token) {
        return mRemoteRepository.createSaving(saving, userid, token);
    }
    
    @Override
    public Observable<String> updateSaving(Saving saving, String userid, String token) {
        return mRemoteRepository.updateSaving(saving, userid, token);
    }
    
    @Override
    public Observable<String> deleteSaving(String userid, String token, String idSaving) {
        return mRemoteRepository.deleteSaving(userid, token, idSaving);
    }
    
    @Override
    public Observable<String> takeInSaving(String userid, String token, String idWallet,
              String idSaving, String moneyUpdateWallet, String moneyUpdateSaving) {
        return mRemoteRepository.takeInSaving(userid, token, idWallet, idSaving, moneyUpdateWallet,
                  moneyUpdateSaving);
    }
    
    @Override
    public Observable<String> takeOutSaving(String userid, String token, String idWallet,
              String idSaving, String moneyUpdateWallet, String moneyUpdateSaving) {
        return mRemoteRepository.takeOutSaving(userid, token, idWallet, idSaving, moneyUpdateWallet,
                  moneyUpdateSaving);
    }
    
    //person
    @Override
    public Observable<List<Person>> getPerson(String userid, String token) {
        return mRemoteRepository.getPerson(userid, token);
    }
    
    @Override
    public Observable<String> addPerson(Person person, String userid, String token) {
        return mRemoteRepository.addPerson(person, userid, token);
    }
    
    @Override
    public Observable<String> removePerson(String userid, String token, String personid) {
        return mRemoteRepository.removePerson(userid, token, personid);
    }
    
    @Override
    public Observable<String> updatePerson(Person person, String userid, String token) {
        return mRemoteRepository.updatePerson(person, userid, token);
    }
    
    
    @Override
    public Observable<String> syncPerson(List<Person> persons, String userid, String token) {
        return mRemoteRepository.syncPerson(persons, userid, token);
    }
    
    //budget
    @Override
    public Observable<List<Budget>> getBudget(String userid, String token) {
        return mRemoteRepository.getBudget(userid, token);
    }
    
    @Override
    public Observable<String> createBudget(Budget budget, String userid, String token) {
        return mRemoteRepository.createBudget(budget, userid, token);
    }
    
    @Override
    public Observable<String> updateBudget(Budget budget, String userid, String token) {
        return mRemoteRepository.updateBudget(budget, userid, token);
    }
    
    @Override
    public Observable<String> deleteBudget(String userid, String token, String budgetId) {
        return mRemoteRepository.deleteBudget(userid, token, budgetId);
    }
    
    @Override
    public Observable<List<Category>> getListCategory(String userid, String token, String type) {
        return mRemoteRepository.getListCategory(userid, token, type);
    }
    
    @Override
    public Observable<List<Category>> getListCategoryByType(String userid, String token,
              String type, String transType) {
        
        return mRemoteRepository.getListCategoryByType(userid, token, type, transType);
    }
    
    @Override
    public Observable<String> createCategory(String userid, String token, String parentId,
              Category category) {
        return mRemoteRepository.createCategory(userid, token, parentId, category);
    }
    
    @Override
    public Observable<String> updateCategory(String userid, String token, String oldParentId,
              String newParentId, Category category) {
        return mRemoteRepository.updateCategory(userid, token, oldParentId, newParentId, category);
    }
    
    @Override
    public Observable<String> deleteCategory(String userid, String token, String parentId,
              Category category) {
        return mRemoteRepository.deleteCategory(userid, token, parentId, category);
    }
    
    @Override
    public Observable<String> addTransaction(String userid, String token, Transaction transaction) {
        return mRemoteRepository.addTransaction(userid, token, transaction);
    }
    
    @Override
    public Observable<String> updateTransaction(String userid, String token,
              Transaction transaction) {
        return mRemoteRepository.updateTransaction(userid, token, transaction);
    }
    
    @Override
    public Observable<String> deleteTransaction(String userid, String token, String trans_id) {
        return mRemoteRepository.deleteTransaction(userid, token, trans_id);
    }
    
    
    @Override
    public Observable<List<Icon>> getListIcon() {
        return mLocalRepository.getListIcon();
    }
    
    @Override
    public Observable<List<Currencies>> getLocalListCurrency() {
        return mLocalRepository.getListCurrency();
    }
    
    @Override
    public Observable<List<Category>> getListLocalCategory(String transType) {
        return mLocalRepository.getListCategory(transType);
    }
    
    @Override
    public Observable<List<Category>> getListLocalCategoryByType(String type, String transType) {
        return mLocalRepository.getListCategoryByType(type, transType);
    }
    
    @Override
    public Observable<String> addLocalCategory(Category category) {
        return mLocalRepository.addCategory(category);
    }
    
    @Override
    public Observable<String> updateLocalCategory(Category category) {
        return mLocalRepository.updateCategory(category);
    }
    
    @Override
    public Observable<String> deleteLocalCategory(Category category) {
        return mLocalRepository.deleteCategory(category);
    }
    
    /*Area saving*/
    @Override
    public Observable<List<Saving>> getLocalListSaving(String userId) {
        return mLocalRepository.getListSaving(userId);
    }
    
    @Override
    public Observable<String> addLocalSaving(Saving saving) {
        return mLocalRepository.addSaving(saving);
    }
    
    @Override
    public Observable<String> updateLocalSaving(Saving saving) {
        return mLocalRepository.updateSaving(saving);
    }
    
    @Override
    public Observable<String> deleteLocalSaving(String saving_id) {
        return mLocalRepository.deleteSaving(saving_id);
    }
    
    @Override
    public Observable<String> takeInLocalSaving(String idWallet, String idSaving,
              String moneyWallet,
              String moneySaving) {
        return mLocalRepository.takeInSaving(idWallet, idSaving, moneyWallet, moneySaving);
    }
    
    @Override
    public Observable<String> takeOutLocalSaving(String idWallet, String idSaving,
              String moneyWallet,
              String moneySaving) {
        return mLocalRepository.takeOutSaving(idWallet, idSaving, moneyWallet, moneySaving);
    }
    
    /*Area wallet*/
    @Override
    public Observable<List<Wallet>> getListWallet(String userId) {
        return mLocalRepository.getListWallet(userId);
    }
    
    @Override
    public Observable<String> addLocalWallet(Wallet wallet) {
        return mLocalRepository.addWallet(wallet);
    }
    
    @Override
    public Observable<String> updateLocalWallet(Wallet wallet) {
        return mLocalRepository.updateWallet(wallet);
    }
    
    @Override
    public Observable<String> deleteLocalWallet(String idWallet) {
        return mLocalRepository.deleteWallet(idWallet);
    }
    
    @Override
    public Observable<String> moveLocalWallet(String idWalletFrom, String idWalletTo,
              String Money) {
        return mLocalRepository.moveWallet(idWalletFrom, idWalletTo, Money);
    }
    
    /*Area Event*/
    @Override
    public Observable<List<Event>> getListEvent(String userId) {
        return mLocalRepository.getListEvent(userId);
    }
    
    @Override
    public Observable<String> addLocalEvent(Event event) {
        return mLocalRepository.addEvent(event);
    }
    
    @Override
    public Observable<String> updateLocalEvent(Event event) {
        return mLocalRepository.updateEvent(event);
    }
    
    @Override
    public Observable<String> deleteLocalEvent(String idEvent) {
        return mLocalRepository.deleteEvent(idEvent);
    }
    
    /*Area Budget*/
    @Override
    public Observable<List<Budget>> getLocalListBudget(String userId) {
        return mLocalRepository.getListBudget(userId);
    }
    
    @Override
    public Observable<String> addLocalBudet(Budget budget) {
        return mLocalRepository.addBudet(budget);
    }
    
    @Override
    public Observable<String> updateLocalBudget(Budget budget) {
        return mLocalRepository.updateBudget(budget);
    }
    
    @Override
    public Observable<String> deleteLocalBudget(String idBudget) {
        return mLocalRepository.deleteBudget(idBudget);
    }
    
    /*Area Person*/
    @Override
    public Observable<List<Person>> getLocalListPerson(String userId) {
        return mLocalRepository.getListPerson(userId);
    }
    
    @Override
    public Observable<String> addLocalPerson(Person person) {
        return mLocalRepository.addPerson(person);
    }
    
    @Override
    public Observable<String> updateLocalPerson(Person person) {
        return mLocalRepository.updatePerson(person);
    }
    
    @Override
    public Observable<String> deleteLocalPeron(String idPerson) {
        return mLocalRepository.deletePerson(idPerson);
    }
    
}
