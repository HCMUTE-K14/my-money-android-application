package com.vn.hcmute.team.cortana.mymoney.di.component;

import com.vn.hcmute.team.cortana.mymoney.di.module.ActivityModule;
import com.vn.hcmute.team.cortana.mymoney.di.module.SavingModule;
import com.vn.hcmute.team.cortana.mymoney.di.scope.PerActivity;
import com.vn.hcmute.team.cortana.mymoney.ui.saving.SavingActivity;
import dagger.Component;

/**
 * Created by kunsubin on 8/23/2017.
 */
@PerActivity
@Component(modules = {ActivityModule.class,
          SavingModule.class}, dependencies = ApplicationComponent.class)
public interface SavingComponent {
    void inject(SavingActivity savingActivity);
}
