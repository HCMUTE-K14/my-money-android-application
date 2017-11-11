package com.vn.hcmute.team.cortana.mymoney.ui.statistics.fragment;

import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import butterknife.BindView;
import com.vn.hcmute.team.cortana.mymoney.R;
import com.vn.hcmute.team.cortana.mymoney.model.Category;
import com.vn.hcmute.team.cortana.mymoney.model.Transaction;
import com.vn.hcmute.team.cortana.mymoney.model.Wallet;
import com.vn.hcmute.team.cortana.mymoney.ui.base.BaseFragment;
import com.vn.hcmute.team.cortana.mymoney.ui.statistics.Objects.ObjectByCategory;
import com.vn.hcmute.team.cortana.mymoney.ui.statistics.adapters.ByCategoryAdapter;
import com.vn.hcmute.team.cortana.mymoney.ui.statistics.adapters.ByCategoryAdapter.ItemClickListener;
import com.vn.hcmute.team.cortana.mymoney.ui.statistics.transaction.TransactionStatisticsActivity;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * Created by kunsubin on 11/4/2017.
 */

public class FragmentByCategory extends BaseFragment {
    
    public static final int ID_EXPENSE = 1;
    public static final int ID_INCOME = 2;
    public static final String TYPE_EXPENSE = "expense";
    public static final String TYPE_INCOME = "income";
    @BindView(R.id.recycler_view_by_category)
    RecyclerView mRecyclerView;
    ByCategoryAdapter.ItemClickListener mItemClickListener = new ItemClickListener() {
        @Override
        public void onItemClick(ObjectByCategory objectByCategory) {
            Intent intent = new Intent(getActivity(), TransactionStatisticsActivity.class);
            intent.putExtra("type", "2");
            intent.putExtra("data", objectByCategory);
            getActivity().startActivity(intent);
        }
    };
    private List<Transaction> mTransactions;
    private int mIdCategory;
    private List<ObjectByCategory> mObjectByCategories;
    private List<Category> mCategories;
    private ByCategoryAdapter mTransactionByCategoryAdapter;
    private Wallet mWallet;
    
    public FragmentByCategory(List<Transaction> transactions, int idCategory, Wallet wallet) {
        this.mTransactions = transactions;
        this.mIdCategory = idCategory;
        this.mWallet = wallet;
    }
    
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_statistics_by_category;
    }
    
    @Override
    protected void initializeDagger() {

    }
    
    @Override
    protected void initializePresenter() {

    }
    
    @Override
    protected void initializeActionBar(View rootView) {

    }
    
    @Override
    protected void initialize() {
        mRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 1));
        mObjectByCategories = new ArrayList<>();
        mCategories = new ArrayList<>();
        getData();
        showData();
        
    }
    
    public void getData() {
        if (mIdCategory == ID_EXPENSE) {
            getDataExpense();
            return;
        }
        if (mIdCategory == ID_INCOME) {
            getDataInCome();
            return;
        }
    }
    
    public void getDataExpense() {
        mObjectByCategories.clear();
        mCategories.clear();
        for (Transaction transaction : mTransactions) {
            if (transaction.getType().equals(TYPE_EXPENSE)) {
                mCategories.add(transaction.getCategory());
            }
        }
        mCategories = new ArrayList<Category>(new HashSet<Category>(mCategories));
        
        if (mCategories != null && !mCategories.isEmpty()) {
            for (Category category : mCategories) {
                ObjectByCategory objectByCategory = new ObjectByCategory();
                List<Transaction> list = new ArrayList<>();
                double sumMoneyExpense = 0;
                for (Transaction transaction : mTransactions) {
                    if (transaction.getCategory().equals(category) &&
                        transaction.getType().equals(TYPE_EXPENSE)) {
                        list.add(transaction);
                        sumMoneyExpense += Double.parseDouble(transaction.getAmount());
                    }
                }
                objectByCategory.setCategory(category);
                objectByCategory.setTransactionList(list);
                objectByCategory.setMoneyExpense(sumMoneyExpense + "");
                
                mObjectByCategories.add(objectByCategory);
            }
        }
        
        
    }
    
    public void getDataInCome() {
        mObjectByCategories.clear();
        mCategories.clear();
        for (Transaction transaction : mTransactions) {
            if (transaction.getType().equals(TYPE_INCOME)) {
                mCategories.add(transaction.getCategory());
            }
        }
        mCategories = new ArrayList<Category>(new HashSet<Category>(mCategories));
        
        if (mCategories != null && !mCategories.isEmpty()) {
            for (Category category : mCategories) {
                ObjectByCategory objectByCategory = new ObjectByCategory();
                List<Transaction> list = new ArrayList<>();
                double sumMoneyExpense = 0;
                for (Transaction transaction : mTransactions) {
                    if (transaction.getCategory().equals(category) &&
                        transaction.getType().equals(TYPE_INCOME)) {
                        list.add(transaction);
                        sumMoneyExpense += Double.parseDouble(transaction.getAmount());
                    }
                }
                objectByCategory.setCategory(category);
                objectByCategory.setTransactionList(list);
                objectByCategory.setMoneyExpense(sumMoneyExpense + "");
                
                mObjectByCategories.add(objectByCategory);
            }
        }
    }
    
    public void showData() {
        mTransactionByCategoryAdapter = new ByCategoryAdapter(getActivity(), mIdCategory,
                  mObjectByCategories, mWallet);
        mTransactionByCategoryAdapter.setClickListener(mItemClickListener);
        mRecyclerView.setAdapter(mTransactionByCategoryAdapter);
    }
}
