package com.vn.hcmute.team.cortana.mymoney.ui.wallet;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AlertDialog.Builder;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.OnClick;
import com.vn.hcmute.team.cortana.mymoney.MyMoneyApplication;
import com.vn.hcmute.team.cortana.mymoney.R;
import com.vn.hcmute.team.cortana.mymoney.di.component.ApplicationComponent;
import com.vn.hcmute.team.cortana.mymoney.di.component.DaggerWalletComponent;
import com.vn.hcmute.team.cortana.mymoney.di.component.WalletComponent;
import com.vn.hcmute.team.cortana.mymoney.di.module.ActivityModule;
import com.vn.hcmute.team.cortana.mymoney.di.module.GlideApp;
import com.vn.hcmute.team.cortana.mymoney.di.module.WalletModule;
import com.vn.hcmute.team.cortana.mymoney.model.Currencies;
import com.vn.hcmute.team.cortana.mymoney.model.Icon;
import com.vn.hcmute.team.cortana.mymoney.model.Wallet;
import com.vn.hcmute.team.cortana.mymoney.ui.base.BaseActivity;
import com.vn.hcmute.team.cortana.mymoney.ui.currencies.CurrenciesActivity;
import com.vn.hcmute.team.cortana.mymoney.ui.iconshop.SelectIconActivity;
import com.vn.hcmute.team.cortana.mymoney.ui.tools.calculator.CalculatorActivity;
import com.vn.hcmute.team.cortana.mymoney.ui.view.CardViewActionBar;
import com.vn.hcmute.team.cortana.mymoney.ui.wallet.WalletContract.View;
import com.vn.hcmute.team.cortana.mymoney.utils.Constraints.RequestCode;
import com.vn.hcmute.team.cortana.mymoney.utils.Constraints.ResultCode;
import com.vn.hcmute.team.cortana.mymoney.utils.DrawableUtil;
import com.vn.hcmute.team.cortana.mymoney.utils.NumberUtil;
import java.util.List;
import javax.inject.Inject;

/**
 * Created by infamouSs on 9/5/17.
 */

public class AddWalletActivity extends BaseActivity implements View {
    
    public static final String TAG = AddWalletActivity.class.getSimpleName();
    
    @BindView(R.id.card_view_action_bar)
    CardViewActionBar mCardViewActionBar;
    
    @BindView(R.id.image_view_icon)
    ImageView mImageViewIcon;
    
    @BindView(R.id.txt_name_wallet)
    EditText mEditTextNameWallet;
    
    @BindView(R.id.txt_currency)
    EditText mEditTextCurrency;
    
    @BindView(R.id.txt_balance)
    EditText mEditTextBalance;
    
    @Inject
    WalletPresenter mWalletPresenter;
    
    private Currencies mCurrentCurrency;
    private String mIconWallet;
    private String mCurrentBalance;
    
    private ProgressDialog mProgressDialog;
    
    private android.view.View.OnClickListener mOnBackClick = new android.view.View.OnClickListener() {
        @Override
        public void onClick(android.view.View v) {
            showConfirmQuitDialog();
        }
    };
    
    private android.view.View.OnClickListener mOnDoneClick = new android.view.View.OnClickListener() {
        @Override
        public void onClick(android.view.View v) {
            addWallet();
        }
    };
    
    public AddWalletActivity() {
        
    }
    
    /*-----------------*/
    /*Initialize       */
    /*-----------------*/
    @Override
    public int getLayoutId() {
        return R.layout.activity_add_wallet;
    }
    
    @Override
    protected void initializeDagger() {
        ApplicationComponent applicationComponent = ((MyMoneyApplication) this.getApplication())
                  .getAppComponent();
        
        WalletComponent walletComponent = DaggerWalletComponent.builder()
                  .applicationComponent(applicationComponent)
                  .walletModule(new WalletModule())
                  .activityModule(new ActivityModule(this))
                  .build();
        
        walletComponent.inject(this);
    }
    
    @Override
    protected void initializePresenter() {
        this.mPresenter = mWalletPresenter;
        this.mWalletPresenter.setView(this);
    }
    
