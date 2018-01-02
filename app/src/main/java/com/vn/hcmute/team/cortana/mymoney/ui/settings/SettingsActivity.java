package com.vn.hcmute.team.cortana.mymoney.ui.settings;

import android.support.v7.app.ActionBar;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.LayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;
import butterknife.BindView;
import com.vn.hcmute.team.cortana.mymoney.ApplicationConfig;
import com.vn.hcmute.team.cortana.mymoney.R;
import com.vn.hcmute.team.cortana.mymoney.ui.base.BaseActivity;
import com.vn.hcmute.team.cortana.mymoney.ui.settings.SettingsAdapter.SettingsListener;
import com.vn.hcmute.team.cortana.mymoney.ui.view.BackupToFileDialog;
import com.vn.hcmute.team.cortana.mymoney.ui.view.DividerItemDecoration;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by infamouSs on 12/27/17.
 */

public class SettingsActivity extends BaseActivity {
    
    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;
    
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    
    private final String[] LANGUAGE = new String[]{ApplicationConfig.LANGUAGE.EN.getData(),
              ApplicationConfig.LANGUAGE.VI.getData()};
    
    private SettingsAdapter mSettingsAdapter;
    
    
    private SettingsListener mSettingsListener = new SettingsListener() {
        @Override
        public void onChooseLanguage() {
            ChooseLanguageHelper.change(SettingsActivity.this);
        }
        
        @Override
        public void onBackupDatabase() {
            BackupToFileDialog.newInstance().show(getFragmentManager(), "dialog");
        }
        
        @Override
        public void onRestoreDatabase() {
            Toast.makeText(SettingsActivity.this, "Restore Language", Toast.LENGTH_SHORT).show();
            
        }
        
        @Override
        public void onShowAbout() {
            Toast.makeText(SettingsActivity.this, "About", Toast.LENGTH_SHORT).show();
            
        }
    };
    
    @Override
    public int getLayoutId() {
        return R.layout.activity_settings;
    }
    
    @Override
    protected void initializeDagger() {
    
    }
    
    @Override
    protected void initializePresenter() {
    
    }
    
    @Override
    protected void initializeActionBar(View rootView) {
        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();
        
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
            
            actionBar.setTitle(R.string.action_settings);
            
        }
    }
    
    @Override
    protected void initialize() {
        super.initialize();
        mSettingsAdapter = new SettingsAdapter(mSettingsListener);
        LayoutManager layoutManager = new GridLayoutManager(this, 1);
        
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, null));
        mRecyclerView.setLayoutManager(layoutManager);
        
        mSettingsAdapter.setData(getDataSettings());
        mRecyclerView.setAdapter(mSettingsAdapter);
    }
    
    
    private List<String> getDataSettings() {
        List<String> data = new ArrayList<>();
        
        data.add(getString(R.string.settings_choose_language));
        data.add(getString(R.string.settings_backup_database));
        data.add(getString(R.string.settings_restore_database));
        data.add(getString(R.string.settings_about_me));
        
        return data;
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return false;
    }
    
}
