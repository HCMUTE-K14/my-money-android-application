package com.vn.hcmute.team.cortana.mymoney.di.component;

import android.app.Application;
import com.vn.hcmute.team.cortana.mymoney.MyMoneyApplication;
import com.vn.hcmute.team.cortana.mymoney.data.remote.ServiceGenerator;
import com.vn.hcmute.team.cortana.mymoney.di.module.ApplicationModule;
import com.vn.hcmute.team.cortana.mymoney.di.module.NetworkModule;
import com.vn.hcmute.team.cortana.mymoney.di.scope.PerApplication;
import dagger.Component;
import javax.inject.Singleton;

/**
 * Created by infamouSs on 8/11/17.
 */

@Singleton
@PerApplication
@Component(modules = {ApplicationModule.class, NetworkModule.class})
public interface ApplicationComponent {
    
    Application application();
    
    MyMoneyApplication myMoneyApplication();
    
    ServiceGenerator serviceGenerator();
    
}
