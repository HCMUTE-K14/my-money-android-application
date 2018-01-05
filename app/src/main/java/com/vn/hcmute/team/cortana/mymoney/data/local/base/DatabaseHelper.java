package com.vn.hcmute.team.cortana.mymoney.data.local.base;

import android.content.Context;
import android.os.Handler;
import com.google.gson.Gson;
import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;
import com.vn.hcmute.team.cortana.mymoney.R;
import com.vn.hcmute.team.cortana.mymoney.data.cache.PreferencesHelper;
import com.vn.hcmute.team.cortana.mymoney.model.RealTimeCurrency;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by infamouSs on 9/11/17.
 */

public class DatabaseHelper extends SQLiteAssetHelper {
    
    public static final String TAG = DatabaseHelper.class.getSimpleName();
    
    private static final String DATABASE_NAME = "dbo_my_money.db";
    private static final int VERSION = 1;
    
    protected static DatabaseHelper sInstance = null;
    public Context mContext;
    
    private DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
        mContext = context.getApplicationContext();
        
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                initExchangeRate();
            }
        }, 500);
    }
    
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
    
    private void initExchangeRate() {
        InputStream inputStream = mContext.getResources().openRawResource(R.raw.exchange_rate);
        
        InputStreamReader reader = new InputStreamReader(inputStream);
        
        Gson gson = new Gson();
        RealTimeCurrency realTimeCurrency = gson.fromJson(reader, RealTimeCurrency.class);
        PreferencesHelper preferencesHelper = PreferencesHelper
                  .getInstance(mContext.getApplicationContext());
        
        preferencesHelper.putRealTimeCurrency(realTimeCurrency);
    }
}
