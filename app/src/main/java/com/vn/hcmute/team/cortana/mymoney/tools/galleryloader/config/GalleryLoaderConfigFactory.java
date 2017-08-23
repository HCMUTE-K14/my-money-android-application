package com.vn.hcmute.team.cortana.mymoney.tools.galleryloader.config;

import android.content.Context;
import com.vn.hcmute.team.cortana.mymoney.R;
import com.vn.hcmute.team.cortana.mymoney.tools.galleryloader.GalleryLoader;
import com.vn.hcmute.team.cortana.mymoney.tools.galleryloader.helper.imageloader.DefaultImageLoader;
import com.vn.hcmute.team.cortana.mymoney.tools.galleryloader.model.ImageGallery;
import java.util.ArrayList;

/**
 * Created by infamouSs on 8/23/17.
 */

public class GalleryLoaderConfigFactory {
    
    
    public static GalleryLoaderConfig getDefaultConfig(Context context) {
        GalleryLoaderConfig config = new GalleryLoaderConfig();
        config.setMode(GalleryLoader.MODE_MULTIPLE);
        config.setFolderMode(true);
        config.setFolderTitle(context.getString(R.string.txt_folder));
        config.setImageTitle(context.getString(R.string.txt_tap_to_select_image));
        config.setImageLoader(new DefaultImageLoader());
        config.setLimit(GalleryLoader.LIMIT_ITEM_SELECTED);
        config.setSelectedIamges(new ArrayList<ImageGallery>());
        return config;
    }
    
}
