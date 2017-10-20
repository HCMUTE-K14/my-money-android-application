package com.vn.hcmute.team.cortana.mymoney.ui.debts;

import android.view.View;
import com.vn.hcmute.team.cortana.mymoney.R;
import com.vn.hcmute.team.cortana.mymoney.ui.base.BaseFragment;

/**
 * Created by infamouSs on 9/27/17.
 */

public class DebtsFragmentByType extends BaseFragment {
    
    
    public static DebtsFragmentByType newInstance() {
        return new DebtsFragmentByType();
    }
    
    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
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
}
