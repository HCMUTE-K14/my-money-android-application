package com.vn.hcmute.team.cortana.mymoney.ui.statistics.adapters;


import android.content.Context;
import android.support.v7.widget.RecyclerView;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.vn.hcmute.team.cortana.mymoney.R;
import com.vn.hcmute.team.cortana.mymoney.model.Wallet;
import com.vn.hcmute.team.cortana.mymoney.ui.statistics.Objects.ObjectByTime;
import com.vn.hcmute.team.cortana.mymoney.ui.statistics.fragment.FragmentByTime;
import com.vn.hcmute.team.cortana.mymoney.utils.DateUtil;
import com.vn.hcmute.team.cortana.mymoney.utils.NumberUtil;
import java.util.Collections;
import java.util.List;


/**
 * Created by kunsubin on 11/5/2017.
 */

public class TransactionByTimeAdapter extends RecyclerView.Adapter<TransactionByTimeAdapter.ViewHolder> {
    
    private List<ObjectByTime> mData = Collections.emptyList();
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;
    private Context mContext;
    private int IdCategory;
    private Wallet mWallet;
    
    
    public TransactionByTimeAdapter(Context context, int idCategory,
              List<ObjectByTime> data, Wallet wallet) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
        this.mContext = context;
        this.IdCategory = idCategory;
        this.mWallet = wallet;
    }
    
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_by_date, parent, false);
        return new ViewHolder(view);
    }
    
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bindView(getItem(position));
    }
    
    @Override
    public int getItemCount() {
        return mData.size();
    }
    
    
    public ObjectByTime getItem(int id) {
        return mData.get(id);
    }
    
    public void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }
    
    public interface ItemClickListener {
        
        void onItemClick(ObjectByTime objectByTime);
    }
    
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        
        @BindView(R.id.linear_frame)
        LinearLayout linear_frame;
        @BindView(R.id.txt_month)
        TextView txt_month;
        @BindView(R.id.txt_year)
        TextView txt_year;
        @BindView(R.id.txt_money_expense)
        TextView txt_money_expense;
        @BindView(R.id.txt_money_income)
        TextView txt_money_income;
        @BindView(R.id.txt_net_income)
        TextView txt_net_income;
        
        public ViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            ButterKnife.bind(this, itemView);
        }
        
        public void bindView(ObjectByTime objectByTime) {
            String curSymbol = mWallet.getCurrencyUnit().getCurSymbol();
            if (IdCategory == FragmentByTime.ID_EXPENSE) {
                linear_frame.setGravity(Gravity.RIGHT);
                txt_net_income.setVisibility(View.GONE);
                txt_money_expense.setVisibility(View.VISIBLE);
                txt_money_income.setVisibility(View.GONE);
                String[] arr = objectByTime.getDate().split("/");
                txt_month.setText(
                          DateUtil.getMonthOfYear(mContext, (Integer.parseInt(arr[0])) - 1));
                txt_year.setText(arr[1]);
                
                if (!objectByTime.getMoneyExpense().equals("0.0")) {
                    txt_money_expense.setText("-" +
                                              NumberUtil.formatAmount(
                                                        objectByTime.getMoneyExpense(),
                                                        curSymbol));
                } else {
                    txt_money_expense
                              .setText(NumberUtil.formatAmount(objectByTime.getMoneyExpense(),
                                        curSymbol));
                }
                return;
            }
            if (IdCategory == FragmentByTime.ID_INCOME) {
                linear_frame.setGravity(Gravity.RIGHT);
                txt_net_income.setVisibility(View.GONE);
                txt_money_expense.setVisibility(View.GONE);
                txt_money_income.setVisibility(View.VISIBLE);
                String[] arr = objectByTime.getDate().split("/");
                txt_month.setText(
                          DateUtil.getMonthOfYear(mContext, (Integer.parseInt(arr[0])) - 1));
                txt_year.setText(arr[1]);
                txt_money_income.setText(NumberUtil.formatAmount(objectByTime.getMoneyIncome(),
                          curSymbol));
                return;
            }
            if (IdCategory == FragmentByTime.ID_NETINCOME) {
                linear_frame.setGravity(Gravity.CENTER);
                txt_net_income.setVisibility(View.VISIBLE);
                txt_money_expense.setVisibility(View.VISIBLE);
                txt_money_income.setVisibility(View.VISIBLE);
                String[] arr = objectByTime.getDate().split("/");
                txt_month.setText(
                          DateUtil.getMonthOfYear(mContext, (Integer.parseInt(arr[0])) - 1));
                txt_year.setText(arr[1]);
                
                if (!objectByTime.getMoneyExpense().equals("0.0")) {
                    txt_money_expense.setText("-" +
                                              NumberUtil.formatAmount(
                                                        objectByTime.getMoneyExpense(),
                                                        curSymbol));
                } else {
                    txt_money_expense
                              .setText(NumberUtil.formatAmount(objectByTime.getMoneyExpense(),
                                        curSymbol));
                }
                txt_money_income.setText(NumberUtil.formatAmount(objectByTime.getMoneyIncome(),
                          curSymbol));
                double moneyNetInCome = Double.parseDouble(objectByTime.getMoneyIncome()) -
                                        Double.parseDouble(objectByTime.getMoneyExpense());
                txt_net_income.setText(NumberUtil.formatAmount(moneyNetInCome+"",curSymbol));
                
                return;
            }
        }
        
        @Override
        public void onClick(View view) {
            if (mClickListener != null) {
                mClickListener.onItemClick(getItem(getAdapterPosition()));
            }
        }
    }
}
