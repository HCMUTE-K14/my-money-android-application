package com.vn.hcmute.team.cortana.mymoney.tools.galleryloader.model;

import java.util.List;

/**
 * Created by infamouSs on 8/22/17.
 */

public class Folder {
    private String name;
    private List<ImageGallery> images;
    
    
    public Folder(){
        this.name="";
        this.images=null;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public List<ImageGallery> getImages() {
        return images;
    }
    
    public void setImages(
              List<ImageGallery> images) {
        this.images = images;
    }
}
