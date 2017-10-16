package com.vn.hcmute.team.cortana.mymoney.usecase.remote;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import com.vn.hcmute.team.cortana.mymoney.R;
import com.vn.hcmute.team.cortana.mymoney.data.DataRepository;
import com.vn.hcmute.team.cortana.mymoney.exception.PersonException;
import com.vn.hcmute.team.cortana.mymoney.exception.UserLoginException;
import com.vn.hcmute.team.cortana.mymoney.model.Person;
import com.vn.hcmute.team.cortana.mymoney.ui.base.listener.BaseCallBack;
import com.vn.hcmute.team.cortana.mymoney.usecase.base.Action;
import com.vn.hcmute.team.cortana.mymoney.usecase.base.TypeRepository;
import com.vn.hcmute.team.cortana.mymoney.usecase.base.UseCase;
import com.vn.hcmute.team.cortana.mymoney.usecase.remote.PersonUseCase.PersonRequest;
import com.vn.hcmute.team.cortana.mymoney.utils.SecurityUtil;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;
import java.util.List;
import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by kunsubin on 8/23/2017.
 */

@Singleton
public class PersonUseCase extends UseCase<PersonRequest> {
    
    public static final String TAG = PersonUseCase.class.getSimpleName();
    
    private DataRepository mDataRepository;
    private Context mContext;
    
    private Disposable mDisposable;
    private CompositeDisposable mCompositeDisposable;
    private DisposableSingleObserver<Object> mDisposableSingleObserver;
    
    @Inject
    public PersonUseCase(Context context, DataRepository dataRepository) {
        this.mContext = context.getApplicationContext();
        this.mDataRepository = dataRepository;
        this.mCompositeDisposable = new CompositeDisposable();
    }
    
    
    @Override
    public void subscribe(PersonRequest requestValues) {
        String action = requestValues.getAction();
        switch (action) {
            case Action.ACTION_GET_PERSON:
                doGetPerson(requestValues.getCallBack(),requestValues.getTypeRepository());
                break;
            case Action.ACTION_ADD_PERSON:
                doAddPerson(requestValues.getCallBack(),
                          ((CRUDPersonRequest) requestValues).getPerson(),requestValues.getTypeRepository());
                break;
            case Action.ACTION_REMOVE_PERSON:
                doRemovePerson(requestValues.getCallBack(),
                          ((CRUDPersonRequest) requestValues).getPerson().getPersonid(),requestValues.getTypeRepository());
                break;
            case Action.ACTION_UPDATE_PERSON:
                doUpdatePerson(requestValues.getCallBack(),
                          ((CRUDPersonRequest) requestValues).getPerson(),requestValues.getTypeRepository());
                break;
            case Action.ACTION_SYNC_PERSON:
                doSyncPerson(requestValues.getCallBack(),
                          ((SyncPersonRequest) requestValues).getPersons());
            default:
                break;
        }
    }
    
    private void doSyncPerson(final BaseCallBack<Object> callBack, List<Person> persons) {
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
                callBack.onSuccess(obj);
            }
            
