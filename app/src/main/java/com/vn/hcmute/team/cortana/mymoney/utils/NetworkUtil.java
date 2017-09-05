package com.vn.hcmute.team.cortana.mymoney.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by infamouSs on 8/23/17.
 */

public class NetworkUtil {
    
    public static NetworkInfo getInformationNetwork(final Context context) {
        ConnectivityManager cm = (ConnectivityManager) context
                  .getSystemService(Context.CONNECTIVITY_SERVICE);
        
        return cm.getActiveNetworkInfo();
    }
    
    public static boolean isNetworkAvailable(final Context context) {
        NetworkInfo info = getInformationNetwork(context);
        return (info != null && info.isConnected());
    }
    
    public boolean isOnWiFi(Context context) {
        ConnectivityManager connManager = (ConnectivityManager) context
                  .getSystemService(Context.CONNECTIVITY_SERVICE);
        
        NetworkInfo networkInfo = connManager.getActiveNetworkInfo();
        if (networkInfo == null) {
            return false;  // no network
        }
        if (!networkInfo.isConnected()) {
            return false;
        }
        
        return networkInfo.getType() == ConnectivityManager.TYPE_WIFI;
        
    }
}