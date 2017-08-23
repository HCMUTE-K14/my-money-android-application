package com.vn.hcmute.team.cortana.mymoney.ui.budget;

import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.OnClick;
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
 * Created by kunsubin on 8/23/2017.
 */

public class BudgetActivity extends BaseActivity {
    @Inject
    BudgetPresenter mBudgetPresenter;
    
    @BindView(R.id.button2)
    Button mButton;
    
    BudgetContract.View view=new BudgetContract.View() {
        @Override
        public void onSuccess(Object object) {
               /* List<Budget> list=(List<Budget>) object;
                
                if(list==null||list.isEmpty()){
                    Toast.makeText(getApplicationContext(),"null",Toast.LENGTH_LONG).show();
                }else {
                    Toast.makeText(getApplicationContext(),list.get(0).getBudgetId(),Toast.LENGTH_LONG).show();
                   
                }*/
            Toast.makeText(getApplicationContext(),(String)object,Toast.LENGTH_LONG).show();
        }
        
        @Override
        public void onFailure(String message) {
            Toast.makeText(getApplicationContext(),message,Toast.LENGTH_LONG).show();
        }
    };
    
    @OnClick(R.id.button2)
    public void onClick() {
        mBudgetPresenter.setView(view);
       // mBudgetPresenter.getBudget();
        //mBudgetPresenter.createBudget(new Budget());
       /* Budget budget=new Budget();
        budget.setBudgetId("anhba");
        budget.setUserid("e67757e090bb47bbbebf7db8b15e7c96");
        budget.setMoneyGoal("15288945");
        mBudgetPresenter.updateBudget(budget);*/
        mBudgetPresenter.deleteBudget("anhba");
    }
    @Override
    public int getLayoutId() {
        return R.layout.activity_wallet;
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
        this.mPresenter=mBudgetPresenter;
    }
    
    @Override
    protected void initializeActionBar(View rootView) {
        
    }
}
