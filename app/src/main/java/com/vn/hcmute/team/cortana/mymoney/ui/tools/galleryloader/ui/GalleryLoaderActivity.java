package com.vn.hcmute.team.cortana.mymoney.ui.tools.galleryloader.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import butterknife.BindView;
import com.vn.hcmute.team.cortana.mymoney.R;
import com.vn.hcmute.team.cortana.mymoney.ui.base.BaseActivity;
import com.vn.hcmute.team.cortana.mymoney.ui.tools.galleryloader.GalleryLoader;
import com.vn.hcmute.team.cortana.mymoney.ui.tools.galleryloader.config.GalleryLoaderConfig;
import com.vn.hcmute.team.cortana.mymoney.ui.tools.galleryloader.helper.imageloader.ImageFileLoader;
import com.vn.hcmute.team.cortana.mymoney.ui.tools.galleryloader.listener.OnBackAction;
import com.vn.hcmute.team.cortana.mymoney.ui.tools.galleryloader.listener.OnFolderClickListener;
import com.vn.hcmute.team.cortana.mymoney.ui.tools.galleryloader.listener.OnImageClickListener;
import com.vn.hcmute.team.cortana.mymoney.ui.tools.galleryloader.listener.OnImageSelectedListener;
import com.vn.hcmute.team.cortana.mymoney.ui.tools.galleryloader.model.Folder;
import com.vn.hcmute.team.cortana.mymoney.ui.tools.galleryloader.model.ImageGallery;
import com.vn.hcmute.team.cortana.mymoney.ui.tools.galleryloader.model.ReturnGalleryValue;
import com.vn.hcmute.team.cortana.mymoney.utils.MyMoneyUtil;
import com.vn.hcmute.team.cortana.mymoney.utils.permission.PermissionCallBack;
import com.vn.hcmute.team.cortana.mymoney.utils.permission.PermissionHelper;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by infamouSs on 8/23/17.
 */

public class GalleryLoaderActivity extends BaseActivity implements GalleryLoaderContract.View {
    
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    
    @BindView(R.id.progress_bar)
    ProgressBar mProgressBar;
    
    private GalleryLoaderPresenter mGalleryLoaderPresenter;
    
    private GalleryLoaderConfig mConfig;
    private ActionBar mActionbar;
    private RecyclerViewManager mRecyclerViewManager;
    
    private OnImageClickListener mImageClickListener = new OnImageClickListener() {
        @Override
        public boolean onImageClick(int position, boolean isSelected) {
            return mRecyclerViewManager.selectImage();
        }
    };
    private OnFolderClickListener mFolderClickListener = new OnFolderClickListener() {
        @Override
        public void onFolderClick(Folder folder) {
            mRecyclerViewManager.setImageAdapter(folder.getImages());
        }
    };
    
    private OnImageSelectedListener mImageSelectedListener = new OnImageSelectedListener() {
        @Override
        public void onUpdateSelection(List<ImageGallery> imageSelecteds) {
            updateTitle();
        }
    };
    
    private PermissionCallBack mPermissionCallBack = new PermissionCallBack() {
        @Override
        public void onPermissionGranted() {
            if (mRecyclerViewManager != null) {
                getData();
            }
        }
        
        @Override
        public void onPermissionDenied() {
            finish();
        }
    };
    
    public GalleryLoaderActivity() {
        
    }
    
    /*------------------------*/
    /*Base Method             */
    /*------------------------*/
    @Override
    public int getLayoutId() {
        return R.layout.activity_gallery;
    }
    
    @Override
    protected void initializeDagger() {
        
    }
    
    @Override
    protected void initializePresenter() {
        
        mGalleryLoaderPresenter = new GalleryLoaderPresenter(new ImageFileLoader(this));
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
        
        getData();
    }
    
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuItem menuDone = menu.findItem(R.id.action_done);
        if (menuDone != null) {
            menuDone.setVisible(mRecyclerViewManager.isShowDoneButton());
        }
        
        return super.onPrepareOptionsMenu(menu);
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_gallery, menu);
        return super.onCreateOptionsMenu(menu);
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            
            case android.R.id.home:
                onBackPressed();
                return true;
            case R.id.action_done:
                onDone();
                return true;
            case R.id.action_sort_a_z:
                return true;
            case R.id.action_sort_z_a:
                return true;
            case R.id.action_sort_date_added:
                return true;
            default:
                return false;
            
        }
    }
    
    @Override
    public void onBackPressed() {
        mRecyclerViewManager.handlerBackAction(new OnBackAction() {
            @Override
            public void onBackToFolder() {
                updateTitle();
            }
            
            @Override
            public void onFinishImagePicker() {
                setResult(RESULT_CANCELED);
                finish();
            }
        });
    }
   /*------------------------------*/
    /*Initialize & Callback Method   */
    /*------------------------------*/
    
    @Override
    public void initializeView(GalleryLoaderConfig config) {
        if (mActionbar != null) {
            mActionbar.setTitle(
                      config.isFolderMode() ? config.getFolderTitle() : config.getImageTitle());
            mActionbar.setDisplayHomeAsUpEnabled(true);
            mActionbar.setDisplayShowTitleEnabled(true);
        }
        mRecyclerViewManager = new RecyclerViewManager(this, this.mRecyclerView, mConfig);
        mRecyclerViewManager.setupAdapter(mFolderClickListener, mImageClickListener);
        mRecyclerViewManager.setImageSelectedListener(mImageSelectedListener);
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
        mProgressBar.setVisibility(isLoading ? View.VISIBLE : View.GONE);
    }
    
    @Override
    public void showResult(ReturnGalleryValue result) {
        if (mConfig.isFolderMode()) {
            setFolderAdapter(result.getFolders());
        } else {
            setImageAdapter(result.getImages());
        }
    }
    
    @Override
    public void showEmpty() {
        setEmptyAdapter();
    }
    
    @Override
    public void showError(String message) {
        setEmptyAdapter();
        Snackbar.make(findViewById(android.R.id.content), message, Snackbar.LENGTH_SHORT).show();
    }
    
    @Override
    public void onDonePickImage(final List<ImageGallery> selectedImages) {
        Runnable doBackGround = new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent();
                intent.putParcelableArrayListExtra(GalleryLoader.EXTRA_SELECTED_IMAGES,
                          (ArrayList<? extends Parcelable>) selectedImages);
                setResult(RESULT_OK, intent);
                
                finish();
            }
        };
        doBackGround.run();
    }
    
    
    /*------------------------*/
    /*Helper Method            */
    /*------------------------*/
    
    private void updateTitle() {
        supportInvalidateOptionsMenu();
        mActionbar.setTitle(mRecyclerViewManager.getTitle());
    }
    
    private void setImageAdapter(List<ImageGallery> images) {
        mRecyclerViewManager.setImageAdapter(images);
        updateTitle();
    }
    
    private void setFolderAdapter(List<Folder> folders) {
        mRecyclerViewManager.setFolderAdapter(folders);
        updateTitle();
    }
    
    private void setEmptyAdapter() {
        mRecyclerViewManager.setEmptyAdapter();
    }
    
    private GalleryLoaderConfig getConfig() {
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        return bundle.getParcelable(GalleryLoader.TAG);
    }
    
    private void getData() {
        mGalleryLoaderPresenter.loadImages(mConfig.isFolderMode());
    }
    
    private void onDone() {
        // MyLogger.d(mRecyclerViewManager.getSelectedImages());
        mGalleryLoaderPresenter.finishPickImage(mRecyclerViewManager.getSelectedImages());
    }
}
