package com.vn.hcmute.team.cortana.mymoney.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by infamouSs on 9/9/17.
 */

public class ResultConvert {
    
    @SerializedName("amount")
    @Expose
    public String amount;
    @SerializedName("from")
    @Expose
    public String from;
    @SerializedName("to")
    @Expose
    public String to;
    @SerializedName("value")
    @Expose
    public String value;
    
    
    public String getAmount() {
        return amount;
    }
    
    public void setAmount(String amount) {
        this.amount = amount;
    }
    
    public String getFrom() {
        return from;
    }
    
    public void setFrom(String from) {
        this.from = from;
    }
    
    public String getTo() {
        return to;
    }
    
    public void setTo(String to) {
        this.to = to;
    }
    
    public String getValue() {
        return value;
    }
    
    public void setValue(String value) {
        this.value = value;
    }
}
