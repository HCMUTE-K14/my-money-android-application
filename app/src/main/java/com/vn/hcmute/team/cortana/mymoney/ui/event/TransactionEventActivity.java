package com.vn.hcmute.team.cortana.mymoney.ui.event;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.vn.hcmute.team.cortana.mymoney.MyMoneyApplication;
import com.vn.hcmute.team.cortana.mymoney.R;
import com.vn.hcmute.team.cortana.mymoney.di.component.ApplicationComponent;
import com.vn.hcmute.team.cortana.mymoney.di.component.DaggerTransactionComponent;
import com.vn.hcmute.team.cortana.mymoney.di.component.TransactionComponent;
import com.vn.hcmute.team.cortana.mymoney.di.module.ActivityModule;
import com.vn.hcmute.team.cortana.mymoney.di.module.TransactionModule;
import com.vn.hcmute.team.cortana.mymoney.model.Event;
import com.vn.hcmute.team.cortana.mymoney.model.Transaction;
import com.vn.hcmute.team.cortana.mymoney.ui.base.BaseActivity;
import com.vn.hcmute.team.cortana.mymoney.ui.base.ExpandableListEmptyAdapter;
import com.vn.hcmute.team.cortana.mymoney.ui.event.adapter.DateObjectTransaction;
import com.vn.hcmute.team.cortana.mymoney.ui.event.adapter.TransactionEventAdapter;
import com.vn.hcmute.team.cortana.mymoney.ui.transaction.TransactionContract;
import com.vn.hcmute.team.cortana.mymoney.ui.transaction.TransactionPresenter;
import com.vn.hcmute.team.cortana.mymoney.utils.DateUtil;
import com.vn.hcmute.team.cortana.mymoney.utils.NumberUtil;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import javax.inject.Inject;

/**
 * Created by kunsubin on 10/17/2017.
 */

