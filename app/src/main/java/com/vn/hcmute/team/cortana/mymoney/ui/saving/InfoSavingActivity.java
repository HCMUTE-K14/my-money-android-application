package com.vn.hcmute.team.cortana.mymoney.ui.saving;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.ImageView;
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
import com.vn.hcmute.team.cortana.mymoney.model.Saving;
import com.vn.hcmute.team.cortana.mymoney.model.Wallet;
import com.vn.hcmute.team.cortana.mymoney.ui.base.BaseActivity;
import com.vn.hcmute.team.cortana.mymoney.ui.base.listener.BaseCallBack;
import com.vn.hcmute.team.cortana.mymoney.usecase.base.Action;
import com.vn.hcmute.team.cortana.mymoney.usecase.remote.WalletUseCase;
import com.vn.hcmute.team.cortana.mymoney.usecase.remote.WalletUseCase.WalletRequest;
import com.vn.hcmute.team.cortana.mymoney.utils.DateUtil;
import com.vn.hcmute.team.cortana.mymoney.utils.DrawableUtil;
import com.vn.hcmute.team.cortana.mymoney.utils.GlideImageLoader;
import com.vn.hcmute.team.cortana.mymoney.utils.TextUtil;
import com.vn.hcmute.team.cortana.mymoney.utils.logger.MyLogger;
import java.util.ArrayList;
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
    @BindView(R.id.image_icon_saving)
    ImageView image_icon_saving;
    @Inject
    SavingPresenter mSavingPresenter;
    @Inject
    WalletUseCase mWalletUseCase;
    private Saving mSaving;
    private String mProcess;
    private List<Wallet> mWalletList;
    private Wallet mWallet;
    
    @Override
    public int getLayoutId() {
        return R.layout.activity_info_saving;
    }
    
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        
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
        getWallet();
        showData();
    }
    
    @Override
    public void onBackPressed() {
        onClose();
        super.onBackPressed();
    }
    
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        
        if (requestCode == 3) {
            if (resultCode == Activity.RESULT_OK) {
                mSaving = (Saving) data.getParcelableExtra("saving");
                double tmp = (Double.parseDouble(mSaving.getCurrentMoney()) /
                              Double.parseDouble(mSaving.getGoalMoney())) * 100;
                int temp = (int) tmp;
                mProcess = String.valueOf(temp);
                txt_unit.setText(mSaving.getCurrencies().getCurSymbol());
                txt_name_wallet.setText(data.getStringExtra("name_wallet"));
                
                showData();
                
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                
            }
        }
        if (requestCode == 7) {
            if (resultCode == Activity.RESULT_OK) {
                doTransferMoneySaving(data);
            }
        }
        if (requestCode == 8) {
            if (resultCode == Activity.RESULT_OK) {
                doTransferMoneySaving(data);
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
    
    /*Area OnClick*/
    @OnClick(R.id.image_view_take_in)
    public void onClickTakeIn(View view) {
        Intent intent = new Intent(this, TransferMoneySavingActivity.class);
        intent.putExtra("saving", mSaving);
        intent.putExtra("wallet", mWallet);
        intent.putExtra("wallet_name", txt_name_wallet.getText().toString().trim());
        intent.putExtra("value", "1");
        startActivityForResult(intent, 7);
    }
    
    @OnClick(R.id.image_view_take_out)
    public void onClickTakeOut(View view) {
        Intent intent = new Intent(this, TransferMoneySavingActivity.class);
        intent.putExtra("saving", mSaving);
        intent.putExtra("wallet", mWallet);
        intent.putExtra("wallet_name", txt_name_wallet.getText().toString().trim());
        intent.putExtra("value", "2");
        startActivityForResult(intent, 8);
    }
    
    @OnClick(R.id.image_view_cancel)
    public void onClickCancel(View view) {
        onClose();
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
        intent.putExtra("wallet_name", txt_name_wallet.getText().toString().trim());
        startActivityForResult(intent, 3);
    }
    
    @OnClick(R.id.btn_list_transaction)
    public void onClickListTransaction(View view) {
        Intent intent = new Intent(this, TransactionSavingActivity.class);
        intent.putExtra("saving", mSaving);
        startActivity(intent);
    }
    
    /*Area Function*/
    private void onClose() {
        Intent returnIntent = new Intent();
        setResult(Activity.RESULT_CANCELED, returnIntent);
        finish();
    }
    
    public void doTransferMoneySaving(Intent data) {
        if (!mSaving.equals("")) {
            mWallet = data.getParcelableExtra("wallet");
        }
        mSaving = data.getParcelableExtra("saving");
        double tmp = (Double.parseDouble(mSaving.getCurrentMoney()) /
                      Double.parseDouble(mSaving.getGoalMoney())) * 100;
        int temp = (int) tmp;
        mProcess = String.valueOf(temp);
        showData();
    }
    
    public void getData() {
        Intent intent = getIntent();
        mSaving = (Saving) intent.getParcelableExtra("MySaving");
        mProcess = intent.getStringExtra("process");
    }
    
    public void showData() {
        txt_saving_name.setText(mSaving.getName());
        txt_money_goal.setText("+" + mSaving.getGoalMoney());
        txt_current_money.setText(mSaving.getCurrentMoney());
        double need_money = Double.parseDouble(txt_money_goal.getText().toString()) -
                            Double.parseDouble(txt_current_money.getText().toString());
        
        txt_need_money.setText(TextUtil.doubleToString(need_money));
        
        txt_date_saving.setText(DateUtil.convertTimeMillisToDate(mSaving.getDate()));
        
        txt_time_rest.setText(getString(R.string.days_left,
                  DateUtil.getDateLeft(Long.parseLong(mSaving.getDate())) + ""));
        
        //txt_name_wallet.setText("");
        if (mSaving.getIdWallet().equals("")) {
            txt_name_wallet.setText(getString(R.string.all_wallet));
        }
        
        txt_unit.setText(mSaving.getCurrencies().getCurSymbol());
        
        seek_bar_saving_info.setProgress(Integer.parseInt(mProcess));
        seek_bar_saving_info.setEnabled(false);
        
        GlideImageLoader
                  .load(this, DrawableUtil.getDrawable(this, mSaving.getIcon()), image_icon_saving);
    }
    
    public Wallet getNameWallet(List<Wallet> wallets) {
        Wallet walletResult = null;
        
        for (Wallet wallet : wallets) {
            if (wallet.getWalletid().equals(mSaving.getIdWallet())) {
                walletResult = wallet;
                break;
            }
        }
        return walletResult;
    }
    
    public void getWallet() {
        mWalletList = new ArrayList<>();
        WalletRequest savingRequest = new WalletRequest(Action.ACTION_GET_WALLET,
                  new BaseCallBack<Object>() {
                      @Override
                      public void onSuccess(Object value) {
                          List<Wallet> wallets = (List<Wallet>) value;
                          mWalletList.addAll(wallets);
                          Wallet tmp = getNameWallet(wallets);
                          if (tmp == null) {
                              txt_name_wallet.setText(getString(R.string.all_wallet));
                          } else {
                              txt_name_wallet.setText(tmp.getWalletName());
                              mWallet = tmp;
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
}
