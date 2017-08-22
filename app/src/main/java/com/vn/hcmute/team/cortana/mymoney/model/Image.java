package com.vn.hcmute.team.cortana.mymoney.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by infamouSs on 8/21/17.
 */

public class Image {
    @SerializedName("imageId")
    @Expose
    private String imageid;
    @SerializedName("imageUrl")
    @Expose
    private String url;
    @SerializedName("userId")
    @Expose
    private String userid;
    @SerializedName("imageDetail")
    @Expose
    private String detail;
    
    public String getImageid() {
        return imageid;
    }
    
    public void setImageid(String imageid) {
        this.imageid = imageid;
    }
    
    public String getUrl() {
        return url;
    }
    
    public void setUrl(String url) {
        this.url = url;
    }
    
    public String getUserid() {
        return userid;
    }
    
    public void setUserid(String userid) {
        this.userid = userid;
    }
    
    public String getDetail() {
        return detail;
    }
    
    public void setDetail(String detail) {
        this.detail = detail;
    }
    
    @Override
    public String toString() {
        return "Image{" +
               "imageid='" + imageid + '\'' +
               ", url='" + url + '\'' +
               ", detail='" + detail + '\'' +
               '}';
    }
}
