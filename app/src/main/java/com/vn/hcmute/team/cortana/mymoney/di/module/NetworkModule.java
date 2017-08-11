package com.vn.hcmute.team.cortana.mymoney.di.module;

import android.content.Context;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.vn.hcmute.team.cortana.mymoney.MyMoneyApplication;
import com.vn.hcmute.team.cortana.mymoney.data.DataRepository;
import com.vn.hcmute.team.cortana.mymoney.data.local.LocalRepository;
import com.vn.hcmute.team.cortana.mymoney.data.remote.RemoteRepository;
import com.vn.hcmute.team.cortana.mymoney.data.remote.ServiceGenerator;
import dagger.Module;
import dagger.Provides;
import javax.inject.Singleton;

/**
 * Created by infamouSs on 8/11/17.
 */
@Module
public class NetworkModule {
    
    
    private MyMoneyApplication mMoneyApplication;
    
    public NetworkModule(MyMoneyApplication myMoneyApplication) {
        this.mMoneyApplication = myMoneyApplication;
    }
    
    @Singleton
    @Provides
    public ServiceGenerator provideServiceGenerator(Context context, Gson gson) {
        return new ServiceGenerator(context, gson);
    }
    
    @Singleton
    @Provides
    public Gson provideGson() {
        return new GsonBuilder().create();
    }
    
    @Singleton
    @Provides
    public DataRepository provideDataRepository(
              LocalRepository localRepository,
              RemoteRepository remoteRepository) {
        return new DataRepository(remoteRepository, localRepository);
    }
}
