package com.vn.hcmute.team.cortana.mymoney.ui.category;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by infamouSs on 9/16/17.
 */

public class CategoryViewPagerAdapter extends FragmentPagerAdapter {
    
    private List<Fragment> mFragmentList = new ArrayList<>();
    private List<String> mTitleFragment = new ArrayList<>();
    
    public CategoryViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }
    
    public void add(Fragment fragment, String title) {
        mFragmentList.add(fragment);
        
        mTitleFragment.add(title);
    }
    
    @Override
    public Fragment getItem(int position) {
        return mFragmentList.get(position);
    }
    
    @Override
    public CharSequence getPageTitle(int position) {
        return mTitleFragment.get(position);
    }
    
    @Override
    public int getCount() {
        return mFragmentList.size();
    }
}
