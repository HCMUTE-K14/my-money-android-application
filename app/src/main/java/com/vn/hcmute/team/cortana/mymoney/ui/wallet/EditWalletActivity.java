package com.vn.hcmute.team.cortana.mymoney.ui.wallet;

import com.vn.hcmute.team.cortana.mymoney.model.Wallet;
import com.vn.hcmute.team.cortana.mymoney.ui.base.BaseActivity;
import com.vn.hcmute.team.cortana.mymoney.ui.wallet.WalletContract.View;
import java.util.List;

/**
 * Created by infamouSs on 9/5/17.
 */

public class EditWalletActivity extends BaseActivity implements View {
    
    public static final String TAG = EditWalletActivity.class.getSimpleName();
    
    public EditWalletActivity() {
        
    }
    
    @Override
    public int getLayoutId() {
        return 0;
    }
    
    @Override
    protected void initializeDagger() {
        
    }
    
    @Override
    protected void initializePresenter() {
        
    }
    
    @Override
    protected void initializeActionBar(android.view.View rootView) {
        
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
        
    }
    
    @Override
    public void loading(boolean isLoading) {
        
    }
}