            @Override
            public void onError(@io.reactivex.annotations.NonNull Throwable e) {
                callBack.onFailure(e);
            }
        };
        
        if (!this.mCompositeDisposable.isDisposed()) {
            mDisposable = mDataRepository.syncPerson(persons, userid, token)
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
    
    private void doUpdatePerson(final BaseCallBack<Object> callBack, Person person,TypeRepository typeRepository) {
       
        
        this.mDisposableSingleObserver = new DisposableSingleObserver<Object>() {
            @Override
            public void onSuccess(@io.reactivex.annotations.NonNull Object obj) {
                
                callBack.onSuccess(obj);
            }
            
            @Override
            public void onError(@io.reactivex.annotations.NonNull Throwable e) {
                callBack.onFailure(e);
            }
        };
        
        if (!this.mCompositeDisposable.isDisposed()) {
            if(typeRepository==TypeRepository.REMOTE){
                String userid = mDataRepository.getUserId();
                String token = mDataRepository.getUserToken();
    
                if (TextUtils.isEmpty(userid) || TextUtils.isEmpty(token)) {
                    callBack.onFailure(new UserLoginException(
                              mContext.getString(R.string.message_warning_need_login)));
                    return;
                }
                mDisposable = mDataRepository.updatePerson(person, userid, token)
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
            }else {
                mDisposable = mDataRepository.updateLocalPerson(person)
                          .subscribeOn(Schedulers.computation())
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
    
    @Override
    public void unSubscribe() {
        if (mCompositeDisposable != null && !mCompositeDisposable.isDisposed() &&
            mDisposable != null) {
            mCompositeDisposable.remove(mDisposable);
        }
    }
    
    private void doGetPerson(final BaseCallBack<Object> callBack,TypeRepository typeRepository) {
       
        
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
            if(typeRepository==TypeRepository.REMOTE){
                String userid = mDataRepository.getUserId();
                String token = mDataRepository.getUserToken();
    
                if (TextUtils.isEmpty(userid) || TextUtils.isEmpty(token)) {
                    callBack.onFailure(new UserLoginException(
                              mContext.getString(R.string.message_warning_need_login)));
                    return;
                }
                mDisposable = mDataRepository.getPerson(userid, token)
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
            }else {
                String userid = mDataRepository.getUserId();
                mDisposable = mDataRepository.getLocalListPerson(userid)
                          .subscribeOn(Schedulers.computation())
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
    
    private void doAddPerson(final BaseCallBack<Object> callBack, Person person,TypeRepository typeRepository) {
    
        if (TextUtils.isEmpty(person.getName())) {
            callBack.onFailure(
                      new PersonException(mContext.getString(R.string.message_name_person_empty)));
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
            if(typeRepository==TypeRepository.REMOTE){
                String userid = mDataRepository.getUserId();
                String token = mDataRepository.getUserToken();
    
                if (TextUtils.isEmpty(userid) || TextUtils.isEmpty(token)) {
                    callBack.onFailure(new UserLoginException(
                              mContext.getString(R.string.message_warning_need_login)));
                    return;
                }
                
                person.setPersonid(SecurityUtil.getRandomUUID());
                person.setUserid(userid);
    
                mDisposable = mDataRepository.addPerson(person, userid, token)
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
            }else {
                String userid = mDataRepository.getUserId();
                person.setUserid(userid);
                mDisposable = mDataRepository.addLocalPerson(person)
                          .subscribeOn(Schedulers.computation())
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
    
    private void doRemovePerson(final BaseCallBack<Object> callBack, String personid,TypeRepository typeRepository) {
       
        
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
            if(typeRepository==TypeRepository.REMOTE){
                
                String userid = mDataRepository.getUserId();
                String token = mDataRepository.getUserToken();
    
                if (TextUtils.isEmpty(userid) || TextUtils.isEmpty(token)) {
                    callBack.onFailure(new UserLoginException(
                              mContext.getString(R.string.message_warning_need_login)));
                    return;
                }
                mDisposable = mDataRepository.removePerson(userid, token, personid)
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
            }else {
                mDisposable = mDataRepository.deleteLocalPeron(personid)
                          .subscribeOn(Schedulers.computation())
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
    
    
    public static class PersonRequest implements UseCase.RequestValue {
        
        private String action;
        private BaseCallBack<Object> callBack;
        private TypeRepository typeRepository;
        
        public PersonRequest(@NonNull String action, @Nullable BaseCallBack<Object> callBack) {
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
        
        public void setCallBack(BaseCallBack<Object> callBack) {
            this.callBack = callBack;
        }
        public TypeRepository getTypeRepository() {
            return typeRepository;
        }
        public void setTypeRepository(
                  TypeRepository typeRepository) {
            this.typeRepository = typeRepository;
        }
    }
    
    public static class CRUDPersonRequest extends PersonRequest {
        
        private Person mPerson;
        
        
        public CRUDPersonRequest(@NonNull String action,
                  @Nullable BaseCallBack<Object> callBack, @NonNull Person person) {
            super(action, callBack);
            this.mPerson = person;
        }
        
        public Person getPerson() {
            return mPerson;
        }
        
        public void setPerson(Person person) {
            mPerson = person;
        }
    }
    
    public static class SyncPersonRequest extends PersonRequest {
        
        private List<Person> mPersons;
        
        public SyncPersonRequest(@NonNull String action,
                  @Nullable BaseCallBack<Object> callBack, List<Person> persons) {
            super(action, callBack);
            this.mPersons = persons;
        }
        
        public List<Person> getPersons() {
            return mPersons;
        }
        
        public void setPersons(List<Person> persons) {
            mPersons = persons;
        }
    }
}
