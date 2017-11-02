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
import com.vn.hcmute.team.cortana.mymoney.R;
import com.vn.hcmute.team.cortana.mymoney.ui.base.BaseActivity;
import com.vn.hcmute.team.cortana.mymoney.utils.DateUtil;
import java.util.Calendar;

/**
 * Created by kunsubin on 11/1/2017.
 */

public class StatisticsActivity extends BaseActivity {
    
    @BindView(R.id.txt_category)
    TextView txt_category;
    @BindView(R.id.txt_time_or_category)
    TextView txt_time_or_category;
    @BindView(R.id.txt_start_time)
    TextView txt_start_time;
    @BindView(R.id.txt_end_time)
    TextView txt_end_time;
    
    private int idCategory=1;
    private int idTimeOrCategory=1;
    private DatePickerDialog mDatePickerDialog;
    private int mYearCurrent;
    
    private int mDay,mMonth,mYear;
    private int mDayEnd,mMonthEnd,mYearEnd;
    
    @Override
    public int getLayoutId() {
        return R.layout.fragment_statistics;
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
        initDate();
    }
    
    /*Area event*/
    @SuppressLint("NewApi")
    @OnClick(R.id.relative_category_expense)
    public void onClickCategoryExpense(View view){
        RelativeLayout relativeLayout=(RelativeLayout)view;
        PopupMenu popupMenu=new PopupMenu(this,relativeLayout);
        popupMenu.getMenuInflater().inflate(R.menu.menu_popup_select_catetory_expense,popupMenu.getMenu());
        
        OnMenuItemClickListener onMenuItemClickListener=new OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()){
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
    public void onClickTimeOrCategory(View view){
        RelativeLayout relativeLayout=(RelativeLayout)view;
        PopupMenu popupMenu=new PopupMenu(this,relativeLayout);
        popupMenu.getMenuInflater().inflate(R.menu.menu_popup_select_time_or_category,popupMenu.getMenu());
    
        OnMenuItemClickListener onMenuItemClickListener=new OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()){
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
    public void onClickStartDate(View view){
        OnDateSetListener onDateSetListener=new OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                if(DateUtil.getLongAsDate(dayOfMonth,month+1,year)<DateUtil.getLongAsDate(mDayEnd,mMonthEnd+1,mYearEnd)){
                    mYear=year;
                    mMonth=month;
                    mDay=dayOfMonth;
                    txt_start_time.setText(DateUtil.getMonthOfYear(getApplication(),mMonth)+" "+mYear);
                }else {
                    alertDiaglog(getString(R.string.txt_select_date_start_end));
                }
                
                
            }
        };
        mDatePickerDialog=new DatePickerDialog(this, onDateSetListener,mYear,mMonth,mDay);
        mDatePickerDialog.show();
    }
    @OnClick(R.id.relative_end_date)
    public void onClickEndDate(View view){
        OnDateSetListener onDateSetListener=new OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                if(DateUtil.getLongAsDate(dayOfMonth,month+1,year)>DateUtil.getLongAsDate(mDay,mMonth+1,mYear)){
                    mYearEnd=year;
                    mMonthEnd=month;
                    mDayEnd=dayOfMonth;
                    txt_end_time.setText(DateUtil.getMonthOfYear(getApplication(),mMonthEnd)+" "+mYearEnd);
                }else {
                    alertDiaglog(getString(R.string.txt_select_date_start_end));
                }
            }
        };
        mDatePickerDialog=new DatePickerDialog(this, onDateSetListener,mYearEnd,mMonthEnd,mDayEnd);
        mDatePickerDialog.show();
    }
    /*Area function*/
    public void selectExpense(MenuItem item){
        txt_category.setText(item.getTitle());
        idCategory=1;
    }
    public void selectIncome(MenuItem item){
        txt_category.setText(item.getTitle());
        idCategory=2;
    }
    public  void selectNetIncome(MenuItem item){
        txt_category.setText(item.getTitle());
        idCategory=3;
    }
    public void selectOverTime(MenuItem item){
        txt_time_or_category.setText(item.getTitle());
        idTimeOrCategory=1;
    }
    public void selectByCategory(MenuItem item){
        txt_time_or_category.setText(item.getTitle());
        idTimeOrCategory=2;
    }
    public void initDate(){
        mDay=1;
        mMonth=0;
        mDayEnd=31;
        mMonthEnd=11;
        
        Calendar calendar=Calendar.getInstance();
        mYearCurrent=calendar.get(Calendar.YEAR);
        txt_start_time.setText(DateUtil.getMonthOfYear(this,0)+" "+mYearCurrent);
        txt_end_time.setText(DateUtil.getMonthOfYear(this,11)+" "+mYearCurrent);
        mYear=mYearCurrent;
        mYearEnd=mYearCurrent;
        
    }
    public void alertDiaglog(String message) {
        AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
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
}
