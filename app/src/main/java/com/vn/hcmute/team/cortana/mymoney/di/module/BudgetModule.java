package com.vn.hcmute.team.cortana.mymoney.di.module;

import android.content.Context;
import com.vn.hcmute.team.cortana.mymoney.data.DataRepository;
import com.vn.hcmute.team.cortana.mymoney.ui.budget.BudgetPresenter;
import com.vn.hcmute.team.cortana.mymoney.usecase.remote.BudgetUseCase;
import dagger.Module;
import dagger.Provides;

/**
 * Created by kunsubin on 8/23/2017.
 */
@Module
public class BudgetModule {
    
    @Provides
    BudgetUseCase providePersonUseCase(Context context, DataRepository dataRepository) {
        return new BudgetUseCase(context, dataRepository);
    }
    
    @Provides
    BudgetPresenter provideBudgetPresenter(BudgetUseCase budgetUseCase) {
        return new BudgetPresenter(budgetUseCase);
    }
}
