package com.vn.hcmute.team.cortana.mymoney.ui.view.calendview.model;

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
        final int day_of_month = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
        final int month = Calendar.getInstance().get(Calendar.MONTH);
        final int year = Calendar.getInstance().get(Calendar.YEAR);
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day_of_month);
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
            final int day_of_month = calendar.get(Calendar.DAY_OF_MONTH);
            final int month = calendar.get(Calendar.MONTH) + 1;
            final int year = calendar.get(Calendar.YEAR);
            String key = "";
            
            if (i == -1) {
                map.put(mContext.getString(R.string.txt_last_month),
                          String.valueOf(calendar.getTimeInMillis()));
                continue;
            }
            
            if (i == 0) {
                map.put(mContext.getString(R.string.txt_this_month),
                          String.valueOf(calendar.getTimeInMillis()));
                calendar.setTimeInMillis(this.startDate);
                calendar.add(Calendar.MONTH, 1);
                map.put(mContext.getString(R.string.txt_next_month),
                          String.valueOf(calendar.getTimeInMillis()));
                break;
            }
            
            key = String.format(this.patternDate, month, year);
            
            map.put(key, String.valueOf(calendar.getTimeInMillis()));
            
        }
        this.endDate = calendar.getTimeInMillis();
        this.data.putAll(map);
    }
}
