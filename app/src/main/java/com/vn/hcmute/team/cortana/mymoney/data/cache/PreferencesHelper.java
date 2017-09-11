package com.vn.hcmute.team.cortana.mymoney.data.cache;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import com.google.gson.Gson;
import com.vn.hcmute.team.cortana.mymoney.model.RealTimeCurrency;
import com.vn.hcmute.team.cortana.mymoney.model.User;
import com.vn.hcmute.team.cortana.mymoney.model.Wallet;
import javax.inject.Inject;

/**
 * Created by infamouSs on 8/21/17.
 */

public class PreferencesHelper {
    
    public static final String TAG = PreferencesHelper.class.getSimpleName();
    
    private static final String PREF_NAME = "my_money_pref";
    
    private final String PREF_USER_ID = "PREF_USER_ID";
    private final String PREF_USER_TOKEN = "PREF_USER_TOKEN";
    private final String PREF_CURRENT_USER = "PREF_CURRENT_USER";
    private final String PREF_CURRENT_WALLET = "PREF_CURRENT_WALLET";
    private final String PREF_CURRENT_REAL_TIME_CURRENCY = "PREF_CURRENT_REAL_TIME_CURRENCY";
    
    private SharedPreferences mSharedPreferences;
    private Gson mGson;
    
    @Inject
    public PreferencesHelper(Context context) {
        this.mSharedPreferences = context.getApplicationContext().getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        this.mGson = new Gson();
    }
    
    public void clear() {
        mSharedPreferences.edit().clear().apply();
    }
    
    public void putUserId(String userid) {
        mSharedPreferences.edit().putString(PREF_USER_ID, userid).apply();
    }
    
    public void putUserToken(String token) {
        mSharedPreferences.edit().putString(PREF_USER_TOKEN, token).apply();
    }
    
    public void putUser(User user) {
        String _user = mGson.toJson(user, User.class);
        mSharedPreferences.edit().putString(PREF_CURRENT_USER, _user).apply();
    }
    
    public String getUserId() {
        return mSharedPreferences.getString(PREF_USER_ID, "");
    }
    
    public String getUserToken() {
        return mSharedPreferences.getString(PREF_USER_TOKEN, "");
    }
    
    public User getCurrentUser() {
        String currentUser = mSharedPreferences.getString(PREF_CURRENT_USER, "");
        if (TextUtils.isEmpty(currentUser)) {
            return null;
        }
        return mGson.fromJson(currentUser, User.class);
    }
    
    public Wallet getCurrentWallet() {
        String currentWallet = mSharedPreferences.getString(PREF_CURRENT_WALLET, "");
        if (TextUtils.isEmpty(currentWallet)) {
            return null;
        }
        return mGson.fromJson(currentWallet, Wallet.class);
    }
    
    public void putCurrentWallet(Wallet wallet) {
        String _wallet = mGson.toJson(wallet, Wallet.class);
        mSharedPreferences.edit().putString(PREF_CURRENT_WALLET, _wallet).apply();
    }
    
    public void putRealTimeCurrency(RealTimeCurrency realTimeCurrency) {
        String _realTimeCurrency = mGson.toJson(realTimeCurrency, RealTimeCurrency.class);
        mSharedPreferences.edit().putString(PREF_CURRENT_REAL_TIME_CURRENCY, _realTimeCurrency)
                  .apply();
    }
    
    public RealTimeCurrency getRealTimeCurrency() {
        String realTimeCurrency = mSharedPreferences.getString(PREF_CURRENT_REAL_TIME_CURRENCY, "");
        if (TextUtils.isEmpty(realTimeCurrency)) {
            return null;
        }
        return mGson.fromJson(realTimeCurrency, RealTimeCurrency.class);
    }
    
}
