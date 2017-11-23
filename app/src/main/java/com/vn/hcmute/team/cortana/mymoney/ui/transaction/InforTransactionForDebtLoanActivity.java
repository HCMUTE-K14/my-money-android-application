package com.vn.hcmute.team.cortana.mymoney.ui.transaction;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import com.vn.hcmute.team.cortana.mymoney.MyMoneyApplication;
import com.vn.hcmute.team.cortana.mymoney.R;
import com.vn.hcmute.team.cortana.mymoney.di.component.ApplicationComponent;
import com.vn.hcmute.team.cortana.mymoney.event.ActivityResultEvent;
import com.vn.hcmute.team.cortana.mymoney.model.DebtLoan;
import com.vn.hcmute.team.cortana.mymoney.model.Person;
import com.vn.hcmute.team.cortana.mymoney.ui.view.RoundedLetterView;
import com.vn.hcmute.team.cortana.mymoney.usecase.base.Action;
import com.vn.hcmute.team.cortana.mymoney.usecase.remote.DebtLoanUseCase;
import com.vn.hcmute.team.cortana.mymoney.utils.Constraints;
import com.vn.hcmute.team.cortana.mymoney.utils.Constraints.ResultCode;
import com.vn.hcmute.team.cortana.mymoney.utils.NumberUtil;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import javax.inject.Inject;
import org.greenrobot.eventbus.EventBus;

/**
 * Created by infamouSs on 10/20/17.
 */

public class InforTransactionForDebtLoanActivity extends BaseInfoTransactionActivity {
    
    public static final String TAG = InforTransactionForDebtLoanActivity.class.getSimpleName();
    
    View mCashBackView;
    TextView mTextViewAmountStart;
    TextView mTextViewAmountEnd;
    TextView mTextViewType;
    RoundedLetterView mRoundedLetterView;
    TextView mTextViewNamePerson;
    
    SeekBar mSeekBar;
    
    @Inject
    DebtLoanUseCase mDebtLoanUseCase;
    
    
    private String mType;
    private DebtLoan mDebtLoan;
    
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
    }
    
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constraints.RequestCode.UPDATE_TRANSACTION_REQUEST_CODE &&
            data != null) {
            initCashBack();
        }
    }
    
    @Override
    protected void initializeDagger() {
        super.initializeDagger();
        mTransactionComponent.inject(this);
    }
    
    @Override
    public void initializeView() {
        super.initializeView();
        
        mCashBackView = LayoutInflater.from(this)
                  .inflate(R.layout.layout_cash_back_trans, null, false);
        
        this.mViewContainer.addView(mCashBackView);
        
        mTextViewAmountStart = (TextView) mCashBackView.findViewById(R.id.txt_amount_start);
        mTextViewAmountEnd = (TextView) mCashBackView.findViewById(R.id.txt_amount_end);
        mTextViewType = (TextView) mCashBackView.findViewById(R.id.txt_type);
        mRoundedLetterView = (RoundedLetterView) mCashBackView.findViewById(R.id.rounded_letter);
        mTextViewNamePerson = (TextView) mCashBackView.findViewById(R.id.txt_name_person);
        ((Button) mCashBackView.findViewById(R.id.btn_cash_back)).setOnClickListener(
                  new OnClickListener() {
                      @Override
                      public void onClick(View v) {
                          cashBack(mDebtLoan);
                      }
                  });
        mSeekBar = (SeekBar) mCashBackView.findViewById(R.id.seek_bar);
        
        mType = getIntent().getStringExtra("type");
        mDebtLoan = getIntent().getParcelableExtra("debt_loan");
        
        if (mType.equals("debt")) {
            mTextViewType.setText(getString(R.string.txt_lender));
        } else if (mType.equals("loan")) {
            mTextViewType.setText(getString(R.string.txt_borrower));
        }
        
        initCashBack();
    }
    
    
    @Override
    public void exit() {
        super.exit();
        EventBus.getDefault()
                  .post(new ActivityResultEvent(ResultCode.EDIT_TRANSACTION_RESULT_CODE, null));
    }
    
    @Override
    public void editTrans() {
        Intent intent = new Intent(this, ManagerTransactionActivity.class);
        intent.putExtra("action", Action.ACTION_UPDATE_TRANSACTION);
        intent.putExtra("transaction", mTransaction);
        intent.putExtra("multiple_select_contact", false);
        startActivityForResult(intent, Constraints.RequestCode.UPDATE_TRANSACTION_REQUEST_CODE);
    }
    
    @Override
    public void onDeleteSuccessTransaction(String message) {
        super.onDeleteSuccessTransaction(message);
    }
    
    private void initCashBack() {
        if (mDebtLoan.getStatus() == 1) {
            mCashBackView.findViewById(R.id.btn_cash_back).setVisibility(View.GONE);
        }
        
        if (mTransaction.getPerson() == null) {
            return;
        }
        
        Person person = mTransaction.getPerson().get(0);
        mTextViewNamePerson.setText(person.getName());
        String symbolCur = mTransaction.getWallet().getCurrencyUnit().getCurSymbol();
        mTextViewAmountStart
                  .setText("0" + " " + symbolCur);
        mTextViewAmountEnd.setText(
                  NumberUtil.formatAmount(mTransaction.getAmount(), symbolCur));
        if (mDebtLoan.getStatus() == 1) {
            mSeekBar.setProgress(100);
        }
    }
    
    private void cashBack(DebtLoan debtLoan) {
        mProgressDialog.show();
        debtLoan.setStatus(1);
        ApplicationComponent applicationComponent = ((MyMoneyApplication) this.getApplication())
                  .getAppComponent();
        //        String user_id = applicationComponent.preferencesHelper().getUserId();
        //        String token = applicationComponent.preferencesHelper().getUserToken();
        //        String wallet_id = applicationComponent.preferencesHelper().getCurrentWallet()
        //                  .getWalletid();
        //        if (TextUtils.isEmpty(user_id) || TextUtils.isEmpty(token) ||
        //            TextUtils.isEmpty(wallet_id)) {
        //            return;
        //        }
        
        applicationComponent
                  .dataRepository()
                  .updateLocalDebtLoan(debtLoan)
                  .subscribeOn(Schedulers.computation())
                  .observeOn(AndroidSchedulers.mainThread())
                  .singleOrError()
                  .subscribe(new Consumer<String>() {
                      @Override
                      public void accept(String s) throws Exception {
                          mProgressDialog.dismiss();
                          Toast.makeText(InforTransactionForDebtLoanActivity.this,
                                    R.string.txt_successful,
                                    Toast.LENGTH_SHORT).show();
                          mSeekBar.setProgress(100);
                          EventBus.getDefault().post(new ActivityResultEvent(
                                    ResultCode.NEED_UPDATE_CURRENT_WALLET_RESULT_CODE, null));
                      }
                  }, new Consumer<Throwable>() {
                      @Override
                      public void accept(Throwable throwable) throws Exception {
                          mProgressDialog.dismiss();
                          Toast.makeText(InforTransactionForDebtLoanActivity.this,
                                    throwable.getMessage(),
                                    Toast.LENGTH_SHORT)
                                    .show();
                      }
                  });
    }
    
}
