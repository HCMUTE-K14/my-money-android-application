package com.vn.hcmute.team.cortana.mymoney.ui.event;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.design.widget.TabLayout.OnTabSelectedListener;
import android.support.design.widget.TabLayout.Tab;
import android.support.v4.view.ViewPager;
import android.view.View;
import butterknife.BindView;
import com.vn.hcmute.team.cortana.mymoney.R;
import com.vn.hcmute.team.cortana.mymoney.ui.base.BaseActivity;
import com.vn.hcmute.team.cortana.mymoney.ui.event.adapter.PagerAdapterEvent;

/**
 * Created by kunsubin on 8/22/2017.
 */

public class EventActivity extends BaseActivity {
    
    
    @BindView(R.id.tab_layout)
    TabLayout mTabLayout;
    @BindView(R.id.pager)
    ViewPager mViewPager;
    @BindView(R.id.btn_add_event)
    FloatingActionButton btn_add_event;
    
    private PagerAdapterEvent mPagerAdapterEvent;
    
    @Override
    public int getLayoutId() {
        return R.layout.activity_event;
    }
    
    @Override
    protected void initializeDagger() {
    }
    
    @Override
    protected void initializePresenter() {
       
    }
    
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    
    
        mPagerAdapterEvent=new PagerAdapterEvent(getSupportFragmentManager(),0);
        mViewPager.setAdapter(mPagerAdapterEvent);
        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mTabLayout));
        mTabLayout.setOnTabSelectedListener(new OnTabSelectedListener() {
            @Override
            public void onTabSelected(Tab tab) {
                mViewPager.setCurrentItem(tab.getPosition());
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
    
    @Override
    protected void initializeActionBar(View rootView) {
        
    }
    public void initTablayout(){
        mTabLayout.addTab(mTabLayout.newTab().setText(this.getString(R.string.saving_running)));
        mTabLayout.addTab(mTabLayout.newTab().setText(this.getString(R.string.saving_finished)));
        mTabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
    }
}
