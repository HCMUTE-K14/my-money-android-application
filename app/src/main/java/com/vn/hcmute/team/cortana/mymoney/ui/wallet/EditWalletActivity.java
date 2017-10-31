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
import com.vn.hcmute.team.cortana.mymoney.model.Icon;
import com.vn.hcmute.team.cortana.mymoney.model.Wallet;
import com.vn.hcmute.team.cortana.mymoney.ui.base.BaseActivity;
import com.vn.hcmute.team.cortana.mymoney.ui.currencies.CurrenciesActivity;
import com.vn.hcmute.team.cortana.mymoney.ui.iconshop.SelectIconActivity;
import com.vn.hcmute.team.cortana.mymoney.ui.view.CardViewActionBar;
import com.vn.hcmute.team.cortana.mymoney.ui.wallet.WalletContract.View;
import com.vn.hcmute.team.cortana.mymoney.utils.Constraints.RequestCode;
import com.vn.hcmute.team.cortana.mymoney.utils.Constraints.ResultCode;
import com.vn.hcmute.team.cortana.mymoney.utils.DrawableUtil;
import com.vn.hcmute.team.cortana.mymoney.utils.GlideImageLoader;
import com.vn.hcmute.team.cortana.mymoney.utils.NumberUtil;
import java.util.List;
import javax.inject.Inject;

/**
 * Created by infamouSs on 9/5/17.
 */

public class EditWalletActivity extends BaseActivity implements View {
    
    public static final String TAG = EditWalletActivity.class.getSimpleName();
    
    
    @Inject
    WalletPresenter mWalletPresenter;
    
    @BindView(R.id.card_view_action_bar)
    CardViewActionBar mCardViewActionBar;
    
    @BindView(R.id.parent_text_1)
    RelativeLayout mParentTextNameWallet;
    
    @BindView(R.id.parent_text_2)
    RelativeLayout mParentTextCurrencyWallet;
    
    @BindView(R.id.image_view_icon)
    ImageView mImageViewIcon;
    
    @BindView(R.id.txt_name_wallet)
    EditText mEditTextNameWallet;
    
    @BindView(R.id.txt_currency)
    EditText mEditTextCurrency;
    
    @BindView(R.id.check_box_archive)
    CheckBox mCheckBoxArchive;
    
    private Wallet mCurrentWallet;
    private Currencies mCurrentCurrency;
    private String mIconWallet;
    
    private ProgressDialog mProgressDialog;
    private String mCurrentBalance;
    
    private android.view.View.OnClickListener mOnBackClick = new android.view.View.OnClickListener() {
        @Override
        public void onClick(android.view.View v) {
            showConfirmQuitDialog();
        }
    };
    
    private android.view.View.OnClickListener mOnDoneClick = new android.view.View.OnClickListener() {
        @Override
        public void onClick(android.view.View v) {
            editWallet();
        }
    };
    
    public EditWalletActivity() {
        
    }
    
    /*-----------------*/
    /*Initialize       */
    /*-----------------*/
    @Override
    public int getLayoutId() {
        return R.layout.activity_edit_wallet;
    }
    
    @Override
    protected void initializeDagger() {
        ApplicationComponent applicationComponent = ((MyMoneyApplication) this.getApplication())
                  .getAppComponent();
        
        WalletComponent walletComponent = DaggerWalletComponent.builder()
                  .applicationComponent(applicationComponent)
                  .activityModule(new ActivityModule(this))
                  .walletModule(new WalletModule())
                  .build();
        
        walletComponent.inject(this);
        
    }
    
    
    @Override
    protected void initializePresenter() {
        this.mPresenter = mWalletPresenter;
        mWalletPresenter.setView(this);
    }
    
    @Override
    protected void initializeActionBar(android.view.View rootView) {
        
    }
    
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        mCurrentWallet = getCurrentWallet();
        
        if (mCurrentWallet == null) {
            finish();
        } else {
            initializeView();
        }
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
                case RequestCode.SELECT_ICON_REQUEST_CODE:
                    Icon icon = data.getParcelableExtra("icon");
                    
                    if (icon == null) {
                        return;
                    }
                    GlideImageLoader.load(this, DrawableUtil.getDrawable(this, icon.getImage()),
                              mImageViewIcon);
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
    public void onClickChooseIcon() {
        Toast.makeText(this, "CHOOSE ICON", Toast.LENGTH_SHORT).show();
    }
    
    @OnClick({R.id.parent_text_2, R.id.txt_currency})
    public void onClickCurrency() {
        openCurrencyActivity();
    }
    
    
    @OnClick(R.id.btn_remove_wallet)
    public void onClickRemoveWallet() {
        removeWallet();
    }
    
