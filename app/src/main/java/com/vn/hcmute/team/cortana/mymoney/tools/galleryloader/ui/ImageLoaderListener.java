package com.vn.hcmute.team.cortana.mymoney.tools.galleryloader.ui;

import com.vn.hcmute.team.cortana.mymoney.tools.galleryloader.model.Folder;
import com.vn.hcmute.team.cortana.mymoney.tools.galleryloader.model.ImageGallery;
import java.util.List;

/**
 * Created by infamouSs on 8/22/17.
 */

public interface ImageLoaderListener {
    void onLoaded(List<Folder>folders,List<ImageGallery> files);
    
    void onFailure(Throwable throwable);
}
