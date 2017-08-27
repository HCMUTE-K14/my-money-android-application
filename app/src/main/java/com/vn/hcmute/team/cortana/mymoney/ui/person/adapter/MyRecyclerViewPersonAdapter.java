package com.vn.hcmute.team.cortana.mymoney.ui.person.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.vn.hcmute.team.cortana.mymoney.R;
import com.vn.hcmute.team.cortana.mymoney.model.Person;
import java.util.Collections;
import java.util.List;

/**
 * Created by kunsubin on 8/24/2017.
 */

public class MyRecyclerViewPersonAdapter extends RecyclerView.Adapter<MyRecyclerViewPersonAdapter.ViewHolder> {
    
    private List<Person> mData = Collections.emptyList();
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;
    
    // data is passed into the constructor
    public MyRecyclerViewPersonAdapter(Context context, List<Person> data) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
    }
    
    // inflates the row layout from xml when needed
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_recycler_view_person, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }
    
    // binds the data to the textview in each row
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.personid.setText(mData.get(position).getPersonid());
        holder.name.setText(mData.get(position).getName());
        holder.describe.setText(mData.get(position).getDescribe());
        holder.userid.setText(mData.get(position).getUserid());
        
        
    }
    
    // total number of rows
    @Override
    public int getItemCount() {
        return mData.size();
    }
    
    
    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        
        @BindView(R.id.personid)
        TextView personid;
        @BindView(R.id.name)
        TextView name;
        @BindView(R.id.describe)
        TextView describe;
        @BindView(R.id.userid)
        TextView userid;
        
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
    
    // convenience method for getting data at click position
    public Person getItem(int id) {
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
}
