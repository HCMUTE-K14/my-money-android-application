package com.vn.hcmute.team.cortana.mymoney.di.module;

import android.content.Context;
import com.vn.hcmute.team.cortana.mymoney.data.DataRepository;
import com.vn.hcmute.team.cortana.mymoney.ui.login.LoginPresenter;
import com.vn.hcmute.team.cortana.mymoney.usecase.remote.ImageUseCase;
import com.vn.hcmute.team.cortana.mymoney.usecase.remote.LoginUseCase;
import dagger.Module;
import dagger.Provides;

/**
 * Created by infamouSs on 8/11/17.
 */

@Module
public class LoginModule {
    
    public LoginModule() {
        
    }
    
    @Provides
    ImageUseCase provideImageUseCase(Context context, DataRepository dataRepository) {
        return new ImageUseCase(context, dataRepository);
    }
    
    @Provides
    LoginUseCase provideLoginUseCase(Context context, DataRepository dataRepository) {
        return new LoginUseCase(context, dataRepository);
    }
    
    @Provides
    LoginPresenter provideLoginPresenter(LoginUseCase loginUseCase) {
        return new LoginPresenter(loginUseCase);
    }
}
