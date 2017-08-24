package com.vn.hcmute.team.cortana.mymoney.di.module;

import android.content.Context;
import com.vn.hcmute.team.cortana.mymoney.data.DataRepository;
import com.vn.hcmute.team.cortana.mymoney.ui.currencies.CurrenciesPresenter;
import com.vn.hcmute.team.cortana.mymoney.usecase.remote.CurrenciesUseCase;
import dagger.Module;
import dagger.Provides;

/**
 * Created by kunsubin on 8/22/2017.
 */
@Module
public class CurrenciesModule {
    
    public CurrenciesModule() {
    }
    
    @Provides
    public CurrenciesUseCase providesCurrenciesUseCase(Context context,
              DataRepository dataRepository) {
        return new CurrenciesUseCase(context, dataRepository);
    }
    
    @Provides
    public CurrenciesPresenter providesCurrenciesPresenter(CurrenciesUseCase currenciesUseCase) {
        return new CurrenciesPresenter(currenciesUseCase);
    }
}
