package com.vn.hcmute.team.cortana.mymoney.data.cache.service;

import com.vn.hcmute.team.cortana.mymoney.model.User;

/**
 * Created by infamouSs on 8/21/17.
 */

public interface PreferenceService {
    
    void putUserId(String userid);
    
    void putUserToken(String token);
    
    void putUser(User user);
    
    void putLoginState(User user);
    
    void clearPreferences();
    
    String getUserId();
    
    String getUserToken();
    
    User getUser();
    
    void removeUserId();
    
    void removeUserToken();
    
    void removeUser();
    
    void removeLoginStage();
    
}
