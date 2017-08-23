package com.vn.hcmute.team.cortana.mymoney.ui.tools.galleryloader.ui;

import com.vn.hcmute.team.cortana.mymoney.ui.base.view.BaseView;
import com.vn.hcmute.team.cortana.mymoney.ui.tools.galleryloader.config.GalleryLoaderConfig;
import com.vn.hcmute.team.cortana.mymoney.ui.tools.galleryloader.model.ReturnGalleryValue;

/**
 * Created by infamouSs on 8/23/17.
 */

public interface GalleryLoaderContract {
    
    public interface View extends BaseView {
        
        void requirePermission();
        
        void initializeView(GalleryLoaderConfig config);
        
        void loading(boolean isLoading);
        
        void showResult(ReturnGalleryValue result);
        
        void showEmpty();
        
        void showError(String message);
    }
    
    public interface Presenter {
        
        void loadImages(boolean isFoldermode);
    
        void unSubscribe();
    }
}
