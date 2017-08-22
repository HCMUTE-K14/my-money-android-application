package com.vn.hcmute.team.cortana.mymoney.tools.galleryloader.helper.imageloader;

import android.widget.ImageView;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.vn.hcmute.team.cortana.mymoney.R;
import com.vn.hcmute.team.cortana.mymoney.di.module.GlideApp;

/**
 * Created by infamouSs on 8/22/17.
 */

public class DefaultImageLoader implements ImageLoader {
    
    
    @Override
    public void loadImage(Object object, ImageView imageView, ImageType imageType) {
        GlideApp.with(imageView.getContext())
                  .load(object)
                  .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                  .placeholder(imageType == ImageType.FOLDER ? R.drawable.folder_placeholder:R.drawable.image_placeholder)
                  .error(imageType == ImageType.FOLDER ? R.drawable.folder_placeholder:R.drawable.image_placeholder)
                  .into(imageView);
    }
}
