package com.vn.hcmute.team.cortana.mymoney.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by kunsubin on 8/22/2017.
 */

public class Event {
    
    @SerializedName("eventid")
    @Expose
    private String eventid;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("money")
    @Expose
    private String money;
    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("idWallet")
    @Expose
    private String idWallet;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("userid")
    @Expose
    private String userid;
    
    public Event() {
        this.eventid = "";
        this.name = "";
        this.money = "";
        this.date = "";
        this.idWallet = "";
        this.status = "";
        this.userid = "";
    }
    
    public String getEventid() {
        return eventid;
    }
    
    public void setEventid(String eventid) {
        this.eventid = eventid;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getMoney() {
        return money;
    }
    
    public void setMoney(String money) {
        this.money = money;
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
}
