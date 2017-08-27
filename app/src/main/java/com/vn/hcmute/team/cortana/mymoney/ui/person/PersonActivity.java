package com.vn.hcmute.team.cortana.mymoney.ui.person;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.OnClick;
import com.vn.hcmute.team.cortana.mymoney.MyMoneyApplication;
import com.vn.hcmute.team.cortana.mymoney.R;
import com.vn.hcmute.team.cortana.mymoney.di.component.ApplicationComponent;
import com.vn.hcmute.team.cortana.mymoney.di.component.DaggerPersonComponent;
import com.vn.hcmute.team.cortana.mymoney.di.component.PersonComponent;
import com.vn.hcmute.team.cortana.mymoney.di.module.ActivityModule;
import com.vn.hcmute.team.cortana.mymoney.di.module.PersonModule;
import com.vn.hcmute.team.cortana.mymoney.model.Person;
import com.vn.hcmute.team.cortana.mymoney.ui.base.BaseActivity;
import com.vn.hcmute.team.cortana.mymoney.ui.person.adapter.MyRecyclerViewPersonAdapter;
import com.vn.hcmute.team.cortana.mymoney.utils.logger.MyLogger;
import java.util.List;
import javax.inject.Inject;

/**
 * Created by kunsubin on 8/23/2017.
 */

public class PersonActivity extends BaseActivity implements PersonContract.View,MyRecyclerViewPersonAdapter.ItemClickListener {
    
    @Inject
    PersonPersenter mPersonPersenter;
    
    @BindView(R.id.recyclerViewPerson)
    RecyclerView mRecyclerViewPerson;
    
    MyRecyclerViewPersonAdapter mMyRecyclerViewPersonAdapter;
    
    List<Person> mPersons;
    
    @OnClick(R.id.buttonClickPerson)
    public void onClick() {
        //mPersonPersenter.removePerson("hi");
     /*   Person person=new Person();
        person.setPersonid("ab1eab09ae1d4983b8021b182e7e5292");
        person.setUserid("e67757e090bb47bbbebf7db8b15e7c96");
        //mPersonPersenter.addPerson(person);
        person.setName("Chieu lang thang ghe quan net");*/
        
        
    }
 
    
    @Override
    public int getLayoutId() {
        return R.layout.activity_person;
    }
    
    @Override
    protected void initializeDagger() {
        ApplicationComponent applicationComponent = ((MyMoneyApplication) this.getApplication())
                  .getAppComponent();
        PersonComponent personComponent = DaggerPersonComponent
                  .builder()
                  .applicationComponent(applicationComponent)
                  .activityModule(new ActivityModule(this))
                  .personModule(new PersonModule())
                  .build();
        personComponent.inject(this);
    }
    
    @Override
    protected void initializePresenter() {
        this.mPresenter = mPersonPersenter;
        mPersonPersenter.setView(this);
    }
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        mPersonPersenter.getPerson();
    }
    
    @Override
    protected void initializeActionBar(View rootView) {
        
    }
    
    public Person get(int index) {
        return mPersons.get(index);
    }
    
    public boolean isEmpty() {
        return mPersons.isEmpty();
    }
    
    @Override
    public void onSuccessGetListPerson(List<Person> list) {
        MyLogger.d("jsfdskfd",list.size()+"");
        mPersons=list;
        mMyRecyclerViewPersonAdapter = new MyRecyclerViewPersonAdapter(this, list);
        mRecyclerViewPerson.setLayoutManager(new GridLayoutManager(this, 1));
        mMyRecyclerViewPersonAdapter.setClickListener(this);
        mRecyclerViewPerson.setAdapter(mMyRecyclerViewPersonAdapter);
    
        MyLogger.d("jsfdskfdiiiiiiiiii",list.get(1).getName()+"");
    }
    
    @Override
    public void onSuccessAddPerson(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
        mPersonPersenter.getPerson();
    }
    
    @Override
    public void onSuccessRemovePerson(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
        mPersonPersenter.getPerson();
    }
    
    @Override
    public void onFailure(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }
    
    @Override
    public void loading(boolean isLoading) {
        
    }
    
    @Override
    public void onItemClick(View view, int position) {
       /* TextView textView= ButterKnife.findById(view,R.id.personid);
        mPersonPersenter.removePerson(textView.getText().toString().trim());*/
        Toast.makeText(this, get(position).getName(), Toast.LENGTH_LONG).show();
    }
}
