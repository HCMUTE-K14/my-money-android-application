package com.vn.hcmute.team.cortana.mymoney.ui.saving;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
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
import com.vn.hcmute.team.cortana.mymoney.model.Category;
import com.vn.hcmute.team.cortana.mymoney.model.Saving;
import com.vn.hcmute.team.cortana.mymoney.model.Transaction;
import com.vn.hcmute.team.cortana.mymoney.model.Wallet;
import com.vn.hcmute.team.cortana.mymoney.ui.base.BaseActivity;
import com.vn.hcmute.team.cortana.mymoney.ui.base.listener.BaseCallBack;
import com.vn.hcmute.team.cortana.mymoney.ui.tools.calculator.CalculatorActivity;
import com.vn.hcmute.team.cortana.mymoney.ui.wallet.MyWalletActivity;
import com.vn.hcmute.team.cortana.mymoney.usecase.base.Action;
import com.vn.hcmute.team.cortana.mymoney.usecase.base.TypeRepository;
import com.vn.hcmute.team.cortana.mymoney.usecase.remote.TransactionUseCase;
import com.vn.hcmute.team.cortana.mymoney.usecase.remote.TransactionUseCase.TransactionRequest;
import com.vn.hcmute.team.cortana.mymoney.utils.NumberUtil;
import java.util.List;
import javax.inject.Inject;

/**
 * Created by kunsubin on 9/8/2017.
 */

public class TransferMoneySavingActivity extends BaseActivity implements SavingContract.View {
    
    @BindView(R.id.txt_name_saving)
    TextView txt_name_saving;
    @BindView(R.id.txt_remainin)
    TextView txt_remainin;
    @BindView(R.id.edit_describe)
    EditText edit_describe;
    @BindView(R.id.txt_money)
    TextView txt_money;
    @BindView(R.id.txt_wallet_name)
    TextView txt_wallet_name;
    @BindView(R.id.linear_wallet)
    LinearLayout linear_wallet;
    
    
    @Inject
    SavingPresenter mSavingPresenter;
    @Inject
    PreferencesHelper mPreferencesHelper;
    @Inject
    TransactionUseCase mTransactionUseCase;
    ProgressDialog mProgressDialog;
    private Wallet mWallet;
    private String value = "-1";
    private Saving mSaving;
    private Wallet mWalletTemp;
    private Transaction mTransaction;
    private String mGoalMoney = "0";
    
    @Override
    public int getLayoutId() {
        return R.layout.activity_transfer_saving;
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
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getData();
        showData();
        mTransaction = new Transaction();
        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setCanceledOnTouchOutside(false);
        mProgressDialog.setMessage(this.getString(R.string.txt_progress));
    }
    
