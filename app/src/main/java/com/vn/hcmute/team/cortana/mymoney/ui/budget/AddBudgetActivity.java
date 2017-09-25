package com.vn.hcmute.team.cortana.mymoney.ui.budget;

import android.view.View;
import android.widget.Toast;
import butterknife.OnClick;
import com.borax12.materialdaterangepicker.date.DatePickerDialog;
import com.borax12.materialdaterangepicker.date.DatePickerDialog.OnDateSetListener;
import com.vn.hcmute.team.cortana.mymoney.MyMoneyApplication;
import com.vn.hcmute.team.cortana.mymoney.R;
import com.vn.hcmute.team.cortana.mymoney.di.component.ApplicationComponent;
import com.vn.hcmute.team.cortana.mymoney.di.component.BudgetComponent;
import com.vn.hcmute.team.cortana.mymoney.di.component.DaggerBudgetComponent;
import com.vn.hcmute.team.cortana.mymoney.di.module.ActivityModule;
import com.vn.hcmute.team.cortana.mymoney.di.module.BudgetModule;
import com.vn.hcmute.team.cortana.mymoney.ui.base.BaseActivity;
import java.util.Calendar;
import javax.inject.Inject;

/**
 * Created by kunsubin on 9/16/2017.
 */

public class AddBudgetActivity extends BaseActivity implements OnDateSetListener {
    
   
    
    
    @Inject
    BudgetPresenter mBudgetPresenter;
    
    
    
    @Override
    public int getLayoutId() {
        return R.layout.activity_add_budget;
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
        mPresenter = mBudgetPresenter;
    }
    
    @Override
    protected void initializeActionBar(View rootView) {
        
    }
    @OnClick(R.id.linear_select_date)
    public void onClickLinearSelectDate(View view){
        
        Calendar now = Calendar.getInstance();
        DatePickerDialog dpd = DatePickerDialog.newInstance(
                  AddBudgetActivity.this,
                  now.get(Calendar.YEAR),
                  now.get(Calendar.MONTH),
                  now.get(Calendar.DAY_OF_MONTH)
        );
        dpd.show(getFragmentManager(), "Datepickerdialog");
    }
    
    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth,
              int yearEnd, int monthOfYearEnd, int dayOfMonthEnd) {
        Toast.makeText(this, dayOfMonth+"/"+monthOfYear+"/"+year+"----"+dayOfMonthEnd+"/"+monthOfYearEnd+"/"+
                             yearEnd,
                  Toast.LENGTH_SHORT).show();
    }
}
