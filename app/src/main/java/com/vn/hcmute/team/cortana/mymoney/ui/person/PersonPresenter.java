package com.vn.hcmute.team.cortana.mymoney.ui.person;

import com.vn.hcmute.team.cortana.mymoney.model.Person;
import com.vn.hcmute.team.cortana.mymoney.ui.base.BasePresenter;
import com.vn.hcmute.team.cortana.mymoney.ui.base.listener.BaseCallBack;
import com.vn.hcmute.team.cortana.mymoney.usecase.base.Action;
import com.vn.hcmute.team.cortana.mymoney.usecase.remote.PersonUseCase;
import com.vn.hcmute.team.cortana.mymoney.usecase.remote.PersonUseCase.PersonRequest;
import java.util.List;
import javax.inject.Inject;

/**
 * Created by kunsubin on 8/23/2017.
 */

public class PersonPresenter extends BasePresenter<PersonContract.View> implements
                                                                        PersonContract.Presenter {
    
    PersonUseCase mPersonUseCase;
    
    @Inject
    public PersonPresenter(
              PersonUseCase personUseCase) {
        mPersonUseCase = personUseCase;
    }
    
    
    @Override
    public void getPerson() {
        BaseCallBack<Object> mObjectBaseCallBack = new BaseCallBack<Object>() {
            @Override
            public void onSuccess(Object value) {
                getView().loading(false);
                
                List<Person> list = (List<Person>) value;
                
                if (list.isEmpty()) {
                    getView().showEmpty();
                    return;
                }
                getView().showListPerson(list);
            }
            
            @Override
            public void onFailure(Throwable throwable) {
                getView().loading(false);
                getView().onFailure(throwable.getMessage());
            }
            
            @Override
            public void onLoading() {
                getView().loading(true);
            }
        };
        
        PersonRequest personRequest = new PersonRequest(Action.ACTION_GET_PERSON,
                  mObjectBaseCallBack, null, null);
        mPersonUseCase.subscribe(personRequest);
    }
    
    @Override
    public void addPerson(final Person person) {
        BaseCallBack<Object> mObjectBaseCallBack = new BaseCallBack<Object>() {
            @Override
            public void onSuccess(Object value) {
                getView().loading(false);
                getView().onSuccessAddPerson((String) value,person);
            }
            
            @Override
            public void onFailure(Throwable throwable) {
                getView().loading(false);
                getView().onFailure(throwable.getMessage());
            }
            
            @Override
            public void onLoading() {
                getView().loading(true);
            }
        };
        PersonRequest personRequest = new PersonRequest(Action.ACTION_ADD_PERSON,
                  mObjectBaseCallBack, person, null);
        mPersonUseCase.subscribe(personRequest);
    }
    
    @Override
    public void removePerson(final int position,final Person person) {
        BaseCallBack<Object> mObjectBaseCallBack = new BaseCallBack<Object>() {
            @Override
            public void onSuccess(Object value) {
                getView().loading(false);
                getView().onSuccessRemovePerson((String)value,position,person);
            }
            
            @Override
            public void onFailure(Throwable throwable) {
                getView().loading(false);
                getView().onFailure(throwable.getMessage());
            }
            
            @Override
            public void onLoading() {
                getView().loading(true);
            }
        };
        String[] param = {person.getPersonid()};
        PersonRequest personRequest = new PersonRequest(Action.ACTION_REMOVE_PERSON,
                  mObjectBaseCallBack, null, param);
        mPersonUseCase.subscribe(personRequest);
    }
    
    @Override
    public void unSubscribe() {
        mPersonUseCase.unSubscribe();
    }
}