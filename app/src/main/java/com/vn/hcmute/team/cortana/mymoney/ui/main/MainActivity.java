package com.vn.hcmute.team.cortana.mymoney.ui.main;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.design.widget.NavigationView.OnNavigationItemSelectedListener;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AlertDialog.Builder;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.BindView;
import com.vn.hcmute.team.cortana.mymoney.MyMoneyApplication;
import com.vn.hcmute.team.cortana.mymoney.R;
import com.vn.hcmute.team.cortana.mymoney.data.cache.PreferencesHelper;
import com.vn.hcmute.team.cortana.mymoney.di.component.ApplicationComponent;
import com.vn.hcmute.team.cortana.mymoney.di.component.DaggerWalletComponent;
import com.vn.hcmute.team.cortana.mymoney.di.component.WalletComponent;
import com.vn.hcmute.team.cortana.mymoney.di.module.ActivityModule;
import com.vn.hcmute.team.cortana.mymoney.di.module.WalletModule;
import com.vn.hcmute.team.cortana.mymoney.event.ActivityResultEvent;
import com.vn.hcmute.team.cortana.mymoney.model.Wallet;
import com.vn.hcmute.team.cortana.mymoney.ui.base.BaseActivity;
import com.vn.hcmute.team.cortana.mymoney.ui.base.listener.BaseCallBack;
import com.vn.hcmute.team.cortana.mymoney.ui.budget.BudgetMainFragment;
import com.vn.hcmute.team.cortana.mymoney.ui.category.CategoryMainFragment;
import com.vn.hcmute.team.cortana.mymoney.ui.debts.DebtsLoanMainFragment;
import com.vn.hcmute.team.cortana.mymoney.ui.event.EventMainFragment;
import com.vn.hcmute.team.cortana.mymoney.ui.login.LoginActivity;
import com.vn.hcmute.team.cortana.mymoney.ui.saving.SavingMainFragment;
import com.vn.hcmute.team.cortana.mymoney.ui.statistics.StatisticsMainFragment;
import com.vn.hcmute.team.cortana.mymoney.ui.transaction.TransactionMainFragment;
import com.vn.hcmute.team.cortana.mymoney.ui.view.selectwallet.SelectWalletListener;
import com.vn.hcmute.team.cortana.mymoney.ui.view.selectwallet.SelectWalletView;
import com.vn.hcmute.team.cortana.mymoney.ui.wallet.AddWalletActivity;
import com.vn.hcmute.team.cortana.mymoney.ui.wallet.EditWalletActivity;
import com.vn.hcmute.team.cortana.mymoney.ui.wallet.MyWalletActivity;
import com.vn.hcmute.team.cortana.mymoney.ui.wallet.WalletContract;
import com.vn.hcmute.team.cortana.mymoney.ui.wallet.WalletPresenter;
import com.vn.hcmute.team.cortana.mymoney.usecase.base.Action;
import com.vn.hcmute.team.cortana.mymoney.usecase.base.TypeRepository;
import com.vn.hcmute.team.cortana.mymoney.usecase.remote.WalletUseCase;
import com.vn.hcmute.team.cortana.mymoney.usecase.remote.WalletUseCase.WalletRequest;
import com.vn.hcmute.team.cortana.mymoney.utils.Constraints;
import com.vn.hcmute.team.cortana.mymoney.utils.Constraints.RequestCode;
import com.vn.hcmute.team.cortana.mymoney.utils.Constraints.ResultCode;
import com.vn.hcmute.team.cortana.mymoney.utils.DrawableUtil;
import com.vn.hcmute.team.cortana.mymoney.utils.GlideImageLoader;
import com.vn.hcmute.team.cortana.mymoney.utils.NumberUtil;
import java.util.List;
import javax.inject.Inject;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

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
    WalletUseCase mWalletUseCase;
    
    @Inject
    PreferencesHelper mPreferenceHelper;
    
    private ImageView mImageViewIconWallet;
    private Wallet mCurrentWallet;
    private TextView mTextViewNameWallet;
    private TextView mTextViewValueWallet;
    private Fragment mCurrentFragment;
    
    private SelectWalletView mSelectWalletView;
    private ActionBar mActionBar;
    private boolean shouldShowMenuWallet = true;
    
    private Handler mHandler = new Handler();
    private Runnable mRunnable;
    
    
    private SelectWalletListener mSelectWalletListener = new SelectWalletListener() {
        @Override
        public void onClickAddWallet() {
            openAddWalletActivity();
        }
        
        @Override
        public void onClickMyWallet() {
            openActivityMyWallet();
        }
        
        
        @Override
        public void onCLickWallet(Wallet wallet) {
            chooseWallet(wallet);
            mDrawerLayout.closeDrawers();
        }
        
        @Override
        public void onEditWallet(int position, Wallet wallet) {
            openEditWalletActivity(wallet);
        }
        
        @Override
        public void onRemoveWallet(final int position, final Wallet wallet) {
            AlertDialog.Builder dialog = new Builder(MainActivity.this);
            dialog.setTitle(R.string.txt_wait_a_second);
            dialog.setMessage(R.string.message_waring_remove_wallet);
            dialog.setNegativeButton(getString(R.string.txt_yes),
                      new DialogInterface.OnClickListener() {
                          @Override
                          public void onClick(DialogInterface dialog, int which) {
                              mWalletPresenter.removeWallet(position, wallet);
                          }
                      });
            
            dialog.setPositiveButton(getString(R.string.txt_no),
                      new DialogInterface.OnClickListener() {
                          @Override
                          public void onClick(DialogInterface dialog, int which) {
                              dialog.cancel();
                              dialog.dismiss();
                          }
                      });
            
            dialog.create().show();
            
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
    
    private Runnable runnableAttachTransactionFragment = new Runnable() {
        @Override
        public void run() {
            mNavigationView.getMenu().findItem(R.id.navigation_item_cashbook).setChecked(true);
            TransactionMainFragment fragment = new TransactionMainFragment();
            mCurrentFragment = fragment;
            getSupportFragmentManager().beginTransaction()
                      .replace(R.id.container_fragment, fragment).commit();
        }
    };
    
    private Runnable runnableAttachCategoryFragment = new Runnable() {
        @Override
        public void run() {
            mNavigationView.getMenu().findItem(R.id.navigation_item_categories).setChecked(true);
            CategoryMainFragment fragment = new CategoryMainFragment();
            mCurrentFragment = fragment;
            getSupportFragmentManager().beginTransaction()
                      .replace(R.id.container_fragment, fragment).commit();
        }
    };
    
    private Runnable runnableAttachSavingFragment = new Runnable() {
        @Override
        public void run() {
            mNavigationView.getMenu().findItem(R.id.navigation_item_saving).setChecked(true);
            SavingMainFragment fragment = new SavingMainFragment();
            mCurrentFragment = fragment;
            getSupportFragmentManager().beginTransaction()
                      .replace(R.id.container_fragment, fragment).commit();
        }
    };
    
    private Runnable runnableAttachEventFragment = new Runnable() {
        @Override
        public void run() {
            mNavigationView.getMenu().findItem(R.id.navigation_item_events).setChecked(true);
            EventMainFragment fragment = new EventMainFragment();
            mCurrentFragment = fragment;
            getSupportFragmentManager().beginTransaction()
                      .replace(R.id.container_fragment, fragment).commit();
        }
    };
    
    private Runnable runnableAttachContactFragment = new Runnable() {
        @Override
        public void run() {
            mNavigationView.getMenu().findItem(R.id.navigation_item_saving).setChecked(true);
            EventMainFragment fragment = new EventMainFragment();
            mCurrentFragment = fragment;
            getSupportFragmentManager().beginTransaction()
                      .replace(R.id.container_fragment, fragment).commit();
        }
    };
    private Runnable runnableAttachBudgetFragment = new Runnable() {
        @Override
        public void run() {
            mNavigationView.getMenu().findItem(R.id.navigation_item_budgets).setChecked(true);
            BudgetMainFragment fragment = new BudgetMainFragment();
            mCurrentFragment = fragment;
            getSupportFragmentManager().beginTransaction()
                      .replace(R.id.container_fragment, fragment).commit();
        }
    };
    
    
    private Runnable runnableAttachDebtsFragment = new Runnable() {
        @Override
        public void run() {
            mNavigationView.getMenu().findItem(R.id.navigation_item_debt).setChecked(true);
            DebtsLoanMainFragment fragment = new DebtsLoanMainFragment();
            mCurrentFragment = fragment;
            getSupportFragmentManager().beginTransaction()
                      .replace(R.id.container_fragment, fragment).commit();
        }
    };
    private Runnable runnableAttachStatisticsFragment = new Runnable() {
        @Override
        public void run() {
            mNavigationView.getMenu().findItem(R.id.navigation_item_trends).setChecked(true);
            StatisticsMainFragment fragment = new StatisticsMainFragment();
            mCurrentFragment = fragment;
            getSupportFragmentManager().beginTransaction()
                      .replace(R.id.container_fragment, fragment).commit();
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
        
        mPreferenceHelper = applicationComponent.preferencesHelper();
        
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
        mActionBar = getSupportActionBar();
    }
    
    
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
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
                  }, 400);
    }
    
    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        mWalletPresenter.unSubscribe();
    }
    
    
    @Subscribe
    public void onEvent(ActivityResultEvent event) {
        if (event.getResultCode() == ResultCode.NEED_UPDATE_CURRENT_WALLET_RESULT_CODE) {
            
            WalletRequest request = new WalletRequest(Action.ACTION_GET_WALLET_BY_ID,
                      new BaseCallBack<Object>() {
                          @Override
                          public void onSuccess(Object value) {
                              mCurrentWallet = (Wallet) value;
                              
                              mPreferenceHelper.putCurrentWallet(mCurrentWallet);
                              
                              updateViewHeaderWithWallet(mCurrentWallet);
                          }
                          
                          @Override
                          public void onFailure(Throwable throwable) {
                          }
                          
                          @Override
                          public void onLoading() {
                          
                          }
                      }, null, new String[]{mCurrentWallet.getWalletid()});
            request.setTypeRepository(TypeRepository.REMOTE);
            
            mWalletUseCase.subscribe(request);
        }
    }
    
    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
    
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (mCurrentFragment != null) {
            mCurrentFragment.onActivityResult(requestCode, resultCode, data);
        }
        if (resultCode == RESULT_OK) {
            if (requestCode == RequestCode.CHOOSE_WALLET_REQUEST_CODE) {
                if (data == null) {
                    return;
                }
                Wallet wallet = data.getParcelableExtra("wallet");
                chooseWallet(wallet);
            } else {
                if (data != null) {
                    int resultChangeCategory = data.getIntExtra("change_category", -1);
                    if (resultChangeCategory == RequestCode.CHANGE_CATEGORY_REQUEST_CODE &&
                        mCurrentFragment instanceof CategoryMainFragment) {
                        ((CategoryMainFragment) mCurrentFragment).reloadData();
                    }
                }
                
            }
            
        } else if (data != null) {
            Wallet wallet = data.getParcelableExtra("wallet");
            if (wallet == null) {
                return;
            }
            switch (requestCode) {
                case RequestCode.ADD_WALLET_REQUEST_CODE:
                    if (resultCode == ResultCode.ADD_WALLET_RESULT_CODE) {
                        mSelectWalletView.addWallet(wallet);
                    }
                    break;
                case RequestCode.EDIT_WALLET_REQUEST_CODE:
                    if (resultCode == ResultCode.EDIT_WALLET_RESULT_CODE) {
                        
                        if (wallet.equals(mPreferenceHelper.getCurrentWallet())) {
                            updateViewHeaderWithWallet(wallet);
                        }
                        mSelectWalletView.updateWallet(wallet);
                    } else if (resultCode == ResultCode.REMOVE_WALLET_RESULT_CODE) {
                        if (wallet.equals(mPreferenceHelper.getCurrentWallet())) {
                            mPreferenceHelper.putCurrentWallet(null);
                        }
                        mSelectWalletView.removeWallet(wallet);
                    }
                    break;
                default:
                    break;
                
            }
        }
    }
    
    @Override
    public void initializeView() {
        initializeHeaderNavView();
        setupNavigationView();
        runnableAttachTransactionFragment.run();
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
        mSelectWalletView.updateArchiveWallet(position, wallet);
    }
    
    @Override
    public void onRemoveWalletSuccess(String message, int position, Wallet wallet) {
        mSelectWalletView.removeWallet(wallet);
        if (wallet.getWalletid().equals(mCurrentWallet.getWalletid())) {
            EventBus.getDefault().post(new ActivityResultEvent(ResultCode.NEED_RELOAD_DATA, null));
            mPreferenceHelper.putCurrentWallet(null);
            updateViewHeaderWithWallet(null);
        }
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
        
        View headerNavView = mNavigationView.getHeaderView(0);
        
        mImageViewIconWallet = (ImageView) headerNavView.findViewById(R.id.img_wallet);
        mTextViewNameWallet = (TextView) headerNavView.findViewById(R.id.txt_name_wallet);
        mTextViewValueWallet = (TextView) headerNavView.findViewById(R.id.txt_value_wallet);
        headerNavView.setOnClickListener(mOnHeaderClickListener);
        
        mCurrentWallet = mPreferenceHelper.getCurrentWallet();
        
        if (mCurrentWallet == null) {
            return;
        }
        mCurrentWallet = mPreferenceHelper.getCurrentWallet();
        GlideImageLoader.load(this, DrawableUtil.getDrawable(this, mCurrentWallet.getWalletImage()),
                  mImageViewIconWallet);
        
        mTextViewNameWallet.setText(mCurrentWallet.getWalletName());
        mTextViewValueWallet.setText(NumberUtil
                  .formatAmount(TextUtils.isEmpty(mCurrentWallet.getMoney())
                                      ? "0"
                                      : mCurrentWallet.getMoney(),
                            mCurrentWallet.getCurrencyUnit().getCurSymbol()));
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
        mDrawerLayout.addDrawerListener(new DrawerLayout.DrawerListener() {
            
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                if (!shouldShowMenuWallet) {
                    mSelectWalletView.setVisibility(View.GONE);
                    mNavigationView.inflateMenu(R.menu.menu_main_drawer);
                    shouldShowMenuWallet = true;
                }
            }
            
            @Override
            public void onDrawerOpened(View drawerView) {
            
            }
            
            @Override
            public void onDrawerClosed(View drawerView) {
            }
            
            @Override
            public void onDrawerStateChanged(int newState) {
            }
        });
    }
    
    private void attachFragment(MenuItem item) {
        mRunnable = null;
        switch (item.getItemId()) {
            case R.id.navigation_item_cashbook:
                mRunnable = runnableAttachTransactionFragment;
                break;
            case R.id.navigation_item_debt:
                mRunnable = runnableAttachDebtsFragment;
                break;
            case R.id.navigation_item_categories:
                mRunnable = runnableAttachCategoryFragment;
                break;
            case R.id.navigation_item_budgets:
                mRunnable = runnableAttachBudgetFragment;
                break;
            case R.id.navigation_item_saving:
                mRunnable = runnableAttachSavingFragment;
                break;
            case R.id.navigation_item_events:
                mRunnable = runnableAttachEventFragment;
                break;
            case R.id.navigation_item_trends:
                mRunnable = runnableAttachStatisticsFragment;
            case R.id.navigation_item_contacts:
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
            }, 200);
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
    
    private void openLoginActivity() {
        //splash
        Intent intent = new Intent(this, LoginActivity.class);
        
        startActivityForResult(intent, Constraints.RequestCode.LOGIN_REQUEST_CODE);
    }
    
    private void openAddWalletActivity() {
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(MainActivity.this, AddWalletActivity.class);
                startActivityForResult(intent, RequestCode.ADD_WALLET_REQUEST_CODE);
                
            }
        }, 100);
        mDrawerLayout.closeDrawers();
        
    }
    
    private void openActivityMyWallet() {
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(MainActivity.this, MyWalletActivity.class);
                intent.putExtra("mode", MyWalletActivity.MODE_OPEN_FROM_ANOTHER);
                startActivityForResult(intent, RequestCode.CHOOSE_WALLET_REQUEST_CODE);
                
            }
        }, 100);
        mDrawerLayout.closeDrawers();
    }
    
    private void chooseWallet(Wallet wallet) {
        
        mPreferenceHelper.putCurrentWallet(wallet);
        mCurrentWallet = mPreferenceHelper.getCurrentWallet();
        mSelectWalletView.notifyDataSetChanged();
        
        updateViewHeaderWithWallet(wallet);
        
    }
    
    private void updateViewHeaderWithWallet(Wallet wallet) {
        if (wallet == null) {
            mTextViewValueWallet.setText("");
            mTextViewNameWallet.setText("");
            return;
        }
        GlideImageLoader.load(MainActivity.this, DrawableUtil
                  .getDrawable(MainActivity.this, wallet.getWalletImage()), mImageViewIconWallet);
        
        mTextViewValueWallet.setText(NumberUtil
                  .formatAmount(TextUtils.isEmpty(wallet.getMoney()) ? "0" : wallet.getMoney(),
                            wallet.getCurrencyUnit().getCurSymbol()));
        mTextViewNameWallet.setText(wallet.getWalletName());
    }
    
    private void openEditWalletActivity(Wallet wallet) {
        Intent intent = new Intent(this, EditWalletActivity.class);
        intent.putExtra("wallet", wallet);
        startActivityForResult(intent, RequestCode.EDIT_WALLET_REQUEST_CODE);
    }
}

