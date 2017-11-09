package com.vn.hcmute.team.cortana.mymoney.di.component;

import com.vn.hcmute.team.cortana.mymoney.di.module.ActivityModule;
import com.vn.hcmute.team.cortana.mymoney.di.module.TransactionModule;
import com.vn.hcmute.team.cortana.mymoney.di.scope.PerActivity;
import com.vn.hcmute.team.cortana.mymoney.ui.budget.TransactionBudgetActivity;
import com.vn.hcmute.team.cortana.mymoney.ui.event.TransactionEventActivity;
import com.vn.hcmute.team.cortana.mymoney.ui.saving.TransactionSavingActivity;
import com.vn.hcmute.team.cortana.mymoney.ui.statistics.StatisticsActivity;
import com.vn.hcmute.team.cortana.mymoney.ui.statistics.StatisticsFragment;
import com.vn.hcmute.team.cortana.mymoney.ui.transaction.BaseInfoTransactionActivity;
import com.vn.hcmute.team.cortana.mymoney.ui.transaction.InforTransactionForDebtLoanActivity;
import com.vn.hcmute.team.cortana.mymoney.ui.transaction.ManagerTransactionFragment;
import com.vn.hcmute.team.cortana.mymoney.ui.transaction.TransactionMainFragment;
import dagger.Component;

/**
 * Created by infamouSs on 9/28/17.
 */
@PerActivity
@Component(modules = {ActivityModule.class,
          TransactionModule.class}, dependencies = ApplicationComponent.class)
public interface TransactionComponent {
    
    void inject(ManagerTransactionFragment transactionFragment);
    
    void inject(TransactionSavingActivity transactionSavingActivity);
    
    void inject(TransactionEventActivity transactionEventActivity);
    
    void inject(TransactionBudgetActivity transactionBudgetActivity);
    
    void inject(InforTransactionForDebtLoanActivity inforTransactionForDebtLoanActivity);
    
    void inject(BaseInfoTransactionActivity baseInfoTransactionActivity);
    
    void inject(StatisticsActivity statisticsActivity);
    
    void inject(StatisticsFragment statisticsFragment);
    
    void inject(TransactionMainFragment transactionMainFragment);
    
}
