package com.vn.hcmute.team.cortana.mymoney.ui.budget;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.OnClick;
import com.borax12.materialdaterangepicker.date.DatePickerDialog;
import com.borax12.materialdaterangepicker.date.DatePickerDialog.OnDateSetListener;
import com.vn.hcmute.team.cortana.mymoney.MyMoneyApplication;
import com.vn.hcmute.team.cortana.mymoney.R;
import com.vn.hcmute.team.cortana.mymoney.data.cache.PreferencesHelper;
import com.vn.hcmute.team.cortana.mymoney.di.component.ApplicationComponent;
import com.vn.hcmute.team.cortana.mymoney.di.component.BudgetComponent;
import com.vn.hcmute.team.cortana.mymoney.di.component.DaggerBudgetComponent;
import com.vn.hcmute.team.cortana.mymoney.di.module.ActivityModule;
import com.vn.hcmute.team.cortana.mymoney.di.module.BudgetModule;
import com.vn.hcmute.team.cortana.mymoney.model.Budget;
import com.vn.hcmute.team.cortana.mymoney.model.Category;
import com.vn.hcmute.team.cortana.mymoney.model.Wallet;
import com.vn.hcmute.team.cortana.mymoney.ui.base.BaseActivity;
import com.vn.hcmute.team.cortana.mymoney.ui.category.CategoryActivity;
import com.vn.hcmute.team.cortana.mymoney.ui.tools.calculator.CalculatorActivity;
import com.vn.hcmute.team.cortana.mymoney.ui.wallet.MyWalletActivity;
import com.vn.hcmute.team.cortana.mymoney.utils.DateUtil;
import com.vn.hcmute.team.cortana.mymoney.utils.DrawableUtil;
import com.vn.hcmute.team.cortana.mymoney.utils.GlideImageLoader;
import com.vn.hcmute.team.cortana.mymoney.utils.NumberUtil;
import com.vn.hcmute.team.cortana.mymoney.utils.SecurityUtil;
import java.util.Calendar;
import java.util.List;
import javax.inject.Inject;

/**
 * Created by kunsubin on 9/16/2017.
 */

