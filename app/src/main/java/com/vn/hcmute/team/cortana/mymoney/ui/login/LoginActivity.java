package com.vn.hcmute.team.cortana.mymoney.ui.login;

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
import com.vn.hcmute.team.cortana.mymoney.di.component.DaggerLoginComponent;
import com.vn.hcmute.team.cortana.mymoney.di.component.LoginComponent;
import com.vn.hcmute.team.cortana.mymoney.di.module.ActivityModule;
import com.vn.hcmute.team.cortana.mymoney.di.module.LoginModule;
import com.vn.hcmute.team.cortana.mymoney.model.UserCredential;
import com.vn.hcmute.team.cortana.mymoney.ui.base.BaseActivity;
import com.vn.hcmute.team.cortana.mymoney.utils.logger.MyLogger;
import com.vn.hcmute.team.cortana.mymoney.utils.permission.PermissionCallBack;
import javax.inject.Inject;

/**
 * Created by infamouSs on 8/11/17.
 */

public class LoginActivity extends BaseActivity implements LoginContract.View {
    
    
    private PermissionCallBack mPermissionCallBack = new PermissionCallBack() {
        @Override
        public void onPermissionGranted() {
            // loadHomeFragment();
        }
        
        @Override
        public void onPermissionDenied() {
            finish();
        }
    };
    
    @BindView(R.id.txt_username)
    TextView mTextViewUsername;
    
    @BindView(R.id.txt_password)
    TextView mTextViewPassword;
    
    @BindView(R.id.button)
    Button mButtonLogin;
    
    @Inject
    LoginPresenter mLoginPresenter;
    
    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }
    
    @Override
    protected void initializeDagger() {
        ApplicationComponent appComponent = ((MyMoneyApplication) this.getApplication())
                  .getAppComponent();
        
        LoginComponent loginComponent = DaggerLoginComponent.builder()
                  .applicationComponent(appComponent)
                  .activityModule(new ActivityModule(this))
                  .loginModule(new LoginModule())
                  .build();
        
        loginComponent.inject(this);
    }
    
    @Override
    protected void initializePresenter() {
        this.mPresenter = mLoginPresenter;
        
        this.mLoginPresenter.setView(this);
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
        mTextViewUsername.setText("USERNAME");
        mTextViewPassword.setText("PASSWORD");
        
        
    }
    
    @OnClick(R.id.button)
    public void onClickLogin(View view) {
        UserCredential userCredential = new UserCredential();
        
        userCredential.setUsername(mTextViewUsername.getText().toString());
        userCredential.setPassword(mTextViewPassword.getText().toString());
        
        mLoginPresenter.login(userCredential);
    }
   
    @Override
    public void loginSuccessful() {
        Toast.makeText(this, "LOGIN successful", Toast.LENGTH_SHORT).show();
    }
    
    @Override
    public void loginFailure(String message) {
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
