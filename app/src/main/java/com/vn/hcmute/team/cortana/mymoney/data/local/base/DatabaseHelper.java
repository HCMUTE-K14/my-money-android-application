package com.vn.hcmute.team.cortana.mymoney.data.local.base;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.AsyncTask;
import com.vn.hcmute.team.cortana.mymoney.utils.logger.MyLogger;
import java.io.File;

/**
 * Created by infamouSs on 9/11/17.
 */

public class DatabaseHelper extends SQLiteOpenHelper {
    
    public static final String TAG = DatabaseHelper.class.getSimpleName();
    
    public static final String DATABASE_NAME = "dbo_my_money.db";
    public static final int VERSION = 1;
    
    public static DatabaseHelper sInstance;
    private Context mContext;
    
    public static DatabaseHelper getInstance(Context context) {
        if (sInstance == null) {
            synchronized (DatabaseHelper.class) {
                if (sInstance == null) {
                    sInstance = new DatabaseHelper(context.getApplicationContext());
                }
            }
        }
        return sInstance;
    }
    
    private DatabaseHelper(Context context) {
        super(context.getApplicationContext(), DATABASE_NAME, null, VERSION, null);
        this.mContext = context;
    }
    
    @Override
    public void onCreate(SQLiteDatabase db) {
       // createNewDatabase();
    }
    
    
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        
    }
    
    public void createNewDatabase() {
        new AsyncTask<Context, Void, Void>() {
            @Override
            protected Void doInBackground(Context... params) {
                
                Context context = params[0].getApplicationContext();
                
                //context.deleteDatabase(DATABASE_NAME);
                DatabaseInitUtil databaseInitUtil = DatabaseInitUtil.getInstance(context);
                
                databaseInitUtil.initDatabase(DatabaseHelper.this.getWritableDatabase());
                
                delay();
                
                MyLogger.d(TAG,
                          "DB was populated in thread " + Thread.currentThread().getName());
                
                return null;
            }
        }.execute(mContext.getApplicationContext());
    }
    
    public boolean doesDatabaseExist() {
        File dbFile = mContext.getApplicationContext().getDatabasePath(DATABASE_NAME);
        return dbFile.exists();
    }
    
    private void delay() {
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            MyLogger.d(TAG, e.getMessage());
        }
    }
}
