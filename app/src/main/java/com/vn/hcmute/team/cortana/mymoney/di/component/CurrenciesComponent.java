package com.vn.hcmute.team.cortana.mymoney.di.component;

import com.vn.hcmute.team.cortana.mymoney.di.module.ActivityModule;
import com.vn.hcmute.team.cortana.mymoney.di.module.CurrenciesModule;
import com.vn.hcmute.team.cortana.mymoney.di.scope.PerActivity;
import com.vn.hcmute.team.cortana.mymoney.ui.currencies.CurrenciesActivity;
import dagger.Component;

/**
 * Created by kunsubin on 8/22/2017.
 */
@PerActivity
@Component(modules = {ActivityModule.class,
          CurrenciesModule.class}, dependencies = ApplicationComponent.class)
public interface CurrenciesComponent {
    
    void inject(CurrenciesActivity currenciesActivity);
    
}
