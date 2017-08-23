package com.vn.hcmute.team.cortana.mymoney.ui.person;

import android.view.View;
import android.widget.Button;
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
import com.vn.hcmute.team.cortana.mymoney.ui.base.BaseActivity;
import javax.inject.Inject;

/**
 * Created by kunsubin on 8/23/2017.
 */

public class PersonActivity extends BaseActivity {
    @Inject
    PersonPersenter mPersonPersenter;
    
    @BindView(R.id.button2)
    Button mButton;
    
    PersonContract.View view=new PersonContract.View() {
        @Override
        public void onSuccess(Object object) {
                /*List<Person> list=(List<Person>) object;
                
                if(list==null||list.isEmpty()){
                    Toast.makeText(getApplicationContext(),"null",Toast.LENGTH_LONG).show();
                }else {
                    Toast.makeText(getApplicationContext(),list.get(0).getName(),Toast.LENGTH_LONG).show();
                   
                }*/
            Toast.makeText(getApplicationContext(),(String)object,Toast.LENGTH_LONG).show();
        }
        
        @Override
        public void onFailure(String message) {
            Toast.makeText(getApplicationContext(),message,Toast.LENGTH_LONG).show();
        }
    };
    
    @OnClick(R.id.button2)
    public void onClick() {
        
        mPersonPersenter.setView(view);
        //mPersonPersenter.getPerson();
        //mPersonPersenter.addPerson(new Person());
       mPersonPersenter.removePerson("hi");
        
    }
    
    
    @Override
    public int getLayoutId() {
        return R.layout.activity_wallet;
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
        this.mPresenter=mPersonPersenter;
        //mPersonPersenter.setView(this);
    }
    
    @Override
    protected void initializeActionBar(View rootView) {
        
    }
    
  /*  @Override
    public void onSuccess(Object object) {
        Toast.makeText(getApplicationContext(),object.toString(),Toast.LENGTH_LONG).show();
    }
    
    @Override
    public void onFailure(String message) {
        Toast.makeText(getApplicationContext(),message,Toast.LENGTH_LONG).show();
    }*/
}
