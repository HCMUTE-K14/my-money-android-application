package com.vn.hcmute.team.cortana.mymoney.ui.view.selectwallet;

import com.vn.hcmute.team.cortana.mymoney.model.Wallet;

/**
 * Created by infamouSs on 9/1/17.
 */

public interface SelectWalletListener {
    
    void onClickAddWallet();
    
    void onClickMyWallet();
    
    void onCLickTotal();
    
    void onCLickWallet(Wallet wallet);
    
    void onEditWallet(int position, Wallet wallet);
    
    void onRemoveWallet(int position, Wallet wallet);
    
    void onArchiveWallet(int position, Wallet wallet);
}
