package com.vn.hcmute.team.cortana.mymoney.usecase.remote;

import android.content.Context;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import com.vn.hcmute.team.cortana.mymoney.R;
import com.vn.hcmute.team.cortana.mymoney.data.DataRepository;
import com.vn.hcmute.team.cortana.mymoney.exception.UserLoginException;
import com.vn.hcmute.team.cortana.mymoney.exception.UserValidateException;
import com.vn.hcmute.team.cortana.mymoney.model.User;
import com.vn.hcmute.team.cortana.mymoney.model.UserCredential;
import com.vn.hcmute.team.cortana.mymoney.ui.base.listener.BaseCallBack;
import com.vn.hcmute.team.cortana.mymoney.usecase.base.Action;
import com.vn.hcmute.team.cortana.mymoney.usecase.base.UseCase;
import com.vn.hcmute.team.cortana.mymoney.usecase.remote.UserManager.UserRequest;
import com.vn.hcmute.team.cortana.mymoney.utils.SecurityUtil;
import com.vn.hcmute.team.cortana.mymoney.utils.validate.UserValidate;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;
import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by infamouSs on 8/11/17.
 */
@Singleton
public class UserManager extends UseCase<UserRequest> {
    
    public static final String TAG = UserManager.class.getSimpleName();
    
    private DataRepository mDataRepository;
    private Context mContext;
    
    private Disposable mDisposable;
    private CompositeDisposable mCompositeDisposable;
    private DisposableSingleObserver<Object> mDisposableSingleObserver;
    
    @Inject
    public UserManager(Context context, DataRepository dataRepository) {
        this.mDataRepository = dataRepository;
        this.mContext = context.getApplicationContext();
        this.mCompositeDisposable = new CompositeDisposable();
    }
    
    @Override
    public void subscribe(UserRequest requestValues) {
        String action = requestValues.getAction();
        switch (action) {
            case Action.ACTION_LOGIN:
                doLogin(requestValues.getCallBack(),
                          requestValues.getUserCredential());
                break;
            case Action.ACTION_LOGIN_WITH_FACEBOOK:
                doLoginWithFacebook(requestValues.getCallBack(), requestValues.getUser());
                break;
            case Action.ACTION_REGISTER:
                doRegister(requestValues.getCallBack(),
                          requestValues.getUser());
                break;
            case Action.ACTION_CHECK_EXIST_FACEBOOK_ACCOUNT:
                doCheckExistFacebookAccount(requestValues.getCallBack(), requestValues.getUser());
                break;
            case Action.ACTION_LOGOUT:
                doLogout(requestValues.getCallBack());
                break;
            case Action.ACTION_CHANGE_PASSWORD:
                doChangePassword(requestValues.getCallBack(), requestValues.getParams());
                break;
            case Action.ACTION_FORGET_PASSWORD:
                doForgotPassword(requestValues.getCallBack(), requestValues.getParams());
                break;
            case Action.ACTION_CHANGE_PROFILE:
                doChangeProfile(requestValues.getCallBack(), requestValues.getUser());
                break;
            default:
                break;
        }
    }
    
