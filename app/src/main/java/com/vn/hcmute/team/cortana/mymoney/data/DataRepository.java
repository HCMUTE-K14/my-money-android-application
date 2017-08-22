package com.vn.hcmute.team.cortana.mymoney.data;

import com.vn.hcmute.team.cortana.mymoney.data.cache.CacheRepository;
import com.vn.hcmute.team.cortana.mymoney.data.local.LocalRepository;
import com.vn.hcmute.team.cortana.mymoney.data.remote.RemoteRepository;
import com.vn.hcmute.team.cortana.mymoney.model.Currencies;
import com.vn.hcmute.team.cortana.mymoney.model.Event;
import com.vn.hcmute.team.cortana.mymoney.model.Image;
import com.vn.hcmute.team.cortana.mymoney.model.User;
import com.vn.hcmute.team.cortana.mymoney.model.UserCredential;
import com.vn.hcmute.team.cortana.mymoney.model.Wallet;
import io.reactivex.Observable;
import java.util.List;
import javax.inject.Inject;

/**
 * Created by infamouSs on 8/10/17.
 */

public class DataRepository implements DataSource.RemoteDataSource, DataSource.CacheDataSource,
                                       DataSource.LocalDataSource, DataSource.WalletDataSource,
                                       DataSource.CurrenciesDataSource, DataSource.EvetnDataSource {
    
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
    public Observable<List<Image>> getImage(String userid, String token) {
        return mRemoteRepository.getImage(userid, token);
    }
    
    
    @Override
    public Observable<String> uploadImage() {
        return null;
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
    
    //currencies
    @Override
    public Observable<List<Currencies>> getCurrencies() {
        
        return mRemoteRepository.getCurrencies();
    }
    //event
    @Override
    public Observable<List<Event>> getEvent(String uerid, String token) {
        return mRemoteRepository.getEvent(uerid,token);
    }
    
    @Override
    public Observable<String> createEvent(Event event, String userid, String token) {
        return mRemoteRepository.createEvent(event,userid,token);
    }
    
    @Override
    public Observable<String> updateEvent(Event event, String userid, String token) {
        return mRemoteRepository.updateEvent(event,userid,token);
    }
    
    @Override
    public Observable<String> deleteEvent(String userid, String token, String idEvent) {
        return mRemoteRepository.deleteEvent(userid,token,idEvent);
    }
}
