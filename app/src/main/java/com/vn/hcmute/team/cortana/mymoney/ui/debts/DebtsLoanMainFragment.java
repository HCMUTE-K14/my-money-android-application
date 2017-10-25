package com.vn.hcmute.team.cortana.mymoney.ui.debts;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;
import butterknife.BindView;
import butterknife.OnClick;
import com.vn.hcmute.team.cortana.mymoney.R;
import com.vn.hcmute.team.cortana.mymoney.model.Transaction;
import com.vn.hcmute.team.cortana.mymoney.ui.base.BaseFragment;
import com.vn.hcmute.team.cortana.mymoney.ui.transaction.ManagerTransactionActivity;
import com.vn.hcmute.team.cortana.mymoney.usecase.base.Action;
import com.vn.hcmute.team.cortana.mymoney.utils.Constraints;
import com.vn.hcmute.team.cortana.mymoney.utils.Constraints.ResultCode;
import com.vn.hcmute.team.cortana.mymoney.utils.logger.MyLogger;

/**
 * Created by infamouSs on 9/27/17.
 */

public class DebtsLoanMainFragment extends BaseFragment {
    
    public static final String TAG = DebtsLoanMainFragment.class.getSimpleName();
    
    @BindView(R.id.tab_layout)
    TabLayout mTabLayout;
    
    @BindView(R.id.viewpager)
    ViewPager mViewPager;
    
    private DebtsViewPagerAdapter mViewPagerAdapter;
    
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_main_debts;
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
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initializeView();
    }
    
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        MyLogger.d(TAG, resultCode);
        if (resultCode == ResultCode.ADD_TRANSACTION_RESULT_CODE) {
            Transaction transaction = data.getParcelableExtra("transaction");
            
            if (transaction != null) {
                ((DebtsLoanFragmentByType) mViewPagerAdapter.getItem(mViewPager.getCurrentItem()))
                          .addDebtLoan(transaction);
            }
        } else if (resultCode == ResultCode.CHANGE_DEBT_LOAN_RESULT_CODE ||
                   resultCode == ResultCode.REMOVE_TRANSACTION_RESULT_CODE) {
            ((DebtsLoanFragmentByType) mViewPagerAdapter.getItem(mViewPager.getCurrentItem()))
                      .getData();
        }
    }
    
    @OnClick(R.id.btn_add_debt)
    public void addDebtLoan() {
        Intent intent = new Intent(this.getContext(), ManagerTransactionActivity.class);
        intent.putExtra(ManagerTransactionActivity.EXTRA_ACTION, Action.ACTION_ADD_TRANSACTION);
        intent.putExtra(ManagerTransactionActivity.EXTRA_MULTIPLE_SELECT_CONTACT, false);
        intent.putExtra(ManagerTransactionActivity.EXTRA_UPLOAD_IMAGE, false);
        intent.putExtra(ManagerTransactionActivity.EXTRA_ONLY_DEBT_LOAN_CATEGORY, true);
        
        startActivityForResult(intent, Constraints.RequestCode.ADD_DEBT_LOAN_REQUEST_CODE);
    }
    
    public void initializeView() {
        mViewPagerAdapter = new DebtsViewPagerAdapter(
                  this.getChildFragmentManager());
        
        mViewPagerAdapter
                  .add(DebtsLoanFragmentByType.newInstance(DebtsLoanFragmentByType.TYPE_DEBT),
                            getString(R.string.txt_debt_uppercase));
        mViewPagerAdapter
                  .add(DebtsLoanFragmentByType.newInstance(DebtsLoanFragmentByType.TYPE_LOAN),
                            getString(R.string.txt_loan_uppercase));
        
        mViewPager.setAdapter(mViewPagerAdapter);
        mTabLayout.setupWithViewPager(mViewPager);
    }
}
