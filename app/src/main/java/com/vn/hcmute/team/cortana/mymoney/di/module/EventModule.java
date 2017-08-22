package com.vn.hcmute.team.cortana.mymoney.di.module;

import android.content.Context;
import com.vn.hcmute.team.cortana.mymoney.data.DataRepository;
import com.vn.hcmute.team.cortana.mymoney.ui.event.EventPresenter;
import com.vn.hcmute.team.cortana.mymoney.usecase.remote.EventUseCase;
import dagger.Module;
import dagger.Provides;

/**
 * Created by kunsubin on 8/22/2017.
 */
@Module
public class EventModule {
    
    public EventModule() {
    }
    
    @Provides
    EventUseCase provideEventUseCase(Context context, DataRepository dataRepository) {
        return new EventUseCase(context, dataRepository);
    }
    
    @Provides
    EventPresenter provideEventPresenter(EventUseCase eventUseCase) {
        return new EventPresenter(eventUseCase);
    }
}
