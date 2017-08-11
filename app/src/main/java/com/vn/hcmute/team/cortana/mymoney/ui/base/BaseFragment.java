package com.vn.hcmute.team.cortana.mymoney.ui.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by infamouSs on 8/11/17.
 */

public abstract class BaseFragment extends Fragment {
    
    protected FragmentManager mFramentManager;
    
    protected BasePresenter mPresenter;
    
    private View view;
    
    private Unbinder unbinder;
    
    public BaseFragment() {
        super();
    }
    
    protected abstract int getLayoutId();
    
    protected abstract void initializeDagger();
    
    protected abstract void initializePresenter();
    
    
    protected abstract void initializeActionBar(View rootView);
    
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mFramentManager = getActivity().getSupportFragmentManager();
        initializeDagger();
        initializePresenter();
        
    }
    
    @Nullable
    @Override
    public View onCreateView(
              LayoutInflater inflater, @Nullable ViewGroup container,
              Bundle savedInstanceState) {
        this.view = inflater.inflate(this.getLayoutId(), container, false);
        if (mPresenter != null) {
            mPresenter.initialize(getArguments());
        }
        initializeActionBar(this.view);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }
    
    @Override
    public void onStart() {
        super.onStart();
        if (mPresenter != null) {
            mPresenter.start();
        }
    }
    
    @Override
    public void onResume() {
        super.onResume();
        
    }
    
    @Override
    public void onStop() {
        super.onStop();
        if (mPresenter != null) {
            mPresenter.finalizeView();
        }
    }
    
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