    @Override
    protected void onDestroy() {
        mSavingPresenter.unSubscribe();
        super.onDestroy();
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
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        
        if (requestCode == 9) {
            if (resultCode == Activity.RESULT_OK) {
                
                mGoalMoney = data.getStringExtra("result");
                String goalMoneyShow = data.getStringExtra("result_view");
                
                txt_money.setText(goalMoneyShow);
                
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                
            }
        }
        if (requestCode == 10) {
            if (resultCode == Activity.RESULT_OK) {
                mWallet = data.getParcelableExtra("wallet");
                txt_wallet_name.setText(mWallet.getWalletName());
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
        
    }
    
    @Override
    public void onSuccessUpdateSaving() {
        
    }
    
    @Override
    public void onSuccessTakeIn() {
        //add transaction
        setTransaction("expense");
        TransactionRequest transactionRequest = new TransactionRequest(
                  Action.ACTION_ADD_TRANSACTION,
                  new BaseCallBack<Object>() {
                      
                      @Override
                      public void onSuccess(Object value) {
                          finishTransaction();
                      }
                      
                      @Override
                      public void onFailure(Throwable throwable) {
                          alertDiaglog(throwable.getMessage());
                      }
                      
                      @Override
                      public void onLoading() {
                          
                      }
                  }, mTransaction, null, TypeRepository.LOCAL);
        mTransactionUseCase.subscribe(transactionRequest);
    }
    
    @Override
    public void onSuccessTakeOut() {
        setTransaction("income");
        TransactionRequest transactionRequest = new TransactionRequest(
                  Action.ACTION_ADD_TRANSACTION,
                  new BaseCallBack<Object>() {
                      
                      @Override
                      public void onSuccess(Object value) {
                          finishTransaction();
                      }
                      
                      @Override
                      public void onFailure(Throwable throwable) {
                          alertDiaglog(throwable.getMessage());
                      }
                      
                      @Override
                      public void onLoading() {
                          
                      }
                  }, mTransaction, null, TypeRepository.LOCAL);
        mTransactionUseCase.subscribe(transactionRequest);
    }
    
    @Override
    public void showError(String message) {
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
    
    /*Area OnClick*/
    @OnClick(R.id.linear_money)
    public void onClickLinearMoney(View view) {
        Intent intent = new Intent(this, CalculatorActivity.class);
        intent.putExtra("goal_money", mGoalMoney);
        if (value.equals("1")) {
            intent.putExtra("currencies", mWallet.getCurrencyUnit());
        } else {
            intent.putExtra("currencies", mSaving.getCurrencies());
        }
        startActivityForResult(intent, 9);
    }
    
    @OnClick(R.id.back_button_saving)
    public void onClickBack(View view) {
        finish();
    }
    
    @OnClick(R.id.linear_wallet)
    public void onClickSelectWallet(View view) {
        if (mSaving.getIdWallet().equals("")) {
            Intent intent = new Intent(this, MyWalletActivity.class);
            startActivityForResult(intent, 10);
        }
    }
    
    @OnClick(R.id.txt_save_transaction)
    public void onClickSave(View view) {
        if (mGoalMoney.equals("0")) {
            alertDiaglog(getString(R.string.select_money));
            return;
        }
        if (value.equals("1")) {
            if (checkTakeIn()) {
                String money = mGoalMoney;
                double moneyWallet;
                double moneySaving;
                double exchangeMoney = NumberUtil
                          .exchangeMoney(this, money, mWallet.getCurrencyUnit().getCurCode(),
                                    mSaving.getCurrencies().getCurCode());
                if ((Double.parseDouble(mSaving.getCurrentMoney()) + exchangeMoney) >
                    Double.parseDouble(mSaving.getGoalMoney())) {
                    double denta = Double.parseDouble(mSaving.getCurrentMoney()) + exchangeMoney -
                                   Double.parseDouble(mSaving.getGoalMoney());
                    
                    moneyWallet =
                              Double.parseDouble(mWallet.getMoney()) - Double.parseDouble(money) +
                              NumberUtil.exchangeMoney(this, String.valueOf(denta),
                                        mSaving.getCurrencies().getCurCode(),
                                        mWallet.getCurrencyUnit().getCurCode());
                    
                    moneySaving = Double.parseDouble(mSaving.getGoalMoney());
                    
                    if (!mSaving.equals("")) {
                        mWallet.setMoney(String.valueOf(moneyWallet));
                    }
                    
                    mSaving.setCurrentMoney(String.valueOf(moneySaving));
                    
                    mSavingPresenter.takeIn(mWallet.getWalletid(), mSaving.getSavingid(),
                              String.valueOf(moneyWallet), String.valueOf(moneySaving));
                } else {
                    moneyWallet =
                              Double.parseDouble(mWallet.getMoney()) - Double.parseDouble(money);
                    moneySaving = Double.parseDouble(mSaving.getCurrentMoney()) + exchangeMoney;
                    
                    //mMoneyAddSaving=exchangeMoney;
                    if (!mSaving.equals("")) {
                        mWallet.setMoney(String.valueOf(moneyWallet));
                    }
                    
                    mSaving.setCurrentMoney(String.valueOf(moneySaving));
                    
                    mSavingPresenter.takeIn(mWallet.getWalletid(), mSaving.getSavingid(),
                              String.valueOf(moneyWallet), String.valueOf(moneySaving));
                }
            }
            return;
        }
        if (value.equals("2")) {
            if (checkTakeOut()) {
                String money = mGoalMoney;
                double moneyWallet;
                double moneySaving;
                double exchangeMoney = NumberUtil
                          .exchangeMoney(this, money, mSaving.getCurrencies().getCurCode(),
                                    mWallet.getCurrencyUnit().getCurCode());
                moneyWallet = Double.parseDouble(mWallet.getMoney()) + exchangeMoney;
                moneySaving =
                          Double.parseDouble(mSaving.getCurrentMoney()) - Double.parseDouble(money);
                if (mSaving.getIdWallet().equals("")) {
                    mWallet.setMoney(String.valueOf(moneyWallet));
                }
                mSaving.setCurrentMoney(String.valueOf(moneySaving));
                
                //take out
                mSavingPresenter.takeOut(mWallet.getWalletid(), mSaving.getSavingid(),
                          String.valueOf(moneyWallet), String.valueOf(moneySaving));
            }
            return;
        }
        
    }
    
    /*Area Function*/
    public void getData() {
        Intent intent = getIntent();
        value = intent.getStringExtra("value");
        mSaving = intent.getParcelableExtra("saving");
        mWalletTemp = intent.getParcelableExtra("wallet");
    }
    
    public void showData() {
        txt_name_saving.setText(mSaving.getName());
        double remainin = Double.parseDouble(mSaving.getGoalMoney()) -
                          Double.parseDouble(mSaving.getCurrentMoney());
        txt_remainin.setText("+" + NumberUtil.formatAmount(String.valueOf(remainin),
                  mSaving.getCurrencies().getCurSymbol()));
        txt_money.setText(
                  NumberUtil.formatAmount(mGoalMoney, mSaving.getCurrencies().getCurSymbol()));
        if (value.equals("1")) {
            edit_describe.setText(getString(R.string.deposit));
        } else {
            edit_describe.setText(getString(R.string.withdraw));
        }
        //wallet
        if (mSaving.getIdWallet().equals("")) {
            mWallet = mPreferencesHelper.getCurrentWallet();
            if (mWallet != null) {
                txt_wallet_name.setText(mWallet.getWalletName());
            }
            linear_wallet.setEnabled(true);
        } else {
            mWallet = mWalletTemp;
            txt_wallet_name.setText(mWallet.getWalletName());
            mWallet.setWalletid(mWallet.getWalletid());
            linear_wallet.setEnabled(false);
        }
        
        
    }
    
    public boolean checkTakeIn() {
        double money = Double.parseDouble(mGoalMoney);
        if (money > Double.parseDouble(mWallet.getMoney())) {
            alertDiaglog(getString(R.string.txt_over_money_wallet));
            return false;
        }
        if (Double.parseDouble(mWallet.getMoney()) <= 0) {
            alertDiaglog(getString(R.string.txt_not_enough));
            return false;
        }
        return true;
    }
    
    public boolean checkTakeOut() {
        double money = Double.parseDouble(mGoalMoney);
        if (money > Double.parseDouble(mSaving.getCurrentMoney())) {
            alertDiaglog(getString(R.string.txt_over_money_saving));
            return false;
        }
        return true;
    }
    
    public void alertDiaglog(String message) {
        AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
        builder1.setMessage(message);
        builder1.setCancelable(true);
        builder1.setPositiveButton(
                  getString(R.string.txt_yes),
                  new DialogInterface.OnClickListener() {
                      public void onClick(DialogInterface dialog, int id) {
                          dialog.cancel();
                      }
                  });
        
        AlertDialog alert11 = builder1.create();
        alert11.show();
    }
    
    public void setTransaction(String type) {
        //mTransaction.setTrans_id(SecurityUtil.getRandomUUID());
        mTransaction.setAmount(mGoalMoney);
        mTransaction.setWallet(mWallet);
        mTransaction.setSaving(mSaving);
        mTransaction.setNote(edit_describe.getText().toString());
        mTransaction.setType(type);
        // mTransaction.setUser_id(mSaving.getUserid());
        mTransaction.setDate_created(String.valueOf(System.currentTimeMillis()));
        mTransaction.setCategory(createCategoty(type));
    }
    
    public Category createCategoty(String type) {
        Category category = new Category();
        category.setName("Other");
        category.setIcon("ic_category_other_expense");
        category.setType(type);
        category.setTransType(type);
        category.setSubcategories(null);
        return category;
    }
    
    public void finishTransaction() {
        Intent returnIntent = new Intent();
        returnIntent.putExtra("saving", mSaving);
        if (!mSaving.equals("")) {
            returnIntent.putExtra("wallet", mWallet);
        }
        setResult(Activity.RESULT_OK, returnIntent);
        finish();
    }
}
