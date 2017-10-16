package com.vn.hcmute.team.cortana.mymoney.ui.budget.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import com.vn.hcmute.team.cortana.mymoney.ui.budget.FragmentBudgetFinished;
import com.vn.hcmute.team.cortana.mymoney.ui.budget.FragmentBudgetRunning;

/**
 * Created by kunsubin on 9/24/2017.
 */

public class PagerAdapterBudget extends FragmentStatePagerAdapter {
    FragmentBudgetRunning mFragmentBudgetRunning=new FragmentBudgetRunning();
    FragmentBudgetFinished mFragmentBudgetFinished=new FragmentBudgetFinished();
    public PagerAdapterBudget(FragmentManager fm) {
        super(fm);
        
    }
    
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return mFragmentBudgetRunning;
            case 1:
                return mFragmentBudgetFinished;
            default:
                return null;
        }
    }
    @Override
    public int getCount() {
        return 2;
    }
}
