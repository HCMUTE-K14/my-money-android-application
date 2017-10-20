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
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphRequest.GraphJSONObjectCallback;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.vn.hcmute.team.cortana.mymoney.MyMoneyApplication;
import com.vn.hcmute.team.cortana.mymoney.R;
import com.vn.hcmute.team.cortana.mymoney.data.cache.PreferencesHelper;
import com.vn.hcmute.team.cortana.mymoney.di.component.ApplicationComponent;
import com.vn.hcmute.team.cortana.mymoney.di.component.DaggerLoginComponent;
import com.vn.hcmute.team.cortana.mymoney.di.component.LoginComponent;
import com.vn.hcmute.team.cortana.mymoney.di.module.ActivityModule;
import com.vn.hcmute.team.cortana.mymoney.di.module.LoginModule;
import com.vn.hcmute.team.cortana.mymoney.model.User;
import com.vn.hcmute.team.cortana.mymoney.model.UserCredential;
import com.vn.hcmute.team.cortana.mymoney.ui.base.BaseActivity;
import com.vn.hcmute.team.cortana.mymoney.ui.base.listener.BaseCallBack;
import com.vn.hcmute.team.cortana.mymoney.ui.forgetpassword.ForgetPasswordActivity;
import com.vn.hcmute.team.cortana.mymoney.ui.main.MainActivity;
import com.vn.hcmute.team.cortana.mymoney.ui.register.RegisterActivity;
import com.vn.hcmute.team.cortana.mymoney.usecase.base.Action;
import com.vn.hcmute.team.cortana.mymoney.usecase.remote.UserManager;
import com.vn.hcmute.team.cortana.mymoney.usecase.remote.UserManager.UserRequest;
import com.vn.hcmute.team.cortana.mymoney.utils.SecurityUtil;
import java.util.Arrays;
import java.util.List;
import javax.inject.Inject;
import org.json.JSONException;
import org.json.JSONObject;

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
    
    @BindView(R.id.real_login_button)
    LoginButton mRealLoginButtonFacebook;
    
    @BindView(R.id.btn_forget_password)
    Button mButtonForgetPassword;
    
    @Inject
    LoginPresenter mLoginPresenter;
    
    @Inject
    UserManager mUserManager;
    
    private ActionBar mActionBar;
    
    private ProgressDialog mProgressDialog;
    
    private CallbackManager mCallbackManager;
    
    private GraphRequest.GraphJSONObjectCallback mGraphCallBack = new GraphJSONObjectCallback() {
        @Override
        public void onCompleted(JSONObject object, GraphResponse response) {
            try {
                String id = object.getString("id");
                String name = object.getString("name");
                String email = object.getString("email");
                
                final User user = new User();
                
                user.setUsername(email);
                user.setName(name);
                user.setEmail(email);
                user.setFacebook_id(SecurityUtil.encrypt(id));
                UserRequest userRequest = new UserRequest(
                          Action.ACTION_CHECK_EXIST_FACEBOOK_ACCOUNT, new BaseCallBack<Object>() {
                    @Override
                    public void onSuccess(Object value) {
                        mProgressDialog.dismiss();
                        mLoginPresenter.login(user);
                    }
                    
                    @Override
                    public void onFailure(Throwable throwable) {
                        mProgressDialog.dismiss();
                        Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                        intent.putExtra("register_user_by_account_facebook", true);
                        intent.putExtra("user", user);
                        
                        startActivity(intent);
                    }
                    
                    @Override
                    public void onLoading() {
                        mProgressDialog.show();
                    }
                }, user);
                
                mUserManager.subscribe(userRequest);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    };
    
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
        initializeLoginFacebookEnvironment();
        
    }
    
    private void initializeLoginFacebookEnvironment() {
        FacebookSdk.sdkInitialize(this.getApplicationContext());
        mCallbackManager = CallbackManager.Factory.create();
        mRealLoginButtonFacebook.registerCallback(mCallbackManager,
                  new FacebookCallback<LoginResult>() {
                      @Override
                      public void onSuccess(LoginResult loginResult) {
                          String accessToken = loginResult.getAccessToken()
                                    .getToken();
                          
                          storedFacebookAccessToken(accessToken);
                          
                          Bundle parameters = new Bundle();
                          parameters.putString("fields",
                                    "id,name,email");
                          GraphRequest request = GraphRequest
                                    .newMeRequest(loginResult.getAccessToken(), mGraphCallBack);
                          request.setParameters(parameters);
                          request.executeAsync();
                      }
                      
                      @Override
                      public void onCancel() {
                          
                      }
                      
                      @Override
                      public void onError(FacebookException error) {
                          
                      }
                  });
    }
    
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mCallbackManager.onActivityResult(requestCode, resultCode, data);
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
        List<String> permissionNeeds = Arrays.asList("email", "public_profile");
        LoginManager.getInstance().logInWithReadPermissions(this, permissionNeeds);
        // mLoginPresenter.loginWithFacebook();
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
        //        Transaction transaction = new Transaction();
        //        transaction.setUser_id("8daff5010562451fbf1c381f4e623390");
        //        transaction.setTrans_id("e46766b7c4274d34bdc853b29b3035d6");
        //        transaction.setAmount("2000000000");
        //        Category category = new Category();
        //        category.setId("2");
        //        category.setName("Restaurants");
        //        category.setIcon("icon_133");
        //        category.setTransType("expense");
        //        category.setType("expense");
        //        transaction.setCategory(category);
        //
        //        Wallet wallet = new Wallet();
        //        wallet.setWalletid("40a41cbcd2cb4feca77f75fb3e43e89a");
        //        wallet.setUserid("8daff5010562451fbf1c381f4e623390");
        //        wallet.setWalletName("Wallet 1");
        //        wallet.setMoney("1,000,000");
        //        Currencies currencies = new Currencies();
        //        currencies.setCurId("5");
        //        currencies.setCurName("Yuan Renminbi");
        //        currencies.setCurCode("CNY");
        //        currencies.setCurSymbol("¥");
        //        currencies.setCurDisplayType("0");
        //        wallet.setWalletImage("ic_saving");
        //        wallet.setCurrencyUnit(currencies);
        //        transaction.setWallet(wallet);
        //        transaction.setDate_created("1504712678928");
        //        transaction.setDate_end("1504971878928");
        intent.putExtra("action", Action.ACTION_ADD_TRANSACTION);
        //intent.putExtra("transaction", transaction);
        startActivity(intent);
    }
    
    private void storedFacebookAccessToken(String accessToken) {
        PreferencesHelper
                  .getInstance(LoginActivity.this)
                  .putFacebookAccessToken(accessToken);
        
    }
}
