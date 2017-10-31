package com.vn.hcmute.team.cortana.mymoney.ui.debts;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.vn.hcmute.team.cortana.mymoney.R;
import com.vn.hcmute.team.cortana.mymoney.data.cache.PreferencesHelper;
import com.vn.hcmute.team.cortana.mymoney.model.DebtLoan;
import com.vn.hcmute.team.cortana.mymoney.model.Person;
import com.vn.hcmute.team.cortana.mymoney.model.Wallet;
import com.vn.hcmute.team.cortana.mymoney.ui.view.RoundedLetterView;
import com.vn.hcmute.team.cortana.mymoney.utils.ColorUtil;
import com.vn.hcmute.team.cortana.mymoney.utils.NumberUtil;
import com.vn.hcmute.team.cortana.mymoney.utils.TextUtil;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by infamouSs on 10/21/17.
 */

public class DebtLoanAdapter extends BaseExpandableListAdapter {
    
    public static final String TAG = DebtLoanAdapter.class.getSimpleName();
    
    private static final int TYPE_DEBT = 0;
    private static final int TYPE_LOAN = 1;
    
    private Map<String, List<DebtLoan>> mData;
    private Context mContext;
    private int mType;
    
    private Wallet mCurrentWallet;
    
    private int groupPosition;
    private int childPosition;
    
    private DebtLoanListener mDebtLoanListener;
    
    public DebtLoanAdapter(Context context, String type, List<DebtLoan> data,
              DebtLoanListener listener) {
        this.mContext = context;
        if (data != null && !data.isEmpty()) {
            mData = buildDataForAdapter(data);
        }
        if (type.equals(DebtsLoanFragmentByType.TYPE_DEBT)) {
            mType = 0;
        } else if (type.equals(DebtsLoanFragmentByType.TYPE_LOAN)) {
            mType = 1;
        }
        
        this.mCurrentWallet = PreferencesHelper.getInstance(mContext).getCurrentWallet();
        
        this.mDebtLoanListener = listener;
    }
    
    
    @Override
    public int getGroupCount() {
        return 2;
    }
    
    @Override
    public int getChildrenCount(int groupPosition) {
        if (groupPosition == 0) {
            return mData.get("not_yet").size();
        } else if (groupPosition == 1) {
            return mData.get("done").size();
        } else {
            return 0;
        }
    }
    
    @Override
    public Object getGroup(int groupPosition) {
        if (groupPosition == 0) {
            return mData.get("not_yet");
        } else if (groupPosition == 1) {
            return mData.get("done");
        } else {
            return null;
        }
    }
    
    @Override
    public Object getChild(int groupPosition, int childPosition) {
        if (groupPosition == 0) {
            return mData.get("not_yet").get(childPosition);
        } else if (groupPosition == 1) {
            return mData.get("done").get(childPosition);
        } else {
            return null;
        }
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
        final DebtLoanHeaderViewHolder holder;
        
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) mContext
                      .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater
                      .inflate(R.layout.item_recycler_view_header_debt_loan, parent, false);
            
            holder = new DebtLoanHeaderViewHolder(convertView);
            
