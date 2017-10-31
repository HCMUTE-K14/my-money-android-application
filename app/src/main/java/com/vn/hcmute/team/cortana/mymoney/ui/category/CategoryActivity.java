package com.vn.hcmute.team.cortana.mymoney.ui.category;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import butterknife.BindView;
import com.vn.hcmute.team.cortana.mymoney.R;
import com.vn.hcmute.team.cortana.mymoney.ui.base.BaseActivity;
import com.vn.hcmute.team.cortana.mymoney.usecase.base.Action;

/**
 * Created by infamouSs on 9/15/17.
 */

public class CategoryActivity extends BaseActivity {
    
    public static final String TAG = CategoryActivity.class.getSimpleName();
    
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    
    @BindView(R.id.tab_layout)
    TabLayout mTabLayout;
    
    @BindView(R.id.viewpager)
    ViewPager mViewPager;
    
    private String mCategoryIdSelected;
    private boolean mIsOnyLyDebtLoanCategory;
    
    @Override
    public int getLayoutId() {
        return R.layout.activity_main_category;
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
            actionBar.setTitle(getString(R.string.txt_select_category));
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
        }
    }
    
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mCategoryIdSelected = getCategoryIdFromIntent();
        mIsOnyLyDebtLoanCategory = isOnyLyDebtLoanCategory();
        initializeView();
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_select_category, menu);
        return super.onCreateOptionsMenu(menu);
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        
        return super.onOptionsItemSelected(item);
    }
    
    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
    
    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
    
    public void initializeView() {
        CategoryViewPagerAdapter viewPagerAdapter = new CategoryViewPagerAdapter(
                  this.getSupportFragmentManager());
        int mode = CategoryByTypeFragment.MODE_ENABLE_CHOOSE_CATEGORY;
        viewPagerAdapter
                  .add(CategoryByTypeFragment
                                      .newInstance(mode, Action.ACTION_GET_DEBT_LOAN_CATEGORY,
                                                mCategoryIdSelected),
                            getString(R.string.txt_debt_loan));
        if (!mIsOnyLyDebtLoanCategory) {
            viewPagerAdapter.add(CategoryByTypeFragment
                                .newInstance(mode, Action.ACTION_GET_EXPENSE_CATEGORY, mCategoryIdSelected),
                      getString(R.string.txt_expense));
            
            viewPagerAdapter.add(CategoryByTypeFragment
                                .newInstance(mode, Action.ACTION_GET_INCOMING_CATEGORY, mCategoryIdSelected),
                      getString(R.string.txt_incoming));
        }
        
        mViewPager.setAdapter(viewPagerAdapter);
        mViewPager.setOffscreenPageLimit(!mIsOnyLyDebtLoanCategory ? 2 : 1);
        
        mTabLayout.setupWithViewPager(mViewPager);
    }
    
    private String getCategoryIdFromIntent() {
        if (getIntent() != null) {
            return getIntent().getStringExtra("cate_id");
        }
        return "";
    }
    
    private boolean isOnyLyDebtLoanCategory() {
        return getIntent().getBooleanExtra("only_debt_loan_category", false);
    }
}
