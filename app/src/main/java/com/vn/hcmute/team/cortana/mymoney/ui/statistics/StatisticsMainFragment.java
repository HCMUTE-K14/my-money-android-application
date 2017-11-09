package com.vn.hcmute.team.cortana.mymoney.ui.statistics;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.content.DialogInterface;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.PopupMenu;
import android.widget.PopupMenu.OnMenuItemClickListener;
import android.widget.RelativeLayout;
import android.widget.TextView;
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
import com.vn.hcmute.team.cortana.mymoney.model.Transaction;
import com.vn.hcmute.team.cortana.mymoney.model.Wallet;
import com.vn.hcmute.team.cortana.mymoney.ui.base.BaseFragment;
import com.vn.hcmute.team.cortana.mymoney.ui.statistics.fragment.FragmentByCategory;
import com.vn.hcmute.team.cortana.mymoney.ui.statistics.fragment.FragmentByTime;
import com.vn.hcmute.team.cortana.mymoney.ui.transaction.TransactionContract;
import com.vn.hcmute.team.cortana.mymoney.ui.transaction.TransactionPresenter;
import com.vn.hcmute.team.cortana.mymoney.utils.DateUtil;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import javax.inject.Inject;

/**
 * Created by kunsubin on 11/5/2017.
 */

public class StatisticsMainFragment extends BaseFragment implements TransactionContract.View {
    
