package com.vn.hcmute.team.cortana.mymoney.ui.saving;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.OnClick;
import com.vn.hcmute.team.cortana.mymoney.MyMoneyApplication;
import com.vn.hcmute.team.cortana.mymoney.R;
import com.vn.hcmute.team.cortana.mymoney.di.component.ApplicationComponent;
import com.vn.hcmute.team.cortana.mymoney.di.component.DaggerTransactionComponent;
import com.vn.hcmute.team.cortana.mymoney.di.component.TransactionComponent;
import com.vn.hcmute.team.cortana.mymoney.di.module.ActivityModule;
import com.vn.hcmute.team.cortana.mymoney.di.module.TransactionModule;
import com.vn.hcmute.team.cortana.mymoney.model.Saving;
import com.vn.hcmute.team.cortana.mymoney.model.Transaction;
import com.vn.hcmute.team.cortana.mymoney.ui.base.BaseActivity;
import com.vn.hcmute.team.cortana.mymoney.ui.base.EmptyAdapter;
import com.vn.hcmute.team.cortana.mymoney.ui.saving.adapter.TransactionSavingAdapter;
import com.vn.hcmute.team.cortana.mymoney.ui.transaction.TransactionContract;
import com.vn.hcmute.team.cortana.mymoney.ui.transaction.TransactionPresenter;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;


/**
 * Created by kunsubin on 10/3/2017.
 */

public class TransactionSavingActivity extends BaseActivity implements TransactionContract.View,
                                                                       TransactionSavingAdapter.ItemClickListener {
    
    @BindView(R.id.recycler_view_list_transaction_saving)
    RecyclerView mRecyclerViewTransaction;
    @BindView(R.id.progress_bar_transaction)
    ProgressBar mProgressBar;
    @BindView(R.id.swipeRefresh)
    SwipeRefreshLayout mSwipeRefreshLayout;
    @Inject
    TransactionPresenter mTransactionPresenter;
    private TransactionSavingAdapter mTransactionSavingAdapter;
    private EmptyAdapter mEmptyAdapter;
    private List<Transaction> mTransactionList;
    private Saving mSaving;
    
    @Override
    public int getLayoutId() {
        return R.layout.activity_list_transaction_saving;
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
        init();
        getData();
        if (mSaving == null) {
            mEmptyAdapter = new EmptyAdapter(this, getString(R.string.no_saving));
            mRecyclerViewTransaction.setAdapter(mEmptyAdapter);
        } else {
            mTransactionPresenter.getAllTransaction();
        }
        mSwipeRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                mTransactionList.clear();
                if (mTransactionSavingAdapter != null) {
                    mTransactionSavingAdapter.notifyDataSetChanged();
                }
                mTransactionPresenter.getAllTransaction();
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
        if (list == null || list.isEmpty()) {
            mEmptyAdapter = new EmptyAdapter(this, getString(R.string.txt_no_transaction));
            mRecyclerViewTransaction.setAdapter(mEmptyAdapter);
        } else {
            for (Transaction transaction : list) {
                
                if (transaction.getSaving() != null &&
                    mSaving.getSavingid().equals(transaction.getSaving().getSavingid())) {
                    mTransactionList.add(transaction);
                }
            }
            if (mTransactionList != null && !mTransactionList.isEmpty()) {
                mTransactionSavingAdapter = new TransactionSavingAdapter(this, mTransactionList);
                mTransactionSavingAdapter.setClickListener(this);
                mRecyclerViewTransaction.setAdapter(mTransactionSavingAdapter);
            } else {
                mEmptyAdapter = new EmptyAdapter(this, getString(R.string.txt_no_transaction));
                mRecyclerViewTransaction.setAdapter(mEmptyAdapter);
            }
        }
        if (mSwipeRefreshLayout.isRefreshing()) {
            mSwipeRefreshLayout.setRefreshing(false);
        }
    }
    
    @Override
    public void onFailure(String message) {
        if (mSwipeRefreshLayout.isRefreshing()) {
            mSwipeRefreshLayout.setRefreshing(false);
        }
        mEmptyAdapter = new EmptyAdapter(this, message);
        mRecyclerViewTransaction.setAdapter(mEmptyAdapter);
    }
    
    @Override
    public void loading(boolean isLoading) {
        mProgressBar.setVisibility(isLoading ? View.VISIBLE : View.GONE);
    }
    
    /*Area onClick*/
    @OnClick(R.id.ic_cancel)
    public void onClickCancel(View view) {
        finish();
    }
    
    /*Area function*/
    public void getData() {
        Intent intent = this.getIntent();
        mSaving = intent.getParcelableExtra("saving");
    }
    
    public void init() {
        mTransactionList = new ArrayList<>();
        mRecyclerViewTransaction.setLayoutManager(new GridLayoutManager(this, 1));
    }
    
    @Override
    public void onItemClick(Transaction transaction) {
        Toast.makeText(this, transaction.getTrans_id(), Toast.LENGTH_SHORT).show();
    }
}
