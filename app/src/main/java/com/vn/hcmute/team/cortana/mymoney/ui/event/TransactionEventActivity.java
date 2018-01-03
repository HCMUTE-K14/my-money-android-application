package com.vn.hcmute.team.cortana.mymoney.ui.event;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.view.View;
import butterknife.BindView;
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
import com.vn.hcmute.team.cortana.mymoney.ui.statistics.transaction.FragmentTransactionByTime;
import com.vn.hcmute.team.cortana.mymoney.ui.transaction.TransactionContract;
import com.vn.hcmute.team.cortana.mymoney.ui.transaction.TransactionPresenter;
import java.util.List;
import javax.inject.Inject;

/**
 * Created by kunsubin on 11/7/2017.
 */

public class TransactionEventActivity extends BaseActivity implements TransactionContract.View {
    
    @BindView(R.id.swipeRefresh)
    SwipeRefreshLayout mSwipeRefreshLayout;
    @Inject
    TransactionPresenter mTransactionPresenter;
    private Event mEvent;
    
    @Override
    public int getLayoutId() {
        return R.layout.activity_list_transaction;
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
        getData();
        mTransactionPresenter.getTransactionByEvent(mEvent.getEventid());
        mSwipeRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                mTransactionPresenter.getTransactionByEvent(mEvent.getEventid());
            }
        });
    }
    
    @Override
    protected void onDestroy() {
        mTransactionPresenter.unSubscribe();
        super.onDestroy();
    }
    
    @Override
    public void showAllListTransaction(List<Transaction> list) {
        if (mSwipeRefreshLayout.isRefreshing()) {
            mSwipeRefreshLayout.setRefreshing(false);
        }
        FragmentTransactionByTime mFragmentTransactionByTime = new FragmentTransactionByTime(this,
                  list);
        getSupportFragmentManager().beginTransaction()
                  .replace(R.id.view_fragment, mFragmentTransactionByTime).commit();
    }
    
    @Override
    public void onFailure(String message) {
    
        if (mSwipeRefreshLayout.isRefreshing()) {
            mSwipeRefreshLayout.setRefreshing(false);
        }
    }
    
    @Override
    public void loading(boolean isLoading) {
    
    }
    
    @OnClick(R.id.ic_cancel)
    public void onClickCancel(View view) {
        finish();
    }
    
    public void getData() {
        mEvent = new Event();
        Intent intent = this.getIntent();
        if (intent.getParcelableExtra("event") != null) {
            mEvent = intent.getParcelableExtra("event");
        }
    }
}
