package com.vn.hcmute.team.cortana.mymoney;

import android.app.Application;
import com.vn.hcmute.team.cortana.mymoney.di.component.ApplicationComponent;
import com.vn.hcmute.team.cortana.mymoney.di.component.DaggerApplicationComponent;
import com.vn.hcmute.team.cortana.mymoney.di.module.ApplicationModule;
import com.vn.hcmute.team.cortana.mymoney.di.module.NetworkModule;

/**
 * Created by infamouSs on 8/11/17.
 */

public class MyMoneyApplication extends Application {
    
    private ApplicationComponent mAppComponent;
    
    public MyMoneyApplication() {
        
    }
    
    @Override
    public void onCreate() {
        super.onCreate();
        
        mAppComponent = DaggerApplicationComponent
                  .builder()
                  .applicationModule(new ApplicationModule(this))
                  .networkModule(new NetworkModule(this))
                  .build();
        
    }
    
    public ApplicationComponent getAppComponent() {
        return mAppComponent;
    }
    
    
}
