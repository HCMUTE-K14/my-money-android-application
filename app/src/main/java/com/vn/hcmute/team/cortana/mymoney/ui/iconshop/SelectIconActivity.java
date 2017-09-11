package com.vn.hcmute.team.cortana.mymoney.ui.iconshop;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ProgressBar;
import butterknife.BindView;
import com.vn.hcmute.team.cortana.mymoney.R;
import com.vn.hcmute.team.cortana.mymoney.model.Icon;
import com.vn.hcmute.team.cortana.mymoney.ui.base.BaseActivity;
import com.vn.hcmute.team.cortana.mymoney.ui.base.EmptyAdapter;
import java.util.List;

/**
 * Created by infamouSs on 9/6/17.
 */

public class SelectIconActivity extends BaseActivity implements SelectIconContract.View {
    
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    
    @BindView(R.id.progress_bar)
    ProgressBar mProgressBar;
    
    private SelectIconAdapter mSelectIconAdapter;
    
    private EmptyAdapter mEmptyAdapter;
    
    private SelectIconListener mSelectIconListener = new SelectIconListener() {
        @Override
        public void onClickIcon(int position, Icon icon) {
            
        }
    };
    
    @Override
    public int getLayoutId() {
        return R.layout.activity_select_icon;
    }
    
    @Override
    protected void initializeDagger() {
        
    }
    
    @Override
    protected void initializePresenter() {
        
    }
    
    @Override
    protected void initializeActionBar(View rootView) {
        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(getString(R.string.txt_select_icon));
            actionBar.setDisplayShowHomeEnabled(true);
        }
    }
    
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    
    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
    
    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
    
    @Override
    public void initializeView() {
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 5));
        
        mSelectIconAdapter = new SelectIconAdapter(this, null, mSelectIconListener);
        mEmptyAdapter = new EmptyAdapter(this, getString(R.string.txt_no_data));
        
    }
    
    @Override
    public void showListIcon(List<Icon> icons) {
        mSelectIconAdapter.setData(icons);
        mRecyclerView.setAdapter(mSelectIconAdapter);
    }
    
    @Override
    public void showEmpty(String message) {
        mEmptyAdapter.setMessage(message);
        mRecyclerView.setAdapter(mEmptyAdapter);
    }
    
    @Override
    public void onFailure(String message) {
        mEmptyAdapter.setMessage(message);
        mRecyclerView.setAdapter(mEmptyAdapter);
    }
    
    @Override
    public void loading(boolean isLoading) {
        mProgressBar.setVisibility(isLoading ? View.VISIBLE : View.GONE);
    }
}
