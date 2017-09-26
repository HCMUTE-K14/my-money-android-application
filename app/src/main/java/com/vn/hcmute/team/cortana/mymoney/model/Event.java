package com.vn.hcmute.team.cortana.mymoney.model;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by kunsubin on 8/22/2017.
 */

public class Event implements Parcelable {
    
    public static final Creator<Event> CREATOR = new Creator<Event>() {
        @Override
        public Event createFromParcel(Parcel in) {
            return new Event(in);
        }

        @Override
        public Event[] newArray(int size) {
            return new Event[size];
        }
    };
    @SerializedName("event_id")
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
    @SerializedName("currencies")
    @Expose
    private Currencies currencies;
    
   
    @SerializedName("icon")
    @Expose
    private String icon;
    
    public Event() {
        this.eventid = "";
        this.name = "";
        this.money = "";
        this.date = "";
        this.idWallet = "";
        this.status = "";
        this.userid = "";
        this.icon="";
        currencies = new Currencies();
    }
    
    protected Event(Parcel in) {
        eventid = in.readString();
        name = in.readString();
        money = in.readString();
        date = in.readString();
        idWallet = in.readString();
        status = in.readString();
        userid = in.readString();
        icon=in.readString();
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
    
    @Override
    public String toString() {
        return "Event{" +
               "eventid='" + eventid + '\'' +
               ", name='" + name + '\'' +
               ", money='" + money + '\'' +
               ", date='" + date + '\'' +
               ", idWallet='" + idWallet + '\'' +
               ", status='" + status + '\'' +
               ", userid='" + userid + '\'' +
               '}';
    }
    @Override
    public int describeContents() {
        return 0;
    }
    
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(eventid);
        dest.writeString(name);
        dest.writeString(money);
        dest.writeString(date);
        dest.writeString(idWallet);
        dest.writeString(status);
        dest.writeString(userid);
        dest.writeString(icon);
        dest.writeParcelable(currencies, flags);
    }
}
