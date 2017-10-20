package com.vn.hcmute.team.cortana.mymoney.model;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by kunsubin on 8/23/2017.
 */

public class Saving implements Parcelable {
    
    public static final Creator<Saving> CREATOR = new Creator<Saving>() {
        @Override
        public Saving createFromParcel(Parcel in) {
            return new Saving(in);
        }
        
        @Override
        public Saving[] newArray(int size) {
            return new Saving[size];
        }
    };
    @SerializedName("saving_id")
    @Expose
    private String savingid;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("goal_money")
    @Expose
    private String goalMoney;
    @SerializedName("start_money")
    @Expose
    private String startMoney;
    @SerializedName("current_money")
    @Expose
    private String currentMoney;
    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("wallet_id")
    @Expose
    private String idWallet;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("user_id")
    @Expose
    private String userid;
    @SerializedName("currencies")
    @Expose
    private Currencies currencies;
    @SerializedName("icon")
    @Expose
    private String icon;
    
    public Saving() {
        this.savingid = "";
        this.name = "";
        this.goalMoney = "";
        this.startMoney = "";
        this.currentMoney = "";
        this.date = "";
        this.idWallet = "";
        this.status = "";
        this.userid = "";
        this.icon = "";
        this.currencies = new Currencies();
    }
    
    public Saving(Parcel in) {
        savingid = in.readString();
        name = in.readString();
        goalMoney = in.readString();
        startMoney = in.readString();
        currentMoney = in.readString();
        date = in.readString();
        idWallet = in.readString();
        status = in.readString();
        userid = in.readString();
        icon = in.readString();
        currencies = in.readParcelable(Currencies.class.getClassLoader());
    }
    
    public String getIcon() {
        return icon;
    }
    
    public void setIcon(String icon) {
        this.icon = icon;
    }
    
    public Currencies getCurrencies() {
        return currencies;
    }
    
    public void setCurrencies(Currencies currencies) {
        this.currencies = currencies;
    }
    
    public String getSavingid() {
        return savingid;
    }
    
    public void setSavingid(String savingid) {
        this.savingid = savingid;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getGoalMoney() {
        return goalMoney;
    }
    
    public void setGoalMoney(String goalMoney) {
        this.goalMoney = goalMoney;
    }
    
    public String getStartMoney() {
        return startMoney;
    }
    
    public void setStartMoney(String startMoney) {
        this.startMoney = startMoney;
    }
    
    public String getCurrentMoney() {
        return currentMoney;
    }
    
    public void setCurrentMoney(String currentMoney) {
        this.currentMoney = currentMoney;
    }
    
    public String getDate() {
        return date;
    }
    
    public void setDate(String date) {
        this.date = date;
    }
    
    public String getIdWallet() {
        return idWallet;
    }
    
    public void setIdWallet(String idWallet) {
        this.idWallet = idWallet;
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
    
    @Override
    public String toString() {
        return "Saving{" +
               "savingid='" + savingid + '\'' +
               ", name='" + name + '\'' +
               ", goalMoney='" + goalMoney + '\'' +
               ", startMoney='" + startMoney + '\'' +
               ", currentMoney='" + currentMoney + '\'' +
               ", date='" + date + '\'' +
               ", idWallet='" + idWallet + '\'' +
               ", status='" + status + '\'' +
               ", userid='" + userid + '\'' +
               '}';
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        
        Saving saving = (Saving) o;
        
        return savingid != null ? savingid.equals(saving.savingid) : saving.savingid == null;
        
    }
    
    @Override
    public int hashCode() {
        return savingid != null ? savingid.hashCode() : 0;
    }
    
    @Override
    public int describeContents() {
        return 0;
    }
    
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(savingid);
        dest.writeString(name);
        dest.writeString(goalMoney);
        dest.writeString(startMoney);
        dest.writeString(currentMoney);
        dest.writeString(date);
        dest.writeString(idWallet);
        dest.writeString(status);
        dest.writeString(userid);
        dest.writeString(icon);
        dest.writeParcelable(currencies, flags);
    }
}
