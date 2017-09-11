package com.vn.hcmute.team.cortana.mymoney.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by infamouSs on 9/10/17.
 */

public class Icon implements Parcelable{
    private String name;
    
    private String image;
    
    
    public Icon(){
        this.name="";
        this.image="";
    }
    
    protected Icon(Parcel in) {
        name = in.readString();
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
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getImage() {
        return image;
    }
    
    public void setImage(String image) {
        this.image = image;
    }
    
    @Override
    public int describeContents() {
        return 0;
    }
    
    @Override
    public void writeToParcel(Parcel dest, int flags) {
    
        dest.writeString(name);
        dest.writeString(image);
    }
}
