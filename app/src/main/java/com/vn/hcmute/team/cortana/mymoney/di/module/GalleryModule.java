package com.vn.hcmute.team.cortana.mymoney.di.module;

import android.content.Context;
import com.vn.hcmute.team.cortana.mymoney.data.DataRepository;
import com.vn.hcmute.team.cortana.mymoney.ui.gallery.GalleryPresenter;
import com.vn.hcmute.team.cortana.mymoney.usecase.remote.ImageUseCase;
import dagger.Module;
import dagger.Provides;

/**
 * Created by infamouSs on 8/22/17.
 */
@Module
public class GalleryModule {
    public GalleryModule(){
        
    }
    
    @Provides
    ImageUseCase provideImageUseCase(Context context,DataRepository dataRepository){
        return new ImageUseCase(context,dataRepository);
    }
    
    @Provides
    GalleryPresenter provideGalleryPresenter(ImageUseCase imageUseCase){
        return new GalleryPresenter(imageUseCase);
    }
}
