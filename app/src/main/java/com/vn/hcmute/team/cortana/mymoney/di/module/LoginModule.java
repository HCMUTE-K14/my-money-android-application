package com.vn.hcmute.team.cortana.mymoney.di.module;

import android.content.Context;
import com.vn.hcmute.team.cortana.mymoney.data.DataRepository;
import com.vn.hcmute.team.cortana.mymoney.ui.login.LoginPresenter;
import com.vn.hcmute.team.cortana.mymoney.usecase.remote.UserManager;
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
    UserManager provideUserManager(Context context, DataRepository dataRepository) {
        return new UserManager(context, dataRepository);
    }
    
    @Provides
    LoginPresenter provideLoginPresenter(UserManager userManager) {
        return new LoginPresenter(userManager);
    }
}
