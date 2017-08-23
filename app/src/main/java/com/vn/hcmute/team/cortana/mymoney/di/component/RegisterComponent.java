package com.vn.hcmute.team.cortana.mymoney.di.component;

import com.vn.hcmute.team.cortana.mymoney.di.module.ActivityModule;
import com.vn.hcmute.team.cortana.mymoney.di.module.RegisterModule;
import com.vn.hcmute.team.cortana.mymoney.di.scope.PerActivity;
import com.vn.hcmute.team.cortana.mymoney.ui.register.RegisterActivity;
import dagger.Component;

/**
 * Created by infamouSs on 8/11/17.
 */
@PerActivity
@Component(modules = {ActivityModule.class,
          RegisterModule.class}, dependencies = ApplicationComponent.class)
public interface RegisterComponent {
    
    void inject(RegisterActivity registerActivity);
    
}
