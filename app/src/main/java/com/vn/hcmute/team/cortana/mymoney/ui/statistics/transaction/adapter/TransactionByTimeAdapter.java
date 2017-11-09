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
import com.vn.hcmute.team.cortana.mymoney.model.Transaction;
import com.vn.hcmute.team.cortana.mymoney.ui.statistics.Objects.DateObjectTransaction;
import com.vn.hcmute.team.cortana.mymoney.utils.DrawableUtil;
import com.vn.hcmute.team.cortana.mymoney.utils.NumberUtil;
import java.util.HashMap;
import java.util.List;

/**
 * Created by kunsubin on 11/7/2017.
 */

public class TransactionByTimeAdapter extends BaseExpandableListAdapter {
    
    private final String EXPENSE = "expense";
    private final String INCOME = "income";
    
    private Context mContext;
    private List<DateObjectTransaction> mListDataHeader;
    private HashMap<DateObjectTransaction, List<Transaction>> mListDataChild;
    private ClickChildView mClickChildView;
    
    
    public TransactionByTimeAdapter(Context context, List<DateObjectTransaction> listDataHeader,
              HashMap<DateObjectTransaction, List<Transaction>> listChildData) {
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
        
        DateObjectTransaction headerTitle = (DateObjectTransaction) getGroup(groupPosition);
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this.mContext
                      .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.item_parent_transaction, null);
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
        
        final Transaction transaction = (Transaction) getChild(groupPosition, childPosition);
        
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this.mContext
                      .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.item_children_transaction, null);
        }
        
        ViewChildHoder viewChildHoder = new ViewChildHoder(convertView);
        viewChildHoder.bindView(transaction);
        
        convertView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mClickChildView.onClickChild(transaction);
            }
        });
        
        return convertView;
    }
    
    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }
    
    public void setClickChildView(ClickChildView clickChildView) {
        mClickChildView = clickChildView;
    }
    
    public List<Transaction> getValuesListTransaction(DateObjectTransaction dateObjectTransaction) {
        return mListDataChild.get(dateObjectTransaction);
    }
    
    public interface ClickChildView {
        
        void onClickChild(Transaction transaction);
    }
    
    public class ViewGroupHoder {
        
        @BindView(R.id.txt_mon)
        TextView txt_mon;
        @BindView(R.id.txt_day)
        TextView txt_day;
        @BindView(R.id.txt_month_year)
        TextView txt_month_year;
        @BindView(R.id.txt_money)
        TextView txt_money;
        
        public ViewGroupHoder(View view) {
            ButterKnife.bind(this, view);
        }
        
        public void bindView(DateObjectTransaction dateObjectTransaction) {
            txt_mon.setText(dateObjectTransaction.getDayOfWeek());
            txt_day.setText(dateObjectTransaction.getDayOfMonth());
            txt_month_year.setText(dateObjectTransaction.getMonthOfYear() + " " +
                                   dateObjectTransaction.getYear());
            
            double value = 0;
            List<Transaction> list = getValuesListTransaction(dateObjectTransaction);
            if (list != null && !list.isEmpty()) {
                for (Transaction transaction : list) {
                    if (transaction.getType().equals(EXPENSE)) {
                        value -= Double.parseDouble(transaction.getAmount());
                    } else {
                        value += Double.parseDouble(transaction.getAmount());
                    }
                }
            }
            txt_money.setText(
                      NumberUtil.formatAmount(value + "", dateObjectTransaction.getCurrencies()));
            
        }
    }
    
    public class ViewChildHoder {
        
        @BindView(R.id.image_category)
        ImageView image_category;
        @BindView(R.id.txt_category_name)
        TextView txt_category_name;
        @BindView(R.id.txt_money)
        TextView txt_money;
        @BindView(R.id.txt_note)
        TextView txt_note;
        
        public ViewChildHoder(View view) {
            ButterKnife.bind(this, view);
        }
        
        private void bindView(Transaction transaction) {
            txt_category_name.setText(transaction.getCategory().getName());
            txt_note.setText(transaction.getNote());
            GlideApp.with(mContext)
                      .load(DrawableUtil.getDrawable(mContext, transaction.getCategory().getIcon()))
                      .placeholder(R.drawable.folder_placeholder)
                      .error(R.drawable.folder_placeholder)
                      .dontAnimate()
                      .into(image_category);
            
            if (transaction.getType().equals(EXPENSE)) {
                txt_money.setText("-" + NumberUtil.formatAmount(transaction.getAmount(),
                          transaction.getWallet().getCurrencyUnit().getCurSymbol()));
                txt_money.setTextColor(ContextCompat.getColor(mContext, R.color.color_red));
                return;
            }
            if (transaction.getType().equals(INCOME)) {
                txt_money.setText(NumberUtil.formatAmount(transaction.getAmount(),
                          transaction.getWallet().getCurrencyUnit().getCurSymbol()));
                txt_money.setTextColor(ContextCompat.getColor(mContext, R.color.green));
                return;
            }
        }
    }
}
