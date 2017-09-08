package com.vn.hcmute.team.cortana.mymoney.ui.saving;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.OnClick;
import com.vn.hcmute.team.cortana.mymoney.MyMoneyApplication;
import com.vn.hcmute.team.cortana.mymoney.R;
import com.vn.hcmute.team.cortana.mymoney.di.component.ApplicationComponent;
import com.vn.hcmute.team.cortana.mymoney.di.component.DaggerSavingComponent;
import com.vn.hcmute.team.cortana.mymoney.di.component.SavingComponent;
import com.vn.hcmute.team.cortana.mymoney.di.module.ActivityModule;
import com.vn.hcmute.team.cortana.mymoney.di.module.SavingModule;
import com.vn.hcmute.team.cortana.mymoney.model.Currencies;
import com.vn.hcmute.team.cortana.mymoney.model.Saving;
import com.vn.hcmute.team.cortana.mymoney.ui.base.BaseActivity;
import com.vn.hcmute.team.cortana.mymoney.ui.tools.calculator.CalculatorActivity;
import com.vn.hcmute.team.cortana.mymoney.ui.currencies.CurrenciesActivity;
import com.vn.hcmute.team.cortana.mymoney.utils.DateUtil;
import java.util.Calendar;
import java.util.List;
import javax.inject.Inject;

/**
 * Created by kunsubin on 8/29/2017.
 */

public class EditSavingActivity extends BaseActivity implements SavingContract.View {
    
    @BindView(R.id.back_button_saving)
    LinearLayout back_button_saving;
    @BindView(R.id.txt_edit_saving)
    TextView txt_edit_saving;
    @BindView(R.id.image_view_icon_saving)
    ImageView image_view_icon_saving;
    @BindView(R.id.ic_clear_date)
    ImageView ic_clear_date;
    
    @BindView(R.id.edit_text_name_saving)
    EditText edit_text_name_saving;
    @BindView(R.id.txt_goal_money)
    TextView txt_goal_money;
    @BindView(R.id.txt_date_saving)
    TextView txt_date_saving;
    @BindView(R.id.txt_currencies)
    TextView txt_currencies;
    @BindView(R.id.txt_wallet_saving)
    TextView txt_wallet_saving;

    
    private Currencies mCurrencies;
    static final int DATE_DIALOG_ID = 999;
    int day, month, year;
    private Saving mSaving;
    
    @Inject
    SavingPresenter mSavingPresenter;
    
    @Override
    public int getLayoutId() {
        return R.layout.activity_edit_saving;
    }
    
    @Override
    protected void initializeDagger() {
        ApplicationComponent applicationComponent = ((MyMoneyApplication) getApplication())
                  .getAppComponent();
        SavingComponent savingComponent = DaggerSavingComponent
                  .builder()
                  .applicationComponent(applicationComponent)
                  .activityModule(new ActivityModule(this))
                  .savingModule(new SavingModule())
                  .build();
        savingComponent.inject(this);
    }
    
    @Override
    protected void initializePresenter() {
        mPresenter = mSavingPresenter;
        mSavingPresenter.setView(this);
    }
    
    @Override
    protected void onDestroy() {
        mSavingPresenter.unSubscribe();
        super.onDestroy();
    }
    
    @OnClick(R.id.back_button_saving)
    public void onClickBack(View view) {
        finish();
    }
    
    @OnClick(R.id.txt_currencies)
    public void onClickCurrency(View view) {
        Intent intent = new Intent(this, CurrenciesActivity.class);
        startActivityForResult(intent, 4);
    }
    
    @OnClick(R.id.ic_clear_date)
    public void onClickClearDate(View view) {
        txt_date_saving.setText(getString(R.string.ending_date));
        txt_date_saving.setTextColor(ContextCompat.getColor(this, R.color.gray));
        ic_clear_date.setVisibility(View.GONE);
    }
    
