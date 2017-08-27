package com.vn.hcmute.team.cortana.mymoney.ui.saving.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.vn.hcmute.team.cortana.mymoney.R;
import com.vn.hcmute.team.cortana.mymoney.model.Saving;
import java.util.Collections;
import java.util.List;

/**
 * Created by kunsubin on 8/25/2017.
 */

public class MyRecyclerViewSavingAdapter extends RecyclerView.Adapter<MyRecyclerViewSavingAdapter.ViewHolder>{
    private List<Saving> mData = Collections.emptyList();
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;
    

    public MyRecyclerViewSavingAdapter(Context context, List<Saving> data) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
    }
    

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_recycler_view_saving, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }
    

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.saving_name.setText(mData.get(position).getName());
        holder.goal_money.setText(mData.get(position).getGoalMoney());
        holder.current_money.setText(mData.get(position).getCurrentMoney());
        
        
        
        holder.need_money.setText(getNeedMoney(mData.get(position).getGoalMoney(),mData.get(position).getCurrentMoney()));
        
        //holder.need_money.setText(holder.getWidthLinearLayout()+"");
       // holder.setWidthView(getProportion(mData.get(position).getCurrentMoney(),mData.get(position).getGoalMoney()));
        
        holder.date.setText(mData.get(0).getDate());
        
        
    }
    
    public  String getNeedMoney(String goalMoney,String currentMoney){
        double money=Double.parseDouble(goalMoney)-Double.parseDouble(currentMoney);
        return String.valueOf(money);
    }
    public double getProportion(String a,String b){
        double proportion=Double.parseDouble(a)/Double.parseDouble(b);
        return proportion;
    }
    
    @Override
    public int getItemCount() {
        return mData.size();
    }
    

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.saving_name)
        TextView saving_name;
        @BindView(R.id.goal_money)
        TextView goal_money;
        @BindView(R.id.current_money)
        TextView current_money;
        @BindView(R.id.need_money)
        TextView need_money;
        @BindView(R.id.date)
        TextView date;
   
        //@BindView(R.id.view_process)
      //  View view_process;
      /*  @BindView(R.id.linearlayout_process)
        LinearLayout linearlayout_process;*/
        
        
        
        public ViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            ButterKnife.bind(this, itemView);
        }
     /*   public int getWidthLinearLayout(){
            return linearlayout_process.getLayoutParams().width;
        }
        public int getWidthView(){
            return view_process.getLayoutParams().width;
        }
        public void setWidthView(double values){
            double result=values*getWidthLinearLayout()-0.065*values*getWidthLinearLayout();
            view_process.getLayoutParams().width=(int)result;
          
        }*/
        
        @Override
        public void onClick(View view) {
            if (mClickListener != null) {
                mClickListener.onItemClick(view,mData, getAdapterPosition());
            }
        }
    }
    

    public Saving getItem(int id) {
        return mData.get(id);
    }
    
    public void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    public interface ItemClickListener {
        void onItemClick(View view,List<Saving> savingList, int position);
    }
}
