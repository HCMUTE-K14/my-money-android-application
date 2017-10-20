package com.vn.hcmute.team.cortana.mymoney.ui.wallet;

import com.vn.hcmute.team.cortana.mymoney.model.Wallet;
import com.vn.hcmute.team.cortana.mymoney.ui.base.BasePresenter;
import com.vn.hcmute.team.cortana.mymoney.ui.base.listener.BaseCallBack;
import com.vn.hcmute.team.cortana.mymoney.usecase.base.Action;
import com.vn.hcmute.team.cortana.mymoney.usecase.base.TypeRepository;
import com.vn.hcmute.team.cortana.mymoney.usecase.remote.WalletUseCase;
import com.vn.hcmute.team.cortana.mymoney.usecase.remote.WalletUseCase.WalletRequest;
import java.util.List;
import javax.inject.Inject;

/**
 * Created by kunsubin on 8/22/2017.
 */

public class WalletPresenter extends BasePresenter<WalletContract.View> implements
                                                                        WalletContract.Presenter {
    
    public static final String TAG = WalletPresenter.class.getSimpleName();
    
    private WalletUseCase mWalletUseCase;
    
    @Inject
    public WalletPresenter(WalletUseCase walletUseCase) {
        mWalletUseCase = walletUseCase;
    }
    
    @Override
    public void addWallet(final Wallet wallet) {
        WalletUseCase.WalletRequest requestValue = new WalletRequest(Action.ACTION_CREATE_WALLET,
                  new BaseCallBack<Object>() {
                      @Override
                      public void onSuccess(Object value) {
                          getView().loading(false);
                          getView().onAddWalletSuccess((String) value, wallet);
                          
                      }
                      
                      @Override
                      public void onFailure(Throwable throwable) {
                          getView().loading(false);
                          getView().onFailure(throwable.getMessage());
                      }
                      
                      @Override
                      public void onLoading() {
                          getView().loading(true);
                      }
                  }, wallet, null);
        requestValue.setTypeRepository(TypeRepository.REMOTE);
        mWalletUseCase.subscribe(requestValue);
    }
    
    @Override
    public void updateWallet(final int position, final Wallet wallet) {
        WalletUseCase.WalletRequest requestValue = new WalletRequest(Action.ACTION_UPDATE_WALLET,
                  new BaseCallBack<Object>() {
                      @Override
                      public void onSuccess(Object value) {
                          getView().loading(false);
                          getView().onUpdateWalletSuccess((String) value, position, wallet);
                      }
                      
                      @Override
                      public void onFailure(Throwable throwable) {
                          getView().loading(false);
                          getView().onFailure(throwable.getMessage());
                      }
                      
                      @Override
                      public void onLoading() {
                          getView().loading(true);
                      }
                  }, wallet, null);
        requestValue.setTypeRepository(TypeRepository.REMOTE);
        mWalletUseCase.subscribe(requestValue);
    }
    
    @Override
    public void removeWallet(final int position, final Wallet wallet) {
        WalletUseCase.WalletRequest requestValue = new WalletRequest(Action.ACTION_DELETE_WALLET,
                  new BaseCallBack<Object>() {
                      @Override
                      public void onSuccess(Object value) {
                          getView().loading(false);
                          getView().onRemoveWalletSuccess((String) value, position, wallet);
                      }
                      
                      @Override
                      public void onFailure(Throwable throwable) {
                          getView().loading(false);
                          getView().onFailure(throwable.getMessage());
                      }
                      
                      @Override
                      public void onLoading() {
                          getView().loading(true);
                      }
                  }, wallet, null);
        requestValue.setTypeRepository(TypeRepository.REMOTE);
        mWalletUseCase.subscribe(requestValue);
    }
    
    @Override
    public void moveWallet(String walletFrom, String walletTo, String money) {
        String[] params = {walletFrom, walletTo, money};
        
        WalletUseCase.WalletRequest requestValue = new WalletRequest(Action.ACTION_MOVE_WALLET,
                  new BaseCallBack<Object>() {
                      @Override
                      public void onSuccess(Object value) {
                          getView().loading(false);
                          getView().onMoveMoneySuccess((String) value);
                      }
                      
                      @Override
                      public void onFailure(Throwable throwable) {
                          getView().loading(false);
                          getView().onFailure(throwable.getMessage());
                      }
                      
                      @Override
                      public void onLoading() {
                          getView().loading(true);
                      }
                  }, null, params);
        requestValue.setTypeRepository(TypeRepository.REMOTE);
        mWalletUseCase.subscribe(requestValue);
    }
    
    @Override
    public void getAllWallet() {
        
        WalletUseCase.WalletRequest requestValue = new WalletRequest(Action.ACTION_GET_WALLET,
                  new BaseCallBack<Object>() {
                      @Override
                      public void onSuccess(Object value) {
                          getView().loading(false);
                          List<Wallet> wallets = (List<Wallet>) value;
                          
                          if (wallets.isEmpty()) {
                              getView().showEmpty();
                          } else {
                              getView().showListWallet(wallets);
                          }
                          
                          
                      }
                      
                      @Override
                      public void onFailure(Throwable throwable) {
                          getView().loading(false);
                          getView().onFailure(throwable.getMessage());
                      }
                      
                      @Override
                      public void onLoading() {
                          getView().loading(true);
                      }
                  }, null, null);
        requestValue.setTypeRepository(TypeRepository.REMOTE);
        mWalletUseCase.subscribe(requestValue);
    }
    
    @Override
    public void unSubscribe() {
        mWalletUseCase.unSubscribe();
    }
}
