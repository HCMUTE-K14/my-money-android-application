package com.vn.hcmute.team.cortana.mymoney.ui.tools.galleryloader.ui.adapter;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.v7.widget.RecyclerView;
import com.vn.hcmute.team.cortana.mymoney.ui.tools.galleryloader.helper.imageloader.ImageLoader;

/**
 * Created by infamouSs on 8/23/17.
 */

public abstract class BaseGalleryAdapter<T extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<T>{
    
    private Context mContext;
    private ImageLoader mImageLoader;
    
    public BaseGalleryAdapter(Context context,ImageLoader imageLoader){
        this.mContext=context;
        this.mImageLoader=imageLoader;
    }
    @LayoutRes
    public abstract int getLayoutId();
    
    public Context getContext() {
        return mContext;
    }
    
    public void setContext(Context context) {
        mContext = context;
    }
    
    public ImageLoader getImageLoader() {
        return mImageLoader;
    }
    
    public void setImageLoader(
              ImageLoader imageLoader) {
        mImageLoader = imageLoader;
    }
}
