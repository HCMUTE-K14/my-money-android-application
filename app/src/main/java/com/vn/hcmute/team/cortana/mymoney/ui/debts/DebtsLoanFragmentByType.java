package com.vn.hcmute.team.cortana.mymoney.ui.debts;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ProgressBar;
import android.widget.Toast;
import butterknife.BindView;
import com.vn.hcmute.team.cortana.mymoney.MyMoneyApplication;
import com.vn.hcmute.team.cortana.mymoney.R;
import com.vn.hcmute.team.cortana.mymoney.di.component.ApplicationComponent;
import com.vn.hcmute.team.cortana.mymoney.di.component.DaggerDebtLoanComponent;
import com.vn.hcmute.team.cortana.mymoney.di.component.DebtLoanComponent;
import com.vn.hcmute.team.cortana.mymoney.di.module.ActivityModule;
import com.vn.hcmute.team.cortana.mymoney.di.module.DebtLoanModule;
import com.vn.hcmute.team.cortana.mymoney.model.DebtLoan;
import com.vn.hcmute.team.cortana.mymoney.model.Transaction;
import com.vn.hcmute.team.cortana.mymoney.ui.base.BaseFragment;
import com.vn.hcmute.team.cortana.mymoney.ui.debts.DebtLoanContract.ShowView;
import com.vn.hcmute.team.cortana.mymoney.ui.transaction.InfoTransactionActivity;
import com.vn.hcmute.team.cortana.mymoney.utils.Constraints;
import com.vn.hcmute.team.cortana.mymoney.utils.logger.MyLogger;
import java.util.List;
import javax.inject.Inject;

/**
 * Created by infamouSs on 9/27/17.
 */

public class DebtsLoanFragmentByType extends BaseFragment implements ShowView,
                                                                     DebtLoanContract.AddEditView {
    
    public static final String TAG = DebtsLoanFragmentByType.class.getName();
    
    public static final String TYPE_DEBT = "debt";
    public static final String TYPE_LOAN = "loan";
    @BindView(R.id.expandable_list_view)
    ExpandableListView mExpandableListView;
    @BindView(R.id.progress_bar)
    ProgressBar mProgressBar;
    @Inject
    DebtLoanPresenter mDebtLoanPresenter;
    private String mType;
    private DebtLoanAdapter mDebtLoanAdapter;
    private DebtLoanListener mDebtLoanListener = new DebtLoanListener() {
        @Override
        public void onClick(DebtLoan debtLoan, int groupPosition, int childPosition) {
            Intent intent = new Intent(DebtsLoanFragmentByType.this.getContext(),
                      InfoTransactionActivity.class);
            
            intent.putExtra(InfoTransactionActivity.EXTRA_SHOW_CASH_BACK_VIEW, true);
            intent.putExtra("transaction", debtLoan.getTransaction());
            
            startActivityForResult(intent,
                      Constraints.RequestCode.OPEN_INFO_TRANSACTION_MODE_DEBT_LOAN_REQUEST_CODE);
        }
    };
    
    public static DebtsLoanFragmentByType newInstance(String type) {
        Bundle bundle = new Bundle();
        
        switch (type) {
            case TYPE_DEBT:
                bundle.putString("type", type);
                break;
            case TYPE_LOAN:
                bundle.putString("type", type);
                break;
            default:
                throw new RuntimeException("Wrong type");
        }
        DebtsLoanFragmentByType fragment = new DebtsLoanFragmentByType();
        fragment.setArguments(bundle);
        
        return fragment;
    }
    
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_debt_loan_by_type;
    }
    
    @Override
    protected void initializeDagger() {
        ApplicationComponent applicationComponent = ((MyMoneyApplication) this.getActivity()
                  .getApplication()).getAppComponent();
        
        DebtLoanComponent debtLoanComponent = DaggerDebtLoanComponent.builder()
                  .activityModule(new ActivityModule(this.getActivity()))
                  .debtLoanModule(new DebtLoanModule())
                  .applicationComponent(applicationComponent)
                  .build();
        
        debtLoanComponent.inject(this);
    }
    
    @Override
    protected void initializePresenter() {
        this.mPresenter = mDebtLoanPresenter;
        this.mDebtLoanPresenter.setView(this);
    }
    
    
    @Override
    protected void initializeActionBar(View rootView) {
        
    }
    
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            
            mType = getArguments().getString("type");
        }
    }
    
    
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getData();
    }
    
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        MyLogger.d(TAG, requestCode);
    }
    
    @Override
    public void onFailure(String message) {
        Toast.makeText(this.getContext(), message, Toast.LENGTH_SHORT).show();
    }
    
    @Override
    public void loading(boolean isLoading) {
        mProgressBar.setVisibility(isLoading ? View.VISIBLE : View.GONE);
    }
    
    @Override
    public void showList(List<DebtLoan> list) {
        
        mDebtLoanAdapter.setData(list);
        mExpandableListView.setAdapter(mDebtLoanAdapter);
        
        expandsOrCollapse(true);
    }
    
    @Override
    public void showEmpty() {
        //TODO: Empty Adapter
    }
    
    @Override
    protected void initialize() {
        super.initialize();
        mDebtLoanAdapter = new DebtLoanAdapter(this.getContext(), mType, null, mDebtLoanListener);
    }
    
    public void addDebtLoan(Transaction transaction) {
        
        DebtLoan debtLoan = new DebtLoan();
        debtLoan.setStatus(0);
        debtLoan.setTransaction(transaction);
        if (transaction == null) {
            Toast.makeText(this.getContext(), R.string.message_warning_transaction_cannot_be_null,
                      Toast.LENGTH_SHORT)
                      .show();
            return;
        }
        String typeTrans = transaction.getType();
        String typeDebtLoan = "";
        if (typeTrans.equals("income")) {
            typeDebtLoan = "debt";
        } else if (typeTrans.equals("expense")) {
            typeDebtLoan = "loan";
        }
        debtLoan.setType(typeDebtLoan);
        debtLoan.setTransaction(transaction);
        
        mDebtLoanPresenter.addDebtLoan(debtLoan);
    }
    
    private void getData() {
        mDebtLoanPresenter.getDebtLoanByType(this.mType);
    }
    
    private void expandsOrCollapse(boolean isExpand) {
        for (int i = 0; i < mDebtLoanAdapter.getGroupCount(); i++) {
            if (isExpand) {
                mExpandableListView.expandGroup(i);
            } else {
                mExpandableListView.collapseGroup(i);
            }
        }
    }
    
    @Override
    public void addDebtLoanSuccessful(DebtLoan debtLoan, String message) {
        Toast.makeText(this.getContext(), "SUCCESSFUL", Toast.LENGTH_SHORT).show();
        mDebtLoanAdapter.add(debtLoan);
    }
    
    @Override
    public void editDebtLoanSuccessful(DebtLoan debtLoan, String message) {
        
    }
    
}
