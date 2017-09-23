package com.vn.hcmute.team.cortana.mymoney.data.cache;

import com.vn.hcmute.team.cortana.mymoney.data.cache.service.PreferenceService;
import com.vn.hcmute.team.cortana.mymoney.model.RealTimeCurrency;
import com.vn.hcmute.team.cortana.mymoney.model.User;
import javax.inject.Inject;

/**
 * Created by infamouSs on 8/21/17.
 */

public class CacheRepository implements PreferenceService {
    
    public static final String TAG = CacheRepository.class.getSimpleName();
    
    private PreferencesHelper mPreferencesHelper;
    
    @Inject
    public CacheRepository(PreferencesHelper preferencesHelper) {
        this.mPreferencesHelper = preferencesHelper;
    }
    
    
    @Override
    public void putUserId(String userid) {
        mPreferencesHelper.putUserId(userid);
    }
    
    @Override
    public void putUserToken(String token) {
        mPreferencesHelper.putUserToken(token);
    }
    
    @Override
    public void putUser(User user) {
        mPreferencesHelper.putUser(user);
    }
    
    @Override
    public void putLoginState(User user) {
        mPreferencesHelper.putUserId(user.getUserid());
        mPreferencesHelper.putUserToken(user.getToken());
        mPreferencesHelper.putUser(user);
        
    }
    
    @Override
    public void clearPreferences() {
        mPreferencesHelper.clear();
    }
    
    @Override
    public String getUserId() {
        return mPreferencesHelper.getUserId();
    }
    
    @Override
    public String getUserToken() {
        return mPreferencesHelper.getUserToken();
    }
    
    @Override
    public User getUser() {
        return mPreferencesHelper.getCurrentUser();
    }
    
    @Override
    public void removeUserId() {
        mPreferencesHelper.putUserId("");
    }
    
    @Override
    public void removeUserToken() {
        mPreferencesHelper.putUserToken("");
    }
    
    @Override
    public void removeUser() {
        mPreferencesHelper.putUser(null);
    }
    
    @Override
    public void removeLoginStage() {
        mPreferencesHelper.putUserId("");
        mPreferencesHelper.putUserToken("");
        mPreferencesHelper.putUser(null);
    }
    
    @Override
    public void putRealTimeCurrency(RealTimeCurrency realTimeCurrency) {
        mPreferencesHelper.putRealTimeCurrency(realTimeCurrency);
    }
    
    @Override
    public void removeRealTimeCurrency() {
        mPreferencesHelper.putRealTimeCurrency(null);
    }
    
    @Override
    public RealTimeCurrency getRealTimeCurrency() {
        return mPreferencesHelper.getRealTimeCurrency();
    }
}
