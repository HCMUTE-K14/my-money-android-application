package com.vn.hcmute.team.cortana.mymoney.utils;

import android.content.Context;
import com.vn.hcmute.team.cortana.mymoney.ApplicationConfig;
import com.vn.hcmute.team.cortana.mymoney.R;
import java.text.DateFormat;
import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

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
    
    public static String convertTimeMillisToMonthAnhYear(String timeMillis) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(Long.parseLong(timeMillis));
        
        int mYear = calendar.get(Calendar.YEAR);
        int mMonth = calendar.get(Calendar.MONTH) + 1;
        
        if (String.valueOf(mMonth).length() == 1) {
            return "0" + mMonth + "/" + mYear;
        }
        return mMonth + "/" + mYear;
    }
    
    public static int getDayOfWeek(String timeMillis) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(Long.parseLong(timeMillis));
        return calendar.get(Calendar.DAY_OF_WEEK);
    }
    
    public static int getDayOfMonth(String timeMillis) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(Long.parseLong(timeMillis));
        return calendar.get(Calendar.DAY_OF_MONTH);
    }
    
    public static int getMonthOfYear(String timeMillis) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(Long.parseLong(timeMillis));
        
        return calendar.get(Calendar.MONTH);
    }
    
    public static int getYear(String timeMillis) {
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
        calendar.set(Calendar.HOUR, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
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
        return formatDate(date, ApplicationConfig.DEFAULT_DATE_PATTERN);
    }
    
    public static String formatDate(Date date, String pattern) {
        Format formatter = new SimpleDateFormat(pattern);
        return formatter.format(date);
    }
    
    public static String getDayOfWeek(Context context, int dayOfWeek) {
        
        switch (dayOfWeek) {
            case 2:
                return context.getString(R.string.txt_monday);
            case 3:
                return context.getString(R.string.txt_tuesday);
            case 4:
                return context.getString(R.string.txt_wednesday);
            case 5:
                return context.getString(R.string.txt_thursday);
            case 6:
                return context.getString(R.string.txt_friday);
            case 7:
                return context.getString(R.string.txt_saturday);
            case 1:
                return context.getString(R.string.txt_sunday);
            default:
                return "";
        }
    }
    
    public static String getMonthOfYear(Context context, int monthOfYear) {
        switch (monthOfYear) {
            case 0:
                return context.getString(R.string.txt_january);
            case 1:
                return context.getString(R.string.txt_february);
            case 2:
                return context.getString(R.string.txt_march);
            case 3:
                return context.getString(R.string.txt_april);
            case 4:
                return context.getString(R.string.txt_may);
            case 5:
                return context.getString(R.string.txt_june);
            case 6:
                return context.getString(R.string.txt_july);
            case 7:
                return context.getString(R.string.txt_august);
            case 8:
                return context.getString(R.string.txt_september);
            case 9:
                return context.getString(R.string.txt_october);
            case 10:
                return context.getString(R.string.txt_november);
            case 11:
                return context.getString(R.string.txt_december);
            default:
                return "";
        }
    }
    
    public static List<String> getMonthAndYearBetweenRanges(String dateStart, String dateEnd) {
        List<String> mDates = new ArrayList<>();
        
        DateFormat formatDate = new SimpleDateFormat("MM/yyyy");
        
        try {
            Date start = formatDate.parse(dateStart);
            Date end = formatDate.parse(dateEnd);
            while (!start.after(end)) {
                String date = formatDate.format(start);
                mDates.add(date);
                start.setMonth(start.getMonth() % 12 + 1);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        
        return mDates;
    }
    
    public static String dateStringForFile() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyy_hhmmss");
        return sdf.format(calendar.getTime());
    }
}
