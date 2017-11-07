package com.vn.hcmute.team.cortana.mymoney.ui.statistics.transaction;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.TextView;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.vn.hcmute.team.cortana.mymoney.R;
import com.vn.hcmute.team.cortana.mymoney.model.Category;
import com.vn.hcmute.team.cortana.mymoney.model.Transaction;
import com.vn.hcmute.team.cortana.mymoney.ui.base.BaseFragment;
import com.vn.hcmute.team.cortana.mymoney.ui.base.ExpandableListEmptyAdapter;
import com.vn.hcmute.team.cortana.mymoney.ui.statistics.Objects.DateObjectTransaction;
import com.vn.hcmute.team.cortana.mymoney.ui.statistics.transaction.adapter.TransactionByCategoryAdapter;
import com.vn.hcmute.team.cortana.mymoney.ui.statistics.transaction.adapter.TransactionByCategoryAdapter.ClickChildView;
import com.vn.hcmute.team.cortana.mymoney.utils.DateUtil;
import com.vn.hcmute.team.cortana.mymoney.utils.NumberUtil;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

/**
 * Created by kunsubin on 11/7/2017.
 */

public class FragmentTransactionByCategory extends BaseFragment {
    
    private final String INCOME = "income";
    private final String EXPENSE = "expense";
    @BindView(R.id.list_transaction_by_category)
    ExpandableListView mExpandableListView;
    TextView txt_inflow_money;
    TextView txt_outflow_money;
    TextView txt_goal_money;
    
    private Context mContext;
    TransactionByCategoryAdapter.ClickChildView mClickChildView = new ClickChildView() {
        @Override
        public void onClickChild(DateObjectTransaction dateObjectTransaction) {
            Toast.makeText(mContext, dateObjectTransaction.getDate(), Toast.LENGTH_SHORT).show();
        }
    };
    private List<Transaction> mTransactions;
    private TransactionByCategoryAdapter mTransactionByCategoryAdapter;
    private ExpandableListEmptyAdapter mBaseEmptyAdapter;
    private List<Category> mCategories;
    private HashMap<Category, List<DateObjectTransaction>> mListDataChild;
    private ViewGroup mHeaderView;
    
