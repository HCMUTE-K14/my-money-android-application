package com.vn.hcmute.team.cortana.mymoney.data.cache;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import com.google.gson.Gson;
import com.vn.hcmute.team.cortana.mymoney.model.User;
import javax.inject.Inject;

/**
 * Created by infamouSs on 8/21/17.
 */

public class PreferencesHelper {
    
    private static final String PREF_NAME = "my_money_pref";
    
    private final String PREF_USER_ID = "PREF_USER_ID";
    private final String PREF_USER_TOKEN = "PREF_USER_TOKEN";
    private final String PREF_CURRENT_USER = "PREF_CURRENT_USER";
    
    private SharedPreferences mSharedPreferences;
    private Gson mGson;
    
    @Inject
    public PreferencesHelper(Context context) {
        this.mSharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
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
    
}
