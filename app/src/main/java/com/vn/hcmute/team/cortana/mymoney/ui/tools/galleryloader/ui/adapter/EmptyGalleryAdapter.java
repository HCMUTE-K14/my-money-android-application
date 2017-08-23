package com.vn.hcmute.team.cortana.mymoney.ui.tools.galleryloader.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.vn.hcmute.team.cortana.mymoney.R;
import com.vn.hcmute.team.cortana.mymoney.ui.tools.galleryloader.ui.adapter.EmptyGalleryAdapter.EmptyGalleryAdapterViewHolder;

/**
 * Created by infamouSs on 8/23/17.
 */

public class EmptyGalleryAdapter extends RecyclerView.Adapter<EmptyGalleryAdapterViewHolder> {
    
    private Context mContext;
    
    public EmptyGalleryAdapter(Context context) {
        this.mContext = context;
    }
    
    @Override
    public EmptyGalleryAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                  .inflate(R.layout.item_gallery_empty, parent, false);
        return new EmptyGalleryAdapterViewHolder(v);
    }
    
    @Override
    public void onBindViewHolder(EmptyGalleryAdapterViewHolder holder, int position) {
        
    }
    
    @Override
    public int getItemCount() {
        return 1;
    }
    
    public class EmptyGalleryAdapterViewHolder extends RecyclerView.ViewHolder {
        
        public EmptyGalleryAdapterViewHolder(View root) {
            super(root);
        }
    }
}
