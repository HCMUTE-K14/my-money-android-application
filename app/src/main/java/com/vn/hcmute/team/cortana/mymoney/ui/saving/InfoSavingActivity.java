package com.vn.hcmute.team.cortana.mymoney.ui.saving;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.SeekBar;
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
import com.vn.hcmute.team.cortana.mymoney.utils.DateUtil;
import java.util.List;
import javax.inject.Inject;

/**
 * Created by kunsubin on 8/28/2017.
 */

public class InfoSavingActivity extends BaseActivity implements SavingContract.View {
    
    @BindView(R.id.txt_saving_name)
    TextView txt_saving_name;
    @BindView(R.id.txt_money_goal)
    TextView txt_money_goal;
    @BindView(R.id.txt_current_money)
    TextView txt_current_money;
    @BindView(R.id.txt_need_money)
    TextView txt_need_money;
    @BindView(R.id.txt_date_saving)
    TextView txt_date_saving;
    @BindView(R.id.txt_time_rest)
    TextView txt_time_rest;
    @BindView(R.id.txt_name_wallet)
    TextView txt_name_wallet;
    @BindView(R.id.seek_bar_saving_info)
    SeekBar seek_bar_saving_info;
    @BindView(R.id.txt_unit)
    TextView txt_unit;
    
    @Inject
    SavingPresenter mSavingPresenter;

    
    private Saving mSaving;
    private String mProcess;
    private Currencies mCurrencies;
    
    @Override
    public int getLayoutId() {
        return R.layout.activity_info_saving;
    }
    
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        
    }
    
    public void getData() {
        Intent intent = getIntent();
        mSaving = (Saving) intent.getSerializableExtra("MySaving");
        mProcess = intent.getStringExtra("process");
    }
    
    public void showData() {
        txt_saving_name.setText(mSaving.getName());
        txt_money_goal.setText("+" + mSaving.getGoalMoney());
        txt_current_money.setText(mSaving.getCurrentMoney());
        double need_money = Double.parseDouble(txt_money_goal.getText().toString()) -
                            Double.parseDouble(txt_current_money.getText().toString());
        txt_need_money.setText(need_money + "");
        
        txt_date_saving.setText(DateUtil.convertTimeMillisToDate(mSaving.getDate()));
        
        txt_time_rest.setText(getString(R.string.days_left,
                  DateUtil.getDateLeft(Long.parseLong(mSaving.getDate())) + ""));
        
        //txt_name_wallet.setText("");
        
        seek_bar_saving_info.setProgress(Integer.parseInt(mProcess));
        seek_bar_saving_info.setEnabled(false);
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
        getData();
        showData();
    }
    
    @OnClick(R.id.image_view_cancel)
    public void onClickCancel(View view) {
        Intent returnIntent = new Intent();
        setResult(Activity.RESULT_CANCELED, returnIntent);
        finish();
    }
    
    @OnClick(R.id.image_view_delete)
    public void onClickDelete(View view) {
        
        final AlertDialog.Builder doneDialog = new AlertDialog.Builder(this);
        doneDialog.setMessage(getString(R.string.content_delete_saving));
        doneDialog.setNegativeButton(getString(R.string.txt_yes), new OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mSavingPresenter.deleteSaving(mSaving.getSavingid());
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
    
    @OnClick(R.id.image_view_edit)
    public void onClickEdit(View view) {
        Intent intent = new Intent(this, EditSavingActivity.class);
        intent.putExtra("saving", mSaving);
        startActivityForResult(intent, 3);
    }
    
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        
        if (requestCode == 3) {
            if (resultCode == Activity.RESULT_OK) {
                
                mSaving = (Saving) data.getSerializableExtra("saving");
                mCurrencies = (Currencies) data.getSerializableExtra("currencies");
                
                double tmp = (Double.parseDouble(mSaving.getCurrentMoney()) /
                              Double.parseDouble(mSaving.getGoalMoney())) * 100;
                int temp = (int) tmp;
                mProcess = String.valueOf(temp);
                txt_unit.setText(mCurrencies.getCurSymbol());
                
                showData();
                
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                
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
        
    }
    
    @Override
    public void onSuccessDeleteSaving() {
        Intent returnIntent = new Intent();
        returnIntent.putExtra("result", mSaving.getSavingid());
        setResult(Activity.RESULT_OK, returnIntent);
        finish();
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

    @OnClick(R.id.image_view_take_in)
    public void onClickTakeIn(View view) {
        Intent intent = new Intent(this, TransferMoneySavingActivity.class);
        intent.putExtra("saving", mSaving);
        intent.putExtra("value", "1");
        startActivityForResult(intent, 7);
    }

    @OnClick(R.id.image_view_take_out)
    public void onClickTakeOut(View view) {
        Intent intent = new Intent(this, TransferMoneySavingActivity.class);
        intent.putExtra("saving", mSaving);
        intent.putExtra("value", "2");
        startActivityForResult(intent, 8);
    }

}
