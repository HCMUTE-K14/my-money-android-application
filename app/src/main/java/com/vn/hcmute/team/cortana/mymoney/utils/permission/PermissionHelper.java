package com.vn.hcmute.team.cortana.mymoney.utils.permission;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by infamouSs on 7/22/17.
 */

public class PermissionHelper {
    
    private static final String TAG = PermissionHelper.class.getSimpleName();
    private static List<PermissionRequest> permissionRequests = new ArrayList<>();
    
    
    public static boolean isHasPermission(Activity activity, String permission) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return activity.checkSelfPermission(permission)
                   == PackageManager.PERMISSION_GRANTED;
        } else {
            return ContextCompat.checkSelfPermission(activity, permission)
                   == PackageManager.PERMISSION_GRANTED;
        }
    }
    
    public static boolean isHasPermission(Activity activity, String[] permissions) {
        for (String permission : permissions) {
            if (!isHasPermission(activity, permission)) {
                return false;
            }
        }
        return true;
    }
    
    public static void askForPermission(
              Activity activity, String permission,
              PermissionCallBack permissionCallback) {
        askForPermission(activity, new String[]{permission}, permissionCallback);
    }
    
    public static void askForPermission(
              Activity activity, String[] permissions,
              PermissionCallBack permissionCallBack) {
        if (permissionCallBack == null) {
            return;
        }
        if (isHasPermission(activity, permissions)) {
            permissionCallBack.onPermissionGranted();
            return;
        }
        PermissionRequest permissionRequest = new PermissionRequest(
                  new ArrayList<String>(Arrays.asList(permissions)), permissionCallBack);
        permissionRequests.add(permissionRequest);
        
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            activity.requestPermissions(permissions,
                      permissionRequest.getRequestCode());
        } else {
            ActivityCompat.requestPermissions(activity, permissions,
                      permissionRequest.getRequestCode());
        }
    }
    
    public static boolean verifyPermissions(int[] grantResults) {
        for (int result : grantResults) {
            if (result != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }
    
    public static void onRequestPermissionsResult(
              int requestCode, String[] permissions,
              int[] grantResults) {
        PermissionRequest requestResult = new PermissionRequest(requestCode);
        if (permissionRequests.contains(requestResult)) {
            PermissionRequest permissionRequest = permissionRequests
                      .get(permissionRequests.indexOf(requestResult));
            if (verifyPermissions(grantResults)) {
                permissionRequest.getPermissionCallBack().onPermissionGranted();
            } else {
                permissionRequest.getPermissionCallBack().onPermissionDenied();
                
            }
            permissionRequests.remove(requestResult);
        }
    }
    
    public static boolean shouldShowRequestPermissionRationale(
              Activity activity,
              String permissions) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return activity.shouldShowRequestPermissionRationale(permissions);
        } else {
            return ActivityCompat
                      .shouldShowRequestPermissionRationale(activity, permissions);
        }
    }
    
    public class Permission {
        
        public static final String WRITE_EXTERNAL_STORAGE = "android.permission.WRITE_EXTERNAL_STORAGE";
        public static final String READ_EXTERNAL_STORAGE = "android.permission.READ_EXTERNAL_STORAGE";
        public static final String INTERNET = "android.permission.INTERNET";
        public static final String ACCESS_NETWORK_STATE = "android.permission.ACCESS_NETWORK_STATE";
    }
}
