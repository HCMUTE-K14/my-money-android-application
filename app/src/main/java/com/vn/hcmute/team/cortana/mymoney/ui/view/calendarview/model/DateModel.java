package com.vn.hcmute.team.cortana.mymoney.ui.view.calendarview.model;

import android.content.Context;
import com.vn.hcmute.team.cortana.mymoney.R;
import com.vn.hcmute.team.cortana.mymoney.utils.DateUtil;
import java.util.Calendar;
import java.util.LinkedHashMap;

/**
 * Created by infamouSs on 10/31/17.
 */

public class DateModel extends BaseModel {
    
    private Context mContext;
    
    
    public DateModel(Context context) {
        super(context, BaseModel.TYPE_DATE);
        mContext = context;
        final int day_of_month = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
        final int month = Calendar.getInstance().get(Calendar.MONTH);
        final int year = Calendar.getInstance().get(Calendar.YEAR);
        
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day_of_month, 0, 0, 0);
        calendar.set(Calendar.MILLISECOND,0);
        startDate = calendar.getTimeInMillis();
    }
    
    public DateModel(Context context, long startDate) {
        super(context, BaseModel.TYPE_DATE);
        mContext = context;
        this.startDate = startDate;
    }
    
    
    @Override
    public void buildData() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(this.startDate);
        LinkedHashMap<String, String> map = new LinkedHashMap<>();
        long second_of_day_minus_1_second = SECOND_OF_DAY - ONE_SECOND;
        for (int i = -LIMIT_DATE; i <= 0; i++) {
            
            calendar.setTimeInMillis(this.startDate);
            calendar.add(Calendar.DAY_OF_MONTH, i);
            final int day_of_month = calendar.get(Calendar.DAY_OF_MONTH);
            final int month = calendar.get(Calendar.MONTH);
            final int year = calendar.get(Calendar.YEAR);
            String key = "";
            
            if (i == -1) {
                map.put(mContext.getString(R.string.txt_last_day),
                          String.valueOf(calendar.getTimeInMillis()) + "-" +
                          String.valueOf(
                                    calendar.getTimeInMillis() + second_of_day_minus_1_second));
                continue;
            }
            
            if (i == 0) {
                map.put(mContext.getString(R.string.txt_today),
                          String.valueOf(this.startDate) + "-" +
                          String.valueOf(this.startDate + second_of_day_minus_1_second));
                calendar.setTimeInMillis(this.startDate);
                calendar.add(Calendar.DAY_OF_MONTH, 1);
                map.put(mContext.getString(R.string.txt_tomorrow),
                          String.valueOf(calendar.getTimeInMillis()) + "-" +
                          String.valueOf(
                                    calendar.getTimeInMillis() + second_of_day_minus_1_second));
                break;
            }
            
            key = String.format(this.patternDate, day_of_month,
                      DateUtil.getMonthOfYear(this.mContext, month), year);
            String value = String.valueOf(calendar.getTimeInMillis()) + "-" +
                           String.valueOf(
                                     calendar.getTimeInMillis() + second_of_day_minus_1_second);
            map.put(key, value);
            
        }
        this.endDate = calendar.getTimeInMillis();
        
        this.data.putAll(map);
    }
}

