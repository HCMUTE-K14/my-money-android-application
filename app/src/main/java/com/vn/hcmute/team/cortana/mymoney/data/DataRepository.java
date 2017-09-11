package com.vn.hcmute.team.cortana.mymoney.data;

import com.vn.hcmute.team.cortana.mymoney.data.cache.CacheRepository;
import com.vn.hcmute.team.cortana.mymoney.data.local.LocalRepository;
import com.vn.hcmute.team.cortana.mymoney.data.remote.RemoteRepository;
import com.vn.hcmute.team.cortana.mymoney.model.Budget;
import com.vn.hcmute.team.cortana.mymoney.model.Currencies;
import com.vn.hcmute.team.cortana.mymoney.model.Event;
import com.vn.hcmute.team.cortana.mymoney.model.Icon;
import com.vn.hcmute.team.cortana.mymoney.model.Image;
import com.vn.hcmute.team.cortana.mymoney.model.Person;
import com.vn.hcmute.team.cortana.mymoney.model.RealTimeCurrency;
import com.vn.hcmute.team.cortana.mymoney.model.ResultConvert;
import com.vn.hcmute.team.cortana.mymoney.model.Saving;
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
    public Observable<String> uploadImage(RequestBody userid, RequestBody token, RequestBody detail,
              MultipartBody.Part file) {
        return mRemoteRepository.uploadImage(userid, token, detail, file);
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
              String idSaving, String money) {
        return mRemoteRepository.takeInSaving(userid, token, idWallet, idSaving, money);
    }
    
    @Override
    public Observable<String> takeOutSaving(String userid, String token, String idWallet,
              String idSaving, String money) {
        return mRemoteRepository.takeOutSaving(userid, token, idWallet, idSaving, money);
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
    public boolean doesDatabaseLocalExist() {
        return mLocalRepository.doesExistsDatabase();
    }
    
    @Override
    public void createNewLocalDatabase() {
        mLocalRepository.createNewDatabase();
    }
    
    @Override
    public Observable<List<Icon>> getListIcon() {
        return mLocalRepository.getListIcon();
    }
    
    @Override
    public Observable<List<Currencies>> getLocalListCurrency() {
        return mLocalRepository.getListCurrency();
    }
    
}
