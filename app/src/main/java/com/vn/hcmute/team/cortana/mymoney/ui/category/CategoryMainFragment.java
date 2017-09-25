package com.vn.hcmute.team.cortana.mymoney.ui.category;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import butterknife.BindView;
import com.vn.hcmute.team.cortana.mymoney.R;
import com.vn.hcmute.team.cortana.mymoney.ui.base.BaseFragment;
import com.vn.hcmute.team.cortana.mymoney.usecase.base.Action;

/**
 * Created by infamouSs on 9/24/17.
 */

public class CategoryMainFragment extends BaseFragment {
    
    public static final String TAG = CategoryMainFragment.class.getSimpleName();
    
    @BindView(R.id.tab_layout)
    TabLayout mTabLayout;
    
    @BindView(R.id.viewpager)
    ViewPager mViewPager;
    
    private CategoryViewPagerAdapter mViewPagerAdapter;
    
    @Override
    protected int getLayoutId() {
        return R.layout.include_main_category_fragment;
    }
    
    @Override
    protected void initializeDagger() {
        
    }
    
    @Override
    protected void initializePresenter() {
        
    }
    
    @Override
    protected void initializeActionBar(View rootView) {
        getActivity().setTitle(getString(R.string.txt_select_category));
        
    }
    
    
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initializeView();
    }
    
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_select_category, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }
    
    private void initializeView() {
        mViewPagerAdapter = new CategoryViewPagerAdapter(
                  this.getChildFragmentManager());
        
        int mode = CategoryByTypeFragment.MODE_DISABLE_CHOOSE_CATEGORY;
        
        mViewPagerAdapter.add(CategoryByTypeFragment
                            .newInstance(mode, Action.ACTION_GET_DEBT_LOAN_CATEGORY, null),
                  getString(R.string.txt_debt_loan));
        
        mViewPagerAdapter.add(CategoryByTypeFragment
                            .newInstance(mode, Action.ACTION_GET_EXPENSE_CATEGORY, null),
                  getString(R.string.txt_expense));
        
        mViewPagerAdapter.add(CategoryByTypeFragment
                            .newInstance(mode, Action.ACTION_GET_INCOMING_CATEGORY, null),
                  getString(R.string.txt_incoming));
        
        mViewPager.setAdapter(mViewPagerAdapter);
        mViewPager.setOffscreenPageLimit(2);
        
        mTabLayout.setupWithViewPager(mViewPager);
    }
    
    public void reloadData() {
        CategoryByTypeFragment fragment = (CategoryByTypeFragment) mViewPagerAdapter
                  .getItem(mViewPager.getCurrentItem());
        
        fragment.reloadData();
    }
}
