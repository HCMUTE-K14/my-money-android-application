package com.vn.hcmute.team.cortana.mymoney.ui.event;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.design.widget.TabLayout.OnTabSelectedListener;
import android.support.design.widget.TabLayout.Tab;
import android.support.v4.view.ViewPager;
import android.view.View;
import butterknife.BindView;
import butterknife.OnClick;
import com.vn.hcmute.team.cortana.mymoney.R;
import com.vn.hcmute.team.cortana.mymoney.ui.base.BaseFragment;
import com.vn.hcmute.team.cortana.mymoney.ui.event.adapter.PagerAdapterEvent;

/**
 * Created by infamouSs on 9/25/17.
 */

public class EventMainFragment extends BaseFragment {
    
    public static final String TAG = EventMainFragment.class.getSimpleName();
    
    @BindView(R.id.tab_layout)
    TabLayout mTabLayout;
    @BindView(R.id.pager)
    ViewPager mViewPager;
    @BindView(R.id.btn_add_event)
    FloatingActionButton btn_add_event;
    
    private PagerAdapterEvent mPagerAdapterEvent;
    private int currentPositionFragment = 0;
    
    @Override
    protected int getLayoutId() {
        return R.layout.activity_event;
    }
    
    @Override
    protected void initializeDagger() {
        
    }
    
    @Override
    protected void initializePresenter() {
        
    }
    
    @Override
    protected void initializeActionBar(View rootView) {
        getActivity().setTitle(getString(R.string.txt_event));
    }
    
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initializeView();
    }
    
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 22) {
            if (resultCode == Activity.RESULT_OK) {
                mPagerAdapterEvent.getItem(0).onActivityResult(requestCode, resultCode, data);
            }
        }
        if (requestCode == 15) {
            mPagerAdapterEvent.getItem(currentPositionFragment)
                      .onActivityResult(requestCode, resultCode, data);
            if (resultCode == Activity.RESULT_CANCELED) {
                mPagerAdapterEvent.getItem(1).onActivityResult(requestCode, resultCode, data);
            }
        }
        if (requestCode == 16) {
            mPagerAdapterEvent.getItem(currentPositionFragment)
                      .onActivityResult(requestCode, resultCode, data);
        }
    }
    
    /*OnClick*/
    @OnClick(R.id.btn_add_event)
    public void onClickAddEvent(View view) {
        Intent intent = new Intent(this.getActivity(), ActivityAddEvent.class);
        getActivity().startActivityForResult(intent, 22);
    }
    
    /*Area Funcion*/
    private void initializeView() {
        mPagerAdapterEvent = new PagerAdapterEvent(this.getFragmentManager());
        mViewPager.setAdapter(mPagerAdapterEvent);
        
        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mTabLayout));
        mTabLayout.setOnTabSelectedListener(new OnTabSelectedListener() {
            @Override
            public void onTabSelected(Tab tab) {
                mViewPager.setCurrentItem(tab.getPosition());
                currentPositionFragment = tab.getPosition();
            }
            
            @Override
            public void onTabUnselected(Tab tab) {
                
            }
            
            @Override
            public void onTabReselected(Tab tab) {
                
            }
        });
        initTablayout();
    }
    
    public void initTablayout() {
        mTabLayout.addTab(mTabLayout.newTab().setText(this.getString(R.string.saving_running)));
        mTabLayout.addTab(mTabLayout.newTab().setText(this.getString(R.string.saving_finished)));
        mTabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
    }
}
