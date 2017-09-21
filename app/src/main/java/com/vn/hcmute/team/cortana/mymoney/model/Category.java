package com.vn.hcmute.team.cortana.mymoney.model;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.List;

/**
 * Created by infamouSs on 9/15/17.
 */

public class Category implements Parcelable {
    
    public static final String TAG = Category.class.getSimpleName();
    public static final Creator<Category> CREATOR = new Creator<Category>() {
        @Override
        public Category createFromParcel(Parcel in) {
            return new Category(in);
        }
        
        @Override
        public Category[] newArray(int size) {
            return new Category[size];
        }
    };
    @SerializedName("cate_id")
    @Expose
    private String id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("icon")
    @Expose
    private String icon;
    @SerializedName("trans_type")
    @Expose
    private String transType;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("subcategories")
    @Expose
    private List<Category> subcategories = null;
    @SerializedName("userid")
    @Expose
    private String userid;
    private Category parent;
    
    public Category(String id, String name) {
        this.id = id;
        this.name = name;
    }
    
    public Category() {
        id = "";
        name = "";
        icon = "";
        transType = "";
        type = "";
        userid = "";
    }
    
    protected Category(Parcel in) {
        id = in.readString();
        name = in.readString();
        icon = in.readString();
        transType = in.readString();
        type = in.readString();
        subcategories = in.createTypedArrayList(Category.CREATOR);
        userid = in.readString();
        parent = in.readParcelable(Category.class.getClassLoader());
    }
    
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(name);
        dest.writeString(icon);
        dest.writeString(transType);
        dest.writeString(type);
        dest.writeTypedList(subcategories);
        dest.writeString(userid);
        dest.writeParcelable(parent, flags);
    }
    
    @Override
    public int describeContents() {
        return 0;
    }
    
    public String getId() {
        return id;
    }
    
    public void setId(String id) {
        this.id = id;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getIcon() {
        return icon;
    }
    
    public void setIcon(String icon) {
        this.icon = icon;
    }
    
    public String getTransType() {
        return transType;
    }
    
    public void setTransType(String transType) {
        this.transType = transType;
    }
    
    public String getType() {
        return type;
    }
    
    public void setType(String type) {
        this.type = type;
    }
    
    public List<Category> getSubcategories() {
        return subcategories;
    }
    
    public void setSubcategories(
              List<Category> subcategories) {
        this.subcategories = subcategories;
    }
    
    public String getUserid() {
        return userid;
    }
    
    public void setUserid(String userid) {
        this.userid = userid;
    }
    
    public Category getParent() {
        return parent;
    }
    
    public void setParent(Category parent) {
        this.parent = parent;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Category category = (Category) o;
        
        return id.equals(category.id);
        
    }
    
    @Override
    public int hashCode() {
        return id.hashCode();
    }
    
    @Override
    public String toString() {
        return "Category{" +
               "id='" + id + '\'' +
               ", name='" + name + '\'' +
               ", icon='" + icon + '\'' +
               ", transType='" + transType + '\'' +
               ", type='" + type + '\'' +
               ", subcategories=" + subcategories +
               ", userid='" + userid + '\'' +
               '}';
    }
    
}
