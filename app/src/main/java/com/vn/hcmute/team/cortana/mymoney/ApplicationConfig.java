package com.vn.hcmute.team.cortana.mymoney;

/**
 * Created by infamouSs on 8/10/17.
 */

public class ApplicationConfig {
    
    public static final int CONNECT_TIMEOUT = 30; //30s
    
    public static final int READ_TIMEOUT = 30;//30s
    
    public static final int CACHE_MAX_AGE = 31536000; //1 year
    
    public static final int CACHE_MAX_STALE = 31536000; //1 year
    
    public static final String DEFAULT_DATE_PATTERN = "EEEE, dd/MM/yyyy";
    
    public static final LANGUAGE DEFAULT_LANGUAGE = LANGUAGE.EN;
    
    
    public enum LANGUAGE {
        EN,
        VI
    }
}
