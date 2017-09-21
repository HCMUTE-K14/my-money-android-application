package com.vn.hcmute.team.cortana.mymoney.usecase.remote;

import android.content.Context;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import com.vn.hcmute.team.cortana.mymoney.R;
import com.vn.hcmute.team.cortana.mymoney.data.DataRepository;
import com.vn.hcmute.team.cortana.mymoney.exception.ImageException;
import com.vn.hcmute.team.cortana.mymoney.exception.UserLoginException;
import com.vn.hcmute.team.cortana.mymoney.ui.base.listener.BaseCallBack;
import com.vn.hcmute.team.cortana.mymoney.usecase.base.Action;
import com.vn.hcmute.team.cortana.mymoney.usecase.base.UseCase;
import com.vn.hcmute.team.cortana.mymoney.usecase.remote.ImageUseCase.ImageRequest;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.Nullable;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;
import java.io.File;
import javax.inject.Inject;
import javax.inject.Singleton;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * Created by infamouSs on 8/21/17.
 */
@Singleton
public class ImageUseCase extends UseCase<ImageRequest> {
    
    public static final String TAG = ImageUseCase.class.getSimpleName();
    
    private DataRepository mDataRepository;
    private Context mContext;
    
    private Disposable mDisposable;
    private CompositeDisposable mCompositeDisposable;
    private DisposableSingleObserver<Object> mDisposableSingleObserver;
    
    @Inject
    public ImageUseCase(Context context, DataRepository dataRepository) {
        this.mDataRepository = dataRepository;
        this.mContext = context.getApplicationContext();
        this.mCompositeDisposable = new CompositeDisposable();
    }
    
    @Override
    public void subscribe(ImageRequest requestValues) {
        String action = requestValues.getAction();
        switch (action) {
            case Action.ACTION_GET_IMAGE:
                doGetImage(requestValues.getCallBack());
                break;
            case Action.ACTION_GET_IMAGE_BY_ID:
                doGetImageById(requestValues.getParams(), requestValues.getCallBack());
                break;
            case Action.ACTION_UPLOAD_IMAGE:
                doUploadImage(requestValues.getParams(), requestValues.getCallBack());
                break;
            case Action.ACTION_REMOVE_IMAGE:
                doRemoveImage(requestValues.getParams(), requestValues.getCallBack());
                break;
            case Action.ACTION_UPDATE_IMAGE:
                doUpdateImage(requestValues.getParams(), requestValues.getCallBack());
                break;
            case Action.ACTION_GET_LIST_ICON:
                doGetListIcon(requestValues.getCallBack());
                break;
            default:
                break;
        }
    }
    
    private void doGetListIcon(final BaseCallBack<Object> callBack) {
        //        if (!mDataRepository.doesDatabaseLocalExist()) {
        //            mDataRepository.createNewLocalDatabase();
        //        }
        //
        this.mDisposableSingleObserver = new DisposableSingleObserver<Object>() {
            @Override
            public void onSuccess(@io.reactivex.annotations.NonNull Object o) {
                
                callBack.onSuccess(o);
            }
            
            @Override
            public void onError(@io.reactivex.annotations.NonNull Throwable e) {
                callBack.onFailure(e);
            }
        };
        
        if (!this.mCompositeDisposable.isDisposed()) {
            mDisposable = mDataRepository.getListIcon()
                      .subscribeOn(Schedulers.computation())
                      .observeOn(AndroidSchedulers.mainThread())
                      .singleOrError()
                      .subscribeWith(this.mDisposableSingleObserver);
            this.mCompositeDisposable.add(mDisposable);
        }
    }
    
