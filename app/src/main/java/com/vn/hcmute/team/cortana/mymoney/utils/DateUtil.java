package com.vn.hcmute.team.cortana.mymoney.utils;

import com.vn.hcmute.team.cortana.mymoney.ApplicationConfig;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by kunsubin on 8/29/2017.
 */

public class DateUtil {
    
    public static int getDateLeft(long date) {
        long currentTime = System.currentTimeMillis();
        return (int) ((date - currentTime) / 86400000);
    }
    
    public static String getDateLeftFromTo(String fromDate, String toDate) {
        double fromLongDate = Long.parseLong(fromDate);
        long toLongDate = Long.parseLong(toDate);
        int result = (int) ((toLongDate - fromLongDate) / 86400000);
        return String.valueOf(result);
    }
    
    public static String convertTimeMillisToDate(String timeMillis) {
        
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(Long.parseLong(timeMillis));
        
        int mYear = calendar.get(Calendar.YEAR);
        int mMonth = calendar.get(Calendar.MONTH) + 1;
        int mDay = calendar.get(Calendar.DAY_OF_MONTH);
        
        return mDay + "/" + mMonth + "/" + mYear;
    }
    public static int getDayOfWeek(String timeMillis){
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(Long.parseLong(timeMillis));
        return calendar.get(Calendar.DAY_OF_WEEK);
    }
    public static int getDayOfMonth(String timeMillis){
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(Long.parseLong(timeMillis));
        return calendar.get(Calendar.DAY_OF_MONTH);
    }
    public static int getMonthOfYear(String timeMillis){
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(Long.parseLong(timeMillis));
        
        return calendar.get(Calendar.MONTH);
    }
    public static int getYear(String timeMillis){
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(Long.parseLong(timeMillis));
        return calendar.get(Calendar.YEAR);
    }
    public static String convertTimeMilisToDateNotYear(String timeMillis) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(Long.parseLong(timeMillis));
        
        int mMonth = calendar.get(Calendar.MONTH) + 1;
        int mDay = calendar.get(Calendar.DAY_OF_MONTH);
        
        return mDay + "/" + mMonth;
    }
    
    public static Date timeMilisecondsToDate(String time) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(Long.parseLong(time));
        
        return calendar.getTime();
    }
    
    public static long getLongAsDate(int day, int month, int year) {
        Calendar calendar = new GregorianCalendar();
        calendar.set(Calendar.DAY_OF_MONTH, day);
        calendar.set(Calendar.MONTH, month - 1);
        calendar.set(Calendar.YEAR, year);
        return calendar.getTimeInMillis();
    }
    
    public static String formatDate(int year, int month, int dayOfMonth) {
        String pattern = ApplicationConfig.DEFAULT_DATE_PATTERN;
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, dayOfMonth);
        Format formatter = new SimpleDateFormat(pattern);
        
        return formatter.format(calendar.getTime());
    }
    
    public static String formatDate(Date date) {
        String pattern = ApplicationConfig.DEFAULT_DATE_PATTERN;
        Calendar calendar = Calendar.getInstance();
        Format formatter = new SimpleDateFormat(pattern);
        return formatter.format(date);
    }
    
    public static String getDayOfWeek(int dayOfWeek){
        switch (dayOfWeek){
            case 2:
                return "Monday";
            case 3:
                return "Tuesday";
            case 4:
                return "Wednesday";
            case 5:
                return "Thurday";
            case 6:
                return "Friday";
            case 7:
                return "Saturday";
            case 1:
                return "Sunday";
            default:
                return "";
        }
    }
    public static String getMonthOfYear(int monthOfYear){
        switch (monthOfYear){
            case 0:
                return "January";
            case 1:
                return "February";
            case 2:
                return "March";
            case 3:
                return "April";
            case 4:
                return "May";
            case 5:
                return "June";
            case 6:
                return "July";
            case 7:
                return "August";
            case 8:
                return "September";
            case 9:
                return "October";
            case 10:
                return "November ";
            case 11:
                return "December";
            default:
                return "";
        }
    }
}
