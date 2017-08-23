package com.vn.hcmute.team.cortana.mymoney.ui.tools.galleryloader.listener;

import com.vn.hcmute.team.cortana.mymoney.ui.tools.galleryloader.model.ReturnGalleryValue;
import io.reactivex.Observable;

/**
 * Created by infamouSs on 8/22/17.
 */

public interface ImageLoaderListener {
    
    void onLoaded(Observable<ReturnGalleryValue> result);
    
    void onFailure(Throwable throwable);
}
