package com.vn.hcmute.team.cortana.mymoney.di.component;

import com.vn.hcmute.team.cortana.mymoney.di.module.ActivityModule;
import com.vn.hcmute.team.cortana.mymoney.di.module.ForgetPasswordModule;
import com.vn.hcmute.team.cortana.mymoney.di.scope.PerActivity;
import com.vn.hcmute.team.cortana.mymoney.ui.forgetpassword.ForgetPasswordActivity;
import dagger.Component;

/**
 * Created by infamouSs on 8/25/17.
 */
@Component(modules = {ActivityModule.class,
          ForgetPasswordModule.class}, dependencies = ApplicationComponent.class)
@PerActivity
public interface ForgetPasswordComponent {
    
    void inject(ForgetPasswordActivity forgetPasswordActivity);
}
