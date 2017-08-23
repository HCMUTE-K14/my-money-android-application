package com.vn.hcmute.team.cortana.mymoney.di.component;

import com.vn.hcmute.team.cortana.mymoney.di.module.ActivityModule;
import com.vn.hcmute.team.cortana.mymoney.di.module.BudgetModule;
import com.vn.hcmute.team.cortana.mymoney.di.scope.PerActivity;
import com.vn.hcmute.team.cortana.mymoney.ui.budget.BudgetActivity;
import dagger.Component;

/**
 * Created by kunsubin on 8/23/2017.
 */
@PerActivity
@Component(modules = {ActivityModule.class,
          BudgetModule.class}, dependencies = ApplicationComponent.class)
public interface BudgetComponent {
    void inject(BudgetActivity budgetActivity);
}
