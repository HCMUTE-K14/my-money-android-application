package com.vn.hcmute.team.cortana.mymoney.ui.statistics.transaction.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.vn.hcmute.team.cortana.mymoney.R;
import com.vn.hcmute.team.cortana.mymoney.di.module.GlideApp;
import com.vn.hcmute.team.cortana.mymoney.model.Category;
import com.vn.hcmute.team.cortana.mymoney.ui.statistics.Objects.DateObjectTransaction;
import com.vn.hcmute.team.cortana.mymoney.utils.DrawableUtil;
import com.vn.hcmute.team.cortana.mymoney.utils.NumberUtil;
import java.util.HashMap;
import java.util.List;

/**
 * Created by kunsubin on 11/7/2017.
 */

public class TransactionByCategoryAdapter extends BaseExpandableListAdapter {
    
    private final String EXPENSE = "expense";
    private final String INCOME = "income";
    
    private Context mContext;
    private List<Category> mListDataHeader;
    private HashMap<Category, List<DateObjectTransaction>> mListDataChild;
    private ClickChildView mClickChildView;
    
    public TransactionByCategoryAdapter(Context context, List<Category> listDataHeader,
              HashMap<Category, List<DateObjectTransaction>> listChildData) {
        this.mContext = context;
        this.mListDataHeader = listDataHeader;
        this.mListDataChild = listChildData;
    }
    
    @Override
    public int getGroupCount() {
        return mListDataHeader.size();
    }
    
    @Override
    public int getChildrenCount(int groupPosition) {
        return mListDataChild.get(mListDataHeader.get(groupPosition)).size();
    }
    
    @Override
    public Object getGroup(int groupPosition) {
        return mListDataHeader.get(groupPosition);
    }
    
    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return mListDataChild.get(mListDataHeader.get(groupPosition)).get(childPosition);
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
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView,
              ViewGroup parent) {
        Category headerTitle = (Category) getGroup(groupPosition);
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this.mContext
                      .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.item_transaction_view_category, null);
        }
        ViewGroupHoder viewGroupHoder = new ViewGroupHoder(convertView);
        viewGroupHoder.bindView(headerTitle);
        
        ExpandableListView mExpandableListView = (ExpandableListView) parent;
        mExpandableListView.expandGroup(groupPosition);
        
        return convertView;
    }
    
    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild,
              View convertView, ViewGroup parent) {
        final DateObjectTransaction dateObjectTransaction = (DateObjectTransaction) getChild(
                  groupPosition, childPosition);
        
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this.mContext
                      .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.item_parent_transaction, null);
        }
        
        ViewChildHoder viewChildHoder = new ViewChildHoder(convertView);
        viewChildHoder.bindView((Category) getGroup(groupPosition), dateObjectTransaction);
        
        convertView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mClickChildView.onClickChild(dateObjectTransaction);
            }
        });
        
        return convertView;
    }
    
    @Override
    public boolean isChildSelectable(int i, int i1) {
        return false;
    }
    
    public void setClickChildView(ClickChildView clickChildView) {
        mClickChildView = clickChildView;
    }
    
    public List<DateObjectTransaction> getDateObjectByCategory(Category category) {
        return mListDataChild.get(category);
    }
    
    public interface ClickChildView {
        
        void onClickChild(DateObjectTransaction dateObjectTransaction);
    }
    
    public class ViewGroupHoder {
        
        @BindView(R.id.image_icon_category)
        ImageView image_icon_category;
        @BindView(R.id.txt_category)
        TextView txt_category;
        @BindView(R.id.txt_money)
        TextView txt_money;
        
        public ViewGroupHoder(View view) {
            ButterKnife.bind(this, view);
        }
        
        public void bindView(Category category) {
            txt_category.setText(category.getName());
            GlideApp.with(mContext)
                      .load(DrawableUtil.getDrawable(mContext, category.getIcon()))
                      .placeholder(R.drawable.folder_placeholder)
                      .error(R.drawable.folder_placeholder)
                      .dontAnimate()
                      .into(image_icon_category);
            double money = 0;
            List<DateObjectTransaction> list = getDateObjectByCategory(category);
            for (DateObjectTransaction dateObjectTransaction : list) {
                if (category.getType().equals(EXPENSE)) {
                    money -= Double.parseDouble(dateObjectTransaction.getMoney());
                } else {
                    money += Double.parseDouble(dateObjectTransaction.getMoney());
                }
            }
            String currency = list != null && !list.isEmpty() ? list.get(0).getCurrencies() : "";
            txt_money.setText(NumberUtil.formatAmount(money + "", currency));
            
        }
    }
    
    public class ViewChildHoder {
        
        @BindView(R.id.txt_mon)
        TextView txt_mon;
        @BindView(R.id.txt_day)
        TextView txt_day;
        @BindView(R.id.txt_month_year)
        TextView txt_month_year;
        @BindView(R.id.txt_money)
        TextView txt_money;
        
        public ViewChildHoder(View view) {
            ButterKnife.bind(this, view);
        }
        
        private void bindView(Category category, DateObjectTransaction dateObjectTransaction) {
            txt_mon.setText(dateObjectTransaction.getDayOfWeek());
            txt_day.setText(dateObjectTransaction.getDayOfMonth());
            txt_month_year.setText(dateObjectTransaction.getMonthOfYear() + " " +
                                   dateObjectTransaction.getYear());
            if (category.getType().equals(EXPENSE)) {
                txt_money.setText("-" + NumberUtil.formatAmount(dateObjectTransaction.getMoney(),
                          dateObjectTransaction.getCurrencies()));
                txt_money.setTextColor(ContextCompat.getColor(mContext, R.color.color_red));
            } else {
                txt_money.setText(NumberUtil.formatAmount(dateObjectTransaction.getMoney(),
                          dateObjectTransaction.getCurrencies()));
                txt_money.setTextColor(ContextCompat.getColor(mContext, R.color.green));
            }
        }
    }
}
