package com.vn.hcmute.team.cortana.mymoney.di.module;

import android.content.Context;
import com.vn.hcmute.team.cortana.mymoney.data.DataRepository;
import com.vn.hcmute.team.cortana.mymoney.ui.iconshop.SelectIconPresenter;
import com.vn.hcmute.team.cortana.mymoney.usecase.remote.ImageUseCase;
import dagger.Module;
import dagger.Provides;

/**
 * Created by infamouSs on 9/13/17.
 */
@Module
public class ImageModule {
    
    public ImageModule() {
        
    }
    
    @Provides
    ImageUseCase provideImageUseCase(Context context, DataRepository dataRepository) {
        return new ImageUseCase(context, dataRepository);
    }
    
    @Provides
    SelectIconPresenter provideSelectIconPresenter(ImageUseCase imageUseCase) {
        return new SelectIconPresenter(imageUseCase);
    }
}
