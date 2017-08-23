package com.vn.hcmute.team.cortana.mymoney.ui.tools.galleryloader.ui.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.vn.hcmute.team.cortana.mymoney.R;
import com.vn.hcmute.team.cortana.mymoney.ui.tools.galleryloader.helper.imageloader.ImageLoader;
import com.vn.hcmute.team.cortana.mymoney.ui.tools.galleryloader.helper.imageloader.ImageType;
import com.vn.hcmute.team.cortana.mymoney.ui.tools.galleryloader.listener.OnImageClickListener;
import com.vn.hcmute.team.cortana.mymoney.ui.tools.galleryloader.listener.OnImageSelectedClickListener;
import com.vn.hcmute.team.cortana.mymoney.ui.tools.galleryloader.model.ImageGallery;
import com.vn.hcmute.team.cortana.mymoney.ui.tools.galleryloader.ui.adapter.ImagePickerAdapter.ImagePickerViewHolder;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by infamouSs on 8/23/17.
 */

public class ImagePickerAdapter extends BaseGalleryAdapter<ImagePickerViewHolder> {
    
    private List<ImageGallery> mImages = new ArrayList<>();
    private List<ImageGallery> mSelectedImages = new ArrayList<>();
    
    private OnImageClickListener mImageClickListener;
    private OnImageSelectedClickListener mImageSelectedClickListener;
    
    public ImagePickerAdapter(Context context, ImageLoader imageLoader,
              List<ImageGallery> selectedImages, OnImageClickListener onImageClickListener) {
        super(context, imageLoader);
        
        mImageClickListener = onImageClickListener;
        
        if (selectedImages != null && !selectedImages.isEmpty()) {
            mSelectedImages.addAll(selectedImages);
        }
    }
    
    @Override
    public ImagePickerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(getLayoutId(), parent, false);
        
        return new ImagePickerViewHolder(v);
    }
    
    @Override
    public void onBindViewHolder(final ImagePickerViewHolder holder, int position) {
        final ImageGallery image = mImages.get(position);
        
        final boolean isSelected = isSelected(image);
        
        getImageLoader().loadImage(image.getPath(), holder.mImageView, ImageType.IMAGE_GALLERY);
        
        holder.mAlphaView.setAlpha(isSelected ? 0.5f : 0.0f);
        
        holder.itemView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean shouldSelect = mImageClickListener
                          .onImageClick(holder.getAdapterPosition(), !isSelected);
                if(isSelected){
                    
                }else if(shouldSelect){
                    
                }
            }
        });
    
        holder.mContainer.setForeground(isSelected
                  ? ContextCompat.getDrawable(getContext(), R.drawable.ic_done_white)
                  : null);
    }
    
    @Override
    public int getItemCount() {
        return 0;
    }
    
    @Override
    public int getLayoutId() {
        return R.layout.item_gallery_folder;
    }
    
    
    private boolean isSelected(ImageGallery image) {
        for (ImageGallery selectedImage : mSelectedImages) {
            if (selectedImage.getPath().equals(image.getPath())) {
                return true;
            }
        }
        return false;
    }
    
    
    public class ImagePickerViewHolder extends RecyclerView.ViewHolder {
        
        @BindView(R.id.container)
        FrameLayout mContainer;
        
        @BindView(R.id.image_item)
        ImageView mImageView;
        
        @BindView(R.id.alpha_view)
        View mAlphaView;
        
        public ImagePickerViewHolder(View root) {
            super(root);
            ButterKnife.bind(this, root);
        }
    }
}
