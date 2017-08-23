package com.vn.hcmute.team.cortana.mymoney.ui.person;

import com.vn.hcmute.team.cortana.mymoney.model.Person;
import com.vn.hcmute.team.cortana.mymoney.ui.base.view.BaseView;

/**
 * Created by kunsubin on 8/23/2017.
 */

public interface PersonContract {
    interface View extends BaseView {
        
        void onSuccess(Object object);
        
       
        
        void onFailure(String message);
    }
    
    interface Presenter {
        
        void getPerson();
       
        void addPerson(Person person);
        void removePerson(String personid);
    }
}
