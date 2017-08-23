package com.vn.hcmute.team.cortana.mymoney.ui.wallet;

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
    /*    Wallet wallet = new Wallet();
        wallet.setMoney("112321321");
        wallet.setCurrencyUnit("VND");
        wallet.setWalletid("anh");*/
        //mWalletPresenter.deleteWallet("vi2");
       // mWalletPresenter.getAllWallet();
        mWalletPresenter.moveWallet("vi1","vi2","1000000");
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
    public void onSuccess(String message) {
       Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }
    
    @Override
    public void onSuccessGetWallet(List<Wallet> wallets) {
        if(wallets!=null&&!wallets.isEmpty()){
            Toast.makeText(this, wallets.get(0).getUserid(), Toast.LENGTH_LONG).show();
            
        }else {
            Toast.makeText(this, "fail", Toast.LENGTH_LONG).show();
        }
    }
    
    @Override
    public void onFailure(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }
}
