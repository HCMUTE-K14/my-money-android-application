package com.vn.hcmute.team.cortana.mymoney.di.module;

import android.content.Context;
import com.vn.hcmute.team.cortana.mymoney.data.DataRepository;
import com.vn.hcmute.team.cortana.mymoney.ui.saving.SavingPresenter;
import com.vn.hcmute.team.cortana.mymoney.usecase.remote.SavingUseCase;
import dagger.Module;
import dagger.Provides;

/**
 * Created by kunsubin on 8/23/2017.
 */
@Module
public class SavingModule {
    
    @Provides
    SavingUseCase provideWalletUseCase(Context context, DataRepository dataRepository) {
        return new SavingUseCase(context, dataRepository);
    }
    
    @Provides
    SavingPresenter provideSavingPresenter(SavingUseCase savingUseCase) {
        return new SavingPresenter(savingUseCase);
    }
}
