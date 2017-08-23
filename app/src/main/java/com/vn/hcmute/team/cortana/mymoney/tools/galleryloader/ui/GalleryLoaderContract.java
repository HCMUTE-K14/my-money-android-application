package com.vn.hcmute.team.cortana.mymoney.tools.galleryloader.ui;

import com.vn.hcmute.team.cortana.mymoney.tools.galleryloader.model.ReturnGalleryValue;
import com.vn.hcmute.team.cortana.mymoney.tools.galleryloader.ui.base.GalleryView;

/**
 * Created by infamouSs on 8/23/17.
 */

public interface GalleryLoaderContract {
    public interface View extends GalleryView {
        void loading(boolean isLoading);
        
        
        void showResult(ReturnGalleryValue result);
        
        void showEmpty();
        
        void showError(String message);
    }
    public interface Presenter{
        void loadImages(boolean isFoldermode);
    }
}