    @BindView(R.id.txt_category)
    TextView txt_category;
    @BindView(R.id.txt_time_or_category)
    TextView txt_time_or_category;
    @BindView(R.id.txt_start_time)
    TextView txt_start_time;
    @BindView(R.id.txt_end_time)
    TextView txt_end_time;
    @Inject
    TransactionPresenter mTransactionPresenter;
    @Inject
    PreferencesHelper mPreferencesHelper;
    private int idCategory = 1;
    private int idTimeOrCategory = 1;
    private DatePickerDialog mDatePickerDialog;
    private int mYearCurrent;
    private int mDay, mMonth, mYear;
    private int mDayEnd, mMonthEnd, mYearEnd;
    private String mIdWallet;
    private Wallet mWallet;
    private FragmentByTime mFragmentByTime;
    private FragmentByCategory mFragmentByCategory;
    private List<String> mStringDates;
    
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_statistics;
    }
    
    @Override
    protected void initializeDagger() {
        ApplicationComponent applicationComponent = ((MyMoneyApplication) getActivity()
                  .getApplication())
                  .getAppComponent();
        TransactionComponent transactionComponent = DaggerTransactionComponent.builder()
                  .applicationComponent(applicationComponent)
                  .activityModule(new ActivityModule(getActivity()))
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
        getActivity().setTitle(getString(R.string.txt_statistics));
    }
    
    @Override
    public void onDestroy() {
        mTransactionPresenter.unSubscribe();
        super.onDestroy();
    }
    
    @Override
    protected void initialize() {
        initDate();
        // showChart(null);
        mStringDates = new ArrayList<>();
        mStringDates.clear();
        mStringDates = DateUtil.getMonthAndYearBetweenRanges((mMonth + 1) + "/" + mYear,
                  (mMonthEnd + 1) + "/" + mYearEnd);
        mIdWallet = mPreferencesHelper.getCurrentWallet().getWalletid();
        mWallet = mPreferencesHelper.getCurrentWallet();
        mTransactionPresenter
                  .getTransactionByTime(DateUtil.getLongAsDate(mDay, (mMonth + 1), mYear) + "",
                            DateUtil.getLongAsDate(mDayEnd, (mMonthEnd + 1), mYearEnd) + "",
                            mIdWallet);
        
        
    }
    
    @Override
    public void showAllListTransaction(List<Transaction> list) {
        showChart(list);
    }
    
    @Override
    public void onFailure(String message) {
    
    }
    
    @Override
    public void loading(boolean isLoading) {
    
    }
    
    /*Area event*/
    @SuppressLint("NewApi")
    @OnClick(R.id.relative_category_expense)
    public void onClickCategoryExpense(View view) {
        RelativeLayout relativeLayout = (RelativeLayout) view;
        PopupMenu popupMenu = new PopupMenu(getActivity(), relativeLayout);
        popupMenu.getMenuInflater()
                  .inflate(R.menu.menu_popup_select_catetory_expense, popupMenu.getMenu());
        
        OnMenuItemClickListener onMenuItemClickListener = new OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.expense:
                        selectExpense(item);
                        break;
                    case R.id.income:
                        selectIncome(item);
                        break;
                    case R.id.net_income:
                        selectNetIncome(item);
                        break;
                    default:
                        break;
                }
                return true;
            }
        };
        popupMenu.setOnMenuItemClickListener(onMenuItemClickListener);
        popupMenu.show();
    }
    
    @OnClick(R.id.relative_time_or_category)
    public void onClickTimeOrCategory(View view) {
        RelativeLayout relativeLayout = (RelativeLayout) view;
        PopupMenu popupMenu = new PopupMenu(getActivity(), relativeLayout);
        popupMenu.getMenuInflater()
                  .inflate(R.menu.menu_popup_select_time_or_category, popupMenu.getMenu());
        
        OnMenuItemClickListener onMenuItemClickListener = new OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.over_time:
                        selectOverTime(item);
                        break;
                    case R.id.by_category:
                        selectByCategory(item);
                        break;
                    default:
                        break;
                }
                return true;
            }
        };
        popupMenu.setOnMenuItemClickListener(onMenuItemClickListener);
        popupMenu.show();
    }
    
    @OnClick(R.id.relative_start_date)
    public void onClickStartDate(View view) {
        OnDateSetListener onDateSetListener = new OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                if (DateUtil.getLongAsDate(dayOfMonth, month + 1, year) <
                    DateUtil.getLongAsDate(mDayEnd, mMonthEnd + 1, mYearEnd)) {
                    mYear = year;
                    mMonth = month;
                    mDay = dayOfMonth;
                    txt_start_time.setText(DateUtil.getMonthOfYear(getActivity(), mMonth) + " " +
                                           mYear);
                    mStringDates.clear();
                    mStringDates = DateUtil.getMonthAndYearBetweenRanges((mMonth + 1) + "/" + mYear,
                              (mMonthEnd + 1) + "/" + mYearEnd);
                    mTransactionPresenter.getTransactionByTime(
                              DateUtil.getLongAsDate(mDay, (mMonth + 1), mYear) + "",
                              DateUtil.getLongAsDate(mDayEnd, (mMonthEnd + 1), mYearEnd) + "",
                              mIdWallet);
                } else {
                    alertDiaglog(getString(R.string.txt_select_date_start_end));
                }
                
                
            }
        };
        mDatePickerDialog = new DatePickerDialog(getActivity(), onDateSetListener, mYear, mMonth,
                  mDay);
        mDatePickerDialog.show();
    }
    
    @OnClick(R.id.relative_end_date)
    public void onClickEndDate(View view) {
        OnDateSetListener onDateSetListener = new OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                if (DateUtil.getLongAsDate(dayOfMonth, month + 1, year) >
                    DateUtil.getLongAsDate(mDay, mMonth + 1, mYear)) {
                    mYearEnd = year;
                    mMonthEnd = month;
                    mDayEnd = dayOfMonth;
                    txt_end_time
                              .setText(DateUtil.getMonthOfYear(getActivity(), mMonthEnd) + " " +
                                       mYearEnd);
                    mStringDates.clear();
                    mStringDates = DateUtil.getMonthAndYearBetweenRanges((mMonth + 1) + "/" + mYear,
                              (mMonthEnd + 1) + "/" + mYearEnd);
                    mTransactionPresenter.getTransactionByTime(
                              DateUtil.getLongAsDate(mDay, (mMonth + 1), mYear) + "",
                              DateUtil.getLongAsDate(mDayEnd, (mMonthEnd + 1), mYearEnd) + "",
                              mIdWallet);
                } else {
                    alertDiaglog(getString(R.string.txt_select_date_start_end));
                }
            }
        };
        mDatePickerDialog = new DatePickerDialog(getActivity(), onDateSetListener, mYearEnd,
                  mMonthEnd,
                  mDayEnd);
        mDatePickerDialog.show();
    }
    
    /*Area function*/
    public void selectExpense(MenuItem item) {
        txt_category.setText(item.getTitle());
        idCategory = 1;
        mTransactionPresenter
                  .getTransactionByTime(DateUtil.getLongAsDate(mDay, (mMonth + 1), mYear) + "",
                            DateUtil.getLongAsDate(mDayEnd, (mMonthEnd + 1), mYearEnd) + "",
                            mIdWallet);
    }
    
    public void selectIncome(MenuItem item) {
        txt_category.setText(item.getTitle());
        idCategory = 2;
        mTransactionPresenter
                  .getTransactionByTime(DateUtil.getLongAsDate(mDay, (mMonth + 1), mYear) + "",
                            DateUtil.getLongAsDate(mDayEnd, (mMonthEnd + 1), mYearEnd) + "",
                            mIdWallet);
    }
    
    public void selectNetIncome(MenuItem item) {
        txt_category.setText(item.getTitle());
        idCategory = 3;
        mTransactionPresenter
                  .getTransactionByTime(DateUtil.getLongAsDate(mDay, (mMonth + 1), mYear) + "",
                            DateUtil.getLongAsDate(mDayEnd, (mMonthEnd + 1), mYearEnd) + "",
                            mIdWallet);
    }
    
    public void selectOverTime(MenuItem item) {
        txt_time_or_category.setText(item.getTitle());
        idTimeOrCategory = 1;
        mTransactionPresenter
                  .getTransactionByTime(DateUtil.getLongAsDate(mDay, (mMonth + 1), mYear) + "",
                            DateUtil.getLongAsDate(mDayEnd, (mMonthEnd + 1), mYearEnd) + "",
                            mIdWallet);
    }
    
    public void selectByCategory(MenuItem item) {
        txt_time_or_category.setText(item.getTitle());
        idTimeOrCategory = 2;
        mTransactionPresenter
                  .getTransactionByTime(DateUtil.getLongAsDate(mDay, (mMonth + 1), mYear) + "",
                            DateUtil.getLongAsDate(mDayEnd, (mMonthEnd + 1), mYearEnd) + "",
                            mIdWallet);
    }
    
    public void initDate() {
        mDay = 1;
        mMonth = 0;
        mDayEnd = 31;
        mMonthEnd = 11;
        
        Calendar calendar = Calendar.getInstance();
        mYearCurrent = calendar.get(Calendar.YEAR);
        txt_start_time.setText(DateUtil.getMonthOfYear(getActivity(), 0) + " " + mYearCurrent);
        txt_end_time.setText(DateUtil.getMonthOfYear(getActivity(), 11) + " " + mYearCurrent);
        mYear = mYearCurrent;
        mYearEnd = mYearCurrent;
        
    }
    
    public void alertDiaglog(String message) {
        AlertDialog.Builder builder1 = new AlertDialog.Builder(getActivity());
        builder1.setMessage(message);
        builder1.setCancelable(true);
        builder1.setPositiveButton(
                  getString(R.string.txt_ok),
                  new DialogInterface.OnClickListener() {
                      public void onClick(DialogInterface dialog, int id) {
                          dialog.cancel();
                      }
                  });
        
        AlertDialog alert11 = builder1.create();
        alert11.show();
    }
    
    public void showChart(List<Transaction> list) {
        if (mFragmentByTime != null) {
            getActivity().getSupportFragmentManager().beginTransaction().remove(mFragmentByTime)
                      .commit();
        }
        if (mFragmentByCategory != null) {
            getActivity().getSupportFragmentManager().beginTransaction().remove(mFragmentByCategory)
                      .commit();
        }
        if (idTimeOrCategory == 1) {
            mFragmentByTime = new FragmentByTime(list, idCategory, mWallet, mStringDates);
            getActivity().getSupportFragmentManager().beginTransaction()
                      .replace(R.id.view_fragment, mFragmentByTime).commit();
            return;
        }
        if (idTimeOrCategory == 2) {
            if (idCategory == 3) {
                mFragmentByTime = new FragmentByTime(list, idCategory, mWallet, mStringDates);
                getActivity().getSupportFragmentManager().beginTransaction()
                          .replace(R.id.view_fragment, mFragmentByTime).commit();
                return;
            }
            mFragmentByCategory = new FragmentByCategory(list, idCategory, mWallet);
            getActivity().getSupportFragmentManager().beginTransaction()
                      .replace(R.id.view_fragment, mFragmentByCategory).commit();
            
            return;
        }
    }
}
