package com.vn.hcmute.team.cortana.mymoney.ui.event.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import com.vn.hcmute.team.cortana.mymoney.ui.event.FragmentEventFinished;
import com.vn.hcmute.team.cortana.mymoney.ui.event.FragmentEventRunning;

/**
 * Created by kunsubin on 9/13/2017.
 */

public class PagerAdapterEvent extends FragmentStatePagerAdapter {

    private FragmentEventRunning mFragmentEventRunning = new FragmentEventRunning();
    private FragmentEventFinished mFragmentEventFinished = new FragmentEventFinished();

    public PagerAdapterEvent(FragmentManager fm) {
        super(fm);
    }
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return mFragmentEventRunning;
            case 1:
                return mFragmentEventFinished;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return 2;
    }
}
