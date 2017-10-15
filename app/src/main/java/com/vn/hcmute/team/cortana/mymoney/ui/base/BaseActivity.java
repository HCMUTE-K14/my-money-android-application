package com.vn.hcmute.team.cortana.mymoney.ui.base;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.View;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import com.vn.hcmute.team.cortana.mymoney.R;
import com.vn.hcmute.team.cortana.mymoney.ui.base.view.BaseView;

/**
 * Created by infamouSs on 8/11/17.
 */

public abstract class BaseActivity extends AppCompatActivity implements BaseView {
    
    
    protected BasePresenter mPresenter;
    private Unbinder unbinder;
    
    public BaseActivity() {
    }
    
    @LayoutRes
    public abstract int getLayoutId();
    
    protected abstract void initializeDagger();
    
    protected abstract void initializePresenter();
    
    protected abstract void initializeActionBar(View rootView);
    
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
        setContentView(getLayoutId());
        
        unbinder = ButterKnife.bind(this);
        
        initializeDagger();
        initializePresenter();
        initializeActionBar(this.findViewById(android.R.id.content));
        initialize();
        
    }
    protected void initialize(){
        
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        
        return true;
    }
    
    @Override
    protected void onStart() {
        super.onStart();
        if (mPresenter != null) {
            mPresenter.start();
        }
    }
    
    @Override
    protected void onResume() {
        super.onResume();
    }
    
    @Override
    protected void onStop() {
        super.onStop();
        if (mPresenter != null) {
            mPresenter.finalizeView();
        }
    }
    
    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
    }
    
    @Override
    protected void onDestroy() {
        super.onDestroy();
        
        unbinder.unbind();
    }
}
