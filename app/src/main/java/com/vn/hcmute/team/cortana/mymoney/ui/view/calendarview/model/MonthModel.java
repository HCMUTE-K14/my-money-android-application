package com.vn.hcmute.team.cortana.mymoney.ui.view.calendarview.model;

import android.content.Context;
import com.vn.hcmute.team.cortana.mymoney.R;
import java.util.Calendar;
import java.util.LinkedHashMap;

/**
 * Created by infamouSs on 11/3/17.
 */

public class MonthModel extends BaseModel {
    
    private Context mContext;
    
    public MonthModel(Context context) {
        super(context, BaseModel.TYPE_MONTH);
        this.mContext = context;
        
        final int month = Calendar.getInstance().get(Calendar.MONTH);
        final int year = Calendar.getInstance().get(Calendar.YEAR);
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, 1, 0, 0, 0);
        calendar.set(Calendar.MILLISECOND,0);
        startDate = calendar.getTimeInMillis();
    }
    
    
    public MonthModel(Context context, long startDate) {
        this(context);
        this.startDate = startDate;
    }
    
    @Override
    public void buildData() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(this.startDate);
        LinkedHashMap<String, String> map = new LinkedHashMap<>();
        
        for (int i = -LIMIT_MONTH; i <= 0; i++) {
            
            calendar.setTimeInMillis(this.startDate);
            calendar.add(Calendar.MONTH, i);
            final int month = calendar.get(Calendar.MONTH) + 1;
            final int year = calendar.get(Calendar.YEAR);
            String key = "";
            
            long start_date = calendar.getTimeInMillis();
            long end_date = calcEndDate(start_date,
                      calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
            String value = String.valueOf(start_date) + "-" + String.valueOf(end_date);
            if (i == -1) {
                map.put(mContext.getString(R.string.txt_last_month), value);
                continue;
            }
            
            if (i == 0) {
                map.put(mContext.getString(R.string.txt_this_month), value);
                calendar.setTimeInMillis(this.startDate);
                calendar.add(Calendar.MONTH, 1);
                
                long endDateForNextMonth = calcEndDate(calendar.getTimeInMillis(),
                          calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
                String valueForNextMonth = String.valueOf(calendar.getTimeInMillis()) + "-" +
                                           String.valueOf(endDateForNextMonth);
                map.put(mContext.getString(R.string.txt_next_month), valueForNextMonth);
                break;
            }
            
            key = String.format(this.patternDate, month, year);
            
            map.put(key, value);
        }
        this.endDate = calendar.getTimeInMillis();
        this.data.putAll(map);
    }
    
    
    private long calcSecondOfMonth(int day) {
        return SECOND_OF_DAY * day;
    }
    
    private long calcEndDate(long startDate, int day) {
        return startDate + calcSecondOfMonth(day) - ONE_SECOND;
    }
}
