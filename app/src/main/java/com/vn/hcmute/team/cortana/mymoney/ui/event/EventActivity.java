package com.vn.hcmute.team.cortana.mymoney.ui.event;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.OnClick;
import com.vn.hcmute.team.cortana.mymoney.MyMoneyApplication;
import com.vn.hcmute.team.cortana.mymoney.R;
import com.vn.hcmute.team.cortana.mymoney.di.component.ApplicationComponent;
import com.vn.hcmute.team.cortana.mymoney.di.component.DaggerEventComponent;
import com.vn.hcmute.team.cortana.mymoney.di.component.EventComponent;
import com.vn.hcmute.team.cortana.mymoney.di.module.ActivityModule;
import com.vn.hcmute.team.cortana.mymoney.di.module.EventModule;
import com.vn.hcmute.team.cortana.mymoney.model.Event;
import com.vn.hcmute.team.cortana.mymoney.ui.base.BaseActivity;
import com.vn.hcmute.team.cortana.mymoney.ui.event.adapter.MyRecyclerViewEventAdapter;
import java.util.List;
import javax.inject.Inject;

/**
 * Created by kunsubin on 8/22/2017.
 */

public class EventActivity extends BaseActivity implements EventContract.View, MyRecyclerViewEventAdapter.ItemClickListener {
    
    @Inject
    EventPresenter mEventPresenter;
    
    @BindView(R.id.buttonClickEvent)
    Button mButton;
    
    @BindView(R.id.recyclerViewEvent)
    RecyclerView mRecyclerViewEvent;
    
    MyRecyclerViewEventAdapter mMyRecyclerViewEventAdapter;
    
    List<Event> mEventList;
    
    @OnClick(R.id.buttonClickEvent)
    public void onClick() {
      
       // mEventPresenter.getEvent();
       /* Event event=new Event();
        event.setEventid(UUID.randomUUID().toString());
        event.setUserid("e67757e090bb47bbbebf7db8b15e7c96");
        mEventPresenter.createEvent(event);*/
        Event event=new Event();
        event.setEventid("a92d28ad72cd42aab0df732cb6344438");
        event.setName("chieu lang thang ghe quan net");
        event.setMoney("19039493");
        event.setUserid("e67757e090bb47bbbebf7db8b15e7c96");
        mEventPresenter.updateEvent(event);
    }
    
    
    @Override
    public int getLayoutId() {
        return R.layout.activity_event;
    }
    
    @Override
    protected void initializeDagger() {
        ApplicationComponent applicationComponent = ((MyMoneyApplication) this.getApplication())
                  .getAppComponent();
        EventComponent eventComponent = DaggerEventComponent
                  .builder()
                  .applicationComponent(applicationComponent)
                  .activityModule(new ActivityModule(this))
                  .eventModule(new EventModule())
                  .build();
        eventComponent.inject(this);
    }
    
    @Override
    protected void initializePresenter() {
        this.mPresenter = mEventPresenter;
        mEventPresenter.setView(this);
    }
    
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mEventPresenter.getEvent();
    }
    
    @Override
    protected void initializeActionBar(View rootView) {
        
    }
    
    
    @Override
    public void onSuccessCreateEvent(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
        mEventPresenter.getEvent();
    }
    
    @Override
    public void onSuccessUpdateEvent(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
        mEventPresenter.getEvent();
    }
    
    @Override
    public void onSuccessDeleteEvent(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
        mEventPresenter.getEvent();
    }
    
    @Override
    public void onSuccessGetListEvent(List<Event> events) {
        mEventList=events;
        
        mMyRecyclerViewEventAdapter = new MyRecyclerViewEventAdapter(this, events);
        mRecyclerViewEvent.setLayoutManager(new GridLayoutManager(this, 1));
        mMyRecyclerViewEventAdapter.setClickListener(this);
        mRecyclerViewEvent.setAdapter(mMyRecyclerViewEventAdapter);
    }
    
    @Override
    public void onFailure(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }
    
    @Override
    public void onItemClick(View view, int position) {
        Toast.makeText(this,mEventList.get(position).toString(),Toast.LENGTH_LONG).show();
       // TextView textView= ButterKnife.findById(view,R.id.eventid);
       // mEventPresenter.deleteEvent(textView.getText().toString().trim());
    }
}