    @Override
    protected void initializeActionBar(android.view.View rootView) {
        
    }
    
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initializeView();
    }
    
    @Override
    protected void onDestroy() {
        super.onDestroy();
        mWalletPresenter.unSubscribe();
    }
    
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && data != null) {
            switch (requestCode) {
                case RequestCode.CURRENCY_REQUEST_CODE:
                    mCurrentCurrency = data.getParcelableExtra("currency");
                    if (mCurrentCurrency != null) {
                        mEditTextCurrency.setText(mCurrentCurrency.getCurName());
                    }
                    break;
                case RequestCode.CALCULATOR_REQUEST_CODE:
                    mCurrentBalance = data.getStringExtra("result");
                    double amount = Double.parseDouble(mCurrentBalance);
                    String result = NumberUtil.format(amount, "#,###.##");
                    mEditTextBalance.setText(result);
                    break;
                case RequestCode.SELECT_ICON_REQUEST_CODE:
                    Icon icon = data.getParcelableExtra("icon");
                    if (icon == null) {
                        return;
                    }
                    GlideApp.with(this)
                              .load(DrawableUtil.getDrawable(this, icon.getImage()))
                              .placeholder(R.drawable.folder_placeholder)
                              .error(R.drawable.folder_placeholder)
                              .into(mImageViewIcon);
                    mIconWallet = icon.getImage();
                default:
                    break;
            }
        }
    }
    
    @Override
    public void onBackPressed() {
        showConfirmQuitDialog();
    }
    
    /*-----------------*/
    /*OnClick          */
    /*-----------------*/
    
    @OnClick(R.id.image_view_icon)
    public void onClickToChangeIconWallet() {
        Toast.makeText(this, "ICON", Toast.LENGTH_SHORT).show();
    }
    
    @OnClick({R.id.parent_text_2, R.id.txt_currency})
    public void onClickToChooseCurrency() {
        openCurrencyActivity();
    }
    
    @OnClick(R.id.txt_balance)
    public void onClickBalance() {
        if (mCurrentCurrency == null) {
            Toast.makeText(this, R.string.message_validate_currency_wallet, Toast.LENGTH_SHORT)
                      .show();
            return;
        }
        
        openCalculator();
    }
    
    /*-----------------*/
    /*TaskView         */
    /*-----------------*/
    @Override
    public void initializeView() {
        mCardViewActionBar.setOnClickBack(mOnBackClick);
        mCardViewActionBar.setOnClickAction(mOnDoneClick);
        
        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setMessage(getString(R.string.txt_creating_wallet));
        mImageViewIcon.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(android.view.View v) {
                openSelectIconActivity();
            }
        });
    }
    
    
    @Override
    public void showEmpty() {
        
    }
    
    @Override
    public void showListWallet(List<Wallet> wallets) {
        
    }
    
    
    @Override
    public void onAddWalletSuccess(String message, Wallet wallet) {
        finishAddWallet(wallet);
    }
    
    @Override
    public void onUpdateWalletSuccess(String message, int position, Wallet wallet) {
        
    }
    
    @Override
    public void onRemoveWalletSuccess(String message, int position, Wallet wallet) {
        
    }
    
    @Override
    public void onMoveMoneySuccess(String message) {
        
    }
    
    @Override
    public void onFailure(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
    
    @Override
    public void loading(boolean isLoading) {
        if (isLoading) {
            mProgressDialog.show();
            return;
        }
        mProgressDialog.dismiss();
    }
    
    /*-----------------*/
    /*Helper Method    */
    /*-----------------*/
    private void addWallet() {
        String name = mEditTextNameWallet.getText().toString();
        String money = mEditTextBalance.getText().toString();
        
        Wallet wallet = new Wallet();
        wallet.setWalletName(name);
        wallet.setCurrencyUnit(mCurrentCurrency);
        wallet.setMoney(money);
        wallet.setWalletImage(mIconWallet);
        
        mWalletPresenter.addWallet(wallet);
    }
    
    private void showConfirmQuitDialog() {
        AlertDialog.Builder confirmDialog = new Builder(this);
        
        confirmDialog.setMessage("Do you want to quit ?");
        confirmDialog.setNegativeButton(getString(R.string.txt_yes), new OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        
        confirmDialog.setPositiveButton(getString(R.string.txt_no), new OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                dialog.dismiss();
            }
        });
        confirmDialog.create().show();
    }
    
    private void finishAddWallet(Wallet wallet) {
        Intent intent = new Intent();
        intent.putExtra("wallet", wallet);
        setResult(ResultCode.ADD_WALLET_RESULT_CODE, intent);
        finish();
    }
    
    private void openCurrencyActivity() {
        Intent intent = new Intent(this, CurrenciesActivity.class);
        startActivityForResult(intent, RequestCode.CURRENCY_REQUEST_CODE);
    }
    
    private void openCalculator() {
        Intent intent = new Intent(this, CalculatorActivity.class);
        intent.putExtra("value", mCurrentBalance);
        startActivityForResult(intent, RequestCode.CALCULATOR_REQUEST_CODE);
    }
    
    private void openSelectIconActivity() {
        Intent intent = new Intent(this, SelectIconActivity.class);
        startActivityForResult(intent, RequestCode.SELECT_ICON_REQUEST_CODE);
    }
}
