package com.vn.hcmute.team.cortana.mymoney.ui.wallet;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;
import butterknife.BindView;
import com.vn.hcmute.team.cortana.mymoney.MyMoneyApplication;
import com.vn.hcmute.team.cortana.mymoney.R;
import com.vn.hcmute.team.cortana.mymoney.data.cache.PreferencesHelper;
import com.vn.hcmute.team.cortana.mymoney.di.component.ApplicationComponent;
import com.vn.hcmute.team.cortana.mymoney.di.component.DaggerWalletComponent;
import com.vn.hcmute.team.cortana.mymoney.di.component.WalletComponent;
import com.vn.hcmute.team.cortana.mymoney.di.module.ActivityModule;
import com.vn.hcmute.team.cortana.mymoney.di.module.WalletModule;
import com.vn.hcmute.team.cortana.mymoney.model.Wallet;
import com.vn.hcmute.team.cortana.mymoney.ui.base.BaseActivity;
import com.vn.hcmute.team.cortana.mymoney.ui.view.CardViewActionBar;
import com.vn.hcmute.team.cortana.mymoney.ui.view.selectwallet.SelectWalletListener;
import com.vn.hcmute.team.cortana.mymoney.ui.view.selectwallet.SelectWalletView;
import com.vn.hcmute.team.cortana.mymoney.utils.Constraints.RequestCode;
import com.vn.hcmute.team.cortana.mymoney.utils.Constraints.ResultCode;
import java.util.List;
import javax.inject.Inject;

/**
 * Created by infamouSs on 8/30/17.
 */

public class SelectWalletActivity extends BaseActivity implements
                                                       WalletContract.View {
    
    public static final String TAG = SelectWalletActivity.class.getSimpleName();
    
    @BindView(R.id.select_wallet)
    SelectWalletView mSelectWalletView;
    
    @BindView(R.id.card_view_action_bar)
    CardViewActionBar mCardViewActionBar;
    
    @Inject
    WalletPresenter mWalletPresenter;
    
    @Inject
    PreferencesHelper mPreferenceHelper;
    
    private SelectWalletListener mSelectWalletListener = new SelectWalletListener() {
        @Override
        public void onClickAddWallet() {
            openAddWalletActivity();
        }
        
        @Override
        public void onClickMyWallet() {
            openActivityMyWallet();
        }
        
        @Override
        public void onCLickWallet(Wallet wallet) {
            mPreferenceHelper.putCurrentWallet(wallet);
            finish();
        }
        
        @Override
        public void onEditWallet(int position, Wallet wallet) {
            openEditWalletActivity(wallet);
        }
        
        @Override
        public void onRemoveWallet(int position, Wallet wallet) {
            removeWallet(position, wallet);
        }
        
        @Override
        public void onArchiveWallet(int position, Wallet wallet) {
            boolean isArchive = wallet.isArchive();
            wallet.setArchive(!isArchive);
            
            mWalletPresenter.updateWallet(position, wallet);
        }
        
        @Override
        public void onTransferMoneyWallet(int position, Wallet wallet) {
            
        }
    };
    
    
    /*-----------------*/
    /*Initialize       */
    /*-----------------*/
    @Override
    public int getLayoutId() {
        return R.layout.activity_select_wallet;
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
        mPresenter = mWalletPresenter;
        mWalletPresenter.setView(this);
    }
    
    @Override
    protected void initializeActionBar(View rootView) {
    }
    
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initializeView();
        
        getWalletData();
    }
    
    @Override
    protected void onDestroy() {
        super.onDestroy();
        mWalletPresenter.unSubscribe();
    }
    
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data != null) {
            Wallet wallet = data.getParcelableExtra("wallet");
            if (wallet == null) {
                return;
            }
            switch (requestCode) {
                case RequestCode.ADD_WALLET_REQUEST_CODE:
                    mSelectWalletView.addWallet(wallet);
                    break;
                case RequestCode.EDIT_WALLET_REQUEST_CODE:
                    if (resultCode == ResultCode.EDIT_WALLET_RESULT_CODE) {
                        mSelectWalletView.updateWallet(wallet);
                    } else if (resultCode == ResultCode.REMOVE_WALLET_RESULT_CODE) {
                        mSelectWalletView.removeWallet(wallet);
                    }
                    break;
                default:
                    break;
                
            }
        }
    }
    
    /*-----------------*/
    /*Task View       */
    /*-----------------*/
    @Override
    public void initializeView() {
        mCardViewActionBar.setOnClickBack(new OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mSelectWalletView.setSelectWalletListener(mSelectWalletListener);
    }
    
    @Override
    public void showEmpty() {
        mSelectWalletView.setEmptyAdapter(getString(R.string.txt_no_data));
    }
    
    @Override
    public void showListWallet(List<Wallet> wallets) {
        mSelectWalletView.setData(wallets);
    }
    
    @Override
    public void onUpdateWalletSuccess(String message, int position, Wallet wallet) {
        mSelectWalletView.updateArchiveWallet(position, wallet);
    }
    
    @Override
    public void onRemoveWalletSuccess(String message, int position, Wallet wallet) {
        mSelectWalletView.removeWallet(wallet);
    }
    
    @Override
    public void onFailure(String message) {
        mSelectWalletView.setEmptyAdapter(message);
    }
    
    @Override
    public void loading(boolean isLoading) {
        mSelectWalletView.loading(isLoading);
    }
    
    @Override
    public void onMoveMoneySuccess(String message) {
        
    }
    
    @Override
    public void onAddWalletSuccess(String message, Wallet wallet) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
    
    /*-----------------*/
    /*Helper Method    */
    /*-----------------*/
    private void getWalletData() {
        mWalletPresenter.getAllWallet();
    }
    
    private void openEditWalletActivity(Wallet wallet) {
        Intent intent = new Intent(this, EditWalletActivity.class);
        intent.putExtra("wallet", wallet);
        startActivityForResult(intent, RequestCode.EDIT_WALLET_REQUEST_CODE);
    }
    
    private void removeWallet(int position, Wallet wallet) {
        mWalletPresenter.removeWallet(position, wallet);
    }
    
    private void openAddWalletActivity() {
        Intent intent = new Intent(this, AddWalletActivity.class);
        startActivityForResult(intent, RequestCode.ADD_WALLET_REQUEST_CODE);
    }
    
    private void openActivityMyWallet() {
        Intent intent = new Intent(this, MyWalletActivity.class);
        intent.putExtra("mode", MyWalletActivity.MODE_OPEN_FROM_SELECT_WALLET);
        startActivity(intent);
    }
}
