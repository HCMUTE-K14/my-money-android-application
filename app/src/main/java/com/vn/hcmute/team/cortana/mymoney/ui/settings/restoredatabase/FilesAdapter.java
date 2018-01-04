package com.vn.hcmute.team.cortana.mymoney.ui.settings.restoredatabase;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.vn.hcmute.team.cortana.mymoney.R;
import java.io.File;

/**
 * Created by kunsubin on 1/4/2018.
 */

public class FilesAdapter extends RecyclerView.Adapter<FilesAdapter.ViewHolder>{
    
    private File[] mFiles;
    private LayoutInflater mInflater;
    private ItemClickListener mItemClickListener;
    private Context mContext;
    public FilesAdapter(Context context, File[] files) {
        this.mInflater = LayoutInflater.from(context);
        this.mFiles = files;
        this.mContext=context;
    }
    
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_recyclerview_tool, parent, false);
        return new ViewHolder(view);
    }
    
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bind(getItem(position));
    }
    
    @Override
    public int getItemCount() {
        return mFiles.length;
    }
    
    public void setItemClickListener(
              ItemClickListener itemClickListener) {
        mItemClickListener = itemClickListener;
    }
    
    public File getItem(int id) {
        return mFiles[id];
    }
    
    public interface ItemClickListener {
        
        void onItemClick(File file);
    }
    
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.txt_name)
        TextView txt_name;
        public ViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            ButterKnife.bind(this, itemView);
        }
        public void bind(File file) {
            txt_name.setText(file.getName());
        }
        @Override
        public void onClick(View view) {
            if (mItemClickListener != null) {
                mItemClickListener.onItemClick(getItem(getAdapterPosition()));
            }
        }
    }
}
