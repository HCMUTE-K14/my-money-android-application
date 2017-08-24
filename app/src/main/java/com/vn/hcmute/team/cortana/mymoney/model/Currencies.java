package com.vn.hcmute.team.cortana.mymoney.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by kunsubin on 8/22/2017.
 */

public class Currencies {
    
    @SerializedName("cur_id")
    @Expose
    private String curId;
    @SerializedName("cur_code")
    @Expose
    private String curCode;
    @SerializedName("cur_name")
    @Expose
    private String curName;
    @SerializedName("cur_symbol")
    @Expose
    private String curSymbol;
    @SerializedName("cur_display_type")
    @Expose
    private String curDisplayType;
    
    public Currencies() {
        this.curId = "";
        this.curCode = "";
        this.curName = "";
        this.curSymbol = "";
        this.curDisplayType = "";
    }
    
    public String getCurId() {
        return curId;
    }
    
    public void setCurId(String curId) {
        this.curId = curId;
    }
    
    public String getCurCode() {
        return curCode;
    }
    
    public void setCurCode(String curCode) {
        this.curCode = curCode;
    }
    
    public String getCurName() {
        return curName;
    }
    
    public void setCurName(String curName) {
        this.curName = curName;
    }
    
    public String getCurSymbol() {
        return curSymbol;
    }
    
    public void setCurSymbol(String curSymbol) {
        this.curSymbol = curSymbol;
    }
    
    public String getCurDisplayType() {
        return curDisplayType;
    }
    
    public void setCurDisplayType(String curDisplayType) {
        this.curDisplayType = curDisplayType;
    }
}
