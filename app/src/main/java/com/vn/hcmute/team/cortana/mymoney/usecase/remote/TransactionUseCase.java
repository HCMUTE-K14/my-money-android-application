package com.vn.hcmute.team.cortana.mymoney.usecase.remote;

import android.content.Context;
import android.text.TextUtils;
import com.vn.hcmute.team.cortana.mymoney.R;
import com.vn.hcmute.team.cortana.mymoney.data.DataRepository;
import com.vn.hcmute.team.cortana.mymoney.exception.TransactionException;
import com.vn.hcmute.team.cortana.mymoney.exception.UserLoginException;
import com.vn.hcmute.team.cortana.mymoney.model.Transaction;
import com.vn.hcmute.team.cortana.mymoney.ui.base.listener.BaseCallBack;
import com.vn.hcmute.team.cortana.mymoney.usecase.base.Action;
import com.vn.hcmute.team.cortana.mymoney.usecase.base.TypeRepository;
import com.vn.hcmute.team.cortana.mymoney.usecase.base.UseCase;
import com.vn.hcmute.team.cortana.mymoney.usecase.remote.TransactionUseCase.TransactionRequest;
import com.vn.hcmute.team.cortana.mymoney.utils.TextUtil;
import com.vn.hcmute.team.cortana.mymoney.utils.logger.MyLogger;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;
import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by infamouSs on 9/28/17.
 */
@Singleton
public class TransactionUseCase extends UseCase<TransactionRequest> {
    
    public static final String TAG = TransactionUseCase.class.getSimpleName();
    
    private DataRepository mDataRepository;
    private Context mContext;
    
    private Disposable mDisposable;
    private CompositeDisposable mCompositeDisposable;
    private DisposableSingleObserver<Object> mDisposableSingleObserver;
    
    @Inject
    public TransactionUseCase(Context context, DataRepository dataRepository) {
        this.mDataRepository = dataRepository;
        this.mContext = context.getApplicationContext();
        this.mCompositeDisposable = new CompositeDisposable();
    }
    
