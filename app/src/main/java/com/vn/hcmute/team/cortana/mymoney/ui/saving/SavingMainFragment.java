package com.vn.hcmute.team.cortana.mymoney.ui.saving;

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

/**
 * Created by infamouSs on 9/25/17.
 */

public class SavingMainFragment extends BaseFragment {
    
    @BindView(R.id.tab_layout)
    TabLayout mTabLayout;
    
    @BindView(R.id.pager)
    ViewPager mViewPager;
    
    @BindView(R.id.btn_add_saving)
    FloatingActionButton btn_add_saving;
    
    private int mCurrentPositionFragment = 0;
    
    private PagerAdapter mPagerAdapter;
    
    
    @Override
    protected int getLayoutId() {
        return R.layout.activity_saving;
    }
    
    @Override
    protected void initializeDagger() {
        
    }
    
    @Override
    protected void initializePresenter() {

    }
    
    @Override
    protected void initializeActionBar(View rootView) {
        getActivity().setTitle(getString(R.string.txt_savings));
    }
    
    @OnClick(R.id.btn_add_saving)
    public void onClickAddSaving(View view) {
        Intent intent = new Intent(this.getActivity(), AddSavingActivity.class);
        getActivity().startActivityForResult(intent, 12);
    }
    
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initializeView();
    }
    
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 12) {
            if (resultCode == Activity.RESULT_OK) {
                mPagerAdapter.getItem(0).onActivityResult(requestCode, resultCode, data);
            }
        }
        if (requestCode == 1) {
            mPagerAdapter.getItem(mCurrentPositionFragment)
                      .onActivityResult(requestCode, resultCode, data);
        }
        if (requestCode == 2) {
            mPagerAdapter.getItem(mCurrentPositionFragment)
                      .onActivityResult(requestCode, resultCode, data);
        }
    }
    
    private void initializeView() {
        mPagerAdapter = new PagerAdapter(this.getFragmentManager(), 2);
        mViewPager.setAdapter(mPagerAdapter);
        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mTabLayout));
        
        mTabLayout.setOnTabSelectedListener(new OnTabSelectedListener() {
            @Override
            public void onTabSelected(Tab tab) {
                mViewPager.setCurrentItem(tab.getPosition());
                mCurrentPositionFragment = tab.getPosition();
            }
            
            @Override
            public void onTabUnselected(Tab tab) {
                
            }
            
            @Override
            public void onTabReselected(Tab tab) {
                
            }
        });
        
        initTabLayout();
    }
    
    private void initTabLayout() {
        mTabLayout.addTab(mTabLayout.newTab().setText(this.getString(R.string.saving_running)));
        mTabLayout.addTab(mTabLayout.newTab().setText(this.getString(R.string.saving_finished)));
        mTabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
    }
}
