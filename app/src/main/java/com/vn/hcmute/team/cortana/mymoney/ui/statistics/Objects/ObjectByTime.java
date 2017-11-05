package com.vn.hcmute.team.cortana.mymoney.ui.statistics.Objects;

import com.vn.hcmute.team.cortana.mymoney.model.Transaction;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by kunsubin on 11/4/2017.
 */

public class ObjectByTime {
    private String mDate;
    private List<Transaction> mTransactionList;
    private String mMoneyExpense;
    private String mMoneyIncome;
    
    
    public ObjectByTime(String date,
              List<Transaction> transactionList, String moneyExpense, String moneyIncome) {
        mDate = date;
        mTransactionList = transactionList;
        mMoneyExpense = moneyExpense;
        mMoneyIncome = moneyIncome;
    }
    
    public ObjectByTime() {
        mDate = "";
        mTransactionList = new ArrayList<>();
        mMoneyExpense = "0";
        mMoneyIncome = "0";
    }
    
    public String getDate() {
        return mDate;
    }
    
    public void setDate(String date) {
        mDate = date;
    }
    
    public List<Transaction> getTransactionList() {
        return mTransactionList;
    }
    
    public void setTransactionList(
              List<Transaction> transactionList) {
        mTransactionList = transactionList;
    }
    
    public String getMoneyExpense() {
        return mMoneyExpense;
    }
    
    public void setMoneyExpense(String moneyExpense) {
        mMoneyExpense = moneyExpense;
    }
    
    public String getMoneyIncome() {
        return mMoneyIncome;
    }
    
    public void setMoneyIncome(String moneyIncome) {
        mMoneyIncome = moneyIncome;
    }
    
    @Override
    public String toString() {
        return "ObjectByTime{" +
               "mDate='" + mDate + '\'' +
               ", mTransactionList=" + mTransactionList +
               ", mMoneyExpense='" + mMoneyExpense + '\'' +
               ", mMoneyIncome='" + mMoneyIncome + '\'' +
               '}';
    }
}
