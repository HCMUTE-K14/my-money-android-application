package com.vn.hcmute.team.cortana.mymoney.ui.budget;

import android.view.View;
import com.vn.hcmute.team.cortana.mymoney.MyMoneyApplication;
import com.vn.hcmute.team.cortana.mymoney.R;
import com.vn.hcmute.team.cortana.mymoney.di.component.ApplicationComponent;
import com.vn.hcmute.team.cortana.mymoney.di.component.BudgetComponent;
import com.vn.hcmute.team.cortana.mymoney.di.component.DaggerBudgetComponent;
import com.vn.hcmute.team.cortana.mymoney.di.module.ActivityModule;
import com.vn.hcmute.team.cortana.mymoney.di.module.BudgetModule;
import com.vn.hcmute.team.cortana.mymoney.ui.base.BaseActivity;
import javax.inject.Inject;

/**
 * Created by kunsubin on 9/16/2017.
 */

public class InfoBudgetActivity extends BaseActivity {
    
    @Inject
    BudgetPresenter mBudgetPresenter;
    
    @Override
    public int getLayoutId() {
        return R.layout.activity_info_budget;
    }
    
    @Override
    protected void initializeDagger() {
        ApplicationComponent applicationComponent = ((MyMoneyApplication) this.getApplication())
                  .getAppComponent();
        BudgetComponent budgetComponent = DaggerBudgetComponent
                  .builder()
                  .applicationComponent(applicationComponent)
                  .activityModule(new ActivityModule(this))
                  .budgetModule(new BudgetModule())
                  .build();
        budgetComponent.inject(this);
    }
    
    @Override
    protected void initializePresenter() {
        mPresenter=mBudgetPresenter;
    }
    
    @Override
    protected void initializeActionBar(View rootView) {
        
    }
}
