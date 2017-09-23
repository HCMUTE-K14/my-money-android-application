package com.vn.hcmute.team.cortana.mymoney.ui.budget.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
       /* holder.budgetId.setText(mData.get(position).getBudgetId());
        holder.categoryId.setText(mData.get(position).getCategoryId());
        holder.rangeDate.setText(mData.get(position).getRangeDate());
        holder.moneyGoal.setText(mData.get(position).getMoneyGoal());
        holder.status.setText(mData.get(position).getStatus());
        holder.userid.setText(mData.get(position).getUserid());
        holder.moneyExpense.setText(mData.get(position).getMoneyExpense());
        holder.walletid.setText(mData.get(position).getWalletid());*/
        
    }
    

    @Override
    public int getItemCount() {
        return mData.size();
    }
 
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        
        /*@BindView(R.id.budgetId)
        TextView budgetId;
        @BindView(R.id.categoryId)
        TextView categoryId;
        @BindView(R.id.walletid)
        TextView walletid;
        @BindView(R.id.rangeDate)
        TextView rangeDate;
        @BindView(R.id.moneyGoal)
        TextView moneyGoal;
        @BindView(R.id.status)
        TextView status;
        @BindView(R.id.userid)
        TextView userid;
        @BindView(R.id.moneyExpense)
        TextView moneyExpense;*/

        public ViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            ButterKnife.bind(this, itemView);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) {
                mClickListener.onItemClick(view, getAdapterPosition());
            }
        }
    }

    public Budget getItem(int id) {
        return mData.get(id);
    }
    

    public void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }
   
    public interface ItemClickListener {
        
        void onItemClick(View view, int position);
    }
}
