package com.vn.hcmute.team.cortana.mymoney.usecase.remote;

import android.content.Context;
import android.text.TextUtils;
import com.vn.hcmute.team.cortana.mymoney.R;
import com.vn.hcmute.team.cortana.mymoney.data.DataRepository;
import com.vn.hcmute.team.cortana.mymoney.data.cache.PreferencesHelper;
import com.vn.hcmute.team.cortana.mymoney.exception.DebtLoanException;
import com.vn.hcmute.team.cortana.mymoney.exception.UserLoginException;
import com.vn.hcmute.team.cortana.mymoney.model.DebtLoan;
import com.vn.hcmute.team.cortana.mymoney.ui.base.listener.BaseCallBack;
import com.vn.hcmute.team.cortana.mymoney.usecase.base.Action;
import com.vn.hcmute.team.cortana.mymoney.usecase.base.TypeRepository;
import com.vn.hcmute.team.cortana.mymoney.usecase.base.UseCase;
import com.vn.hcmute.team.cortana.mymoney.usecase.remote.DebtLoanUseCase.DebtLoanRequest;
import com.vn.hcmute.team.cortana.mymoney.utils.TextUtil;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;
import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by infamouSs on 10/18/17.
 */
@Singleton
public class DebtLoanUseCase extends UseCase<DebtLoanRequest> {
    
    public static final String TAG = DebtLoanUseCase.class.getSimpleName();
    @Inject
    PreferencesHelper mPreferenceHelper;
    private DataRepository mDataRepository;
    private Context mContext;
    private Disposable mDisposable;
    private CompositeDisposable mCompositeDisposable;
    private DisposableSingleObserver<Object> mDisposableSingleObserver;
    
    @Inject
    public DebtLoanUseCase(Context context, DataRepository dataRepository) {
        this.mContext = context.getApplicationContext();
        this.mDataRepository = dataRepository;
        this.mCompositeDisposable = new CompositeDisposable();
    }
    
