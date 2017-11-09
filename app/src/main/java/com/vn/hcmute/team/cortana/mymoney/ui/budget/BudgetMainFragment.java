package com.vn.hcmute.team.cortana.mymoney.ui.budget;

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
import com.vn.hcmute.team.cortana.mymoney.ui.budget.adapter.PagerAdapterBudget;

/**
 * Created by kunsubin on 9/27/2017.
 */

public class BudgetMainFragment extends BaseFragment {
    
    @BindView(R.id.tab_layout)
    TabLayout mTabLayout;
    @BindView(R.id.pager)
    ViewPager mViewPager;
    @BindView(R.id.btn_add_budget)
    FloatingActionButton btn_add_budget;
    
    private PagerAdapterBudget mPagerAdapterBudget;
    
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_budget;
    }
    
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initializeView();
    }
    
    @Override
    protected void initializeDagger() {
        
    }
    
    @Override
    protected void initializePresenter() {
        
    }
    
    @Override
    protected void initializeActionBar(View rootView) {
        getActivity().setTitle(getString(R.string.txt_budget));
    }
    
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 30) {
            if (resultCode == Activity.RESULT_OK) {
                mPagerAdapterBudget.getItem(0).onActivityResult(requestCode, resultCode, data);
            }
        }
        if (requestCode == 34) {
            mPagerAdapterBudget.getItem(0).onActivityResult(requestCode, resultCode, data);
        }
        if (requestCode == 35) {
            mPagerAdapterBudget.getItem(1).onActivityResult(requestCode, resultCode, data);
        }
    }
    
    @OnClick(R.id.btn_add_budget)
    public void onClickAddBudget(View view) {
        Intent intent = new Intent(getActivity(), AddBudgetActivity.class);
        getActivity().startActivityForResult(intent, 30);
    }
    
    private void initializeView() {
        mPagerAdapterBudget = new PagerAdapterBudget(this.getFragmentManager());
        
        mViewPager.setAdapter(mPagerAdapterBudget);
        
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
    
    public void initTablayout() {
        mTabLayout.addTab(mTabLayout.newTab().setText(this.getString(R.string.saving_running)));
        mTabLayout.addTab(mTabLayout.newTab().setText(this.getString(R.string.saving_finished)));
        mTabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
    }
}
