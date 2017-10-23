package com.vn.hcmute.team.cortana.mymoney.ui.event.adapter;

/**
 * Created by kunsubin on 10/20/2017.
 */

public class DateObjectTransaction {
    
    private String mDayOfWeek;
    private String mDayOfMonth;
    private String mMonthOfYear;
    private String mYear;
    private String mMoney;
    private String mCurrencies;
    private String mDate;
    
    public DateObjectTransaction(String dayOfWeek, String dayOfMonth, String monthOfYear,
              String year, String money, String currencies,String date) {
        mDayOfWeek = dayOfWeek;
        mDayOfMonth = dayOfMonth;
        mMonthOfYear = monthOfYear;
        mYear = year;
        mMoney = money;
        mCurrencies = currencies;
        mDate=date;
    }
    
    public DateObjectTransaction() {
        mDayOfWeek = "";
        mDayOfMonth = "";
        mMonthOfYear = "";
        mYear = "";
        mMoney = "";
        mCurrencies = "";
        mDate="";
    }
    
    public String getDayOfWeek() {
        return mDayOfWeek;
    }
    
    public void setDayOfWeek(String dayOfWeek) {
        mDayOfWeek = dayOfWeek;
    }
    
    public String getDayOfMonth() {
        return mDayOfMonth;
    }
    
    public void setDayOfMonth(String dayOfMonth) {
        mDayOfMonth = dayOfMonth;
    }
    
    public String getMonthOfYear() {
        return mMonthOfYear;
    }
    
    public void setMonthOfYear(String monthOfYear) {
        mMonthOfYear = monthOfYear;
    }
    
    public String getYear() {
        return mYear;
    }
    
    public void setYear(String year) {
        mYear = year;
    }
    
    public String getMoney() {
        return mMoney;
    }
    
    public void setMoney(String money) {
        mMoney = money;
    }
    
    public String getCurrencies() {
        return mCurrencies;
    }
    
    public void setCurrencies(String currencies) {
        mCurrencies = currencies;
    }
    public void setDate(String date){
        mDate=date;
    }
    public String getDate(){
        return mDate;
    }
    
}
