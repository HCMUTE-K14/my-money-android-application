package com.vn.hcmute.team.cortana.mymoney.model;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by infamouSs on 10/18/17.
 */

public class DebtLoan implements Parcelable {
    
    public static final Creator<DebtLoan> CREATOR = new Creator<DebtLoan>() {
        @Override
        public DebtLoan createFromParcel(Parcel in) {
            return new DebtLoan(in);
        }
        
        @Override
        public DebtLoan[] newArray(int size) {
            return new DebtLoan[size];
        }
    };
    @SerializedName("debt_loan_id")
    @Expose
    private String debt_loan_id;
    @SerializedName("note")
    @Expose
    private String note;
    @SerializedName("transaction")
    @Expose
    private Transaction transaction;
    @SerializedName("type")
    @Expose
    private String type; // "debt : no" "loan: cho ai muon";
    @SerializedName("status")
    @Expose
    private int status; // 0,1; 1 da thanh toan
    @SerializedName("user_id")
    @Expose
    private String user_id;
    
    public DebtLoan() {
        debt_loan_id = "";
        note = "";
        transaction = null;
        type = "";
        status = -1;
        user_id = "";
    }
    
    protected DebtLoan(Parcel in) {
        debt_loan_id = in.readString();
        note = in.readString();
        transaction = in.readParcelable(Transaction.class.getClassLoader());
        type = in.readString();
        status = in.readInt();
        user_id = in.readString();
    }
    
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(debt_loan_id);
        dest.writeString(note);
        dest.writeParcelable(transaction, flags);
        dest.writeString(type);
        dest.writeInt(status);
        dest.writeString(user_id);
    }
    
    @Override
    public int describeContents() {
        return 0;
    }
    
    public String getDebt_loan_id() {
        return debt_loan_id;
    }
    
    public void setDebt_loan_id(String debt_loan_id) {
        this.debt_loan_id = debt_loan_id;
    }
    
    public String getNote() {
        return note;
    }
    
    public void setNote(String note) {
        this.note = note;
    }
    
    public Transaction getTransaction() {
        return transaction;
    }
    
    public void setTransaction(Transaction transaction) {
        this.transaction = transaction;
    }
    
    public String getType() {
        return type;
    }
    
    public void setType(String type) {
        this.type = type;
    }
    
    public int getStatus() {
        return status;
    }
    
    public void setStatus(int status) {
        this.status = status;
    }
    
    public String getUser_id() {
        return user_id;
    }
    
    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }
    
    
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        
        DebtLoan debtLoan = (DebtLoan) o;
        
        return debt_loan_id != null ? debt_loan_id.equals(debtLoan.debt_loan_id)
                  : debtLoan.debt_loan_id == null;
        
    }
    
    @Override
    public int hashCode() {
        return debt_loan_id != null ? debt_loan_id.hashCode() : 0;
    }
    
    @Override
    public String toString() {
        return "DebtLoan{" +
               "debt_loan_id='" + debt_loan_id + '\'' +
               ", note='" + note + '\'' +
               ", transaction=" + transaction +
               ", type='" + type + '\'' +
               ", status=" + status +
               ", user_id='" + user_id + '\'' +
               '}';
    }
}
