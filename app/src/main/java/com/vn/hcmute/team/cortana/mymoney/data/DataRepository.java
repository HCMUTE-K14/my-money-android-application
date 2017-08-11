package com.vn.hcmute.team.cortana.mymoney.data;

import com.vn.hcmute.team.cortana.mymoney.data.local.LocalRepository;
import com.vn.hcmute.team.cortana.mymoney.data.remote.RemoteRepository;
import com.vn.hcmute.team.cortana.mymoney.model.User;
import com.vn.hcmute.team.cortana.mymoney.model.UserCredential;
import io.reactivex.Observable;
import javax.inject.Inject;

/**
 * Created by infamouSs on 8/10/17.
 */

public class DataRepository implements DataSource {
    
    private RemoteRepository mRemoteRepository;
    private LocalRepository mLocalRepository;
    
    @Inject
    public DataRepository(RemoteRepository remoteRepository, LocalRepository localRepository) {
        this.mRemoteRepository = remoteRepository;
        this.mLocalRepository = localRepository;
    }
    
    @Override
    public Observable<User> login(UserCredential userCredential) {
        return this.mRemoteRepository.login(userCredential);
    }
    
    
    @Override
    public Observable<String> register(User user) {
        return this.mRemoteRepository.register(user);
    }
}
