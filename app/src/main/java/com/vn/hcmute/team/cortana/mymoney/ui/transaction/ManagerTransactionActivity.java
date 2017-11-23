package com.vn.hcmute.team.cortana.mymoney.ui.transaction;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;
import com.vn.hcmute.team.cortana.mymoney.R;
import com.vn.hcmute.team.cortana.mymoney.model.Transaction;
import com.vn.hcmute.team.cortana.mymoney.ui.base.BaseActivity;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by infamouSs on 9/28/17.
 */

public class ManagerTransactionActivity extends BaseActivity {
    
    public static final String TAG = ManagerTransactionActivity.class.getSimpleName();
    
    public static final String EXTRA_ACTION = "action";
    public static final String EXTRA_TRANSACTION = "transaction";
    public static final String EXTRA_UPLOAD_IMAGE = "upload_image";
    public static final String EXTRA_MULTIPLE_SELECT_CONTACT = "multiple_select_contact";
    public static final String EXTRA_ONLY_DEBT_LOAN_CATEGORY = "only_debt_loan_category";
    
    private String mAction;
    private Transaction mTransaction;
    private Fragment mFragment;
    private long mDate;
    
    private boolean isEnableUploadImage = true;
    private boolean isEnableMultipleSelectContact = true;
    private boolean isLoadOnlyDebtLoanCategory = false;
    
    @Override
    public int getLayoutId() {
        return R.layout.activity_manager;
    }
    
    @Override
    protected void initializeDagger() {
        
    }
    
    @Override
    protected void initializePresenter() {
        
    }
    
    @Override
    protected void initializeActionBar(View rootView) {
        
    }
    
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        getDataFromIntent();
        Map<String, Boolean> params = new HashMap<>();
        params.put("upload_image", isEnableUploadImage);
        params.put("multiple_select_contact", isEnableMultipleSelectContact);
        params.put("only_debt_loan_category", isLoadOnlyDebtLoanCategory);
        
        mFragment = ManagerTransactionFragment
                  .newInstance(mAction, params, mTransaction, mDate, null);
        
        attachFragment();
    }
    
    
    private void getDataFromIntent() {
        Intent intent = getIntent();
        if (intent != null) {
            mAction = intent.getStringExtra("action");
            mTransaction = intent.getParcelableExtra("transaction");
            isEnableUploadImage = intent.getBooleanExtra("upload_image", true);
            isEnableMultipleSelectContact = intent.getBooleanExtra("multiple_select_contact", true);
            isLoadOnlyDebtLoanCategory = intent.getBooleanExtra("only_debt_loan_category", false);
            mDate = intent.getLongExtra("date", 0);
        }
    }
    
    private void attachFragment() {
        this.getSupportFragmentManager()
                  .beginTransaction()
                  .replace(R.id.container, mFragment)
                  .commit();
    }
}
