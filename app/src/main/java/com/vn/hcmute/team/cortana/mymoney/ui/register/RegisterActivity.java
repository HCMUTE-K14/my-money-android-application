package com.vn.hcmute.team.cortana.mymoney.ui.register;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.OnClick;
import com.vn.hcmute.team.cortana.mymoney.MyMoneyApplication;
import com.vn.hcmute.team.cortana.mymoney.R;
import com.vn.hcmute.team.cortana.mymoney.di.component.ApplicationComponent;
import com.vn.hcmute.team.cortana.mymoney.di.component.DaggerRegisterComponent;
import com.vn.hcmute.team.cortana.mymoney.di.component.RegisterComponent;
import com.vn.hcmute.team.cortana.mymoney.di.module.ActivityModule;
import com.vn.hcmute.team.cortana.mymoney.di.module.RegisterModule;
import com.vn.hcmute.team.cortana.mymoney.model.User;
import com.vn.hcmute.team.cortana.mymoney.ui.base.BaseActivity;
import com.vn.hcmute.team.cortana.mymoney.utils.logger.MyLogger;
import javax.inject.Inject;

/**
 * Created by infamouSs on 8/11/17.
 */

public class RegisterActivity extends BaseActivity implements RegisterContract.View {
    
    @Inject
    RegisterPresenter mRegisterPresenter;
    
    @BindView(R.id.txt_username)
    TextView mTextViewUsername;
    
    @BindView(R.id.txt_password)
    TextView mTextViewPassword;
    
    @BindView(R.id.txt_name)
    TextView mTextViewName;
    
    @BindView(R.id.button)
    Button mButton;
    
    public RegisterActivity() {
        
    }
    
    @Override
    public int getLayoutId() {
        return R.layout.register;
    }
    
    @Override
    protected void initializeDagger() {
        ApplicationComponent appComponent = ((MyMoneyApplication) this.getApplication())
                  .getAppComponent();
        
        RegisterComponent registerComponent = DaggerRegisterComponent.builder()
                  .applicationComponent(appComponent)
                  .activityModule(new ActivityModule(this))
                  .registerModule(new RegisterModule())
                  .build();
        
        registerComponent.inject(this);
    }
    
    @Override
    protected void initializePresenter() {
        this.mPresenter = mRegisterPresenter;
        this.mRegisterPresenter.setView(this);
    }
    
    @Override
    protected void initializeActionBar(View rootView) {
        
    }
    
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initializeView();
    }
    
    @Override
    public void initializeView() {
        mTextViewUsername.setText("");
        mTextViewPassword.setText("");
        mTextViewName.setText("");
    }
    
    @OnClick(R.id.button)
    public void onCLick(View view) {
        User user = new User();
        
        user.setUsername(mTextViewUsername.getText().toString());
        user.setPassword(mTextViewPassword.getText().toString());
        user.setEmail(mTextViewName.getText().toString());
        
        mRegisterPresenter.register(user);
    }
    
    @Override
    public void registerSuccess() {
        Toast.makeText(this, "REGISTER SUCCESSFUL", Toast.LENGTH_SHORT).show();
    }
    
    @Override
    public void registerFailure(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
    
    @Override
    public void loading(boolean isLoading) {
        if (isLoading) {
            MyLogger.d("RUNNING");
            return;
        }
        MyLogger.d("STOPPED");
    }
}