    @Override
    public void subscribe(TransactionRequest requestValues) {
        String action = requestValues.getAction();
        
        switch (action) {
            case Action.ACTION_ADD_TRANSACTION:
                doAddTransaction(requestValues.getCallback(), requestValues.getTransaction(),
                          requestValues.getTypeRepository());
                break;
            case Action.ACTION_UPDATE_TRANSACTION:
                doUpdateTransaction(requestValues.getCallback(), requestValues.getTransaction(),
                          requestValues.getTypeRepository());
                break;
            case Action.ACTION_GET_TRANSACTION_BY_CATEGORY:
                doGetTransactionByCategory(requestValues.getCallback(), requestValues.getParams(),
                          requestValues.getTypeRepository());
                break;
            case Action.ACTION_GET_ALL_TRANSACTION:
                doGetAllTransaction(requestValues.getCallback(), requestValues.getTypeRepository());
                break;
            case Action.ACTION_GET_TRANSACTION_BY_ID:
                doGetTransactionById(requestValues.getCallback(), requestValues.getParams(),
                          requestValues.getTypeRepository());
                break;
            case Action.ACTION_GET_TRANSACTION_BY_TYPE:
                doGetTransactionByType(requestValues.getCallback(), requestValues.getParams(),
                          requestValues.getTypeRepository());
                break;
            case Action.ACTION_GET_TRANSACTION_BY_TIME:
                doGetTransactionByTime(requestValues.getCallback(), requestValues.getParams(),
                          requestValues.getTypeRepository());
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
    
    private void doGetTransactionByType(final BaseCallBack<Object> callBack, String[] params,
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
        
        String type = params[0];
        String wallet_id = params[1];
        
        if (TextUtil.isEmpty(type)) {
            callBack.onFailure(new TransactionException("Wrong params"));
            return;
        }
        
        if (!this.mCompositeDisposable.isDisposed()) {
            if (typeRepository == TypeRepository.LOCAL) {
                //                mDisposable = mDataRepository.getListLocalCategory(type)
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
                mDisposable = mDataRepository
                          .getTransactionByType(userid, token, type, wallet_id)
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
    
    private void doGetTransactionByTime(final BaseCallBack<Object> callBack, String[] params,
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
        
        String startDate = params[0];
        String endDate = params[1];
        String wallet_id = params[2];
        
        if (!this.mCompositeDisposable.isDisposed()) {
            if (typeRepository == TypeRepository.LOCAL) {
                //                mDisposable = mDataRepository.getListLocalCategory(type)
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
                mDisposable = mDataRepository
                          .getTransactionByTime(userid, token, startDate, endDate, wallet_id)
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
    
    private void doGetTransactionById(final BaseCallBack<Object> callBack, String[] params,
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
        
        String id = params[0];
        
        if (!this.mCompositeDisposable.isDisposed()) {
            if (typeRepository == TypeRepository.LOCAL) {
                //                mDisposable = mDataRepository.getListLocalCategory(type)
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
                mDisposable = mDataRepository
                          .getTransactionById(id, userid, token)
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
    
    private void doGetAllTransaction(final BaseCallBack<Object> callBack, TypeRepository typeRepository) {
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
    
        if (!this.mCompositeDisposable.isDisposed()) {
            if (typeRepository == TypeRepository.LOCAL) {
                //                mDisposable = mDataRepository.getListLocalCategory(type)
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
                mDisposable = mDataRepository
                          .getTransaction(userid, token)
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
    
    private void doGetTransactionByCategory(final BaseCallBack<Object> callBack, String[] params,
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
        
        String cate_id = params[0];
        String wallet_id = params[1];
        
        if (TextUtil.isEmpty(cate_id) || TextUtil.isEmpty(wallet_id)) {
            callBack.onFailure(new TransactionException("Wrong params"));
            return;
        }
        
        if (!this.mCompositeDisposable.isDisposed()) {
            if (typeRepository == TypeRepository.LOCAL) {
                //                mDisposable = mDataRepository.getListLocalCategory(type)
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
                MyLogger.d(TAG, "GET BY CATEGORY");
                mDisposable = mDataRepository
                          .getTransactionByCategory(userid, token, cate_id, wallet_id)
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
    
    private void doUpdateTransaction(final BaseCallBack<Object> callBack, Transaction transaction,
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
        
        if (!this.mCompositeDisposable.isDisposed()) {
            if (typeRepository == TypeRepository.LOCAL) {
                //                mDisposable = mDataRepository.getListLocalCategory(type)
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
                
                mDisposable = mDataRepository.updateTransaction(userid, token, transaction)
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
    
    private void doAddTransaction(final BaseCallBack<Object> callBack, Transaction transaction,
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
        
        if (!this.mCompositeDisposable.isDisposed()) {
            if (typeRepository == TypeRepository.LOCAL) {
                //                mDisposable = mDataRepository.getListLocalCategory(type)
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
                
                /*transaction.setUser_id(userid);
                transaction.setTrans_id(SecurityUtil.getRandomUUID());*/
                if (TextUtils.isEmpty(userid) || TextUtils.isEmpty(token)) {
                    callBack.onFailure(new UserLoginException(
                              mContext.getString(R.string.message_warning_need_login)));
                    return;
                }
                
                mDisposable = mDataRepository.addTransaction(userid, token, transaction)
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
    
    public static class TransactionRequest implements UseCase.RequestValue {
        
        String action;
        BaseCallBack<Object> callback;
        Transaction transaction;
        String[] params;
        TypeRepository typeRepository;
        
        public TransactionRequest(String action, BaseCallBack<Object> callback,
                  Transaction transaction, String[] params) {
            this(action, callback, transaction, params, TypeRepository.LOCAL);
        }
        
        public TransactionRequest(String action, BaseCallBack<Object> callback,
                  Transaction transaction, String[] params, TypeRepository typeRepository) {
            this.action = action;
            this.callback = callback;
            this.transaction = transaction;
            this.params = params;
            this.typeRepository = typeRepository;
        }
        
        public String getAction() {
            return action;
        }
        
        public void setAction(String action) {
            this.action = action;
        }
        
        public BaseCallBack<Object> getCallback() {
            return callback;
        }
        
        public void setCallback(
                  BaseCallBack<Object> callback) {
            this.callback = callback;
        }
        
        public Transaction getTransaction() {
            return transaction;
        }
        
        public void setTransaction(Transaction transaction) {
            this.transaction = transaction;
        }
        
        public String[] getParams() {
            return params;
        }
        
        public void setParams(String[] params) {
            this.params = params;
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
