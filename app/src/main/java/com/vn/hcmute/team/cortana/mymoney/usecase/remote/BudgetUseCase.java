package com.vn.hcmute.team.cortana.mymoney.usecase.remote;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import com.vn.hcmute.team.cortana.mymoney.R;
import com.vn.hcmute.team.cortana.mymoney.data.DataRepository;
import com.vn.hcmute.team.cortana.mymoney.exception.UserLoginException;
import com.vn.hcmute.team.cortana.mymoney.model.Budget;
import com.vn.hcmute.team.cortana.mymoney.ui.base.listener.BaseCallBack;
import com.vn.hcmute.team.cortana.mymoney.usecase.base.Action;
import com.vn.hcmute.team.cortana.mymoney.usecase.base.UseCase;
import com.vn.hcmute.team.cortana.mymoney.usecase.remote.BudgetUseCase.BudgetRequest;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;
import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by kunsubin on 8/23/2017.
 */
@Singleton
public class BudgetUseCase extends UseCase<BudgetRequest> {
    
    private DataRepository mDataRepository;
    private Context mContext;
    
    private Disposable mDisposable;
    private CompositeDisposable mCompositeDisposable;
    private DisposableSingleObserver<Object> mDisposableSingleObserver;
    
    @Inject
    public BudgetUseCase(Context context, DataRepository dataRepository) {
        this.mContext = context;
        this.mDataRepository = dataRepository;
        this.mCompositeDisposable = new CompositeDisposable();
    }
    
    
    @Override
    public void subscribe(BudgetRequest requestValues) {
        String action = requestValues.getAction();
        switch (action) {
            case Action.ACTION_GET_BUDGET:
                doGetBudget(requestValues.getCallBack());
                break;
            case Action.ACTION_CREATE_BUDGET:
                doCreateBudget(requestValues.getCallBack(), requestValues.getData());
                break;
            case Action.ACTION_UPDATE_BUDGET:
                doUpdateBudget(requestValues.getCallBack(), requestValues.getData());
                break;
            case Action.ACTION_DELETE_BUDGET:
                doDeleteBudget(requestValues.getCallBack(), requestValues.getParam());
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
    
    private void doGetBudget(final BaseCallBack<Object> callBack) {
        String userid = mDataRepository.getUserId();
        String token = mDataRepository.getUserToken();
        
        if (TextUtils.isEmpty(userid) || TextUtils.isEmpty(token)) {
            callBack.onFailure(new UserLoginException(
                      mContext.getString(R.string.message_warning_need_login)));
            return;
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
            
            mDisposable = mDataRepository.getBudget(userid, token)
                      .subscribeOn(Schedulers.io())
                      .observeOn(AndroidSchedulers.mainThread())
                      .doOnSubscribe(new Consumer<Disposable>() {
                          @Override
                          public void accept(Disposable disposable) throws Exception {
                              callBack.onLoading();
                          }
                      })
                      .cacheWithInitialCapacity(10)
                      .singleOrError()
                      .subscribeWith(this.mDisposableSingleObserver);
            this.mCompositeDisposable.add(mDisposable);
        }
    }
    
    private void doCreateBudget(final BaseCallBack<Object> callBack, Budget budget) {
        String userid = mDataRepository.getUserId();
        String token = mDataRepository.getUserToken();
        
        if (TextUtils.isEmpty(userid) || TextUtils.isEmpty(token)) {
            callBack.onFailure(new UserLoginException(
                      mContext.getString(R.string.message_warning_need_login)));
            return;
        }
        
        this.mDisposableSingleObserver = new DisposableSingleObserver<Object>() {
            @Override
            public void onSuccess(@io.reactivex.annotations.NonNull Object object) {
                
                callBack.onSuccess(object);
            }
            
            @Override
            public void onError(@io.reactivex.annotations.NonNull Throwable e) {
                callBack.onFailure(e);
            }
        };
        
        if (!this.mCompositeDisposable.isDisposed()) {
            
            mDisposable = mDataRepository.createBudget(budget, userid, token)
                      .subscribeOn(Schedulers.io())
                      .observeOn(AndroidSchedulers.mainThread())
                      .doOnSubscribe(new Consumer<Disposable>() {
                          @Override
                          public void accept(Disposable disposable) throws Exception {
                              callBack.onLoading();
                          }
                      })
                      .cacheWithInitialCapacity(10)
                      .singleOrError()
                      .subscribeWith(this.mDisposableSingleObserver);
            this.mCompositeDisposable.add(mDisposable);
            
        }
    }
    
    private void doUpdateBudget(final BaseCallBack<Object> callBack, Budget budget) {
        String userid = mDataRepository.getUserId();
        String token = mDataRepository.getUserToken();
        
        if (TextUtils.isEmpty(userid) || TextUtils.isEmpty(token)) {
            callBack.onFailure(new UserLoginException(
                      mContext.getString(R.string.message_warning_need_login)));
            return;
        }
        
        this.mDisposableSingleObserver = new DisposableSingleObserver<Object>() {
            @Override
            public void onSuccess(@io.reactivex.annotations.NonNull Object object) {
                
                callBack.onSuccess(object);
            }
            
            @Override
            public void onError(@io.reactivex.annotations.NonNull Throwable e) {
                callBack.onFailure(e);
            }
        };
        
        if (!this.mCompositeDisposable.isDisposed()) {
            
            mDisposable = mDataRepository.updateBudget(budget, userid, token)
                      .subscribeOn(Schedulers.io())
                      .observeOn(AndroidSchedulers.mainThread())
                      .doOnSubscribe(new Consumer<Disposable>() {
                          @Override
                          public void accept(Disposable disposable) throws Exception {
                              callBack.onLoading();
                          }
                      })
                      .cacheWithInitialCapacity(10)
                      .singleOrError()
                      .subscribeWith(this.mDisposableSingleObserver);
            this.mCompositeDisposable.add(mDisposable);
            
        }
    }
    
    private void doDeleteBudget(final BaseCallBack<Object> callBack, String[] params) {
        String userid = mDataRepository.getUserId();
        String token = mDataRepository.getUserToken();
        
        if (TextUtils.isEmpty(userid) || TextUtils.isEmpty(token)) {
            callBack.onFailure(new UserLoginException(
                      mContext.getString(R.string.message_warning_need_login)));
            return;
        }
        
        this.mDisposableSingleObserver = new DisposableSingleObserver<Object>() {
            @Override
            public void onSuccess(@io.reactivex.annotations.NonNull Object object) {
                
                callBack.onSuccess(object);
            }
            
            @Override
            public void onError(@io.reactivex.annotations.NonNull Throwable e) {
                callBack.onFailure(e);
            }
        };
        
        if (!this.mCompositeDisposable.isDisposed()) {
            
            mDisposable = mDataRepository.deleteBudget(userid, token, params[0])
                      .subscribeOn(Schedulers.io())
                      .observeOn(AndroidSchedulers.mainThread())
                      .doOnSubscribe(new Consumer<Disposable>() {
                          @Override
                          public void accept(Disposable disposable) throws Exception {
                              callBack.onLoading();
                          }
                      })
                      .cacheWithInitialCapacity(10)
                      .singleOrError()
                      .subscribeWith(this.mDisposableSingleObserver);
            this.mCompositeDisposable.add(mDisposable);
            
        }
    }
    
    public static class BudgetRequest implements UseCase.RequestValue {
        
        private String action;
        private BaseCallBack<Object> callBack;
        private Budget mBudget;
        private String[] params;
        
        public BudgetRequest(@NonNull String action, @Nullable BaseCallBack<Object> callBack,
                  @Nullable Budget budget, @Nullable String[] params) {
            this.action = action;
            this.callBack = callBack;
            this.mBudget = budget;
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
        
        public Budget getData() {
            return mBudget;
        }
        
        public void setData(Budget object) {
            this.mBudget = object;
        }
        
        public String[] getParam() {
            return params;
        }
    }
}
