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
    
    public static final String TAG = RegisterActivity.class.getSimpleName();
    
    @BindView(R.id.txt_username)
    EditText mEditTextUsername;
    
    @BindView(R.id.txt_password)
    EditText mEditTextPassword;
    
    @BindView(R.id.txt_email)
    EditText mEditTextEmail;
    
    @BindView(R.id.btn_register)
    Button mButtonRegister;
    
    @BindView(R.id.btn_login)
    Button mButtonLogin;
    
    @BindView(R.id.btn_back)
    View mButtonBack;
    
    @Inject
    RegisterPresenter mRegisterPresenter;
    
    private ProgressDialog mProgressDialog;
    private User mUser;
    private boolean isRegisterUserWithFacebookAccount;
    
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
        getDataFromIntent();
        initializeView();
        
    }
    
    private void getDataFromIntent() {
        if (getIntent() != null) {
            isRegisterUserWithFacebookAccount = getIntent()
                      .getBooleanExtra("register_user_by_account_facebook", false);
            mUser = getIntent().getParcelableExtra("user");
        }
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
        
        if (mUser != null) {
            user.setFacebook_id(mUser.getFacebook_id());
            String name = mUser.getName();
            user.setName(name);
        }
        
        mRegisterPresenter.register(user);
    }
    
    @OnClick(R.id.btn_back)
    public void onClickBackButton() {
        finish();
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
        
        if (isRegisterUserWithFacebookAccount) {
            mEditTextEmail.setEnabled(false);
            mEditTextEmail.setText(mUser.getEmail());
            
            mEditTextUsername.setEnabled(false);
            mEditTextUsername.setText(mUser.getEmail());
        }
    }
    
    @Override
    public void registerSuccess(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        finish();
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
