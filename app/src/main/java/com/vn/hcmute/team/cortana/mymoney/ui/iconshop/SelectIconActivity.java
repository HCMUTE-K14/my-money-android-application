package com.vn.hcmute.team.cortana.mymoney.ui.iconshop;

import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog.Builder;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import butterknife.BindView;
import com.vn.hcmute.team.cortana.mymoney.MyMoneyApplication;
import com.vn.hcmute.team.cortana.mymoney.R;
import com.vn.hcmute.team.cortana.mymoney.di.component.ApplicationComponent;
import com.vn.hcmute.team.cortana.mymoney.di.component.DaggerImageComponent;
import com.vn.hcmute.team.cortana.mymoney.di.component.ImageComponent;
import com.vn.hcmute.team.cortana.mymoney.di.module.ActivityModule;
import com.vn.hcmute.team.cortana.mymoney.di.module.ImageModule;
import com.vn.hcmute.team.cortana.mymoney.model.Icon;
import com.vn.hcmute.team.cortana.mymoney.ui.base.BaseActivity;
import com.vn.hcmute.team.cortana.mymoney.ui.base.EmptyAdapter;
import com.vn.hcmute.team.cortana.mymoney.ui.view.SpacesItemDecoration;
import java.util.List;
import javax.inject.Inject;

/**
 * Created by infamouSs on 9/6/17.
 */

public class SelectIconActivity extends BaseActivity implements SelectIconContract.View {
    
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    
    @BindView(R.id.swipeRefresh)
    SwipeRefreshLayout mSwipeRefreshLayout;
    
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    
    @BindView(R.id.progress_bar)
    ProgressBar mProgressBar;
    
    @Inject
    SelectIconPresenter mSelectIconPresenter;
    
    private SelectIconAdapter mSelectIconAdapter;
    
    private Icon mIconSelected;
    
    private EmptyAdapter mEmptyAdapter;
    
    private SelectIconListener mSelectIconListener = new SelectIconListener() {
        @Override
        public void onClickIcon(int position, Icon icon) {
            mIconSelected = icon;
            onDone();
        }
    };
    
    @Override
    public int getLayoutId() {
        return R.layout.activity_select_icon;
    }
    
    @Override
    protected void initializeDagger() {
        ApplicationComponent applicationComponent = ((MyMoneyApplication) this.getApplication())
                  .getAppComponent();
        ImageComponent imageComponent = DaggerImageComponent.builder()
                  .applicationComponent(applicationComponent)
                  .imageModule(new ImageModule())
                  .activityModule(new ActivityModule(this))
                  .build();
        
        imageComponent.inject(this);
    }
    
    @Override
    protected void initializePresenter() {
        this.mPresenter = mSelectIconPresenter;
        this.mSelectIconPresenter.setView(this);
    }
    
    @Override
    protected void initializeActionBar(View rootView) {
        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(getString(R.string.txt_select_icon));
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
        }
    }
    
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initializeView();
        
        getData();
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            showConfirmDoneDialog();
            return true;
        }
        
        return super.onOptionsItemSelected(item);
    }
    
    @Override
    protected void onDestroy() {
        super.onDestroy();
        mSelectIconPresenter.unSubscribe();
    }
    
    @Override
    public void onBackPressed() {
        showConfirmDoneDialog();
    }
    
    @Override
    public void initializeView() {
        mSwipeRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                getData();
            }
        });
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 5);
        mRecyclerView.setLayoutManager(gridLayoutManager);
        mRecyclerView.addItemDecoration(new SpacesItemDecoration(30));
        mSelectIconAdapter = new SelectIconAdapter(this, null, mSelectIconListener);
        mEmptyAdapter = new EmptyAdapter(this, getString(R.string.txt_no_data));
        
    }
    
    @Override
    public void showListIcon(List<Icon> icons) {
        if (mSwipeRefreshLayout.isRefreshing()) {
            mSwipeRefreshLayout.setRefreshing(false);
        }
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 5));
        mSelectIconAdapter.setData(icons);
        mRecyclerView.setAdapter(mSelectIconAdapter);
    }
    
    @Override
    public void showEmpty(String message) {
        if (mSwipeRefreshLayout.isRefreshing()) {
            mSwipeRefreshLayout.setRefreshing(false);
        }
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 1));
        mEmptyAdapter.setMessage(message);
        mRecyclerView.setAdapter(mEmptyAdapter);
    }
    
    @Override
    public void onFailure(String message) {
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 1));
        mEmptyAdapter.setMessage(message);
        mRecyclerView.setAdapter(mEmptyAdapter);
    }
    
    @Override
    public void loading(boolean isLoading) {
        mProgressBar.setVisibility(isLoading ? View.VISIBLE : View.GONE);
    }
    
    private void getData() {
        mSelectIconPresenter.getListIcon();
    }
    
    private void onDone() {
        Intent intent = new Intent();
        intent.putExtra("icon", mIconSelected);
        setResult(RESULT_OK, intent);
        
        finish();
    }
    
    private void showConfirmDoneDialog() {
        Builder doneDialog = new Builder(this);
        doneDialog.setMessage(getString(R.string.message_finish_choosing_icon));
        doneDialog.setNegativeButton(getString(R.string.txt_yes), new OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                onDone();
            }
        });
        doneDialog.setPositiveButton(getString(R.string.txt_no), new OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                dialog.dismiss();
            }
        });
        
        doneDialog.create().show();
    }
}
