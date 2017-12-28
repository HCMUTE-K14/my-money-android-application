package com.vn.hcmute.team.cortana.mymoney.ui.event;

import com.vn.hcmute.team.cortana.mymoney.model.Event;
import com.vn.hcmute.team.cortana.mymoney.ui.base.BasePresenter;
import com.vn.hcmute.team.cortana.mymoney.ui.base.listener.BaseCallBack;
import com.vn.hcmute.team.cortana.mymoney.usecase.base.Action;
import com.vn.hcmute.team.cortana.mymoney.usecase.base.TypeRepository;
import com.vn.hcmute.team.cortana.mymoney.usecase.remote.EventUseCase;
import com.vn.hcmute.team.cortana.mymoney.usecase.remote.EventUseCase.EventRequest;
import java.util.List;
import javax.inject.Inject;

/**
 * Created by kunsubin on 8/22/2017.
 */

public class EventPresenter extends BasePresenter<EventContract.View> implements
                                                                      EventContract.Presenter {
    
    private EventUseCase mEventUseCase;
    
    @Inject
    public EventPresenter(EventUseCase mEventUseCase) {
        this.mEventUseCase = mEventUseCase;
    }
    
    @Override
    public void unSubscribe() {
        mEventUseCase.unSubscribe();
    }
    
    @Override
    public void getEvent() {
        
        BaseCallBack<Object> callBack = new BaseCallBack<Object>() {
            
            @Override
            public void onSuccess(Object value) {
                getView().loading(false);
                getView().onSuccessGetListEvent((List<Event>) value);
                
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
        EventRequest eventRequest = new EventRequest(Action.ACTION_GET_EVENT, callBack, null, null);
        eventRequest.setTypeRepository(TypeRepository.LOCAL);
        mEventUseCase.subscribe(eventRequest);
    }
    
    @Override
    public void createEvent(Event event) {
        BaseCallBack<Object> mObjectBaseCallBack = new BaseCallBack<Object>() {
            
            @Override
            public void onSuccess(Object value) {
                getView().loading(false);
                getView().onSuccessCreateEvent((String) value);
                
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
        
        EventRequest eventRequest = new EventRequest(Action.ACTION_CREATE_EVENT,
                  mObjectBaseCallBack, event, null);
        eventRequest.setTypeRepository(TypeRepository.LOCAL);
        mEventUseCase.subscribe(eventRequest);
    }
    
    @Override
    public void updateEvent(Event event) {
        BaseCallBack<Object> mObjectBaseCallBack = new BaseCallBack<Object>() {
            
            @Override
            public void onSuccess(Object value) {
                getView().loading(false);
                getView().onSuccessUpdateEvent((String) value);
                
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
        EventRequest eventRequest = new EventRequest(Action.ACTION_UPDATE_EVENT,
                  mObjectBaseCallBack, event, null);
        eventRequest.setTypeRepository(TypeRepository.LOCAL);
        mEventUseCase.subscribe(eventRequest);
    }
    
    @Override
    public void updateStatusEvent(List<Event> eventList) {
        BaseCallBack<Object> mObjectBaseCallBack = new BaseCallBack<Object>() {
        
            @Override
            public void onSuccess(Object value) {
                getView().loading(false);
                getView().onSuccessUpdateEvent((String) value);
            
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
        EventRequest eventRequest = new EventRequest(Action.ACTION_UPDATE_STATUS_EVENT,
                  mObjectBaseCallBack, null, null);
        eventRequest.setTypeRepository(TypeRepository.LOCAL);
        eventRequest.setEventList(eventList);
        mEventUseCase.subscribe(eventRequest);
    }
    
    @Override
    public void deleteEvent(String idEvent) {
        BaseCallBack<Object> mObjectBaseCallBack = new BaseCallBack<Object>() {
            
            @Override
            public void onSuccess(Object value) {
                getView().loading(false);
                getView().onSuccessDeleteEvent((String) value);
                
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
        String[] params = {idEvent};
        EventRequest eventRequest = new EventRequest(Action.ACTION_DELETE_EVENT,
                  mObjectBaseCallBack, null, params);
        eventRequest.setTypeRepository(TypeRepository.LOCAL);
        mEventUseCase.subscribe(eventRequest);
    }
}
