package com.vn.hcmute.team.cortana.mymoney.ui.wallet;

import com.vn.hcmute.team.cortana.mymoney.model.Wallet;
import com.vn.hcmute.team.cortana.mymoney.ui.base.view.BaseView;
import java.util.List;

/**
 * Created by kunsubin on 8/22/2017.
 */

public interface WalletContract {
    
    interface View extends BaseView {
        
        void onSuccess(String message);

        void onSuccessGetWallet(List<Wallet> wallets);

        void onFailure(String message);
    }
    
    interface Presenter {
        
        void createWallet(Wallet wallet);
        
        void updateWallet(Wallet wallet);
        
        void deleteWallet(String idWallet);
        
        void moveWallet(String walletFrom, String walletTo, String money);
        
        void getAllWallet();
    }
}
