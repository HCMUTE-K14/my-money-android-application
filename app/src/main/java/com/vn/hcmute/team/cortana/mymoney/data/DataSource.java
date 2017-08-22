package com.vn.hcmute.team.cortana.mymoney.data;

import com.vn.hcmute.team.cortana.mymoney.model.Image;
import com.vn.hcmute.team.cortana.mymoney.model.User;
import com.vn.hcmute.team.cortana.mymoney.model.UserCredential;
import io.reactivex.Observable;
import java.util.List;

/**
 * Created by infamouSs on 8/10/17.
 */

public interface DataSource {
    
    public interface RemoteDataSource {
        
        Observable<User> login(UserCredential userCredential);
        
        Observable<String> register(User user);
    
        Observable<List<Image>> getImage(String userid,String token);
    
        Observable<String> uploadImage();
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
    }
    
    public interface LocalDataSource {
        
    }
    
    
}
