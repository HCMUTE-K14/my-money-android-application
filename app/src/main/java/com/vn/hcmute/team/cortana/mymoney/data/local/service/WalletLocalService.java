package com.vn.hcmute.team.cortana.mymoney.data.local.service;

import android.content.ContentValues;
import android.database.Cursor;
import com.vn.hcmute.team.cortana.mymoney.data.local.base.DatabaseHelper;
import com.vn.hcmute.team.cortana.mymoney.data.local.base.DbContentProvider;
import com.vn.hcmute.team.cortana.mymoney.model.Category;
import com.vn.hcmute.team.cortana.mymoney.model.Currencies;
import com.vn.hcmute.team.cortana.mymoney.model.Transaction;
import com.vn.hcmute.team.cortana.mymoney.model.Wallet;
import com.vn.hcmute.team.cortana.mymoney.utils.SecurityUtil;
import com.vn.hcmute.team.cortana.mymoney.utils.logger.MyLogger;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

/**
 * Created by kunsubin on 10/3/2017.
 */

public class WalletLocalService extends DbContentProvider<Wallet> implements
                                                                  LocalService.WalletLocalService {
    
    public static final String TAG = WalletLocalService.class.getSimpleName();
    private static WalletLocalService sInstance;
    public final Category WITH_DRAW_CATEGORY = new Category("48", "Withdrawal", "icon_withdrawal",
              "expense",
              "expense");
    public final Category GIFT_CATEGORY = new Category("53", "Gift", "ic_category_give", "income",
              "income");
    private final String TABLE_NAME = "tbl_wallet";
    private final String WALLET_ID = "wallet_id";
    private final String USER_ID = "user_id";
    private final String NAME = "name";
    private final String MONEY = "money";
    private final String CUR_ID = "cur_id";
    private final String ICON = "icon";
    private final String ARCHIVE = "archive";
    
    private WalletLocalService(DatabaseHelper mDatabaseHelper) {
        super(mDatabaseHelper);
        
    }
    
    public static WalletLocalService getInstance(DatabaseHelper databaseHelper) {
        if (sInstance == null) {
            synchronized (BudgetLocalService.class) {
                if (sInstance == null) {
                    sInstance = new WalletLocalService(databaseHelper);
                }
            }
        }
        return sInstance;
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
    protected List<Wallet> makeListObjectFromCursor(Cursor cursor) {
        List<Wallet> wallets = new ArrayList<>();
        while (cursor.moveToNext()) {
            Wallet wallet = makeSingleObjectFromCursor(cursor);
            
            wallets.add(wallet);
            
        }
        cursor.close();
        
        return wallets;
    }
    
    @Override
    protected Wallet makeSingleObjectFromCursor(Cursor cursor) {
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
        
        return wallet;
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
                
                return makeListObjectFromCursor(cursor);
                
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
                
                BudgetLocalService.getInstance(mDatabaseHelper)
                          .deleteBudgetFromWallet(idWallet);
                
                SavingLocalService.getInstance(mDatabaseHelper)
                          .deleteSavingByWallet(idWallet);
                
                EventLocalService.getInstance(mDatabaseHelper)
                          .deleteEventByWallet(idWallet);
                
                String whereClause = "wallet_id=?";
                
                return mDatabase.delete(TABLE_NAME, whereClause, new String[]{idWallet});
            }
        };
        
    }
    
    @Override
    public Callable<Integer> moveWallet(final String userid, final String wallet_id_from,
              final String wallet_id_to,
              final String moneyMinus, final String moneyPlus, final String date_created) {
        return new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                boolean flagAddTransactionFrom = addTransaction(0, userid, wallet_id_from,
                          moneyMinus,
                          date_created);
                
                boolean flagAddTransactionTo = addTransaction(1, userid, wallet_id_to, moneyPlus,
                          date_created);
                
                return flagAddTransactionFrom && flagAddTransactionTo ? 1 : -1;
            }
        };
    }
    
    private boolean addTransaction(int type, String userid, String wallet_id, String amount,
              String date_created) {
        Transaction transaction = prepareTransaction(type, userid, wallet_id, amount, date_created);
        
        long result = -1;
        try {
            result = TransactionLocalService.getInstance(mDatabaseHelper)
                      .addTransaction(transaction).call();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result > 0;
    }
    
    private Transaction prepareTransaction(int type, String userid, String wallet_id, String amount,
              String date_created) {
        
        Transaction transaction = new Transaction();
        
        transaction.setTrans_id(SecurityUtil.getRandomUUID());
        
        transaction.setDate_created(date_created);
        if (type == 0) {
            transaction.setCategory(WITH_DRAW_CATEGORY);
            transaction.setType("expense");
        } else if (type == 1) {
            transaction.setCategory(GIFT_CATEGORY);
            transaction.setType("income");
        }
        
        Wallet walletFrom = getWalletById(wallet_id);
        
        transaction.setWallet(walletFrom);
        transaction.setUser_id(userid);
        transaction.setAmount(amount);
        MyLogger.d(TAG, transaction, true);
        return transaction;
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
            wallet = makeSingleObjectFromCursor(cursor);
        }
        cursor.close();
        return wallet;
    }
    
    @Override
    public Callable<Integer> takeInWallet(final String wallet_id, final String money) {
        return new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                String[] selectionArg = new String[]{money, wallet_id};
                String query = "Update tbl_wallet set money = money + ? where wallet_id = ?";
                Cursor cursor = mDatabase
                          .rawQuery(query,
                                    selectionArg);
                
                if (cursor.getCount() >= 0) {
                    cursor.close();
                    return 1;
                } else {
                    cursor.close();
                    return -1;
                }
            }
        };
    }
    
    @Override
    public Callable<Integer> takeOutWallet(final String wallet_id, final String money) {
        return new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                String[] selectionArg = new String[]{money, wallet_id};
                String query = "Update tbl_wallet set money = money - ? where wallet_id = ?";
                Cursor cursor = mDatabase
                          .rawQuery(query,
                                    selectionArg);
                
                if (cursor.getCount() >= 0) {
                    cursor.close();
                    return 1;
                } else {
                    cursor.close();
                    return -1;
                }
            }
        };
    }
    
    public Currencies getCurrenciesId(String idCurrencies) {
        return CurrencyLocalService.getInstance(mDatabaseHelper).getCurrency(idCurrencies);
    }
}
