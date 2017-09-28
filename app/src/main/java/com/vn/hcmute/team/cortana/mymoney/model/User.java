package com.vn.hcmute.team.cortana.mymoney.model;

/**
 * Created by infamouSs on 8/10/17.
 */

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class User implements Parcelable {
    
    @SerializedName("user_id")
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
    @SerializedName("facebook_id")
    private String facebook_id;
    
    public User() {
        this.userid = "";
        this.username = "";
        this.password = "";
        this.name = "";
        this.token = "";
        this.email = "";
        this.active = false;
        this.facebook_id = "";
    }
    
    public User(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
    }
    
    protected User(Parcel in) {
        userid = in.readString();
        username = in.readString();
        password = in.readString();
        name = in.readString();
        token = in.readString();
        email = in.readString();
        active = in.readByte() != 0;
        facebook_id = in.readString();
    }
    
    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }
        
        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };
    
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
               "userid='" + userid + '\'' +
               ", username='" + username + '\'' +
               ", password='" + password + '\'' +
               ", name='" + name + '\'' +
               ", token='" + token + '\'' +
               ", email='" + email + '\'' +
               ", active=" + active +
               ", facebook_id='" + facebook_id + '\'' +
               '}';
    }
    
    @Override
    public int describeContents() {
        return 0;
    }
    
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        
        dest.writeString(userid);
        dest.writeString(username);
        dest.writeString(password);
        dest.writeString(name);
        dest.writeString(token);
        dest.writeString(email);
        dest.writeByte((byte) (active ? 1 : 0));
        dest.writeString(facebook_id);
    }
    
    public String getFacebook_id() {
        return facebook_id;
    }
    
    public void setFacebook_id(String facebook_id) {
        this.facebook_id = facebook_id;
    }
}
