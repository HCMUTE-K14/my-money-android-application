package com.vn.hcmute.team.cortana.mymoney.model;

/**
 * Created by infamouSs on 8/10/17.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class User {
    
    @SerializedName("userid")
    @Expose
    private String userid;
    @SerializedName("username")
    @Expose
    private String username;
    @SerializedName("password")
    @Expose
    private String password;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("token")
    @Expose
    private String token;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("active")
    @Expose
    private boolean active;
    
    public User() {
        this.userid = "";
        this.username = "";
        this.password = "";
        this.name = "";
        this.token = "";
        this.email = "";
        this.active = false;
    }
    
    public String getUserid() {
        return userid;
    }
    
    public void setUserid(String userid) {
        this.userid = userid;
    }
    
    public String getUsername() {
        return username;
    }
    
    public void setUsername(String username) {
        this.username = username;
    }
    
    public String getPassword() {
        return password;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getToken() {
        return token;
    }
    
    public void setToken(String token) {
        this.token = token;
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public Boolean getActive() {
        return active;
    }
    
    public void setActive(Boolean active) {
        this.active = active;
    }
    
    @Override
    public String toString() {
        return "User{" +
               "username='" + username + '\'' +
               ", name='" + name + '\'' +
               ", email='" + email + '\'' +
               ", active=" + active +
               '}';
    }
}
