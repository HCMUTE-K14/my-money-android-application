package com.vn.hcmute.team.cortana.mymoney.di.module;

import android.content.Context;
import com.vn.hcmute.team.cortana.mymoney.data.DataRepository;
import com.vn.hcmute.team.cortana.mymoney.ui.forgetpassword.ForgetPasswordPresenter;
import com.vn.hcmute.team.cortana.mymoney.usecase.remote.UserManager;
import dagger.Module;
import dagger.Provides;

/**
 * Created by infamouSs on 8/25/17.
 */

@Module
public class ForgetPasswordModule {
    
    public ForgetPasswordModule() {
        
    }
    
    @Provides
    UserManager provideUserManager(Context context, DataRepository dataRepository) {
        return new UserManager(context, dataRepository);
    }
    
    @Provides
    ForgetPasswordPresenter provideForgetPassword(UserManager userManager) {
        return new ForgetPasswordPresenter(userManager);
    }
}
