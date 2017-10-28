package com.vn.hcmute.team.cortana.mymoney.ui.budget.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.vn.hcmute.team.cortana.mymoney.R;
import com.vn.hcmute.team.cortana.mymoney.model.Budget;
import com.vn.hcmute.team.cortana.mymoney.utils.DateUtil;
import com.vn.hcmute.team.cortana.mymoney.utils.DrawableUtil;
import com.vn.hcmute.team.cortana.mymoney.utils.GlideImageLoader;
import com.vn.hcmute.team.cortana.mymoney.utils.NumberUtil;
import java.util.ArrayList;
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
    private Context mContext;
    
    public MyRecyclerViewBudgetAdapter(Context context, List<Budget> data) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
        mContext = context;
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
    
    public void setList(List<Budget> list) {
        mData = new ArrayList<>();
        mData.addAll(list);
        notifyDataSetChanged();
    }
    
    public interface ItemClickListener {
        
        void onItemClick(Budget budget);
    }
    
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        
        @BindView(R.id.txt_budget_name)
        TextView txt_budget_name;
        @BindView(R.id.txt_range_date)
        TextView txt_range_date;
        @BindView(R.id.txt_money_expense)
        TextView txt_money_expense;
        @BindView(R.id.txt_time_rest)
        TextView txt_time_rest;
        @BindView(R.id.seek_bar_budget)
        SeekBar seek_bar_budget;
        @BindView(R.id.image_icon_category_budget)
        ImageView image_icon_category_budget;
        
        public ViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            ButterKnife.bind(this, itemView);
        }
        
        public void bindView(Budget budget) {
            txt_budget_name.setText(budget.getCategory().getName());
            txt_range_date.setText(getRangeDate(budget.getRangeDate()));
            seek_bar_budget
                      .setProgress(getProgress(budget.getMoneyExpense(), budget.getMoneyGoal()));
            if (!checkNegative(budget.getMoneyExpense())) {
                txt_money_expense.setText("+" + NumberUtil.formatAmount(budget.getMoneyExpense(),budget.getWallet().getCurrencyUnit().getCurSymbol()));
            } else {
                txt_money_expense.setText("-" + NumberUtil.formatAmount(budget.getMoneyExpense(),budget.getWallet().getCurrencyUnit().getCurSymbol()));
                
                txt_money_expense.setTextColor(ContextCompat.getColor(mContext, R.color.color_red));
                seek_bar_budget.setProgressDrawable(
                          ContextCompat.getDrawable(mContext, R.drawable.process_color_red));
            }
            seek_bar_budget.setEnabled(false);
            
            txt_time_rest.setText(getTimeRest(budget.getRangeDate()));
            txt_time_rest.setText(mContext
                      .getString(R.string.days_left, getTimeRest(budget.getRangeDate()) + ""));
            
            GlideImageLoader.load(mContext,
                      DrawableUtil.getDrawable(mContext, budget.getCategory().getIcon()),
                      image_icon_category_budget);
        }
        
        public String getTimeRest(String rangeDate) {
            String[] arr = rangeDate.split("/");
            int result = DateUtil.getDateLeft(Long.parseLong(arr[1]));
            return String.valueOf(result);
        }
        
        public String getRangeDate(String rangeDate) {
            String result = "";
            String[] arr = rangeDate.split("/");
            result = DateUtil.convertTimeMilisToDateNotYear(arr[0]) + " - " +
                     DateUtil.convertTimeMilisToDateNotYear(arr[1]);
            return result;
        }
        
        public int getProgress(String a, String b) {
            double current = Double.parseDouble(a);
            if (current < 0) {
                return 100;
            }
            double goal = Double.parseDouble(b);
            double proportion = (current / goal) * 100;
            return (int) proportion;
        }
        
        public boolean checkNegative(String moneyExpense) {
            double money = Double.parseDouble(moneyExpense);
            return money < 0 ? true : false;
        }
        
        @Override
        public void onClick(View view) {
            if (mClickListener != null) {
                mClickListener.onItemClick(getItem(getAdapterPosition()));
            }
        }
    }
}