    private void doUpdateImage(String[] params, final BaseCallBack<Object> callBack) {
        String userid = mDataRepository.getUserId();
        String token = mDataRepository.getUserToken();
        
        if (TextUtils.isEmpty(userid) || TextUtils.isEmpty(token)) {
            callBack.onFailure(new UserLoginException(
                      mContext.getString(R.string.message_warning_need_login)));
            return;
        }
        
        String imageid = params[0];
        
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
            
            mDisposable = mDataRepository.removeImage(userid, token, imageid)
                      .subscribeOn(Schedulers.io())
                      .observeOn(AndroidSchedulers.mainThread())
                      .singleOrError()
                      .subscribeWith(this.mDisposableSingleObserver);
            this.mCompositeDisposable.add(mDisposable);
        }
    }
    
    private void doRemoveImage(String[] params, final BaseCallBack<Object> callBack) {
        String userid = mDataRepository.getUserId();
        String token = mDataRepository.getUserToken();
        
        if (TextUtils.isEmpty(userid) || TextUtils.isEmpty(token)) {
            callBack.onFailure(new UserLoginException(
                      mContext.getString(R.string.message_warning_need_login)));
            return;
        }
        
        String imageid = params[0];
        
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
            
            mDisposable = mDataRepository.removeImage(userid, token, imageid)
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
    
    private void doGetImageById(String[] params, final BaseCallBack<Object> callBack) {
        String userid = mDataRepository.getUserId();
        String token = mDataRepository.getUserToken();
        
        if (TextUtils.isEmpty(userid) || TextUtils.isEmpty(token)) {
            callBack.onFailure(new UserLoginException(
                      mContext.getString(R.string.message_warning_need_login)));
            return;
        }
        
        String imageid = params[0];
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
            
            mDisposable = mDataRepository.getImageById(userid, token, imageid)
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
    
    private void doUploadImage(String[] params, final BaseCallBack<Object> callBack) {
        String userid = mDataRepository.getUserId();
        String token = mDataRepository.getUserToken();
        
        if (TextUtils.isEmpty(userid) || TextUtils.isEmpty(token)) {
            callBack.onFailure(new UserLoginException(
                      mContext.getString(R.string.message_warning_need_login)));
            return;
        }
        
        String detail = params[0];
        String path_url = params[1];
        
        File image = new File(path_url);
        
        if (image == null) {
            callBack.onFailure(new ImageException("Cannot load image from " + path_url));
            return;
        }
        
        RequestBody requestBodyUserid = RequestBody
                  .create(MediaType.parse("multipart/form-data"), userid);
        RequestBody requestBodyToken = RequestBody
                  .create(MediaType.parse("multipart/form-data"), token);
        RequestBody requestBodyDetail = RequestBody
                  .create(MediaType.parse("multipart/form-data"), detail);
        RequestBody requestBodyFile = RequestBody
                  .create(MediaType.parse("multipart/form-data"), image);
        MultipartBody.Part multipartFile =
                  MultipartBody.Part.createFormData("file", image.getName(), requestBodyFile);
        
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
            
            mDisposable = mDataRepository
                      .uploadImage(requestBodyUserid, requestBodyToken, requestBodyDetail,
                                multipartFile)
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
    
    private void doGetImage(final BaseCallBack<Object> callBack) {
        String userid = mDataRepository.getUserId();
        String token = mDataRepository.getUserToken();
        
        if (TextUtils.isEmpty(userid) || TextUtils.isEmpty(token)) {
            callBack.onFailure(new UserLoginException(
                      mContext.getString(R.string.message_warning_need_login)));
            return;
        }
        
        this.mDisposableSingleObserver = new DisposableSingleObserver<Object>() {
            @Override
            public void onSuccess(@io.reactivex.annotations.NonNull Object o) {
                callBack.onSuccess(o);
                
            }
            
            @Override
            public void onError(@io.reactivex.annotations.NonNull Throwable e) {
                callBack.onFailure(e);
            }
        };
        if (!this.mCompositeDisposable.isDisposed()) {
            
            mDisposable = mDataRepository.getImage(userid, token)
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
    
    public static class ImageRequest implements UseCase.RequestValue {
        
        private String action;
        private BaseCallBack<Object> callBack;
        private String[] params;
        
        
        public ImageRequest(@NonNull String action, @NonNull BaseCallBack<Object> callBack) {
            this.action = action;
            
            this.callBack = callBack;
        }
        
        
        public ImageRequest() {
            
        }
        
        public ImageRequest(@NonNull String action, @NonNull BaseCallBack<Object> callBack,
                  @Nullable String[] params) {
            this.action = action;
            this.callBack = callBack;
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
        
        public String[] getParams() {
            return params;
        }
        
        public void setParams(String[] params) {
            this.params = params;
        }
    }
}
