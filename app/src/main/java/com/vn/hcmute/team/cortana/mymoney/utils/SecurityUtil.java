package com.vn.hcmute.team.cortana.mymoney.utils;

import java.util.UUID;

/**
 * Created by infamouSs on 8/25/17.
 */

public class SecurityUtil {
    
    public static String getRandomUUID() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }
}