public class TransactionEventActivity extends BaseActivity implements TransactionContract.View,
                                                                      TransactionEventAdapter.ClickChildView {
    
    @BindView(R.id.list_transaction_event)
    ExpandableListView mExpandableListView;
    @BindView(R.id.progress_bar_transaction)
    ProgressBar mProgressBar;
    @BindView(R.id.swipeRefresh)
    SwipeRefreshLayout mSwipeRefreshLayout;
    
    TextView mGoalMoney;
    
    private ExpandableListEmptyAdapter mBaseEmptyAdapter;
    private List<Transaction> mTransactionList;
    private Event mEvent;
    private TransactionEventAdapter mTransactionEventAdapter;
    
    private List<DateObjectTransaction> mListDataHeader;
    private HashMap<DateObjectTransaction, List<Transaction>> mListDataChild;
    
    private List<String> mDateList;

    
    
    @Inject
    TransactionPresenter mTransactionPresenter;
    
    @Override
    public int getLayoutId() {
        return R.layout.activity_list_transaction_event;
    }
    
    @Override
    protected void initializeDagger() {
        ApplicationComponent applicationComponent = ((MyMoneyApplication) getApplication())
                  .getAppComponent();
        TransactionComponent transactionComponent = DaggerTransactionComponent.builder()
                  .applicationComponent(applicationComponent)
                  .activityModule(new ActivityModule(this))
                  .transactionModule(new TransactionModule())
                  .build();
        
        transactionComponent.inject(this);
    }
    
    @Override
    protected void initializePresenter() {
        mPresenter = mTransactionPresenter;
        mTransactionPresenter.setView(this);
    }
    
    @Override
    protected void initializeActionBar(View rootView) {
        
    }
    
    @Override
    protected void initialize() {
        mTransactionList = new ArrayList<>();
        mDateList = new ArrayList<>();
        mListDataHeader = new ArrayList<>();
        mListDataChild = new HashMap<>();
        getData();
        //add header
        LayoutInflater inflater = getLayoutInflater();
        ViewGroup header = (ViewGroup) inflater
                  .inflate(R.layout.item_header_list_view_transaction, mExpandableListView, false);
        
        mGoalMoney= ButterKnife.findById(header, R.id.txt_goal_money);
        mExpandableListView.addHeaderView(header);
        //get list transaction by event
        mTransactionPresenter.getTransactionByEvent(mEvent.getEventid());
        mSwipeRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                mTransactionList.clear();
                mDateList.clear();
                mListDataHeader.clear();
                mListDataChild.clear();
                if (mTransactionEventAdapter != null) {
                    mTransactionEventAdapter.notifyDataSetChanged();
                }
                mTransactionPresenter.getTransactionByEvent(mEvent.getEventid());
            }
        });
        
    }
    
    @Override
    public void showAllListTransaction(List<Transaction> list) {
        //List transaction event id
        
        mTransactionList = list;
        if (mTransactionList != null && !mTransactionList.isEmpty()) {
            //set data
            setDataAdapter();
            //set adapter
            mTransactionEventAdapter = new TransactionEventAdapter(this, mListDataHeader,
                      mListDataChild);
            mTransactionEventAdapter.setClickChildView(this);
            
            setGoalMoneyHeader();
            
            mExpandableListView.setAdapter(mTransactionEventAdapter);
            mExpandableListView.setGroupIndicator(null);
        } else {
            mBaseEmptyAdapter = new ExpandableListEmptyAdapter(this,
                      this.getString(R.string.txt_no_transaction));
            mExpandableListView.setAdapter(mBaseEmptyAdapter);
        }
        if (mSwipeRefreshLayout.isRefreshing()) {
            mSwipeRefreshLayout.setRefreshing(false);
        }
        
        
    }
    
    
    @Override
    public void onFailure(String message) {
        mBaseEmptyAdapter = new ExpandableListEmptyAdapter(this, message);
        mExpandableListView.setAdapter(mBaseEmptyAdapter);
        if (mSwipeRefreshLayout.isRefreshing()) {
            mSwipeRefreshLayout.setRefreshing(false);
        }
    }
    
    @Override
    public void loading(boolean isLoading) {
        mProgressBar.setVisibility(isLoading ? View.VISIBLE : View.GONE);
    }
    
    @Override
    public void onClickChild(Transaction transaction) {
        Toast.makeText(this, transaction.getCategory().getName(), Toast.LENGTH_LONG).show();
    }
    
    /*Area onClick*/
    @OnClick(R.id.ic_cancel)
    public void onClickCancel(View view) {
        finish();
    }
    
    /*Area funcction*/
    public void getData() {
        mEvent = new Event();
        Intent intent = this.getIntent();
        if (intent.getParcelableExtra("event") != null) {
            mEvent = intent.getParcelableExtra("event");
        }
    }
    
    public void setDataAdapter() {
        for (Transaction transaction : mTransactionList) {
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
        
        String dayOfWeek = DateUtil.getDayOfWeek(this,DateUtil.getDayOfWeek(millisecond.trim()));
        String dayOfMonth = DateUtil.getDayOfMonth(millisecond.trim()) + "";
        String monthOfYear = DateUtil.getMonthOfYear(this,DateUtil.getMonthOfYear(millisecond.trim()));
        String yearT = DateUtil.getYear(millisecond.trim()) + "";
        
        DateObjectTransaction dateObjectTransaction = new DateObjectTransaction();
        dateObjectTransaction.setDate(date);
        dateObjectTransaction.setDayOfWeek(dayOfWeek);
        dateObjectTransaction.setDayOfMonth(dayOfMonth);
        dateObjectTransaction.setMonthOfYear(monthOfYear);
        dateObjectTransaction.setYear(yearT);
        dateObjectTransaction.setCurrencies(mEvent.getCurrencies().getCurSymbol());
        
        double money = 0;
        for (Transaction transaction : mTransactionList) {
            if (DateUtil.convertTimeMillisToDate(transaction.getDate_created()).equals(date)) {
                money += NumberUtil.exchangeMoney(this, transaction.getAmount(),
                          transaction.getWallet().getCurrencyUnit().getCurCode(),
                          mEvent.getCurrencies().getCurCode());
            }
        }
        dateObjectTransaction.setMoney(money + "");
        
        return dateObjectTransaction;
    }
    
    public void setDataChild(DateObjectTransaction dateObjectTransaction) {
        List<Transaction> list = new ArrayList<>();
        for (Transaction transaction : mTransactionList) {
            if (DateUtil.convertTimeMillisToDate(transaction.getDate_created())
                      .equals(dateObjectTransaction.getDate())) {
                list.add(transaction);
            }
        }
        mListDataChild.put(dateObjectTransaction, list);
    }
    
    private void setGoalMoneyHeader() {
        double moneyGoal = 0;
        for (DateObjectTransaction dateObjectTransaction : mListDataHeader) {
            moneyGoal += Double.parseDouble(dateObjectTransaction.getMoney().trim());
        }
        mGoalMoney.setText("-" + NumberUtil.formatAmount(moneyGoal + "",
                  mEvent.getCurrencies().getCurSymbol()));
    }
    
}
