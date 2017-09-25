package com.vn.hcmute.team.cortana.mymoney.ui.category;

import android.content.Context;
import android.support.v7.widget.PopupMenu.OnMenuItemClickListener;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.vn.hcmute.team.cortana.mymoney.R;
import com.vn.hcmute.team.cortana.mymoney.di.module.GlideApp;
import com.vn.hcmute.team.cortana.mymoney.model.Category;
import com.vn.hcmute.team.cortana.mymoney.ui.view.MenuPopupWithIcon;
import com.vn.hcmute.team.cortana.mymoney.utils.DrawableUtil;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by infamouSs on 9/16/17.
 */

public class CategoryByTypeAdapter extends BaseExpandableListAdapter {
    
    private List<Category> mOriginalCategories = new ArrayList<>();
    private Context mContext;
    
    private String mCategoryIdSelected;
    
    private int groupPosition;
    private int childPosition;
    
    private CategoryListener mCategoryListener;
    
    public CategoryByTypeAdapter(Context context, CategoryListener categoryListener,
              List<Category> categories) {
        this.mContext = context;
        
        if (categories != null) {
            mOriginalCategories = categories;
        }
        
        mCategoryListener = categoryListener;
    }
    
    @Override
    public int getGroupTypeCount() {
        return getGroupCount() == 0 ? 1 : getGroupCount();
    }
    
    @Override
    public int getGroupType(int groupPosition) {
        return groupPosition;
    }
    
    @Override
    public int getGroupCount() {
        return mOriginalCategories.size();
    }
    
    @Override
    public int getChildrenCount(int groupPosition) {
        if (mOriginalCategories.size() == 0) {
            return 0;
        }
        return mOriginalCategories.get(groupPosition).getSubcategories().size();
    }
    
