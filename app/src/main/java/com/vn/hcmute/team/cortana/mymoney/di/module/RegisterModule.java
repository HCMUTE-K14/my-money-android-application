package com.vn.hcmute.team.cortana.mymoney.di.module;

import android.content.Context;
import com.vn.hcmute.team.cortana.mymoney.data.DataRepository;
import com.vn.hcmute.team.cortana.mymoney.ui.register.RegisterPresenter;
import com.vn.hcmute.team.cortana.mymoney.usecase.remote.RegisterUseCase;
import dagger.Module;
import dagger.Provides;

/**
 * Created by infamouSs on 8/11/17.
 */
@Module
public class RegisterModule {
    
    public RegisterModule() {
        
    }
    
    @Provides
    RegisterUseCase provideRegisterUseCase(Context context, DataRepository dataRepository) {
        return new RegisterUseCase(context, dataRepository);
    }
    
    @Provides
    RegisterPresenter provideRegisterPresenter(RegisterUseCase registerUseCase) {
        return new RegisterPresenter(registerUseCase);
    }
}
