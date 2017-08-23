package com.vn.hcmute.team.cortana.mymoney.ui.person;

import com.vn.hcmute.team.cortana.mymoney.model.Person;
import com.vn.hcmute.team.cortana.mymoney.ui.base.BasePresenter;
import com.vn.hcmute.team.cortana.mymoney.ui.base.listener.BaseCallBack;
import com.vn.hcmute.team.cortana.mymoney.usecase.base.Action;
import com.vn.hcmute.team.cortana.mymoney.usecase.remote.PersonUseCase;
import com.vn.hcmute.team.cortana.mymoney.usecase.remote.PersonUseCase.PersonRequest;
import javax.inject.Inject;

/**
 * Created by kunsubin on 8/23/2017.
 */

public class PersonPersenter extends BasePresenter<PersonContract.View> implements
                                                                        PersonContract.Presenter {
    
    PersonUseCase mPersonUseCase;
    
    @Inject
    public PersonPersenter(
              PersonUseCase personUseCase) {
        mPersonUseCase = personUseCase;
    }
    BaseCallBack<Object> mObjectBaseCallBack=new BaseCallBack<Object>() {
        @Override
        public void onSuccess(Object value) {
             getView().onSuccess(value);
        }
    
        @Override
        public void onFailure(Throwable throwable) {
            getView().onFailure(throwable.getMessage());
        }
    
        @Override
        public void onLoading() {
        
        }
    };
    
    
    @Override
    public void getPerson() {
        PersonRequest personRequest=new PersonRequest(Action.ACTION_GET_PERSON,mObjectBaseCallBack,null,null);
        mPersonUseCase.subscribe(personRequest);
    }
    
    @Override
    public void addPerson(Person person) {
        PersonRequest personRequest=new PersonRequest(Action.ACTION_ADD_PERSON,mObjectBaseCallBack,person,null);
        mPersonUseCase.subscribe(personRequest);
    }
    
    @Override
    public void removePerson(String personid) {
        String [] param={personid};
        PersonRequest personRequest=new PersonRequest(Action.ACTION_REMOVE_PERSON,mObjectBaseCallBack,null,param);
        mPersonUseCase.subscribe(personRequest);
    }
}
