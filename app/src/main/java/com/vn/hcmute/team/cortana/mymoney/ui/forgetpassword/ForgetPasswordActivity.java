package com.vn.hcmute.team.cortana.mymoney.ui.forgetpassword;

import android.app.ProgressDialog;
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
import com.vn.hcmute.team.cortana.mymoney.di.component.DaggerForgetPasswordComponent;
import com.vn.hcmute.team.cortana.mymoney.di.component.ForgetPasswordComponent;
import com.vn.hcmute.team.cortana.mymoney.di.module.ActivityModule;
import com.vn.hcmute.team.cortana.mymoney.di.module.ForgetPasswordModule;
import com.vn.hcmute.team.cortana.mymoney.ui.base.BaseActivity;
import javax.inject.Inject;

/**
 * Created by infamouSs on 8/25/17.
 */

public class ForgetPasswordActivity extends BaseActivity implements ForgetPasswordContract.View {
    
    
    @BindView(R.id.txt_email)
    EditText mEditTextEmail;
    
    @BindView(R.id.btn_submit)
    Button mButtonSubmit;
    
    @BindView(R.id.btn_back)
    View mButtonBack;
    
    @Inject
    ForgetPasswordPresenter mForgetPasswordPresenter;
    
    private ProgressDialog mProgressDialog;
    
    /*-----------------*/
    /*Initialize       */
    /*-----------------*/
    @Override
    public int getLayoutId() {
        return R.layout.activity_forget_password;
    }
    
    @Override
    protected void initializeDagger() {
        ApplicationComponent applicationComponent = ((MyMoneyApplication) this.getApplication())
                  .getAppComponent();
        ForgetPasswordComponent forgetPasswordComponent = DaggerForgetPasswordComponent
                  .builder()
                  .applicationComponent(applicationComponent)
                  .activityModule(new ActivityModule(this))
                  .forgetPasswordModule(new ForgetPasswordModule())
                  .build();
        
        forgetPasswordComponent.inject(this);
    }
    
    @Override
    protected void initializePresenter() {
        this.mPresenter = mForgetPasswordPresenter;
        mForgetPasswordPresenter.setView(this);
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
        mForgetPasswordPresenter.unSubscribe();
    }
    /*-----------------*/
    /*Helper Method    */
    /*-----------------*/
    
    @OnClick(R.id.btn_submit)
    public void onClickSubmit() {
        String email = mEditTextEmail.getText().toString();
        mForgetPasswordPresenter.forgetPassword(email);
    }
    
    @OnClick(R.id.btn_back)
    public void onClickBackButtion() {
        finish();
    }
    
    /*-----------------*/
    /*Task View       */
    /*-----------------*/
    @Override
    public void initializeView() {
        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setCanceledOnTouchOutside(false);
        mProgressDialog.setMessage(getString(R.string.message_sending_email));
    }
    
    @Override
    public void successForgetPassword(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
    
    @Override
    public void showError(String message) {
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
