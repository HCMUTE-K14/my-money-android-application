package com.vn.hcmute.team.cortana.mymoney.di.module;

import android.app.Application;
import android.content.Context;
import com.vn.hcmute.team.cortana.mymoney.MyMoneyApplication;
import dagger.Module;
import dagger.Provides;

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
    
    
}
