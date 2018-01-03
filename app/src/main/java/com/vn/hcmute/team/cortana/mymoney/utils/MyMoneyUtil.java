package com.vn.hcmute.team.cortana.mymoney.utils;

import android.app.Activity;
import android.os.Build;
import com.vn.hcmute.team.cortana.mymoney.utils.permission.PermissionHelper;
import com.vn.hcmute.team.cortana.mymoney.utils.permission.PermissionHelper.Permission;

/**
 * Created by infamouSs on 8/22/17.
 */

public class MyMoneyUtil {
    
    public static boolean isMarshmallow() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.M;
    }
    
    public static boolean isHasReadPermission(Activity activity) {
        return PermissionHelper
                  .isHasPermission(activity,
                            PermissionHelper.Permission.READ_EXTERNAL_STORAGE);
    }
    
    public static boolean isHasWritePermission(Activity activity) {
        return PermissionHelper
                  .isHasPermission(activity,
                            Permission.WRITE_EXTERNAL_STORAGE);
    }
    
}
