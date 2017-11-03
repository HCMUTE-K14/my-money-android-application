package com.vn.hcmute.team.cortana.mymoney.ui.view.calendview.model;

import android.content.Context;
import java.util.Calendar;

/**
 * Created by infamouSs on 11/3/17.
 */

public class CustomModel extends BaseModel {
    
    public CustomModel(Context context, long startDate, long endDate) {
        super(context, BaseModel.TYPE_CUSTOM);
        this.startDate = startDate;
        this.endDate = endDate;
    }
    
    @Override
    public void buildData() {
        Calendar calendarStart = Calendar.getInstance();
        calendarStart.setTimeInMillis(this.startDate);
        
        Calendar calendarEnd = Calendar.getInstance();
        calendarEnd.setTimeInMillis(this.endDate);
        
        final int day_of_month_start = calendarStart.get(Calendar.DAY_OF_MONTH);
        final int month_start = calendarStart.get(Calendar.MONTH);
        final int year_start = calendarStart.get(Calendar.YEAR);
        
        final int day_of_month_end = calendarEnd.get(Calendar.DAY_OF_MONTH);
        final int month_end = calendarEnd.get(Calendar.MONTH);
        final int year_end = calendarEnd.get(Calendar.YEAR);
        
        final int yearNow = Calendar.getInstance().get(Calendar.YEAR);
        String key = "";
        
        if (year_end == year_start && year_end == yearNow) {
            this.patternDate = "%s/%s - %s/%s";
            key = String.format(this.patternDate, day_of_month_start, month_start, day_of_month_end,
                      month_end);
        } else {
            if (year_start == yearNow) {
                this.patternDate = "%s/%s - %s/%s/%s";
                key = String.format(this.patternDate, day_of_month_start, month_start,
                          day_of_month_end, month_end, year_end);
            } else if (year_end == yearNow) {
                this.patternDate = "%s/%s/%s - %s/%s";
                key = String.format(this.patternDate, day_of_month_start, month_start, year_start,
                          day_of_month_end, month_end);
            } else if (year_start != yearNow) {
                this.patternDate = "%s/%s/%s - %s/%s%s";
                key = String.format(this.patternDate, day_of_month_start, month_start, year_start,
                          day_of_month_end, month_end, year_end);
            }
        }
        
        this.data.put(key, calendarStart.getTimeInMillis() + "-" + calendarEnd.getTimeInMillis());
    }
}
