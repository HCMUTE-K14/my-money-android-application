package com.vn.hcmute.team.cortana.mymoney.ui.debts;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;
import butterknife.BindView;
import com.vn.hcmute.team.cortana.mymoney.R;
import com.vn.hcmute.team.cortana.mymoney.ui.base.BaseFragment;

/**
 * Created by infamouSs on 9/27/17.
 */

public class DebtsMainFragment extends BaseFragment {
    
    
    @BindView(R.id.tab_layout)
    TabLayout mTabLayout;
    
    @BindView(R.id.viewpager)
    ViewPager mViewPager;
    
    
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_main_debts;
    }
    
    @Override
    protected void initializeDagger() {
        
    }
    
    @Override
    protected void initializePresenter() {
        
    }
    
    @Override
    protected void initializeActionBar(View rootView) {
        
    }
    
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
    
    public void initializeView() {
        DebtsViewPagerAdapter viewPagerAdapter = new DebtsViewPagerAdapter(
                  this.getChildFragmentManager());
        
    }
}
