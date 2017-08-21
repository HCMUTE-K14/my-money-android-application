package com.vn.hcmute.team.cortana.mymoney.usecase.remote;

import android.content.Context;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import com.vn.hcmute.team.cortana.mymoney.data.DataRepository;
import com.vn.hcmute.team.cortana.mymoney.exception.ApiRequestException;
import com.vn.hcmute.team.cortana.mymoney.model.Image;
import com.vn.hcmute.team.cortana.mymoney.ui.base.listener.BaseCallBack;
import com.vn.hcmute.team.cortana.mymoney.usecase.base.Action;
import com.vn.hcmute.team.cortana.mymoney.usecase.base.UseCase;
import com.vn.hcmute.team.cortana.mymoney.usecase.remote.ImageUseCase.ImageRequest;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;

/**
 * Created by infamouSs on 8/21/17.
 */

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
        this.mCompositeDisposable = new CompositeDisposable();
    }
    
    @Override
    public void subscribe(ImageRequest requestValues) {
        String action=requestValues.getAction();
        switch (action){
            case Action.ACTION_GET_IMAGE:
                doGetImage(requestValues.getCallBack());
                break;
            default:
                break;
        }
    }
    
    private void doGetImage(final BaseCallBack<Object> callBack) {
        String userid=mDataRepository.getUserId();
        String token=mDataRepository.getUserToken();
        if(TextUtils.isEmpty(userid) || TextUtils.isEmpty(token)){
            callBack.onFailure(new ApiRequestException("USER ID, TOKEN NULL"));
            return;
        }
        this.mDisposableSingleObserver = new DisposableSingleObserver<Object>() {
            @Override
            public void onSuccess(@io.reactivex.annotations.NonNull Object o) {
            
                List<Image> list=new ArrayList<>();
                if (o instanceof List){
                    for(int i = 0; i < ((List<?>)o).size(); i++){
                        Object item = ((List<?>) o).get(i);
                        if(item instanceof Image){
                            list.add((Image) item);
                        }
                    }
                }
                
                callBack.onSuccess(list);
            }
        
            @Override
            public void onError(@io.reactivex.annotations.NonNull Throwable e) {
                callBack.onFailure(e);
            }
        };
        if (!this.mCompositeDisposable.isDisposed()) {
        
            mDisposable = mDataRepository.getImage(userid,token)
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
        if (!mCompositeDisposable.isDisposed()) {
            mCompositeDisposable.remove(mDisposable);
        }
    }
    
    public static class ImageRequest implements UseCase.RequestValue{
        private String action;
        private BaseCallBack<Object> callBack;
        
        public ImageRequest(@NonNull String action,@NonNull BaseCallBack<Object> callBack){
            this.action=action;
            
            this.callBack=callBack;
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
}
