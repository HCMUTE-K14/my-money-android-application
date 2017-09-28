package com.vn.hcmute.team.cortana.mymoney.ui.category;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.SearchView.OnQueryTextListener;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;
import butterknife.BindView;
import com.vn.hcmute.team.cortana.mymoney.MyMoneyApplication;
import com.vn.hcmute.team.cortana.mymoney.R;
import com.vn.hcmute.team.cortana.mymoney.di.component.ApplicationComponent;
import com.vn.hcmute.team.cortana.mymoney.di.component.CategoryComponent;
import com.vn.hcmute.team.cortana.mymoney.di.component.DaggerCategoryComponent;
import com.vn.hcmute.team.cortana.mymoney.di.module.ActivityModule;
import com.vn.hcmute.team.cortana.mymoney.di.module.CategoryModule;
import com.vn.hcmute.team.cortana.mymoney.model.Category;
import com.vn.hcmute.team.cortana.mymoney.ui.base.BaseActivity;
import com.vn.hcmute.team.cortana.mymoney.ui.base.EmptyAdapter;
import com.vn.hcmute.team.cortana.mymoney.ui.category.CategoryContract.ShowView;
import com.vn.hcmute.team.cortana.mymoney.ui.category.ListParentCategoryAdapter.OnSelectParentCategoryListener;
import com.vn.hcmute.team.cortana.mymoney.utils.sort.SortType;
import com.vn.hcmute.team.cortana.mymoney.utils.sort.SortUtil;
import com.vn.hcmute.team.cortana.mymoney.utils.TextUtil;
import java.util.List;
import javax.inject.Inject;

/**
 * Created by infamouSs on 9/18/17.
 */

public class ListParentCategoryActivity extends BaseActivity implements ShowView {
    
    public static final String TAG = ListParentCategoryActivity.class.getSimpleName();
    
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    
    @BindView(R.id.progress_bar)
    ProgressBar mProgressBar;
    
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    
    @Inject
    CategoryPresenter mCategoryPresenter;
    
    private ListParentCategoryAdapter mParentCategoryAdapter;
    private EmptyAdapter mEmptyAdapter;
    
    
    private String mCurrentType;
    private String mCurrentTransType;
    private String mSelectedCategoryId;
    
    private OnSelectParentCategoryListener mOnSelectParentCategoryListener = new OnSelectParentCategoryListener() {
        @Override
        public void onClick(int position, Category category) {
            finishChooseParentCategory(category);
        }
    };
    
    private OnQueryTextListener mQueryTextListener = new OnQueryTextListener() {
        @Override
        public boolean onQueryTextSubmit(String query) {
            return false;
        }
        
        @Override
        public boolean onQueryTextChange(String newText) {
            searchCategory(newText);
            return true;
        }
    };
    
    
    public ListParentCategoryActivity() {
        
    }
    
    
    @Override
    public int getLayoutId() {
        return R.layout.activity_list_parent_category;
    }
    
    @Override
    protected void initializeDagger() {
        ApplicationComponent applicationComponent = ((MyMoneyApplication) this.getApplication())
                  .getAppComponent();
        
        CategoryComponent categoryComponent = DaggerCategoryComponent.builder()
                  .applicationComponent(applicationComponent)
                  .activityModule(new ActivityModule(this))
                  .categoryModule(new CategoryModule())
                  .build();
        
        categoryComponent.inject(this);
    }
    
    @Override
    protected void initializePresenter() {
        this.mPresenter = mCategoryPresenter;
        mCategoryPresenter.setView(this);
    }
    
    @Override
    protected void initializeActionBar(View rootView) {
        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(getString(R.string.txt_select_parent_category));
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
        }
    }
    
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getIntent() != null) {
            Intent intent = getIntent();
            mSelectedCategoryId = intent.getStringExtra("selected_cate_id");
            mCurrentType = intent.getStringExtra("type");
            mCurrentTransType = intent.getStringExtra("trans_type");
        }
        initializeView();
        if (TextUtil.isEmpty(mCurrentType)) {
            showEmpty();
            return;
        }
        mCategoryPresenter.getCategoryByType(mCurrentType, mCurrentTransType);
    }
    
    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        getMenuInflater().inflate(R.menu.menu_choose_parent_category, menu);
        final MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setOnQueryTextListener(mQueryTextListener);
        return super.onCreateOptionsMenu(menu);
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            case R.id.action_sort_a_z:
                sortCategory(SortType.A_Z);
                return true;
            case R.id.action_sort_z_a:
                sortCategory(SortType.Z_A);
                return true;
            
        }
        
        return super.onOptionsItemSelected(item);
    }
    
    public void initializeView() {
        mParentCategoryAdapter = new ListParentCategoryAdapter(this,
                  mOnSelectParentCategoryListener, null);
        mParentCategoryAdapter.setSelectedCategory(mSelectedCategoryId);
        
        mEmptyAdapter = new EmptyAdapter(this, getString(R.string.txt_no_data));
        
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
    
    @Override
    public void onFailure(String message) {
        mEmptyAdapter.setMessage(message);
        mRecyclerView.setAdapter(mEmptyAdapter);
    }
    
    @Override
    public void loading(boolean isLoading) {
        mProgressBar.setVisibility(isLoading ? View.VISIBLE : View.GONE);
    }
    
    @Override
    public void showCategory(List<Category> categories) {
        mParentCategoryAdapter.setData(categories);
        mRecyclerView.setAdapter(mParentCategoryAdapter);
    }
    
    @Override
    public void showEmpty() {
        mEmptyAdapter.setMessage(getString(R.string.txt_no_data));
        mRecyclerView.setAdapter(mEmptyAdapter);
    }
    
    private void finishChooseParentCategory(Category category) {
        if (category == null) {
            Toast.makeText(this, R.string.message_touch_to_select_category, Toast.LENGTH_SHORT)
                      .show();
            return;
        }
        
        Category cate = new Category(category.getId(), category.getName());
        
        Intent intent = new Intent();
        intent.putExtra("category_parent", cate);
        
        setResult(RESULT_OK, intent);
        finish();
        
    }
    
    private void searchCategory(String str) {
        if (mParentCategoryAdapter == null || mParentCategoryAdapter.isEmptyData()) {
            return;
        }
        mParentCategoryAdapter.filter(str);
    }
    
    private void sortCategory(SortType sortType) {
        if (mParentCategoryAdapter == null || mParentCategoryAdapter.isEmptyData()) {
            return;
        }
        List<Category> data = mParentCategoryAdapter.getData();
        if (data.size() == 1) {
            return;
        }
        
        List<Category> sort = SortUtil.sort(data, Category.class, "name", sortType);
        
        mParentCategoryAdapter.setData(sort);
        mParentCategoryAdapter.notifyDataSetChanged();
    }
}
