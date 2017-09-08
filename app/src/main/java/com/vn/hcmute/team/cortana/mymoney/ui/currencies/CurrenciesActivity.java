package com.vn.hcmute.team.cortana.mymoney.ui.currencies;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;
import butterknife.BindView;
import com.vn.hcmute.team.cortana.mymoney.MyMoneyApplication;
import com.vn.hcmute.team.cortana.mymoney.R;
import com.vn.hcmute.team.cortana.mymoney.di.component.ApplicationComponent;
import com.vn.hcmute.team.cortana.mymoney.di.component.CurrenciesComponent;
import com.vn.hcmute.team.cortana.mymoney.di.component.DaggerCurrenciesComponent;
import com.vn.hcmute.team.cortana.mymoney.di.module.ActivityModule;
import com.vn.hcmute.team.cortana.mymoney.di.module.CurrenciesModule;
import com.vn.hcmute.team.cortana.mymoney.model.Currencies;
import com.vn.hcmute.team.cortana.mymoney.ui.base.BaseActivity;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;

/**
 * Created by kunsubin on 8/22/2017.
 */

public class CurrenciesActivity extends BaseActivity implements CurrenciesContract.View,
                                                                CurrenciesAdapter.ItemClickListener,
                                                                SearchView.OnQueryTextListener {
    
    public static final String TAG = CurrenciesActivity.class.getSimpleName();
    
    @Inject
    CurrenciesPresenter mCurrenciesPresenter;
    
    
    @BindView(R.id.recyclerViewCurrencies)
    RecyclerView mRecyclerViewCurrencies;
    
    @BindView(R.id.toolbar)
    Toolbar mToolbar_currencies;
    
    private CurrenciesAdapter mCurrenciesAdapter;
    private List<Currencies> mCurrenciesList;
    
    public CurrenciesActivity() {
        
    }
    
    @Override
    public int getLayoutId() {
        return R.layout.activity_currencies;
    }
    
    @Override
    protected void initializeDagger() {
        ApplicationComponent applicationComponent = ((MyMoneyApplication) this.getApplication())
                  .getAppComponent();
        CurrenciesComponent currenciesComponent = DaggerCurrenciesComponent
                  .builder()
                  .applicationComponent(applicationComponent)
                  .activityModule(new ActivityModule(this))
                  .currenciesModule(new CurrenciesModule())
                  .build();
        currenciesComponent.inject(this);
    }
    
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        mCurrenciesPresenter.getCurrencies();
    }
    
    @Override
    protected void onDestroy() {
        mRecyclerViewCurrencies.setAdapter(null);
        mCurrenciesPresenter.unSubscribe();
        super.onDestroy();
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search_currencies, menu);
        MenuItem menuItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(menuItem);
        searchView.setOnQueryTextListener(this);
        
        return true;
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    
    @Override
    protected void initializePresenter() {
        this.mPresenter = mCurrenciesPresenter;
        mCurrenciesPresenter.setView(this);
    }
    
    @Override
    protected void initializeActionBar(View rootView) {
        setSupportActionBar(mToolbar_currencies);
        
        ActionBar actionBar = getSupportActionBar();
        
        if (actionBar != null) {
            actionBar.setTitle(getString(R.string.title_currency));
            actionBar.setDisplayHomeAsUpEnabled(true);
            mToolbar_currencies.setTitleTextColor(ContextCompat.getColor(this, R.color.white));
        }
    }
    
    
    @Override
    public void showCurrencies(List<Currencies> list) {
        mCurrenciesList = list;
        
        mCurrenciesAdapter = new CurrenciesAdapter(this, list);
        mRecyclerViewCurrencies.setLayoutManager(new GridLayoutManager(this, 1));
        mCurrenciesAdapter.setClickListener(this);
        mRecyclerViewCurrencies.setAdapter(mCurrenciesAdapter);
    }
    
    @Override
    public void showEmpty() {
        
    }
    
    @Override
    public void showError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }
    
    @Override
    public void loading(boolean isLoading) {
        
    }
    
    @Override
    public void onItemClick(View view, Currencies currencies, int position) {
        
        Toast.makeText(this, currencies.getCurName(), Toast.LENGTH_LONG).show();
        
        Intent intent = new Intent();
        intent.putExtra("currency", currencies);
        setResult(RESULT_OK, intent);
        finish();
    }
    
    @Override
    public boolean onQueryTextSubmit(String query) {
        
        return false;
    }
    
    @Override
    public boolean onQueryTextChange(String newText) {
        
        newText = newText.toLowerCase();
        List<Currencies> currenciesList = new ArrayList<>();
        
        if (mCurrenciesList != null) {
            for (Currencies currencies : mCurrenciesList) {
                String name = currencies.getCurName().toLowerCase();
                String code = currencies.getCurCode().toLowerCase();
                if (name.contains(newText) || code.contains(newText)) {
                    currenciesList.add(currencies);
                }
            }
            
            mCurrenciesAdapter.setFilter(currenciesList);
        }
        
        return true;
    }
}
