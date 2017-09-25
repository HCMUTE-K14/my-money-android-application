package com.vn.hcmute.team.cortana.mymoney.ui.saving;


import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.OnClick;
import com.vn.hcmute.team.cortana.mymoney.MyMoneyApplication;
import com.vn.hcmute.team.cortana.mymoney.R;
import com.vn.hcmute.team.cortana.mymoney.data.cache.PreferencesHelper;
import com.vn.hcmute.team.cortana.mymoney.di.component.ApplicationComponent;
import com.vn.hcmute.team.cortana.mymoney.di.component.DaggerSavingComponent;
import com.vn.hcmute.team.cortana.mymoney.di.component.SavingComponent;
import com.vn.hcmute.team.cortana.mymoney.di.module.ActivityModule;
import com.vn.hcmute.team.cortana.mymoney.di.module.GlideApp;
import com.vn.hcmute.team.cortana.mymoney.di.module.SavingModule;
import com.vn.hcmute.team.cortana.mymoney.model.Currencies;
import com.vn.hcmute.team.cortana.mymoney.model.Icon;
import com.vn.hcmute.team.cortana.mymoney.model.Saving;
import com.vn.hcmute.team.cortana.mymoney.model.Wallet;
import com.vn.hcmute.team.cortana.mymoney.ui.base.BaseActivity;
import com.vn.hcmute.team.cortana.mymoney.ui.currencies.CurrenciesActivity;
import com.vn.hcmute.team.cortana.mymoney.ui.iconshop.SelectIconActivity;
import com.vn.hcmute.team.cortana.mymoney.ui.tools.calculator.CalculatorActivity;
import com.vn.hcmute.team.cortana.mymoney.ui.wallet.MyWalletActivity;
import com.vn.hcmute.team.cortana.mymoney.utils.DateUtil;
import com.vn.hcmute.team.cortana.mymoney.utils.DrawableUtil;
import com.vn.hcmute.team.cortana.mymoney.utils.SecurityUtil;
import java.util.Calendar;
import java.util.List;
import javax.inject.Inject;

/**
 * Created by kunsubin on 9/7/2017.
 */

public class AddSavingActivity extends BaseActivity implements SavingContract.View {
    
    static final int DATE_DIALOG_ID = 999;
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
    @BindView(R.id.ic_clear_date)
    ImageView ic_clear_date;
    @BindView(R.id.image_view_icon_saving)
    ImageView image_view_icon_saving;
    
    
    String ic_saving_defaut;
    Saving mSaving;
    int day, month, year;
    @Inject
    SavingPresenter mSavingPresenter;
    @Inject
    PreferencesHelper mPreferencesHelper;
    private Currencies mCurrencies;
    private Wallet mWallet;
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
    public int getLayoutId() {
        return R.layout.activity_add_saving;
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
    protected void initializeActionBar(View rootView) {
        initDatePicker();
    }
    
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        //
       init();
        
    }
    public void init(){
        mSaving = new Saving();
        mCurrencies = new Currencies();
        mWallet = new Wallet();
        setCurrenciesDefault();
        txt_currencies.setText(mCurrencies.getCurName());
        ic_saving_defaut="ic_saving";
    
        showIcon();
    }
    public void setCurrenciesDefault() {
        mCurrencies.setCurId("4");
        mCurrencies.setCurName("Việt Nam Đồng");
        mCurrencies.setCurCode("VND");
        mCurrencies.setCurSymbol("₫");
        mCurrencies.setCurDisplayType("cur_display_type");
    }
    public void showIcon(){
        GlideApp.with(this)
                  .load(DrawableUtil.getDrawable(this, ic_saving_defaut))
                  .placeholder(R.drawable.folder_placeholder)
                  .error(R.drawable.folder_placeholder)
                  .dontAnimate()
                  .into(image_view_icon_saving);
    }
    @Override
    protected void onDestroy() {
        mSavingPresenter.unSubscribe();
        super.onDestroy();
    }
    
    public void setSaving() {
        
        mSaving.setSavingid(SecurityUtil.getRandomUUID());
        mSaving.setName(edit_text_name_saving.getText().toString().trim());
        mSaving.setGoalMoney(txt_goal_money.getText().toString().substring(1));
        mSaving.setCurrentMoney("0");
        mSaving.setStartMoney("0");
        
        if (!txt_date_saving.getText().toString().trim().equals(getString(R.string.ending_date))) {
            String[] arr = txt_date_saving.getText().toString().trim().split("/");
            long tmp = DateUtil
                      .getLongAsDate(Integer.parseInt(arr[0]), Integer.parseInt(arr[1]),
                                Integer.parseInt(arr[2]));
            mSaving.setDate(String.valueOf(tmp));
        }
        
        mSaving.setIdWallet(mWallet.getWalletid());
        
        mSaving.setStatus("0");
        mSaving.setUserid(mPreferencesHelper.getUserId());
        mSaving.setIcon(ic_saving_defaut);
        mSaving.setCurrencies(mCurrencies);
        
    }
    
