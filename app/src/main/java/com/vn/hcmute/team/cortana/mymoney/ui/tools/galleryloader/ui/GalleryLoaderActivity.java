package com.vn.hcmute.team.cortana.mymoney.ui.tools.galleryloader.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.widget.Toast;
import butterknife.BindView;
import com.vn.hcmute.team.cortana.mymoney.R;
import com.vn.hcmute.team.cortana.mymoney.ui.base.BaseActivity;
import com.vn.hcmute.team.cortana.mymoney.ui.tools.galleryloader.GalleryLoader;
import com.vn.hcmute.team.cortana.mymoney.ui.tools.galleryloader.config.GalleryLoaderConfig;
import com.vn.hcmute.team.cortana.mymoney.ui.tools.galleryloader.helper.imageloader.ImageFileLoader;
import com.vn.hcmute.team.cortana.mymoney.ui.tools.galleryloader.model.ReturnGalleryValue;
import com.vn.hcmute.team.cortana.mymoney.utils.MyMoneyUtil;
import com.vn.hcmute.team.cortana.mymoney.utils.permission.PermissionCallBack;
import com.vn.hcmute.team.cortana.mymoney.utils.permission.PermissionHelper;

/**
 * Created by infamouSs on 8/23/17.
 */

public class GalleryLoaderActivity extends BaseActivity implements GalleryLoaderContract.View {
    
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    
    GalleryLoaderPresenter mGalleryLoaderPresenter;
    
    private GalleryLoaderConfig mConfig;
    private ActionBar mActionbar;
    
    private PermissionCallBack mPermissionCallBack = new PermissionCallBack() {
        @Override
        public void onPermissionGranted() {
            
        }
        
        @Override
        public void onPermissionDenied() {
            finish();
        }
    };
    
    public GalleryLoaderActivity() {
        
    }
    
    @Override
    public int getLayoutId() {
        return R.layout.activity_gallery;
    }
    
    @Override
    protected void initializeDagger() {
        
    }
    
    @Override
    protected void initializePresenter() {
        
        mGalleryLoaderPresenter=new GalleryLoaderPresenter(new ImageFileLoader(this));
        mGalleryLoaderPresenter.setView(this);
        
        mPresenter = mGalleryLoaderPresenter;
       
    }
    
    @Override
    protected void initializeActionBar(View rootView) {
        setSupportActionBar(mToolbar);
        mActionbar = getSupportActionBar();
    }
    
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        if (MyMoneyUtil.isMarshmallow() || !MyMoneyUtil.isHasReadPermission(this)) {
            requirePermission();
        }
        
        Intent intent = getIntent();
        if (intent == null || intent.getExtras() == null) {
            finish();
            return;
        }
        
        mConfig = getConfig();
        
        initializeView(mConfig);
    }
    @Override
    public void initializeView(GalleryLoaderConfig config) {
        if (mActionbar != null) {
            mActionbar.setTitle(
                      config.isFolderMode() ? config.getFolderTitle() : config.getImageTitle());
            mActionbar.setDisplayHomeAsUpEnabled(true);
            mActionbar.setDisplayShowTitleEnabled(true);
        }
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_gallery,menu);
        return super.onCreateOptionsMenu(menu);
    }
    
    @Override
    public void requirePermission() {
        if (PermissionHelper.shouldShowRequestPermissionRationale(this,
                  PermissionHelper.Permission.READ_EXTERNAL_STORAGE)) {
            
            Snackbar.make(findViewById(android.R.id.content),
                      getString(R.string.permission_read_request),
                      Snackbar.LENGTH_INDEFINITE)
                      .setAction(getString(R.string.action_ok),
                                new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        PermissionHelper
                                                  .askForPermission(
                                                            GalleryLoaderActivity.this,
                                                            PermissionHelper.Permission.READ_EXTERNAL_STORAGE,
                                                            mPermissionCallBack);
                                    }
                                })
                      .show();
        } else {
            PermissionHelper
                      .askForPermission(this,
                                PermissionHelper.Permission.READ_EXTERNAL_STORAGE,
                                mPermissionCallBack);
        }
    }
    
    @Override
    public void loading(boolean isLoading) {
        
    }
    
    @Override
    public void showResult(ReturnGalleryValue result) {
        
    }
    
    @Override
    public void showEmpty() {
        Toast.makeText(this, "empty", Toast.LENGTH_SHORT).show();
    }
    
    @Override
    public void showError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
    
    public GalleryLoaderConfig getConfig() {
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        return bundle.getParcelable(GalleryLoader.TAG);
    }
    
    
}
