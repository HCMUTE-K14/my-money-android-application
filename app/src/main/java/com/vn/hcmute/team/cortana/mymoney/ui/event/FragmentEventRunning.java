package com.vn.hcmute.team.cortana.mymoney.ui.event;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import butterknife.BindView;
import com.vn.hcmute.team.cortana.mymoney.MyMoneyApplication;
import com.vn.hcmute.team.cortana.mymoney.R;
import com.vn.hcmute.team.cortana.mymoney.di.component.ApplicationComponent;
import com.vn.hcmute.team.cortana.mymoney.di.component.DaggerEventComponent;
import com.vn.hcmute.team.cortana.mymoney.di.component.EventComponent;
import com.vn.hcmute.team.cortana.mymoney.di.module.ActivityModule;
import com.vn.hcmute.team.cortana.mymoney.di.module.EventModule;
import com.vn.hcmute.team.cortana.mymoney.model.Event;
import com.vn.hcmute.team.cortana.mymoney.ui.base.BaseFragment;
import com.vn.hcmute.team.cortana.mymoney.ui.base.EmptyAdapter;
import com.vn.hcmute.team.cortana.mymoney.ui.event.adapter.MyRecyclerViewEventAdapter;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;

/**
 * Created by kunsubin on 9/13/2017.
 */

public class FragmentEventRunning extends BaseFragment implements EventContract.View,
                                                                  MyRecyclerViewEventAdapter.ItemClickListener {
    
    public MyRecyclerViewEventAdapter mMyRecyclerViewEventAdapter;
    
    @BindView(R.id.progress_bar_event)
    ProgressBar mProgressBar;
    
    @BindView(R.id.recycler_view_event_running)
    RecyclerView mRecyclerView;
    @Inject
    EventPresenter mEventPresenter;
    private EmptyAdapter mEmptyAdapter;
    private List<Event> mEventList;
    
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_event_running;
    }
    
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
       
        
    }
    
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        
    }
    
    @Override
    public void onDestroy() {
        mEventPresenter.unSubscribe();
        super.onDestroy();
    }
    
    @Override
    protected void initializeDagger() {
        ApplicationComponent applicationComponent = ((MyMoneyApplication) getActivity()
                  .getApplication())
                  .getAppComponent();
        EventComponent eventComponent = DaggerEventComponent
                  .builder()
                  .applicationComponent(applicationComponent)
                  .activityModule(new ActivityModule(getActivity()))
                  .eventModule(new EventModule())
                  .build();
        eventComponent.inject(this);
    }
    
    @Override
    protected void initializePresenter() {
        mPresenter = mEventPresenter;
        mEventPresenter.setView(this);
    }

    public void initView() {
        mRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 1));
    }

    @Override
    protected void initializeActionBar(View rootView) {
        
    }
    
    @Override
    protected void initialize() {
        initView();
        mEventList = new ArrayList<>();
        mEventPresenter.getEvent();
    }
    
    @Override
    public void onSuccessCreateEvent(String message) {
        
    }
    
    @Override
    public void onSuccessUpdateEvent(String message) {
        
    }
    
    @Override
    public void onSuccessDeleteEvent(String message) {
        
    }
    
    @Override
    public void onSuccessGetListEvent(List<Event> events) {
        // MyLogger.d("event",events.size());

        if (!events.isEmpty()) {
            for (Event event : events) {
                if (event.getStatus().equals("0")) {
                    mEventList.add(event);
                }
            }
            if (!mEventList.isEmpty()) {
                mMyRecyclerViewEventAdapter = new MyRecyclerViewEventAdapter(getActivity(),
                          mEventList);
                mMyRecyclerViewEventAdapter.setClickListener(this);
                mRecyclerView.setAdapter(mMyRecyclerViewEventAdapter);
            } else {
                mEmptyAdapter = new EmptyAdapter(getActivity(), getString(R.string.no_event));
                mRecyclerView.setAdapter(mEmptyAdapter);
            }

        } else {
            mEmptyAdapter = new EmptyAdapter(getActivity(), getString(R.string.no_event));
            mRecyclerView.setAdapter(mEmptyAdapter);
        }

    }
    
    @Override
    public void onFailure(String message) {
        
    }
    
    @Override
    public void loading(boolean isLoading) {
        mProgressBar.setVisibility(isLoading?View.VISIBLE:View.GONE);
    }

    @Override
    public void onItemClick(Event event) {
        Intent intent = new Intent(getActivity(), ActivityInfoEvent.class);
        intent.putExtra("event", event);
        getActivity().startActivityForResult(intent, 15);
    }
    

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        
        if (requestCode == 15) {
            if (resultCode == Activity.RESULT_OK) {
                String eventId = data.getStringExtra("result");
                
                if (!mEventList.isEmpty()) {
                    for (Event event : mEventList) {
                        if (event.getEventid().equals(eventId)) {
                            mEventList.remove(event);
                            mMyRecyclerViewEventAdapter.setList(mEventList);
                            break;
                        }
                    }
                    if (mEventList.isEmpty()) {
                        mEmptyAdapter = new EmptyAdapter(getContext(),
                                  getString(R.string.no_event));
                        mRecyclerView.setAdapter(mEmptyAdapter);
                    }
                }
                
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                mEventList.clear();
                mMyRecyclerViewEventAdapter.notifyDataSetChanged();
                mEventPresenter.getEvent();
            }
        }
        if (requestCode == 22) {
            if (resultCode == Activity.RESULT_OK) {
                Event event = data.getParcelableExtra("event");
                if(mEventList.isEmpty()){
                    
                    mEventList.add(event);
                    mMyRecyclerViewEventAdapter = new MyRecyclerViewEventAdapter(getActivity(),
                              mEventList);
                    mMyRecyclerViewEventAdapter.setClickListener(this);
                    mRecyclerView.setAdapter(mMyRecyclerViewEventAdapter);
                    
                }else {
                    mEventList.add(event);
                    mMyRecyclerViewEventAdapter.setList(mEventList);
                }
                
            }
        }
    }
    
}