public class AddBudgetActivity extends BaseActivity implements OnDateSetListener,
                                                               BudgetContract.View {
    
    @BindView(R.id.image_view_icon_budget)
    ImageView image_view_icon_budget;
    @BindView(R.id.txt_name_budget)
    TextView txt_name_budget;
    @BindView(R.id.txt_goal_money)
    TextView txt_goal_money;
    @BindView(R.id.txt_date_budget)
    TextView txt_date_budget;
    @BindView(R.id.txt_wallet_budget)
    TextView txt_wallet_budget;
    
    @Inject
    BudgetPresenter mBudgetPresenter;
    @Inject
    PreferencesHelper mPreferencesHelper;
    
    private Budget mBudget;
    private Category mCategory;
    private Wallet mWallet;
    private int year;
    private int monthOfYear;
    private int dayOfMonth;
    private int yearEnd;
    private int monthOfYearEnd;
    private int dayOfMonthEnd;
    private String mGoalMoney = "0";
    private ProgressDialog mProgressDialog;
    
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
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    
    @Override
    protected void initialize() {
        init();
        showData();
        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setCanceledOnTouchOutside(false);
        mProgressDialog.setMessage(this.getString(R.string.txt_progress));
    }
    
    @Override
    protected void onDestroy() {
        mBudgetPresenter.unSubscribe();
        super.onDestroy();
    }
    
    @Override
    protected void initializePresenter() {
        mPresenter = mBudgetPresenter;
        mBudgetPresenter.setView(this);
    }
    
    @Override
    protected void initializeActionBar(View rootView) {
        
    }
    
    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth,
              int yearEnd, int monthOfYearEnd, int dayOfMonthEnd) {
        
        this.year = year;
        this.monthOfYear = monthOfYear + 1;
        this.dayOfMonth = dayOfMonth;
        this.yearEnd = yearEnd;
        this.monthOfYearEnd = monthOfYearEnd + 1;
        this.dayOfMonthEnd = dayOfMonthEnd;
        
        txt_date_budget
                  .setText(this.dayOfMonth + "/" + this.monthOfYear + "/" + this.year + " - " +
                           this.dayOfMonthEnd +
                           "/" + this.monthOfYearEnd + "/" +
                           this.yearEnd);
        txt_date_budget.setTextColor(ContextCompat.getColor(this, R.color.black));
        
    }
    
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 31) {
            if (resultCode == Activity.RESULT_OK) {
                
                mGoalMoney = data.getStringExtra("result");
                String goalMoneyShow = data.getStringExtra("result_view");
                txt_goal_money
                          .setText("+" + goalMoneyShow);
                mBudget.setMoneyGoal(mGoalMoney);
            }
        }
        if (requestCode == 32) {
            if (resultCode == Activity.RESULT_OK) {
                mWallet = data.getParcelableExtra("wallet");
                txt_wallet_budget.setText(mWallet.getWalletName());
                txt_goal_money
                          .setText("+" + NumberUtil.formatAmount(mGoalMoney,
                                    mWallet.getCurrencyUnit().getCurSymbol()));
            }
        }
        if (requestCode == 33) {
            if (resultCode == Activity.RESULT_OK) {
                mCategory = data.getParcelableExtra("category");
                txt_name_budget.setText(mCategory.getName());
                GlideImageLoader.load(this, DrawableUtil.getDrawable(this, mCategory.getIcon()),
                          image_view_icon_budget);
            }
        }
    }
    
    @Override
    public void onSuccessGetListBudget(List<Budget> list) {
        
    }
    
    @Override
    public void onSuccessCreateBudget(String message) {
        Intent returnIntent = new Intent();
        returnIntent.putExtra("resultAdd", mBudget);
        setResult(Activity.RESULT_OK, returnIntent);
        finish();
    }
    
    @Override
    public void onSuccessUpdateBudget(String message) {
        
    }
    
    @Override
    public void onSucsessDeleteBudget(String message) {
        
    }
    
    @Override
    public void onFailure(String message) {
        alertDiaglog(message);
    }
    
    @Override
    public void loading(boolean isLoading) {
        if (isLoading) {
            mProgressDialog.show();
        } else {
            mProgressDialog.dismiss();
        }
    }
    
    /*Area onClick*/
    @OnClick(R.id.linear_select_date)
    public void onClickLinearSelectDate(View view) {
        
        Calendar now = Calendar.getInstance();
        DatePickerDialog dpd = DatePickerDialog.newInstance(
                  AddBudgetActivity.this,
                  now.get(Calendar.YEAR),
                  now.get(Calendar.MONTH),
                  now.get(Calendar.DAY_OF_MONTH)
        );
        dpd.show(getFragmentManager(), "Datepickerdialog");
    }
    
    @OnClick(R.id.ic_cancel_budget)
    public void onClickCancel(View view) {
        finish();
    }
    
    @OnClick(R.id.linear_goal_money)
    public void onClickGoalMoney(View view) {
        Intent intent = new Intent(this, CalculatorActivity.class);
        intent.putExtra("goal_money", mGoalMoney);
        intent.putExtra("currencies", mWallet.getCurrencyUnit());
        startActivityForResult(intent, 31);
    }
    
    @OnClick(R.id.linear_select_wallet)
    public void onClickSelectWallet(View view) {
        Intent intent = new Intent(this, MyWalletActivity.class);
        startActivityForResult(intent, 32);
    }
    
    @OnClick(R.id.txt_edit_budget)
    public void onClickSave(View view) {
        if (checkSaveBudget()) {
            setBudget();
            mBudgetPresenter.createBudget(mBudget);
        }
    }
    
    
    @OnClick(R.id.linear_select_category)
    public void onClickSelectCategory(View view) {
        Intent intent = new Intent(this, CategoryActivity.class);
        startActivityForResult(intent, 33);
    }
    
    /*Area function*/
    public void init() {
        mBudget = new Budget();
        mCategory = new Category();
        mWallet = new Wallet();
        
    }
    
    public void showData() {
        mWallet = mPreferencesHelper.getCurrentWallet();
        txt_wallet_budget.setText(mWallet.getWalletName());
        txt_goal_money.setText("+" + NumberUtil.formatAmount(mGoalMoney,
                  mWallet.getCurrencyUnit().getCurSymbol()));
    }
    
    public boolean checkSaveBudget() {
        if (txt_name_budget.getText().toString().equals("")) {
            alertDiaglog(getString(R.string.txt_select_category));
            return false;
        }
        if (mGoalMoney.equals("0")) {
            alertDiaglog(getString(R.string.select_money));
            return false;
        }
        if (txt_date_budget.getText().toString().equals("")) {
            alertDiaglog(getString(R.string.select_date));
            return false;
        }
        if (!checkRangeDate()) {
            alertDiaglog(getString(R.string.txt_error_range_date));
            return false;
        }
        return true;
    }
    
    public void setBudget() {
        mBudget.setBudgetId(SecurityUtil.getRandomUUID());
        mBudget.setStatus("0");
        mBudget.setRangeDate(DateUtil.getLongAsDate(dayOfMonth, monthOfYear, year) + "/" +
                             DateUtil.getLongAsDate(dayOfMonthEnd, monthOfYearEnd, yearEnd));
        mBudget.setMoneyExpense("0");
        mBudget.setUserid(mPreferencesHelper.getUserId());
        mBudget.setCategory(mCategory);
        mBudget.setWallet(mWallet);
        
    }
    
    public boolean checkRangeDate() {
        return DateUtil.getLongAsDate(dayOfMonth, monthOfYear, year) >
               DateUtil.getLongAsDate(dayOfMonthEnd, monthOfYearEnd, yearEnd) ? false : true;
    }
    
    public void alertDiaglog(String message) {
        AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
        builder1.setMessage(message);
        builder1.setCancelable(true);
        builder1.setPositiveButton(
                  getString(R.string.txt_ok),
                  new DialogInterface.OnClickListener() {
                      public void onClick(DialogInterface dialog, int id) {
                          dialog.cancel();
                      }
                  });
        
        AlertDialog alert11 = builder1.create();
        alert11.show();
    }
}
