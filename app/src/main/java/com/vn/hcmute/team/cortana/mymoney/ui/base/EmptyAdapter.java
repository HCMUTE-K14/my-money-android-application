package com.vn.hcmute.team.cortana.mymoney.ui.base;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.vn.hcmute.team.cortana.mymoney.R;
import com.vn.hcmute.team.cortana.mymoney.ui.base.EmptyAdapter.EmptyAdapterViewHolder;

/**
 * Created by infamouSs on 8/26/17.
 */

public class EmptyAdapter extends RecyclerView.Adapter<EmptyAdapterViewHolder> {
    
    private Context mContext;
    private String mMessage;
    
    public EmptyAdapter(Context context, String message) {
        this.mContext = context;
        this.mMessage = message;
    }
    
    @Override
    public EmptyAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                  .inflate(R.layout.item_empty, parent, false);
        
        return new EmptyAdapterViewHolder(v);
    }
    
    @Override
    public void onBindViewHolder(EmptyAdapterViewHolder holder, int position) {
        holder.mTextViewMessage.setText(mMessage);
    }
    
    @Override
    public int getItemCount() {
        return 1;
    }
    
    public Context getContext() {
        return mContext;
    }
    
    public void setContext(Context context) {
        mContext = context;
    }
    
    public String getMessage() {
        return mMessage;
    }
    
    public void setMessage(String message) {
        mMessage = message;
        notifyItemChanged(1);
    }
    
    public class EmptyAdapterViewHolder extends RecyclerView.ViewHolder {
        
        @BindView(R.id.txt_message)
        TextView mTextViewMessage;
        
        public EmptyAdapterViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
    
}
