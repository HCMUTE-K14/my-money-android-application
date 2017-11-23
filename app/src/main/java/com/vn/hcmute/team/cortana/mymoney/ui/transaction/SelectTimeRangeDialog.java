package com.vn.hcmute.team.cortana.mymoney.ui.transaction;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;
import com.vn.hcmute.team.cortana.mymoney.R;
import com.vn.hcmute.team.cortana.mymoney.utils.DateUtil;
import java.util.Calendar;

/**
 * Created by infamouSs on 11/11/17.
 */

public class SelectTimeRangeDialog extends AlertDialog.Builder {
    
    private SelectTimeRangeListener mListener;
    
    private TextView mTextViewStartDate;
    private TextView mTextViewEndDate;
    
    private int mDayOfMonth_Start, mMonth_Start, mYear_Start;
    private int mDayOfMonth_End, mMonth_End, mYear_End;
    
    private DatePickerDialog mDatePickerDialogStart, mDatePickerDialogEnd;
    private Context mContext;
    
    public SelectTimeRangeDialog(Activity context) {
        super(context, 0);
        this.mContext = context;
        setTitle(R.string.txt_select_time);
        View view = context.getLayoutInflater().inflate(R.layout.layout_select_custom_date, null);
        mTextViewStartDate = (TextView) view.findViewById(R.id.text_view_start_date);
        mTextViewEndDate = (TextView) view.findViewById(R.id.text_view_end_date);
        
        mTextViewStartDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mDatePickerDialogStart != null) {
                    mDatePickerDialogStart.show();
                }
            }
        });
        mTextViewEndDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mDatePickerDialogEnd != null) {
                    mDatePickerDialogEnd.show();
                }
            }
        });
        
        setView(view);
        setNegativeButton(R.string.txt_done, new OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (mListener != null) {
                    String start = String.valueOf(DateUtil
                              .getLongAsDate(mDayOfMonth_Start, mMonth_Start + 1,
                                        mYear_Start));
                    String end = String.valueOf(DateUtil
                              .getLongAsDate(mDayOfMonth_End, mMonth_End + 1,
                                        mYear_End));
                    mListener.onSelectTimeRange(start + "-" + end);
                }
            }
        });
        
        setPositiveButton(R.string.txt_cancel, new OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                dialog.dismiss();
            }
        });
        
        initDatePickerDialog();
        
    }
    
    
    public SelectTimeRangeDialog(Context context, int themeResId) {
        super(context, themeResId);
    }
    
    public void setListener(SelectTimeRangeListener selectTimeRangeListener) {
        this.mListener = selectTimeRangeListener;
    }
    
    private void initDatePickerDialog() {
        Calendar now = Calendar.getInstance();
        
        mDayOfMonth_Start = now.get(Calendar.DAY_OF_MONTH);
        mMonth_Start = now.get(Calendar.MONTH);
        mYear_Start = now.get(Calendar.YEAR);
        
        mDayOfMonth_End = mDayOfMonth_Start;
        mMonth_End = mMonth_Start;
        mYear_End = mYear_Start;
        
        mDatePickerDialogStart = new DatePickerDialog(mContext,
                  new OnDateSetListener() {
                      @Override
                      public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                          mYear_Start = year;
                          mMonth_Start = month;
                          mDayOfMonth_Start = dayOfMonth;
                          
                          mDatePickerDialogStart
                                    .updateDate(mYear_Start, mMonth_Start, mDayOfMonth_Start);
                          
                          mTextViewStartDate.setText(DateUtil
                                    .formatDate(mYear_Start, mMonth_Start, mDayOfMonth_Start));
                      }
                  }, mYear_Start, mMonth_Start, mDayOfMonth_Start);
        
        mDatePickerDialogEnd = new DatePickerDialog(mContext,
                  new OnDateSetListener() {
                      @Override
                      public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                          mYear_End = year;
                          mMonth_End = month;
                          mDayOfMonth_End = dayOfMonth;
                          
                          mDatePickerDialogEnd
                                    .updateDate(mYear_Start, mMonth_Start, mDayOfMonth_Start);
                          
                          mTextViewEndDate.setText(DateUtil
                                    .formatDate(mYear_End, mMonth_End, mDayOfMonth_End));
                      }
                  }, mYear_End, mMonth_End, mDayOfMonth_End);
        
        String dateNow = DateUtil.formatDate(now.getTime());
        
        mTextViewEndDate.setText(dateNow);
        mTextViewStartDate.setText(dateNow);
    }
    
    public interface SelectTimeRangeListener {
        
        void onSelectTimeRange(String data);
    }
}
