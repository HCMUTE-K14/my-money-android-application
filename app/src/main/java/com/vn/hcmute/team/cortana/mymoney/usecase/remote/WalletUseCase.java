package com.vn.hcmute.team.cortana.mymoney.usecase.remote;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import com.vn.hcmute.team.cortana.mymoney.R;
import com.vn.hcmute.team.cortana.mymoney.data.DataRepository;
import com.vn.hcmute.team.cortana.mymoney.exception.UserLoginException;
import com.vn.hcmute.team.cortana.mymoney.exception.WalletException;
import com.vn.hcmute.team.cortana.mymoney.model.Wallet;
import com.vn.hcmute.team.cortana.mymoney.ui.base.listener.BaseCallBack;
import com.vn.hcmute.team.cortana.mymoney.usecase.base.Action;
import com.vn.hcmute.team.cortana.mymoney.usecase.base.UseCase;
import com.vn.hcmute.team.cortana.mymoney.utils.SecurityUtil;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;
import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by kunsubin on 8/22/2017.
 */

@Singleton
public class WalletUseCase extends UseCase<WalletUseCase.WalletRequest> {
    
    public static final String TAG = WalletUseCase.class.getSimpleName();
    
    private DataRepository mDataRepository;
    private Context mContext;
    
    private Disposable mDisposable;
    private CompositeDisposable mCompositeDisposable;
    private DisposableSingleObserver<Object> mDisposableSingleObserver;
    
    @Inject
    public WalletUseCase(Context context, DataRepository dataRepository) {
        this.mContext = context;
        this.mDataRepository = dataRepository;
        this.mCompositeDisposable = new CompositeDisposable();
    }
    
    @Override
    public void subscribe(WalletRequest requestValues) {
        String action = requestValues.getAction();
        
        switch (action) {
            case Action.ACTION_CREATE_WALLET:
                //doLoginNormal(requestValues.getCallBack(), requestValues.getData());
                doCreate(requestValues.getCallBack(), requestValues.getData());
                break;
            case Action.ACTION_UPDATE_WALLET:
                doUpdate(requestValues.getCallBack(), requestValues.getData());
                break;
            case Action.ACTION_DELETE_WALLET:
                doDelete(requestValues.getCallBack(), requestValues.getData());
                break;
            case Action.ACTION_MOVE_WALLET:
                doMove(requestValues.getCallBack(), requestValues.getParam());
                break;
            case Action.ACTION_GET_WALLET:
                doGetWallet(requestValues.getCallBack());
                break;
            default:
                break;
        }
    }
    
    
    @Override
    public void unSubscribe() {
        if (mCompositeDisposable != null && !mCompositeDisposable.isDisposed() &&
            mDisposable != null) {
            mCompositeDisposable.remove(mDisposable);
        }
    }
    
    private void doCreate(final BaseCallBack<Object> callBack, final Wallet wallet) {
        String userid = mDataRepository.getUserId();
        String token = mDataRepository.getUserToken();
        
        if (TextUtils.isEmpty(userid) || TextUtils.isEmpty(token)) {
            callBack.onFailure(new UserLoginException(
                      mContext.getString(R.string.message_warning_need_login)));
            return;
        }
        
        if (TextUtils.isEmpty(wallet.getWalletName())) {
            callBack.onFailure(new WalletException(
                      mContext.getString(R.string.message_validate_name_wallet)));
            return;
        }
        if (wallet.getCurrencyUnit() == null) {
            callBack.onFailure(new WalletException(
                      mContext.getString(R.string.message_validate_currency_wallet)));
            return;
        }
        
        if (TextUtils.isEmpty(wallet.getWalletImage())) {
            wallet.setWalletImage("ic_saving");
        }
        
        this.mDisposableSingleObserver = new DisposableSingleObserver<Object>() {
            @Override
            public void onSuccess(@io.reactivex.annotations.NonNull Object user) {
                
                callBack.onSuccess(user);
            }
            
            @Override
            public void onError(@io.reactivex.annotations.NonNull Throwable e) {
                callBack.onFailure(e);
            }
        };
        
        if (!this.mCompositeDisposable.isDisposed()) {
            wallet.setWalletid(SecurityUtil.getRandomUUID());
            wallet.setUserid(userid);
            
            mDisposable = mDataRepository.createWallet(wallet, userid, token)
                      .subscribeOn(Schedulers.io())
                      .observeOn(AndroidSchedulers.mainThread())
                      .doOnSubscribe(new Consumer<Disposable>() {
                          @Override
                          public void accept(Disposable disposable) throws Exception {
                              callBack.onLoading();
                          }
                      })
                      .singleOrError()
                      .subscribeWith(this.mDisposableSingleObserver);
            this.mCompositeDisposable.add(mDisposable);
            
        }
    }
    
