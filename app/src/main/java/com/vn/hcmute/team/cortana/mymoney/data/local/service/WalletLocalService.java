package com.vn.hcmute.team.cortana.mymoney.data.local.service;

import android.content.ContentValues;
import android.database.Cursor;
import com.vn.hcmute.team.cortana.mymoney.data.local.base.DatabaseHelper;
import com.vn.hcmute.team.cortana.mymoney.data.local.base.DbContentProvider;
import com.vn.hcmute.team.cortana.mymoney.model.Currencies;
import com.vn.hcmute.team.cortana.mymoney.model.Wallet;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

/**
 * Created by kunsubin on 10/3/2017.
 */

public class WalletLocalService extends DbContentProvider<Wallet> implements
                                                                  LocalService.WalletLocalService {
    
    public static final String TAG = WalletLocalService.class.getSimpleName();
    
    private final String TABLE_NAME = "tbl_wallet";
    private final String WALLET_ID = "wallet_id";
    private final String USER_ID = "user_id";
    private final String NAME = "name";
    private final String MONEY = "money";
    private final String CUR_ID = "cur_id";
    private final String ICON = "icon";
    private final String ARCHIVE = "archive";
    
    private CurrencyLocalService mCurrencyLocalService;
    
    
    public WalletLocalService(DatabaseHelper mDatabaseHelper) {
        super(mDatabaseHelper);
        mCurrencyLocalService = new CurrencyLocalService(mDatabaseHelper);
    }
    
    
    @Override
    protected String[] getAllColumns() {
        return new String[]{WALLET_ID, USER_ID, NAME, MONEY, CUR_ID, ICON, ARCHIVE};
    }
    
    @Override
    protected ContentValues createContentValues(Wallet values) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("wallet_id", values.getWalletid());
        contentValues.put("user_id", values.getUserid());
        contentValues.put("name", values.getWalletName());
        contentValues.put("money", values.getMoney());
        contentValues.put("cur_id", values.getCurrencyUnit().getCurId());
        contentValues.put("icon", values.getWalletImage());
        contentValues.put("archive", values.isArchive() ? "true" : "false");
        return contentValues;
    }
    
    @Override
    public Callable<List<Wallet>> getListWallet(final String userId) {
        
        return new Callable<List<Wallet>>() {
            @Override
            public List<Wallet> call() throws Exception {
                String selection = "user_id=?";
                String[] selectionArg = new String[]{userId};
                Cursor cursor = WalletLocalService.this
                          .query(TABLE_NAME, getAllColumns(), selection, selectionArg, null);
                if (cursor == null) {
                    return null;
                }
                List<Wallet> wallets = new ArrayList<>();
                while (cursor.moveToNext()) {
                    Wallet wallet = new Wallet();
                    wallet.setWalletid(cursor.getString(0));
                    wallet.setUserid(cursor.getString(1));
                    wallet.setWalletName(cursor.getString(2));
                    wallet.setMoney(cursor.getString(3));
                    //currencies 4
                    Currencies currencies = getCurrenciesId(cursor.getString(4));
                    if (currencies != null) {
                        wallet.setCurrencyUnit(currencies);
                    }
                    wallet.setWalletImage(cursor.getString(5));
                    wallet.setArchive(Boolean.parseBoolean(cursor.getString(6)));
                    
                    wallets.add(wallet);
                    
                }
                cursor.close();
                
                return wallets;
            }
        };
    }
    
    @Override
    public Callable<Long> addWallet(final Wallet wallet) {
        
        return new Callable<Long>() {
            @Override
            public Long call() throws Exception {
                ContentValues contentValues = createContentValues(wallet);
                return mDatabase.insert(TABLE_NAME, null, contentValues);
            }
        };
    }
    
    @Override
    public Callable<Integer> updateWallet(final Wallet wallet) {
        return new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                ContentValues contentValues = createContentValues(wallet);
                String whereClause = "wallet_id=?";
                return mDatabase.update(TABLE_NAME, contentValues, whereClause,
                          new String[]{wallet.getWalletid()});
            }
        };
    }
    
    @Override
    public Callable<Integer> deleteWallet(final String idWallet) {
        return new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                String whereClause = "wallet_id=?";
                return mDatabase.delete(TABLE_NAME, whereClause, new String[]{idWallet});
            }
        };
        
    }
    
    @Override
    public Callable<Integer> moveWallet(String idWalletFrom, String idWalletTo, String Money) {
        return null;
    }
    
    @Override
    public int updateMoneyWallet(final String idWallet, final String money) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("money", money);
        String whereClause = "wallet_id=?";
        return mDatabase.update(TABLE_NAME, contentValues, whereClause, new String[]{idWallet});
        
    }
    
    @Override
    public Wallet getWalletById(String idWallet) {
        String selection = "wallet_id=?";
        String[] selectionArg = new String[]{idWallet};
        Wallet wallet = null;
        Cursor cursor = WalletLocalService.this
                  .query(TABLE_NAME, getAllColumns(), selection, selectionArg, null);
        if (cursor.moveToFirst()) {
            wallet = new Wallet();
            wallet.setWalletid(cursor.getString(0));
            if (cursor.getString(0) != null) {
                wallet.setUserid(cursor.getString(1));
            }
            wallet.setWalletName(cursor.getString(2));
            wallet.setMoney(cursor.getString(3));
            //currencies 4
            Currencies currencies = getCurrenciesId(cursor.getString(4));
            if (currencies != null) {
                wallet.setCurrencyUnit(currencies);
            }
            wallet.setWalletImage(cursor.getString(5));
            wallet.setArchive(Boolean.parseBoolean(cursor.getString(6)));
        }
        return wallet;
    }
    
    public Currencies getCurrenciesId(String idCurrencies) {
        return mCurrencyLocalService.getCurrency(idCurrencies);
    }
}
