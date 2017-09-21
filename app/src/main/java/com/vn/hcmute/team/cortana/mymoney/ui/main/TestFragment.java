package com.vn.hcmute.team.cortana.mymoney.ui.main;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.OnClick;
import com.vn.hcmute.team.cortana.mymoney.MyMoneyApplication;
import com.vn.hcmute.team.cortana.mymoney.R;
import com.vn.hcmute.team.cortana.mymoney.data.cache.PreferencesHelper;
import com.vn.hcmute.team.cortana.mymoney.di.component.ApplicationComponent;
import com.vn.hcmute.team.cortana.mymoney.di.component.CurrenciesComponent;
import com.vn.hcmute.team.cortana.mymoney.di.component.DaggerCurrenciesComponent;
import com.vn.hcmute.team.cortana.mymoney.di.module.ActivityModule;
import com.vn.hcmute.team.cortana.mymoney.di.module.CurrenciesModule;
import com.vn.hcmute.team.cortana.mymoney.model.ResultConvert;
import com.vn.hcmute.team.cortana.mymoney.ui.base.BaseFragment;
import com.vn.hcmute.team.cortana.mymoney.ui.base.listener.BaseCallBack;
import com.vn.hcmute.team.cortana.mymoney.usecase.base.Action;
import com.vn.hcmute.team.cortana.mymoney.usecase.remote.CurrenciesUseCase;
import com.vn.hcmute.team.cortana.mymoney.usecase.remote.CurrenciesUseCase.CurrenciesRequest;
import com.vn.hcmute.team.cortana.mymoney.utils.NumberUtil;
import javax.inject.Inject;

/**
 * Created by infamouSs on 9/4/17.
 */

public class TestFragment extends BaseFragment {
    
    @BindView(R.id.edit_text_1)
    EditText mEditTextAmount;
    
    @BindView(R.id.edit_text_2)
    EditText mEditTextFrom;
    
    @BindView(R.id.edit_text_3)
    EditText mEditTextTo;
    
    @BindView(R.id.result)
    TextView result;
    
    @Inject
    CurrenciesUseCase mCurrenciesUseCase;
    
    @Inject
    PreferencesHelper mPreferencesHelper;
    
    public TestFragment() {
        
    }
    
    @OnClick(R.id.button_1)
    public void onClick1() {
        String amount = mEditTextAmount.getText().toString();
        String from = mEditTextFrom.getText().toString();
        String to = mEditTextTo.getText().toString();
        
        String[] params = {amount, from, to};
        CurrenciesRequest currenciesRequest = new CurrenciesRequest(
                  Action.ACTION_CONVERT_CURRENCY_ONLINE,
                  new BaseCallBack<Object>() {
                      @Override
                      public void onSuccess(Object value) {
                          ResultConvert convert = (ResultConvert) value;
                          
                          result.setText(convert.getValue());
                      }
                      
                      @Override
                      public void onFailure(Throwable throwable) {
                          
                      }
                      
                      @Override
                      public void onLoading() {
                          
                      }
                  }, params);
        
        mCurrenciesUseCase.subscribe(currenciesRequest);
    }
    
    @OnClick(R.id.button_2)
    public void onClick2() {
        CurrenciesRequest request = new CurrenciesRequest(Action.ACTION_UPDATE_REAL_TIME_CURRENCY,
                  new BaseCallBack<Object>() {
                      @Override
                      public void onSuccess(Object value) {
                      }
                      
                      @Override
                      public void onFailure(Throwable throwable) {
                          
                      }
                      
                      @Override
                      public void onLoading() {
                          
                      }
                  }, null);
        
        mCurrenciesUseCase.subscribe(request);
    }
    
    @OnClick(R.id.button_3)
    public void onClick3() {
        String amount = mEditTextAmount.getText().toString();
        String from = mEditTextFrom.getText().toString();
        String to = mEditTextTo.getText().toString();
        
        String[] params = {amount, from, to};
        CurrenciesRequest currenciesRequest = new CurrenciesRequest(
                  Action.ACTION_CONVERT_CURRENCY_OFFLINE,
                  new BaseCallBack<Object>() {
                      @Override
                      public void onSuccess(Object value) {
                          String _result = NumberUtil.format((double) value, "#,###.###");
                          result.setText(_result);
                      }
                      
                      
                      @Override
                      public void onFailure(Throwable throwable) {
                          Toast.makeText(TestFragment.this.getContext(), throwable.getMessage(),
                                    Toast.LENGTH_SHORT).show();
                      }
                      
                      @Override
                      public void onLoading() {
                          
                      }
                  }, params);
        
        mCurrenciesUseCase.subscribe(currenciesRequest);
    }
    
    @Override
    protected int getLayoutId() {
        return R.layout.test;
    }
    
    @Override
    protected void initializeDagger() {
        ApplicationComponent applicationComponent = ((MyMoneyApplication) this.getActivity()
                  .getApplication()).getAppComponent();
        CurrenciesComponent currenciesComponent = DaggerCurrenciesComponent.builder()
                  .applicationComponent(applicationComponent)
                  .activityModule(new ActivityModule(this.getActivity()))
                  .currenciesModule(new CurrenciesModule())
                  .build();
        currenciesComponent.inject(this);
    }
    
    @Override
    protected void initializePresenter() {
        
    }
    
    @Override
    protected void initializeActionBar(View rootView) {
        
    }
    
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}
