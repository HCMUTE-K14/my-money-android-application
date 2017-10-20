package com.vn.hcmute.team.cortana.mymoney.ui.saving;

import android.app.Activity;
import android.content.Intent;
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
import com.vn.hcmute.team.cortana.mymoney.di.component.DaggerSavingComponent;
import com.vn.hcmute.team.cortana.mymoney.di.component.SavingComponent;
import com.vn.hcmute.team.cortana.mymoney.di.module.ActivityModule;
import com.vn.hcmute.team.cortana.mymoney.di.module.SavingModule;
import com.vn.hcmute.team.cortana.mymoney.model.Saving;
import com.vn.hcmute.team.cortana.mymoney.ui.base.BaseFragment;
import com.vn.hcmute.team.cortana.mymoney.ui.base.EmptyAdapter;
import com.vn.hcmute.team.cortana.mymoney.ui.saving.adapter.MyRecyclerViewSavingAdapter;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;

/**
 * Created by kunsubin on 8/28/2017.
 */

public class FragmentRunning extends BaseFragment implements
                                                  MyRecyclerViewSavingAdapter.ItemClickListener,
                                                  SavingContract.View {
    
    
    @BindView(R.id.recycler_view_saving_running)
    RecyclerView mRecyclerView;
    @BindView(R.id.progress_bar_saving)
    ProgressBar mProgressBar;
    @BindView(R.id.swipeRefresh)
    SwipeRefreshLayout mSwipeRefreshLayout;
    @Inject
    SavingPresenter mSavingPresenter;
    private List<Saving> mSavingList;
    private MyRecyclerViewSavingAdapter mMyRecyclerViewSavingAdapter;
    private EmptyAdapter mEmptyAdapter;
    
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_saving_running;
    }
    
    @Override
    protected void initializeDagger() {
        ApplicationComponent applicationComponent = ((MyMoneyApplication) getActivity()
                  .getApplication())
                  .getAppComponent();
        SavingComponent savingComponent = DaggerSavingComponent
                  .builder()
                  .applicationComponent(applicationComponent)
                  .activityModule(new ActivityModule(getActivity()))
                  .savingModule(new SavingModule())
                  .build();
        savingComponent.inject(this);
    }
    
    @Override
    protected void initializePresenter() {
        mPresenter = this.mSavingPresenter;
        mSavingPresenter.setView(this);
    }
    
    @Override
    protected void initializeActionBar(View rootView) {
        
    }
    
    @Override
    protected void initialize() {
        init();
        mSavingPresenter.getSaving();
        
        mSwipeRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                mSavingList.clear();
                if (mMyRecyclerViewSavingAdapter != null) {
                    mMyRecyclerViewSavingAdapter.notifyDataSetChanged();
                }
                mSavingPresenter.getSaving();
            }
        });
    }
    
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1) {
            if (resultCode == Activity.RESULT_OK) {
                String savingId = data.getStringExtra("result");
                
                if (!mSavingList.isEmpty()) {
                    for (Saving saving : mSavingList) {
                        if (saving.getSavingid().equals(savingId)) {
                            mSavingList.remove(saving);
                            mMyRecyclerViewSavingAdapter.setList(mSavingList);
                            break;
                        }
                    }
                    if (mSavingList.isEmpty()) {
                        mEmptyAdapter = new EmptyAdapter(getContext(),
                                  getString(R.string.nosaving));
                        mRecyclerView.setAdapter(mEmptyAdapter);
                    }
                }
                
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                mSavingList.clear();
                mMyRecyclerViewSavingAdapter.notifyDataSetChanged();
                mSavingPresenter.getSaving();
            }
        }
        if (requestCode == 12) {
            if (resultCode == Activity.RESULT_OK) {
                Saving saving = data.getParcelableExtra("resultAdd");
                if (mSavingList.isEmpty()) {
                    
                    mSavingList.add(saving);
                    mMyRecyclerViewSavingAdapter = new MyRecyclerViewSavingAdapter(getContext(),
                              mSavingList);
                    mMyRecyclerViewSavingAdapter.setClickListener(this);
                    mRecyclerView.setAdapter(mMyRecyclerViewSavingAdapter);
                } else {
                    mSavingList.add(saving);
                    mMyRecyclerViewSavingAdapter.setList(mSavingList);
                }
            }
        }
        
    }
    
    @Override
    public void showListSaving(List<Saving> savings) {
        if (!savings.isEmpty()) {
            
            for (Saving saving : savings) {
                if (saving.getStatus().equals("0")) {
                    mSavingList.add(saving);
                }
            }
            if (!mSavingList.isEmpty()) {
                mMyRecyclerViewSavingAdapter = new MyRecyclerViewSavingAdapter(getContext(),
                          mSavingList);
                mMyRecyclerViewSavingAdapter.setClickListener(this);
                mRecyclerView.setAdapter(mMyRecyclerViewSavingAdapter);
            } else {
                mEmptyAdapter = new EmptyAdapter(getContext(), getString(R.string.txt_no_saving));
                mRecyclerView.setAdapter(mEmptyAdapter);
            }
        } else {
            mEmptyAdapter = new EmptyAdapter(getContext(), getString(R.string.txt_no_saving));
            mRecyclerView.setAdapter(mEmptyAdapter);
        }
        
        if (mSwipeRefreshLayout.isRefreshing()) {
            mSwipeRefreshLayout.setRefreshing(false);
        }
    }
    
    @Override
    public void showSaving() {
        
    }
    
    @Override
    public void onSuccessCreateSaving() {
        
    }
    
    @Override
    public void onSuccessDeleteSaving() {
        
    }
    
    @Override
    public void onSuccessUpdateSaving() {
        
    }
    
    @Override
    public void onSuccessTakeIn() {
        
    }
    
    @Override
    public void onSuccessTakeOut() {
        
    }
    
    @Override
    public void showError(String message) {
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
    public void onDestroy() {
        mSavingPresenter.unSubscribe();
        super.onDestroy();
    }
    
    @Override
    public void onItemClick(View view, List<Saving> savingList, int position, int process) {
        Saving saving = savingList.get(position);
        Intent intent = new Intent(this.getContext(), InfoSavingActivity.class);
        if (saving != null) {
            intent.putExtra("MySaving", saving);
            intent.putExtra("process", String.valueOf(process));
            
        }
        getActivity().startActivityForResult(intent, 1);
    }
    
    public void init() {
        mRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 1));
        mSavingList = new ArrayList<>();
    }
}
