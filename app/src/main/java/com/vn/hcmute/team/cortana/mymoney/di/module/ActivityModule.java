package com.vn.hcmute.team.cortana.mymoney.di.module;

import android.app.Activity;
import android.content.Context;
import dagger.Module;
import dagger.Provides;

/**
 * Created by infamouSs on 8/11/17.
 */
@Module
public class ActivityModule {
    
    private Activity mActivity;
    
    public ActivityModule(Activity activity) {
        this.mActivity = activity;
    }
    
    @Provides
    public Context provideActivityContext() {
        return mActivity;
    }
}
