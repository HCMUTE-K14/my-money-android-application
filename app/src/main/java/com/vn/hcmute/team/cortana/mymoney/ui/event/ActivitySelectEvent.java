package com.vn.hcmute.team.cortana.mymoney.ui.event;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import butterknife.BindView;
import butterknife.OnClick;
import com.vn.hcmute.team.cortana.mymoney.MyMoneyApplication;
import com.vn.hcmute.team.cortana.mymoney.R;
import com.vn.hcmute.team.cortana.mymoney.di.component.ApplicationComponent;
import com.vn.hcmute.team.cortana.mymoney.di.component.DaggerEventComponent;
import com.vn.hcmute.team.cortana.mymoney.di.component.EventComponent;
import com.vn.hcmute.team.cortana.mymoney.di.module.ActivityModule;
import com.vn.hcmute.team.cortana.mymoney.di.module.EventModule;
import com.vn.hcmute.team.cortana.mymoney.model.Event;
import com.vn.hcmute.team.cortana.mymoney.model.Wallet;
import com.vn.hcmute.team.cortana.mymoney.ui.base.BaseActivity;
import com.vn.hcmute.team.cortana.mymoney.ui.base.EmptyAdapter;
import com.vn.hcmute.team.cortana.mymoney.ui.event.adapter.MyRecyclerViewEventAdapter;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;

/**
 * Created by kunsubin on 9/28/2017.
 */

public class ActivitySelectEvent extends BaseActivity implements EventContract.View,
                                                                 MyRecyclerViewEventAdapter.ItemClickListener {
    
    @BindView(R.id.recycler_view_event)
    RecyclerView mRecyclerView;
    @BindView(R.id.progress_bar_select_event)
    ProgressBar mProgressBar;
    @BindView(R.id.swipeRefresh)
    SwipeRefreshLayout mSwipeRefreshLayout;
    @Inject
    EventPresenter mEventPresenter;
    private MyRecyclerViewEventAdapter mMyRecyclerViewEventAdapter;
    private List<Event> mEventList;
    private EmptyAdapter mEmptyAdapter;
    private Wallet mWallet;
    
    @Override
    public int getLayoutId() {
        return R.layout.activity_select_event;
    }
    
    @Override
    protected void initializeDagger() {
        ApplicationComponent applicationComponent = ((MyMoneyApplication) this
                  .getApplication())
                  .getAppComponent();
        EventComponent eventComponent = DaggerEventComponent
                  .builder()
                  .applicationComponent(applicationComponent)
                  .activityModule(new ActivityModule(this))
                  .eventModule(new EventModule())
                  .build();
        eventComponent.inject(this);
    }
    
    @Override
    protected void initializePresenter() {
        mPresenter = mEventPresenter;
        mEventPresenter.setView(this);
        
    }
    
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState,
              @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
    }
    
    @Override
    protected void initializeActionBar(View rootView) {
        
    }
    
    @Override
    protected void initialize() {
        mWallet = new Wallet();
        getData();
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 1));
        mEventList = new ArrayList<>();
        mEventPresenter.getEvent();
        mSwipeRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                mEventList.clear();
                if (mMyRecyclerViewEventAdapter != null) {
                    mMyRecyclerViewEventAdapter.notifyDataSetChanged();
                }
                mEventPresenter.getEvent();
            }
        });
    }
    
    @Override
    public void onSuccessGetListEvent(List<Event> events) {
        if (!events.isEmpty()) {
            for (Event event : events) {
                if (event.getStatus().equals("0") && (event.getIdWallet().equals("") ||
                                                      event.getIdWallet()
                                                                .equals(mWallet.getWalletid()))) {
                    mEventList.add(event);
                }
            }
            if (!mEventList.isEmpty()) {
                mMyRecyclerViewEventAdapter = new MyRecyclerViewEventAdapter(this,
                          mEventList);
                mMyRecyclerViewEventAdapter.setClickListener(this);
                mRecyclerView.setAdapter(mMyRecyclerViewEventAdapter);
            } else {
                mEmptyAdapter = new EmptyAdapter(this, getString(R.string.no_event));
                mRecyclerView.setAdapter(mEmptyAdapter);
            }
            
        } else {
            mEmptyAdapter = new EmptyAdapter(this, getString(R.string.no_event));
            mRecyclerView.setAdapter(mEmptyAdapter);
        }
        if (mSwipeRefreshLayout.isRefreshing()) {
            mSwipeRefreshLayout.setRefreshing(false);
        }
    }
    
    @Override
    public void onSuccessCreateEvent(String message) {
        
    }
    
    @Override
    public void onSuccessUpdateEvent(String message) {
        
    }
    
    @Override
    public void onSuccessDeleteEvent(String message) {
        
    }
    
    @Override
    public void onFailure(String message) {
        if (mSwipeRefreshLayout.isRefreshing()) {
            mSwipeRefreshLayout.setRefreshing(false);
        }
        mEmptyAdapter = new EmptyAdapter(this, getString(R.string.no_event));
        mRecyclerView.setAdapter(mEmptyAdapter);
    }
    
    @Override
    public void loading(boolean isLoading) {
        mProgressBar.setVisibility(isLoading ? View.VISIBLE : View.GONE);
    }
    
    @Override
    public void onItemClick(Event event) {
        Intent intent = new Intent();
        intent.putExtra("event", event);
        setResult(Activity.RESULT_OK, intent);
        finish();
    }
    
    @OnClick(R.id.ic_back)
    public void onClickBack(View view) {
        finish();
    }
    
    /*Area Function*/
    public void getData() {
        Intent intent = this.getIntent();
        if (intent.getParcelableExtra("wallet") != null) {
            mWallet = intent.getParcelableExtra("wallet");
        }
    }
    public void alertDiaglog(String message) {
        AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
        builder1.setMessage(message);
        builder1.setCancelable(true);
        builder1.setPositiveButton(
                  getString(R.string.txt_ok),
                  new DialogInterface.OnClickListener() {
                      public void onClick(DialogInterface dialog, int id) {
                          dialog.cancel();
                      }
                  });
        
        AlertDialog alert11 = builder1.create();
        alert11.show();
        
    }
}
