package com.vn.hcmute.team.cortana.mymoney.ui.tools.galleryloader.ui;

import static com.vn.hcmute.team.cortana.mymoney.ui.tools.galleryloader.GalleryLoader.MODE_SINGLE;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ItemDecoration;
import android.widget.Toast;
import com.vn.hcmute.team.cortana.mymoney.R;
import com.vn.hcmute.team.cortana.mymoney.ui.tools.galleryloader.GalleryLoader;
import com.vn.hcmute.team.cortana.mymoney.ui.tools.galleryloader.config.GalleryLoaderConfig;
import com.vn.hcmute.team.cortana.mymoney.ui.tools.galleryloader.listener.OnBackAction;
import com.vn.hcmute.team.cortana.mymoney.ui.tools.galleryloader.listener.OnFolderClickListener;
import com.vn.hcmute.team.cortana.mymoney.ui.tools.galleryloader.listener.OnImageClickListener;
import com.vn.hcmute.team.cortana.mymoney.ui.tools.galleryloader.listener.OnImageSelectedListener;
import com.vn.hcmute.team.cortana.mymoney.ui.tools.galleryloader.model.Folder;
import com.vn.hcmute.team.cortana.mymoney.ui.tools.galleryloader.model.ImageGallery;
import com.vn.hcmute.team.cortana.mymoney.ui.tools.galleryloader.ui.adapter.EmptyGalleryAdapter;
import com.vn.hcmute.team.cortana.mymoney.ui.tools.galleryloader.ui.adapter.FolderPickerAdapter;
import com.vn.hcmute.team.cortana.mymoney.ui.tools.galleryloader.ui.adapter.ImagePickerAdapter;
import com.vn.hcmute.team.cortana.mymoney.ui.view.SpacesItemDecoration;
import java.util.List;

/**
 * Created by infamouSs on 8/23/17.
 */

public class RecyclerViewManager {
    
    private Context mContext;
    private RecyclerView mRecyclerView;
    private GalleryLoaderConfig mConfig;
    
    private GridLayoutManager mGridLayoutManager;
    
    private ImagePickerAdapter mImagePickerAdapter;
    private FolderPickerAdapter mFolderPickerAdapter;
    private EmptyGalleryAdapter mEmptyGalleryAdapter;
    
    /*------------------------*/
    /*Initialize              */
    /*------------------------*/
    public RecyclerViewManager(Context context, RecyclerView recyclerView,
              GalleryLoaderConfig config) {
        this.mContext = context;
        this.mRecyclerView = recyclerView;
        this.mConfig = config;
        setupLayoutManager();
    }
    
    /*------------------------*/
    /*Setup Method            */
    /*------------------------*/
    public void setupLayoutManager() {
        mGridLayoutManager = new GridLayoutManager(mContext, 3);
        ItemDecoration itemDecoration = new SpacesItemDecoration(2);
        mRecyclerView.setLayoutManager(mGridLayoutManager);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.addItemDecoration(itemDecoration);
    }
    
    public void setupAdapter(OnFolderClickListener onFolderClickListener,
              OnImageClickListener onImageClickListener) {
        List<ImageGallery> images = null;
        if (mConfig.getMode() == GalleryLoader.MODE_MULTIPLE &&
            !mConfig.getSelectedIamges().isEmpty()) {
            
            images = mConfig.getSelectedIamges();
        }
        mImagePickerAdapter = new ImagePickerAdapter(mContext,
                  mConfig.getImageLoader(), mConfig.getSelectedIamges(), onImageClickListener);
        
        mFolderPickerAdapter = new FolderPickerAdapter(mContext, mConfig.getImageLoader(),
                  onFolderClickListener);
        
        mEmptyGalleryAdapter = new EmptyGalleryAdapter(mContext);
    }
    
    /*------------------------*/
    /*Setup data adapter      */
    /*------------------------*/
    public String getTitle() {
        if (isDisplayingFolderView()) {
            
            return mConfig.getFolderTitle();
        }
        
        if (mConfig.getMode() == GalleryLoader.MODE_MULTIPLE) {
            int imageSize = mImagePickerAdapter.getSelectedImages().size();
            return mConfig.getLimit() == GalleryLoader.LIMIT_ITEM_SELECTED
                      ? String
                      .format(mContext.getString(R.string.txt_selected_with_max_limit), imageSize)
                      : String.format(mContext.getString(R.string.txt_selected_with_limit),
                                imageSize, mConfig.getLimit());
        }
        
        return mConfig.getImageTitle();
    }
    
    private boolean isDisplayingFolderView() {
        return mRecyclerView.getAdapter() == null ||
               mRecyclerView.getAdapter() instanceof FolderPickerAdapter;
    }
    
    public void setImageAdapter(List<ImageGallery> images) {
        mImagePickerAdapter.setData(images);
        mRecyclerView.setAdapter(mImagePickerAdapter);
    }
    
    public void setFolderAdapter(List<Folder> folders) {
        mFolderPickerAdapter.setData(folders);
        mRecyclerView.setAdapter(mFolderPickerAdapter);
    }
    
    public void setEmptyAdapter() {
        mGridLayoutManager.setSpanCount(1);
        mRecyclerView.setAdapter(mEmptyGalleryAdapter);
    }
    
    /*------------------------*/
    /*Helper Method           */
    /*------------------------*/
    
    public void setImageSelectedListener(OnImageSelectedListener listener) {
        if (mImagePickerAdapter == null) {
            throw new IllegalStateException("Must call setupAdapter first!");
        }
        mImagePickerAdapter.setImageSelectedListener(listener);
    }
    
    
    public boolean selectImage() {
        if (mConfig.getMode() == GalleryLoader.MODE_MULTIPLE) {
            if (mImagePickerAdapter.getSelectedImages().size() >= mConfig.getLimit()) {
                Toast.makeText(mContext, "DSADAS", Toast.LENGTH_SHORT).show();
                return false;
            }
        } else if (mConfig.getMode() == MODE_SINGLE) {
            if (mImagePickerAdapter.getSelectedImages().size() > 0) {
                mImagePickerAdapter.removeAllSelectedSingleClick();
            }
        }
        return true;
    }
    
    public List<ImageGallery> getSelectedImages() {
        if (mImagePickerAdapter == null) {
            throw new IllegalStateException("Must call setupAdapter first!");
        }
        return mImagePickerAdapter.getSelectedImages();
    }
    
    public void handlerBackAction(OnBackAction backAction) {
        if (mConfig.isFolderMode() && !isDisplayingFolderView()) {
            setFolderAdapter(null);
            backAction.onBackToFolder();
            return;
        }
        backAction.onFinishImagePicker();
    }
    
    public boolean isShowDoneButton() {
        return !isDisplayingFolderView()
               && !mImagePickerAdapter.getSelectedImages().isEmpty();
    }
    
    
}
