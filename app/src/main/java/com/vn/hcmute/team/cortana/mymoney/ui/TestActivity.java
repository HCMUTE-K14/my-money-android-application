package com.vn.hcmute.team.cortana.mymoney.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.design.widget.TabLayout.OnTabSelectedListener;
import android.support.design.widget.TabLayout.Tab;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;
import com.vn.hcmute.team.cortana.mymoney.R;
import com.vn.hcmute.team.cortana.mymoney.ui.view.calendview.model.BaseModel;
import com.vn.hcmute.team.cortana.mymoney.ui.view.calendview.model.DateModel;

/**
 * Created by infamouSs on 11/3/17.
 */

public class TestActivity extends AppCompatActivity {
    
    public TestActivity() {
    
    }
    
    private TabLayout mTabLayout;
    
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test);
        mTabLayout = (TabLayout) findViewById(R.id.tab_layout);
        
        mTabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        final BaseModel dateModel = new DateModel(this);
        dateModel.buildData();
        for (String key : dateModel.getData().keySet()) {
            mTabLayout.addTab(mTabLayout.newTab().setText(key));
        }
        mTabLayout.addOnTabSelectedListener(new OnTabSelectedListener() {
            @Override
            public void onTabSelected(Tab tab) {
                String data = dateModel.getData().get(tab.getText().toString());
                Toast.makeText(TestActivity.this, data, Toast.LENGTH_SHORT).show();
            }
            
            @Override
            public void onTabUnselected(Tab tab) {
            
            }
            
            @Override
            public void onTabReselected(Tab tab) {
            
            }
        });
    }
}
