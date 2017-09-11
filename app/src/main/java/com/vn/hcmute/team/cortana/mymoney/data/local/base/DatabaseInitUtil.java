package com.vn.hcmute.team.cortana.mymoney.data.local.base;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import com.vn.hcmute.team.cortana.mymoney.R;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by infamouSs on 9/11/17.
 */

public class DatabaseInitUtil {
    
    protected static DatabaseInitUtil sInstance;
    private Context mContext;
    
    public static DatabaseInitUtil getInstance(Context context) {
        if (sInstance == null) {
            synchronized (DatabaseInitUtil.class) {
                if (sInstance == null) {
                    sInstance = new DatabaseInitUtil(context);
                }
            }
        }
        return sInstance;
    }
    
    private DatabaseInitUtil(Context context) {
        mContext = context.getApplicationContext();
    }
    
    public void initDatabase(SQLiteDatabase db) {
        //executeRawSql(db, 0);
        //insertDataCategory(db);
        insertDataCurrency(db);
    }
    
    public void insertDataCurrency(SQLiteDatabase db) {
        executeRawSql(db, R.raw.tbl_currency);
    }
    
    public void insertDataCategory(SQLiteDatabase db) {
        executeRawSql(db, 0);
    }
    
    private void executeRawSql(SQLiteDatabase db, int drawFile) {
        try {
            InputStream inputStream = mContext.getApplicationContext().getResources()
                      .openRawResource(drawFile);
            String raw = getStringFromInputStream(inputStream);
            String[] queryArr = raw.split(";");
            
            for (String query : queryArr) {
                db.execSQL(query);
            }
        } catch (Exception e) {
            
        }
    }
    
    private String getStringFromInputStream(InputStream is) {
        
        BufferedReader br = null;
        StringBuilder sb = new StringBuilder();
        
        String line;
        try {
            
            br = new BufferedReader(new InputStreamReader(is));
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
            
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        
        return sb.toString();
    }
}
