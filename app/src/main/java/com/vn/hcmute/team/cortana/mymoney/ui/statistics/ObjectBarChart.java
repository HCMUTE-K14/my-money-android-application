package com.vn.hcmute.team.cortana.mymoney.ui.statistics;

import com.vn.hcmute.team.cortana.mymoney.model.Transaction;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by kunsubin on 11/4/2017.
 */

public class ObjectBarChart {
    private String mDate;
    private List<Transaction> mTransactionList;
    private String mMoney;
    
    public ObjectBarChart(String date,
              List<Transaction> transactionList, String money) {
        mDate = date;
        mTransactionList = transactionList;
        mMoney = money;
    }
    public ObjectBarChart(){
        this.mDate="";
        this.mTransactionList=new ArrayList<>();
        this.mMoney="0";
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
    
    public String getMoney() {
        return mMoney;
    }
    
    public void setMoney(String money) {
        mMoney = money;
    }
    
}