    @OnClick(R.id.cancel_button)
    public void onClickCancel(View view) {
        finish();
    }
    
    @OnClick(R.id.txt_date_saving)
    public void onClickSelectDate(View view) {
        showDialog(DATE_DIALOG_ID);
    }
    
    @OnClick(R.id.ic_clear_date)
    public void onClickClearDate(View view) {
        txt_date_saving.setText(getString(R.string.ending_date));
        txt_date_saving.setTextColor(ContextCompat.getColor(this, R.color.gray));
        ic_clear_date.setVisibility(View.GONE);
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
    
    public void updateDate() {
        txt_date_saving.setText(new StringBuilder().append(day)
                  .append("/").append(month + 1).append("/").append(year));
        txt_date_saving.setTextColor(ContextCompat.getColor(this, R.color.black));
        ic_clear_date.setVisibility(View.VISIBLE);
    }
    
    @OnClick(R.id.txt_currencies)
    public void onClickCurrency(View view) {
        Intent intent = new Intent(this, CurrenciesActivity.class);
        startActivityForResult(intent, 6);
    }
    
    @OnClick(R.id.txt_goal_money)
    public void onClickGoalMoney(View view) {
        Intent intent = new Intent(this, CalculatorActivity.class);
        intent.putExtra("goal_money", txt_goal_money.getText());
        startActivityForResult(intent, 7);
    }
    @OnClick(R.id.linear_icon_saving)
    public void onClickSelectIcon(View view){
        Intent intent=new Intent(this, SelectIconActivity.class);
        startActivityForResult(intent,25);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        
        if (requestCode == 6) {
            if (resultCode == Activity.RESULT_OK) {
                mCurrencies = (Currencies) data.getParcelableExtra("currency");
                
                if (mCurrencies != null) {
                    txt_currencies.setText(mCurrencies.getCurName());
                    mSaving.setIdCurrencies(mCurrencies.getCurId());
                }
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                
            }
        }
        if (requestCode == 7) {
            if (resultCode == Activity.RESULT_OK) {
                String goalMoney = data.getStringExtra("result");
                txt_goal_money.setText("+" + goalMoney);
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                
            }
        }
        if (requestCode == 13) {
            if (resultCode == Activity.RESULT_OK) {
                mWallet = data.getParcelableExtra("wallet");
                txt_wallet_saving.setText(mWallet.getWalletName());
                
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                
            }
        }
        if(requestCode==25){
            if(resultCode==Activity.RESULT_OK){
                Icon icon=data.getParcelableExtra("icon");
                if(icon!=null){
                    ic_saving_defaut=icon.getImage();
                    showIcon();
                }
                
            }
        }
    }
    @OnClick(R.id.txt_edit_saving)
    public void onClickSaveAdd(View view) {
        if (edit_text_name_saving.getText().toString().trim().equals("")) {
            Toast.makeText(this, getString(R.string.name_saving), Toast.LENGTH_LONG).show();
            return;
        }
        if (txt_date_saving.getText().toString().trim().equals(getString(R.string.ending_date))) {
            Toast.makeText(this, getString(R.string.select_date), Toast.LENGTH_LONG).show();
            return;
        }
        if (txt_goal_money.getText().toString().substring(1).trim().equals("0")) {
            Toast.makeText(this, getString(R.string.input_money), Toast.LENGTH_LONG).show();
            return;
        }
        //check date
        String[] arr = txt_date_saving.getText().toString().trim().split("/");
        long tmp = DateUtil
                  .getLongAsDate(Integer.parseInt(arr[0]), Integer.parseInt(arr[1]),
                            Integer.parseInt(arr[2]));
        if (System.currentTimeMillis() >= tmp) {
            Toast.makeText(this, getString(R.string.small_date), Toast.LENGTH_LONG).show();
            return;
        }
        setSaving();
        mSavingPresenter.createSaving(mSaving);
        
    }
    
    @OnClick(R.id.linear_select_wallet)
    public void onClickSelectWallet(View view) {
        Intent intent = new Intent(this, MyWalletActivity.class);
        startActivityForResult(intent, 13);
    }
    
    @Override
    public void showListSaving(List<Saving> savings) {
        
    }
    
    @Override
    public void showSaving() {
        
    }
    
    @Override
    public void onSuccessCreateSaving() {
        Intent returnIntent = new Intent();
        returnIntent.putExtra("resultAdd", mSaving);
        setResult(Activity.RESULT_OK, returnIntent);
        finish();
        
    }
    
    @Override
    public void onSuccessDeleteSaving() {
        
    }
    
    @Override
    public void onSuccessUpdateSaving() {
        
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
