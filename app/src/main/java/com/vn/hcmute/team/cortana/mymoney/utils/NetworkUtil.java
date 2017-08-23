package com.vn.hcmute.team.cortana.mymoney.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by infamouSs on 8/23/17.
 */

public class NetworkUtil {
    
    public static NetworkInfo getInforNetwork(final Context context) {
        ConnectivityManager cm = (ConnectivityManager) context
                  .getSystemService(Context.CONNECTIVITY_SERVICE);
        
        return cm.getActiveNetworkInfo();
    }
    
    public static boolean isNetworkAvailable(final Context context) {
        NetworkInfo info = getInforNetwork(context);
        return (info != null && info.isConnected());
    }
    
}