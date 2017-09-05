package com.vn.hcmute.team.cortana.mymoney.ui.wallet;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;
import butterknife.BindView;
import com.vn.hcmute.team.cortana.mymoney.MyMoneyApplication;
import com.vn.hcmute.team.cortana.mymoney.R;
import com.vn.hcmute.team.cortana.mymoney.di.component.ApplicationComponent;
import com.vn.hcmute.team.cortana.mymoney.di.component.DaggerWalletComponent;
import com.vn.hcmute.team.cortana.mymoney.di.component.WalletComponent;
import com.vn.hcmute.team.cortana.mymoney.di.module.ActivityModule;
import com.vn.hcmute.team.cortana.mymoney.di.module.WalletModule;
import com.vn.hcmute.team.cortana.mymoney.model.Wallet;
import com.vn.hcmute.team.cortana.mymoney.ui.base.BaseActivity;
import com.vn.hcmute.team.cortana.mymoney.ui.view.selectwallet.SelectWalletListener;
import com.vn.hcmute.team.cortana.mymoney.ui.view.selectwallet.SelectWalletView;
import java.util.List;
import javax.inject.Inject;

/**
 * Created by infamouSs on 8/30/17.
 */

public class SelectWalletActivity extends BaseActivity implements WalletContract.View {
    
    public static final String TAG = SelectWalletActivity.class.getSimpleName();
    
    @BindView(R.id.select_wallet)
    SelectWalletView mSelectWalletView;
    
    @BindView(R.id.btn_close)
    LinearLayout mButtonClose;
    
    @Inject
    WalletPresenter mWalletPresenter;
    
    private SelectWalletListener mSelectWalletListener = new SelectWalletListener() {
        @Override
        public void onClickAddWallet() {
            Toast.makeText(SelectWalletActivity.this, "ADD", Toast.LENGTH_SHORT).show();
        }
        
        @Override
        public void onClickMyWallet() {
            Toast.makeText(SelectWalletActivity.this, "MYWALLET", Toast.LENGTH_SHORT).show();
        }
        
        @Override
        public void onCLickTotal() {
            Toast.makeText(SelectWalletActivity.this, "TOTAL", Toast.LENGTH_SHORT).show();
        }
        
        @Override
        public void onCLickWallet(Wallet wallet) {
            Toast.makeText(SelectWalletActivity.this, "ITEMCLICK", Toast.LENGTH_SHORT).show();
        }
        
        @Override
        public void onEditWallet(int position, Wallet wallet) {
            Toast.makeText(SelectWalletActivity.this, "EDIT", Toast.LENGTH_SHORT).show();
        }
        
        @Override
        public void onRemoveWallet(int position, Wallet wallet) {
            
            mWalletPresenter.removeWallet(position, wallet);
        }
        
        @Override
        public void onArchiveWallet(int position, Wallet wallet) {
            boolean isArchive = wallet.isArchive();
            wallet.setArchive(!isArchive);
            
            mWalletPresenter.updateWallet(position, wallet);
        }
    };
    
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
    public void initializeView() {
        mSelectWalletView.setSelectWalletListener(mSelectWalletListener);
    }
    
    @Override
    public void showEmpty() {
        mSelectWalletView.setEmptyAdapter("No data");
    }
    
    @Override
    public void showListWallet(List<Wallet> wallets) {
        mSelectWalletView.setData(wallets);
    }
    
    @Override
    public void onAddWalletSuccess(String message) {
        Toast.makeText(this, "ADDDD", Toast.LENGTH_SHORT).show();
        //TODO: OPEN Activity
    }
    
    @Override
    public void onUpdateWalletSuccess(String message, int position, Wallet wallet) {
        mSelectWalletView.updateArchiveWallet(position, wallet);
    }
    
    
    @Override
    public void onRemoveWalletSuccess(String message, int position, Wallet wallet) {
        mSelectWalletView.removeWallet(position, wallet);
    }
    
    @Override
    public void onMoveMoneySuccess(String message) {
        
    }
    
    @Override
    public void onFailure(String message) {
        mSelectWalletView.setEmptyAdapter(message);
    }
    
    @Override
    public void loading(boolean isLoading) {
        mSelectWalletView.loading(isLoading);
    }
    
    
    private void getWalletData() {
        mWalletPresenter.getAllWallet();
    }
    
}
