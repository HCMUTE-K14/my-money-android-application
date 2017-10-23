package com.vn.hcmute.team.cortana.mymoney.ui.event;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import com.vn.hcmute.team.cortana.mymoney.model.Event;
import com.vn.hcmute.team.cortana.mymoney.model.Wallet;
import com.vn.hcmute.team.cortana.mymoney.ui.base.BaseActivity;
import com.vn.hcmute.team.cortana.mymoney.ui.base.listener.BaseCallBack;
import com.vn.hcmute.team.cortana.mymoney.usecase.base.Action;
import com.vn.hcmute.team.cortana.mymoney.usecase.remote.WalletUseCase;
import com.vn.hcmute.team.cortana.mymoney.usecase.remote.WalletUseCase.WalletRequest;
import com.vn.hcmute.team.cortana.mymoney.utils.DateUtil;
import com.vn.hcmute.team.cortana.mymoney.utils.DrawableUtil;
import com.vn.hcmute.team.cortana.mymoney.utils.GlideImageLoader;
import com.vn.hcmute.team.cortana.mymoney.utils.logger.MyLogger;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;

/**
 * Created by kunsubin on 9/14/2017.
 */

public class ActivityInfoEvent extends BaseActivity implements EventContract.View {
    
    @BindView(R.id.txt_name_event)
    TextView txt_name_event;
    @BindView(R.id.txt_date_saving)
    TextView txt_date_saving;
    @BindView(R.id.txt_time_rest)
    TextView txt_time_rest;
    @BindView(R.id.txt_name_wallet)
    TextView txt_name_wallet;
    @BindView(R.id.linear_mark_as_finished)
    LinearLayout linear_mark_as_finished;
    @BindView(R.id.image_icon_event)
    ImageView image_icon_event;
    @Inject
    EventPresenter mEventPresenter;
    @Inject
    WalletUseCase mWalletUseCase;
    private Event mEvent;
    private List<Wallet> mWalletList;
    
    @Override
    public int getLayoutId() {
        return R.layout.activity_info_event;
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
        
    }
    
    @Override
    protected void initialize() {
        getData();
        getWallet();
        showData();
    }
    
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState,
              @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        
        
    }
    
    @Override
    public void onBackPressed() {
        onDone();
        super.onBackPressed();
    }
    
    @Override
    protected void onDestroy() {
        mEventPresenter.unSubscribe();
        super.onDestroy();
    }
    
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 17) {
            if (resultCode == Activity.RESULT_OK) {
                mEvent = data.getParcelableExtra("event");
                String walletName = data.getStringExtra("name_wallet");
                txt_name_wallet.setText(walletName);
                showData();
            }
        }
    }
    
    @Override
    public void onSuccessCreateEvent(String message) {
        
    }
    
    @Override
    public void onSuccessUpdateEvent(String message) {
        linear_mark_as_finished.setVisibility(View.GONE);
    }
    
    @Override
    public void onSuccessDeleteEvent(String message) {
        Intent returnIntent = new Intent();
        returnIntent.putExtra("result", mEvent.getEventid());
        setResult(Activity.RESULT_OK, returnIntent);
        finish();
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
    
    /*Area OnClick*/
    @OnClick(R.id.btn_mark_as_finished)
    public void onClickMarkAsFinished(View view) {
        mEvent.setStatus("1");
        mEventPresenter.updateEvent(mEvent);
    }
    
    @OnClick(R.id.btn_list_transaction)
    public void onClickListTransaction(View view) {
        Intent intent=new Intent(this,TransactionEventActivity.class);
        intent.putExtra("event",mEvent);
        startActivity(intent);
    }
    
    @OnClick(R.id.image_view_cancel)
    public void onClickCancel(View view) {
        onDone();
    }
    
    
    @OnClick(R.id.image_view_edit)
    public void onClickEdit(View view) {
        Intent intent = new Intent(this, ActivityEditEvent.class);
        intent.putExtra("event", mEvent);
        intent.putExtra("wallet_name", txt_name_wallet.getText().toString().trim());
        startActivityForResult(intent, 17);
    }
    
    
    @OnClick(R.id.image_view_delete)
    public void onClickDelete(View view) {
        final AlertDialog.Builder doneDialog = new AlertDialog.Builder(this);
        doneDialog.setMessage(getString(R.string.content_delete_saving));
        doneDialog.setNegativeButton(getString(R.string.txt_yes), new OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mEventPresenter.deleteEvent(mEvent.getEventid());
            }
        });
        
        doneDialog.setPositiveButton(getString(R.string.txt_no), new OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        doneDialog.create().show();
    }
    
    /*Area Function*/
    public void getData() {
        Intent intent = getIntent();
        mEvent = intent.getParcelableExtra("event");
    }
    
    public void showData() {
        txt_name_event.setText(mEvent.getName());
        txt_date_saving.setText(DateUtil.convertTimeMillisToDate(mEvent.getDate()));
        
        txt_time_rest.setText(getString(R.string.days_left,
                  DateUtil.getDateLeft(Long.parseLong(mEvent.getDate())) + ""));
        if (mEvent.getIdWallet().trim().equals("")) {
            txt_name_wallet.setText(getString(R.string.all_wallet));
        }
        if (mEvent.getStatus().equals("0")) {
            linear_mark_as_finished.setVisibility(View.VISIBLE);
        } else {
            linear_mark_as_finished.setVisibility(View.GONE);
        }
        GlideImageLoader
                  .load(this, DrawableUtil.getDrawable(this, mEvent.getIcon()), image_icon_event);
    }
    
    public String getNameWallet(List<Wallet> wallets) {
        String name = "";
        
        for (Wallet wallet : wallets) {
            if (wallet.getWalletid().equals(mEvent.getIdWallet())) {
                name = wallet.getWalletName();
                break;
            }
        }
        return name;
    }
    
    public void getWallet() {
        mWalletList = new ArrayList<>();
        WalletUseCase.WalletRequest savingRequest = new WalletRequest(Action.ACTION_GET_WALLET,
                  new BaseCallBack<Object>() {
                      @Override
                      public void onSuccess(Object value) {
                          List<Wallet> wallets = (List<Wallet>) value;
                          mWalletList.addAll(wallets);
                          String tmp = getNameWallet(wallets);
                          if (tmp.equals("")) {
                              txt_name_wallet.setText(getString(R.string.all_wallet));
                          } else {
                              txt_name_wallet.setText(tmp);
                          }
                      }
                      
                      @Override
                      public void onFailure(Throwable throwable) {
                          MyLogger.d("erro get wallet");
                      }
                      
                      @Override
                      public void onLoading() {
                          
                      }
                  }, null, null);
        mWalletUseCase.subscribe(savingRequest);
        
    }
    
    private void onDone() {
        Intent returnIntent = new Intent();
        setResult(Activity.RESULT_CANCELED, returnIntent);
        finish();
    }
}
