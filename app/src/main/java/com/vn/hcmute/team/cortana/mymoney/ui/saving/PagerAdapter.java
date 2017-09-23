package com.vn.hcmute.team.cortana.mymoney.ui.saving;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;


/**
 * Created by kunsubin on 8/28/2017.
 */

public class PagerAdapter extends FragmentStatePagerAdapter {

    private FragmentRunning mFragmentRunning;
    private FragmentFinished mFragmentFinished;
    private int mNumOfTabs;

    public PagerAdapter(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
        mFragmentRunning=new FragmentRunning();
        mFragmentFinished=new FragmentFinished();
    }
    
    @Override
    public Fragment getItem(int position) {
        
        switch (position) {
            case 0:
                return mFragmentRunning;
            case 1:
                return mFragmentFinished;
            default:
                return null;
        }
    }
    public  Fragment getFragment(int position){
        switch (position){
            case 0:
                return mFragmentRunning;
            case 1:
                return mFragmentFinished;
            default:
                return null;
        }
    }
    @Override
    public int getCount() {
        return 2;
    }
    
    public int getNumOfTabs() {
        return mNumOfTabs;
    }
}
