package com.vn.hcmute.team.cortana.mymoney.ui.category;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.View;
import com.vn.hcmute.team.cortana.mymoney.R;
import com.vn.hcmute.team.cortana.mymoney.model.Category;
import com.vn.hcmute.team.cortana.mymoney.ui.base.BaseActivity;

/**
 * Created by infamouSs on 9/17/17.
 */

public class ManagerCategoryActivity extends BaseActivity {
    
    public static final String TAG = ManagerCategoryActivity.class.getSimpleName();
    
    private String mAction;
    private String mTransType;
    private Category mCategory;
    private Fragment mFragment;
    
    private Runnable mRunnableAttachManagerCategoryFragment = new Runnable() {
        @Override
        public void run() {
            mFragment = ManagerCategoryFragment.newInstance(mAction, mTransType, mCategory);
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction()
                      .replace(R.id.container, mFragment)
                      .commit();
        }
    };
    
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
        if (getIntent() != null) {
            Intent intent = getIntent();
            
            mAction = intent.getStringExtra("action");
            mCategory = intent.getParcelableExtra("category");
            mTransType = intent.getStringExtra("trans_type");
        }
        initializeView();
    }
    
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        mFragment.onActivityResult(requestCode, resultCode, data);
    }
    
    public void initializeView() {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mRunnableAttachManagerCategoryFragment.run();
            }
        }, 150);
        
    }
}
