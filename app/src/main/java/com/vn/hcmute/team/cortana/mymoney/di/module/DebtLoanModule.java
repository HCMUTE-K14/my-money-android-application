package com.vn.hcmute.team.cortana.mymoney.di.module;

import android.content.Context;
import com.vn.hcmute.team.cortana.mymoney.data.DataRepository;
import com.vn.hcmute.team.cortana.mymoney.ui.debts.DebtLoanPresenter;
import com.vn.hcmute.team.cortana.mymoney.usecase.remote.DebtLoanUseCase;
import dagger.Module;
import dagger.Provides;

/**
 * Created by infamouSs on 10/22/17.
 */

@Module
public class DebtLoanModule {
    
    public DebtLoanModule() {
        
    }
    
    @Provides
    DebtLoanUseCase provideDebtLoanUseCase(Context context, DataRepository dataRepository) {
        return new DebtLoanUseCase(context, dataRepository);
    }
    
    @Provides
    DebtLoanPresenter provideDebtLoanPresenter(DebtLoanUseCase debtLoanUseCase) {
        return new DebtLoanPresenter(debtLoanUseCase);
    }
}
