package com.vn.hcmute.team.cortana.mymoney.tools.galleryloader.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by infamouSs on 8/22/17.
 */

public class ImageGallery implements Parcelable{
    
    private long id;
    private String name;
    private String datetaken;
    private String path;
    
    public ImageGallery(long id, String name, String datetaken, String path) {
        this.id = id;
        this.name = name;
        this.datetaken = datetaken;
        this.path = path;
    }
    
    public ImageGallery(){
        this.id=0L;
        this.name="";
        this.datetaken="";
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
    
    public String getDatetaken() {
        return datetaken;
    }
    
    public void setDatetaken(String datetaken) {
        this.datetaken = datetaken;
    }
    
    public String getPath() {
        return path;
    }
    
    public void setPath(String path) {
        this.path = path;
    }
    
    
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ImageGallery that = (ImageGallery) o;
    
        return path != null ? path.equals(that.path) : that.path == null;
    
    }
    
    @Override
    public int hashCode() {
        return path != null ? path.hashCode() : 0;
    }
    
    @Override
    public int describeContents() {
        return 0;
    }
    
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.id);
        dest.writeString(this.name);
        dest.writeString(this.datetaken);
        dest.writeString(this.path);
    }
    
    protected ImageGallery(Parcel in) {
        this.id = in.readLong();
        this.name = in.readString();
        this.datetaken = in.readString();
        this.path = in.readString();
    }
    
    public static final Creator<ImageGallery> CREATOR = new Creator<ImageGallery>() {
        @Override
        public ImageGallery createFromParcel(Parcel source) {
            return new ImageGallery(source);
        }
        
        @Override
        public ImageGallery[] newArray(int size) {
            return new ImageGallery[size];
        }
    };
}
