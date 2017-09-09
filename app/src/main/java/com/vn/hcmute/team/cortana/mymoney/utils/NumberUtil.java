package com.vn.hcmute.team.cortana.mymoney.utils;

import static com.vn.hcmute.team.cortana.mymoney.R.string.amount;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;

/**
 * Created by infamouSs on 9/9/17.
 */

public class NumberUtil {
    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();
        
        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }
    public static String format(double value,String pattern){
        DecimalFormat formatter = new DecimalFormat(pattern);
        return formatter.format(value);
    }
}
