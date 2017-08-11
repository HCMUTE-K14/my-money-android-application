package com.vn.hcmute.team.cortana.mymoney.utils.permission;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by infamouSs on 7/22/17.
 */

public class PermissionRequest {
    
    private static final String TAG = PermissionRequest.class.getSimpleName();
    
    private static Random ramdom;
    
    private int requestCode;
    
    private List<String> permissions;
    
    private PermissionCallBack permissionCallBack;
    
    public PermissionRequest(int requestCode) {
        this.requestCode = requestCode;
    }
    
    public PermissionRequest() {
        if (ramdom == null) {
            ramdom = new Random();
        }
    }
    
    public PermissionRequest(
              ArrayList<String> permissions,
              PermissionCallBack permissionCallBack) {
        if (ramdom == null) {
            ramdom = new Random();
        }
        this.requestCode = ramdom.nextInt(Integer.MAX_VALUE);
        this.permissions = permissions;
        this.permissionCallBack = permissionCallBack;
    }
    
    public int getRequestCode() {
        return requestCode;
    }
    
    public void setRequestCode(int requestCode) {
        this.requestCode = requestCode;
    }
    
    public List<String> getPermissions() {
        return permissions;
    }
    
    public void setPermissions(List<String> permissions) {
        this.permissions = permissions;
    }
    
    public PermissionCallBack getPermissionCallBack() {
        return permissionCallBack;
    }
    
    public void setPermissionCallBack(PermissionCallBack permissionCallBack) {
        this.permissionCallBack = permissionCallBack;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        
        PermissionRequest that = (PermissionRequest) o;
        
        return requestCode == that.requestCode;
        
    }
    
    @Override
    public int hashCode() {
        return requestCode;
    }
}
