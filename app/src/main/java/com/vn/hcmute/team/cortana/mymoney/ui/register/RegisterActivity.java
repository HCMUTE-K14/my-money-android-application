package com.vn.hcmute.team.cortana.mymoney.ui.register;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
import com.vn.hcmute.team.cortana.mymoney.ui.login.LoginActivity;
import javax.inject.Inject;

/**
 * Created by infamouSs on 8/11/17.
 */

public class RegisterActivity extends BaseActivity implements RegisterContract.View {
    
    @BindView(R.id.txt_username)
    EditText mEditTextUsername;
    
    @BindView(R.id.txt_password)
    EditText mEditTextPassword;
    
    @BindView(R.id.txt_email)
    EditText mEditTextEmail;
    
    @BindView(R.id.btn_register)
    Button mButtonRegister;
    
    @BindView(R.id.btn_register_with_facebook)
    Button mButtonRegisterWithFacebook;
    
    @BindView(R.id.btn_login)
    Button mButtonLogin;
    
    @BindView(R.id.btn_back)
    View mButtonBack;
    
    @Inject
    RegisterPresenter mRegisterPresenter;
    
    private ProgressDialog mProgressDialog;
    
    public RegisterActivity() {
        
    }
    
    /*-----------------*/
    /*Initialize       */
    /*-----------------*/
    @Override
    public int getLayoutId() {
        return R.layout.activity_register;
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
    protected void onDestroy() {
        super.onDestroy();
        mRegisterPresenter.unSubscribe();
    }
    /*-----------------*/
    /*Helper Method    */
    /*-----------------*/
    
    @OnClick(R.id.btn_register)
    public void onClickRegister() {
        
        String username = mEditTextUsername.getText().toString();
        String password = mEditTextPassword.getText().toString();
        String email = mEditTextEmail.getText().toString();
        
        User user = new User(username, password, email);
        
        mRegisterPresenter.register(user);
    }
    
    @OnClick(R.id.btn_back)
    public void onClickBackButtion() {
        finish();
    }
    
    @OnClick(R.id.btn_register_with_facebook)
    public void onClickRegisterWithFacebook() {
        mRegisterPresenter.registerWithFacebook();
    }
    
    @OnClick(R.id.btn_login)
    public void onClickLogin() {
        openActivityLogin();
    }
    
    private void openActivityLogin() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }
    
    /*-----------------*/
    /*Task View        */
    /*-----------------*/
    
    @Override
    public void initializeView() {
        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setCanceledOnTouchOutside(false);
        mProgressDialog.setMessage(getString(R.string.message_creating_account));
    }
    
    @Override
    public void registerSuccess(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
    
    @Override
    public void registerWithFacebookSuccess(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
    
    @Override
    public void registerFailure(String message) {
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
}
