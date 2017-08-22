package com.vn.hcmute.team.cortana.mymoney.di.component;

import com.vn.hcmute.team.cortana.mymoney.di.module.ActivityModule;
import com.vn.hcmute.team.cortana.mymoney.di.module.WalletModule;
import com.vn.hcmute.team.cortana.mymoney.di.scope.PerActivity;
import com.vn.hcmute.team.cortana.mymoney.ui.wallet.WalletActivity;
import dagger.Component;

/**
 * Created by kunsubin on 8/22/2017.
 */
@PerActivity
@Component(modules = {ActivityModule.class,
          WalletModule.class}, dependencies = ApplicationComponent.class)
public interface WalletComponent {
    
    void inject(WalletActivity walletActivity);
}