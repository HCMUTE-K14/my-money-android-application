package com.vn.hcmute.team.cortana.mymoney.ui.saving.adapter;

import android.content.Context;
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
import com.vn.hcmute.team.cortana.mymoney.di.module.GlideApp;
import com.vn.hcmute.team.cortana.mymoney.model.Transaction;
import com.vn.hcmute.team.cortana.mymoney.utils.DateUtil;
import com.vn.hcmute.team.cortana.mymoney.utils.DrawableUtil;
import com.vn.hcmute.team.cortana.mymoney.utils.NumberUtil;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by kunsubin on 10/15/2017.
 */

public class TransactionSavingAdapter extends
                                      RecyclerView.Adapter<TransactionSavingAdapter.ViewHolder> {
    
    private List<Transaction> mData = Collections.emptyList();
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;
    private Context mContext;
    
    public TransactionSavingAdapter(Context context, List<Transaction> data) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
        this.mContext = context;
    }
    
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_list_transaction_saving, parent, false);
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
    
    public Transaction getItem(int id) {
        return mData.get(id);
    }
    
    public void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }
    
    public void setList(List<Transaction> list) {
        mData = new ArrayList<>();
        mData.addAll(list);
        notifyDataSetChanged();
    }
    
    public interface ItemClickListener {
        
        void onItemClick(Transaction transaction);
    }
    
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        
        @BindView(R.id.ic_saving)
        ImageView ic_saving;
        @BindView(R.id.txt_date)
        TextView txt_date;
        @BindView(R.id.txt_take_in_out)
        TextView txt_take_in_out;
        @BindView(R.id.txt_money)
        TextView txt_money;
        @BindView(R.id.txt_note)
        TextView txt_note;
        
        public ViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            ButterKnife.bind(this, itemView);
        }
        
        public void bind(Transaction transaction) {
            GlideApp.with(mContext)
                      .load(DrawableUtil.getDrawable(mContext, transaction.getSaving().getIcon()))
                      .placeholder(R.drawable.folder_placeholder)
                      .error(R.drawable.folder_placeholder)
                      .dontAnimate()
                      .into(ic_saving);
            
            txt_date.setText(
                      DateUtil.convertTimeMillisToDate(transaction.getDate_created().trim()));
            txt_note.setText(transaction.getNote());
            
            if (transaction.getType().equals("expense")) {
                txt_take_in_out.setText(mContext.getString(R.string.take_in));
                txt_money.setText("-" + NumberUtil.formatAmount(transaction.getAmount(),
                          transaction.getWallet().getCurrencyUnit().getCurSymbol()));
                
                txt_money.setTextColor(ContextCompat.getColor(mContext, R.color.color_red));
                
            }
            if (transaction.getType().equals("income")) {
                txt_take_in_out.setText(mContext.getString(R.string.take_out));
                txt_money.setText("+" + NumberUtil.formatAmount(transaction.getAmount(),
                          transaction.getSaving().getCurrencies().getCurSymbol()));
                txt_money.setTextColor(ContextCompat.getColor(mContext, R.color.green));
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
