package com.vn.hcmute.team.cortana.mymoney.data.local.service;

import android.content.ContentValues;
import android.database.Cursor;
import com.vn.hcmute.team.cortana.mymoney.data.local.base.DatabaseHelper;
import com.vn.hcmute.team.cortana.mymoney.data.local.base.DbContentProvider;
import com.vn.hcmute.team.cortana.mymoney.model.Budget;
import com.vn.hcmute.team.cortana.mymoney.model.Category;
import com.vn.hcmute.team.cortana.mymoney.model.Wallet;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

/**
 * Created by kunsubin on 10/16/2017.
 */

public class BudgetLocalService extends DbContentProvider<Budget> implements
                                                                  LocalService.BudgetLocalService {
    
    public static final String TAG = BudgetLocalService.class.getSimpleName();
    private static BudgetLocalService sInstance;
    private final String TABLE_NAME = "tbl_budget";
    private final String ID = "budget_id";
    private final String RANGE_DATE = "range_date";
    private final String MONEY_GOAL = "money_goal";
    private final String STATUS = "status";
    private final String USER_ID = "user_id";
    private final String MONEY_EXPENSE = "money_expense";
    private final String WALLET_ID = "wallet_id";
    private final String CATE_ID = "cate_id";
    
    private BudgetLocalService(DatabaseHelper mDatabaseHelper) {
        super(mDatabaseHelper);
        
    }
    
    public static BudgetLocalService getInstance(DatabaseHelper databaseHelper) {
        if (sInstance == null) {
            synchronized (BudgetLocalService.class) {
                if (sInstance == null) {
                    sInstance = new BudgetLocalService(databaseHelper);
                }
            }
        }
        return sInstance;
    }
    
    @Override
    protected String[] getAllColumns() {
        return new String[]{ID, RANGE_DATE, MONEY_GOAL, STATUS, USER_ID, MONEY_EXPENSE, WALLET_ID,
                  CATE_ID};
    }
    
    @Override
    protected ContentValues createContentValues(Budget values) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("budget_id", values.getBudgetId());
        contentValues.put("range_date", values.getRangeDate());
        contentValues.put("money_goal", values.getMoneyGoal());
        contentValues.put("status", values.getStatus());
        contentValues.put("user_id", values.getUserid());
        contentValues.put("money_expense", values.getMoneyExpense());
        contentValues.put("wallet_id", values.getWallet().getWalletid());
        contentValues.put("cate_id", values.getCategory().getId());
        return contentValues;
    }
    
    @Override
    protected List<Budget> makeListObjectFromCursor(Cursor cursor) {
        List<Budget> budgets = new ArrayList<>();
        while (cursor.moveToNext()) {
            Budget budget = makeSingleObjectFromCursor(cursor);
            
            budgets.add(budget);
        }
        cursor.close();
        
        return budgets;
    }
    
    @Override
    protected Budget makeSingleObjectFromCursor(Cursor cursor) {
        Budget budget = new Budget();
        
        budget.setBudgetId(cursor.getString(0));
        budget.setRangeDate(cursor.getString(1));
        budget.setMoneyGoal(cursor.getString(2));
        budget.setStatus(cursor.getString(3));
        if (cursor.getString(4) != null) {
            budget.setUserid(cursor.getString(4));
        }
        budget.setMoneyExpense(cursor.getString(5));
        //get wallet
        Wallet wallet = getWalletById(cursor.getString(6));
        budget.setWallet(wallet);
        //getCategory
        Category category = getCategoryById(cursor.getString(7));
        budget.setCategory(category);
        
        return budget;
    }
    
    @Override
    public Callable<List<Budget>> getListBudget(final String userId) {
        return new Callable<List<Budget>>() {
            @Override
            public List<Budget> call() throws Exception {
                String selection = "user_id=?";
                String[] selectionArg = new String[]{userId};
                Cursor cursor = BudgetLocalService.this
                          .query(TABLE_NAME, getAllColumns(), selection, selectionArg, null);
                if (cursor == null) {
                    return null;
                }
                
                return makeListObjectFromCursor(cursor);
                
            }
        };
    }
    
    @Override
    public Callable<Long> addBudet(final Budget budget) {
        return new Callable<Long>() {
            @Override
            public Long call() throws Exception {
                ContentValues contentValues = createContentValues(budget);
                return mDatabase.insert(TABLE_NAME, null, contentValues);
            }
        };
    }
    
    @Override
    public Callable<Integer> updateBudget(final Budget budget) {
        return new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                ContentValues contentValues = createContentValues(budget);
                String selection = "budget_id=?";
                String[] selectionArg = new String[]{budget.getBudgetId()};
                return mDatabase.update(TABLE_NAME, contentValues, selection, selectionArg);
            }
        };
    }
    
    @Override
    public Callable<Integer> deleteBudget(final String idBudget) {
        return new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                String whereClause = "budget_id=?";
                return mDatabase.delete(TABLE_NAME, whereClause, new String[]{idBudget});
            }
        };
    }
    
    @Override
    public Callable<Integer> updateStatusBudget(final List<Budget> budgetList) {
       
        return new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                for (Budget budget: budgetList) {
                   updateItemBudget(budget);
                }
                return 1;
            }
        };
    }
    
    @Override
    public int deleteBudgetFromWallet(String wallet_id) {
        String whereClause = "wallet_id = ?";
        return mDatabase.delete(TABLE_NAME, whereClause, new String[]{whereClause});
    }
    public void updateItemBudget(Budget budget){
        budget.setStatus("1");
        ContentValues contentValues = createContentValues(budget);
        String selection = "budget_id=?";
        String[] selectionArg = new String[]{budget.getBudgetId()};
        mDatabase.update(TABLE_NAME, contentValues, selection, selectionArg);
    }
    public Wallet getWalletById(String idWallet) {
        return WalletLocalService.getInstance(mDatabaseHelper).getWalletById(idWallet);
    }
    
    public Category getCategoryById(String idCategory) {
        return CategoryLocalService.getInstance(mDatabaseHelper).getCategoryById(idCategory);
    }
}
