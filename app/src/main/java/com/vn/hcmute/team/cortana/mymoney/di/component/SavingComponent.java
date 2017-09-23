package com.vn.hcmute.team.cortana.mymoney.di.component;

import com.vn.hcmute.team.cortana.mymoney.di.module.ActivityModule;
import com.vn.hcmute.team.cortana.mymoney.di.module.SavingModule;
import com.vn.hcmute.team.cortana.mymoney.di.module.WalletModule;
import com.vn.hcmute.team.cortana.mymoney.di.scope.PerActivity;
import com.vn.hcmute.team.cortana.mymoney.ui.saving.AddSavingActivity;
import com.vn.hcmute.team.cortana.mymoney.ui.saving.EditSavingActivity;
import com.vn.hcmute.team.cortana.mymoney.ui.saving.FragmentFinished;
import com.vn.hcmute.team.cortana.mymoney.ui.saving.FragmentRunning;
import com.vn.hcmute.team.cortana.mymoney.ui.saving.InfoSavingActivity;
import com.vn.hcmute.team.cortana.mymoney.ui.saving.SavingActivity;
import com.vn.hcmute.team.cortana.mymoney.ui.saving.TransferMoneySavingActivity;
import dagger.Component;

/**
 * Created by kunsubin on 8/23/2017.
 */
@PerActivity
@Component(modules = {ActivityModule.class,
          SavingModule.class, WalletModule.class}, dependencies = ApplicationComponent.class)
public interface SavingComponent {
    
    void inject(SavingActivity savingActivity);
    
    void inject(FragmentRunning fragmentRunning);
    
    void inject(FragmentFinished fragmentFinished);
    
    void inject(InfoSavingActivity infoSavingActivity);
    
    void inject(EditSavingActivity editSavingActivity);
    
    void inject(AddSavingActivity addSavingActivity);
    
    void inject(TransferMoneySavingActivity transferMoneySavingActivity);
}
