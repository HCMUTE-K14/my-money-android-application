package com.vn.hcmute.team.cortana.mymoney.di.component;

import com.vn.hcmute.team.cortana.mymoney.di.module.ActivityModule;
import com.vn.hcmute.team.cortana.mymoney.di.module.BudgetModule;
import com.vn.hcmute.team.cortana.mymoney.di.module.CategoryModule;
import com.vn.hcmute.team.cortana.mymoney.di.module.WalletModule;
import com.vn.hcmute.team.cortana.mymoney.di.scope.PerActivity;
import com.vn.hcmute.team.cortana.mymoney.ui.budget.AddBudgetActivity;
import com.vn.hcmute.team.cortana.mymoney.ui.budget.BudgetActivity;
import com.vn.hcmute.team.cortana.mymoney.ui.budget.EditBudgetActivity;
import com.vn.hcmute.team.cortana.mymoney.ui.budget.FragmentBudgetFinished;
import com.vn.hcmute.team.cortana.mymoney.ui.budget.FragmentBudgetRunning;
import com.vn.hcmute.team.cortana.mymoney.ui.budget.InfoBudgetActivity;
import dagger.Component;

/**
 * Created by kunsubin on 8/23/2017.
 */
@PerActivity
@Component(modules = {ActivityModule.class,
          BudgetModule.class, WalletModule.class,
          CategoryModule.class}, dependencies = ApplicationComponent.class)
public interface BudgetComponent {
    
    void inject(BudgetActivity budgetActivity);
    
    void inject(EditBudgetActivity editBudgetActivity);
    
    void inject(AddBudgetActivity addBudgetActivity);
    
    void inject(InfoBudgetActivity infoBudgetActivity);
    
    void inject(FragmentBudgetRunning fragmentBudgetRunning);
    
    void inject(FragmentBudgetFinished fragmentBudgetFinished);
}