    private void doUpdate(final BaseCallBack<Object> callBack, final Wallet wallet) {
        String userid = mDataRepository.getUserId();
        String token = mDataRepository.getUserToken();
        
        if (TextUtils.isEmpty(userid) || TextUtils.isEmpty(token)) {
            callBack.onFailure(new UserLoginException(
                      mContext.getString(R.string.message_warning_need_login)));
            return;
        }
    
        if (TextUtils.isEmpty(wallet.getWalletName())) {
            callBack.onFailure(new WalletException(
                      mContext.getString(R.string.message_validate_name_wallet)));
            return;
        }
        if (wallet.getCurrencyUnit() == null) {
            callBack.onFailure(new WalletException(
                      mContext.getString(R.string.message_validate_currency_wallet)));
            return;
        }
    
        if (TextUtils.isEmpty(wallet.getWalletImage())) {
            wallet.setWalletImage("ic_saving");
        }
        
        this.mDisposableSingleObserver = new DisposableSingleObserver<Object>() {
            @Override
            public void onSuccess(@io.reactivex.annotations.NonNull Object value) {
                
                callBack.onSuccess(value);
            }
            
            @Override
            public void onError(@io.reactivex.annotations.NonNull Throwable e) {
                callBack.onFailure(e);
            }
        };
        
        if (!this.mCompositeDisposable.isDisposed()) {
            
            mDisposable = mDataRepository.updateWallet(wallet, userid, token)
                      .subscribeOn(Schedulers.io())
                      .observeOn(AndroidSchedulers.mainThread())
                      .doOnSubscribe(new Consumer<Disposable>() {
                          @Override
                          public void accept(Disposable disposable) throws Exception {
                              callBack.onLoading();
                          }
                      })
                      .singleOrError()
                      .subscribeWith(this.mDisposableSingleObserver);
            this.mCompositeDisposable.add(mDisposable);
            
        }
    }
    
    private void doDelete(final BaseCallBack<Object> callBack, final Wallet wallet) {
        String userid = mDataRepository.getUserId();
        String token = mDataRepository.getUserToken();
        
        if (TextUtils.isEmpty(userid) || TextUtils.isEmpty(token)) {
            callBack.onFailure(new UserLoginException(
                      mContext.getString(R.string.message_warning_need_login)));
            return;
        }
        
        this.mDisposableSingleObserver = new DisposableSingleObserver<Object>() {
            @Override
            public void onSuccess(@io.reactivex.annotations.NonNull Object value) {
                
                callBack.onSuccess(value);
            }
            
            @Override
            public void onError(@io.reactivex.annotations.NonNull Throwable e) {
                callBack.onFailure(e);
            }
        };
        
        if (!this.mCompositeDisposable.isDisposed()) {
            
            mDisposable = mDataRepository.deleteWallet(userid, token, wallet.getWalletid())
                      .subscribeOn(Schedulers.io())
                      .observeOn(AndroidSchedulers.mainThread())
                      .doOnSubscribe(new Consumer<Disposable>() {
                          @Override
                          public void accept(Disposable disposable) throws Exception {
                              callBack.onLoading();
                          }
                      })
                      .singleOrError()
                      .subscribeWith(this.mDisposableSingleObserver);
            this.mCompositeDisposable.add(mDisposable);
            
        }
    }
    
