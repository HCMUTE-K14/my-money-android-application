package com.vn.hcmute.team.cortana.mymoney.di.component;

import com.vn.hcmute.team.cortana.mymoney.di.module.ActivityModule;
import com.vn.hcmute.team.cortana.mymoney.di.module.DebtLoanModule;
import com.vn.hcmute.team.cortana.mymoney.di.scope.PerActivity;
import com.vn.hcmute.team.cortana.mymoney.ui.debts.DebtsLoanFragmentByType;
import dagger.Component;

/**
 * Created by infamouSs on 10/22/17.
 */

@PerActivity
@Component(modules = {ActivityModule.class, DebtLoanModule.class}, dependencies = {
          ApplicationComponent.class})
public interface DebtLoanComponent {
    
    void inject(DebtsLoanFragmentByType fragment);
}
