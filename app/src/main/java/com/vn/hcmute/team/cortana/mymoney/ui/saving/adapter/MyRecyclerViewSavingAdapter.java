package com.vn.hcmute.team.cortana.mymoney.ui.saving.adapter;

import android.content.Context;
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
import com.vn.hcmute.team.cortana.mymoney.model.Saving;
import com.vn.hcmute.team.cortana.mymoney.utils.DateUtil;
import com.vn.hcmute.team.cortana.mymoney.utils.DrawableUtil;
import com.vn.hcmute.team.cortana.mymoney.utils.GlideImageLoader;
import com.vn.hcmute.team.cortana.mymoney.utils.NumberUtil;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by kunsubin on 8/25/2017.
 */

public class MyRecyclerViewSavingAdapter extends
                                         RecyclerView.Adapter<MyRecyclerViewSavingAdapter.ViewHolder> {
    
    private List<Saving> mData = Collections.emptyList();
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;
    private Context mContext;
    
    public MyRecyclerViewSavingAdapter(Context context, List<Saving> data) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
        this.mContext = context;
    }
    
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_saving, parent, false);
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
    
    public Saving getItem(int id) {
        return mData.get(id);
    }
    
    public void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }
    
    public void setList(List<Saving> list) {
        mData = new ArrayList<>();
        mData.addAll(list);
        notifyDataSetChanged();
    }
    
    public interface ItemClickListener {
        
        void onItemClick(View view, List<Saving> savingList, int position, int process);
    }
    
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        
        @BindView(R.id.txt_saving_name)
        TextView txt_saving_name;
        @BindView(R.id.txt_money_goal)
        TextView txt_money_goal;
        @BindView(R.id.txt_time_rest)
        TextView txt_time_rest;
        @BindView(R.id.seek_bar_saving)
        SeekBar seek_bar_saving;
        @BindView(R.id.image_icon_saving)
        ImageView image_icon_saving;
        
        public ViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            ButterKnife.bind(this, itemView);
        }
        
        public void bind(Saving saving) {
            txt_saving_name.setText(saving.getName());
            txt_money_goal.setText(NumberUtil
                      .formatAmount(saving.getGoalMoney(), saving.getCurrencies().getCurSymbol()));
            
            txt_time_rest.setText(mContext
                      .getString(R.string.days_left, getDateRest(saving.getDate()) + ""));
            int t = getProgress(saving.getCurrentMoney(), saving.getGoalMoney());
            seek_bar_saving.setProgress(t);
            seek_bar_saving.setEnabled(false);
            
            GlideImageLoader.load(mContext, DrawableUtil.getDrawable(mContext, saving.getIcon()),
                      image_icon_saving);
        }
        
        public int getDateRest(String milisecond) {
            long dateMilisecond = Long.parseLong(milisecond);
            return DateUtil.getDateLeft(dateMilisecond);
        }
        
        public int getProgress(String a, String b) {
            double current = Double.parseDouble(a);
            double goal = Double.parseDouble(b);
            
            double proportion = (current / goal) * 100;
            
            return (int) proportion;
        }
        
        @Override
        public void onClick(View view) {
            if (mClickListener != null) {
                mClickListener.onItemClick(view, mData, getAdapterPosition(),
                          seek_bar_saving.getProgress());
            }
        }
    }
    
}
