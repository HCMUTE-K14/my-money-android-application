package com.vn.hcmute.team.cortana.mymoney.ui.event.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.vn.hcmute.team.cortana.mymoney.R;
import com.vn.hcmute.team.cortana.mymoney.model.Event;
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
    

    public MyRecyclerViewEventAdapter(Context context, List<Event> data) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
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
    
    // convenience method for getting data at click position
    public Event getItem(int id) {
        return mData.get(id);
    }
    
    // allows clicks events to be caught
    public void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }
    
    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {

        void onItemClick(View view, int position);
    }
    
 
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        
        @BindView(R.id.txt_name_event)
        TextView txt_name_event;
        @BindView(R.id.txt_money)
        TextView txt_money;
        @BindView(R.id.txt_currency)
        TextView txt_currency;
        public ViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            ButterKnife.bind(this, itemView);
        }
        public void bindView(Event event){
            txt_name_event.setText(event.getName());
            txt_money.setText(event.getMoney());
            txt_currency.setText(event.getCurrencies().getCurSymbol());
        }
        @Override
        public void onClick(View view) {
            if (mClickListener != null) {
                mClickListener.onItemClick(getItem(getAdapterPosition()));
            }
        }
    }
    public Event getItem(int id) {
        return mData.get(id);
    }
    public void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }
    public interface ItemClickListener {
        
        void onItemClick(Event event);
    }
    
    public void setList(List<Event> eventList){
        mData=new ArrayList<>();
        mData.addAll(eventList);
        notifyDataSetChanged();
    }
}
