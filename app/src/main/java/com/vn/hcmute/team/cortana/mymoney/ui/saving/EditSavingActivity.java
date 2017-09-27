package com.vn.hcmute.team.cortana.mymoney.ui.saving;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.OnClick;
import com.vn.hcmute.team.cortana.mymoney.MyMoneyApplication;
import com.vn.hcmute.team.cortana.mymoney.R;
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
import com.vn.hcmute.team.cortana.mymoney.utils.logger.MyLogger;
import java.util.Calendar;
import java.util.List;
import javax.inject.Inject;

/**
 * Created by kunsubin on 8/29/2017.
 */

public class EditSavingActivity extends BaseActivity implements SavingContract.View {
    
    static final int DATE_DIALOG_ID = 999;
    @BindView(R.id.back_button_saving)
    LinearLayout back_button_saving;
    @BindView(R.id.txt_edit_saving)
    TextView txt_edit_saving;
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
    @BindView(R.id.image_view_icon_saving)
    ImageView image_view_icon_saving;
    @BindView(R.id.linear_icon_saving)
    LinearLayout linear_icon_saving;
    
    int day, month, year;
    @Inject
    SavingPresenter mSavingPresenter;
    private String mWalletName;
    private Wallet mWallet;
    private Currencies mCurrencies;
    private Saving mSaving;
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
    protected void initializeActionBar(View rootView) {
        
    }
    @Override
    protected void initialize() {
        getData();
        showData();
        initDatePicker();
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
    
    @OnClick(R.id.linear_currencies)
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
    
    @OnClick(R.id.linear_date)
    public void onClickDate(View view) {
        showDialog(DATE_DIALOG_ID);
    }
    
    @OnClick(R.id.txt_edit_saving)
    public void onClickSaving(View view) {
        if (edit_text_name_saving.getText().toString().trim().equals("")) {
            alertDiaglog(getString(R.string.enter_your_name));
            return;
        }
        if (txt_date_saving.getText().toString().trim()
                  .equals(getString(R.string.ending_date))) {
            alertDiaglog(getString(R.string.select_date));
            return;
        }
        //check date
        if (!checkDate()) {
            alertDiaglog(getString(R.string.small_date));
            return;
        }
        mSaving.setName(edit_text_name_saving.getText().toString().trim());
        String goalMoney = txt_goal_money.getText().toString().trim().substring(1);
        mSaving.setGoalMoney(goalMoney);
        mSaving.setCurrencies(mCurrencies);
        mSaving.setIdWallet(mWallet.getWalletid());
        //set date
        mSaving.setDate(convertDateToMillisecond());
    
        //saving
        mSavingPresenter.updateSaving(mSaving);
        
        MyLogger.d("ksdfj", "Lnag thang co nha nha");
        
        
    }
    public boolean checkDate(){
        String[] arrDate = txt_date_saving.getText().toString().trim().split("/");
        long temp = DateUtil
                  .getLongAsDate(Integer.parseInt(arrDate[0]), Integer.parseInt(arrDate[1]),
                            Integer.parseInt(arrDate[2]));
        return System.currentTimeMillis() >= temp ? false:true;
        
    }
    public String convertDateToMillisecond(){
        String[] arr = txt_date_saving.getText().toString().trim().split("/");
        long tmp = DateUtil
                  .getLongAsDate(Integer.parseInt(arr[0]), Integer.parseInt(arr[1]),
                            Integer.parseInt(arr[2]));
        return String.valueOf(tmp);
    }
    @OnClick(R.id.linear_goal_money)
    public void onClickGoalMoney(View view) {
        Intent intent = new Intent(this, CalculatorActivity.class);
        intent.putExtra("goal_money", txt_goal_money.getText());
        startActivityForResult(intent, 5);
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
    
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        
        if (requestCode == 4) {
            if (resultCode == Activity.RESULT_OK) {
                mCurrencies = (Currencies) data.getParcelableExtra("currency");
                if (mCurrencies != null) {
                    txt_currencies.setText(mCurrencies.getCurName());
                }
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                
            }
        }
        if (requestCode == 5) {
            if (resultCode == Activity.RESULT_OK) {
                String goalMoney = data.getStringExtra("result");
                txt_goal_money.setText("+" + goalMoney);
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                
            }
        }
        if (requestCode == 14) {
            if (resultCode == Activity.RESULT_OK) {
                mWallet = data.getParcelableExtra("wallet");
                txt_wallet_saving.setText(mWallet.getWalletName());
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                
            }
        }
        if(requestCode==26){
            if(resultCode==Activity.RESULT_OK){
                Icon icon=data.getParcelableExtra("icon");
                if(icon!=null){
                    mSaving.setIcon(icon.getImage());
                    showIcon();
                }
            }
        }
    }
    
    public void updateDate() {
        txt_date_saving.setText(new StringBuilder().append(day)
                  .append("/").append(month + 1).append("/").append(year));
        txt_date_saving.setTextColor(ContextCompat.getColor(this, R.color.black));
        ic_clear_date.setVisibility(View.VISIBLE);
    }
    
    public void getData() {
        Intent intent = getIntent();
        mSaving = (Saving) intent.getParcelableExtra("saving");
        mWalletName = intent.getStringExtra("wallet_name");
        mCurrencies = new Currencies();
        mWallet = new Wallet();
    }
    
    //set defaut
    public void showData() {
        edit_text_name_saving.setText(mSaving.getName());
        txt_goal_money.setText("+" + mSaving.getGoalMoney());
        txt_date_saving.setText(DateUtil.convertTimeMillisToDate(mSaving.getDate()));
        txt_currencies.setText(mSaving.getCurrencies().getCurName());
        txt_wallet_saving.setText(mWalletName);
        showIcon();
        
        
        //init defaut
        mWallet.setWalletid(mSaving.getIdWallet());
        mCurrencies = mSaving.getCurrencies();
        
        
    }
    
    @OnClick(R.id.linear_wallet)
    public void onClickSelectWallet(View view) {
        Intent intent = new Intent(this, MyWalletActivity.class);
        startActivityForResult(intent, 14);
    }
    @OnClick(R.id.linear_icon_saving)
    public void onClickLinearIcon(View view){
        Intent intent =new Intent(this, SelectIconActivity.class);
        startActivityForResult(intent,26);
    }
    public void showIcon(){
        GlideApp.with(this)
                  .load(DrawableUtil.getDrawable(this, mSaving.getIcon()))
                  .placeholder(R.drawable.folder_placeholder)
                  .error(R.drawable.folder_placeholder)
                  .dontAnimate()
                  .into(image_view_icon_saving);
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
        MyLogger.d("ksdfj", "Lnag thang khong nha");
        Toast.makeText(this, "sdjkfdsk", Toast.LENGTH_LONG).show();
        Intent returnIntent = new Intent();
        returnIntent.putExtra("saving", mSaving);
        returnIntent.putExtra("name_wallet", txt_wallet_saving.getText().toString().trim());
        setResult(Activity.RESULT_OK, returnIntent);
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
        alertDiaglog(message);
    }
    
    @Override
    public void loading(boolean isLoading) {
        
    }
}
