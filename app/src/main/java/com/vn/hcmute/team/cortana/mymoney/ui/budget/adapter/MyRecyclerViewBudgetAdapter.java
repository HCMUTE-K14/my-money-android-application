package com.vn.hcmute.team.cortana.mymoney.ui.budget.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.vn.hcmute.team.cortana.mymoney.R;
import com.vn.hcmute.team.cortana.mymoney.model.Budget;
import java.util.Collections;
import java.util.List;

/**
 * Created by kunsubin on 8/24/2017.
 */

public class MyRecyclerViewBudgetAdapter extends
                                         RecyclerView.Adapter<MyRecyclerViewBudgetAdapter.ViewHolder> {
    
    private List<Budget> mData = Collections.emptyList();
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;
    
    
    public MyRecyclerViewBudgetAdapter(Context context, List<Budget> data) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
    }
    
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_budget, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }
    
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
       holder.bindView(getItem(position));
    }
    
    
    @Override
    public int getItemCount() {
        return mData.size();
    }
    
    public Budget getItem(int id) {
        return mData.get(id);
    }
    
    public void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }
    
    
    public interface ItemClickListener {
        
        void onItemClick(Budget budget);
    }
    
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.txt_budget_name)
        TextView txt_budget_name;
        @BindView(R.id.txt_range_date)
        TextView txt_range_date;
        @BindView(R.id.txt_money_goal)
        TextView txt_money_goal;
        @BindView(R.id.txt_time_rest)
        TextView txt_time_rest;
        @BindView(R.id.seek_bar_budget)
        SeekBar seek_bar_budget;
        
        public ViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            ButterKnife.bind(this, itemView);
        }
        public void bindView(Budget budget){
            
        }
        @Override
        public void onClick(View view) {
            if (mClickListener != null) {
                mClickListener.onItemClick(getItem(getAdapterPosition()));
            }
        }
    }
}
