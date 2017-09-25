package com.vn.hcmute.team.cortana.mymoney.ui.budget;

import android.view.View;
import com.vn.hcmute.team.cortana.mymoney.MyMoneyApplication;
import com.vn.hcmute.team.cortana.mymoney.R;
import com.vn.hcmute.team.cortana.mymoney.di.component.ApplicationComponent;
import com.vn.hcmute.team.cortana.mymoney.di.component.BudgetComponent;
import com.vn.hcmute.team.cortana.mymoney.di.component.DaggerBudgetComponent;
import com.vn.hcmute.team.cortana.mymoney.di.module.ActivityModule;
import com.vn.hcmute.team.cortana.mymoney.di.module.BudgetModule;
import com.vn.hcmute.team.cortana.mymoney.ui.base.BaseFragment;
import javax.inject.Inject;

/**
 * Created by kunsubin on 9/24/2017.
 */

public class FragmentBudgetFinished extends BaseFragment {
    
    @Inject
    BudgetPresenter mBudgetPresenter;
    
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_budget_finished;
    }
    
    @Override
    protected void initializeDagger() {
        ApplicationComponent applicationComponent = ((MyMoneyApplication) getActivity().getApplication())
                  .getAppComponent();
        BudgetComponent budgetComponent = DaggerBudgetComponent
                  .builder()
                  .applicationComponent(applicationComponent)
                  .activityModule(new ActivityModule(getActivity()))
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