            convertView.setTag(holder);
        } else {
            holder = (DebtLoanHeaderViewHolder) convertView.getTag();
        }
        
        String symbolCurrency = mCurrentWallet.getCurrencyUnit().getCurSymbol();
        if (mType == TYPE_DEBT && groupPosition == 0) {
            if (!mData.get("not_yet").isEmpty()) {
                holder.mTextViewHeaderTitle.setText(R.string.txt_not_yet_paid);
                String total = totalAmount(this.mData.get("not_yet"));
                holder.mTextViewHeaderMoney
                          .setTextColor(ContextCompat.getColor(mContext, R.color.color_31ADE9));
                
                holder.mTextViewHeaderMoney.setText(NumberUtil.formatAmount(total, symbolCurrency));
            }
            
        } else if (mType == TYPE_DEBT && groupPosition == 1) {
            if (!mData.get("done").isEmpty()) {
                holder.mTextViewHeaderTitle.setText(R.string.txt_paid_uppercase);
                String total = totalAmount(this.mData.get("done"));
                holder.mTextViewHeaderMoney
                          .setTextColor(ContextCompat.getColor(mContext, R.color.color_31ADE9));
                
                holder.mTextViewHeaderMoney.setText(NumberUtil.formatAmount(total, symbolCurrency));
            }
        } else if (mType == TYPE_LOAN && groupPosition == 0) {
            if (!mData.get("not_yet").isEmpty()) {
                holder.mTextViewHeaderTitle.setText(R.string.txt_not_yet_received);
                String total = totalAmount(this.mData.get("not_yet"));
                holder.mTextViewHeaderMoney
                          .setTextColor(ContextCompat.getColor(mContext, R.color.color_E7252C));
                
                holder.mTextViewHeaderMoney.setText(NumberUtil.formatAmount(total, symbolCurrency));
            }
        } else if (mType == TYPE_LOAN && groupPosition == 1) {
            if (!mData.get("done").isEmpty()) {
                holder.mTextViewHeaderTitle.setText(R.string.txt_received);
                String total = totalAmount(this.mData.get("done"));
                holder.mTextViewHeaderMoney
                          .setTextColor(ContextCompat.getColor(mContext, R.color.color_E7252C));
                
                holder.mTextViewHeaderMoney.setText(NumberUtil.formatAmount(total, symbolCurrency));
            }
        }
        
        return convertView;
    }
    
    private String totalAmount(List<DebtLoan> data) {
        double total = 0;
        if (data == null) {
            return "0";
        }
        for (DebtLoan item : data) {
            total += Double.valueOf(item.getTransaction().getAmount());
        }
        return String.valueOf(total);
    }
    
    @Override
    public View getChildView(final int groupPosition, final int childPosition, boolean isLastChild,
              View convertView, ViewGroup parent) {
        this.groupPosition = groupPosition;
        this.childPosition = childPosition;
        
        final DebtLoanItemViewHolder holder;
        
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) mContext
                      .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater
                      .inflate(R.layout.item_recycler_view_debt_loan, parent, false);
            
            holder = new DebtLoanItemViewHolder(convertView);
            
            convertView.setTag(holder);
        } else {
            holder = (DebtLoanItemViewHolder) convertView.getTag();
        }
        
        final DebtLoan item;
        String symbolCurrency = mCurrentWallet.getCurrencyUnit().getCurSymbol();
        if (mType == TYPE_DEBT && groupPosition == 0) {
            item = mData.get("not_yet").get(childPosition);
            holder.mTextViewStatusMoney_1
                      .setTextColor(ContextCompat.getColor(mContext, R.color.color_31ADE9));
            
            holder.mTextViewStatusMoney_1.setText(mContext.getString(R.string.txt_you_owe,
                      NumberUtil.formatAmount(item.getTransaction().getAmount(), symbolCurrency)));
            
        } else if (mType == TYPE_DEBT && groupPosition == 1) {
            item = mData.get("done").get(childPosition);
            String amount =
                      NumberUtil.formatAmount(item.getTransaction().getAmount(), symbolCurrency);
            holder.mTextViewStatusMoney_1
                      .setTextColor(ContextCompat.getColor(mContext, R.color.color_31ADE9));
            
            holder.mTextViewStatusMoney_1.setText(TextUtil.buildDelString(amount));
        } else if (mType == TYPE_LOAN && groupPosition == 0) {
            item = mData.get("not_yet").get(childPosition);
            holder.mTextViewStatusMoney_1
                      .setTextColor(ContextCompat.getColor(mContext, R.color.color_E7252C));
            holder.mTextViewStatusMoney_1.setText(mContext.getString(R.string.txt_owes_you,
                      NumberUtil.formatAmount(item.getTransaction().getAmount(), symbolCurrency)));
        } else if (mType == TYPE_LOAN && groupPosition == 1) {
            item = mData.get("done").get(childPosition);
            String amount =
                      NumberUtil.formatAmount(item.getTransaction().getAmount(), symbolCurrency);
            holder.mTextViewStatusMoney_1
                      .setTextColor(ContextCompat.getColor(mContext, R.color.color_E7252C));
            holder.mTextViewStatusMoney_1.setText(TextUtil.buildDelString(amount));
        } else {
            item = null;
        }
        
        initPersonView(item, holder);
        
        holder.view.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mDebtLoanListener.onClick(item, groupPosition, childPosition);
            }
        });
        
        return convertView;
    }
    
    private Map<String, List<DebtLoan>> buildDataForAdapter(List<DebtLoan> data) {
        Map<String, List<DebtLoan>> result = new HashMap<>();
        List<DebtLoan> done = new ArrayList<>();
        List<DebtLoan> notYet = new ArrayList<>();
        for (DebtLoan item : data) {
            if (item.getStatus() == 0) {
                notYet.add(item);
            } else if (item.getStatus() == 1) {
                done.add(item);
            }
        }
        
        result.put("not_yet", notYet);
        result.put("done", done);
        
        return result;
    }
    
    
    private void initPersonView(DebtLoan debtLoan, DebtLoanItemViewHolder holder) {
        if (debtLoan == null) {
            return;
        }
        
        if (debtLoan.getTransaction().getPerson() == null ||
            debtLoan.getTransaction().getPerson().isEmpty()) {
            return;
        }
        
        Person person = debtLoan.getTransaction().getPerson().get(0);
        if (person == null) {
            return;
        }
        int color = Color.parseColor(ColorUtil.getRandomColor());
        if (color == 0) {
            color = Color.parseColor(ColorUtil.getRandomColor());
        }
        if (color == Color.WHITE) {
            color = Color.BLACK;
        }
        holder.mRoundedLetterView.setBackgroundColor(color);
        
        if (!TextUtils.isEmpty(person.getName())) {
            final String startLetter = String.valueOf(person.getName().charAt(0)).toUpperCase();
            holder.mRoundedLetterView.setTitleText(startLetter);
        }
        
        holder.mTextViewNamePerson.setText(person.getName());
        holder.mTextViewDescribe
                  .setText(TextUtil.isEmpty(person.getDescribe()) ? "" : person.getDescribe());
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
    public int getGroupTypeCount() {
        return getGroupCount() == 0 ? 1 : getGroupCount();
    }
    
    @Override
    public int getGroupType(int groupPosition) {
        return groupPosition;
    }
    
    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }
    
    public void setData(List<DebtLoan> list) {
        if (mData != null) {
            mData.clear();
        }
        mData = buildDataForAdapter(list);
        notifyDataSetChanged();
    }
    
    static class DebtLoanItemViewHolder {
        
        View view;
        
        @BindView(R.id.rounded_letter)
        RoundedLetterView mRoundedLetterView;
        
        @BindView(R.id.txt_name)
        TextView mTextViewNamePerson;
        
        @BindView(R.id.txt_describe)
        TextView mTextViewDescribe;
        
        @BindView(R.id.txt_status_money_1)
        TextView mTextViewStatusMoney_1;
        
        @BindView(R.id.txt_status_money_2)
        TextView mTextViewStatusMoney_2;
        
        public DebtLoanItemViewHolder(View itemView) {
            this.view = itemView;
            ButterKnife.bind(this, itemView);
        }
    }
    
    static class DebtLoanHeaderViewHolder {
        
        View view;
        
        @BindView(R.id.txt_header_title)
        TextView mTextViewHeaderTitle;
        
        @BindView(R.id.txt_header_money)
        TextView mTextViewHeaderMoney;
        
        public DebtLoanHeaderViewHolder(View itemView) {
            this.view = itemView;
            ButterKnife.bind(this, itemView);
        }
    }
}
