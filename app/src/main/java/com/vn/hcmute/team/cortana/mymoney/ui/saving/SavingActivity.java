package com.vn.hcmute.team.cortana.mymoney.ui.saving;

import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.OnClick;
import com.vn.hcmute.team.cortana.mymoney.MyMoneyApplication;
import com.vn.hcmute.team.cortana.mymoney.R;
import com.vn.hcmute.team.cortana.mymoney.di.component.ApplicationComponent;
import com.vn.hcmute.team.cortana.mymoney.di.component.DaggerSavingComponent;
import com.vn.hcmute.team.cortana.mymoney.di.component.SavingComponent;
import com.vn.hcmute.team.cortana.mymoney.di.module.ActivityModule;
import com.vn.hcmute.team.cortana.mymoney.di.module.SavingModule;
import com.vn.hcmute.team.cortana.mymoney.model.Saving;
import com.vn.hcmute.team.cortana.mymoney.ui.base.BaseActivity;
import java.util.List;
import javax.inject.Inject;

/**
 * Created by kunsubin on 8/23/2017.
 */

public class SavingActivity extends BaseActivity {
    @Inject
    SavingPresenter mSavingPresenter;
    
    @BindView(R.id.button2)
    Button mButton;
    
    @OnClick(R.id.button2)
    public void onClick() {
       // mSavingPresenter.getSaving();
        //mSavingPresenter.createSaving(new Saving());
       /* Saving saving=new Saving();
        saving.setSavingid("anhky");
        saving.setCurrentMoney("1234456");
        saving.setUserid("e67757e090bb47bbbebf7db8b15e7c96");
        mSavingPresenter.updateSaving(saving);*/
     // mSavingPresenter.deleteSaving("bom");
       // mSavingPresenter.takeOut("vi1","anhky","40000");
        SavingContract.View view=new SavingContract.View() {
            @Override
            public void onSuccess(String message) {
                Toast.makeText(getApplicationContext(),message,Toast.LENGTH_LONG).show();
            }
    
            @Override
            public void onSuccessGetSaving(List<Saving> list) {
        
            }
    
            @Override
            public void onFailure(String message) {
                Toast.makeText(getApplicationContext(),message,Toast.LENGTH_LONG).show();
            }
        };
        
        mSavingPresenter.setView(view);
        mSavingPresenter.createSaving(new Saving());
       
        
        
    }
    
    
    @Override
    public int getLayoutId() {
        return R.layout.activity_wallet;
    }
    
    @Override
    protected void initializeDagger() {
        ApplicationComponent applicationComponent = ((MyMoneyApplication) this.getApplication())
                  .getAppComponent();
        SavingComponent savingComponent = DaggerSavingComponent
                  .builder()
                  .applicationComponent(applicationComponent)
                  .activityModule(new ActivityModule(this))
                  .savingModule(new SavingModule())
                  .build();
        savingComponent.inject(this);
    }
    
    @Override
    protected void initializePresenter() {
        this.mPresenter=mSavingPresenter;
       // mSavingPresenter.setView(this);
    }
    
    @Override
    protected void initializeActionBar(View rootView) {
        
    }
    
    
    
    /*@Override
    public void onSuccess(String message) {
        Toast.makeText(this,message,Toast.LENGTH_LONG).show();
    }
    
    @Override
    public void onSuccessGetSaving(List<Saving> list) {
        if(list==null||list.isEmpty()){
            Toast.makeText(this,"fail",Toast.LENGTH_LONG).show();
        }else {
            Toast.makeText(this,list.get(0).getName(),Toast.LENGTH_LONG).show();
        }
       
    }
    
    @Override
    public void onFailure(String message) {
        Toast.makeText(this,message,Toast.LENGTH_LONG).show();
    }*/
}
