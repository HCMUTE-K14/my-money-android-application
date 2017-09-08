package com.vn.hcmute.team.cortana.mymoney.utils;

import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Created by kunsubin on 8/29/2017.
 */

public class DateUtil {
    public static int getDateLeft(long date){
        long currentTime=System.currentTimeMillis();
        return (int)((date-currentTime)/86400000);
    }
    public static String  convertTimeMillisToDate(String timeMillis){
        
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(Long.parseLong(timeMillis));
    
        int mYear = calendar.get(Calendar.YEAR);
        int mMonth = calendar.get(Calendar.MONTH) +1;
        int mDay = calendar.get(Calendar.DAY_OF_MONTH);
        
        String date=mDay+"/"+mMonth+"/"+mYear;
        return date;
    }
    public static long getLongAsDate(int day, int month, int year) {
        Calendar calendar = new GregorianCalendar();
        calendar.set(Calendar.DAY_OF_MONTH, day);
        calendar.set(Calendar.MONTH, month - 1);
        calendar.set(Calendar.YEAR, year);
        return calendar.getTimeInMillis();
    }
}
