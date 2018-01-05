package com.vn.hcmute.team.cortana.mymoney.ui.event.adapter;

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
import com.vn.hcmute.team.cortana.mymoney.model.Event;
import com.vn.hcmute.team.cortana.mymoney.utils.DrawableUtil;
import com.vn.hcmute.team.cortana.mymoney.utils.GlideImageLoader;
import com.vn.hcmute.team.cortana.mymoney.utils.NumberUtil;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by kunsubin on 8/24/2017.
 */

public class MyRecyclerViewEventAdapter extends
                                        RecyclerView.Adapter<MyRecyclerViewEventAdapter.ViewHolder> {
    
    private List<Event> mData = Collections.emptyList();
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;
    private Context mContext;
    
    public MyRecyclerViewEventAdapter(Context context, List<Event> data) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
        this.mContext = context;
    }
    
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_event, parent, false);
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
    
    
    public Event getItem(int id) {
        return mData.get(id);
    }
    
    public void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }
    
    public void setList(List<Event> eventList) {
        mData = new ArrayList<>();
        mData.addAll(eventList);
        notifyDataSetChanged();
    }
    
    public interface ItemClickListener {
        
        void onItemClick(Event event);
    }
    
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        
        @BindView(R.id.txt_name_event)
        TextView txt_name_event;
        @BindView(R.id.txt_money)
        TextView txt_money;
        @BindView(R.id.txt_currency)
        TextView txt_currency;
        @BindView(R.id.image_event)
        ImageView image_event;
        @BindView(R.id.txt_spent_earning)
        TextView txt_spent_earning;
        
        public ViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            ButterKnife.bind(this, itemView);
        }
        
        public void bindView(Event event) {
            if(Double.parseDouble(event.getMoney())<0){
                double money=-Double.parseDouble(event.getMoney());
                txt_name_event.setText(event.getName());
                txt_spent_earning.setText(mContext.getString(R.string.txt_earning));
                txt_money.setText(NumberUtil.formatAmount(String.valueOf(money), ""));
                txt_currency.setText(event.getCurrencies().getCurSymbol());
                txt_money.setTextColor(ContextCompat.getColor(mContext,R.color.green));
                txt_currency.setTextColor(ContextCompat.getColor(mContext,R.color.green));
            }else {
                txt_name_event.setText(event.getName());
                txt_money.setText(NumberUtil.formatAmount(event.getMoney(), ""));
                txt_spent_earning.setText(mContext.getString(R.string.spent));
                txt_currency.setText(event.getCurrencies().getCurSymbol());
                txt_money.setTextColor(ContextCompat.getColor(mContext,R.color.color_red));
                txt_currency.setTextColor(ContextCompat.getColor(mContext,R.color.color_red));
            }
            GlideImageLoader.load(mContext, DrawableUtil.getDrawable(mContext, event.getIcon()),
                      image_event);
        }
        @Override
        public void onClick(View view) {
            if (mClickListener != null) {
                mClickListener.onItemClick(getItem(getAdapterPosition()));
            }
        }
    }
    
}
