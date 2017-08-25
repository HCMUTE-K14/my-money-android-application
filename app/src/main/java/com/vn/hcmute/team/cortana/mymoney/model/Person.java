package com.vn.hcmute.team.cortana.mymoney.model;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by kunsubin on 8/23/2017.
 */

public class Person implements Parcelable {
    
    @SerializedName("personid")
    @Expose
    private String personid;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("describe")
    @Expose
    private String describe;
    @SerializedName("userid")
    @Expose
    private String userid;
    
    private int color;
    
    public Person() {
        this.personid = "";
        this.name = "";
        this.describe = "";
        this.userid = "";
    }
    
    protected Person(Parcel in) {
        personid = in.readString();
        name = in.readString();
        describe = in.readString();
        userid = in.readString();
    }
    
    public static final Creator<Person> CREATOR = new Creator<Person>() {
        @Override
        public Person createFromParcel(Parcel in) {
            return new Person(in);
        }
        
        @Override
        public Person[] newArray(int size) {
            return new Person[size];
        }
    };
    
    public String getPersonid() {
        return personid;
    }
    
    public void setPersonid(String personid) {
        this.personid = personid;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getDescribe() {
        return describe;
    }
    
    public void setDescribe(String describe) {
        this.describe = describe;
    }
    
    public String getUserid() {
        return userid;
    }
    
    public void setUserid(String userid) {
        this.userid = userid;
    }
    
    public int getColor() {
        return color;
    }
    
    public void setColor(int color) {
        this.color = color;
    }
    
    @Override
    public int describeContents() {
        return 0;
    }
    
    @Override
    public void writeToParcel(Parcel dest, int flags) {
    
        dest.writeString(personid);
        dest.writeString(name);
        dest.writeString(describe);
        dest.writeString(userid);
    }
}
