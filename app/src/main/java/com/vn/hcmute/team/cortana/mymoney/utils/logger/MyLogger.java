package com.vn.hcmute.team.cortana.mymoney.utils.logger;

import android.util.Log;
import com.vn.hcmute.team.cortana.mymoney.utils.printer.JsonPrinter;

/**
 * Created by infamouSs on 8/11/17.
 */

public class MyLogger {
    
    public static final int LEVEL_DEBUG = 10;
    public static final int LEVEL_INFO = 20;
    public static final int LEVEL_WARNING = 30;
    public static final int LEVEL_ERROR = 40;
    
    private static final String LOGGER_NAME = "MY_LOGGER";
    private static final String TAG_DEBUG = LOGGER_NAME + " [DEBUG] :";
    private static final String TAG_INFO = LOGGER_NAME + " [INFO] :";
    private static final String TAG_WARNING = LOGGER_NAME + " [WARNING] :";
    private static final String TAG_ERROR = LOGGER_NAME + " [ERROR] :";
    
    private static JsonPrinter jsonPrinter = JsonPrinter.getInstance();
    
    public static void d(String tag, Object obj, boolean isJson) {
        custom(tag, obj, LEVEL_DEBUG, isJson);
    }
    
    public static void d(String tag, Object obj) {
        custom(tag, obj, LEVEL_DEBUG, false);
    }
    
    public static void d(Object obj, boolean isJson) {
        custom(null, obj, LEVEL_DEBUG, isJson);
    }
    
    public static void d(Object obj) {
        custom(null, obj, LEVEL_DEBUG, false);
    }
    
    
    public static void i(String tag, Object obj, boolean isJson) {
        custom(tag, obj, LEVEL_INFO, isJson);
    }
    
    public static void i(String tag, Object obj) {
        custom(tag, obj, LEVEL_INFO, false);
    }
    
    public static void i(Object obj, boolean isJson) {
        custom(null, obj, LEVEL_INFO, isJson);
    }
    
    public static void i(Object obj) {
        custom(null, obj, LEVEL_INFO, false);
    }
    
    
    public static void w(String tag, Object obj, boolean isJson) {
        custom(tag, obj, LEVEL_WARNING, isJson);
    }
    
    public static void w(String tag, Object obj) {
        custom(tag, obj, LEVEL_WARNING, false);
    }
    
    public static void w(Object obj, boolean isJson) {
        custom(null, obj, LEVEL_WARNING, isJson);
    }
    
    public static void w(Object obj) {
        custom(null, obj, LEVEL_WARNING, false);
    }
    
    
    public static void e(String tag, Object obj, boolean isJson) {
        custom(tag, obj, LEVEL_ERROR, isJson);
    }
    
    public static void e(String tag, Object obj) {
        custom(tag, obj, LEVEL_ERROR, false);
    }
    
    public static void e(Object obj, boolean isJson) {
        custom(null, obj, LEVEL_ERROR, isJson);
    }
    
    public static void e(Object obj) {
        custom(null, obj, LEVEL_ERROR, false);
    }
    
    
    public static void custom(String tag, Object obj, int lvl, boolean isJson) {
        StringBuilder tagBuilder = new StringBuilder();
        StringBuilder objBuilder = new StringBuilder();
        if (isJson) {
            objBuilder.append(jsonPrinter.print(obj));
        } else {
            objBuilder.append(obj.toString());
        }
        if (lvl == LEVEL_DEBUG) {
            tagBuilder.append(TAG_DEBUG).append(tag == null ? "" : tag);
            Log.d(tagBuilder.toString(), objBuilder.toString());
            
        } else if (lvl == LEVEL_INFO) {
            tagBuilder.append(TAG_INFO).append(tag == null ? "" : tag);
            Log.i(tagBuilder.toString(), objBuilder.toString());
            
        } else if (lvl == LEVEL_WARNING) {
            tagBuilder.append(TAG_WARNING).append(tag == null ? "" : tag);
            Log.w(tagBuilder.toString(), objBuilder.toString());
            
        } else if (lvl == LEVEL_ERROR) {
            tagBuilder.append(TAG_ERROR).append(tag == null ? "" : tag);
            Log.e(tagBuilder.toString(), objBuilder.toString());
        }
    }
}
