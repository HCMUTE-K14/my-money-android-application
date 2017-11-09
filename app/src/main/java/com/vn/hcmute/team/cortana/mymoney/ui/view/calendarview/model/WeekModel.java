package com.vn.hcmute.team.cortana.mymoney.ui.view.calendarview.model;

import android.content.Context;
import com.vn.hcmute.team.cortana.mymoney.R;
import java.util.Calendar;
import java.util.LinkedHashMap;

/**
 * Created by infamouSs on 11/3/17.
 */

public class WeekModel extends BaseModel {
    
    public WeekModel(Context context) {
        super(context, BaseModel.TYPE_WEEK);
        final int day_of_month = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
        final int month = Calendar.getInstance().get(Calendar.MONTH);
        final int year = Calendar.getInstance().get(Calendar.YEAR);
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day_of_month);
        
        startDate = calendar.getTimeInMillis();
    }
    
    
    public WeekModel(Context context, long startDate) {
        this(context);
        this.startDate = startDate;
    }
    
    @Override
    public void buildData() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(this.startDate);
        LinkedHashMap<String, String> map = new LinkedHashMap<>();
        
        for (int i = -LIMIT_WEEK; i <= 0; i++) {
            calendar.setTimeInMillis(this.startDate);
            calendar.add(Calendar.WEEK_OF_MONTH, i);
            
            if (i == -1) {
                map.put(mContext.getString(R.string.txt_last_week), calcWeek(calendar)[1]);
                continue;
            }
            
            if (i == 0) {
                map.put(mContext.getString(R.string.txt_this_week), calcWeek(calendar)[1]);
                calendar.setTimeInMillis(this.startDate);
                calendar.add(Calendar.WEEK_OF_MONTH, 1);
                map.put(mContext.getString(R.string.txt_next_week), calcWeek(calendar)[1]);
                
                break;
            }
            String[] result = calcWeek(calendar);
            
            map.put(result[0], result[1]);
            
        }
        this.endDate = calendar.getTimeInMillis();
        this.data.putAll(map);
    }
    
    private String[] calcWeek(Calendar calendar) {
        StringBuilder value = new StringBuilder();
        String key = "";
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK) - calendar.getFirstDayOfWeek();
        calendar.add(Calendar.DAY_OF_MONTH, -dayOfWeek);
        int day_of_month_start_week = calendar.get(Calendar.DAY_OF_MONTH);
        int month_start_week = calendar.get(Calendar.MONTH) + 1;
        value.append(calendar.getTimeInMillis())
                  .append("-");
        
        calendar.add(Calendar.DAY_OF_WEEK, 6);
        
        int day_of_month_end_week = calendar.get(Calendar.DAY_OF_MONTH);
        int month_end_week = calendar.get(Calendar.MONTH) + 1;
        value.append(calendar.getTimeInMillis());
        
        key = String.format(this.patternDate, day_of_month_start_week, month_start_week,
                  day_of_month_end_week, month_end_week);
        
        return new String[]{key, value.toString()};
    }
}
