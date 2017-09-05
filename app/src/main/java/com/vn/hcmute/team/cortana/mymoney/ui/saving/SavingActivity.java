package com.vn.hcmute.team.cortana.mymoney.ui.saving;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
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
 * Created by infamouSs on 8/28/2017.
 */

public class SavingActivity extends BaseActivity implements SavingContract.View {
    
    @Inject
    SavingPresenter mSavingPresenter;
 
    @Override
    public int getLayoutId() {
        return R.layout.activity_saving;
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
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSavingPresenter.getSaving();
    
    }
    
    @Override
    protected void initializePresenter() {
        this.mPresenter = mSavingPresenter;
        mSavingPresenter.setView(this);
    }
    
    @Override
    protected void initializeActionBar(View rootView) {
        
    }
    
    @Override
    public void showListSaving(List<Saving> savings) {
      
    }
    
    @Override
    public void showSaving() {
        
    }
    
    @Override
    public void onSuccessCreateSaving() {
        
    }
    
    @Override
    public void onSuccessDeleteSaving() {
        
    }
    
    @Override
    public void onSuccessUpdateSaving() {
        
    }
    
    @Override
    public void onSuccessTakeIn() {
        
    }
    
    @Override
    public void onSuccessTakeOut() {
        
    }
    
    @Override
    public void showError(String message) {
        
    }
    
    @Override
    public void loading(boolean isLoading) {
        
    }
}
