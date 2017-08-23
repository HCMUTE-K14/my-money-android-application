package com.vn.hcmute.team.cortana.mymoney.usecase.remote;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.vn.hcmute.team.cortana.mymoney.data.DataRepository;
import com.vn.hcmute.team.cortana.mymoney.model.Person;
import com.vn.hcmute.team.cortana.mymoney.ui.base.listener.BaseCallBack;
import com.vn.hcmute.team.cortana.mymoney.usecase.base.Action;
import com.vn.hcmute.team.cortana.mymoney.usecase.base.UseCase;
import com.vn.hcmute.team.cortana.mymoney.usecase.remote.PersonUseCase.PersonRequest;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;
import javax.inject.Inject;

/**
 * Created by kunsubin on 8/23/2017.
 */

public class PersonUseCase extends UseCase<PersonRequest> {
    
    
    private DataRepository mDataRepository;
    private Context mContext;
    
    private Disposable mDisposable;
    private CompositeDisposable mCompositeDisposable;
    private DisposableSingleObserver<Object> mDisposableSingleObserver;
    
    @Inject
    public PersonUseCase(Context context, DataRepository dataRepository) {
        this.mDataRepository = dataRepository;
        this.mCompositeDisposable = new CompositeDisposable();
    }
    
    
    
    @Override
    public void subscribe(PersonRequest requestValues) {
        String action=requestValues.getAction();
        switch (action){
            case Action.ACTION_GET_PERSON:
                doGetPerson(requestValues.getCallBack());
                break;
            case Action.ACTION_ADD_PERSON:
                doAddPerson(requestValues.getCallBack(),requestValues.getData());
                break;
            case Action.ACTION_REMOVE_PERSON:
                doRemovePerson(requestValues.getCallBack(),requestValues.getParam());
                break;
            default:
                break;
        }
    }
    
    @Override
    public void unSubscribe() {
        if (!mCompositeDisposable.isDisposed()) {
            mCompositeDisposable.remove(mDisposable);
        }
    }
    
    private void doGetPerson(final BaseCallBack<Object> callBack){
        String userid = mDataRepository.getUserId();
        String token =mDataRepository.getUserToken();
    
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
        
            mDisposable = mDataRepository.getPerson(userid,token)
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
    
    private void doAddPerson(final BaseCallBack<Object> callBack,Person person){
        String userid = mDataRepository.getUserId();
        String token =mDataRepository.getUserToken();
    
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
        
            mDisposable = mDataRepository.addPerson(person,userid,token)
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
    //bi loi chua sua
    private void doRemovePerson(final BaseCallBack<Object> callBack,String[] params){
        String userid = "e67757e090bb47bbbebf7db8b15e7c96";//mDataRepository.getUserId();
        String token ="557b32ce486d4a02b961d2befd310541" ;//mDataRepository.getUserToken();
    
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
        
            mDisposable = mDataRepository.removePerson(userid,token,params[0])
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
    
    public static class PersonRequest implements UseCase.RequestValue {
        
        private String action;
        private BaseCallBack<Object> callBack;
        private Person mPerson;
        private String[] params;
        
        public PersonRequest(@NonNull String action, @Nullable BaseCallBack<Object> callBack,
                  @Nullable Person person, @Nullable String[] params) {
            this.action = action;
            this.callBack = callBack;
            this.mPerson = person;
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
        
        public Person getData() {
            return mPerson;
        }
        
        public String[] getParam() {
            return params;
        }
        
        public void setData(Person object) {
            this.mPerson = object;
        }
    }
}
