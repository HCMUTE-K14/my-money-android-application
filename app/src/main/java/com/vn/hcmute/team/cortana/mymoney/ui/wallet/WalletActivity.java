package com.vn.hcmute.team.cortana.mymoney.ui.wallet;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
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
import com.vn.hcmute.team.cortana.mymoney.model.Wallet;
import com.vn.hcmute.team.cortana.mymoney.ui.base.BaseActivity;
import java.util.List;
import javax.inject.Inject;

/**
 * Created by kunsubin on 8/22/2017.
 */

public class WalletActivity extends BaseActivity implements WalletContract.View {
    
    @Inject
    WalletPresenter mWalletPresenter;
    
    @BindView(R.id.button2)
    Button mButton;
    
    @OnClick(R.id.button2)
    public void onClick() {
        mWalletPresenter.moveWallet("vi1", "vi2", "1000000");
    }
    
    @Override
    public int getLayoutId() {
        return R.layout.activity_wallet;
    }
    
    @Override
    protected void initializeDagger() {
        ApplicationComponent applicationComponent = ((MyMoneyApplication) this.getApplication())
                  .getAppComponent();
        WalletComponent walletComponent = DaggerWalletComponent
                  .builder()
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
    protected void initializeActionBar(View rootView) {
        
    }
    
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    
    @Override
    protected void onDestroy() {
        super.onDestroy();
        mWalletPresenter.unSubscribe();
    }
    
    @Override
    public void initializeView() {
        
    }
    
    @Override
    public void showEmpty() {
        
    }
    
    @Override
    public void showListWallet(List<Wallet> wallets) {
        
    }
    
    @Override
    public void onAddWalletSuccess(String message) {
        
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
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }
    
    @Override
    public void loading(boolean isLoading) {
        
    }
}
