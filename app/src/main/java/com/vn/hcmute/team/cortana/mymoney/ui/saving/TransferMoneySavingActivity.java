package com.vn.hcmute.team.cortana.mymoney.ui.saving;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.OnClick;
import com.vn.hcmute.team.cortana.mymoney.MyMoneyApplication;
import com.vn.hcmute.team.cortana.mymoney.R;
import com.vn.hcmute.team.cortana.mymoney.di.component.ApplicationComponent;
import com.vn.hcmute.team.cortana.mymoney.di.component.DaggerSavingComponent;
import com.vn.hcmute.team.cortana.mymoney.di.component.SavingComponent;
import com.vn.hcmute.team.cortana.mymoney.di.module.ActivityModule;
import com.vn.hcmute.team.cortana.mymoney.di.module.SavingModule;
import com.vn.hcmute.team.cortana.mymoney.model.Saving;
import com.vn.hcmute.team.cortana.mymoney.ui.base.BaseActivity;
import com.vn.hcmute.team.cortana.mymoney.ui.tools.calculator.CalculatorActivity;
import javax.inject.Inject;

/**
 * Created by kunsubin on 9/8/2017.
 */

public class TransferMoneySavingActivity extends BaseActivity{
    @BindView(R.id.txt_name_saving)
    TextView txt_name_saving;
    @BindView(R.id.txt_remainin)
    TextView txt_remainin;
    @BindView(R.id.edit_describe)
    EditText edit_describe;
    @BindView(R.id.txt_money)
    TextView txt_money;
    
    
    @Inject
    SavingPresenter mSavingPresenter;
    
    private   String value="-1";
    private Saving mSaving;

    @Override
    public int getLayoutId() {
        return R.layout.activity_transaction_saving;
    }
    
    @Override
    protected void initializeDagger() {
        
        ApplicationComponent applicationComponent = ((MyMoneyApplication) getApplication())
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
    
        getData();
        showData();
        
        
    }
    public void getData(){
        Intent intent=getIntent();
        value=intent.getStringExtra("value");
        mSaving=(Saving)intent.getSerializableExtra("saving");
        
    }
    public void showData(){
        txt_name_saving.setText(mSaving.getName());
        double remainin=Double.parseDouble(mSaving.getGoalMoney())-Double.parseDouble(mSaving.getCurrentMoney());
        txt_remainin.setText("+"+remainin);
        if(value.equals("1")){
            edit_describe.setText(getString(R.string.deposit));
        }else {
            edit_describe.setText(getString(R.string.withdraw));
        }
    }
    @Override
    protected void onDestroy() {
        mSavingPresenter.unSubscribe();
        super.onDestroy();
    }
    
    @Override
    protected void initializePresenter() {
        mPresenter=mSavingPresenter;
        
    }
    
    @Override
    protected void initializeActionBar(View rootView) {
        
    }
    @OnClick(R.id.linear_money)
    public void onClickLinearMoney(View view){
        Intent intent=new Intent(this, CalculatorActivity.class);
        intent.putExtra("goal_money","0");
        startActivityForResult(intent,9);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        
        if (requestCode == 9) {
            if (resultCode == Activity.RESULT_OK) {
                
                String result=data.getStringExtra("result");
                
                txt_money.setText(result);
                
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                
            }
        }
    }
    @OnClick(R.id.back_button_saving)
    public void onClickBack(View view){
        finish();
    }
}
