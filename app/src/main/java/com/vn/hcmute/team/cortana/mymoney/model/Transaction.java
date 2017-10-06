package com.vn.hcmute.team.cortana.mymoney.model;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.List;

/**
 * Created by infamouSs on 9/28/17.
 */

public class Transaction implements Parcelable {
    
    @SerializedName("trans_id")
    @Expose
    private String trans_id;
    
    @SerializedName("amount")
    @Expose
    private String amount;
    
    @SerializedName("person")
    @Expose
    private List<Person> person;
    
    @SerializedName("address")
    @Expose
    private String address;
    
    @SerializedName("note")
    @Expose
    private String note;
    
    @SerializedName("image")
    @Expose
    private List<Image> image;
    
    @SerializedName("type")
    @Expose
    private int type; //0:Cho vay, 1:Chit ieu ,2 thu nhap
    
    @SerializedName("category")
    @Expose
    private Category category;
    
    @SerializedName("event")
    @Expose
    private Event event;
    
    @SerializedName("latitude")
    @Expose
    private String latitude;
    
    @SerializedName("longitude")
    @Expose
    private String longitude;
    
    @SerializedName("user_id")
    @Expose
    private String user_id;
    
    @SerializedName("wallet")
    @Expose
    private Wallet wallet;
    
    @SerializedName("date_created")
    @Expose
    private String date_created;
    
    @SerializedName("date_end")
    @Expose
    private String date_end;
    
    @SerializedName("saving")
    @Expose
    private Saving saving;
    
    public Transaction() {
        
    }
    
    
    protected Transaction(Parcel in) {
        trans_id = in.readString();
        amount = in.readString();
        person = in.createTypedArrayList(Person.CREATOR);
        address = in.readString();
        note = in.readString();
        type = in.readInt();
        category = in.readParcelable(Category.class.getClassLoader());
        event = in.readParcelable(Event.class.getClassLoader());
        latitude = in.readString();
        longitude = in.readString();
        user_id = in.readString();
        wallet = in.readParcelable(Wallet.class.getClassLoader());
        date_created = in.readString();
        date_end = in.readString();
        saving = in.readParcelable(Saving.class.getClassLoader());
    }
    
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(trans_id);
        dest.writeString(amount);
        dest.writeTypedList(person);
        dest.writeString(address);
        dest.writeString(note);
        dest.writeInt(type);
        dest.writeParcelable(category, flags);
        dest.writeParcelable(event, flags);
        dest.writeString(latitude);
        dest.writeString(longitude);
        dest.writeString(user_id);
        dest.writeParcelable(wallet, flags);
        dest.writeString(date_created);
        dest.writeString(date_end);
        dest.writeParcelable(saving, flags);
    }
    
    @Override
    public int describeContents() {
        return 0;
    }
    
    public static final Creator<Transaction> CREATOR = new Creator<Transaction>() {
        @Override
        public Transaction createFromParcel(Parcel in) {
            return new Transaction(in);
        }
        
        @Override
        public Transaction[] newArray(int size) {
            return new Transaction[size];
        }
    };
    
    public String getTrans_id() {
        return trans_id;
    }
    
    public void setTrans_id(String trans_id) {
        this.trans_id = trans_id;
    }
    
    public String getAmount() {
        return amount;
    }
    
    public void setAmount(String amount) {
        this.amount = amount;
    }
    
    public List<Person> getPerson() {
        return person;
    }
    
    public void setPerson(List<Person> person) {
        this.person = person;
    }
    
    public String getAddress() {
        return address;
    }
    
    public void setAddress(String address) {
        this.address = address;
    }
    
    public String getNote() {
        return note;
    }
    
    public void setNote(String note) {
        this.note = note;
    }
    
    public List<Image> getImage() {
        return image;
    }
    
    public void setImage(List<Image> image) {
        this.image = image;
    }
    
    public int getType() {
        return type;
    }
    
    public void setType(int type) {
        this.type = type;
    }
    
    public Category getCategory() {
        return category;
    }
    
    public void setCategory(Category category) {
        this.category = category;
    }
    
    public Event getEvent() {
        return event;
    }
    
    public void setEvent(Event event) {
        this.event = event;
    }
    
    public String getLatitude() {
        return latitude;
    }
    
    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }
    
    public String getLongitude() {
        return longitude;
    }
    
    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }
    
    public String getUser_id() {
        return user_id;
    }
    
    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }
    
    public Wallet getWallet() {
        return wallet;
    }
    
    public void setWallet(Wallet wallet) {
        this.wallet = wallet;
    }
    
    public String getDate_created() {
        return date_created;
    }
    
    public void setDate_created(String date_created) {
        this.date_created = date_created;
    }
    
    public String getDate_end() {
        return date_end;
    }
    
    public void setDate_end(String date_end) {
        this.date_end = date_end;
    }
    
    public Saving getSaving() {
        return saving;
    }
    
    public void setSaving(Saving saving) {
        this.saving = saving;
    }
}
