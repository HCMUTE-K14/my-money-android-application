package com.vn.hcmute.team.cortana.mymoney.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.io.Serializable;

/**
 * Created by kunsubin on 8/23/2017.
 */

public class Saving implements Serializable {
    
    @SerializedName("savingid")
    @Expose
    private String savingid;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("goalMoney")
    @Expose
    private String goalMoney;
    @SerializedName("startMoney")
    @Expose
    private String startMoney;
    @SerializedName("currentMoney")
    @Expose
    private String currentMoney;
    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("idWallet")
    @Expose
    private String idWallet;
    @SerializedName("idCurrencies")
    @Expose
    private String idCurrencies;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("userid")
    @Expose
    private String userid;
    
    public Saving() {
        this.savingid = "";
        this.name = "";
        this.goalMoney = "";
        this.startMoney = "";
        this.currentMoney = "";
        this.date = "";
        this.idWallet = "";
        this.idCurrencies = "";
        this.status = "";
        this.userid = "";
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
    
    public String getIdCurrencies() {
        return idCurrencies;
    }
    
    public void setIdCurrencies(String idCurrencies) {
        this.idCurrencies = idCurrencies;
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
               ", idCurrencies='" + idCurrencies + '\'' +
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
}
