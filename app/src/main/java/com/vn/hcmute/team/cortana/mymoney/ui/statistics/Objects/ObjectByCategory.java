package com.vn.hcmute.team.cortana.mymoney.ui.statistics.Objects;

import android.os.Parcel;
import android.os.Parcelable;
import com.vn.hcmute.team.cortana.mymoney.model.Category;
import com.vn.hcmute.team.cortana.mymoney.model.Transaction;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by kunsubin on 11/4/2017.
 */

public class ObjectByCategory implements Parcelable{
    private Category mCategory;
    private List<Transaction> mTransactionList;
    private String mMoneyExpense;
    private String mMoneyIncome;
    
    public ObjectByCategory(Category category,
              List<Transaction> transactionList, String moneyExpense, String moneyIncome) {
        mCategory = category;
        mTransactionList = transactionList;
        mMoneyExpense = moneyExpense;
        mMoneyIncome = moneyIncome;
    }
    
    public ObjectByCategory() {
        mCategory = new Category();
        mTransactionList = new ArrayList<>();
        mMoneyExpense = "0";
        mMoneyIncome = "0";
    }
    
    protected ObjectByCategory(Parcel in) {
        mCategory = in.readParcelable(Category.class.getClassLoader());
        mTransactionList = in.createTypedArrayList(Transaction.CREATOR);
        mMoneyExpense = in.readString();
        mMoneyIncome = in.readString();
    }
    
    public static final Creator<ObjectByCategory> CREATOR = new Creator<ObjectByCategory>() {
        @Override
        public ObjectByCategory createFromParcel(Parcel in) {
            return new ObjectByCategory(in);
        }
        
        @Override
        public ObjectByCategory[] newArray(int size) {
            return new ObjectByCategory[size];
        }
    };
    
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
        return "ObjectByCategory{" +
               "mCategory=" + mCategory +
               ", mTransactionList=" + mTransactionList +
               ", mMoneyExpense='" + mMoneyExpense + '\'' +
               ", mMoneyIncome='" + mMoneyIncome + '\'' +
               '}';
    }
    
    @Override
    public int describeContents() {
        return 0;
    }
    
    @Override
    public void writeToParcel(Parcel parcel, int i) {
    
        parcel.writeParcelable(mCategory, i);
        parcel.writeTypedList(mTransactionList);
        parcel.writeString(mMoneyExpense);
        parcel.writeString(mMoneyIncome);
    }
}
