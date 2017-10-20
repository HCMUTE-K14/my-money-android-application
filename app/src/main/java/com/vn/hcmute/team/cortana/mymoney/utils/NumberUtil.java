package com.vn.hcmute.team.cortana.mymoney.utils;

import android.content.Context;
import com.vn.hcmute.team.cortana.mymoney.ApplicationConfig;
import com.vn.hcmute.team.cortana.mymoney.data.cache.PreferencesHelper;
import com.vn.hcmute.team.cortana.mymoney.model.RealTimeCurrency;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;

/**
 * Created by infamouSs on 9/9/17.
 */

public class NumberUtil {
    
    public static double round(double value, int places) {
        if (places < 0) {
            throw new IllegalArgumentException();
        }
        
        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }
    
    public static String format(double value, String pattern) {
        DecimalFormat formatter = new DecimalFormat(pattern);
        return formatter.format(value);
    }
    
    public static double exchangeMoney(Context context, String amount, String from, String to) {
        
        PreferencesHelper preferencesHelper = PreferencesHelper
                  .getInstance(context.getApplicationContext());
        
        RealTimeCurrency realTimeCurrency = preferencesHelper.getRealTimeCurrency();
        
        if (realTimeCurrency == null) {
            throw new NullPointerException("");
        }
        double _1_usd_to_ = realTimeCurrency.get(from);
        
        double rate = Double.parseDouble(amount.replaceAll(",", "")) / _1_usd_to_;
        
        double value = rate * realTimeCurrency.get(to);
        
        return NumberUtil.round(value, 3);
    }
    
    public static String formatAmount(String number, String symbolCurrency) {
        double amount = Double.parseDouble(number);

        DecimalFormat formatter = new DecimalFormat(ApplicationConfig.DEFAULT_AMOUNT_PATTERN);
        
        return formatter.format(amount) + " " + symbolCurrency;
    }
    
}
