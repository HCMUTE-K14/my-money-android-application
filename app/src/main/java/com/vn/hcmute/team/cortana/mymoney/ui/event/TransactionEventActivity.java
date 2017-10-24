package com.vn.hcmute.team.cortana.mymoney.ui.event;

import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ProgressBar;
import butterknife.BindView;
import com.vn.hcmute.team.cortana.mymoney.R;
import com.vn.hcmute.team.cortana.mymoney.model.Transaction;
import com.vn.hcmute.team.cortana.mymoney.ui.base.BaseActivity;
import com.vn.hcmute.team.cortana.mymoney.ui.base.EmptyAdapter;
import com.vn.hcmute.team.cortana.mymoney.ui.transaction.TransactionPresenter;
import java.util.List;
import javax.inject.Inject;

/**
 * Created by kunsubin on 10/17/2017.
 */

public class TransactionEventActivity extends BaseActivity {
    
    @BindView(R.id.list_transaction_event)
    ExpandableListView mExpandableListView;
    @BindView(R.id.progress_bar_transaction)
    ProgressBar mProgressBar;
    @BindView(R.id.swipeRefresh)
    SwipeRefreshLayout mSwipeRefreshLayout;
    @Inject
    TransactionPresenter mTransactionPresenter;
    private EmptyAdapter mEmptyAdapter;
    private List<Transaction> mTransactionList;
    
    @Override
    public int getLayoutId() {
        return R.layout.activity_list_transaction_event;
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
}
