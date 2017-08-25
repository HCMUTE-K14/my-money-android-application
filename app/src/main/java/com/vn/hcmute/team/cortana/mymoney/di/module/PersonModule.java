package com.vn.hcmute.team.cortana.mymoney.di.module;

import android.content.Context;
import com.vn.hcmute.team.cortana.mymoney.data.DataRepository;
import com.vn.hcmute.team.cortana.mymoney.ui.person.PersonPresenter;
import com.vn.hcmute.team.cortana.mymoney.usecase.remote.PersonUseCase;
import dagger.Module;
import dagger.Provides;

/**
 * Created by kunsubin on 8/23/2017.
 */
@Module
public class PersonModule {
    
    @Provides
    PersonUseCase providePersonUseCase(Context context, DataRepository dataRepository) {
        return new PersonUseCase(context, dataRepository);
    }
    
    @Provides
    PersonPresenter providePersonPersenter(PersonUseCase personUseCase) {
        return new PersonPresenter(personUseCase);
    }
}
