package com.vn.hcmute.team.cortana.mymoney.model;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by kunsubin on 8/23/2017.
 */

public class Budget implements Parcelable{
    
    @SerializedName("budgetId")
    @Expose
    private String budgetId;
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
    @SerializedName("wallet")
    @Expose
    private Wallet wallet;
    @SerializedName("category")
    @Expose
    private Category category;
    
    public Budget() {
        this.budgetId = "";
        this.rangeDate = "";
        this.moneyGoal = "";
        this.status = "";
        this.userid = "";
        this.moneyExpense = "";
        this.wallet=new Wallet();
        this.category=new Category();
    }
    
    protected Budget(Parcel in) {
        budgetId = in.readString();
        rangeDate = in.readString();
        moneyGoal = in.readString();
        status = in.readString();
        userid = in.readString();
        moneyExpense = in.readString();
        wallet = in.readParcelable(Wallet.class.getClassLoader());
        category = in.readParcelable(Category.class.getClassLoader());
    }
    
    public static final Creator<Budget> CREATOR = new Creator<Budget>() {
        @Override
        public Budget createFromParcel(Parcel in) {
            return new Budget(in);
        }
        
        @Override
        public Budget[] newArray(int size) {
            return new Budget[size];
        }
    };
    
    public String getBudgetId() {
        return budgetId;
    }
    
    public void setBudgetId(String budgetId) {
        this.budgetId = budgetId;
    }
    public Wallet getWallet() {
        return wallet;
    }
    
    public void setWallet(Wallet wallet) {
        this.wallet = wallet;
    }
    
    public Category getCategory() {
        return category;
    }
    
    public void setCategory(Category category) {
        this.category = category;
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
    
    @Override
    public String toString() {
        return "Budget{" +
               "budgetId='" + budgetId + '\'' +
               ", rangeDate='" + rangeDate + '\'' +
               ", moneyGoal='" + moneyGoal + '\'' +
               ", status='" + status + '\'' +
               ", userid='" + userid + '\'' +
               ", moneyExpense='" + moneyExpense + '\'' +
               '}';
    }
    
    
    @Override
    public int describeContents() {
        return 0;
    }
    
    @Override
    public void writeToParcel(Parcel dest, int flags) {
    
        dest.writeString(budgetId);
        dest.writeString(rangeDate);
        dest.writeString(moneyGoal);
        dest.writeString(status);
        dest.writeString(userid);
        dest.writeString(moneyExpense);
        dest.writeParcelable(wallet, flags);
        dest.writeParcelable(category, flags);
    }
}