    /*-----------------*/
    /*TaskView         */
    /*-----------------*/
    @Override
    public void initializeView() {
        mCardViewActionBar.setOnClickBack(mOnBackClick);
        mCardViewActionBar.setOnClickAction(mOnDoneClick);
        
        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setMessage(getString(R.string.txt_updating_wallet));
        
        mCurrentCurrency = mCurrentWallet.getCurrencyUnit();
        mIconWallet = mCurrentWallet.getWalletImage();
        
        mImageViewIcon.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(android.view.View v) {
                openSelectIconActivity();
            }
        });
        
        GlideImageLoader.load(this, DrawableUtil.getDrawable(this, mCurrentWallet.getWalletImage()),
                  mImageViewIcon);
        
        mEditTextNameWallet.setText(mCurrentWallet.getWalletName());
        mCheckBoxArchive.setChecked(mCurrentWallet.isArchive());
        
        mEditTextCurrency.setText(mCurrentCurrency.getCurName());
    }
    
    private void openSelectIconActivity() {
        Intent intent = new Intent(this, SelectIconActivity.class);
        startActivityForResult(intent, RequestCode.SELECT_ICON_REQUEST_CODE);
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
    
    @Override
    public void onUpdateWalletSuccess(String message, int position, Wallet wallet) {
        Toast.makeText(this, R.string.txt_successful, Toast.LENGTH_SHORT).show();
        finishTask(wallet, ResultCode.EDIT_WALLET_RESULT_CODE);
    }
    
    
    @Override
    public void onRemoveWalletSuccess(String message, int position, Wallet wallet) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        finishTask(wallet, ResultCode.REMOVE_WALLET_RESULT_CODE);
        
    }
    
    
    @Override
    public void onMoveMoneySuccess(String message) {
        
    }
    
    @Override
    public void showEmpty() {
        
    }
    
    @Override
    public void showListWallet(List<Wallet> wallets) {
        
    }
    
    @Override
    public void onAddWalletSuccess(String message, Wallet wallet) {
        
    }
    
    
    /*-----------------*/
    /*Helper Method    */
    /*-----------------*/
    private Wallet getCurrentWallet() {
        Intent intent = getIntent();
        
        return intent.getParcelableExtra("wallet");
    }
    
    private void openCurrencyActivity() {
        Intent intent = new Intent(this, CurrenciesActivity.class);
        startActivityForResult(intent, RequestCode.CURRENCY_REQUEST_CODE);
    }
    
    private void editWallet() {
        String name = mEditTextNameWallet.getText().toString();
        boolean isArchive = mCheckBoxArchive.isChecked();
        
        mCurrentWallet.setWalletName(name);
        
        mCurrentWallet.setWalletImage(mIconWallet);
        mCurrentWallet.setArchive(isArchive);
        double money = 0;
        try {
            money = exchangeMoney();
        } catch (Exception e) {
        }
        mCurrentWallet.setMoney(String.valueOf(money));
        mCurrentWallet.setCurrencyUnit(mCurrentCurrency);
        
        mWalletPresenter.updateWallet(0, mCurrentWallet);
    }
    
    private void finishTask(Wallet wallet, int resultCode) {
        Intent intent = new Intent();
        intent.putExtra("wallet", wallet);
        
        setResult(resultCode, intent);
        finish();
    }
    
    private double exchangeMoney() {
        String from = mCurrentWallet.getCurrencyUnit().getCurCode();
        String to = mCurrentCurrency.getCurCode();
        String amount = mCurrentWallet.getMoney();
        
        return NumberUtil.exchangeMoney(this, amount, from, to);
    }
    
    
    private void removeWallet() {
        AlertDialog.Builder alert = new Builder(this);
        alert.setTitle(getString(R.string.txt_wait_a_second));
        
        alert.setMessage(getString(R.string.message_waring_remove_wallet));
        alert.setNegativeButton(getString(R.string.txt_yes), new OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mWalletPresenter.removeWallet(0, mCurrentWallet);
            }
        });
        
        alert.setPositiveButton(getString(R.string.txt_no), new OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                dialog.dismiss();
            }
        });
        
        alert.create().show();
    }
    
    private void showConfirmQuitDialog() {
        AlertDialog.Builder alert = new Builder(this);
        alert.setMessage(getString(R.string.message_warning_finish_edit_wallet));
        alert.setNegativeButton(getString(R.string.txt_yes), new OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                editWallet();
            }
        });
        alert.setPositiveButton(getString(R.string.txt_no), new OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                dialog.dismiss();
            }
        });
        
        alert.show();
    }
}
