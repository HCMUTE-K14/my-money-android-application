package com.vn.hcmute.team.cortana.mymoney.ui.wallet;

import com.vn.hcmute.team.cortana.mymoney.model.Wallet;
import com.vn.hcmute.team.cortana.mymoney.ui.base.view.BaseView;
import java.util.List;

/**
 * Created by kunsubin on 8/22/2017.
 */

public interface WalletContract {
    
    interface View extends BaseView {
        
        void initializeView();
        
        void showEmpty();
        
        void showListWallet(List<Wallet> wallets);
        
        void onAddWalletSuccess(String message, Wallet wallet);
        
        void onUpdateWalletSuccess(String message, int position, Wallet wallet);
        
        void onRemoveWalletSuccess(String message, int position, Wallet wallet);
        
        void onMoveMoneySuccess(String message);
        
        void onFailure(String message);
        
        void loading(boolean isLoading);
    }
    

    
    interface Presenter {
        
        void addWallet(Wallet wallet);
        
        void updateWallet(int position, Wallet wallet);
        
        void removeWallet(int position, Wallet wallet);
        
        void moveWallet(String walletFrom, String walletTo, String money);
        
        void getAllWallet();
        
        void unSubscribe();
    }
}
