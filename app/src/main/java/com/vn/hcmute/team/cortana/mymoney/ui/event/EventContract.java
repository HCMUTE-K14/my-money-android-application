package com.vn.hcmute.team.cortana.mymoney.ui.event;

import com.vn.hcmute.team.cortana.mymoney.model.Event;
import com.vn.hcmute.team.cortana.mymoney.ui.base.view.BaseView;
import java.util.List;

/**
 * Created by kunsubin on 8/22/2017.
 */

public interface EventContract {
    
    interface View extends BaseView {
        
        void onSuccessCreate(String message);
        void onSuccessUpdate(String message);
        void onSuccessDelete(String message);
        void onSuccessGet(List<Event> events);
        
        void onFailure(String message);
    }
    
    interface Presenter {
        
        void getEvent();
        
        void createEvent(Event event);
        
        void updateEvent(Event event);
        
        void deleteEvent(String idEvent);
        
        
    }
}
