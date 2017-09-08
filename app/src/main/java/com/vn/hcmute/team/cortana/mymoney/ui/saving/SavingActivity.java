package com.vn.hcmute.team.cortana.mymoney.ui.saving;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.design.widget.TabLayout.OnTabSelectedListener;
import android.support.design.widget.TabLayout.Tab;
import android.support.v4.view.ViewPager;
import android.view.View;
import butterknife.BindView;
import com.vn.hcmute.team.cortana.mymoney.R;
import com.vn.hcmute.team.cortana.mymoney.model.Saving;
import com.vn.hcmute.team.cortana.mymoney.ui.base.BaseActivity;
import com.vn.hcmute.team.cortana.mymoney.ui.saving.adapter.MyRecyclerViewSavingAdapter;
import java.util.List;

/**
 * Created by kunsubin on 8/23/2017.
 */

public class SavingActivity extends BaseActivity implements SavingContract.View,MyRecyclerViewSavingAdapter.ItemClickListener {
    
    
    @BindView(R.id.tab_layout)
    TabLayout mTabLayout;
    @BindView(R.id.pager)
    ViewPager mViewPager;
    
    private PagerAdapter mPagerAdapter;
 
    @Override
    public int getLayoutId() {
        return R.layout.activity_saving;
    }
    
    @Override
    protected void initializeDagger() {
     
    }
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       
    
        mPagerAdapter=new PagerAdapter(getSupportFragmentManager(),0);
        mViewPager.setAdapter(mPagerAdapter);
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
        
        initTabLayout();
    
    }
    
    @Override
    protected void onDestroy() {
     
        super.onDestroy();
    }
    
    @Override
    protected void initializePresenter() {
      
    }
    
    @Override
    protected void initializeActionBar(View rootView) {
       
    }
    private void initTabLayout(){
        mTabLayout.addTab(mTabLayout.newTab().setText(this.getString(R.string.saving_running)));
        mTabLayout.addTab(mTabLayout.newTab().setText(this.getString(R.string.saving_finished)));
        mTabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

       
    }
    @Override
    public void showListSaving(List<Saving> savings) {
       
     
    }
    
    @Override
    public void showSaving() {
        
    }
    
    @Override
    public void onSuccessCreateSaving() {
       
    }
    
    @Override
    public void onSuccessDeleteSaving() {
     
    }
    
    @Override
    public void onSuccessUpdateSaving() {
       
    }
    
    @Override
    public void onSuccessTakeIn() {
      
    }
    
    @Override
    public void onSuccessTakeOut() {
    
    }
    
    @Override
    public void showError(String message) {
      
    }
    
    @Override
    public void loading(boolean isLoading) {
        
    }
    
    @Override
    public void onItemClick(View view, List<Saving> savingList, int position,int process) {
        
    }
    
    
}
