package com.vn.hcmute.team.cortana.mymoney.utils;

import java.math.BigDecimal;

/**
 * Created by kunsubin on 9/5/2017.
 */

public class TextUtil {
    
    public static String doubleToString(Double d) {
        if (d == null) {
            return null;
        }
        if (d.isNaN() || d.isInfinite()) {
            return d.toString();
        }
        
        // pre java 8, a value of 0 would yield "0.0" below
        if (d.doubleValue() == 0) {
            return "0";
        }
        return new BigDecimal(d.toString()).stripTrailingZeros().toPlainString();
    }
    
    public static boolean isEmpty(String str) {
        if (str == null || str.length() == 0) {
            return true;
        } else {
            return false;
        }
    }
}
