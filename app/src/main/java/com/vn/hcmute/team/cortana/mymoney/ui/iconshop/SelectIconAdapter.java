package com.vn.hcmute.team.cortana.mymoney.ui.iconshop;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.vn.hcmute.team.cortana.mymoney.R;
import com.vn.hcmute.team.cortana.mymoney.di.module.GlideApp;
import com.vn.hcmute.team.cortana.mymoney.model.Icon;
import com.vn.hcmute.team.cortana.mymoney.ui.iconshop.SelectIconAdapter.SelectIconViewHolder;
import com.vn.hcmute.team.cortana.mymoney.utils.DrawableUtil;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by infamouSs on 9/10/17.
 */

public class SelectIconAdapter extends RecyclerView.Adapter<SelectIconViewHolder> {
    
    private Context mContext;
    private List<Icon> mIcons;
    private SelectIconListener mListener;
    
    public SelectIconAdapter(Context context, List<Icon> data, SelectIconListener listener) {
        mContext = context;
        mListener = listener;
        
        if (data != null) {
            mIcons = new ArrayList<>();
            mIcons.addAll(data);
        }
    }
    
    private int getLayoutId() {
        return R.layout.item_recycler_view_select_icon;
    }
    
    @Override
    public SelectIconViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(getLayoutId(), parent, false);
        
        return new SelectIconViewHolder(view);
    }
    
    @Override
    public void onBindViewHolder(SelectIconViewHolder holder, final int position) {
        final Icon icon = mIcons.get(position);
        
        GlideApp.with(mContext)
                  .load(DrawableUtil.getDrawable(mContext, icon.getImage()))
                  .placeholder(R.drawable.folder_placeholder)
                  .error(R.drawable.folder_placeholder)
                  .dontAnimate()
                  .into(holder.mImageView);
        holder.itemView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onClickIcon(position, icon);
            }
        });
    }
    
    @Override
    public int getItemCount() {
        return mIcons.size();
    }
    
    public void setData(List<Icon> icons) {
        mIcons.clear();
        mIcons.addAll(icons);
        notifyDataSetChanged();
    }
    
    static class SelectIconViewHolder extends RecyclerView.ViewHolder {
        
        @BindView(R.id.imageView)
        ImageView mImageView;
        
        public SelectIconViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
