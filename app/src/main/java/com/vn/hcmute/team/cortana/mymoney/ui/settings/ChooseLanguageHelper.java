package com.vn.hcmute.team.cortana.mymoney.ui.settings;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AlertDialog.Builder;
import android.support.v7.app.AppCompatActivity;
import com.vn.hcmute.team.cortana.mymoney.ApplicationConfig;
import com.vn.hcmute.team.cortana.mymoney.R;
import com.vn.hcmute.team.cortana.mymoney.data.cache.PreferencesHelper;
import com.vn.hcmute.team.cortana.mymoney.ui.main.MainActivity;
import java.util.Locale;

/**
 * Created by infamouSs on 1/2/18.
 */

public class ChooseLanguageHelper {
    
    private static final String[] LANGUAGE = new String[]{ApplicationConfig.LANGUAGE.EN.getData(),
              ApplicationConfig.LANGUAGE.VI.getData()};
    
    public static void change(final AppCompatActivity activity) {
        final AlertDialog.Builder alertDialog = new Builder(activity);
        
        alertDialog.setTitle(R.string.settings_choose_language);
        
        int index = getCurrentLanguage(activity).equals("en") ? 0 : 1;
        
        alertDialog.setSingleChoiceItems(LANGUAGE, index, new DialogInterface.OnClickListener() {
            
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String code = "en";
                switch (which) {
                    case 0:
                        code = "en";
                        break;
                    case 1:
                        code = "vi";
                        break;
                    default:
                        break;
                    
                }
                doChangeLanguage(activity, code);
                dialog.dismiss();
            }
        });
        alertDialog.create().show();
    }
    
    private static String getCurrentLanguage(Context context) {
        return PreferencesHelper.getInstance(context).getLanguage();
    }
    
    private static void doChangeLanguage(AppCompatActivity activity, String code) {
        changeConfig(activity, code);
        savePref(activity, code);
        refresh(activity);
    }
    
    public static void changeConfig(AppCompatActivity activity, String code) {
        Locale locale = new Locale(code);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        activity.getBaseContext().getResources().updateConfiguration(config,
                  activity.getBaseContext().getResources().getDisplayMetrics());
    }
    
    private static void savePref(AppCompatActivity activity, String code) {
        PreferencesHelper.getInstance(activity).putLanguage(code);
    }
    
    private static void refresh(AppCompatActivity activity) {
        Intent intent = new Intent(activity, MainActivity.class);
        activity.startActivity(intent);
        activity.finish();
    }
}