    @OnClick(R.id.txt_date_saving)
    public void onClickDate(View view) {
        showDialog(DATE_DIALOG_ID);
        
        /*String a=String.valueOf(DateUtil.getLongAsDate(12,12,2017));
        MyLogger.d("bómdkss",a);*/
    
 
    }
    @OnClick(R.id.txt_edit_saving)
    public void onClickSaving(View view){
        mSaving.setName(edit_text_name_saving.getText().toString().trim());
        String goalMoney=txt_goal_money.getText().toString().trim().substring(1);
        mSaving.setGoalMoney(goalMoney);
        if(!txt_date_saving.getText().toString().trim().equals(getString(R.string.ending_date))){
            String[] arr=txt_date_saving.getText().toString().trim().split("/");
            long tmp=DateUtil.getLongAsDate(Integer.parseInt(arr[0]),Integer.parseInt(arr[1]),Integer.parseInt(arr[2]));
            mSaving.setDate(String.valueOf(tmp));
        }
        
        //saving
        mSavingPresenter.updateSaving(mSaving);
        
    }
    @OnClick(R.id.txt_goal_money)
    public void onClickGoalMoney(View view){
        Intent intent=new Intent(this, CalculatorActivity.class);
        intent.putExtra("goal_money",txt_goal_money.getText());
        startActivityForResult(intent,5);
    }
    @Override
    protected void initializeActionBar(View rootView) {
        getData();
        showData();
        initDatePicker();
    }
    
    public void initDatePicker() {
        final Calendar c = Calendar.getInstance();
        year = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH);
        day = c.get(Calendar.DAY_OF_MONTH);
    }
    
    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case DATE_DIALOG_ID:
                return new DatePickerDialog(this, datePickerListener,
                          year, month, day);
        }
        return null;
    }
    
    private DatePickerDialog.OnDateSetListener datePickerListener
              = new DatePickerDialog.OnDateSetListener() {
        
        // when dialog box is closed, below method will be called.
        public void onDateSet(DatePicker view, int selectedYear,
                  int selectedMonth, int selectedDay) {
            year = selectedYear;
            month = selectedMonth;
            day = selectedDay;
            
            updateDate();
           
        }
    };
    
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        
        if (requestCode == 4) {
            if (resultCode == Activity.RESULT_OK) {
                mCurrencies = (Currencies) data.getSerializableExtra("MyCurrencies");
                
                if (mCurrencies != null) {
                    txt_currencies.setText(mCurrencies.getCurName());
                    mSaving.setIdCurrencies(mCurrencies.getCurId());
                }
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                
            }
        }
        if (requestCode == 5) {
            if (resultCode == Activity.RESULT_OK) {
                String goalMoney=data.getStringExtra("result");
                txt_goal_money.setText("+"+goalMoney);
            }
            if (resultCode == Activity.RESULT_CANCELED) {
            
            }
        }
    }
    public void updateDate(){
        txt_date_saving.setText(new StringBuilder().append(day)
                  .append("/").append(month + 1).append("/").append(year));
        txt_date_saving.setTextColor(ContextCompat.getColor(this, R.color.black));
        ic_clear_date.setVisibility(View.VISIBLE);
    }
    public void getData(){
        Intent intent=getIntent();
        mSaving=(Saving)intent.getSerializableExtra("saving");
    }
    public void showData(){
        edit_text_name_saving.setText(mSaving.getName());
        txt_goal_money.setText("+"+mSaving.getGoalMoney());
        txt_date_saving.setText(DateUtil.convertTimeMillisToDate(mSaving.getDate()));
        
    }
    @Override
    public void showListSaving(List<Saving> savings) {
        
    }
    
    @Override
    public void showSaving() {
        
    }
    
    @Override
    public void onSuccessCreateSaving() {
        
    }
    
    @Override
    public void onSuccessDeleteSaving() {
        
    }
    
    @Override
    public void onSuccessUpdateSaving() {
        Intent returnIntent = new Intent();
        returnIntent.putExtra("saving",mSaving);
        returnIntent.putExtra("currencies",mCurrencies);
        setResult(Activity.RESULT_OK,returnIntent);
        finish();
    }
    
    @Override
    public void onSuccessTakeIn() {
        
    }
    
    @Override
    public void onSuccessTakeOut() {
        
    }
    
    @Override
    public void showError(String message) {
        
    }
    
    @Override
    public void loading(boolean isLoading) {
        
    }
}
