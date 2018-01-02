package com.vn.hcmute.team.cortana.mymoney.ui.budget;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import butterknife.BindView;
import com.vn.hcmute.team.cortana.mymoney.MyMoneyApplication;
import com.vn.hcmute.team.cortana.mymoney.R;
import com.vn.hcmute.team.cortana.mymoney.di.component.ApplicationComponent;
import com.vn.hcmute.team.cortana.mymoney.di.component.BudgetComponent;
import com.vn.hcmute.team.cortana.mymoney.di.component.DaggerBudgetComponent;
import com.vn.hcmute.team.cortana.mymoney.di.module.ActivityModule;
import com.vn.hcmute.team.cortana.mymoney.di.module.BudgetModule;
import com.vn.hcmute.team.cortana.mymoney.model.Budget;
import com.vn.hcmute.team.cortana.mymoney.ui.base.BaseFragment;
import com.vn.hcmute.team.cortana.mymoney.ui.base.EmptyAdapter;
import com.vn.hcmute.team.cortana.mymoney.ui.budget.adapter.MyRecyclerViewBudgetAdapter;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;

/**
 * Created by kunsubin on 9/24/2017.
 */

public class FragmentBudgetRunning extends BaseFragment implements BudgetContract.View,
                                                                   MyRecyclerViewBudgetAdapter.ItemClickListener {
    
    @BindView(R.id.recycler_view_budget_running)
    RecyclerView mRecyclerView;
    @BindView(R.id.progress_bar_budget)
    ProgressBar mProgressBar;
    @BindView(R.id.swipeRefresh)
    SwipeRefreshLayout mSwipeRefreshLayout;
    @Inject
    BudgetPresenter mBudgetPresenter;
    private MyRecyclerViewBudgetAdapter mMyRecyclerViewBudgetAdapter;
    private List<Budget> mBudgetList;
    private EmptyAdapter mEmptyAdapter;
    private List<Budget> mBudgetsUpdateStatus;
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_budget_running;
    }
    
    @Override
    protected void initializeDagger() {
        ApplicationComponent applicationComponent = ((MyMoneyApplication) getActivity()
                  .getApplication())
                  .getAppComponent();
        BudgetComponent budgetComponent = DaggerBudgetComponent
                  .builder()
                  .applicationComponent(applicationComponent)
                  .activityModule(new ActivityModule(getActivity()))
                  .budgetModule(new BudgetModule())
                  .build();
        budgetComponent.inject(this);
    }
    
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        
    }
    
    @Override
    protected void initializePresenter() {
        mPresenter = mBudgetPresenter;
        mBudgetPresenter.setView(this);
    }
    
    @Override
    protected void initializeActionBar(View rootView) {
        
    }
    
    @Override
    protected void initialize() {
        mRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 1));
        mBudgetList = new ArrayList<>();
        mBudgetsUpdateStatus=new ArrayList<>();
        mBudgetPresenter.getBudget();
        mSwipeRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                mBudgetList.clear();
                if (mMyRecyclerViewBudgetAdapter != null) {
                    mMyRecyclerViewBudgetAdapter.notifyDataSetChanged();
                }
                mBudgetPresenter.getBudget();
            }
        });
    }
    
    @Override
    public void onSuccessGetListBudget(List<Budget> list) {
        if (!list.isEmpty()) {
            for (Budget budget : list) {
                if (budget.getStatus().equals("0")) {
                    mBudgetList.add(budget);
                }
            }
            if (!mBudgetList.isEmpty()) {
                mMyRecyclerViewBudgetAdapter = new MyRecyclerViewBudgetAdapter(getActivity(),
                          mBudgetList);
                mMyRecyclerViewBudgetAdapter.setClickListener(this);
                mRecyclerView.setAdapter(mMyRecyclerViewBudgetAdapter);
            } else {
                mEmptyAdapter = new EmptyAdapter(getActivity(), getString(R.string.txt_no_budget));
                mRecyclerView.setAdapter(mEmptyAdapter);
            }
        } else {
            mEmptyAdapter = new EmptyAdapter(getContext(), getString(R.string.txt_no_budget));
            mRecyclerView.setAdapter(mEmptyAdapter);
        }
        if (mSwipeRefreshLayout.isRefreshing()) {
            mSwipeRefreshLayout.setRefreshing(false);
        }
        checkUpdateStatus(mBudgetList);
    }
    
    private void checkUpdateStatus(List<Budget> budgetList) {
        mBudgetsUpdateStatus.clear();
        long currentMillisecond=System.currentTimeMillis();
        for(Budget budget:budgetList){
            String millisecond=budget.getRangeDate().split("/")[1];
            long millisecondEndBudget=Long.parseLong(millisecond);
            if(millisecondEndBudget<currentMillisecond){
                mBudgetsUpdateStatus.add(budget);
            }
        }
        if(mBudgetsUpdateStatus!=null&&mBudgetsUpdateStatus.size()>0){
            mBudgetPresenter.updateStatusBudget(mBudgetsUpdateStatus);
        }
    }
    
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 30) {
            if (resultCode == Activity.RESULT_OK) {
                Budget budget = data.getParcelableExtra("resultAdd");
                if (mBudgetList.isEmpty()) {
                    
                    mBudgetList.add(budget);
                    mMyRecyclerViewBudgetAdapter = new MyRecyclerViewBudgetAdapter(getContext(),
                              mBudgetList);
                    mMyRecyclerViewBudgetAdapter.setClickListener(this);
                    mRecyclerView.setAdapter(mMyRecyclerViewBudgetAdapter);
                } else {
                    mBudgetList.add(budget);
                    mMyRecyclerViewBudgetAdapter.setList(mBudgetList);
                }
            }
        }
        if (requestCode == 34) {
            if (resultCode == Activity.RESULT_OK) {
                String budgetId = data.getStringExtra("result");
                
                if (!mBudgetList.isEmpty()) {
                    for (Budget budget : mBudgetList) {
                        if (budget.getBudgetId().equals(budgetId)) {
                            mBudgetList.remove(budget);
                            mMyRecyclerViewBudgetAdapter.setList(mBudgetList);
                            break;
                        }
                    }
                    if (mBudgetList.isEmpty()) {
                        mEmptyAdapter = new EmptyAdapter(getContext(),
                                  getString(R.string.nosaving));
                        mRecyclerView.setAdapter(mEmptyAdapter);
                    }
                }
                
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                mBudgetList.clear();
                mMyRecyclerViewBudgetAdapter.notifyDataSetChanged();
                mBudgetPresenter.getBudget();
            }
        }
    }
    
    @Override
    public void onSuccessCreateBudget(String message) {
        
    }
    
    @Override
    public void onSuccessUpdateBudget(String message) {
        mBudgetList.clear();
        if (mMyRecyclerViewBudgetAdapter != null) {
            mMyRecyclerViewBudgetAdapter.notifyDataSetChanged();
        }
        mBudgetPresenter.getBudget();
    }
    
    @Override
    public void onSuccessDeleteBudget(String message) {
        
    }
    
    @Override
    public void onFailure(String message) {
        if (mSwipeRefreshLayout.isRefreshing()) {
            mSwipeRefreshLayout.setRefreshing(false);
        }
        mEmptyAdapter = new EmptyAdapter(getContext(), getString(R.string.txt_no_saving));
        mRecyclerView.setAdapter(mEmptyAdapter);
    }
    
    @Override
    public void loading(boolean isLoading) {
        mProgressBar.setVisibility(isLoading ? View.VISIBLE : View.GONE);
    }
    
    @Override
    public void onItemClick(Budget budget) {
        
        Intent intent = new Intent(getActivity(), InfoBudgetActivity.class);
        intent.putExtra("budget", budget);
        getActivity().startActivityForResult(intent, 34);
        
    }
}
