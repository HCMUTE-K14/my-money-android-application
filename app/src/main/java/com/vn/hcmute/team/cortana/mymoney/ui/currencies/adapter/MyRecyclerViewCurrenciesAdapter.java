package com.vn.hcmute.team.cortana.mymoney.ui.currencies.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.vn.hcmute.team.cortana.mymoney.R;
import com.vn.hcmute.team.cortana.mymoney.model.Currencies;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by kunsubin on 8/25/2017.
 */

public class MyRecyclerViewCurrenciesAdapter extends RecyclerView.Adapter<MyRecyclerViewCurrenciesAdapter.ViewHolder>{
    private List<Currencies> mData = Collections.emptyList();
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;
    private Context mContext;
    
    // data is passed into the constructor
    public MyRecyclerViewCurrenciesAdapter(Context context, List<Currencies> data) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
        this.mContext=context;
    }
    
    // inflates the row layout from xml when needed
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_recycler_view_currencies, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }
    
    // binds the data to the textview in each row
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.currenccies_name.setText(mData.get(position).getCurName());
        holder.currencies_code.setText(mData.get(position).getCurCode());
        Drawable drawable=getDrawable(mData.get(position).getCurCode().toLowerCase());
        
        if(drawable!=null){
            holder.image_view_curencies.setImageDrawable(drawable);
        }
       
    }
    private Drawable getDrawable(String name){
        String srcName="ic_currency_"+name;
        int id = mContext.getResources().getIdentifier(srcName, "drawable", mContext.getPackageName());
        if(checkDrawableExists(id)){
            Drawable drawable = ContextCompat.getDrawable(mContext,id);
            return drawable;
        }
        return null;
    }
    private boolean checkDrawableExists(int id){
        if(id!=0)
            return true;
        return false;
    }
    // total number of rows
    @Override
    public int getItemCount() {
        return mData.size();
    }
    
    
    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.image_view_curencies)
        ImageView image_view_curencies;
        @BindView(R.id.currenccies_name)
        TextView currenccies_name;
        @BindView(R.id.currencies_code)
        TextView currencies_code;
        
        public ViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            ButterKnife.bind(this, itemView);
        }
        
        @Override
        public void onClick(View view) {
            if (mClickListener != null) {
                mClickListener.onItemClick(view,getItem(getAdapterPosition()), getAdapterPosition());
            }
        }
    }
    
    // convenience method for getting data at click position
    public Currencies getItem(int id) {
        return mData.get(id);
    }
    
    // allows clicks events to be caught
    public void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }
    
    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        
        void onItemClick(View view,Currencies currencies, int position);
    }
    
    public void setFilter(List<Currencies> list){
        mData=new ArrayList<>();
        mData.addAll(list);
        notifyDataSetChanged();
    }
}
