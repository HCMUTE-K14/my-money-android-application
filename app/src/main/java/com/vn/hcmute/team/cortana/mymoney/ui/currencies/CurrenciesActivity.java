package com.vn.hcmute.team.cortana.mymoney.ui.currencies;

import android.view.View;
import android.widget.Button;
import butterknife.BindView;
import butterknife.OnClick;
import com.vn.hcmute.team.cortana.mymoney.MyMoneyApplication;
import com.vn.hcmute.team.cortana.mymoney.R;
import com.vn.hcmute.team.cortana.mymoney.di.component.ApplicationComponent;
import com.vn.hcmute.team.cortana.mymoney.di.component.CurrenciesComponent;
import com.vn.hcmute.team.cortana.mymoney.di.component.DaggerCurrenciesComponent;
import com.vn.hcmute.team.cortana.mymoney.di.module.ActivityModule;
import com.vn.hcmute.team.cortana.mymoney.di.module.CurrenciesModule;
import com.vn.hcmute.team.cortana.mymoney.model.Currencies;
import com.vn.hcmute.team.cortana.mymoney.ui.base.BaseActivity;
import java.util.List;
import javax.inject.Inject;

/**
 * Created by kunsubin on 8/22/2017.
 */

public class CurrenciesActivity extends BaseActivity implements CurrenciesContract.View{
    
    @Inject
    CurrenciesPresenter mCurrenciesPresenter;
 
    
    @BindView(R.id.button2)
    Button mButton;
    
    @OnClick(R.id.button2)
    public void onClick() {
        mCurrenciesPresenter.getCurrencies();
    }
    
    
    @Override
    public int getLayoutId() {
        return R.layout.activity_wallet;
    }
    
    @Override
    protected void initializeDagger() {
        ApplicationComponent applicationComponent = ((MyMoneyApplication) this.getApplication())
                  .getAppComponent();
        CurrenciesComponent currenciesComponent = DaggerCurrenciesComponent
                  .builder()
                  .applicationComponent(applicationComponent)
                  .activityModule(new ActivityModule(this))
                  .currenciesModule(new CurrenciesModule())
                  .build();
        currenciesComponent.inject(this);
    }
    
    @Override
    protected void initializePresenter() {
        this.mPresenter = mCurrenciesPresenter;
        mCurrenciesPresenter.setView(this);
    }
    
    @Override
    protected void initializeActionBar(View rootView) {
        
    }
    
    
    @Override
    public void showCurrencies(List<Currencies> list) {
        
    }
    
    @Override
    public void showEmpty() {
        
    }
    
    @Override
    public void showError(String message) {
        
    }
    
    @Override
    public void loading(boolean isLoading) {
        
    }
}