    @Override
    public Object getGroup(int groupPosition) {
        return mOriginalCategories.get(groupPosition);
    }
    
    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return mOriginalCategories.get(groupPosition).getSubcategories().get(childPosition);
    }
    
    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }
    
    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }
    
    @Override
    public boolean hasStableIds() {
        return false;
    }
    
    @Override
    public View getGroupView(final int groupPosition, boolean isExpanded, View convertView,
              ViewGroup parent) {
        
        final CategoryParentViewHolder holder;
        
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) mContext
                      .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater
                      .inflate(R.layout.item_view_parent_category, parent, false);
            
            holder = new CategoryParentViewHolder(convertView);
            
            convertView.setTag(holder);
        } else {
            holder = (CategoryParentViewHolder) convertView.getTag();
        }
        
        if (groupPosition == 0) {
            holder.mViewDivider.setVisibility(View.GONE);
        } else {
            holder.mViewDivider.setVisibility(View.VISIBLE);
        }
        
        final Category category = mOriginalCategories.get(groupPosition);
        
        if (category.getSubcategories().size() > 0) {
            String numOfSubCategory = mContext
                      .getString(R.string.txt_num_of_sub_cate, category.getSubcategories().size());
            holder.mTextViewNumOfSubCate.setVisibility(View.VISIBLE);
            holder.mTextViewNumOfSubCate.setText(numOfSubCategory);
        } else {
            holder.mTextViewNumOfSubCate.setVisibility(View.GONE);
        }
        
        holder.mTextViewNameCate.setText(category.getName());
        
        GlideApp.with(mContext).load(DrawableUtil.getDrawable(mContext, category.getIcon()))
                  .placeholder(R.drawable.folder_placeholder)
                  .error(R.drawable.folder_placeholder)
                  .into(holder.mImageViewIcon);
        
        if (isCategorySelected(category)) {
            holder.mImageViewSelected.setVisibility(View.VISIBLE);
        } else {
            holder.mImageViewSelected.setVisibility(View.GONE);
        }
        
        holder.mImageViewPopup.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                
                MenuPopupWithIcon popup = new MenuPopupWithIcon(mContext, holder.mLayoutIcon);
                popup.inflate(R.menu.menu_popup_select_category);
                popup.setMenuItemCLickListener(new OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.action_edit:
                                category.setParent(
                                          new Category(category.getId(), category.getName()));
                                mCategoryListener.onClickEdit(groupPosition, -1, category);
                                return true;
                            case R.id.action_remove:
                                mCategoryListener.onClickRemove(groupPosition, -1, category);
                                return true;
                        }
                        return false;
                    }
                });
                popup.show();
            }
        });
        
        holder.item.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mCategoryListener.onChooseCategory(groupPosition, -1, category);
            }
        });
        
        return convertView;
        
    }
    
    @Override
    public View getChildView(final int groupPosition, final int childPosition, boolean isLastChild,
              View convertView, final ViewGroup parent) {
        final CategoryChildViewHolder holder;
        
        this.groupPosition = groupPosition;
        this.childPosition = childPosition;
        
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) mContext
                      .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater
                      .inflate(R.layout.item_view_child_category, parent, false);
            holder = new CategoryChildViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (CategoryChildViewHolder) convertView.getTag();
        }
        
        final Category category = mOriginalCategories.get(groupPosition).getSubcategories()
                  .get(childPosition);
        final Category parentCategory = mOriginalCategories.get(groupPosition);
        
        int size = mOriginalCategories.get(groupPosition).getSubcategories().size();
        
        if (childPosition == size - 1) {
            holder.mImageViewChildIndicator_1.setVisibility(View.GONE);
            holder.mImageViewChildIndicator_2.setVisibility(View.VISIBLE);
        } else {
            holder.mImageViewChildIndicator_1.setVisibility(View.VISIBLE);
            holder.mImageViewChildIndicator_2.setVisibility(View.GONE);
        }
        
        holder.mTextViewNameCate.setText(category.getName());
        
        GlideApp.with(mContext).load(DrawableUtil.getDrawable(mContext, category.getIcon()))
                  .placeholder(R.drawable.folder_placeholder)
                  .error(R.drawable.folder_placeholder)
                  .into(holder.mImageViewIcon);
        
        if (isCategorySelected(category)) {
            holder.mImageViewSelected.setVisibility(View.VISIBLE);
        } else {
            holder.mImageViewSelected.setVisibility(View.GONE);
        }
        
        holder.mImageViewPopup.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                MenuPopupWithIcon popup = new MenuPopupWithIcon(mContext, holder.mLayoutIcon);
                popup.inflate(R.menu.menu_popup_select_category);
                popup.setMenuItemCLickListener(new OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.action_edit:
                                category.setParent(new Category(parentCategory.getId(),
                                          parentCategory.getName()));
                                mCategoryListener
                                          .onClickEdit(groupPosition, childPosition, category);
                                return true;
                            case R.id.action_remove:
                                mCategoryListener
                                          .onClickRemove(groupPosition, childPosition,
                                                    category);
                                return true;
                        }
                        return false;
                    }
                });
                popup.show();
            }
        });
        
        holder.item.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mCategoryListener.onChooseCategory(groupPosition, childPosition, category);
            }
        });
        
        return convertView;
    }
    
    @Override
    public int getChildType(int groupPosition, int childPosition) {
        return childPosition;
    }
    
    @Override
    public int getChildTypeCount() {
        return this.getChildrenCount(groupPosition) + 10;
    }
    
    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }
    
    public boolean isEmptyData() {
        return mOriginalCategories.isEmpty();
    }
    
    public void setCategorySelected(String currentCategoryId) {
        mCategoryIdSelected = currentCategoryId;
    }
    
    private boolean isCategorySelected(Category category) {
        return category.getId().equalsIgnoreCase(mCategoryIdSelected);
    }
    
    public List<Category> getData() {
        return this.mOriginalCategories;
    }
    
    public void setData(List<Category> categories) {
        
        if (mOriginalCategories == null) {
            mOriginalCategories = new ArrayList<>();
        }
        
        mOriginalCategories.clear();
        mOriginalCategories.addAll(categories);
    }
    
    interface CategoryListener {
        
        void onClickEdit(int groupPosition, int childPosition, Category category);
        
        void onClickRemove(int groupPosition, int childPosition, Category category);
        
        void onChooseCategory(int groupPosition, int childPosition, Category category);
    }
    
    static class CategoryParentViewHolder {
        
        View item;
        
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
        
        @BindView(R.id.image_view_popup)
        ImageView mImageViewPopup;
        
        @BindView(R.id.layout_icon)
        LinearLayout mLayoutIcon;
        
        CategoryParentViewHolder(View itemView) {
            item = itemView;
            ButterKnife.bind(this, itemView);
        }
    }
    
    static class CategoryChildViewHolder {
        
        View item;
        
        @BindView(R.id.text_view_name_cate)
        TextView mTextViewNameCate;
        
        @BindView(R.id.image_view_icon)
        ImageView mImageViewIcon;
        
        @BindView(R.id.child_indicator_1)
        ImageView mImageViewChildIndicator_1;
        
        @BindView(R.id.child_indicator_2)
        ImageView mImageViewChildIndicator_2;
        
        @BindView(R.id.image_view_selected)
        ImageView mImageViewSelected;
        
        @BindView(R.id.image_view_popup)
        ImageView mImageViewPopup;
        
        @BindView(R.id.layout_icon)
        LinearLayout mLayoutIcon;
        
        CategoryChildViewHolder(View itemView) {
            item = itemView;
            ButterKnife.bind(this, itemView);
            
        }
    }
}
