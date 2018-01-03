package com.vn.hcmute.team.cortana.mymoney.ui.welcome;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import com.vn.hcmute.team.cortana.mymoney.R;
import com.vn.hcmute.team.cortana.mymoney.data.cache.PreferencesHelper;
import com.vn.hcmute.team.cortana.mymoney.model.Wallet;
import com.vn.hcmute.team.cortana.mymoney.ui.main.MainActivity;
import com.vn.hcmute.team.cortana.mymoney.ui.settings.ChooseLanguageHelper;
import com.vn.hcmute.team.cortana.mymoney.ui.wallet.AddWalletActivity;

/**
 * Created by kunsubin on 12/26/2017.
 */

public class WelcomeActivity extends AppCompatActivity {
    
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        startMainActivity();
        
    }
    
    private void startMainActivity() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                String currentLanguage = PreferencesHelper.getInstance(WelcomeActivity.this)
                          .getLanguage();
                ChooseLanguageHelper.changeConfig(WelcomeActivity.this, currentLanguage);
                Wallet currentWallet = PreferencesHelper.getInstance(WelcomeActivity.this)
                          .getCurrentWallet();
                Intent intent;
                if (currentWallet == null) {
                    intent = new Intent(WelcomeActivity.this, AddWalletActivity.class);
                    intent.putExtra(AddWalletActivity.FIRST_TIME_RUNNING, true);
                } else {
                    intent = new Intent(WelcomeActivity.this, MainActivity.class);
                }
                startActivity(intent);
                finish();
            }
        }, 1000);
    }
}
