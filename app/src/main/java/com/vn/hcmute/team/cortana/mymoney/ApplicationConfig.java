package com.vn.hcmute.team.cortana.mymoney;

/**
 * Created by infamouSs on 8/10/17.
 */

public class ApplicationConfig {
    
    public static final int CONNECT_TIMEOUT = 30; //30s
    
    public static final int READ_TIMEOUT = 30;//30s
    
    public static final int CACHE_MAX_AGE = 31536000; //1 year
    
    public static final int CACHE_MAX_STALE = 31536000; //1 year
    
    public static  String DEFAULT_DATE_PATTERN = "EEEE, dd/MM/yyyy";
    
    public static  LANGUAGE DEFAULT_LANGUAGE = LANGUAGE.EN;
    
    public static final String DEFAULT_AMOUNT_PATTERN ="5,000.123 $";

    public enum LANGUAGE {
        EN,
        VI
    }
}
