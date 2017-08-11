package com.vn.hcmute.team.cortana.mymoney.di.component;

import com.vn.hcmute.team.cortana.mymoney.di.module.ActivityModule;
import com.vn.hcmute.team.cortana.mymoney.di.module.LoginModule;
import com.vn.hcmute.team.cortana.mymoney.di.scope.PerActivity;
import com.vn.hcmute.team.cortana.mymoney.ui.login.LoginActivity;
import dagger.Component;

/**
 * Created by infamouSs on 8/11/17.
 */
@PerActivity
@Component(modules = {LoginModule.class,
          ActivityModule.class}, dependencies = ApplicationComponent.class)
public interface LoginComponent {
    
    void inject(LoginActivity loginActivity);
}
