package com.vn.hcmute.team.cortana.mymoney.utils;

import android.app.Activity;
import com.vn.hcmute.team.cortana.mymoney.ApplicationConfig;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * Created by infamouSs on 1/2/18.
 */

public class DatabaseUtil {
    
    public static void backup(Activity activity, DatabaseUtil.CallBack callBack, String fileName) {
        try {
            File database = activity.getDatabasePath("dbo_my_money.db");
            
            FileInputStream fis = new FileInputStream(database);
            String outputPath = ApplicationConfig.STORAGE_DIRECTION + "/backup/";
            File outputDirector = new File(outputPath);
            
            if (!outputDirector.exists()) {
                outputDirector.mkdirs();
            }
            
            String outputFileName = outputPath + fileName;
            
            OutputStream output = new FileOutputStream(outputFileName);
            
            byte[] buffer = new byte[1024];
            int length;
            while ((length = fis.read(buffer)) > 0) {
                output.write(buffer, 0, length);
            }
            output.flush();
            output.close();
            fis.close();
            
            callBack.onSuccess();
        } catch (IOException ex) {
            callBack.onError(ex);
        }
    }
    
    public static void restore(Activity activity, String path) {
    
    }
    
    
    public interface CallBack {
        
        void onSuccess();
        
        void onError(Exception ex);
    }
}
