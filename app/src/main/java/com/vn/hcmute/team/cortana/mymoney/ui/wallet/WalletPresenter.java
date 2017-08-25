package com.vn.hcmute.team.cortana.mymoney.ui.wallet;

import com.vn.hcmute.team.cortana.mymoney.model.Wallet;
import com.vn.hcmute.team.cortana.mymoney.ui.base.BasePresenter;
import com.vn.hcmute.team.cortana.mymoney.ui.base.listener.BaseCallBack;
import com.vn.hcmute.team.cortana.mymoney.usecase.base.Action;
import com.vn.hcmute.team.cortana.mymoney.usecase.remote.WalletUseCase;
import java.util.List;
import javax.inject.Inject;

/**
 * Created by kunsubin on 8/22/2017.
 */

public class WalletPresenter extends BasePresenter<WalletContract.View> implements
                                                                        WalletContract.Presenter {
    
    WalletUseCase mWalletUseCase;
    BaseCallBack<Object> mObjectBaseCallBack = new BaseCallBack<Object>() {
        
        @Override
        public void onSuccess(Object value) {
            getView().onSuccess((String) value);
            // getView().onSuccessGetWallet();
        }
        
        @Override
        public void onFailure(Throwable throwable) {
            getView().onFailure(throwable.getMessage());
        }
        
        @Override
        public void onLoading() {
            
        }
    };
    
    
    @Inject
    public WalletPresenter(WalletUseCase walletUseCase) {
        mWalletUseCase = walletUseCase;
    }
    
    @Override
    public void createWallet(Wallet wallet) {
        WalletUseCase.WalletRequest walletRequest = new WalletUseCase.WalletRequest(
                  Action.ACTION_CREATE_WALLET, mObjectBaseCallBack, wallet, null);
        mWalletUseCase.subscribe(walletRequest);
    }
    
    @Override
    public void updateWallet(Wallet wallet) {
        WalletUseCase.WalletRequest walletRequest = new WalletUseCase.WalletRequest(
                  Action.ACTION_UPDATE_WALLET, mObjectBaseCallBack, wallet, null);
        mWalletUseCase.subscribe(walletRequest);
    }
    
    @Override
    public void deleteWallet(String idWallet) {
        String[] params = {idWallet};
        WalletUseCase.WalletRequest walletRequest = new WalletUseCase.WalletRequest(
                  Action.ACTION_DELETE_WALLET, mObjectBaseCallBack, null, params);
        mWalletUseCase.subscribe(walletRequest);
    }
    
    @Override
    public void moveWallet(String walletFrom, String walletTo, String money) {
        String[] params = {walletFrom, walletTo, money};
        WalletUseCase.WalletRequest walletRequest = new WalletUseCase.WalletRequest(
                  Action.ACTION_MOVE_WALLET, mObjectBaseCallBack, null, params);
        mWalletUseCase.subscribe(walletRequest);
    }
    
    @Override
    public void getAllWallet() {
        
        BaseCallBack<Object> baseCallBack = new BaseCallBack<Object>() {
            @Override
            public void onSuccess(Object value) {
                getView().onSuccessGetWallet((List<Wallet>) value);
            }
            
            @Override
            public void onFailure(Throwable throwable) {
                getView().onFailure(throwable.getMessage());
            }
            
            @Override
            public void onLoading() {
                
            }
        };
        
        WalletUseCase.WalletRequest walletRequest = new WalletUseCase.WalletRequest(
                  Action.ACTION_GET_WALLET, baseCallBack, null, null);
        mWalletUseCase.subscribe(walletRequest);
    }
}
