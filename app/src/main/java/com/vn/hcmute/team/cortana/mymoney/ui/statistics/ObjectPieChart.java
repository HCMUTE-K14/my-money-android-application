package com.vn.hcmute.team.cortana.mymoney.ui.statistics;

import com.vn.hcmute.team.cortana.mymoney.model.Category;
import com.vn.hcmute.team.cortana.mymoney.model.Transaction;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by kunsubin on 11/4/2017.
 */

public class ObjectPieChart {
    private Category mCategory;
    private List<Transaction> mTransactionList;
    private String mMoney;
    
    public ObjectPieChart(Category category,
              List<Transaction> transactionList, String money) {
        mCategory = category;
        mTransactionList = transactionList;
        mMoney = money;
    }
    public ObjectPieChart(){
        mCategory=new Category();
        mTransactionList=new ArrayList<>();
        mMoney="0";
    }
    public Category getCategory() {
        return mCategory;
    }
    
    public void setCategory(Category category) {
        mCategory = category;
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
