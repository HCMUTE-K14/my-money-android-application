package com.vn.hcmute.team.cortana.mymoney.di.component;

import com.vn.hcmute.team.cortana.mymoney.di.module.ActivityModule;
import com.vn.hcmute.team.cortana.mymoney.di.module.GalleryModule;
import com.vn.hcmute.team.cortana.mymoney.di.scope.PerActivity;
import com.vn.hcmute.team.cortana.mymoney.ui.gallery.GalleryActivity;
import dagger.Component;

/**
 * Created by infamouSs on 8/22/17.
 */
@PerActivity
@Component(modules = {ActivityModule.class, GalleryModule.class}, dependencies = ApplicationComponent.class)
public interface GalleryComponent {
    void inject(GalleryActivity galleryActivity);
}
