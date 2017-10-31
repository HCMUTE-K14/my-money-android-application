package com.vn.hcmute.team.cortana.mymoney.ui.view.calendview.model;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by infamouSs on 10/31/17.
 */

public class DateModel extends BaseModel {
    
    public DateModel() {
        super(BaseModel.TYPE_DATE);
        startDate = Calendar.getInstance().getTimeInMillis();
    }
    
    public DateModel(long startDate, long endDate) {
        super(BaseModel.TYPE_DATE);
        this.startDate = startDate;
        this.endDate = endDate;
    }
    
    public DateModel(long startDate) {
        this(startDate, startDate);
    }
    
    @Override
    public List<String> buildData() {
        
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(this.startDate);
        List<String> list = new ArrayList<>();
        for (int i = -LIMIT_DATE; i <= 0; i++) {
            calendar.add(Calendar.DAY_OF_MONTH, i);
            int day_of_month = calendar.get(Calendar.DAY_OF_MONTH);
            int month = calendar.get(Calendar.MONTH);
            int year = calendar.get(Calendar.YEAR);
            String value = String.format(this.patternDate, day_of_month, month, year);
            
            list.add(value);
        }
        
        return list;
    }
}
