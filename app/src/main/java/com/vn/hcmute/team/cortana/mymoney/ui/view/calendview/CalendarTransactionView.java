package com.vn.hcmute.team.cortana.mymoney.ui.view.calendview;

import android.content.Context;
import android.os.Handler;
import android.support.design.widget.TabLayout;
import android.support.design.widget.TabLayout.OnTabSelectedListener;
import android.support.design.widget.TabLayout.Tab;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;
import com.vn.hcmute.team.cortana.mymoney.R;
import com.vn.hcmute.team.cortana.mymoney.ui.view.calendview.model.AllTransactionModel;
import com.vn.hcmute.team.cortana.mymoney.ui.view.calendview.model.BaseModel;
import com.vn.hcmute.team.cortana.mymoney.ui.view.calendview.model.CustomModel;
import com.vn.hcmute.team.cortana.mymoney.ui.view.calendview.model.DateModel;
import com.vn.hcmute.team.cortana.mymoney.ui.view.calendview.model.MonthModel;
import com.vn.hcmute.team.cortana.mymoney.ui.view.calendview.model.WeekModel;

/**
 * Created by infamouSs on 10/31/17.
 */

public class CalendarTransactionView extends RelativeLayout {
    
    public static final String MODE_DATE = "date";
    public static final String MODE_WEEK = "week";
    public static final String MODE_MONTH = "month";
    public static final String MODE_CUSTOM = "custom";
    public static final String MODE_ALL_TRANS = "all_trans";
    
    private String mode;
    private BaseModel mModel;
    private TabLayout mTabLayout;
    private Context mContext;
    
    private Listener mListener;
    
    private long mStartDate;
    private long mEndDate;
    
    
    public CalendarTransactionView(Context context) {
        super(context, null);
        this.mContext = context;
        initView();
    }
    
    public CalendarTransactionView(Context context, AttributeSet attrs) {
        super(context, attrs, 0);
    }
    
    public CalendarTransactionView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
    
    public Listener getListener() {
        return mListener;
    }
    
    public void setListener(
              Listener listener) {
        mListener = listener;
    }
    
    private void initView() {
        View rootView = inflate(getContext(), R.layout.layout_calendar_view, this);
        
        mTabLayout = (TabLayout) rootView.findViewById(R.id.tab_layout);
    }
    
    public void init(final String mode) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                switch (mode) {
                    case CalendarTransactionView.MODE_DATE:
                        mModel = new DateModel(CalendarTransactionView.this.mContext);
                        break;
                    case CalendarTransactionView.MODE_WEEK:
                        mModel = new WeekModel(CalendarTransactionView.this.mContext);
                        break;
                    case CalendarTransactionView.MODE_MONTH:
                        mModel = new MonthModel(CalendarTransactionView.this.mContext);
                        break;
                    case CalendarTransactionView.MODE_CUSTOM:
                        if (CalendarTransactionView.this.mStartDate == 0L) {
                            Toast.makeText(CalendarTransactionView.this.mContext,
                                      R.string.message_warning_select_start_date,
                                      Toast.LENGTH_SHORT)
                                      .show();
                            return;
                        }
                        if (CalendarTransactionView.this.mEndDate == 0L) {
                            Toast.makeText(CalendarTransactionView.this.mContext,
                                      R.string.message_warning_select_end_date,
                                      Toast.LENGTH_SHORT)
                                      .show();
                            return;
                        }
                        mModel = new CustomModel(CalendarTransactionView.this.mContext,
                                  CalendarTransactionView.this.mStartDate,
                                  CalendarTransactionView.this.mEndDate);
                        break;
                    case CalendarTransactionView.MODE_ALL_TRANS:
                        mModel = new AllTransactionModel(CalendarTransactionView.this.mContext);
                        break;
                    default:
                        mModel = null;
                        break;
                }
                if (mModel != null) {
                    mModel.buildData();
                }
                
                initTabLayout();
            }
        };
        new Handler().postDelayed(runnable, 100);
        
        
    }
    
    private void initTabLayout() {
        mTabLayout.removeAllTabs();
        for (String key : mModel.getData().keySet()) {
            mTabLayout.addTab(mTabLayout.newTab().setText(key));
        }
        jump2ToDay();
        mTabLayout.addOnTabSelectedListener(new OnTabSelectedListener() {
            @Override
            public void onTabSelected(Tab tab) {
                String key = "";
                if (!TextUtils.isEmpty(tab.getText())) {
                    key = tab.getText().toString().trim();
                }
                final String value = mModel.getData().get(key);
                if (mListener != null) {
                    mListener.onClickTab(value);
                }
            }
            
            @Override
            public void onTabUnselected(Tab tab) {
            
            }
            
            @Override
            public void onTabReselected(Tab tab) {
            
            }
        });
    }
    
    public void jump2ToDay() {
        new Handler().postDelayed(
                  new Runnable() {
                      @Override
                      public void run() {
                          if (mTabLayout.getTabCount() >= 3) {
                              Tab tab = mTabLayout.getTabAt(mTabLayout.getTabCount() - 2);
                              if (tab != null) {
                                  tab.select();
                              }
                          }
                      }
                  }, 100);
    }
    
    public String getMode() {
        return mode;
    }
    
    public void setMode(String mode) {
        this.mode = mode;
        init(this.mode);
    }
    
    public void setMode(String mode, long startDate, long endDate) {
        this.mStartDate = startDate;
        this.mEndDate = endDate;
        setMode(mode);
    }
    
    public long getStartDate() {
        return mStartDate;
    }
    
    public void setStartDate(long startDate) {
        mStartDate = startDate;
    }
    
    public long getEndDate() {
        return mEndDate;
    }
    
    public void setEndDate(long endDate) {
        mEndDate = endDate;
    }
    
    public interface Listener {
        
        void onClickTab(String data);
    }
}
