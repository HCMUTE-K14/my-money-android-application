package com.vn.hcmute.team.cortana.mymoney.ui.event;

import static com.vn.hcmute.team.cortana.mymoney.R.id.linear_icon_event;

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
import com.vn.hcmute.team.cortana.mymoney.di.component.ApplicationComponent;
import com.vn.hcmute.team.cortana.mymoney.di.component.DaggerEventComponent;
import com.vn.hcmute.team.cortana.mymoney.di.component.EventComponent;
import com.vn.hcmute.team.cortana.mymoney.di.module.ActivityModule;
import com.vn.hcmute.team.cortana.mymoney.di.module.EventModule;
import com.vn.hcmute.team.cortana.mymoney.di.module.GlideApp;
import com.vn.hcmute.team.cortana.mymoney.model.Currencies;
import com.vn.hcmute.team.cortana.mymoney.model.Event;
import com.vn.hcmute.team.cortana.mymoney.model.Icon;
import com.vn.hcmute.team.cortana.mymoney.model.Wallet;
import com.vn.hcmute.team.cortana.mymoney.ui.base.BaseActivity;
import com.vn.hcmute.team.cortana.mymoney.ui.currencies.CurrenciesActivity;
import com.vn.hcmute.team.cortana.mymoney.ui.iconshop.SelectIconActivity;
import com.vn.hcmute.team.cortana.mymoney.ui.wallet.MyWalletActivity;
import com.vn.hcmute.team.cortana.mymoney.utils.DateUtil;
import com.vn.hcmute.team.cortana.mymoney.utils.DrawableUtil;
import java.util.Calendar;
import java.util.List;
import javax.inject.Inject;

/**
 * Created by kunsubin on 9/14/2017.
 */

public class ActivityEditEvent extends BaseActivity implements EventContract.View {
    
    static final int DATE_DIALOG_ID = 999;
    @BindView(R.id.edit_text_name_event)
    EditText edit_text_name_event;
    @BindView(R.id.txt_date_event)
    TextView txt_date_event;
    @BindView(R.id.txt_currencies)
    TextView txt_currencies;
    @BindView(R.id.txt_wallet_event)
    TextView txt_wallet_event;
    @BindView(R.id.ic_clear_date)
    ImageView ic_clear_date;
    @BindView(R.id.image_view_icon_event)
    ImageView image_view_icon_event;
    
    int day, month, year;
    @Inject
    EventPresenter mEventPresenter;
    private Event mEvent;
    private String mWalletName;
    private Wallet mWallet;
    private Currencies mCurrencies;
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
        return R.layout.activity_edit_event;
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
    protected void onDestroy() {
        mEventPresenter.unSubscribe();
        super.onDestroy();
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
    
    public void getData() {
        Intent intent = getIntent();
        mEvent = intent.getParcelableExtra("event");
        mWalletName = intent.getStringExtra("wallet_name");
        mWallet = new Wallet();
        mWallet.setWalletid(mEvent.getIdWallet());
        mCurrencies = mEvent.getCurrencies();
    }
    
    public void showData() {
        edit_text_name_event.setText(mEvent.getName());
        txt_date_event.setText(DateUtil.convertTimeMillisToDate(mEvent.getDate()));
        txt_currencies.setText(mEvent.getCurrencies().getCurName());
        if (mEvent.getIdWallet().equals("")) {
            txt_wallet_event.setText(getString(R.string.all_wallet));
        } else {
            txt_wallet_event.setText(mWalletName);
        }
        showIcon();
    }
    public void showIcon(){
        GlideApp.with(this)
                  .load(DrawableUtil.getDrawable(this, mEvent.getIcon()))
                  .placeholder(R.drawable.folder_placeholder)
                  .error(R.drawable.folder_placeholder)
                  .dontAnimate()
                  .into(image_view_icon_event);
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
    
    @OnClick(R.id.back_button_event)
    public void onClickBack(View view) {
        finish();
    }
    
    @OnClick(R.id.txt_edit_event)
    public void onClickSaveEditEvent(View view) {
        
        if (edit_text_name_event.getText().toString().trim().equals("")) {
            alertDiaglog(getString(R.string.enter_your_name));
            return;
        }
        
        mEvent.setCurrencies(mCurrencies);
        mEvent.setIdWallet(mWallet.getWalletid());
        mEvent.setName(edit_text_name_event.getText().toString().trim());
        mEvent.setDate(getDate());
        
        mEventPresenter.updateEvent(mEvent);
        
    }
    
    public String getDate() {
        String[] arr = txt_date_event.getText().toString().trim().split("/");
        int a = Integer.parseInt(arr[0]);
        int b = Integer.parseInt(arr[1]);
        int c = Integer.parseInt(arr[2]);
        long timeTimeMillis = DateUtil.getLongAsDate(a, b, c);
        return String.valueOf(timeTimeMillis);
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
    
    @OnClick(R.id.linear_date)
    public void onClickDate(View view) {
        showDialog(DATE_DIALOG_ID);
    }
    
    @OnClick(R.id.linear_currencies)
    public void onClickSelectCurrency(View view) {
        Intent intent = new Intent(this, CurrenciesActivity.class);
        startActivityForResult(intent, 18);
    }
    
    @OnClick(R.id.linear_wallet)
    public void onClickSelectWallet(View view) {
        Intent intent = new Intent(this, MyWalletActivity.class);
        startActivityForResult(intent, 19);
    }
    
    @OnClick(R.id.ic_clear_date)
    public void onClickClearDate(View view) {
        txt_date_event.setText(getString(R.string.ending_date));
        txt_date_event.setTextColor(ContextCompat.getColor(this, R.color.gray));
        ic_clear_date.setVisibility(View.GONE);
    }
    @OnClick(linear_icon_event)
    public void onClickLinearIcon(View view){
        Intent intent=new Intent(this, SelectIconActivity.class);
        startActivityForResult(intent,27);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 18) {
            if (resultCode == Activity.RESULT_OK) {
                mCurrencies = data.getParcelableExtra("currency");
                txt_currencies.setText(mCurrencies.getCurName());
            }
        }
        if (requestCode == 19) {
            if (resultCode == Activity.RESULT_OK) {
                mWallet = data.getParcelableExtra("wallet");
                txt_wallet_event.setText(mWallet.getWalletName());
            }
        }
        if(requestCode==27){
            if(resultCode==Activity.RESULT_OK){
                Icon icon=data.getParcelableExtra("icon");
                if(icon!=null){
                    mEvent.setIcon(icon.getImage());
                    showIcon();
                }
            }
        }
    }
    
    @Override
    public void onSuccessCreateEvent(String message) {
        
    }
    
    @Override
    public void onSuccessUpdateEvent(String message) {
        Intent returnIntent = new Intent();
        returnIntent.putExtra("event", mEvent);
        returnIntent.putExtra("name_wallet", txt_wallet_event.getText().toString().trim());
        setResult(Activity.RESULT_OK, returnIntent);
        finish();
    }
    
    @Override
    public void onSuccessDeleteEvent(String message) {
        
    }
    
    @Override
    public void onSuccessGetListEvent(List<Event> events) {
        
    }
    
    @Override
    public void onFailure(String message) {
        alertDiaglog(message);
    }
    
    @Override
    public void loading(boolean isLoading) {
        
    }
    
}
