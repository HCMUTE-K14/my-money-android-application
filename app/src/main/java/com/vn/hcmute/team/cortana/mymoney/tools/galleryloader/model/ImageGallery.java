package com.vn.hcmute.team.cortana.mymoney.tools.galleryloader.model;

/**
 * Created by infamouSs on 8/22/17.
 */

public class ImageGallery {
    
    private long id;
    private String name;
    private long datetaken;
    private String path;
    
    public ImageGallery(){
        this.id=0L;
        this.name="";
        this.datetaken=0L;
        this.path="";
    }
    
    public long getId() {
        return id;
    }
    
    public void setId(long id) {
        this.id = id;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public long getDatetaken() {
        return datetaken;
    }
    
    public void setDatetaken(long datetaken) {
        this.datetaken = datetaken;
    }
    
    public String getPath() {
        return path;
    }
    
    public void setPath(String path) {
        this.path = path;
    }
}
