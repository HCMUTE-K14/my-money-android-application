package com.vn.hcmute.team.cortana.mymoney.model;

/**
 * Created by infamouSs on 8/10/17.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserCredential {
    
    @SerializedName("username")
    @Expose
    private String username;
    
    @SerializedName("password")
    @Expose
    private String password;
    
    public UserCredential() {
        
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
    
}
