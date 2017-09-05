package com.vn.hcmute.team.cortana.mymoney.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by kunsubin on 8/22/2017.
 */

public class Wallet {
    
    @SerializedName("walletid")
    @Expose
    private String walletid;
    @SerializedName("userid")
    @Expose
    private String userid;
    @SerializedName("walletName")
    @Expose
    private String walletName;
    @SerializedName("money")
    @Expose
    private String money;
    @SerializedName("currencyUnit")
    @Expose
    private String currencyUnit;
    @SerializedName("walletImage")
    @Expose
    private String walletImage;
    
    @SerializedName("archive")
    @Expose
    public boolean archive;
    
    public Wallet() {
        this.walletid = "";
        this.userid = "";
        this.walletName = "";
        this.money = "";
        this.currencyUnit = "";
        this.walletImage = "";
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
    
    public String getCurrencyUnit() {
        return currencyUnit;
    }
    
    public void setCurrencyUnit(String currencyUnit) {
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
        
        if (walletid != null ? !walletid.equals(wallet.walletid) : wallet.walletid != null) {
            return false;
        }
        return walletName != null ? walletName.equals(wallet.walletName)
                  : wallet.walletName == null;
    
    }
    
    @Override
    public int hashCode() {
        int result = walletid != null ? walletid.hashCode() : 0;
        result = 31 * result + (walletName != null ? walletName.hashCode() : 0);
        return result;
    }
}
