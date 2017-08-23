package com.vn.hcmute.team.cortana.mymoney.ui.tools.galleryloader.model;

import android.support.annotation.NonNull;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by infamouSs on 8/22/17.
 */

public class Folder implements Comparable<Folder> {
    
    private String name;
    private List<ImageGallery> images;
    private long dateTaken;
    
    public Folder(String name, long dateTaken) {
        this.name = name;
        this.images = new ArrayList<>();
    }
    
    public Folder() {
        this.name = "";
        this.images = new ArrayList<>();
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
    
    public long getDateTaken() {
        return dateTaken;
    }
    
    public void setDateTaken(long dateTaken) {
        this.dateTaken = dateTaken;
    }
    
    @Override
    public String toString() {
        return "Folder{" +
               "name='" + name + '\'' +
               '}';
    }
    
    @Override
    public int compareTo(@NonNull Folder o) {
        return name.compareTo(o.getName());
    }
}

