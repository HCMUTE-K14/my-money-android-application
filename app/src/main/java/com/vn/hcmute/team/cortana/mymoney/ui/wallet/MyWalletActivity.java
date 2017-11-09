package com.vn.hcmute.team.cortana.mymoney.ui.wallet;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import butterknife.BindView;
import butterknife.OnClick;
import com.vn.hcmute.team.cortana.mymoney.MyMoneyApplication;
import com.vn.hcmute.team.cortana.mymoney.R;
import com.vn.hcmute.team.cortana.mymoney.di.component.ApplicationComponent;
import com.vn.hcmute.team.cortana.mymoney.di.component.DaggerWalletComponent;
import com.vn.hcmute.team.cortana.mymoney.di.component.WalletComponent;
import com.vn.hcmute.team.cortana.mymoney.di.module.ActivityModule;
import com.vn.hcmute.team.cortana.mymoney.di.module.WalletModule;
import com.vn.hcmute.team.cortana.mymoney.event.ActivityResultEvent;
import com.vn.hcmute.team.cortana.mymoney.model.Wallet;
import com.vn.hcmute.team.cortana.mymoney.ui.base.BaseActivity;
import com.vn.hcmute.team.cortana.mymoney.ui.view.selectwallet.SelectWalletListener;
import com.vn.hcmute.team.cortana.mymoney.ui.view.selectwallet.SelectWalletView;
import com.vn.hcmute.team.cortana.mymoney.ui.wallet.WalletContract.View;
import com.vn.hcmute.team.cortana.mymoney.utils.Constraints.RequestCode;
import com.vn.hcmute.team.cortana.mymoney.utils.Constraints.ResultCode;
import com.vn.hcmute.team.cortana.mymoney.utils.TextUtil;
import com.vn.hcmute.team.cortana.mymoney.utils.logger.MyLogger;
import java.util.List;
import javax.inject.Inject;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

/**
 * Created by infamouSs on 9/6/17.
 */

public class MyWalletActivity extends BaseActivity implements View {
    
    public static final String TAG = MyWalletActivity.class.getSimpleName();
    
    public static final int MODE_OPEN_FROM_SELECT_WALLET = 0;
    public static final int MODE_OPEN_FROM_ANOTHER = 1;
    
    @BindView(R.id.select_wallet)
    SelectWalletView mSelectWalletView;
    
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    
    @Inject
    WalletPresenter mWalletPresenter;
    
    private ProgressDialog mProgressDialog;
    private int mMode;
    private String mExceptWalletId;
    
    private SelectWalletListener mSelectWalletListener = new SelectWalletListener() {
        @Override
        public void onClickAddWallet() {
            
        }
        
        @Override
        public void onClickMyWallet() {
            
        }
        
        @Override
        public void onCLickWallet(Wallet wallet) {
            if (mMode == MODE_OPEN_FROM_ANOTHER) {
                Intent intent = new Intent();
                intent.putExtra("wallet", wallet);
                setResult(RESULT_OK, intent);
                finish();
            }
        }
        
        @Override
        public void onEditWallet(int position, Wallet wallet) {
            openActivityUpdateWallet(wallet);
        }
        
        @Override
        public void onRemoveWallet(int position, Wallet wallet) {
            removeWallet(position, wallet);
        }
        
        @Override
        public void onArchiveWallet(int position, Wallet wallet) {
            
        }
        
        @Override
        public void onTransferMoneyWallet(int position, Wallet wallet) {
            Intent intent = new Intent(MyWalletActivity.this, TransferMoneyActivity.class);
            intent.putExtra("wallet_from", wallet);
            startActivity(intent);
        }
    };
    
    @Override
    public int getLayoutId() {
        return R.layout.activity_my_wallet;
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
    protected void initializeActionBar(android.view.View rootView) {
        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(getString(R.string.txt_my_wallet));
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }
    
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
        mMode = getIntent().getIntExtra("mode", MODE_OPEN_FROM_ANOTHER);
        mExceptWalletId = getIntent().getStringExtra("except_wallet");
        initializeView();
        
        getWalletData();
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        
        return super.onOptionsItemSelected(item);
    }
    
    @Override
    protected void onDestroy() {
        mWalletPresenter.unSubscribe();
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }
    
    @Subscribe
    public void onEvent(ActivityResultEvent event) {
        if (event.getResultCode() == ResultCode.NEED_UPDATE_CURRENT_WALLET_RESULT_CODE) {
            getWalletData();
        }
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
    
    @OnClick(R.id.btn_add_wallet)
    public void onClickAddWallet() {
        openActivityAddWallet();
    }
    
    @Override
    public void initializeView() {
        mSelectWalletView.showFooter(false);
        mSelectWalletView.showTotal(false);
        mSelectWalletView.showMenuWallet(true);
        mSelectWalletView.setSelectWalletListener(mSelectWalletListener);
    }
    
    @Override
    public void showEmpty() {
        mSelectWalletView.setEmptyAdapter(getString(R.string.txt_no_data));
    }
    
    @Override
    public void showListWallet(List<Wallet> wallets) {
        
        if (!TextUtil.isEmpty(mExceptWalletId)) {
            MyLogger.d(TAG, mExceptWalletId);
            Wallet walletExcept = new Wallet();
            walletExcept.setWalletid(mExceptWalletId);
            wallets.remove(walletExcept);
        }
        mSelectWalletView.setData(wallets);
    }
    
    @Override
    public void onAddWalletSuccess(String message, Wallet wallet) {
        
    }
    
    @Override
    public void onUpdateWalletSuccess(String message, int position, Wallet wallet) {
        
    }
    
    @Override
    public void onMoveMoneySuccess(String message) {
        
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
    
    private void getWalletData() {
        mWalletPresenter.getAllWallet();
    }
    
    private void openActivityAddWallet() {
        Intent intent = new Intent(this, AddWalletActivity.class);
        startActivityForResult(intent, RequestCode.ADD_WALLET_REQUEST_CODE);
    }
    
    private void openActivityUpdateWallet(Wallet wallet) {
        Intent intent = new Intent(this, EditWalletActivity.class);
        intent.putExtra("wallet", wallet);
        startActivityForResult(intent, RequestCode.EDIT_WALLET_REQUEST_CODE);
    }
    
    private void removeWallet(int position, Wallet wallet) {
        mWalletPresenter.removeWallet(position, wallet);
    }
}
