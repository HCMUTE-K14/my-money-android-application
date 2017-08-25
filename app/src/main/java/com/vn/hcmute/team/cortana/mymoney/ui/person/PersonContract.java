package com.vn.hcmute.team.cortana.mymoney.ui.person;

import com.vn.hcmute.team.cortana.mymoney.model.Person;
import com.vn.hcmute.team.cortana.mymoney.ui.base.view.BaseView;
import java.util.List;

/**
 * Created by kunsubin on 8/23/2017.
 */

public interface PersonContract {
    
    interface View extends BaseView {
        void initializeView();
        
        void showListPerson(List<Person> list);
        
        void showEmpty();
        
        void onSuccessAddPerson(String message);
        
        void onSuccessRemovePerson(String message);
        
        void onFailure(String message);
        
        void loading(boolean isLoading);
    }
    
    interface Presenter {
        
        void getPerson();
        
        void addPerson(Person person);
        
        void removePerson(String personid);
        
        void unSubscribe();
    }
}
