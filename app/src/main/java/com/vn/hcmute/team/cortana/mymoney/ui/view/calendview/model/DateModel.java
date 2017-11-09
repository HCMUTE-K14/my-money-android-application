package com.vn.hcmute.team.cortana.mymoney.ui.view.calendview.model;

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
        calendar.set(year, month, day_of_month);
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
        
        for (int i = -LIMIT_DATE; i <= 0; i++) {
            calendar.setTimeInMillis(this.startDate);
            calendar.add(Calendar.DAY_OF_MONTH, i);
            final int day_of_month = calendar.get(Calendar.DAY_OF_MONTH);
            final int month = calendar.get(Calendar.MONTH);
            final int year = calendar.get(Calendar.YEAR);
            String key = "";
            
            if (i == -1) {
                map.put(mContext.getString(R.string.txt_last_day),
                          String.valueOf(this.startDate));
                continue;
            }
            
            if (i == 0) {
                map.put(mContext.getString(R.string.txt_today),
                          String.valueOf(this.startDate));
                calendar.setTimeInMillis(this.startDate);
                calendar.add(Calendar.DAY_OF_MONTH, 1);
                map.put(mContext.getString(R.string.txt_tomorrow),
                          String.valueOf(calendar.getTimeInMillis()));
                break;
            }
            
            key = String.format(this.patternDate, day_of_month,
                      DateUtil.getMonthOfYear(this.mContext, month), year);
            map.put(key, String.valueOf(calendar.getTimeInMillis()));
            
        }
        this.endDate = calendar.getTimeInMillis();
        this.data.putAll(map);
    }
}

