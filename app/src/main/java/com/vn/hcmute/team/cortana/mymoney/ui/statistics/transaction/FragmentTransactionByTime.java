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
import com.vn.hcmute.team.cortana.mymoney.model.Transaction;
import com.vn.hcmute.team.cortana.mymoney.ui.base.BaseFragment;
import com.vn.hcmute.team.cortana.mymoney.ui.base.ExpandableListEmptyAdapter;
import com.vn.hcmute.team.cortana.mymoney.ui.statistics.Objects.DateObjectTransaction;
import com.vn.hcmute.team.cortana.mymoney.ui.statistics.transaction.adapter.TransactionByTimeAdapter;
import com.vn.hcmute.team.cortana.mymoney.utils.DateUtil;
import com.vn.hcmute.team.cortana.mymoney.utils.NumberUtil;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

/**
 * Created by kunsubin on 11/7/2017.
 */

public class FragmentTransactionByTime extends BaseFragment {
    
    private final String INCOME = "income";
    private final String EXPENSE = "expense";
    
    @BindView(R.id.list_transaction_by_time)
    ExpandableListView mExpandableListView;
    
    TextView txt_inflow_money;
    TextView txt_outflow_money;
    TextView txt_goal_money;
    
    
    private Context mContext;
    TransactionByTimeAdapter.ClickChildView mClickChildView = new TransactionByTimeAdapter.ClickChildView() {
        @Override
        public void onClickChild(Transaction transaction) {
            Toast.makeText(mContext, transaction.getAmount(), Toast.LENGTH_SHORT).show();
        }
    };
    private List<Transaction> mTransactions;
    private TransactionByTimeAdapter mTransactionByTimeAdapter;
    private List<DateObjectTransaction> mListDataHeader;
    private HashMap<DateObjectTransaction, List<Transaction>> mListDataChild;
    private ExpandableListEmptyAdapter mBaseEmptyAdapter;
    private List<String> mDateList;
    private ViewGroup mHeaderView;
    
    public FragmentTransactionByTime(Context context, List<Transaction> list) {
        this.mContext = context;
        this.mTransactions = list;
    }
    
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_transaction_by_time;
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
        setData();
    }
    
    public void init() {
        mDateList = new ArrayList<>();
        mListDataHeader = new ArrayList<>();
        mListDataChild = new HashMap<>();
        //add header expandable view
        initHeaderExpendableListView();
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
            //set data
            setDataAdapter();
            //set adapter
            mTransactionByTimeAdapter = new TransactionByTimeAdapter(getActivity(), mListDataHeader,
                      mListDataChild);
            mTransactionByTimeAdapter.setClickChildView(mClickChildView);
            setDataHeader();
            mExpandableListView.setAdapter(mTransactionByTimeAdapter);
            mExpandableListView.setGroupIndicator(null);
        } else {
            mExpandableListView.removeHeaderView(mHeaderView);
            mBaseEmptyAdapter = new ExpandableListEmptyAdapter(getActivity(),
                      getActivity().getString(R.string.txt_no_transaction));
            mExpandableListView.setAdapter(mBaseEmptyAdapter);
            mExpandableListView.setGroupIndicator(null);
        }
    }
    
    public void setDataAdapter() {
        for (Transaction transaction : mTransactions) {
            mDateList.add(DateUtil.convertTimeMillisToDate(transaction.getDate_created()));
        }
        mDateList = new ArrayList<String>(new HashSet<String>(mDateList));
        if (mDateList != null && !mDateList.isEmpty()) {
            for (String date : mDateList) {
                mListDataHeader.add(convertDateObjectTransaction(date));
            }
        }
        if (mListDataHeader != null && !mListDataHeader.isEmpty()) {
            for (DateObjectTransaction dateObjectTransaction : mListDataHeader) {
                setDataChild(dateObjectTransaction);
            }
        }
    }
    
    public DateObjectTransaction convertDateObjectTransaction(String date) {
        
        String[] arr = date.split("/");
        int day = Integer.parseInt(arr[0]);
        int month = Integer.parseInt(arr[1]);
        int year = Integer.parseInt(arr[2]);
        
        String millisecond = DateUtil.getLongAsDate(day, month, year) + "";
        
        String dayOfWeek = DateUtil
                  .getDayOfWeek(getActivity(), DateUtil.getDayOfWeek(millisecond.trim()));
        String dayOfMonth = DateUtil.getDayOfMonth(millisecond.trim()) + "";
        String monthOfYear = DateUtil
                  .getMonthOfYear(getActivity(), DateUtil.getMonthOfYear(millisecond.trim()));
        String yearT = DateUtil.getYear(millisecond.trim()) + "";
        
        double money = 0;
        for (Transaction transaction : mTransactions) {
            if (DateUtil.convertTimeMillisToDate(transaction.getDate_created()).equals(date)) {
                money += Double.parseDouble(transaction.getAmount());
            }
        }
        DateObjectTransaction dateObject = new DateObjectTransaction();
        dateObject.setDate(date);
        dateObject.setDayOfWeek(dayOfWeek);
        dateObject.setDayOfMonth(dayOfMonth);
        dateObject.setMonthOfYear(monthOfYear);
        dateObject.setYear(yearT);
        dateObject.setCurrencies(mTransactions.get(0).getWallet().getCurrencyUnit().getCurSymbol());
        dateObject.setMoney(money + "");
        
        return dateObject;
    }
    
    public void setDataChild(DateObjectTransaction dateObjectTransaction) {
        List<Transaction> list = new ArrayList<>();
        for (Transaction transaction : mTransactions) {
            if (DateUtil.convertTimeMillisToDate(transaction.getDate_created())
                      .equals(dateObjectTransaction.getDate())) {
                list.add(transaction);
            }
        }
        mListDataChild.put(dateObjectTransaction, list);
    }
    
    public void setDataHeader() {
        
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
}
