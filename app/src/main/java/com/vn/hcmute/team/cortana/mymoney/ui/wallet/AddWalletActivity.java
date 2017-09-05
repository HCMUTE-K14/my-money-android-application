package com.vn.hcmute.team.cortana.mymoney.ui.wallet;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AlertDialog.Builder;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.OnClick;
import com.vn.hcmute.team.cortana.mymoney.MyMoneyApplication;
import com.vn.hcmute.team.cortana.mymoney.R;
import com.vn.hcmute.team.cortana.mymoney.di.component.ApplicationComponent;
import com.vn.hcmute.team.cortana.mymoney.di.component.DaggerWalletComponent;
import com.vn.hcmute.team.cortana.mymoney.di.component.WalletComponent;
import com.vn.hcmute.team.cortana.mymoney.di.module.ActivityModule;
import com.vn.hcmute.team.cortana.mymoney.di.module.WalletModule;
import com.vn.hcmute.team.cortana.mymoney.model.Currencies;
import com.vn.hcmute.team.cortana.mymoney.model.Wallet;
import com.vn.hcmute.team.cortana.mymoney.ui.base.BaseActivity;
import com.vn.hcmute.team.cortana.mymoney.ui.currencies.CurrenciesActivity;
import com.vn.hcmute.team.cortana.mymoney.ui.wallet.WalletContract.View;
import com.vn.hcmute.team.cortana.mymoney.utils.Constraints.RequestCode;
import java.util.List;
import javax.inject.Inject;

/**
 * Created by infamouSs on 9/5/17.
 */

public class AddWalletActivity extends BaseActivity implements View {
    
    public static final String TAG = AddWalletActivity.class.getSimpleName();
    
    @BindView(R.id.image_view_icon)
    ImageView mImageViewIcon;
    
    @BindView(R.id.txt_name_wallet)
    EditText mEditTextNameWallet;
    
    @BindView(R.id.currency)
    RelativeLayout mLayoutCurrency;
    
    @BindView(R.id.txt_currency)
    EditText mEditTextCurrency;
    
    @BindView(R.id.txt_balance)
    EditText mEditTextBalance;
    
    @BindView(R.id.check_box_enable_notify)
    CheckBox mCheckBoxEnableNotify;
    
    @BindView(R.id.check_box_excluded_total)
    CheckBox mCheckBoxExcludedTotal;
    
    @Inject
    WalletPresenter mWalletPresenter;
    
    private String mCurrencyCode;
    private String mIconWallet;
    
    private ProgressDialog mProgressDialog;
    
    public AddWalletActivity() {
        
    }
    
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
                    Currencies currencies = data.getParcelableExtra("currency");
                    mCurrencyCode = currencies.getCurCode();
                    mEditTextCurrency.setText(currencies.getCurName());
                    break;
                default:
                    break;
            }
        }
    }
    
    @Override
    public void onBackPressed() {
        showCofirmQuitDialog();
    }
    
    @OnClick(R.id.btn_close)
    public void onClickClose() {
        showCofirmQuitDialog();
    }
    
    @OnClick(R.id.txt_done)
    public void onClickDone() {
        addWallet();
    }
    
    @OnClick(R.id.image_view_icon)
    public void onClickToChangeIconWallet() {
        Toast.makeText(this, "ICON", Toast.LENGTH_SHORT).show();
    }
    
    @OnClick({R.id.currency, R.id.txt_currency})
    public void onClickToChooseCurrency() {
        Intent intent = new Intent(this, CurrenciesActivity.class);
        startActivityForResult(intent, RequestCode.CURRENCY_REQUEST_CODE);
    }
    
    @Override
    public void initializeView() {
        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setMessage(getString(R.string.txt_creating_wallet));
    }
    
    @Override
    public void showEmpty() {
        
    }
    
    @Override
    public void showListWallet(List<Wallet> wallets) {
        
    }
    
    @Override
    public void onAddWalletSuccess(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
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
    
    private void addWallet() {
        String name = mEditTextNameWallet.getText().toString();
        String money = mEditTextBalance.getText().toString();
        
        Wallet wallet = new Wallet();
        wallet.setWalletName(name);
        wallet.setCurrencyUnit(mCurrencyCode);
        wallet.setMoney(money);
        wallet.setWalletImage(mIconWallet);
        
        mWalletPresenter.addWallet(wallet);
    }
    
    private void showCofirmQuitDialog() {
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
}
