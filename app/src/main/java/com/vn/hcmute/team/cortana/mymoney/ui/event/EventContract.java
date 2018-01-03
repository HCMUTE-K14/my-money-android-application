package com.vn.hcmute.team.cortana.mymoney.ui.event;

import com.vn.hcmute.team.cortana.mymoney.model.Event;
import com.vn.hcmute.team.cortana.mymoney.ui.base.view.BaseView;
import java.util.List;

/**
 * Created by kunsubin on 8/22/2017.
 */

public interface EventContract {
    
    interface View extends BaseView {
        
        void onSuccessCreateEvent(String message);
        
        void onSuccessUpdateEvent(String message);
        
        void onSuccessDeleteEvent(String message);
        
        void onSuccessGetListEvent(List<Event> events);
        
        void onFailure(String message);
        
        void loading(boolean isLoading);
    }
    
    interface Presenter {
        
        void getEvent();
        
        void createEvent(Event event);
        
        void updateEvent(Event event);
        
        void updateStatusEvent(List<Event> eventList);
        
        void deleteEvent(String idEvent);
        
        void unSubscribe();
        
    }
}
