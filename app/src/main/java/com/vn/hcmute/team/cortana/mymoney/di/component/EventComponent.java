package com.vn.hcmute.team.cortana.mymoney.di.component;

import com.vn.hcmute.team.cortana.mymoney.di.module.ActivityModule;
import com.vn.hcmute.team.cortana.mymoney.di.module.EventModule;
import com.vn.hcmute.team.cortana.mymoney.di.module.WalletModule;
import com.vn.hcmute.team.cortana.mymoney.di.scope.PerActivity;
import com.vn.hcmute.team.cortana.mymoney.ui.event.ActivityEditEvent;
import com.vn.hcmute.team.cortana.mymoney.ui.event.ActivityInfoEvent;
import com.vn.hcmute.team.cortana.mymoney.ui.event.EventActivity;
import com.vn.hcmute.team.cortana.mymoney.ui.event.FragmentEventFinished;
import com.vn.hcmute.team.cortana.mymoney.ui.event.FragmentEventRunning;
import dagger.Component;

/**
 * Created by kunsubin on 8/22/2017.
 */
@PerActivity
@Component(modules = {ActivityModule.class,
          EventModule.class, WalletModule.class}, dependencies = ApplicationComponent.class)
public interface EventComponent {
    void inject(EventActivity eventActivity);
    void inject(FragmentEventRunning fragmentEventRunning);
    void inject(FragmentEventFinished fragmentEventFinished);
    void inject(ActivityInfoEvent activityInfoEvent);
    void inject(ActivityEditEvent activityEditEvent);
}
