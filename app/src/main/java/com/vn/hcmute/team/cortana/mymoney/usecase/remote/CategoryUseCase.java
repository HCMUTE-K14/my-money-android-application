package com.vn.hcmute.team.cortana.mymoney.usecase.remote;

import android.content.Context;
import android.text.TextUtils;
import com.vn.hcmute.team.cortana.mymoney.R;
import com.vn.hcmute.team.cortana.mymoney.data.DataRepository;
import com.vn.hcmute.team.cortana.mymoney.exception.UserLoginException;
import com.vn.hcmute.team.cortana.mymoney.model.Category;
import com.vn.hcmute.team.cortana.mymoney.ui.base.listener.BaseCallBack;
import com.vn.hcmute.team.cortana.mymoney.usecase.base.Action;
import com.vn.hcmute.team.cortana.mymoney.usecase.base.TypeRepository;
import com.vn.hcmute.team.cortana.mymoney.usecase.base.UseCase;
import com.vn.hcmute.team.cortana.mymoney.usecase.remote.CategoryUseCase.CategoryRequest;
import com.vn.hcmute.team.cortana.mymoney.utils.SecurityUtil;
import com.vn.hcmute.team.cortana.mymoney.utils.logger.MyLogger;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;
import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by infamouSs on 9/15/17.
 */

@Singleton
public class CategoryUseCase extends UseCase<CategoryRequest> {
    
    public static final String TAG = CategoryUseCase.class.getSimpleName();
    
    private DataRepository mDataRepository;
    private Context mContext;
    
    private Disposable mDisposable;
    private CompositeDisposable mCompositeDisposable;
    private DisposableSingleObserver<Object> mDisposableSingleObserver;
    
    @Inject
    public CategoryUseCase(Context context, DataRepository dataRepository) {
        this.mContext = context.getApplicationContext();
        this.mDataRepository = dataRepository;
        this.mCompositeDisposable = new CompositeDisposable();
    }
    
