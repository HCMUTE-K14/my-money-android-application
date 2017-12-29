package com.vn.hcmute.team.cortana.mymoney.ui.tools.toolsui;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.vn.hcmute.team.cortana.mymoney.R;
import com.vn.hcmute.team.cortana.mymoney.utils.DrawableUtil;
import com.vn.hcmute.team.cortana.mymoney.utils.GlideImageLoader;
import java.util.List;

/**
 * Created by kunsubin on 12/28/2017.
 */

public class ToolsAdapter extends RecyclerView.Adapter<ToolsAdapter.ViewHolder> {
    
    private List<ItemTool> mData;
    private Context mContext;
    private LayoutInflater mInflater;
    private ItemClickListener mItemClickListener;
    public ToolsAdapter(Context context, List<ItemTool> itemTools) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = itemTools;
        this.mContext = context;
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
        return mData.size();
    }
    public void setItemClickListener(
              ItemClickListener itemClickListener) {
        mItemClickListener = itemClickListener;
    }
    public ItemTool getItem(int id){
        return mData.get(id);
    }
    public interface ItemClickListener {
        void onItemClick(ItemTool itemTool);
    }
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.ic_tool)
        ImageView ic_tool;
        @BindView(R.id.txt_name)
        TextView txt_name;
        
        public ViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            ButterKnife.bind(this, itemView);
        }
        public void bind(ItemTool itemTool){
            GlideImageLoader.load(mContext, DrawableUtil.getDrawable(mContext, itemTool.getImage()),
                      ic_tool);
            txt_name.setText(itemTool.getName());
        }
        @Override
        public void onClick(View view) {
            if (mItemClickListener != null) {
                mItemClickListener.onItemClick(getItem(getAdapterPosition()));
            }
        }
    }
}