    @Override
    public void subscribe(DebtLoanRequest requestValues) {
        String action = requestValues.getAction();
        
        switch (action) {
            case Action.ACTION_GET_DEBT_LOAN_BY_TYPE:
                doGetDebtLoanByType(requestValues.getCallback(), requestValues.getParams(),
                          requestValues.getTypeRepository());
                break;
            case Action.ACTION_ADD_DEBT_LOAN:
                doAddDebtLoan(requestValues.getCallback(), requestValues.getData(),
                          requestValues.getTypeRepository());
                break;
            case Action.ACTION_UPDATE_DEBT_LOAN:
                doUpdateDebtLoan(requestValues.getCallback(), requestValues.getData(),
                          requestValues.getParams(), requestValues.getTypeRepository());
                break;
            case Action.ACTION_DELETE_DEBT_LOAN:
                doDeleteDebtLoan(requestValues.getCallback(), requestValues.getData(),
                          requestValues.getTypeRepository());
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
    
    
    private void doDeleteDebtLoan(final BaseCallBack<Object> callBack, DebtLoan data,
              TypeRepository typeRepository) {
        this.mDisposableSingleObserver = new DisposableSingleObserver<Object>() {
            @Override
            public void onSuccess(@io.reactivex.annotations.NonNull Object result) {
                callBack.onSuccess(result);
            }
            
            @Override
            public void onError(@io.reactivex.annotations.NonNull Throwable e) {
                callBack.onFailure(e);
            }
        };
        
        String wallet_id = mPreferenceHelper.getCurrentWallet().getWalletid();
        if (TextUtil.isEmpty(wallet_id)) {
            callBack.onFailure(new RuntimeException(
                      mContext.getString(R.string.message_warning_choose_wallet)));
            return;
        }
        if (!this.mCompositeDisposable.isDisposed()) {
            if (typeRepository == TypeRepository.LOCAL) {
                //                mDisposable = mDataRepository.getDebtLoanByType(type)
                //                          .subscribeOn(Schedulers.computation())
                //                          .observeOn(AndroidSchedulers.mainThread())
                //                          .doOnSubscribe(new Consumer<Disposable>() {
                //                              @Override
                //                              public void accept(Disposable disposable) throws Exception {
                //                                  callBack.onLoading();
                //                              }
                //                          })
                //                          .singleOrError()
                //                          .subscribeWith(this.mDisposableSingleObserver);
            } else if (typeRepository == TypeRepository.REMOTE) {
                String userid = mDataRepository.getUserId();
                String token = mDataRepository.getUserToken();
                
                if (TextUtils.isEmpty(userid) || TextUtils.isEmpty(token)) {
                    callBack.onFailure(new UserLoginException(
                              mContext.getString(R.string.message_warning_need_login)));
                    return;
                }
                
                mDisposable = mDataRepository.deleteDebtLoan(userid, token, data)
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
            }
            
            this.mCompositeDisposable.add(mDisposable);
        }
    }
    
    private void doUpdateDebtLoan(final BaseCallBack<Object> callBack, DebtLoan data,
              String[] params,
              TypeRepository typeRepository) {
        this.mDisposableSingleObserver = new DisposableSingleObserver<Object>() {
            @Override
            public void onSuccess(@io.reactivex.annotations.NonNull Object result) {
                callBack.onSuccess(result);
            }
            
            @Override
            public void onError(@io.reactivex.annotations.NonNull Throwable e) {
                callBack.onFailure(e);
            }
        };
        
        String wallet_id = mPreferenceHelper.getCurrentWallet().getWalletid();
        if (TextUtil.isEmpty(wallet_id)) {
            callBack.onFailure(new RuntimeException(
                      mContext.getString(R.string.message_warning_choose_wallet)));
            return;
        }
        if (!this.mCompositeDisposable.isDisposed()) {
            if (typeRepository == TypeRepository.LOCAL) {
                //                mDisposable = mDataRepository.getDebtLoanByType(type)
                //                          .subscribeOn(Schedulers.computation())
                //                          .observeOn(AndroidSchedulers.mainThread())
                //                          .doOnSubscribe(new Consumer<Disposable>() {
                //                              @Override
                //                              public void accept(Disposable disposable) throws Exception {
                //                                  callBack.onLoading();
                //                              }
                //                          })
                //                          .singleOrError()
                //                          .subscribeWith(this.mDisposableSingleObserver);
            } else if (typeRepository == TypeRepository.REMOTE) {
                String userid = mDataRepository.getUserId();
                String token = mDataRepository.getUserToken();
                
                if (TextUtils.isEmpty(userid) || TextUtils.isEmpty(token)) {
                    callBack.onFailure(new UserLoginException(
                              mContext.getString(R.string.message_warning_need_login)));
                    return;
                }
                
                mDisposable = mDataRepository.updateDebtLoan(userid, token, wallet_id, data)
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
            }
            
            this.mCompositeDisposable.add(mDisposable);
        }
    }
    
    private void doAddDebtLoan(final BaseCallBack<Object> callBack, DebtLoan data,
              TypeRepository typeRepository) {
        this.mDisposableSingleObserver = new DisposableSingleObserver<Object>() {
            @Override
            public void onSuccess(@io.reactivex.annotations.NonNull Object result) {
                callBack.onSuccess(result);
            }
            
            @Override
            public void onError(@io.reactivex.annotations.NonNull Throwable e) {
                callBack.onFailure(e);
            }
        };
        
        String wallet_id = mPreferenceHelper.getCurrentWallet().getWalletid();
        if (TextUtil.isEmpty(wallet_id)) {
            callBack.onFailure(new RuntimeException(
                      mContext.getString(R.string.message_warning_choose_wallet)));
            return;
        }
        if (!this.mCompositeDisposable.isDisposed()) {
            if (typeRepository == TypeRepository.LOCAL) {
                //                mDisposable = mDataRepository.getDebtLoanByType(type)
                //                          .subscribeOn(Schedulers.computation())
                //                          .observeOn(AndroidSchedulers.mainThread())
                //                          .doOnSubscribe(new Consumer<Disposable>() {
                //                              @Override
                //                              public void accept(Disposable disposable) throws Exception {
                //                                  callBack.onLoading();
                //                              }
                //                          })
                //                          .singleOrError()
                //                          .subscribeWith(this.mDisposableSingleObserver);
            } else if (typeRepository == TypeRepository.REMOTE) {
                String userid = mDataRepository.getUserId();
                String token = mDataRepository.getUserToken();
                
                if (TextUtils.isEmpty(userid) || TextUtils.isEmpty(token)) {
                    callBack.onFailure(new UserLoginException(
                              mContext.getString(R.string.message_warning_need_login)));
                    return;
                }
                
                mDisposable = mDataRepository.addDebtLoan(userid, token, data)
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
            }
            
            this.mCompositeDisposable.add(mDisposable);
        }
    }
    
    private void doGetDebtLoanByType(final BaseCallBack<Object> callBack, String[] params,
              TypeRepository typeRepository) {
        this.mDisposableSingleObserver = new DisposableSingleObserver<Object>() {
            @Override
            public void onSuccess(@io.reactivex.annotations.NonNull Object result) {
                callBack.onSuccess(result);
            }
            
            @Override
            public void onError(@io.reactivex.annotations.NonNull Throwable e) {
                callBack.onFailure(e);
            }
        };
        
        String type = params[0]; //loan or debt
        if (!type.equals("loan") || !type.equals("debt")) {
            callBack.onFailure(new DebtLoanException(
                      mContext.getString(R.string.message_warning_wrong_param_input)));
            return;
        }
        String wallet_id = mPreferenceHelper.getCurrentWallet().getWalletid();
        if (TextUtil.isEmpty(wallet_id)) {
            callBack.onFailure(new RuntimeException(
                      mContext.getString(R.string.message_warning_choose_wallet)));
            return;
        }
        if (!this.mCompositeDisposable.isDisposed()) {
            if (typeRepository == TypeRepository.LOCAL) {
                //                mDisposable = mDataRepository.getDebtLoanByType(type)
                //                          .subscribeOn(Schedulers.computation())
                //                          .observeOn(AndroidSchedulers.mainThread())
                //                          .doOnSubscribe(new Consumer<Disposable>() {
                //                              @Override
                //                              public void accept(Disposable disposable) throws Exception {
                //                                  callBack.onLoading();
                //                              }
                //                          })
                //                          .singleOrError()
                //                          .subscribeWith(this.mDisposableSingleObserver);
            } else if (typeRepository == TypeRepository.REMOTE) {
                String userid = mDataRepository.getUserId();
                String token = mDataRepository.getUserToken();
                
                if (TextUtils.isEmpty(userid) || TextUtils.isEmpty(token)) {
                    callBack.onFailure(new UserLoginException(
                              mContext.getString(R.string.message_warning_need_login)));
                    return;
                }
                
                mDisposable = mDataRepository.getDebtLoanByType(userid, token, wallet_id, type)
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
            }
            
            this.mCompositeDisposable.add(mDisposable);
        }
    }
    
    public static class DebtLoanRequest implements UseCase.RequestValue {
        
        private String action;
        private DebtLoan data;
        private String[] params;
        private BaseCallBack<Object> callback;
        private TypeRepository typeRepository;
        
        public DebtLoanRequest(String action, DebtLoan data, String[] param,
                  BaseCallBack<Object> callback, TypeRepository typeRepository) {
            this.action = action;
            this.data = data;
            this.params = param;
            this.typeRepository = typeRepository;
            this.callback = callback;
        }
        
        public DebtLoanRequest(String action, DebtLoan data, String[] param,
                  BaseCallBack<Object> callBack) {
            this(action, data, param, callBack, TypeRepository.LOCAL);
        }
        
        public String getAction() {
            return action;
        }
        
        public void setAction(String action) {
            this.action = action;
        }
        
        public DebtLoan getData() {
            return data;
        }
        
        public void setData(DebtLoan data) {
            this.data = data;
        }
        
        public String[] getParams() {
            return params;
        }
        
        public void setParams(String[] params) {
            this.params = params;
        }
        
        public BaseCallBack<Object> getCallback() {
            return callback;
        }
        
        public void setCallback(
                  BaseCallBack<Object> callback) {
            this.callback = callback;
        }
        
        public TypeRepository getTypeRepository() {
            return typeRepository;
        }
        
        public void setTypeRepository(
                  TypeRepository typeRepository) {
            this.typeRepository = typeRepository;
        }
    }
}
