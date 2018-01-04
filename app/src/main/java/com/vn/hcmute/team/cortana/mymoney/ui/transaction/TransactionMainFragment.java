package com.vn.hcmute.team.cortana.mymoney.ui.transaction;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.OnClick;
import com.vn.hcmute.team.cortana.mymoney.MyMoneyApplication;
import com.vn.hcmute.team.cortana.mymoney.R;
import com.vn.hcmute.team.cortana.mymoney.data.cache.PreferencesHelper;
import com.vn.hcmute.team.cortana.mymoney.di.component.ApplicationComponent;
import com.vn.hcmute.team.cortana.mymoney.di.component.DaggerTransactionComponent;
import com.vn.hcmute.team.cortana.mymoney.di.component.TransactionComponent;
import com.vn.hcmute.team.cortana.mymoney.di.module.ActivityModule;
import com.vn.hcmute.team.cortana.mymoney.di.module.TransactionModule;
import com.vn.hcmute.team.cortana.mymoney.event.ActivityResultEvent;
import com.vn.hcmute.team.cortana.mymoney.model.DebtLoan;
import com.vn.hcmute.team.cortana.mymoney.model.Transaction;
import com.vn.hcmute.team.cortana.mymoney.model.Wallet;
import com.vn.hcmute.team.cortana.mymoney.ui.base.BaseFragment;
import com.vn.hcmute.team.cortana.mymoney.ui.base.listener.BaseCallBack;
import com.vn.hcmute.team.cortana.mymoney.ui.statistics.transaction.FragmentTransactionByCategory;
import com.vn.hcmute.team.cortana.mymoney.ui.statistics.transaction.FragmentTransactionByTime;
import com.vn.hcmute.team.cortana.mymoney.ui.transaction.SelectTimeRangeDialog.SelectTimeRangeListener;
import com.vn.hcmute.team.cortana.mymoney.ui.view.calendarview.CalendarTransactionView;
import com.vn.hcmute.team.cortana.mymoney.ui.view.calendarview.CalendarTransactionView.Listener;
import com.vn.hcmute.team.cortana.mymoney.ui.welcome.WelcomeActivity;
import com.vn.hcmute.team.cortana.mymoney.usecase.base.Action;
import com.vn.hcmute.team.cortana.mymoney.usecase.base.TypeRepository;
import com.vn.hcmute.team.cortana.mymoney.usecase.remote.DebtLoanUseCase;
import com.vn.hcmute.team.cortana.mymoney.usecase.remote.DebtLoanUseCase.DebtLoanRequest;
import com.vn.hcmute.team.cortana.mymoney.utils.Constraints.ResultCode;
import com.vn.hcmute.team.cortana.mymoney.utils.logger.MyLogger;
import java.util.List;
import javax.inject.Inject;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

/**
 * Created by infamouSs on 11/3/17.
 */

public class TransactionMainFragment extends BaseFragment implements TransactionContract.View {
    
    private final int VIEW_BY_TRANS = 0;
    private final int VIEW_BY_CATEGORY = 1;
    
    @BindView(R.id.calendar_view)
    LinearLayout mView;
    
    @Inject
    TransactionPresenter mTransactionPresenter;
    
    @Inject
    DebtLoanUseCase mDebtLoanUseCase;
    
    private PreferencesHelper mPreferencesHelper;
    
    private boolean mIsViewByCategory;
    
    private CalendarTransactionView mCalendarTransactionView;
    
    private ProgressDialog mProgressDialog;
    private Fragment mFragment;
    
    
    private String mWalletId;
    private String mStartDate;
    private String mEndDate;
    
