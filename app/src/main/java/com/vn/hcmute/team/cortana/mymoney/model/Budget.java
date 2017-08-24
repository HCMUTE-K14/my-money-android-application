package com.vn.hcmute.team.cortana.mymoney.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by kunsubin on 8/23/2017.
 */

public class Budget {
    
    @SerializedName("budgetId")
    @Expose
    private String budgetId;
    @SerializedName("categoryId")
    @Expose
    private String categoryId;
    @SerializedName("walletid")
    @Expose
    private String walletid;
    @SerializedName("rangeDate")
    @Expose
    private String rangeDate;
    @SerializedName("moneyGoal")
    @Expose
    private String moneyGoal;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("userid")
    @Expose
    private String userid;
    @SerializedName("moneyExpense")
    @Expose
    private String moneyExpense;
    
    public Budget() {
        this.budgetId = "";
        this.categoryId = "";
        this.walletid = "";
        this.rangeDate = "";
        this.moneyGoal = "";
        this.status = "";
        this.userid = "";
        this.moneyExpense = "";
    }
    
    public String getBudgetId() {
        return budgetId;
    }
    
    public void setBudgetId(String budgetId) {
        this.budgetId = budgetId;
    }
    
    public String getCategoryId() {
        return categoryId;
    }
    
    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }
    
    public String getWalletid() {
        return walletid;
    }
    
    public void setWalletid(String walletid) {
        this.walletid = walletid;
    }
    
    public String getRangeDate() {
        return rangeDate;
    }
    
    public void setRangeDate(String rangeDate) {
        this.rangeDate = rangeDate;
    }
    
    public String getMoneyGoal() {
        return moneyGoal;
    }
    
    public void setMoneyGoal(String moneyGoal) {
        this.moneyGoal = moneyGoal;
    }
    
    public String getStatus() {
        return status;
    }
    
    public void setStatus(String status) {
        this.status = status;
    }
    
    public String getUserid() {
        return userid;
    }
    
    public void setUserid(String userid) {
        this.userid = userid;
    }
    
    public String getMoneyExpense() {
        return moneyExpense;
    }
    
    public void setMoneyExpense(String moneyExpense) {
        this.moneyExpense = moneyExpense;
    }
}
