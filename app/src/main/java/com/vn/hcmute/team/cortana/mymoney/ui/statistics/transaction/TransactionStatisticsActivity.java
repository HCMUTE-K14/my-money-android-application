package com.vn.hcmute.team.cortana.mymoney.ui.statistics.transaction;

import android.content.Intent;
import android.view.View;
import butterknife.OnClick;
import com.vn.hcmute.team.cortana.mymoney.R;
import com.vn.hcmute.team.cortana.mymoney.ui.base.BaseActivity;
import com.vn.hcmute.team.cortana.mymoney.ui.statistics.Objects.ObjectByCategory;
import com.vn.hcmute.team.cortana.mymoney.ui.statistics.Objects.ObjectByTime;

/**
 * Created by kunsubin on 11/7/2017.
 */

public class TransactionStatisticsActivity extends BaseActivity {
    
    private ObjectByTime mObjectByTime;
    private ObjectByCategory mObjectByCategory;
    private String mType;
    private FragmentTransactionByTime mFragmentTransactionByTime;
    private FragmentTransactionByCategory mFragmentTransactionByCategory;
    
    @Override
    public int getLayoutId() {
        return R.layout.activity_transaction_statistics;
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
    protected void initialize() {
        getData();
        showData();
    }
    
    @OnClick(R.id.back_button)
    public void onClickBack(View view) {
        finish();
    }
    
    public void getData() {
        Intent intent = this.getIntent();
        mType = intent.getStringExtra("type");
        if (mType.equals("1")) {
            mObjectByTime = intent.getParcelableExtra("data");
            return;
        }
        if (mType.equals("2")) {
            mObjectByCategory = intent.getParcelableExtra("data");
            return;
        }
    }
    
    public void showData() {
        if (mFragmentTransactionByTime != null) {
            getSupportFragmentManager().beginTransaction().remove(mFragmentTransactionByTime)
                      .commit();
        }
        if (mFragmentTransactionByCategory != null) {
            getSupportFragmentManager().beginTransaction().remove(mFragmentTransactionByCategory)
                      .commit();
        }
        if (mType.equals("1")) {
            mFragmentTransactionByTime = new FragmentTransactionByTime(this,
                      mObjectByTime.getTransactionList());
            getSupportFragmentManager().beginTransaction()
                      .replace(R.id.view_list_transaction, mFragmentTransactionByTime).commit();
            return;
        }
        if (mType.equals("2")) {
            mFragmentTransactionByCategory = new FragmentTransactionByCategory(this,
                      mObjectByCategory.getTransactionList());
            getSupportFragmentManager().beginTransaction()
                      .replace(R.id.view_list_transaction, mFragmentTransactionByCategory).commit();
            return;
        }
    }
}
