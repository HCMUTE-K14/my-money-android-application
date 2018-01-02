package com.vn.hcmute.team.cortana.mymoney;

import android.os.Environment;
import java.io.File;

/**
 * Created by infamouSs on 8/10/17.
 */

public class ApplicationConfig {
    
    public static String STORAGE_DIRECTION;
    
    
    static {
        File store = Environment.getExternalStorageDirectory();
        File directory = new File(store.getAbsolutePath() + "/MyMoney");
        if (directory.exists()) {
            directory.mkdirs();
        }
        STORAGE_DIRECTION = directory.getAbsolutePath();
    }
    
    public static final int CONNECT_TIMEOUT = 30; //30s
    
    public static final int READ_TIMEOUT = 30;//30s
    
    public static final int CACHE_MAX_AGE = 31536000; //1 year
    
    public static final int CACHE_MAX_STALE = 31536000; //1 year
    public static final String DEFAULT_AMOUNT_PATTERN = "#,###.000";
    public static final String DEFAULT_DATE_PATTERN = "EEEE, dd/MM/yyyy";
    
    public enum LANGUAGE {
        EN("English (Tiếng Anh)"),
        VI("Vietnamese (Tiếng Việt)");
        
        private final String mString;
        
        LANGUAGE(String str) {
            this.mString = str;
        }
        
        public String getData() {
            return this.mString;
        }
    }
}
