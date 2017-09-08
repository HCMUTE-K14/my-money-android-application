package com.vn.hcmute.team.cortana.mymoney.ui.main;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.design.widget.NavigationView.OnNavigationItemSelectedListener;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import butterknife.BindView;
import com.vn.hcmute.team.cortana.mymoney.MyMoneyApplication;
import com.vn.hcmute.team.cortana.mymoney.R;
import com.vn.hcmute.team.cortana.mymoney.data.cache.PreferencesHelper;
import com.vn.hcmute.team.cortana.mymoney.di.component.ApplicationComponent;
import com.vn.hcmute.team.cortana.mymoney.di.component.DaggerWalletComponent;
import com.vn.hcmute.team.cortana.mymoney.di.component.WalletComponent;
import com.vn.hcmute.team.cortana.mymoney.di.module.ActivityModule;
import com.vn.hcmute.team.cortana.mymoney.di.module.GlideApp;
import com.vn.hcmute.team.cortana.mymoney.di.module.WalletModule;
import com.vn.hcmute.team.cortana.mymoney.model.Wallet;
import com.vn.hcmute.team.cortana.mymoney.ui.base.BaseActivity;
import com.vn.hcmute.team.cortana.mymoney.ui.login.LoginActivity;
import com.vn.hcmute.team.cortana.mymoney.ui.view.selectwallet.SelectWalletListener;
import com.vn.hcmute.team.cortana.mymoney.ui.view.selectwallet.SelectWalletView;
import com.vn.hcmute.team.cortana.mymoney.ui.wallet.WalletContract;
import com.vn.hcmute.team.cortana.mymoney.ui.wallet.WalletPresenter;
import com.vn.hcmute.team.cortana.mymoney.utils.Constraints;
import com.vn.hcmute.team.cortana.mymoney.utils.DrawableUtil;
import java.util.List;
import javax.inject.Inject;

public class MainActivity extends BaseActivity implements WalletContract.View {
    
    public static final String TAG = MainActivity.class.getSimpleName();
    
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    
    @BindView(R.id.drawer_layout)
    DrawerLayout mDrawerLayout;
    
    @BindView(R.id.nav_view)
    NavigationView mNavigationView;
    
    @Inject
    WalletPresenter mWalletPresenter;
    
    @Inject
    PreferencesHelper mPreferenceHelper;
    
    private ImageView mImageViewIconWallet;
    
    private TextView mTextViewNameWallet;
    private TextView mTextViewValueWallet;
    
    private LinearLayout mContainerHeaderNavView;
    
    private View mHeaderNavView;
    private SelectWalletView mSelectWalletView;
    
    private boolean shouldShowMenuWallet = true;
    
    private Handler mHandler = new Handler();
    private Runnable mRunnable;
    
    private SelectWalletListener mSelectWalletListener = new SelectWalletListener() {
        @Override
        public void onClickAddWallet() {
            
        }
        
        @Override
        public void onClickMyWallet() {
            
        }
        
        @Override
        public void onCLickTotal() {
            
        }
        
        @Override
        public void onCLickWallet(Wallet wallet) {
            Toast.makeText(MainActivity.this, wallet.getWalletImage() + "", Toast.LENGTH_SHORT)
                      .show();
        }
        
        @Override
        public void onEditWallet(int position, Wallet wallet) {
            
        }
        
        @Override
        public void onRemoveWallet(int position, Wallet wallet) {
            mWalletPresenter.removeWallet(position, wallet);
        }
        
        @Override
        public void onArchiveWallet(int position, Wallet wallet) {
            boolean isArchive = wallet.isArchive();
            wallet.setArchive(!isArchive);
            mWalletPresenter.updateWallet(position, wallet);
        }
    
        @Override
        public void onTransferMoneyWallet(int position, Wallet wallet) {
        
        }
    
    };
    
