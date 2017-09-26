package com.vn.hcmute.team.cortana.mymoney.ui.login;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
import com.vn.hcmute.team.cortana.mymoney.ui.forgetpassword.ForgetPasswordActivity;
import com.vn.hcmute.team.cortana.mymoney.ui.main.MainActivity;
import com.vn.hcmute.team.cortana.mymoney.ui.register.RegisterActivity;
import javax.inject.Inject;

/**
 * Created by infamouSs on 8/11/17.
 */

public class LoginActivity extends BaseActivity implements LoginContract.View {
    
    
    public static final String TAG = LoginActivity.class.getSimpleName();
    
    @BindView(R.id.txt_username)
    EditText mTextViewUsername;
    
    @BindView(R.id.txt_password)
    EditText mTextViewPassword;
    
    @BindView(R.id.btn_login)
    Button mButtonLogin;
    
    @BindView(R.id.btn_register)
    Button mButtonRegister;
    
    @BindView(R.id.btn_login_with_facebook)
    Button mButtonLoginWithFacebook;
    
    @BindView(R.id.btn_forget_password)
    Button mButtonForgetPassword;
    
    @Inject
    LoginPresenter mLoginPresenter;
    
    private ActionBar mActionBar;
    
    private ProgressDialog mProgressDialog;
    
    /*-----------------*/
    /*Initialize       */
    /*-----------------*/
    @Override
    public int getLayoutId() {
        return R.layout.activity_login;
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
        
        if (mLoginPresenter.isLogin()) {
            openMainActivity();
            finish();
            return;
        }
        
        initializeView();
    }
    
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        
    }
    
    @Override
    protected void onDestroy() {
        super.onDestroy();
        mLoginPresenter.unSubscribe();
    }
    
    /*-----------------*/
    /* Onclick         */
    /*-----------------*/
    
    @OnClick(R.id.btn_login)
    public void onClickLogin(View view) {
        UserCredential userCredential = new UserCredential();
        String username = mTextViewUsername.getText().toString();
        String password = mTextViewPassword.getText().toString();
        
        userCredential.setUsername(username);
        userCredential.setPassword(password);
        
        mLoginPresenter.login(userCredential);
    }
    
    @OnClick(R.id.btn_register)
    public void onClickRegister() {
        openActivityRegister();
    }
    
    @OnClick(R.id.btn_login_with_facebook)
    public void onClickLoginWithFacebook() {
        mLoginPresenter.loginWithFacebook();
    }
    
    @OnClick(R.id.btn_forget_password)
    public void onClickForgetPassword() {
        openActivityForgetPassword();
    }
    
    
    /*-----------------*/
    /*Task View        */
    /*-----------------*/
    @Override
    public void initializeView() {
        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setCanceledOnTouchOutside(false);
        mProgressDialog.setMessage(getString(R.string.message_wait_login));
    }
    
    
    @Override
    public void loginSuccessful() {
        openMainActivity();
    }
    
    @Override
    public void loginFailure(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
    
    @Override
    public void loading(boolean isLoading) {
        
        if (isLoading) {
            mProgressDialog.show();
            return;
        }
        
        mProgressDialog.dismiss();
    }
    
    /*-----------------*/
    /*Helper Method    */
    /*-----------------*/
    
    private void openActivityRegister() {
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }
    
    private void openActivityForgetPassword() {
        Intent intent = new Intent(this, ForgetPasswordActivity.class);
        startActivity(intent);
    }
    
    private void openMainActivity() {
        //TODO: CHANGE
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
