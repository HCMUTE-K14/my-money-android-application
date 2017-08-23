package com.vn.hcmute.team.cortana.mymoney.utils;

/**
 * Created by infamouSs on 8/11/17.
 */

public class ObjectUtil {
    
    public static Object requireNotNull(Object object) {
        if (isNull(object)) {
            throw new RuntimeException("Object is null");
        }
        
        return object;
    }
    
    public static boolean isNull(Object object) {
        return object == null;
    }
    
}
