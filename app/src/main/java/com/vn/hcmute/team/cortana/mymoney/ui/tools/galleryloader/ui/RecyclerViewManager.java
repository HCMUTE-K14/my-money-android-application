package com.vn.hcmute.team.cortana.mymoney.ui.tools.galleryloader.ui;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import com.vn.hcmute.team.cortana.mymoney.ui.tools.galleryloader.GalleryLoader;
import com.vn.hcmute.team.cortana.mymoney.ui.tools.galleryloader.config.GalleryLoaderConfig;
import com.vn.hcmute.team.cortana.mymoney.ui.tools.galleryloader.listener.OnFolderClickListener;
import com.vn.hcmute.team.cortana.mymoney.ui.tools.galleryloader.listener.OnImageClickListener;
import com.vn.hcmute.team.cortana.mymoney.ui.tools.galleryloader.model.ImageGallery;
import java.util.List;

/**
 * Created by infamouSs on 8/23/17.
 */

public class RecyclerViewManager {
    
    private Context mContext;
    private RecyclerView mRecyclerView;
    private GalleryLoaderConfig mConfig;
    
    public RecyclerViewManager(Context context,RecyclerView recyclerView,GalleryLoaderConfig config){
        this.mContext=context;
        this.mRecyclerView=recyclerView;
        this.mConfig=config;
    }
    
    
    public void setupApdapter(OnFolderClickListener onFolderClickListener,OnImageClickListener onImageClickListener){
        List<ImageGallery> images=null;
        if(mConfig.getMode() == GalleryLoader.MODE_MULTIPLE && !mConfig.getSelectedIamges().isEmpty()){
            images = mConfig.getSelectedIamges();
        }
        
        
        
        
        
    }
    
    
}