    @Override
    public void subscribe(CategoryRequest requestValues) {
        String action = requestValues.getAction();
        switch (action) {
            case Action.ACTION_GET_CATEGORY:
            case Action.ACTION_GET_INCOMING_CATEGORY:
            case Action.ACTION_GET_EXPENSE_CATEGORY:
            case Action.ACTION_GET_DEBT_LOAN_CATEGORY:
                doGetCategory(requestValues.getAction(), requestValues.getCallback(),
                          requestValues.getTypeRepository());
                break;
            case Action.ACTION_GET_CATEGORY_BY_TYPE:
                doGetCategoryByType(requestValues.getCallback(), requestValues.getParams(),
                          requestValues.getTypeRepository());
                break;
            case Action.ACTION_ADD_CATEGORY:
                doAddCategory(requestValues.getCallback(), requestValues.getParams(),
                          requestValues.getCategory(),
                          requestValues.getTypeRepository());
                break;
            case Action.ACTION_UPDATE_CATEGORY:
                doUpdateCategory(requestValues.getCallback(), requestValues.getParams(),
                          requestValues.getCategory(),
                          requestValues.getTypeRepository());
                break;
            case Action.ACTION_DELETE_CATEGORY:
                doDeleteCategory(requestValues.getCallback(), requestValues.getParams(),
                          requestValues.getCategory(),
                          requestValues.getTypeRepository());
                break;
            case Action.ACTION_SYNC_CATEGORY:
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
    
    private void doGetCategory(final String action, final BaseCallBack<Object> callBack,
              TypeRepository typeRepository) {
        this.mDisposableSingleObserver = new DisposableSingleObserver<Object>() {
            @Override
            public void onSuccess(@io.reactivex.annotations.NonNull Object currencies) {
                callBack.onSuccess(currencies);
            }
            
            @Override
            public void onError(@io.reactivex.annotations.NonNull Throwable e) {
                callBack.onFailure(e);
            }
        };
        
        String type = "";
        switch (action) {
            case Action.ACTION_GET_CATEGORY:
                type = "all";
                break;
            case Action.ACTION_GET_INCOMING_CATEGORY:
                type = "income";
                break;
            case Action.ACTION_GET_EXPENSE_CATEGORY:
                type = "expense";
                break;
            case Action.ACTION_GET_DEBT_LOAN_CATEGORY:
                type = "debt_loan";
                break;
            default:
                callBack.onFailure(new RuntimeException(
                          mContext.getString(R.string.message_warning_wrong_type_category)));
                return;
        }
        
        if (!this.mCompositeDisposable.isDisposed()) {
            if (typeRepository == TypeRepository.LOCAL) {
                //                mDisposable = mDataRepository.get()
                //                          .subscribeOn(Schedulers.computation())
                //                          .observeOn(AndroidSchedulers.mainThread())
                //                          .doOnSubscribe(new Consumer<Disposable>() {
                //                              @Override
                //                              public void accept(Disposable disposable) throws Exception {
                //                                  MyLogger.d("Currencies Usecacse", "loading");
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
                
                mDisposable = mDataRepository.getListCategory(userid, token, type)
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
    
    private void doGetCategoryByType(final BaseCallBack<Object> callBack, String[] params,
              TypeRepository typeRepository) {
        this.mDisposableSingleObserver = new DisposableSingleObserver<Object>() {
            @Override
            public void onSuccess(@io.reactivex.annotations.NonNull Object currencies) {
                callBack.onSuccess(currencies);
            }
            
            @Override
            public void onError(@io.reactivex.annotations.NonNull Throwable e) {
                callBack.onFailure(e);
            }
        };
        
        if (!this.mCompositeDisposable.isDisposed()) {
            if (typeRepository == TypeRepository.LOCAL) {
                //                mDisposable = mDataRepository.get()
                //                          .subscribeOn(Schedulers.computation())
                //                          .observeOn(AndroidSchedulers.mainThread())
                //                          .doOnSubscribe(new Consumer<Disposable>() {
                //                              @Override
                //                              public void accept(Disposable disposable) throws Exception {
                //                                  MyLogger.d("Currencies Usecacse", "loading");
                //                                  callBack.onLoading();
                //                              }
                //                          })
                //                          .singleOrError()
                //                          .subscribeWith(this.mDisposableSingleObserver);
            } else if (typeRepository == TypeRepository.REMOTE) {
                String userid = mDataRepository.getUserId();
                String token = mDataRepository.getUserToken();
                String type = params[0];
                String transType = params[1];
                
                if (TextUtils.isEmpty(userid) || TextUtils.isEmpty(token)) {
                    callBack.onFailure(new UserLoginException(
                              mContext.getString(R.string.message_warning_need_login)));
                    return;
                }
                
                if (!type.equals("income") && !type.equals("expense")) {
                    callBack.onFailure(new Throwable(
                              mContext.getString(R.string.message_warning_wrong_type_category)));
                    return;
                }
                
                if (!transType.equals("income") && !transType.equals("expense") &&
                    !transType.equals("debt_loan")) {
                    callBack.onFailure(new Throwable(
                              mContext.getString(R.string.message_warning_wrong_type_category)));
                    return;
                }
                
                mDisposable = mDataRepository.getListCategoryByType(userid, token, type, transType)
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
    
    private void doDeleteCategory(final BaseCallBack<Object> callBack, String[] params,
              Category category,
              TypeRepository typeRepository) {
        this.mDisposableSingleObserver = new DisposableSingleObserver<Object>() {
            @Override
            public void onSuccess(@io.reactivex.annotations.NonNull Object currencies) {
                callBack.onSuccess(currencies);
            }
            
            @Override
            public void onError(@io.reactivex.annotations.NonNull Throwable e) {
                callBack.onFailure(e);
            }
        };
        
        if (!this.mCompositeDisposable.isDisposed()) {
            if (typeRepository == TypeRepository.LOCAL) {
                //                mDisposable = mDataRepository.get()
                //                          .subscribeOn(Schedulers.computation())
                //                          .observeOn(AndroidSchedulers.mainThread())
                //                          .doOnSubscribe(new Consumer<Disposable>() {
                //                              @Override
                //                              public void accept(Disposable disposable) throws Exception {
                //                                  MyLogger.d("Currencies Usecacse", "loading");
                //                                  callBack.onLoading();
                //                              }
                //                          })
                //                          .singleOrError()
                //                          .subscribeWith(this.mDisposableSingleObserver);
            } else if (typeRepository == TypeRepository.REMOTE) {
                String userid = mDataRepository.getUserId();
                String token = mDataRepository.getUserToken();
                String parentId = params[0];
                if (TextUtils.isEmpty(userid) || TextUtils.isEmpty(token)) {
                    callBack.onFailure(new UserLoginException(
                              mContext.getString(R.string.message_warning_need_login)));
                    return;
                }
                
                mDisposable = mDataRepository.deleteCategory(userid, token, parentId, category)
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
    
    private void doUpdateCategory(final BaseCallBack<Object> callBack, String[] params,
              Category category,
              TypeRepository typeRepository) {
        this.mDisposableSingleObserver = new DisposableSingleObserver<Object>() {
            @Override
            public void onSuccess(@io.reactivex.annotations.NonNull Object currencies) {
                callBack.onSuccess(currencies);
            }
            
            @Override
            public void onError(@io.reactivex.annotations.NonNull Throwable e) {
                callBack.onFailure(e);
            }
        };
        
        if (!this.mCompositeDisposable.isDisposed()) {
            if (typeRepository == TypeRepository.LOCAL) {
                //                mDisposable = mDataRepository.get()
                //                          .subscribeOn(Schedulers.computation())
                //                          .observeOn(AndroidSchedulers.mainThread())
                //                          .doOnSubscribe(new Consumer<Disposable>() {
                //                              @Override
                //                              public void accept(Disposable disposable) throws Exception {
                //                                  MyLogger.d("Currencies Usecacse", "loading");
                //                                  callBack.onLoading();
                //                              }
                //                          })
                //                          .singleOrError()
                //                          .subscribeWith(this.mDisposableSingleObserver);
            } else if (typeRepository == TypeRepository.REMOTE) {
                String userid = mDataRepository.getUserId();
                String token = mDataRepository.getUserToken();
                
                String oldParentId = params[0];
                String newParentId = params[1];
                
                if (TextUtils.isEmpty(userid) || TextUtils.isEmpty(token)) {
                    callBack.onFailure(new UserLoginException(
                              mContext.getString(R.string.message_warning_need_login)));
                    return;
                }
                MyLogger.d(oldParentId + newParentId);
                mDisposable = mDataRepository
                          .updateCategory(userid, token, oldParentId, newParentId, category)
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
    
    private void doAddCategory(final BaseCallBack<Object> callBack, String[] params,
              Category category,
              TypeRepository typeRepository) {
        this.mDisposableSingleObserver = new DisposableSingleObserver<Object>() {
            @Override
            public void onSuccess(@io.reactivex.annotations.NonNull Object currencies) {
                callBack.onSuccess(currencies);
            }
            
            @Override
            public void onError(@io.reactivex.annotations.NonNull Throwable e) {
                callBack.onFailure(e);
            }
        };
        
        if (!this.mCompositeDisposable.isDisposed()) {
            if (typeRepository == TypeRepository.LOCAL) {
                //                mDisposable = mDataRepository.get()
                //                          .subscribeOn(Schedulers.computation())
                //                          .observeOn(AndroidSchedulers.mainThread())
                //                          .doOnSubscribe(new Consumer<Disposable>() {
                //                              @Override
                //                              public void accept(Disposable disposable) throws Exception {
                //                                  MyLogger.d("Currencies Usecacse", "loading");
                //                                  callBack.onLoading();
                //                              }
                //                          })
                //                          .singleOrError()
                //                          .subscribeWith(this.mDisposableSingleObserver);
            } else if (typeRepository == TypeRepository.REMOTE) {
                String userid = mDataRepository.getUserId();
                String token = mDataRepository.getUserToken();
                String parentId = params[0];
                
                category.setId(SecurityUtil.getRandomUUID());
                category.setUserid(userid);
                
                if (TextUtils.isEmpty(userid) || TextUtils.isEmpty(token)) {
                    callBack.onFailure(new UserLoginException(
                              mContext.getString(R.string.message_warning_need_login)));
                    return;
                }
                
                mDisposable = mDataRepository.createCategory(userid, token, parentId, category)
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
    
    
    public static class CategoryRequest implements UseCase.RequestValue {
        
        private String action;
        private BaseCallBack<Object> callback;
        private String[] params;
        private Category category;
        private TypeRepository typeRepository;
        
        public CategoryRequest(@NonNull String action, @NonNull BaseCallBack<Object> callBack,
                  Category category, String[] params) {
            this(action, callBack, category, params, TypeRepository.LOCAL);
        }
        
        public CategoryRequest(@NonNull String action, @NonNull BaseCallBack<Object> callback,
                  Category category, String[] params, TypeRepository type
        ) {
            this.action = action;
            this.callback = callback;
            this.category = category;
            this.params = params;
            this.typeRepository = type;
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
        
        public void setCallback(BaseCallBack<Object> callback) {
            this.callback = callback;
        }
        
        public Category getCategory() {
            return category;
        }
        
        public void setCategory(Category category) {
            this.category = category;
        }
        
        public TypeRepository getTypeRepository() {
            return typeRepository;
        }
        
        public void setTypeRepository(
                  TypeRepository typeRepository) {
            this.typeRepository = typeRepository;
        }
        
        public String[] getParams() {
            return params;
        }
        
        public void setParams(String[] params) {
            this.params = params;
        }
    }
}
