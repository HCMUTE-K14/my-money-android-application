package com.vn.hcmute.team.cortana.mymoney.ui.category;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.vn.hcmute.team.cortana.mymoney.R;
import com.vn.hcmute.team.cortana.mymoney.model.Category;
import com.vn.hcmute.team.cortana.mymoney.ui.category.ListParentCategoryAdapter.ListParentCategoryViewHolder;
import com.vn.hcmute.team.cortana.mymoney.utils.DrawableUtil;
import com.vn.hcmute.team.cortana.mymoney.utils.GlideImageLoader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by infamouSs on 9/18/17.
 */

public class ListParentCategoryAdapter extends RecyclerView.Adapter<ListParentCategoryViewHolder> {
    
    private List<Category> mCategories = new ArrayList<>();
    private List<Category> mDisplayCategories = new ArrayList<>();
    private Context mContext;
    private String mSelectedCategory;
    private OnSelectParentCategoryListener mListener;
    
    private ItemFilter mFilter = new ItemFilter();
    
    public ListParentCategoryAdapter(Context context, OnSelectParentCategoryListener listener,
              List<Category> categories) {
        mContext = context;
        if (categories != null) {
            mCategories = categories;
            mDisplayCategories = categories;
        }
        mListener = listener;
    }
    
    @Override
    public ListParentCategoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                  .inflate(R.layout.item_view_parent_category, parent, false);
        
        return new ListParentCategoryViewHolder(view);
    }
    
    @Override
    public int getItemCount() {
        return mDisplayCategories.size();
    }
    
    @Override
    public void onBindViewHolder(ListParentCategoryViewHolder holder, final int position) {
        final Category category = mDisplayCategories.get(position);
        
        holder.mTextViewNameCate.setText(category.getName());
        GlideImageLoader.load(mContext, DrawableUtil.getDrawable(mContext, category.getIcon()),
                  holder.mImageViewIcon);
        holder.mImageViewSelected
                  .setVisibility(isSelectedCategory(category) ? View.VISIBLE : View.GONE);
        
        holder.itemView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    mListener.onClick(position, category);
                }
            }
        });
    }
    
    private boolean isSelectedCategory(Category category) {
        return category.getId().equalsIgnoreCase(mSelectedCategory);
    }
    
    public boolean isEmptyData() {
        return mCategories.isEmpty();
    }
    
    public List<Category> getData() {
        return mCategories;
    }
    
    public void setData(List<Category> categories) {
        if (mCategories == null) {
            mCategories = new ArrayList<>();
            
        }
        
        if (mDisplayCategories == null) {
            mDisplayCategories = new ArrayList<>();
            
        }
        
        mCategories.clear();
        mCategories.addAll(categories);
        
        mDisplayCategories.clear();
        mDisplayCategories.addAll(categories);
    }
    
    public String getSelectedCategory() {
        return mSelectedCategory;
    }
    
    public void setSelectedCategory(String selectedCategory) {
        mSelectedCategory = selectedCategory;
    }
    
    public void filter(String str) {
        this.mFilter.filter(str);
    }
    
    public interface OnSelectParentCategoryListener {
        
        void onClick(int position, Category category);
    }
    
    static class ListParentCategoryViewHolder extends RecyclerView.ViewHolder {
        
        @BindView(R.id.text_view_name_cate)
        TextView mTextViewNameCate;
        
        @BindView(R.id.image_view_icon)
        ImageView mImageViewIcon;
        
        @BindView(R.id.image_view_selected)
        ImageView mImageViewSelected;
        
        @BindView(R.id.divider)
        View mViewDivider;
        
        @BindView(R.id.txt_view_num_of_sub_cate)
        TextView mTextViewNumOfSubCate;
        
        @BindView(R.id.layout_icon)
        LinearLayout mLinearLayoutIcon;
        
        ListParentCategoryViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            mLinearLayoutIcon.setVisibility(View.GONE);
        }
    }
    
    private class ItemFilter extends Filter {
        
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            String filterString = constraint.toString().toLowerCase();
            
            FilterResults results = new FilterResults();
            
            final ArrayList<Category> tempFilterList = new ArrayList<>();
            
            for (Category category : mCategories) {
                String name = category.getName().toLowerCase();
                
                if (name.contains(filterString)) {
                    tempFilterList.add(category);
                }
            }
            
            results.values = tempFilterList;
            results.count = tempFilterList.size();
            
            return results;
        }
        
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            mDisplayCategories.clear();
            mDisplayCategories = (ArrayList<Category>) results.values;
            notifyDataSetChanged();
        }
    }
}
