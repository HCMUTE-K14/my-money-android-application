package com.vn.hcmute.team.cortana.mymoney.ui.transaction;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.CheckedTextView;
import android.widget.ImageView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.vn.hcmute.team.cortana.mymoney.R;
import com.vn.hcmute.team.cortana.mymoney.di.module.GlideApp;
import com.vn.hcmute.team.cortana.mymoney.ui.tools.galleryloader.model.ImageGallery;
import com.vn.hcmute.team.cortana.mymoney.ui.transaction.SelectedImageAdapter.SelectedImageViewHolder;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by infamouSs on 10/1/17.
 */

public class SelectedImageAdapter extends RecyclerView.Adapter<SelectedImageViewHolder> {
    
    private Context mContext;
    private List<ImageGallery> mImageGalleries;
    private RemoveImageSelectedListener mListener;
    
    public SelectedImageAdapter(Context context, List<ImageGallery> imageGalleries,
              RemoveImageSelectedListener listener) {
        if (imageGalleries != null) {
            mImageGalleries = imageGalleries;
        }
        mListener = listener;
        mContext = context;
    }
    
    @Override
    public SelectedImageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recycler_view_selected_image, parent, false);
        return new SelectedImageViewHolder(view);
    }
    
    @Override
    public void onBindViewHolder(SelectedImageViewHolder holder, final int position) {
        final ImageGallery imageGallery = mImageGalleries.get(position);
        
        GlideApp.with(mContext)
                  .load(imageGallery.getPath())
                  .placeholder(R.drawable.folder_placeholder)
                  .error(R.drawable.folder_placeholder)
                  .into(holder.mImageViewFile);
        
        holder.mCheckedTextView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onClick(imageGallery, position);
            }
        });
    }
    
    public interface RemoveImageSelectedListener {
        
        void onClick(ImageGallery imageGallery, int position);
    }
    
    
    public void setData(List<ImageGallery> list) {
        if (mImageGalleries == null) {
            mImageGalleries = new ArrayList<>();
        }
        
        mImageGalleries.clear();
        mImageGalleries.addAll(list);
    }
    
    public void remove(ImageGallery imageGallery, int position) {
        mImageGalleries.remove(imageGallery);
        notifyDataSetChanged();
    }
    
    @Override
    public int getItemCount() {
        return mImageGalleries.size();
    }
    
    static class SelectedImageViewHolder extends RecyclerView.ViewHolder {
        
        @BindView(R.id.check_text_view)
        CheckedTextView mCheckedTextView;
        
        @BindView(R.id.image_view_file)
        ImageView mImageViewFile;
        
        SelectedImageViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            mCheckedTextView.bringToFront();
        }
    }
}
