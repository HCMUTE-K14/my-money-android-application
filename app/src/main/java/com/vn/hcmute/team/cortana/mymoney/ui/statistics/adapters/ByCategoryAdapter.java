package com.vn.hcmute.team.cortana.mymoney.ui.statistics.adapters;

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
import com.vn.hcmute.team.cortana.mymoney.model.Wallet;
import com.vn.hcmute.team.cortana.mymoney.ui.statistics.Objects.ObjectByCategory;
import com.vn.hcmute.team.cortana.mymoney.ui.statistics.fragment.FragmentByCategory;
import com.vn.hcmute.team.cortana.mymoney.utils.DrawableUtil;
import com.vn.hcmute.team.cortana.mymoney.utils.GlideImageLoader;
import com.vn.hcmute.team.cortana.mymoney.utils.NumberUtil;
import java.util.List;

/**
 * Created by kunsubin on 11/5/2017.
 */

public class ByCategoryAdapter extends RecyclerView.Adapter<ByCategoryAdapter.ViewHolder> {
    
    private LayoutInflater mInflater;
    private List<ObjectByCategory> mData;
    private Context mContext;
    private Wallet mWallet;
    private ItemClickListener mItemClickListener;
    private int mIdCategory;
    public ByCategoryAdapter(Context context, int idCategory,
              List<ObjectByCategory> data, Wallet wallet) {
        this.mInflater = LayoutInflater.from(context);
        this.mData=data;
        this.mContext=context;
        this.mWallet=wallet;
        this.mIdCategory=idCategory;
    }
    
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_transaction_view_category, parent, false);
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
    
    
    public ObjectByCategory getItem(int id) {
        return mData.get(id);
    }
    
    public void setClickListener(ItemClickListener itemClickListener) {
        this.mItemClickListener = itemClickListener;
    }
    
    public interface ItemClickListener {
        
        void onItemClick(ObjectByCategory objectByCategory);
    }
    
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        
        @BindView(R.id.image_icon_category)
        ImageView image_icon_category;
        @BindView(R.id.txt_category)
        TextView txt_category;
        @BindView(R.id.txt_money)
        TextView txt_money;
        
        public ViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            ButterKnife.bind(this, itemView);
        }
        public void bindView(ObjectByCategory objectByCategory) {
            String curSymbol = mWallet.getCurrencyUnit().getCurSymbol();
            if(mIdCategory==FragmentByCategory.ID_EXPENSE){
                GlideImageLoader.load(mContext, DrawableUtil.getDrawable(mContext, objectByCategory.getCategory().getIcon()),
                          image_icon_category);
                txt_category.setText(objectByCategory.getCategory().getName());
                if(Double.parseDouble(objectByCategory.getMoneyExpense())==0.0){
                    txt_money.setText(NumberUtil.formatAmount(objectByCategory.getMoneyExpense(),curSymbol));
                }else {
                    txt_money.setText("-"+NumberUtil.formatAmount(objectByCategory.getMoneyExpense(),curSymbol));
                }
                txt_money.setTextColor(ContextCompat.getColor(mContext,R.color.color_red));
                return;
            }
            if(mIdCategory==FragmentByCategory.ID_INCOME){
                GlideImageLoader.load(mContext, DrawableUtil.getDrawable(mContext, objectByCategory.getCategory().getIcon()),
                          image_icon_category);
                txt_category.setText(objectByCategory.getCategory().getName());
                txt_money.setText(NumberUtil.formatAmount(objectByCategory.getMoneyExpense(),curSymbol));
                txt_money.setTextColor(ContextCompat.getColor(mContext,R.color.green));
                return;
            }
        }
        
        @Override
        public void onClick(View view) {
            if (mItemClickListener != null) {
                mItemClickListener.onItemClick(getItem(getAdapterPosition()));
            }
        }
    }
}
