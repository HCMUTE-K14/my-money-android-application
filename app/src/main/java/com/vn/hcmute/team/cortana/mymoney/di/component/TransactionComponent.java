package com.vn.hcmute.team.cortana.mymoney.di.component;

import com.vn.hcmute.team.cortana.mymoney.di.module.ActivityModule;
import com.vn.hcmute.team.cortana.mymoney.di.module.TransactionModule;
import com.vn.hcmute.team.cortana.mymoney.di.scope.PerActivity;
import com.vn.hcmute.team.cortana.mymoney.ui.saving.TransactionSavingActivity;
import com.vn.hcmute.team.cortana.mymoney.ui.transaction.BaseInfoTransactionActivity;
import com.vn.hcmute.team.cortana.mymoney.ui.transaction.InfoTransactionForDebtLoanActivity;
import com.vn.hcmute.team.cortana.mymoney.ui.transaction.ManagerTransactionFragment;
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
    
    void inject(InfoTransactionForDebtLoanActivity infoTransactionForDebtLoanActivity);
    
    void inject(BaseInfoTransactionActivity baseInfoTransactionActivity);
}
