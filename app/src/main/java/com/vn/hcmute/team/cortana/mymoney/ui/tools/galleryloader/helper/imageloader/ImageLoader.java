package com.vn.hcmute.team.cortana.mymoney.ui.tools.galleryloader.helper.imageloader;

import android.widget.ImageView;
import java.io.Serializable;

/**
 * Created by infamouSs on 8/22/17.
 */

public interface ImageLoader extends Serializable {
    
    void loadImage(Object object, ImageView imageView, ImageType imageType);
}
