package com.vn.hcmute.team.cortana.mymoney.ui.transaction;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;
import com.vn.hcmute.team.cortana.mymoney.R;
import com.vn.hcmute.team.cortana.mymoney.model.Transaction;
import com.vn.hcmute.team.cortana.mymoney.ui.base.BaseActivity;

/**
 * Created by infamouSs on 9/28/17.
 */

public class ManagerTransactionActivity extends BaseActivity {
    
    public static final String TAG = ManagerTransactionActivity.class.getSimpleName();
    
    private String mAction;
    private Transaction mTransaction;
    private Fragment mFragment;
    
    @Override
    public int getLayoutId() {
        return R.layout.activity_manager;
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
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        getDataFromIntent();
        
        mFragment = ManagerTransactionFragment.newInstance(mAction, mTransaction, null);
        
        attachFragment();
    }
    
    private void getDataFromIntent() {
        Intent intent = getIntent();
        if (intent != null) {
            mAction = intent.getStringExtra("action");
            mTransaction = intent.getParcelableExtra("transaction");
        }
    }
    
    private void attachFragment() {
        this.getSupportFragmentManager()
                  .beginTransaction()
                  .replace(R.id.container, mFragment)
                  .commit();
    }
}