    public FragmentTransactionByCategory(Context context, List<Transaction> list) {
        this.mContext = context;
        this.mTransactions = list;
    }
    
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_transaction_by_category;
    }
    
    @Override
    protected void initializeDagger() {
    
    }
    
    @Override
    protected void initializePresenter() {
    
    }
    
    @Override
    protected void initializeActionBar(View rootView) {
    
    }
    
    @Override
    protected void initialize() {
        init();
        initHeaderExpendableListView();
        setData();
    }
    
    private void init() {
        mCategories = new ArrayList<>();
        mListDataChild = new HashMap<>();
    }
    
    private void initHeaderExpendableListView() {
        LayoutInflater inflater = getActivity().getLayoutInflater();
        mHeaderView = (ViewGroup) inflater
                  .inflate(R.layout.item_header_transaction, mExpandableListView, false);
        txt_inflow_money = ButterKnife.findById(mHeaderView, R.id.txt_inflow_money);
        txt_outflow_money = ButterKnife.findById(mHeaderView, R.id.txt_outflow_money);
        txt_goal_money = ButterKnife.findById(mHeaderView, R.id.txt_goal_money);
        mExpandableListView.addHeaderView(mHeaderView);
    }
    
    private void setData() {
        if (mTransactions != null && !mTransactions.isEmpty()) {
            setDataAdapter();
            
            mTransactionByCategoryAdapter = new TransactionByCategoryAdapter(getActivity(),
                      mCategories,
                      mListDataChild);
            mTransactionByCategoryAdapter.setClickChildView(mClickChildView);
            setDataHeader();
            mExpandableListView.setAdapter(mTransactionByCategoryAdapter);
            mExpandableListView.setGroupIndicator(null);
            
        } else {
            mBaseEmptyAdapter = new ExpandableListEmptyAdapter(getActivity(),
                      getActivity().getString(R.string.txt_no_transaction));
            mExpandableListView.setAdapter(mBaseEmptyAdapter);
        }
    }
    
    private void setDataHeader() {
        String curSymbol = mTransactions.get(0).getWallet().getCurrencyUnit().getCurSymbol();
        double moneyIncome = 0;
        double moneyExpense = 0;
        for (Transaction transaction : mTransactions) {
            if (transaction.getType().equals(EXPENSE)) {
                moneyExpense -= Double.parseDouble(transaction.getAmount());
            } else {
                moneyIncome += Double.parseDouble(transaction.getAmount());
            }
        }
        double goalMoney = moneyIncome + moneyExpense;
        txt_inflow_money.setText(NumberUtil
                  .formatAmount(moneyIncome + "", curSymbol));
        txt_outflow_money.setText(NumberUtil
                  .formatAmount(moneyExpense + "", curSymbol));
        txt_goal_money.setText(NumberUtil
                  .formatAmount(goalMoney + "", curSymbol));
    }
    
    private void setDataAdapter() {
        for (Transaction transaction : mTransactions) {
            mCategories.add(transaction.getCategory());
        }
        mCategories = new ArrayList<Category>(new HashSet<Category>(mCategories));
        if (mCategories != null && !mCategories.isEmpty()) {
            
            for (Category category : mCategories) {
                List<DateObjectTransaction> list = new ArrayList<>();
                for (Transaction transaction : mTransactions) {
                    if (transaction.getCategory().equals(category)) {
                        list.add(convertDateObjectTransaction(transaction));
                    }
                }
                list = distinctObject(list);
                mListDataChild.put(category, list);
            }
        }
    }
    
    private List<DateObjectTransaction> distinctObject(List<DateObjectTransaction> list) {
        List<String> stringDates = new ArrayList<>();
        List<DateObjectTransaction> listObject = new ArrayList<>();
        for (DateObjectTransaction dateObjectTransaction : list) {
            stringDates.add(dateObjectTransaction.getDate());
        }
        stringDates = new ArrayList<String>(new HashSet<String>(stringDates));
        for (String date : stringDates) {
            for (DateObjectTransaction dateObjectTransaction : list) {
                if (date.equals(dateObjectTransaction.getDate())) {
                    listObject.add(dateObjectTransaction);
                    break;
                }
            }
        }
        for (DateObjectTransaction dateObject1 : listObject) {
            double money = 0;
            for (DateObjectTransaction dateObject2 : list) {
                if (dateObject1.getDate().equals(dateObject2.getDate())) {
                    money += Double.parseDouble(dateObject2.getMoney());
                }
            }
            dateObject1.setMoney(money + "");
        }
        return listObject;
    }
    
    private DateObjectTransaction convertDateObjectTransaction(Transaction transaction) {
        String millisecond = transaction.getDate_created();
        
        String dayOfWeek = DateUtil
                  .getDayOfWeek(getActivity(), DateUtil.getDayOfWeek(millisecond.trim()));
        String dayOfMonth = DateUtil.getDayOfMonth(millisecond.trim()) + "";
        String monthOfYear = DateUtil
                  .getMonthOfYear(getActivity(), DateUtil.getMonthOfYear(millisecond.trim()));
        String year = DateUtil.getYear(millisecond.trim()) + "";
        String date = DateUtil.convertTimeMillisToDate(millisecond);
        
        DateObjectTransaction dateObject = new DateObjectTransaction();
        dateObject.setDate(date);
        dateObject.setDayOfWeek(dayOfWeek);
        dateObject.setDayOfMonth(dayOfMonth);
        dateObject.setMonthOfYear(monthOfYear);
        dateObject.setYear(year);
        dateObject.setCurrencies(transaction.getWallet().getCurrencyUnit().getCurSymbol());
        dateObject.setMoney(transaction.getAmount());
        
        return dateObject;
    }
    
}
