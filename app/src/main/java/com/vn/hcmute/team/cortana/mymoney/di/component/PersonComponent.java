package com.vn.hcmute.team.cortana.mymoney.di.component;

import com.vn.hcmute.team.cortana.mymoney.di.module.ActivityModule;
import com.vn.hcmute.team.cortana.mymoney.di.module.PersonModule;
import com.vn.hcmute.team.cortana.mymoney.di.scope.PerActivity;
import com.vn.hcmute.team.cortana.mymoney.ui.person.PersonActivity;
import dagger.Component;

/**
 * Created by kunsubin on 8/23/2017.
 */
@PerActivity
@Component(modules = {ActivityModule.class,
          PersonModule.class}, dependencies = ApplicationComponent.class)
public interface PersonComponent {
    void inject(PersonActivity personActivity);
}