    private void doMove(final BaseCallBack<Object> callBack, final String[] params) {
        String userid = mDataRepository.getUserId();
        String token = mDataRepository.getUserToken();
        
        if (TextUtils.isEmpty(userid) || TextUtils.isEmpty(token)) {
            callBack.onFailure(new UserLoginException(
                      mContext.getString(R.string.message_warning_need_login)));
            return;
        }
        
        this.mDisposableSingleObserver = new DisposableSingleObserver<Object>() {
            @Override
            public void onSuccess(@io.reactivex.annotations.NonNull Object value) {
                
                callBack.onSuccess(value);
            }
            
            @Override
            public void onError(@io.reactivex.annotations.NonNull Throwable e) {
                callBack.onFailure(e);
            }
        };
        
        if (!this.mCompositeDisposable.isDisposed()) {
            
            mDisposable = mDataRepository.moveWallet(userid, token, params[0], params[1], params[2])
                      .subscribeOn(Schedulers.io())
                      .observeOn(AndroidSchedulers.mainThread())
                      .doOnSubscribe(new Consumer<Disposable>() {
                          @Override
                          public void accept(Disposable disposable) throws Exception {
                              callBack.onLoading();
                          }
                      })
                      .singleOrError()
                      .subscribeWith(this.mDisposableSingleObserver);
            this.mCompositeDisposable.add(mDisposable);
            
        }
    }
    
    private void doGetWallet(final BaseCallBack<Object> callBack) {
        String userid = mDataRepository.getUserId();
        String token = mDataRepository.getUserToken();
        
        if (TextUtils.isEmpty(userid) || TextUtils.isEmpty(token)) {
            callBack.onFailure(new UserLoginException(
                      mContext.getString(R.string.message_warning_need_login)));
            return;
        }
        
        this.mDisposableSingleObserver = new DisposableSingleObserver<Object>() {
            @Override
            public void onSuccess(@io.reactivex.annotations.NonNull Object list) {
                
                callBack.onSuccess(list);
            }
            
            @Override
            public void onError(@io.reactivex.annotations.NonNull Throwable e) {
                callBack.onFailure(e);
            }
        };
        
        if (!this.mCompositeDisposable.isDisposed()) {
            
            mDisposable = mDataRepository.getAllWallet(userid, token)
                      .subscribeOn(Schedulers.io())
                      .observeOn(AndroidSchedulers.mainThread())
                      .doOnSubscribe(new Consumer<Disposable>() {
                          @Override
                          public void accept(Disposable disposable) throws Exception {
                              callBack.onLoading();
                          }
                      })
                      .singleOrError()
                      .subscribeWith(this.mDisposableSingleObserver);
            this.mCompositeDisposable.add(mDisposable);
            
        }
    }
    
    public static class WalletRequest implements UseCase.RequestValue {
        
        private String action;
        private BaseCallBack<Object> callBack;
        private Wallet wallet;
        private String[] params;
        
        public WalletRequest(@NonNull String action, @Nullable BaseCallBack<Object> callBack,
                  @Nullable Wallet wallet, @Nullable String[] params) {
            this.action = action;
            this.callBack = callBack;
            this.wallet = wallet;
            this.params = params;
        }
        
        public String getAction() {
            return action;
        }
        
        public void setAction(String action) {
            this.action = action;
        }
        
        public BaseCallBack<Object> getCallBack() {
            return callBack;
        }
        
        public void setCallBack(BaseCallBack<Object> callBack) {
            this.callBack = callBack;
        }
        
        public Wallet getData() {
            return wallet;
        }
        
        public void setData(Wallet object) {
            this.wallet = object;
        }
        
        public String[] getParam() {
            return params;
        }
    }
}
