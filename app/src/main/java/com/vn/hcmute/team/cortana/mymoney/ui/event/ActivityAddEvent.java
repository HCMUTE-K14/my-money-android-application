package com.vn.hcmute.team.cortana.mymoney.ui.event;

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
import android.widget.TextView;
import butterknife.BindView;
import butterknife.OnClick;
import com.vn.hcmute.team.cortana.mymoney.MyMoneyApplication;
import com.vn.hcmute.team.cortana.mymoney.R;
import com.vn.hcmute.team.cortana.mymoney.data.cache.PreferencesHelper;
import com.vn.hcmute.team.cortana.mymoney.di.component.ApplicationComponent;
import com.vn.hcmute.team.cortana.mymoney.di.component.DaggerEventComponent;
import com.vn.hcmute.team.cortana.mymoney.di.component.EventComponent;
import com.vn.hcmute.team.cortana.mymoney.di.module.ActivityModule;
import com.vn.hcmute.team.cortana.mymoney.di.module.EventModule;
import com.vn.hcmute.team.cortana.mymoney.model.Currencies;
import com.vn.hcmute.team.cortana.mymoney.model.Event;
import com.vn.hcmute.team.cortana.mymoney.model.Wallet;
import com.vn.hcmute.team.cortana.mymoney.ui.base.BaseActivity;
import com.vn.hcmute.team.cortana.mymoney.ui.currencies.CurrenciesActivity;
import com.vn.hcmute.team.cortana.mymoney.ui.wallet.MyWalletActivity;
import com.vn.hcmute.team.cortana.mymoney.utils.DateUtil;
import com.vn.hcmute.team.cortana.mymoney.utils.SecurityUtil;
import java.util.Calendar;
import java.util.List;
import javax.inject.Inject;

/**
 * Created by kunsubin on 9/15/2017.
 */

public class ActivityAddEvent extends BaseActivity implements EventContract.View {
    
    static final int DATE_DIALOG_ID = 999;
    @BindView(R.id.edit_text_name_event)
    EditText edit_text_name_event;
    @BindView(R.id.txt_date_event)
    TextView txt_date_event;
    @BindView(R.id.ic_clear_date)
    ImageView ic_clear_date;
    @BindView(R.id.txt_currencies)
    TextView txt_currencies;
    @BindView(R.id.txt_wallet_event)
    TextView txt_wallet_event;
    int day, month, year;
    @Inject
    EventPresenter mEventPresenter;
    @Inject
    PreferencesHelper mPreferencesHelper;
    private Currencies mCurrencies;
    private Wallet mWallet;
    private Event mEvent;
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
        return R.layout.activity_add_event;
    }
    
    @Override
    protected void initializeDagger() {
        ApplicationComponent applicationComponent = ((MyMoneyApplication) this.getApplication())
                  .getAppComponent();
        EventComponent eventComponent = DaggerEventComponent
                  .builder()
                  .applicationComponent(applicationComponent)
                  .activityModule(new ActivityModule(this))
                  .eventModule(new EventModule())
                  .build();
        eventComponent.inject(this);
    }
    
    @Override
    protected void initializePresenter() {
        mPresenter = mEventPresenter;
        mEventPresenter.setView(this);
        
    }
    
    @Override
    protected void initializeActionBar(View rootView) {
        initDatePicker();
        init();
        initView();
    }
    
    public void init() {
        mEvent = new Event();
        mCurrencies = new Currencies();
        setCurrenciesDefault();
        mWallet = new Wallet();
    }
    
    public void initView() {
        txt_date_event.setText(getString(R.string.ending_date));
        txt_date_event.setTextColor(ContextCompat.getColor(this, R.color.gray));
        ic_clear_date.setVisibility(View.GONE);
    }
    
    public void setCurrenciesDefault() {
        mCurrencies.setCurId("4");
        mCurrencies.setCurName("Việt Nam Đồng");
        mCurrencies.setCurCode("VND");
        mCurrencies.setCurSymbol("₫");
        mCurrencies.setCurDisplayType("cur_display_type");
    }
    
    @OnClick(R.id.ic_cancel_event)
    public void onClickCancel(View view) {
        finish();
    }
    
    @OnClick(R.id.txt_date_event)
    public void onClickDate(View view) {
        showDialog(DATE_DIALOG_ID);
    }
    
    @OnClick(R.id.txt_currencies)
    public void onClickSelectCurrency(View view) {
        Intent intent = new Intent(this, CurrenciesActivity.class);
        startActivityForResult(intent, 20);
    }
    
    @OnClick(R.id.txt_wallet_event)
    public void onClickSelectWallet(View view) {
        Intent intent = new Intent(this, MyWalletActivity.class);
        startActivityForResult(intent, 21);
    }
    
    @OnClick(R.id.txt_edit_event)
    public void onClickSaveAddEvent() {
        if (edit_text_name_event.getText().toString().trim().equals("")) {
            alertDiaglog(getString(R.string.enter_your_name));
            return;
        }
        if (txt_date_event.getText().toString().trim().equals(getString(R.string.ending_date))) {
            alertDiaglog(getString(R.string.select_date));
            return;
        }
        //set event
        setEvent();
        
        mEventPresenter.createEvent(mEvent);
    }
    
    public void setEvent() {
        mEvent.setEventid(SecurityUtil.getRandomUUID());
        mEvent.setName(edit_text_name_event.getText().toString().trim());
        mEvent.setCurrencies(mCurrencies);
        mEvent.setDate(getDate());
        mEvent.setIdWallet(mWallet.getWalletid());
        mEvent.setUserid(mPreferencesHelper.getUserId());
        mEvent.setMoney("0");
        mEvent.setStatus("0");
    }
    
    public String getDate() {
        String[] arr = txt_date_event.getText().toString().trim().split("/");
        int a = Integer.parseInt(arr[0]);
        int b = Integer.parseInt(arr[1]);
        int c = Integer.parseInt(arr[2]);
        long timeTimeMillis = DateUtil.getLongAsDate(a, b, c);
        return String.valueOf(timeTimeMillis);
    }
    
    @OnClick(R.id.ic_clear_date)
    public void onClickClearDate(View view) {
        txt_date_event.setText(getString(R.string.ending_date));
        txt_date_event.setTextColor(ContextCompat.getColor(this, R.color.gray));
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
        txt_date_event.setText(new StringBuilder().append(day)
                  .append("/").append(month + 1).append("/").append(year));
        txt_date_event.setTextColor(ContextCompat.getColor(this, R.color.black));
        ic_clear_date.setVisibility(View.VISIBLE);
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 20) {
            if (resultCode == Activity.RESULT_OK) {
                mCurrencies = data.getParcelableExtra("currency");
                txt_currencies.setText(mCurrencies.getCurName());
            }
        }
        if (requestCode == 21) {
            if (resultCode == Activity.RESULT_OK) {
                mWallet = data.getParcelableExtra("wallet");
                txt_wallet_event.setText(mWallet.getWalletName());
            }
        }
    }
    
    @Override
    public void onSuccessCreateEvent(String message) {
        
        Intent returnIntent = new Intent();
        returnIntent.putExtra("event", mEvent);
        setResult(Activity.RESULT_OK, returnIntent);
        finish();
    }
    
    @Override
    public void onSuccessUpdateEvent(String message) {
        
    }
    
    @Override
    public void onSuccessDeleteEvent(String message) {
        
    }
    
    @Override
    public void onSuccessGetListEvent(List<Event> events) {
        
    }
    
    @Override
    public void onFailure(String message) {
        
    }
    
    @Override
    public void loading(boolean isLoading) {
        
    }
}
