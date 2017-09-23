package com.vn.hcmute.team.cortana.mymoney.ui.budget;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import com.vn.hcmute.team.cortana.mymoney.R;
import com.vn.hcmute.team.cortana.mymoney.model.Budget;
import com.vn.hcmute.team.cortana.mymoney.ui.base.BaseActivity;
import com.vn.hcmute.team.cortana.mymoney.ui.budget.adapter.MyRecyclerViewBudgetAdapter;
import java.util.List;

/**
 * Created by kunsubin on 8/23/2017.
 */

public class BudgetActivity extends BaseActivity implements BudgetContract.View,
                                                            MyRecyclerViewBudgetAdapter.ItemClickListener {
    
    @Override
    public int getLayoutId() {
        return R.layout.activity_budget;
    }
    
    @Override
    protected void initializeDagger() {
        
    }
    
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
    }
    
    @Override
    protected void initializePresenter() {
        
    }
    
    @Override
    protected void initializeActionBar(View rootView) {
        
    }
    
    @Override
    public void onSuccessGetListBudget(List<Budget> list) {
        
    }
    
    @Override
    public void onSuccessCreateBudget(String message) {
        
    }
    
    @Override
    public void onSuccessUpdateBudget(String message) {
        
    }
    
    @Override
    public void onSucsessDeleteBudget(String message) {
        
    }
    
    @Override
    public void onFailure(String message) {
        
    }
    
    @Override
    public void loading(boolean isLoading) {
        
    }
    
    @Override
    public void onItemClick(View view, int position) {
        
    }
}
