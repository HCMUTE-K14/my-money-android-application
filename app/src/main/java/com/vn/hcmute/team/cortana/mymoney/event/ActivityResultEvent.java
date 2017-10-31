package com.vn.hcmute.team.cortana.mymoney.event;

/**
 * Created by infamouSs on 10/28/17.
 */

public class ActivityResultEvent {
    
    
    private int requestCode;
    private int resultCode;
    private Object data;
    
    
    public ActivityResultEvent(int requestCode, int resultCode, Object data) {
        this.requestCode = requestCode;
        this.resultCode = resultCode;
        this.data = data;
    }
    
    public ActivityResultEvent(Object data) {
        this(0, 0, data);
    }
    
    public ActivityResultEvent(int resultCode, Object data) {
        this.resultCode = resultCode;
        this.data = data;
    }
    
    
    public int getRequestCode() {
        return requestCode;
    }
    
    public void setRequestCode(int requestCode) {
        this.requestCode = requestCode;
    }
    
    public int getResultCode() {
        return resultCode;
    }
    
    public void setResultCode(int resultCode) {
        this.resultCode = resultCode;
    }
    
    public Object getData() {
        return data;
    }
    
    public void setData(Object data) {
        this.data = data;
    }
}
