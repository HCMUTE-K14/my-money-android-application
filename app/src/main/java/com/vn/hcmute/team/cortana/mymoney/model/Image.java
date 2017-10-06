package com.vn.hcmute.team.cortana.mymoney.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by infamouSs on 8/21/17.
 */

public class Image {
    
    @SerializedName("image_id")
    @Expose
    private String imageid;
    @SerializedName("url")
    @Expose
    private String url;
    @SerializedName("user_id")
    @Expose
    private String userid;
    @SerializedName("detail")
    @Expose
    private String detail;
    @SerializedName("local_path")
    @Expose
    private String local_path;
    
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
    
    public String getLocal_path() {
        return local_path;
    }
    
    public void setLocal_path(String local_path) {
        this.local_path = local_path;
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
