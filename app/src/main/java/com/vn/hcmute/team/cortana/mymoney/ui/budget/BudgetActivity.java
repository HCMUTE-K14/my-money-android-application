package com.vn.hcmute.team.cortana.mymoney.ui.budget;

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
import com.vn.hcmute.team.cortana.mymoney.ui.budget.adapter.PagerAdapterBudget;

/**
 * Created by kunsubin on 8/23/2017.
 */

public class BudgetActivity extends BaseActivity {
    
    @BindView(R.id.tab_layout)
    TabLayout mTabLayout;
    @BindView(R.id.pager)
    ViewPager mViewPager;
    @BindView(R.id.btn_add_budget)
    FloatingActionButton btn_add_budget;
    
    private PagerAdapterBudget mPagerAdapterBudget;
    private int currentPositionFragment = 0;
    
    @Override
    public int getLayoutId() {
        return R.layout.activity_budget;
    }
    
    @Override
    protected void initializeDagger() {
        
    }
    
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    
    
        mPagerAdapterBudget = new PagerAdapterBudget(getSupportFragmentManager());
        
        mViewPager.setAdapter(mPagerAdapterBudget);
    
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
    
    @Override
    protected void initializePresenter() {
        
    }
    
    @Override
    protected void initializeActionBar(View rootView) {
        
    }
    public void initTablayout() {
        mTabLayout.addTab(mTabLayout.newTab().setText(this.getString(R.string.saving_running)));
        mTabLayout.addTab(mTabLayout.newTab().setText(this.getString(R.string.saving_finished)));
        mTabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
    }
    
}
