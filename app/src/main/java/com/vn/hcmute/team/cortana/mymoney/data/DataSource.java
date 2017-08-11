package com.vn.hcmute.team.cortana.mymoney.data;

import com.vn.hcmute.team.cortana.mymoney.model.User;
import com.vn.hcmute.team.cortana.mymoney.model.UserCredential;
import io.reactivex.Observable;

/**
 * Created by infamouSs on 8/10/17.
 */

public interface DataSource {
    
    Observable<User> login(UserCredential userCredential);
    
    Observable<String> register(User user);
    
}
