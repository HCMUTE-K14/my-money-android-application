package com.vn.hcmute.team.cortana.mymoney.ui.category;

import static android.app.Activity.RESULT_OK;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ExpandableListView;
import android.widget.ProgressBar;
import android.widget.TextView;
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
import com.vn.hcmute.team.cortana.mymoney.ui.base.BaseFragment;
import com.vn.hcmute.team.cortana.mymoney.ui.category.CategoryByTypeAdapter.CategoryListener;
import com.vn.hcmute.team.cortana.mymoney.ui.category.CategoryContract.DeleteView;
import com.vn.hcmute.team.cortana.mymoney.usecase.base.Action;
import com.vn.hcmute.team.cortana.mymoney.utils.Constraints.RequestCode;
import com.vn.hcmute.team.cortana.mymoney.utils.sort.SortType;
import com.vn.hcmute.team.cortana.mymoney.utils.sort.SortUtil;
import java.util.List;
import javax.inject.Inject;

/**
 * Created by infamouSs on 9/16/17.
 */

public class CategoryByTypeFragment extends BaseFragment implements CategoryContract.ShowView,
                                                                    DeleteView {
    
    public static final int MODE_ENABLE_CHOOSE_CATEGORY = 0;
    public static final int MODE_DISABLE_CHOOSE_CATEGORY = 1;
    
    public static final String TAG = CategoryByTypeFragment.class.getSimpleName();
    
    @BindView(R.id.expandable_list_view)
    ExpandableListView mExpandableListView;
    
    @BindView(R.id.progress_bar)
    ProgressBar mProgressBar;
    
    @Inject
    CategoryPresenter mCategoryPresenter;
    
    private CategoryByTypeAdapter mCategoryByTypeAdapter;
    private CategoryEmptyAdapter mEmptyAdapter;
    private String mCurrentCategoryId;
    private String mAction;
    private String mTransType;
    private boolean isExpand = true;
    private int mMode;
    
    private OnClickListener mAddCategoryListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            openManagerCategoryActivity(Action.ACTION_ADD_CATEGORY, null);
        }
    };
    private CategoryListener mChooseCategoryListener = new CategoryListener() {
        
        @Override
        public void onClickEdit(int groupPosition, int childPosition, Category category) {
            openManagerCategoryActivity(Action.ACTION_UPDATE_CATEGORY, category);
        }
        
        @Override
        public void onClickRemove(int groupPosition, int childPosition, Category category) {
            deleteCategory(category);
        }
        
        @Override
        public void onChooseCategory(int groupPosition, int childPosition, Category category) {
            if (mMode == MODE_ENABLE_CHOOSE_CATEGORY) {
                finishChooseCategory(category);
            }
        }
    };
    
    public CategoryByTypeFragment() {
        
    }
    
    public static CategoryByTypeFragment newInstance(int mode, String action, String categoryId) {
        CategoryByTypeFragment categoryByTypeFragment = new CategoryByTypeFragment();
        Bundle bundle = new Bundle();
        switch (action) {
            case Action.ACTION_GET_INCOMING_CATEGORY:
                bundle.putString("action", action);
                bundle.putString("cate_id", categoryId);
                bundle.putString("trans_type", "income");
                break;
            case Action.ACTION_GET_EXPENSE_CATEGORY:
                bundle.putString("action", action);
                bundle.putString("cate_id", categoryId);
                bundle.putString("trans_type", "expense");
                break;
            case Action.ACTION_GET_DEBT_LOAN_CATEGORY:
                bundle.putString("action", action);
                bundle.putString("cate_id", categoryId);
                bundle.putString("trans_type", "debt_loan");
                break;
            default:
                throw new RuntimeException("wrong action type");
        }
        bundle.putInt("mode", mode);
        
        categoryByTypeFragment.setArguments(bundle);
        
        return categoryByTypeFragment;
    }
    
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        if (getArguments() != null) {
            Bundle bundle = getArguments();
            mAction = bundle.getString("action");
            mCurrentCategoryId = bundle.getString("cate_id");
            mTransType = bundle.getString("trans_type");
            mMode = bundle.getInt("mode");
        }
    }
    
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_main_category;
    }
    
    @Override
    protected void initializeDagger() {
        ApplicationComponent applicationComponent = ((MyMoneyApplication) this.getActivity()
                  .getApplication()).getAppComponent();
        
        CategoryComponent categoryComponent = DaggerCategoryComponent.builder()
                  .applicationComponent(applicationComponent)
                  .activityModule(new ActivityModule(this.getActivity()))
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
        
    }
    
    @Override
    public void onCreateOptionsMenu(final Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
    }
    
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_collapse_expand) {
            if (isExpand) {
                isExpand = false;
                item.setIcon(R.drawable.ic_collapse_all);
            } else {
                isExpand = true;
                item.setIcon(R.drawable.ic_expand_all);
            }
            expandsOrCollapse(isExpand);
        } else if (item.getItemId() == R.id.action_sort_a_z) {
            sortData(SortType.A_Z);
        } else if (item.getItemId() == R.id.action_sort_z_a) {
            sortData(SortType.Z_A);
        }
        return super.onOptionsItemSelected(item);
    }
    
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initializeView();
        getCategory();
    }
    
    @Override
    public void onDestroyView() {
        mCategoryPresenter.unSubscribe();
        super.onDestroyView();
    }
    
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            getCategory();
        }
    }
    
    public void initializeView() {
        mCategoryByTypeAdapter = new CategoryByTypeAdapter(this.getContext(),
                  mChooseCategoryListener,
                  null);
        mCategoryByTypeAdapter.setCategorySelected(mCurrentCategoryId);
        
        mEmptyAdapter = new CategoryEmptyAdapter(this.getContext());
        
        View footer = LayoutInflater.from(this.getActivity())
                  .inflate(R.layout.item_view_footer_category, null);
        TextView textViewAddAction = (TextView) footer.findViewById(R.id.text_view);
        
        footer.setOnClickListener(mAddCategoryListener);
        textViewAddAction.setText(getStringAddAction());
        
        mExpandableListView.addFooterView(footer);
    }
    
    @Override
    public void showCategory(List<Category> categories) {
        
        mCategoryByTypeAdapter.setData(categories);
        
        mExpandableListView.setAdapter(mCategoryByTypeAdapter);
        
        isExpand = true;
        
        expandsOrCollapse(isExpand);
    }
    
    @Override
    public void onDeleteSuccessCategory(String message, Category category) {
        getCategory();
        String mess = getString(R.string.message_delete_category_success, category.getName());
        Toast.makeText(this.getContext(), mess, Toast.LENGTH_SHORT).show();
    }
    
    @Override
    public void showEmpty() {
        
        mExpandableListView.setAdapter(mEmptyAdapter);
        Snackbar.make(this.view, getString(R.string.txt_no_data), Snackbar.LENGTH_SHORT).show();
    }
    
    @Override
    public void onFailure(String message) {
        Snackbar.make(this.view, message, Snackbar.LENGTH_SHORT).show();
    }
    
    @Override
    public void loading(boolean isLoading) {
        mProgressBar.setVisibility(isLoading ? View.VISIBLE : View.GONE);
    }
    
    
    public void reloadData() {
        getCategory();
    }
    
    private String getStringAddAction() {
        
        switch (mAction) {
            case Action.ACTION_GET_INCOMING_CATEGORY:
                return getString(R.string.txt_new_incoming_cate);
            case Action.ACTION_GET_EXPENSE_CATEGORY:
                return getString(R.string.txt_new_expense_cate);
            case Action.ACTION_GET_DEBT_LOAN_CATEGORY:
                return getString(R.string.txt_new_debt_loan_cate);
            default:
                return "";
        }
    }
    
    private void finishChooseCategory(Category category) {
        Intent intent = new Intent();
        
        category.setSubcategories(null);
        intent.putExtra("category", category);
        getActivity().setResult(RESULT_OK, intent);
        
        getActivity().finish();
    }
    
    private void getCategory() {
        mCategoryPresenter.getCategory(mAction);
    }
    
    private void sortData(SortType sortType) {
        if (mCategoryByTypeAdapter == null || mCategoryByTypeAdapter.isEmptyData()) {
            return;
        }
        List<Category> data = mCategoryByTypeAdapter.getData();
        if (data.size() == 1) {
            return;
        }
        
        List<Category> sort = SortUtil.sort(data, Category.class, "name", sortType);
        
        mCategoryByTypeAdapter.setData(sort);
        mCategoryByTypeAdapter.notifyDataSetChanged();
    }
    
    
    private void expandsOrCollapse(boolean isExpand) {
        for (int i = 0; i < mCategoryByTypeAdapter.getGroupCount(); i++) {
            if (isExpand) {
                mExpandableListView.expandGroup(i);
            } else {
                mExpandableListView.collapseGroup(i);
            }
        }
    }
    
    private void openManagerCategoryActivity(String action, @Nullable Category category) {
        Intent intent = new Intent(this.getContext(), ManagerCategoryActivity.class);
        intent.putExtra("action", action);
        intent.putExtra("trans_type", mTransType);
        if (action.equals(Action.ACTION_ADD_CATEGORY)) {
            startActivityForResult(intent, RequestCode.ADD_CATEGORY_REQUEST_CODE);
        } else if (action.equals(Action.ACTION_UPDATE_CATEGORY)) {
            intent.putExtra("category", category);
            startActivityForResult(intent, RequestCode.UPDATE_CATEGORY_REQUEST_CODE);
        }
    }
    
    private void deleteCategory(Category category) {
        mCategoryPresenter.deleteCategory(category);
    }
    
    
}
