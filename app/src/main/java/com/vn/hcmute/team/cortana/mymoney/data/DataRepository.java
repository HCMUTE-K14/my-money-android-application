package com.vn.hcmute.team.cortana.mymoney.data;

import com.vn.hcmute.team.cortana.mymoney.data.cache.CacheRepository;
import com.vn.hcmute.team.cortana.mymoney.data.local.LocalRepository;
import com.vn.hcmute.team.cortana.mymoney.data.remote.RemoteRepository;
import com.vn.hcmute.team.cortana.mymoney.model.Image;
import com.vn.hcmute.team.cortana.mymoney.model.User;
import com.vn.hcmute.team.cortana.mymoney.model.UserCredential;
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
}
