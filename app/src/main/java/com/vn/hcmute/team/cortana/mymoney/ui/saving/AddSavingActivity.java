package com.vn.hcmute.team.cortana.mymoney.ui.saving;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.OnClick;
import com.vn.hcmute.team.cortana.mymoney.MyMoneyApplication;
import com.vn.hcmute.team.cortana.mymoney.R;
import com.vn.hcmute.team.cortana.mymoney.data.cache.PreferencesHelper;
import com.vn.hcmute.team.cortana.mymoney.di.component.ApplicationComponent;
import com.vn.hcmute.team.cortana.mymoney.di.component.DaggerSavingComponent;
import com.vn.hcmute.team.cortana.mymoney.di.component.SavingComponent;
import com.vn.hcmute.team.cortana.mymoney.di.module.ActivityModule;
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
import com.vn.hcmute.team.cortana.mymoney.utils.GlideImageLoader;
import com.vn.hcmute.team.cortana.mymoney.utils.SecurityUtil;
import java.util.Calendar;
import java.util.List;
import javax.inject.Inject;

/**
 * Created by kunsubin on 9/7/2017.
 */

public class AddSavingActivity extends BaseActivity implements SavingContract.View {
    
    private static final int DATE_DIALOG_ID = 999;
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
    @Inject
    SavingPresenter mSavingPresenter;
    @Inject
    PreferencesHelper mPreferencesHelper;
    private String mIconSavingDefault;
    private Saving mSaving;
    private int day, month, year;
    private Currencies mCurrencies;
    private Wallet mWallet;
    private String mGoalMoney = "0";
    private ProgressDialog mProgressDialog;
    private DatePickerDialog.OnDateSetListener mDatePickerListener
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
        
    }
    
    @Override
    protected void initialize() {
        initDatePicker();
        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setMessage(this.getString(R.string.txt_progress));
    }
    
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
        
    }
    
    @Override
    protected void onDestroy() {
        mSavingPresenter.unSubscribe();
        super.onDestroy();
    }
    
    
    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case DATE_DIALOG_ID:
                return new DatePickerDialog(this, mDatePickerListener,
                          year, month, day);
        }
        return null;
    }
    
    
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        
        if (requestCode == 6) {
            if (resultCode == Activity.RESULT_OK) {
                mCurrencies = data.getParcelableExtra("currency");
                
                if (mCurrencies != null) {
                    txt_currencies.setText(mCurrencies.getCurName());
                    
                }
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                
            }
        }
        if (requestCode == 7) {
            if (resultCode == Activity.RESULT_OK) {
                //TODO: View, Result
                String goalMoney2Show = data.getStringExtra("result_view");
                mGoalMoney = data.getStringExtra("result");
                txt_goal_money.setText("+" + goalMoney2Show);
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
        if (requestCode == 25) {
            if (resultCode == Activity.RESULT_OK) {
                Icon icon = data.getParcelableExtra("icon");
                if (icon != null) {
                    mIconSavingDefault = icon.getImage();
                    showIcon();
                }
                
            }
        }
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
        mProgressDialog.dismiss();
    }
    
    @Override
    public void loading(boolean isLoading) {
        if (isLoading) {
            mProgressDialog.show();
        } else {
            mProgressDialog.dismiss();
        }
    }
    
    /*Area OnClick*/
    @OnClick(R.id.cancel_button)
    public void onClickCancel(View view) {
        finish();
    }
    
    @OnClick(R.id.linear_select_date)
    public void onClickSelectDate(View view) {
        showDialog(DATE_DIALOG_ID);
    }
    
    @OnClick(R.id.ic_clear_date)
    public void onClickClearDate(View view) {
        txt_date_saving.setText(getString(R.string.ending_date));
        txt_date_saving.setTextColor(ContextCompat.getColor(this, R.color.gray));
        ic_clear_date.setVisibility(View.GONE);
    }
    
    @OnClick(R.id.linear_currencies)
    public void onClickCurrency(View view) {
        Intent intent = new Intent(this, CurrenciesActivity.class);
        startActivityForResult(intent, 6);
    }
    
    @OnClick(R.id.linear_goal_money)
    public void onClickGoalMoney(View view) {
        Intent intent = new Intent(this, CalculatorActivity.class);
        intent.putExtra("goal_money", mGoalMoney);
        intent.putExtra("currencies", mCurrencies);
        startActivityForResult(intent, 7);
    }
    
    @OnClick(R.id.linear_icon_saving)
    public void onClickSelectIcon(View view) {
        Intent intent = new Intent(this, SelectIconActivity.class);
        startActivityForResult(intent, 25);
    }
    
    @OnClick(R.id.txt_edit_saving)
    public void onClickSaveAdd(View view) {
        if (edit_text_name_saving.getText().toString().trim().equals("")) {
            alertDiaglog(getString(R.string.enter_your_name));
            return;
        }
        if (txt_date_saving.getText().toString().trim().equals(getString(R.string.ending_date))) {
            alertDiaglog(getString(R.string.select_date));
            return;
        }
        if (mGoalMoney.equals("0")) {
            alertDiaglog(getString(R.string.input_money));
            return;
        }
        //check date
        if (!checkDate()) {
            alertDiaglog(getString(R.string.small_date));
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
    
    /*Area Function*/
    public void init() {
        mSaving = new Saving();
        mCurrencies = new Currencies();
        mWallet = new Wallet();
        setCurrenciesDefault();
        txt_currencies.setText(mCurrencies.getCurName());
        mIconSavingDefault = "ic_saving";
        
        showIcon();
    }
    
    public void initDatePicker() {
        final Calendar c = Calendar.getInstance();
        year = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH);
        day = c.get(Calendar.DAY_OF_MONTH);
    }
    
    public void setCurrenciesDefault() {
        mCurrencies.setCurId("4");
        mCurrencies.setCurName("Việt Nam Đồng");
        mCurrencies.setCurCode("VND");
        mCurrencies.setCurSymbol("₫");
        mCurrencies.setCurDisplayType("cur_display_type");
    }
    
    public void showIcon() {
        GlideImageLoader.load(this, DrawableUtil.getDrawable(this, mIconSavingDefault),
                  image_view_icon_saving);
    }
    
    public void setSaving() {
        mSaving.setSavingid(SecurityUtil.getRandomUUID());
        mSaving.setName(edit_text_name_saving.getText().toString().trim());
        mSaving.setGoalMoney(mGoalMoney);
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
        mSaving.setIcon(mIconSavingDefault);
        mSaving.setCurrencies(mCurrencies);
        
    }
    
    public void updateDate() {
        txt_date_saving.setText(new StringBuilder().append(day)
                  .append("/").append(month + 1).append("/").append(year));
        txt_date_saving.setTextColor(ContextCompat.getColor(this, R.color.black));
        ic_clear_date.setVisibility(View.VISIBLE);
    }
    
    public boolean checkDate() {
        String[] arr = txt_date_saving.getText().toString().trim().split("/");
        long tmp = DateUtil
                  .getLongAsDate(Integer.parseInt(arr[0]), Integer.parseInt(arr[1]),
                            Integer.parseInt(arr[2]));
        return System.currentTimeMillis() >= tmp ? false : true;
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
