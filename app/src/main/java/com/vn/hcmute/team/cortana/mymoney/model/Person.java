package com.vn.hcmute.team.cortana.mymoney.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by kunsubin on 8/23/2017.
 */

public class Person {
    
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
    
    public Person() {
        this.personid = "";
        this.name = "";
        this.describe = "";
        this.userid = "";
    }
    
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
    
    @Override
    public String toString() {
        return "Person{" +
               "personid='" + personid + '\'' +
               ", name='" + name + '\'' +
               ", describe='" + describe + '\'' +
               ", userid='" + userid + '\'' +
               '}';
    }
}
