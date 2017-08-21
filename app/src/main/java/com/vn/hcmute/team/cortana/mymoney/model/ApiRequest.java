package com.vn.hcmute.team.cortana.mymoney.model;

/**
 * Created by infamouSs on 8/21/17.
 */

public class ApiRequest {
    private String userid;
    private String token;
    
    public ApiRequest(){
        this.userid="";
        this.token="";
    }
    
    public String getUserid() {
        return userid;
    }
    
    public void setUserid(String userid) {
        this.userid = userid;
    }
    
    public String getToken() {
        return token;
    }
    
    public void setToken(String token) {
        this.token = token;
    }
    
    @Override
    public String toString() {
        return "ApiRequest{" +
               "userid='" + userid + '\'' +
               ", token='" + token + '\'' +
               '}';
    }
}
