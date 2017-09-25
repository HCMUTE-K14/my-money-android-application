package com.vn.hcmute.team.cortana.mymoney.ui.currencies;

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
import com.vn.hcmute.team.cortana.mymoney.di.module.GlideApp;
import com.vn.hcmute.team.cortana.mymoney.model.Currencies;
import com.vn.hcmute.team.cortana.mymoney.utils.DrawableUtil;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by kunsubin on 8/25/2017.
 */

public class CurrenciesAdapter extends
                               RecyclerView.Adapter<CurrenciesAdapter.ViewHolder> {
    
    public static final String TAG = CurrenciesAdapter.class.getSimpleName();
    
    private List<Currencies> mData = Collections.emptyList();
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;
    private Context mContext;
    
    public CurrenciesAdapter(Context context, List<Currencies> data) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
        this.mContext = context;
    }
    
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_recycler_view_currencies, parent, false);
        return new ViewHolder(view);
    }
    
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Currencies currencies = mData.get(position);
        
        holder.currencies_name.setText(currencies.getCurName());
        holder.currencies_code.setText(currencies.getCurCode());
        
        String iconString = "ic_currency_" + currencies.getCurCode().toLowerCase();
        
        GlideApp.with(mContext)
                  .load(DrawableUtil.getDrawable(mContext, iconString))
                  .placeholder(R.drawable.folder_placeholder)
                  .error(R.drawable.folder_placeholder)
                  .into(holder.image_view_currencies);
        
    }
    
    @Override
    public int getItemCount() {
        return mData.size();
    }
    
    public Currencies getItem(int id) {
        return mData.get(id);
    }
    
    public void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }
    
    public void setFilter(List<Currencies> list) {
        mData = new ArrayList<>();
        mData.addAll(list);
        notifyDataSetChanged();
    }
    
    public interface ItemClickListener {
        
        void onItemClick(View view, Currencies currencies, int position);
    }
    
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        
        @BindView(R.id.image_view_curencies)
        ImageView image_view_currencies;
        @BindView(R.id.currenccies_name)
        TextView currencies_name;
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
                mClickListener
                          .onItemClick(view, getItem(getAdapterPosition()), getAdapterPosition());
            }
        }
    }
}
