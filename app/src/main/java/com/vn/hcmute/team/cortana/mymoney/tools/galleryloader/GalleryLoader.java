package com.vn.hcmute.team.cortana.mymoney.tools.galleryloader;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import com.vn.hcmute.team.cortana.mymoney.tools.galleryloader.config.GalleryLoaderConfig;
import com.vn.hcmute.team.cortana.mymoney.tools.galleryloader.config.GalleryLoaderConfigFactory;
import com.vn.hcmute.team.cortana.mymoney.tools.galleryloader.exception.ConfigException;
import com.vn.hcmute.team.cortana.mymoney.tools.galleryloader.helper.imageloader.ImageLoader;
import com.vn.hcmute.team.cortana.mymoney.tools.galleryloader.model.ImageGallery;
import java.util.List;

/**
 * Created by infamouSs on 8/23/17.
 */

public abstract class GalleryLoader {
    
    public static final int LIMIT_ITEM_SELECTED = 99;
    public static final int MODE_SINGLE = 0;
    public static final int MODE_MULTIPLE = 1;
    private static final String EXTRA_SELECTED_IMAGES = "extra_selected_images";
    
    
    private GalleryLoaderConfig mGalleryLoaderConfig;
    
    public abstract void start(int requestCode);
    
    
    public void buildDefaultConfig(Context context) {
        mGalleryLoaderConfig = GalleryLoaderConfigFactory.getDefaultConfig(context);
    }
    
    public static class GalleryLoaderWithActivity extends GalleryLoader {
        
        Activity mActivity;
        
        public GalleryLoaderWithActivity(Activity activity) {
            this.mActivity = activity;
            buildDefaultConfig(activity);
        }
        
        @Override
        public void start(int requestCode) {
            Intent intent = getIntent(mActivity);
            mActivity.startActivityForResult(intent, requestCode);
        }
        
    }
    
    public static class GalleryLoaderWithFragment extends GalleryLoader {
        
        Fragment mFragment;
        
        public GalleryLoaderWithFragment(Fragment fragment) {
            this.mFragment = fragment;
            buildDefaultConfig(fragment.getActivity());
        }
        
        @Override
        public void start(int requestCode) {
            Intent intent = getIntent(mFragment.getActivity());
            mFragment.startActivityForResult(intent, requestCode);
        }
        
        
    }
    
    public static GalleryLoaderWithActivity create(Activity activity) {
        return new GalleryLoaderWithActivity(activity);
    }
    
    public static GalleryLoaderWithFragment create(Fragment fragment) {
        return new GalleryLoaderWithFragment(fragment);
    }
    
    public GalleryLoader setFolderTitle(String title) {
        mGalleryLoaderConfig.setFolderTitle(title);
        return this;
    }
    
    public GalleryLoader setImageTitle(String title) {
        mGalleryLoaderConfig.setImageTitle(title);
        return this;
    }
    
    public GalleryLoader setMode(int mode) {
        mGalleryLoaderConfig.setMode(mode);
        return this;
    }
    
    public GalleryLoader setLimit(int limit) {
        mGalleryLoaderConfig.setLimit(limit);
        return this;
    }
    
    public GalleryLoader setTheme(int theme) {
        mGalleryLoaderConfig.setTheme(theme);
        return this;
    }
    
    public GalleryLoader setFolderMode(boolean folderMode) {
        mGalleryLoaderConfig.setFolderMode(folderMode);
        return this;
    }
    
    public GalleryLoader setImageLoader(ImageLoader imageLoader) {
        mGalleryLoaderConfig.setImageLoader(imageLoader);
        return this;
    }
    
    
    public GalleryLoaderConfig getConfig() {
        return mGalleryLoaderConfig;
    }
    
    public void setConfig(GalleryLoaderConfig config) {
        this.mGalleryLoaderConfig = config;
    }
    
    public Intent getIntent(Context context) {
        GalleryLoaderConfig config = getConfig();
        
        if (config == null) {
            throw new ConfigException("Config is null");
        }
        Intent intent = new Intent(context, null); //Activity.class;
        intent.putExtra(GalleryLoaderConfig.class.getSimpleName(), config);
        
        return intent;
        
    }
    
    public static List<ImageGallery> getImages(Intent intent) {
        if (intent == null) {
            return null;
        }
        return intent.getParcelableArrayListExtra(GalleryLoader.EXTRA_SELECTED_IMAGES);
    }
    
}
