package com.vn.hcmute.team.cortana.mymoney.model;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by kunsubin on 8/22/2017.
 */

public class Wallet implements Parcelable {
    
    public static final Creator<Wallet> CREATOR = new Creator<Wallet>() {
        @Override
        public Wallet createFromParcel(Parcel in) {
            return new Wallet(in);
        }
        
        @Override
        public Wallet[] newArray(int size) {
            return new Wallet[size];
        }
    };
    @SerializedName("wallet_id")
    @Expose
    private String walletid;
    @SerializedName("user_id")
    @Expose
    private String userid;
    @SerializedName("name")
    @Expose
    private String walletName;
    @SerializedName("money")
    @Expose
    private String money;
    @SerializedName("currency")
    @Expose
    private Currencies currencyUnit;
    @SerializedName("icon")
    @Expose
    private String walletImage;
    @SerializedName("archive")
    @Expose
    private boolean archive;
    
    
    public Wallet() {
        this.walletid = "";
        this.userid = "";
        this.walletName = "";
        this.money = "";
        this.currencyUnit = new Currencies();
        this.walletImage = "";
    }
    
    protected Wallet(Parcel in) {
        walletid = in.readString();
        userid = in.readString();
        walletName = in.readString();
        money = in.readString();
        currencyUnit = in.readParcelable(Currencies.class.getClassLoader());
        walletImage = in.readString();
        archive = in.readByte() != 0;
    }
    
    public String getWalletid() {
        return walletid;
    }
    
    public void setWalletid(String walletid) {
        this.walletid = walletid;
    }
    
    public String getUserid() {
        return userid;
    }
    
    public void setUserid(String userid) {
        this.userid = userid;
    }
    
    public String getWalletName() {
        return walletName;
    }
    
    public void setWalletName(String walletName) {
        this.walletName = walletName;
    }
    
    public String getMoney() {
        return money;
    }
    
    public void setMoney(String money) {
        this.money = money;
    }
    
    public Currencies getCurrencyUnit() {
        return currencyUnit;
    }
    
    public void setCurrencyUnit(Currencies currencyUnit) {
        this.currencyUnit = currencyUnit;
    }
    
    public String getWalletImage() {
        return walletImage;
    }
    
    public void setWalletImage(String walletImage) {
        this.walletImage = walletImage;
    }
    
    public boolean isArchive() {
        return archive;
    }
    
    public void setArchive(boolean archive) {
        this.archive = archive;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        
        Wallet wallet = (Wallet) o;
    
        return walletid != null ? walletid.equals(wallet.walletid) : wallet.walletid == null;
    
    }
    
    @Override
    public int hashCode() {
        return walletid != null ? walletid.hashCode() : 0;
    }
    
    @Override
    public int describeContents() {
        return 0;
    }
    
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        
        dest.writeString(walletid);
        dest.writeString(userid);
        dest.writeString(walletName);
        dest.writeString(money);
        dest.writeParcelable(currencyUnit, flags);
        dest.writeString(walletImage);
        dest.writeByte((byte) (archive ? 1 : 0));
    }
}