    private void doCheckExistFacebookAccount(final BaseCallBack<Object> callBack, final User user) {
        this.mDisposableSingleObserver = new DisposableSingleObserver<Object>() {
            @Override
            public void onSuccess(@io.reactivex.annotations.NonNull Object obj) {
                callBack.onSuccess((String) obj);
            }
            
            @Override
            public void onError(@io.reactivex.annotations.NonNull Throwable e) {
                callBack.onFailure(e);
            }
        };
        if (!this.mCompositeDisposable.isDisposed()) {
            mDisposable = mDataRepository.isExistFacebookAccount(user)
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
    
    public boolean isLogin() {
        return mDataRepository.getUser() != null;
    }
    
    private void doChangeProfile(final BaseCallBack<Object> callBack, final User user) {
        if (!UserValidate.getInstance().validateUser(user)) {
            callBack.onFailure(new UserValidateException(
                      mContext.getString(R.string.message_validate_user_login)));
            return;
        }
        
        String userid = mDataRepository.getUserId();
        String token = mDataRepository.getUserToken();
        
        if (TextUtils.isEmpty(userid) || TextUtils.isEmpty(token)) {
            callBack.onFailure(new UserLoginException(
                      mContext.getString(R.string.message_warning_need_login)));
            return;
        }
        
        this.mDisposableSingleObserver = new DisposableSingleObserver<Object>() {
            @Override
            public void onSuccess(@io.reactivex.annotations.NonNull Object obj) {
                callBack.onSuccess((String) obj);
            }
            
            @Override
            public void onError(@io.reactivex.annotations.NonNull Throwable e) {
                callBack.onFailure(e);
            }
        };
        if (!this.mCompositeDisposable.isDisposed()) {
            mDisposable = mDataRepository.changeProfile(userid, token, user)
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
    
    private void doForgotPassword(final BaseCallBack<Object> callBack, final String[] params) {
        String email = params[0];
        if (TextUtils.isEmpty(email)) {
            callBack.onFailure(new UserValidateException(
                      mContext.getString(R.string.message_email_cannot_be_empty)));
            return;
        }
        this.mDisposableSingleObserver = new DisposableSingleObserver<Object>() {
            @Override
            public void onSuccess(@io.reactivex.annotations.NonNull Object obj) {
                callBack.onSuccess((String) obj);
            }
            
            @Override
            public void onError(@io.reactivex.annotations.NonNull Throwable e) {
                callBack.onFailure(e);
            }
        };
        if (!this.mCompositeDisposable.isDisposed()) {
            mDisposable = mDataRepository.forgetPassword(email)
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
    
    private void doChangePassword(final BaseCallBack<Object> callBack, final String[] params) {
        String oldPassword = params[0];
        String newPassword = params[1];
        
        if (TextUtils.isEmpty(oldPassword) || TextUtils.isEmpty(newPassword)) {
            callBack.onFailure(new UserValidateException(
                      mContext.getString(R.string.message_password_cannot_be_empty)));
            return;
        }
        
        String userid = mDataRepository.getUserId();
        String token = mDataRepository.getUserToken();
        
        if (TextUtils.isEmpty(userid) || TextUtils.isEmpty(token)) {
            callBack.onFailure(new UserLoginException(
                      mContext.getString(R.string.message_warning_need_login)));
            return;
        }
        
        this.mDisposableSingleObserver = new DisposableSingleObserver<Object>() {
            @Override
            public void onSuccess(@io.reactivex.annotations.NonNull Object obj) {
                callBack.onSuccess((String) obj);
            }
            
            @Override
            public void onError(@io.reactivex.annotations.NonNull Throwable e) {
                callBack.onFailure(e);
            }
        };
        if (!this.mCompositeDisposable.isDisposed()) {
            mDisposable = mDataRepository.changePassword(userid, token, oldPassword, newPassword)
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
    
    private void doLogout(final BaseCallBack<Object> callBack) {
        callBack.onLoading();
        new Runnable() {
            @Override
            public void run() {
                try {
                    mDataRepository.removeLoginStage();
                    // callBack.onSuccess(mContext.getString(R.string.message_log_out_successful));
                    callBack.onSuccess(mDataRepository.getUserId());
                } catch (Exception e) {
                    callBack.onFailure(e);
                }
            }
        }.run();
    }
    
    private void doLogin(final BaseCallBack<Object> callBack,
              final UserCredential userCredential) {
        if (!UserValidate.getInstance().validateUser(userCredential)) {
            callBack.onFailure(new UserValidateException(
                      mContext.getString(R.string.message_validate_user_login)));
            return;
        }
        this.mDisposableSingleObserver = new DisposableSingleObserver<Object>() {
            @Override
            public void onSuccess(@io.reactivex.annotations.NonNull Object user) {
                mDataRepository.putLoginState((User) user);
                callBack.onSuccess((User) user);
            }
            
            @Override
            public void onError(@io.reactivex.annotations.NonNull Throwable e) {
                callBack.onFailure(e);
            }
        };
        if (!this.mCompositeDisposable.isDisposed()) {
            String encryptPassword = SecurityUtil.encrypt(userCredential.getPassword());
            userCredential.setPassword(encryptPassword);
            mDisposable = mDataRepository.login(userCredential)
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
    
    private void doLoginWithFacebook(final BaseCallBack<Object> callBack,
              final User userCredential) {
        this.mDisposableSingleObserver = new DisposableSingleObserver<Object>() {
            @Override
            public void onSuccess(@io.reactivex.annotations.NonNull Object user) {
                mDataRepository.putLoginState((User) user);
                callBack.onSuccess((User) user);
            }
            
            @Override
            public void onError(@io.reactivex.annotations.NonNull Throwable e) {
                callBack.onFailure(e);
            }
        };
        if (!this.mCompositeDisposable.isDisposed()) {
            mDisposable = mDataRepository.login(userCredential)
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
    
    private void doRegister(final BaseCallBack<Object> callBack, final User data) {
        if (!UserValidate.getInstance().validateUser(data)) {
            callBack.onFailure(new UserValidateException(
                      mContext.getString(R.string.message_validate_user_register)));
            return;
        }
        this.mDisposableSingleObserver = new DisposableSingleObserver<Object>() {
            @Override
            public void onSuccess(@io.reactivex.annotations.NonNull Object o) {
                callBack.onSuccess((String) o);
            }
            
            @Override
            public void onError(@io.reactivex.annotations.NonNull Throwable e) {
                callBack.onFailure(e);
            }
        };
        if (!this.mCompositeDisposable.isDisposed()) {
            
            mDisposable = mDataRepository.register(data)
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
    
    @Override
    public void unSubscribe() {
        if (mCompositeDisposable != null && !mCompositeDisposable.isDisposed() &&
            mDisposable != null) {
            mCompositeDisposable.remove(mDisposable);
        }
    }
    
    public static class UserRequest implements UseCase.RequestValue {
        
        private String action;
        private BaseCallBack<Object> callBack;
        private User user;
        private UserCredential userCredential;
        private String[] params;
        
        public UserRequest(@NonNull String action, @NonNull BaseCallBack<Object> callBack) {
            this.action = action;
            this.callBack = callBack;
        }
        
        public UserRequest(@NonNull String action, @NonNull BaseCallBack<Object> callBack,
                  @NonNull User data) {
            this(action, callBack);
            this.user = data;
        }
        
        
        public UserRequest(@NonNull String action, @NonNull BaseCallBack<Object> callBack,
                  @NonNull UserCredential userCredential) {
            this(action, callBack);
            this.userCredential = userCredential;
        }
        
        public UserRequest(@NonNull String action, @NonNull BaseCallBack<Object> callBack,
                  String[] params) {
            this(action, callBack);
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
        
        public void setCallBack(
                  BaseCallBack<Object> callBack) {
            this.callBack = callBack;
        }
        
        public User getUser() {
            return user;
        }
        
        public void setUser(User user) {
            this.user = user;
        }
        
        public UserCredential getUserCredential() {
            return userCredential;
        }
        
        public void setUserCredential(UserCredential userCredential) {
            this.userCredential = userCredential;
        }
        
        public String[] getParams() {
            return params;
        }
        
        public void setParams(String[] params) {
            this.params = params;
        }
    }
}