    private SelectTimeRangeListener mSelectTimeRangeListener = new SelectTimeRangeListener() {
        @Override
        public void onSelectTimeRange(String data) {
            mStartDate = data.split("-")[0];
            mEndDate = data.split("-")[1];
            mPreferencesHelper.putLastStartDateAndEndDate(data);
            mCalendarTransactionView.setStartDate(Long.valueOf(mStartDate));
            mCalendarTransactionView.setEndDate(Long.valueOf(mEndDate));
            
            mCalendarTransactionView.setMode(CalendarTransactionView.MODE_CUSTOM);
            
            getData();
        }
    };
    private Listener mCalendarViewListener = new Listener() {
        @Override
        public void onClickTab(String data) {
            mStartDate = data.split("-")[0];
            mEndDate = data.split("-")[1];
            getData();
        }
    };
    
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_main_transaction;
    }
    
    @Override
    protected void initializeDagger() {
        ApplicationComponent applicationComponent = ((MyMoneyApplication) this.getActivity()
                  .getApplication()).getAppComponent();
        
        TransactionComponent transactionComponent = DaggerTransactionComponent.builder()
                  .applicationComponent(applicationComponent)
                  .activityModule(new ActivityModule(this.getActivity()))
                  .transactionModule(new TransactionModule())
                  .build();
        
        transactionComponent.inject(this);
    }
    
    @Override
    protected void initializePresenter() {
        mPresenter = mTransactionPresenter;
        mTransactionPresenter.setView(this);
    }
    
    @Override
    protected void initializeActionBar(View rootView) {
        getActivity().setTitle(R.string.txt_cash_book);
    }
    
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
        mPreferencesHelper = PreferencesHelper.getInstance(this.getContext());
        Wallet currentWallet = mPreferencesHelper.getCurrentWallet();
        if (currentWallet == null) {
            //TODO: OPEN SLASH SCREEN
            Intent intent = new Intent(this.getContext(), WelcomeActivity.class);
            startActivity(intent);
            
        } else {
            mWalletId = currentWallet.getWalletid();
        }
    }
    
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_main_transaction, menu);
        MenuItem menuItem = menu.findItem(R.id.action_view_by_transaction_or_category);
        int viewBy = mPreferencesHelper.getTransactionViewBy();
        if (viewBy == 0) {
            menuItem.setTitle(R.string.action_view_by_transaction);
        } else if (viewBy == 1) {
            menuItem.setTitle(R.string.action_view_by_category);
        }
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        
        switch (item.getItemId()) {
            case R.id.action_jump_to_today:
                mCalendarTransactionView.jump2ToDay();
                return true;
            case R.id.action_view_by_transaction_or_category:
                if (mIsViewByCategory) {
                    mPreferencesHelper.putTransactionViewBy(VIEW_BY_CATEGORY);
                    item.setTitle(R.string.action_view_by_transaction);
                    mIsViewByCategory = false;
                } else {
                    mPreferencesHelper.putTransactionViewBy(VIEW_BY_TRANS);
                    item.setTitle(R.string.action_view_by_category);
                    mIsViewByCategory = true;
                }
                getData();
                return true;
            case R.id.action_view_by_day:
                mPreferencesHelper.putTransactionTimeRange(CalendarTransactionView.MODE_DATE);
                mCalendarTransactionView.setMode(CalendarTransactionView.MODE_DATE);
                getData();
                return true;
            case R.id.action_view_by_week:
                mPreferencesHelper.putTransactionTimeRange(CalendarTransactionView.MODE_WEEK);
                mCalendarTransactionView.setMode(CalendarTransactionView.MODE_WEEK);
                getData();
                return true;
            case R.id.action_view_by_month:
                mPreferencesHelper.putTransactionTimeRange(CalendarTransactionView.MODE_MONTH);
                mCalendarTransactionView.setMode(CalendarTransactionView.MODE_MONTH);
                getData();
                return true;
            case R.id.action_view_by_custom:
                mPreferencesHelper.putTransactionTimeRange(CalendarTransactionView.MODE_CUSTOM);
                
                SelectTimeRangeDialog selectTimeRangeDialog = new SelectTimeRangeDialog(
                          this.getActivity());
                selectTimeRangeDialog.setListener(mSelectTimeRangeListener);
                selectTimeRangeDialog.create().show();
                return true;
            case R.id.action_view_by_all_trans:
                mPreferencesHelper.putTransactionTimeRange(CalendarTransactionView.MODE_ALL_TRANS);
                mCalendarTransactionView.setMode(CalendarTransactionView.MODE_ALL_TRANS);
                getData();
                return true;
            default:
                return false;
        }
    }
    
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mCalendarTransactionView = new CalendarTransactionView(this.getContext());
        String typeTimeRange = mPreferencesHelper.getTransactionTimeRange();
        String lastDate = mPreferencesHelper.getLastStartDateAndEndDate();
        
        try {
            mStartDate = lastDate.split("-")[0];
            mEndDate = lastDate.split("-")[1];
            if (typeTimeRange.equals(CalendarTransactionView.MODE_CUSTOM)) {
                mCalendarTransactionView
                          .setMode(typeTimeRange, Long.valueOf(mStartDate),
                                    Long.valueOf(mEndDate));
            } else {
                mCalendarTransactionView.setMode(typeTimeRange);
            }
        } catch (Exception e) {
            mCalendarTransactionView.setMode(typeTimeRange);
        }
        
        mCalendarTransactionView.setListener(mCalendarViewListener);
        mView.addView(mCalendarTransactionView);
        
        mProgressDialog = new
                  
                  ProgressDialog(this.getActivity());
        mProgressDialog.setMessage(
                  
                  getString(R.string.txt_pls_wait));
        mProgressDialog.setCanceledOnTouchOutside(false);
        
        getData();
    }
    
    @Subscribe
    public void onEvent(ActivityResultEvent event) {
        switch (event.getResultCode()) {
            case ResultCode.ADD_TRANSACTION_RESULT_CODE:
                if (event.getData() != null) {
                    Transaction transaction = (Transaction) event.getData();
                    if (transaction.getCategory() != null &&
                        transaction.getCategory().getTransType().equals("debt_loan")) {
                        addDebtLoan(transaction);
                    } else {
                        getData();
                    }
                }
                
                break;
            case ResultCode.EDIT_TRANSACTION_RESULT_CODE:
            case ResultCode.REMOVE_TRANSACTION_RESULT_CODE:
                getData();
            case ResultCode.CHANGE_WALLET_RESULT_CODE:
                mWalletId = mPreferencesHelper.getCurrentWallet().getWalletid();
                getData();
                break;
            default:
                break;
        }
    }
    
    @OnClick(R.id.btn_add_trans)
    public void addTransaction() {
        Intent intent = new Intent(this.getContext(), ManagerTransactionActivity.class);
        intent.putExtra(ManagerTransactionActivity.EXTRA_ACTION, Action.ACTION_ADD_TRANSACTION);
        intent.putExtra(ManagerTransactionActivity.EXTRA_UPLOAD_IMAGE, false);
        intent.putExtra("date", Long.valueOf(mStartDate));
        startActivity(intent);
    }
    
    @Override
    public void onDestroyView() {
        mTransactionPresenter.unSubscribe();
        EventBus.getDefault().unregister(this);
        super.onDestroyView();
    }
    
    @Override
    public void showAllListTransaction(List<Transaction> list) {
        MyLogger.d("dsfds",list.size());
        if (list.size() > 0) {
            mPreferencesHelper.putLastStartDateAndEndDate(mStartDate + "-" + mEndDate);
        }
        if (mFragment != null) {
            mFramentManager.beginTransaction().remove(mFragment).commitAllowingStateLoss();
        }
        if (mIsViewByCategory) {
            mFragment = new FragmentTransactionByCategory();
            ((FragmentTransactionByCategory)mFragment).setDataTransaction(list);
        } else {
            mFragment = new FragmentTransactionByTime();
            ((FragmentTransactionByTime)mFragment).setDataTransaction(list);
        }
        mFramentManager.beginTransaction().replace(R.id.container, mFragment)
                  .commitAllowingStateLoss();
    }
    
    @Override
    public void onFailure(String message) {
        // Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }
    
    
    @Override
    public void loading(boolean isLoading) {
        if (isLoading) {
            mProgressDialog.show();
            return;
        }
        mProgressDialog.dismiss();
    }
    
    private void getData() {
        MyLogger.d("run");
        mTransactionPresenter.getTransactionByTime(mStartDate, mEndDate, mWalletId);
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
        
        DebtLoanRequest request = new DebtLoanRequest(Action.ACTION_ADD_DEBT_LOAN,
                  new BaseCallBack<Object>() {
                      @Override
                      public void onSuccess(Object value) {
                          mProgressDialog.dismiss();
                          getData();
                      }
                      
                      @Override
                      public void onFailure(Throwable throwable) {
                          mProgressDialog.dismiss();
                          Toast.makeText(getContext(), throwable.getMessage(), Toast.LENGTH_SHORT)
                                    .show();
                      }
                      
                      @Override
                      public void onLoading() {
                          mProgressDialog.show();
                      }
                  }, debtLoan, null, TypeRepository.LOCAL);
        
        mDebtLoanUseCase.subscribe(request);
        
    }
}