    private OnClickListener mOnHeaderClickListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            if (!shouldShowMenuWallet) {
                mSelectWalletView.setVisibility(View.GONE);
                mNavigationView.inflateMenu(R.menu.menu_main_drawer);
                shouldShowMenuWallet = true;
            } else {
                mNavigationView.getMenu().clear();
                mWalletPresenter.getAllWallet();
            }
        }
    };
    
    
    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }
    
    @Override
    protected void initializeDagger() {
        ApplicationComponent applicationComponent = ((MyMoneyApplication) this.getApplication())
                  .getAppComponent();
        
        mPreferenceHelper = applicationComponent.prefenencesHelper();
        
        WalletComponent walletComponent = DaggerWalletComponent.builder()
                  .applicationComponent(applicationComponent)
                  .activityModule(new ActivityModule(this))
                  .walletModule(new WalletModule())
                  .build();
        
        walletComponent.inject(this);
    }
    
    @Override
    protected void initializePresenter() {
        this.mPresenter = mWalletPresenter;
        mWalletPresenter.setView(this);
    }
    
    @Override
    protected void initializeActionBar(View rootView) {
        setSupportActionBar(mToolbar);
    }
    
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        if (mPreferenceHelper.getCurrentUser() == null) {
            openLoginActivity();
            return;
        }
        mHandler.postDelayed(
                  new Runnable() {
                      @Override
                      public void run() {
                          initializeView();
                      }
                  }, 700);
    }
    
    @Override
    protected void onDestroy() {
        super.onDestroy();
        mWalletPresenter.unSubscribe();
    }
    
    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
    
    @Override
    public void initializeView() {
        initializeHeaderNavView();
        setupNavigationView();
    }
    
    
    @Override
    public void showEmpty() {
        showEmptyData(getString(R.string.txt_no_data));
    }
    
    @Override
    public void showListWallet(List<Wallet> wallets) {
        showWalletData(wallets);
    }
    
    @Override
    public void onAddWalletSuccess(String message, Wallet wallet) {
        
    }
    
    @Override
    public void onUpdateWalletSuccess(String message, int position, Wallet wallet) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        
        mSelectWalletView.updateArchiveWallet(position, wallet);
    }
    
    
    @Override
    public void onRemoveWalletSuccess(String message, int position, Wallet wallet) {
        
    }
    
    
    @Override
    public void onMoveMoneySuccess(String message) {
        
    }
    
    @Override
    public void onFailure(String message) {
        showEmptyData(message);
    }
    
    @Override
    public void loading(boolean isLoading) {
        mSelectWalletView.loading(isLoading);
    }
    
    
    private void initializeHeaderNavView() {
        mSelectWalletView = (SelectWalletView) mNavigationView.findViewById(R.id.select_wallet);
        mSelectWalletView.setSelectWalletListener(mSelectWalletListener);
        
        mHeaderNavView = mNavigationView.getHeaderView(0);
        
        ImageView imageViewBackgroundHeader = (ImageView) mHeaderNavView
                  .findViewById(R.id.img_header_bg);
        mImageViewIconWallet = (ImageView) mHeaderNavView.findViewById(R.id.img_wallet);
        mTextViewNameWallet = (TextView) mHeaderNavView.findViewById(R.id.txt_name_wallet);
        mTextViewValueWallet = (TextView) mHeaderNavView.findViewById(R.id.txt_value_wallet);
        
        Wallet currentWallet = mPreferenceHelper.getCurrentWallet();
        mHeaderNavView.setOnClickListener(mOnHeaderClickListener);
        
        if (currentWallet == null) {
            return;
        }
        currentWallet = mPreferenceHelper.getCurrentWallet();
        
        GlideApp.with(this).load(R.drawable.img_header_nav_back_ground)
                  .placeholder(R.drawable.folder_placeholder)
                  .error(R.drawable.folder_placeholder)
                  .into(imageViewBackgroundHeader);
        
        GlideApp.with(this).load(DrawableUtil.getDrawable(this, currentWallet.getWalletImage()))
                  .placeholder(R.drawable.folder_placeholder)
                  .error(R.drawable.folder_placeholder)
                  .into(mImageViewIconWallet);
        
        mTextViewNameWallet.setText(currentWallet.getWalletName());
        mTextViewValueWallet.setText(currentWallet.getMoney());
        
    }
    
    private void setupNavigationView() {
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                  this, mDrawerLayout, mToolbar, R.string.navigation_drawer_open,
                  R.string.navigation_drawer_close);
        mDrawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        mNavigationView.setNavigationItemSelectedListener(new OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                attachFragment(item);
                return true;
            }
        });
    }
    
    private void attachFragment(MenuItem item) {
        mRunnable = null;
        switch (item.getItemId()) {
            case R.id.navigation_item_cashbook:
                mRunnable = runnableAttachTestFragment;
                break;
            default:
                break;
        }
        
        if (mRunnable != null) {
            mDrawerLayout.closeDrawers();
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    mRunnable.run();
                }
            }, 350);
        }
    }
    
    private void showWalletData(List<Wallet> data) {
        mSelectWalletView.setVisibility(View.VISIBLE);
        mSelectWalletView.setData(data);
        
        shouldShowMenuWallet = false;
    }
    
    private void showEmptyData(String message) {
        mNavigationView.getMenu().clear();
        mSelectWalletView.setVisibility(View.VISIBLE);
        mSelectWalletView.setEmptyAdapter(message);
        
        shouldShowMenuWallet = false;
    }
    
    private Runnable runnableAttachTestFragment = new Runnable() {
        @Override
        public void run() {
            mNavigationView.getMenu().findItem(R.id.navigation_item_cashbook).setChecked(true);
            TestFragment fragment = new TestFragment();
            getSupportFragmentManager().beginTransaction()
                      .replace(R.id.container_fragment, fragment).commit();
        }
    };
    
    private void openLoginActivity() {
        //splash
        Intent intent = new Intent(this, LoginActivity.class);
        
        startActivityForResult(intent, Constraints.RequestCode.LOGIN_REQUEST_CODE);
    }
    
}

