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
        this.mContext = context;
        this.mCompositeDisposable = new CompositeDisposable();
    }
    
    @Override
    public void subscribe(UserRequest requestValues) {
        String action = requestValues.getAction();
        switch (action) {
            case Action.ACTION_LOGIN_NORMAL:
                doLoginNormal(requestValues.getCallBack(),
                          ((LoginRequest) requestValues).getData());
                break;
            case Action.ACTION_REGISTER:
                doRegister(requestValues.getCallBack(),
                          ((RegisterRequest) requestValues).getData());
                break;
            case Action.ACTION_LOGOUT:
                doLogout(requestValues.getCallBack());
                break;
            case Action.ACTION_CHANGE_PASSWORD:
                doChangePassword(requestValues.getCallBack(),
                          ((ChangePasswordRequest) requestValues).getOldPassword(),
                          ((ChangePasswordRequest) requestValues).getNewPassword());
                break;
            case Action.ACTION_FORGET_PASSWORD:
                doForgotPassword(requestValues.getCallBack(),
                          ((ForgetPasswordRequest) requestValues).getEmail());
                break;
            case Action.ACTION_CHANGE_PROFILE:
                doChangeProfile(requestValues.getCallBack(),
                          ((ChangeProfileRequest) requestValues).getUser());
                break;
            default:
                break;
        }
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
                      .cacheWithInitialCapacity(10)
                      .singleOrError()
                      .subscribeWith(this.mDisposableSingleObserver);
            this.mCompositeDisposable.add(mDisposable);
        }
    }
    
    private void doForgotPassword(final BaseCallBack<Object> callBack, final String email) {
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
                      .cacheWithInitialCapacity(10)
                      .singleOrError()
                      .subscribeWith(this.mDisposableSingleObserver);
            this.mCompositeDisposable.add(mDisposable);
        }
    }
    
    private void doChangePassword(final BaseCallBack<Object> callBack, final String oldPassword,
              final String newPassword) {
        
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
                      .cacheWithInitialCapacity(10)
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
    
    private void doLoginNormal(final BaseCallBack<Object> callBack,
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
            
            mDisposable = mDataRepository.login(userCredential)
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
        
        public UserRequest(@NonNull String action, @NonNull BaseCallBack<Object> callBack) {
            this.action = action;
            this.callBack = callBack;
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
    }
    
    
    public static class RegisterRequest extends UserRequest {
        
        private User data;
        
        public RegisterRequest(@NonNull String action, @NonNull BaseCallBack<Object> callBack,
                  @NonNull User data) {
            super(action, callBack);
            this.data = data;
        }
        
        public User getData() {
            return data;
        }
        
        public void setData(User data) {
            this.data = data;
        }
    }
    
    public static class LoginRequest extends UserRequest {
        
        private UserCredential userCredential;
        
        
        public LoginRequest(@NonNull String action, @NonNull BaseCallBack<Object> callBack,
                  @NonNull UserCredential userCredential) {
            super(action, callBack);
            this.userCredential = userCredential;
        }
        
        public UserCredential getData() {
            return userCredential;
        }
        
        public void setData(UserCredential object) {
            this.userCredential = object;
        }
    }
    
    public static class ChangePasswordRequest extends UserRequest {
        
        private String newPassword;
        private String oldPassword;
        
        public ChangePasswordRequest(@NonNull String action, @NonNull BaseCallBack<Object> callBack,
                  String oldPassword,
                  String newPassword) {
            super(action, callBack);
            this.newPassword = newPassword;
            this.oldPassword = oldPassword;
        }
        
        public String getOldPassword() {
            return oldPassword;
        }
        
        public void setOldPassword(String oldPassword) {
            this.oldPassword = oldPassword;
        }
        
        public String getNewPassword() {
            return newPassword;
        }
        
        public void setNewPassword(String newPassword) {
            this.newPassword = newPassword;
        }
    }
    
    public static class ForgetPasswordRequest extends UserRequest {
        
        private String email;
        
        public ForgetPasswordRequest(@NonNull String action,
                  @NonNull BaseCallBack<Object> callBack, String email) {
            super(action, callBack);
            this.email = email;
        }
        
        public String getEmail() {
            return email;
        }
        
        public void setEmail(String email) {
            this.email = email;
        }
    }
    
    public static class ChangeProfileRequest extends UserRequest {
        
        private User user;
        
        public ChangeProfileRequest(@NonNull String action,
                  @NonNull BaseCallBack<Object> callBack, User user) {
            super(action, callBack);
            this.user = user;
        }
        
        public User getUser() {
            return user;
        }
        
        public void setUser(User user) {
            this.user = user;
        }
    }
}
