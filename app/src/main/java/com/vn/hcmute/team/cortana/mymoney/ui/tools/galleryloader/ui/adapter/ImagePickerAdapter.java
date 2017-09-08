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
import com.vn.hcmute.team.cortana.mymoney.ui.tools.galleryloader.listener.OnImageSelectedListener;
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
    private OnImageSelectedListener mImageSelectedListener;
    
    public ImagePickerAdapter(Context context, ImageLoader imageLoader,
              List<ImageGallery> selectedImages, OnImageClickListener onImageClickListener) {
        super(context, imageLoader);
        
        mImageClickListener = onImageClickListener;
        
        if (selectedImages != null && !selectedImages.isEmpty()) {
            mSelectedImages.addAll(selectedImages);
        }
    }
    
    /*------------------------------------------*/
    /* Initialize                               */
    /*------------------------------------------*/
    @Override
    public int getLayoutId() {
        return R.layout.item_gallery_image;
    }
    
    @Override
    public ImagePickerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(getLayoutId(), parent, false);
        return new ImagePickerViewHolder(v);
    }
    
    @Override
    public void onBindViewHolder(final ImagePickerViewHolder holder, final int position) {
        final ImageGallery image = mImages.get(position);
        
        final boolean isSelected = isSelected(image);
        
        getImageLoader().loadImage(image.getPath(), holder.mImageView, ImageType.IMAGE_GALLERY);
        
        holder.mAlphaView.setAlpha(isSelected ? 0.5f : 0f);
        
        holder.mContainer.setForeground(isSelected
                  ? ContextCompat.getDrawable(getContext(), R.drawable.ic_done_white)
                  : null);
        
        holder.itemView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean shouldSelect = mImageClickListener
                          .onImageClick(holder.getAdapterPosition(), !isSelected);
                if (isSelected) {
                    removeSelected(image, position);
                } else if (shouldSelect) {
                    addSelected(image, position);
                }
            }
        });
        
        
    }
    
    @Override
    public int getItemCount() {
        return mImages.size();
    }
    
    
    /*------------------------------------------*/
    /* Helper method                            */
    /*------------------------------------------*/
    public List<ImageGallery> getSelectedImages() {
        return mSelectedImages;
    }
    
    private boolean isSelected(ImageGallery image) {
        for (ImageGallery selectedImage : mSelectedImages) {
            if (selectedImage.getPath().equals(image.getPath())) {
                return true;
            }
        }
        return false;
    }
    
    public void setData(List<ImageGallery> data) {
        this.mImages.clear();
        this.mImages.addAll(data);
    }
    
    public void setImageSelectedListener(
              OnImageSelectedListener imageSelectedListener) {
        mImageSelectedListener = imageSelectedListener;
    }
    
    public ImageGallery getItem(int index) {
        return mImages.get(index);
    }
    
    public void removeAllSelectedSingleClick() {
        handlerSelection(new Runnable() {
            @Override
            public void run() {
                mSelectedImages.clear();
                notifyDataSetChanged();
            }
        });
    }
    
    public void addSelected(final ImageGallery image, final int position) {
        handlerSelection(new Runnable() {
            @Override
            public void run() {
                mSelectedImages.add(image);
                notifyItemChanged(position);
            }
        });
    }
    
    public void removeSelected(final ImageGallery image, final int position) {
        handlerSelection(new Runnable() {
            @Override
            public void run() {
                mSelectedImages.remove(image);
                notifyItemChanged(position);
            }
        });
    }
    
    private void handlerSelection(Runnable runnable) {
        runnable.run();
        if (mImageSelectedListener != null) {
            mImageSelectedListener.onUpdateSelection(getSelectedImages());
        }
    }
    
    /*------------------------------------------*/
    /* View holder                              */
    /*------------------------------------------*/
    static class ImagePickerViewHolder extends RecyclerView.ViewHolder {
        
        @BindView(R.id.container_image)
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
