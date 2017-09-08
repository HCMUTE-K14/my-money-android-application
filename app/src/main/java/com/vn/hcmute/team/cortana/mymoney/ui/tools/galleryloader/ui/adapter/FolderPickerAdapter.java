package com.vn.hcmute.team.cortana.mymoney.ui.tools.galleryloader.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.vn.hcmute.team.cortana.mymoney.R;
import com.vn.hcmute.team.cortana.mymoney.ui.tools.galleryloader.helper.imageloader.ImageLoader;
import com.vn.hcmute.team.cortana.mymoney.ui.tools.galleryloader.helper.imageloader.ImageType;
import com.vn.hcmute.team.cortana.mymoney.ui.tools.galleryloader.listener.OnFolderClickListener;
import com.vn.hcmute.team.cortana.mymoney.ui.tools.galleryloader.model.Folder;
import com.vn.hcmute.team.cortana.mymoney.ui.tools.galleryloader.ui.adapter.FolderPickerAdapter.FolderPickerViewHolder;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by infamouSs on 8/23/17.
 */

public class FolderPickerAdapter extends BaseGalleryAdapter<FolderPickerViewHolder> {
    
    List<Folder> mFolders = new ArrayList<>();
    private OnFolderClickListener mFolderClickListener;
    
    public FolderPickerAdapter(Context context, ImageLoader imageLoader,
              OnFolderClickListener onFolderClickListener) {
        super(context, imageLoader);
        
        mFolderClickListener = onFolderClickListener;
    }
    
    /*------------------------*/
    /*Initialize              */
    /*------------------------*/
    @Override
    public int getLayoutId() {
        return R.layout.item_gallery_folder;
    }
    
    @Override
    public FolderPickerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(getLayoutId(), parent, false);
        return new FolderPickerViewHolder(v);
    }
    
    @Override
    public void onBindViewHolder(FolderPickerViewHolder holder, int position) {
        final Folder folder = mFolders.get(position);
        
        getImageLoader().loadImage(folder.getImages().get(0).getPath(), holder.mImageFolder,
                  ImageType.FOLDER);
        holder.mTextViewTitle.setText(folder.getName());
        holder.mTextViewSubTitle.setText(String.valueOf(folder.getImages().size()));
        holder.mContainer.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mFolderClickListener != null) {
                    mFolderClickListener.onFolderClick(folder);
                }
            }
        });
    }
    
    @Override
    public int getItemCount() {
        return mFolders.size();
    }
    
    /*-----------------------------*/
    /*Helper Method                */
    /*-----------------------------*/
    
    public void setData(List<Folder> data) {
        if (data != null) {
            this.mFolders.clear();
            this.mFolders.addAll(data);
        }
        
        notifyDataSetChanged();
    }
    
    /*-----------------------------*/
    /*View Holder                  */
    /*-----------------------------*/
    static class FolderPickerViewHolder extends RecyclerView.ViewHolder {
        
        @BindView(R.id.container_folder)
        RelativeLayout mContainer;
        
        @BindView(R.id.image_folder)
        ImageView mImageFolder;
        
        @BindView(R.id.text_item_title)
        TextView mTextViewTitle;
        
        @BindView(R.id.text_item_sub_title)
        TextView mTextViewSubTitle;
        
        public FolderPickerViewHolder(View root) {
            super(root);
            ButterKnife.bind(this, root);
        }
    }
}
