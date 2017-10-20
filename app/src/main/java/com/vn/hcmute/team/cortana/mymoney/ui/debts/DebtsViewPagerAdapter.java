package com.vn.hcmute.team.cortana.mymoney.ui.debts;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by infamouSs on 9/27/17.
 */

public class DebtsViewPagerAdapter extends FragmentPagerAdapter {
    
    private List<Fragment> mFragmentList = new ArrayList<>();
    private List<String> mTitleList = new ArrayList<>();
    
    public DebtsViewPagerAdapter(FragmentManager fragmentManager) {
        super(fragmentManager);
    }
    
    @Override
    public Fragment getItem(int position) {
        return mFragmentList.get(position);
    }
    
    @Override
    public int getCount() {
        return mFragmentList.size();
    }
    
    
    public void add(Fragment fragment, String title) {
        this.mFragmentList.add(fragment);
        this.mTitleList.add(title);
    }
    
    
    @Override
    public CharSequence getPageTitle(int position) {
        return mTitleList.get(position);
    }
}
