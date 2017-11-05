package com.vn.hcmute.team.cortana.mymoney.ui.statistics.fragment;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import android.widget.Toast;
import butterknife.BindView;
import com.vn.hcmute.team.cortana.mymoney.R;
import com.vn.hcmute.team.cortana.mymoney.model.Transaction;
import com.vn.hcmute.team.cortana.mymoney.model.Wallet;
import com.vn.hcmute.team.cortana.mymoney.ui.base.BaseFragment;
import com.vn.hcmute.team.cortana.mymoney.ui.statistics.Objects.ObjectByTime;

import com.vn.hcmute.team.cortana.mymoney.ui.statistics.adapters.TransactionByTimeAdapter;
import com.vn.hcmute.team.cortana.mymoney.ui.statistics.adapters.TransactionByTimeAdapter.ItemClickListener;
import com.vn.hcmute.team.cortana.mymoney.utils.DateUtil;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by kunsubin on 11/4/2017.
 */

public class FragmentByTime extends BaseFragment {
    
    public static final int ID_EXPENSE = 1;
    public static final int ID_INCOME = 2;
    public static final int ID_NETINCOME = 3;
    public static final String TYPE_EXPENSE = "expense";
    public static final String TYPE_INCOME = "income";
    
    @BindView(R.id.recycler_view_by_time)
    RecyclerView mRecyclerView;
    
    private List<Transaction> mTransactions;
    private int mIdCategory;
    private List<ObjectByTime> mObjectByTimes;
    private List<String> mStringDates;
    private TransactionByTimeAdapter mTransactionByTimeAdapter;
    private Wallet mWallet;
    
    public FragmentByTime(List<Transaction> transactions, int idCategory,Wallet wallet,List<String> listDates) {
        this.mTransactions = transactions;
        this.mIdCategory = idCategory;
        this.mWallet=wallet;
        this.mStringDates=listDates;
    }
    
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_statistics_by_time;
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
        mObjectByTimes = new ArrayList<>();
        mRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 1));
        getData();
        showData();
        setHeaderBarChart();
    }
    
    public void getData() {
        switch (mIdCategory) {
            case ID_EXPENSE:
                getDataExpense();
                break;
            case ID_INCOME:
                getDataInCome();
                break;
            case ID_NETINCOME:
                getDataNetInCome();
                break;
        }
    }
    
    public void getDataExpense() {
        mObjectByTimes.clear();
        
        if (mStringDates != null && !mStringDates.isEmpty()) {
            for (String string : mStringDates) {
                
                double sumMoney = 0;
                ObjectByTime objectByTime = new ObjectByTime();
                List<Transaction> list = new ArrayList<>();
                
                for (Transaction transaction : mTransactions) {
                    if (DateUtil.convertTimeMillisToMonthAnhYear(transaction.getDate_created())
                              .equals(string)&&transaction.getType().equals(TYPE_EXPENSE)) {
                        list.add(transaction);
                        sumMoney += Double.parseDouble(transaction.getAmount());
                    }
                }
                objectByTime.setDate(string);
                objectByTime.setTransactionList(list);
                objectByTime.setMoneyExpense(sumMoney + "");
                
                mObjectByTimes.add(objectByTime);
            }
        }
     
    }
    
    public void getDataInCome() {
        mObjectByTimes.clear();
        
        if (mStringDates != null && !mStringDates.isEmpty()) {
            for (String string : mStringDates) {
                
                double sumMoney = 0;
                ObjectByTime objectByTime = new ObjectByTime();
                List<Transaction> list = new ArrayList<>();
                
                for (Transaction transaction : mTransactions) {
                    if (DateUtil.convertTimeMillisToMonthAnhYear(transaction.getDate_created())
                              .equals(string)&&transaction.getType().equals(TYPE_INCOME)) {
                        list.add(transaction);
                        sumMoney += Double.parseDouble(transaction.getAmount());
                    }
                }
                objectByTime.setDate(string);
                objectByTime.setTransactionList(list);
                objectByTime.setMoneyIncome(sumMoney + "");
                
                mObjectByTimes.add(objectByTime);
            }
        }
    }
    
    public void getDataNetInCome() {
        mObjectByTimes.clear();

        
        if (mStringDates != null && !mStringDates.isEmpty()) {
            for (String string : mStringDates) {
                
                double sumMoneyExpense = 0;
                double sumMoneyIncome = 0;
                ObjectByTime objectByTime = new ObjectByTime();
                List<Transaction> list = new ArrayList<>();
                
                for (Transaction transaction : mTransactions) {
                    if (DateUtil.convertTimeMillisToMonthAnhYear(transaction.getDate_created())
                              .equals(string)) {
                        list.add(transaction);
                        if (transaction.getType().equals(TYPE_EXPENSE)) {
                            sumMoneyExpense += Double.parseDouble(transaction.getAmount());
                        } else {
                            sumMoneyIncome += Double.parseDouble(transaction.getAmount());
                        }
                        
                    }
                }
                objectByTime.setDate(string);
                objectByTime.setTransactionList(list);
                objectByTime.setMoneyExpense(sumMoneyExpense + "");
                objectByTime.setMoneyIncome(sumMoneyIncome + "");
                
                mObjectByTimes.add(objectByTime);
            }
        }
    }
    
    TransactionByTimeAdapter.ItemClickListener mItemClickListener = new ItemClickListener() {
        @Override
        public void onItemClick(ObjectByTime objectByTime) {
            Toast.makeText(getActivity(), objectByTime.getDate(), Toast.LENGTH_SHORT).show();
        }
    };
    
    public void showData() {
        if (mObjectByTimes != null && !mObjectByTimes.isEmpty()) {
            mTransactionByTimeAdapter = new TransactionByTimeAdapter(
                      getActivity(), mIdCategory, mObjectByTimes,mWallet);
            mTransactionByTimeAdapter.setClickListener(mItemClickListener);
            mRecyclerView.setAdapter(mTransactionByTimeAdapter);
        }
    }
    public void setHeaderBarChart(){
    
    }
}
