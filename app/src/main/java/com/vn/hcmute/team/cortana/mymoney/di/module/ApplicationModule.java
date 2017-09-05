package com.vn.hcmute.team.cortana.mymoney.di.module;

import android.app.Application;
import android.content.Context;
import com.vn.hcmute.team.cortana.mymoney.MyMoneyApplication;
import com.vn.hcmute.team.cortana.mymoney.data.DataRepository;
import com.vn.hcmute.team.cortana.mymoney.data.cache.CacheRepository;
import com.vn.hcmute.team.cortana.mymoney.data.cache.PreferencesHelper;
import com.vn.hcmute.team.cortana.mymoney.data.local.LocalRepository;
import com.vn.hcmute.team.cortana.mymoney.data.remote.RemoteRepository;
import dagger.Module;
import dagger.Provides;
import javax.inject.Singleton;

/**
 * Created by infamouSs on 8/11/17.
 */

@Module
public class ApplicationModule {
    
    private MyMoneyApplication mMoneyApplication;
    
    public ApplicationModule(MyMoneyApplication application) {
        this.mMoneyApplication = application;
    }
    
    @Provides
    public Context provideApplicationContext() {
        return mMoneyApplication;
    }
    
    @Provides
    public MyMoneyApplication provideMyMoneyApplication() {
        return mMoneyApplication;
    }
    
    @Provides
    public Application provideApplication() {
        return mMoneyApplication;
    }
    
    @Singleton
    @Provides
    public PreferencesHelper providePreferenceHelper(){
        return new PreferencesHelper(mMoneyApplication);
    }
    
    @Singleton
    @Provides
    public DataRepository provideDataRepository(
              LocalRepository localRepository,
              RemoteRepository remoteRepository,
              CacheRepository cacheRepository) {
        return new DataRepository(remoteRepository, localRepository, cacheRepository);
    }
    
}
