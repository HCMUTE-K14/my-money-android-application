package com.vn.hcmute.team.cortana.mymoney.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by infamouSs on 9/10/17.
 */

public class Icon implements Parcelable {
    
    private String id;
    private String image;
    
    public Icon() {
        id = "";
        image = "";
    }
    
    public Icon(String id, String image) {
        this.id = id;
        this.image = image;
    }
    
    protected Icon(Parcel in) {
        id = in.readString();
        image = in.readString();
    }
    
    public static final Creator<Icon> CREATOR = new Creator<Icon>() {
        @Override
        public Icon createFromParcel(Parcel in) {
            return new Icon(in);
        }
        
        @Override
        public Icon[] newArray(int size) {
            return new Icon[size];
        }
    };
    
    @Override
    public int describeContents() {
        return 0;
    }
    
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        
        dest.writeString(id);
        dest.writeString(image);
    }
    
    @Override
    public String toString() {
        return "Icon{" +
               "id='" + id + '\'' +
               ", image='" + image + '\'' +
               '}';
    }
    
    public String getId() {
        return id;
    }
    
    public void setId(String id) {
        this.id = id;
    }
    
    public String getImage() {
        return image;
    }
    
    public void setImage(String image) {
        this.image = image;
    }
}
