package com.vn.hcmute.team.cortana.mymoney.data.local.service;

import android.content.ContentValues;
import android.database.Cursor;
import com.vn.hcmute.team.cortana.mymoney.data.local.base.DatabaseHelper;
import com.vn.hcmute.team.cortana.mymoney.data.local.base.DbContentProvider;
import com.vn.hcmute.team.cortana.mymoney.model.Currencies;
import com.vn.hcmute.team.cortana.mymoney.model.Saving;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

/**
 * Created by kunsubin on 10/3/2017.
 */

public class SavingLocalService extends DbContentProvider<Saving> implements
                                                                  LocalService.SavingLocalService {
    
    public static final String TAG = SavingLocalService.class.getSimpleName();
    private static SavingLocalService sInstance;
    private final String TABLE_NAME = "tbl_saving";
    private final String ID = "saving_id";
    private final String NAME = "name";
    private final String GOAL_MONEY = "goal_money";
    private final String START_MONEY = "start_money";
    private final String CURRENT_MONEY = "current_money";
    private final String DATE = "date";
    private final String WALLET_ID = "wallet_id";
    private final String CUR_ID = "cur_id";
    private final String STATUS = "status";
    private final String USER_ID = "user_id";
    private final String ICON = "icon";
    CurrencyLocalService mCurrencyLocalService;
    WalletLocalService mWalletLocalService;
    
    private SavingLocalService(DatabaseHelper mDatabaseHelper) {
        super(mDatabaseHelper);
        mCurrencyLocalService = CurrencyLocalService.getInstance(mDatabaseHelper);
        mWalletLocalService = WalletLocalService.getInstance(mDatabaseHelper);
    }
    
    public static SavingLocalService getInstance(DatabaseHelper databaseHelper) {
        if (sInstance == null) {
            synchronized (BudgetLocalService.class) {
                if (sInstance == null) {
                    sInstance = new SavingLocalService(databaseHelper);
                }
            }
        }
        return sInstance;
    }
    
    @Override
    protected String[] getAllColumns() {
        return new String[]{ID, NAME, GOAL_MONEY, START_MONEY, CURRENT_MONEY, DATE, WALLET_ID,
                  CUR_ID, STATUS, USER_ID, ICON};
    }
    
    @Override
    protected ContentValues createContentValues(Saving values) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("saving_id", values.getSavingid());
        contentValues.put("name", values.getName());
        contentValues.put("goal_money", values.getGoalMoney());
        contentValues.put("start_money", values.getStartMoney());
        contentValues.put("current_money", values.getCurrentMoney());
        contentValues.put("date", values.getDate());
        contentValues.put("wallet_id", values.getIdWallet());
        contentValues.put("cur_id", values.getCurrencies().getCurId());
        contentValues.put("status", values.getStatus());
        contentValues.put("user_id", values.getUserid());
        contentValues.put("icon", values.getIcon());
        return contentValues;
    }
    
    @Override
    public Callable<List<Saving>> getListSaving(final String userId) {
        return new Callable<List<Saving>>() {
            @Override
            public List<Saving> call() throws Exception {
                String selection = "user_id=?";
                String[] selectionArg = new String[]{userId};
                Cursor cursor = SavingLocalService.this
                          .query(TABLE_NAME, getAllColumns(), selection, selectionArg, null);
                
                if (cursor == null) {
                    return null;
                }
                List<Saving> savings = new ArrayList<>();
                while (cursor.moveToNext()) {
                    Saving saving = new Saving();
                    saving.setSavingid(cursor.getString(0));
                    saving.setName(cursor.getString(1));
                    saving.setGoalMoney(cursor.getString(2));
                    saving.setStartMoney(cursor.getString(3));
                    saving.setCurrentMoney(cursor.getString(4));
                    saving.setDate(cursor.getString(5));
                    if (cursor.getString(6) == null) {
                        saving.setIdWallet("");
                    } else {
                        saving.setIdWallet(cursor.getString(6));
                    }
                    //currencies 7
                    Currencies currencies = getCurrencies(cursor.getString(7));
                    saving.setCurrencies(currencies);
                    saving.setStatus(cursor.getString(8));
                    if (cursor.getString(9) != null) {
                        saving.setUserid(cursor.getString(9));
                    }
                    saving.setIcon(cursor.getString(10));
                    
                    savings.add(saving);
                    
                    
                }
                cursor.close();
                return savings;
            }
        };
    }
    
    @Override
    public Callable<Long> addSaving(final Saving saving) {
        return new Callable<Long>() {
            @Override
            public Long call() throws Exception {
                ContentValues contentValues = createContentValues(saving);
                return mDatabase.insert(TABLE_NAME, null, contentValues);
            }
        };
    }
    
    @Override
    public Callable<Integer> updateSaving(final Saving saving) {
        return new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                ContentValues contentValues = createContentValues(saving);
                String selection = "saving_id=?";
                String[] selectionArg = new String[]{saving.getSavingid()};
                return mDatabase.update(TABLE_NAME, contentValues, selection, selectionArg);
            }
        };
    }
    
    @Override
    public Callable<Integer> deleteSaving(final String saving_id) {
        
        return new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                String whereClause = "saving_id=?";
                return mDatabase.delete(TABLE_NAME, whereClause, new String[]{saving_id});
            }
        };
    }
    
    @Override
    public Callable<Integer> takeInSaving(final String idWallet, final String idSaving,
              final String moneyWallet,
              final String moneySaving) {
        
        return new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                return takeSaving(idWallet, idSaving, moneyWallet, moneySaving);
            }
        };
    }
    
    @Override
    public Callable<Integer> takeOutSaving(
              final String idWallet, final String idSaving, final String moneyWallet,
              final String moneySaving) {
        return new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                return takeSaving(idWallet, idSaving, moneyWallet, moneySaving);
            }
        };
    }
    
    public int takeSaving(String idWallet, String idSaving, String moneyWallet,
              String moneySaving) {
        //wallet
        int updateMoneyWallet = mWalletLocalService.updateMoneyWallet(idWallet, moneyWallet);
        //saving
        ContentValues contentValuesSaving = new ContentValues();
        contentValuesSaving.put("current_money", moneySaving);
        int updateMoneySaving = mDatabase
                  .update(TABLE_NAME, contentValuesSaving, "saving_id=?", new String[]{idSaving});
        if (updateMoneyWallet > 0 && updateMoneySaving > 0) {
            return 1;
        }
        return -1;
    }
    
    public Currencies getCurrencies(String idCurrencies) {
        return mCurrencyLocalService.getCurrency(idCurrencies);
    }
}
