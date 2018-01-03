package com.vn.hcmute.team.cortana.mymoney.data.local.service;

import android.content.ContentValues;
import android.database.Cursor;
import com.vn.hcmute.team.cortana.mymoney.data.local.base.DatabaseHelper;
import com.vn.hcmute.team.cortana.mymoney.data.local.base.DbContentProvider;
import com.vn.hcmute.team.cortana.mymoney.data.local.service.LocalService.TransPersonLocalService;
import com.vn.hcmute.team.cortana.mymoney.model.Category;
import com.vn.hcmute.team.cortana.mymoney.model.DebtLoan;
import com.vn.hcmute.team.cortana.mymoney.model.Event;
import com.vn.hcmute.team.cortana.mymoney.model.Person;
import com.vn.hcmute.team.cortana.mymoney.model.Saving;
import com.vn.hcmute.team.cortana.mymoney.model.Transaction;
import com.vn.hcmute.team.cortana.mymoney.model.Wallet;
import com.vn.hcmute.team.cortana.mymoney.utils.TextUtil;
import com.vn.hcmute.team.cortana.mymoney.utils.logger.MyLogger;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

/**
 * Created by infamouSs on 11/17/17.
 */

public class TransactionLocalService extends DbContentProvider<Transaction> implements
                                                                            LocalService.TransactionLocalService {
    
    private static TransactionLocalService sInstance;
    
    private final String TABLE_NAME = "tbl_transaction";
    private final String ID = "trans_id";
    private final String AMOUNT = "amount";
    private final String NOTE = "note";
    private final String TYPE = "type";
    private final String USER_ID = "user_id";
    private final String WALLET = "wallet_id";
    private final String DATE_CREATE = "date_create";
    private final String DATE_END = "date_end";
    private final String EVENT = "event_id";
    private final String SAVING = "saving_id";
    private final String CATEGORY = "cate_id";
    
    private TransactionLocalService(DatabaseHelper databaseHelper) {
        super(databaseHelper);
    }
    
    
    public static TransactionLocalService getInstance(DatabaseHelper databaseHelper) {
        if (sInstance == null) {
            synchronized (TransactionLocalService.class) {
                if (sInstance == null) {
                    sInstance = new TransactionLocalService(databaseHelper);
                }
            }
        }
        
        return sInstance;
    }
    
    @Override
    protected String[] getAllColumns() {
        return new String[]{ID, AMOUNT, NOTE, TYPE, USER_ID, WALLET, DATE_CREATE, DATE_END,
                  EVENT, SAVING, CATEGORY};
    }
    
    @Override
    protected ContentValues createContentValues(Transaction values) {
        ContentValues contentValues = new ContentValues();
        
        contentValues.put(ID, values.getTrans_id());
        contentValues.put(AMOUNT, values.getAmount());
        contentValues.put(NOTE, values.getNote());
        contentValues.put(TYPE, values.getType());
        contentValues.put(USER_ID, values.getUser_id());
        contentValues.put(WALLET, values.getWallet().getWalletid());
        contentValues.put(DATE_CREATE, values.getDate_created());
        contentValues.put(DATE_END, values.getDate_end());
        contentValues.put(EVENT, values.getEvent() != null ? values.getEvent().getEventid() : "");
        contentValues
                  .put(SAVING, values.getSaving() != null ? values.getSaving().getSavingid() : "");
        contentValues
                  .put(CATEGORY, values.getCategory() != null ? values.getCategory().getId() : "");
        
        return contentValues;
    }
    
    @Override
    protected List<Transaction> makeListObjectFromCursor(Cursor cursor) {
        List<Transaction> transactions = new ArrayList<>();
        
        while (cursor.moveToNext()) {
            Transaction transaction = makeSingleObjectFromCursor(cursor);
            
            transactions.add(transaction);
        }
        
        cursor.close();
        
        return transactions;
    }
    
    @Override
    protected Transaction makeSingleObjectFromCursor(Cursor cursor) {
        Transaction transaction = new Transaction();
        
        String id = cursor.getString(0);
        String amount = cursor.getString(1);
        List<Person> persons = TransPersonService.getInstance(mDatabaseHelper)
                  .getPersonByTransactionId(id);
        String note = cursor.getString(2);
        String type = cursor.getString(3);
        String user_id = cursor.getString(4);
        String wallet_id = cursor.getString(5);
        String date_created = cursor.getString(6);
        String date_end = cursor.getString(7);
        String event_id = cursor.getString(8);
        String saving_id = cursor.getString(9);
        String cate_id = cursor.getString(10);
        
        Category category = CategoryLocalService.getInstance(mDatabaseHelper)
                  .getCategoryById(cate_id);
        
        Wallet wallet = WalletLocalService.getInstance(mDatabaseHelper)
                  .getWalletById(wallet_id);
        Event event = EventLocalService.getInstance(mDatabaseHelper)
                  .getEventById(event_id);
        Saving saving = SavingLocalService.getInstance(mDatabaseHelper)
                  .getSavingById(saving_id);
        
        transaction.setTrans_id(id);
        transaction.setAmount(amount);
        transaction.setPerson(persons);
        transaction.setNote(note);
        transaction.setUser_id(user_id);
        transaction.setDate_created(date_created);
        transaction.setDate_end(date_end);
        transaction.setWallet(wallet);
        transaction.setEvent(event);
        transaction.setSaving(saving);
        transaction.setType(type);
        transaction.setCategory(category);
        
        return transaction;
    }
    
    @Override
    public Callable<Long> addTransaction(final Transaction transaction) {
        return new Callable<Long>() {
            @Override
            public Long call() throws Exception {
                ContentValues contentValues = createContentValues(transaction);
                
                callTransPerson("add", transaction);
                updateMoneyWalletWhenAddOrDeleteTransaction("add", transaction);
                
                return mDatabase.insert(TABLE_NAME, null, contentValues);
            }
        };
    }
    
    
    @Override
    public Callable<Integer> updateTransaction(final Transaction transaction) {
        return new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                ContentValues contentValues = createContentValues(transaction);
                
                String selection = "trans_id = ?";
                String[] selectionArg = new String[]{transaction.getTrans_id()};
                
                callTransPerson("update", transaction);
                updateMoneyWalletWhenUpdateTransaction(transaction);
                updateOrDeleteDebtLoanWhenUpdateTransaction("update", transaction);
                
                return mDatabase.update(TABLE_NAME, contentValues, selection, selectionArg);
            }
        };
    }
    
    
    @Override
    public Callable<Integer> deleteTransaction(final Transaction transaction) {
        return new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                String selection = "trans_id = ?";
                String[] selectionArg = new String[]{transaction.getTrans_id()};
                
                updateMoneyWalletWhenAddOrDeleteTransaction("delete", transaction);
                
                updateOrDeleteDebtLoanWhenUpdateTransaction("delete", transaction);
                
                TransPersonService.getInstance(mDatabaseHelper).delete(transaction.getTrans_id());
                
                return mDatabase.delete(TABLE_NAME, selection, selectionArg);
            }
        };
    }
    
    public Callable<Integer> deleteTransactionByWallet(final String wallet_id) {
        return new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                String selection = "wallet_id = ?";
                String[] selectionArg = new String[]{wallet_id};
                
                //                updateMoneyWalletWhenAddOrDeleteTransaction("delete", transaction);
                //
                //                updateOrDeleteDebtLoanWhenUpdateTransaction("delete", transaction);
                //
                //                TransPersonService.getInstance(mDatabaseHelper).delete(transaction.getTrans_id());
                
                return mDatabase.delete(TABLE_NAME, selection, selectionArg);
            }
        };
    }
    
    @Override
    public Callable<List<Transaction>> getTransactionByTime(final String user_id,
              final String start, final String end, final String wallet_id) {
        return new Callable<List<Transaction>>() {
            @Override
            public List<Transaction> call() throws Exception {
                String selection;
                String[] selectionArg;
                
                if (TextUtil.isEmpty(wallet_id)) {
                    selection = "date_create >= ? and date_create <= ?";
                    selectionArg = new String[]{start, end};
                } else {
                    selection = "wallet_id = ? and date_create >= ? and date_create <= ?";
                    selectionArg = new String[]{wallet_id, start, end};
                }
                
                Cursor cursor = query(TABLE_NAME, getAllColumns(), selection, selectionArg, null);
                
                if (cursor == null) {
                    return null;
                }
                MyLogger.d("GETBYTIME", cursor.getCount());
                return makeListObjectFromCursor(cursor);
            }
        };
    }
    
    @Override
    public Transaction getTransactionById(String trans_id) {
        String selection = "trans_id = ?";
        String[] selectionArgs = new String[]{trans_id};
        
        Cursor cursor = this.query(TABLE_NAME, getAllColumns(), selection, selectionArgs, null);
        
        if (cursor == null) {
            return null;
        }
        
        Transaction transaction = null;
        
        if (cursor.moveToNext()) {
            transaction = makeSingleObjectFromCursor(cursor);
        }
        
        cursor.close();
        return transaction;
    }
    
    @Override
    public Callable<Transaction> getTransactionByIdUseCallable(final String trans_id) {
        return new Callable<Transaction>() {
            @Override
            public Transaction call() throws Exception {
                return getTransactionById(trans_id);
            }
        };
    }
    
    @Override
    public Callable<List<Transaction>> getAllTransaction(final String user_id) {
        return new Callable<List<Transaction>>() {
            @Override
            public List<Transaction> call() throws Exception {
                String selection = "user_id = ?";
                String[] selectionArg = new String[]{user_id};
                
                Cursor cursor = query(TABLE_NAME, getAllColumns(), selection, selectionArg, null);
                
                if (cursor == null) {
                    return null;
                }
                
                return makeListObjectFromCursor(cursor);
            }
        };
    }
    
    @Override
    public Callable<List<Transaction>> getTransactionBySaving(final String user_id, final String saving_id) {
        MyLogger.d("lang kdshfkdsf");
        return new Callable<List<Transaction>>() {
            @Override
            public List<Transaction> call() throws Exception {
                MyLogger.d("thingdsfs:",user_id);
                String selection = "user_id = ? and saving_id = ?";
                String[] selectionArg = new String[]{user_id, saving_id};
            
                Cursor cursor = query(TABLE_NAME, getAllColumns(), selection, selectionArg, null);
            
                if (cursor == null) {
                    return null;
                }
            
                return makeListObjectFromCursor(cursor);
            }
        };
    }
    
    @Override
    public Callable<List<Transaction>> getTransactionByEvent(final String user_id,
              final String event_id) {
        return new Callable<List<Transaction>>() {
            @Override
            public List<Transaction> call() throws Exception {
                String selection = "user_id = ? and event_id = ?";
                String[] selectionArg = new String[]{user_id, event_id};
                
                Cursor cursor = query(TABLE_NAME, getAllColumns(), selection, selectionArg, null);
                
                if (cursor == null) {
                    return null;
                }
                
                return makeListObjectFromCursor(cursor);
            }
        };
    }
    
    @Override
    public Callable<List<Transaction>> getTransactionByBudget(String user_id, final String start,
              final String end, final String cate_id, final String wallet_id) {
        return new Callable<List<Transaction>>() {
            @Override
            public List<Transaction> call() throws Exception {
                StringBuilder query = new StringBuilder(
                          "select " + makeSelectionTransaction(getAllColumns()) +
                          "from tbl_transaction, tbl_category " +
                          "where tbl_transaction.wallet_id = ? and " +
                          "tbl_transaction.cate_id = tbl_category.cate_id and " +
                          "tbl_category.cate_id = ? and " +
                          "date_create >= ? and date_create <= ?");
                
                Cursor cursor = rawQuery(query.toString(),
                          new String[]{wallet_id, cate_id, start, end});
                
                if (cursor == null) {
                    return null;
                }
                
                return makeListObjectFromCursor(cursor);
            }
        };
    }
    
    private void callTransPerson(String method, Transaction transaction) {
        if (transaction.getPerson() != null && !transaction.getPerson().isEmpty()) {
            TransPersonLocalService transPersonLocalService = TransPersonService
                      .getInstance(mDatabaseHelper);
            for (Person person : transaction.getPerson()) {
                String[] params = new String[]{transaction.getTrans_id(), person.getPersonid()};
                
                if (method.equals("add")) {
                    transPersonLocalService.add(params);
                } else if (method.equals("update")) {
                    transPersonLocalService.update(params);
                }
            }
        }
        
    }
    
    private void updateMoneyWalletWhenAddOrDeleteTransaction(String method, Transaction transaction)
              throws Exception {
        if (method.equals("add")) {
            if (transaction.getType().equals("income")) {
                WalletLocalService.getInstance(mDatabaseHelper)
                          .takeInWallet(transaction.getWallet().getWalletid(),
                                    transaction.getAmount()).call();
            } else if (transaction.getType().equals("expense")) {
                WalletLocalService.getInstance(mDatabaseHelper)
                          .takeOutWallet(transaction.getWallet().getWalletid(),
                                    transaction.getAmount()).call();
            }
        } else if (method.equals("delete")) {
            DebtLoan debtLoan = DebtLoanLocalService.getInstance(mDatabaseHelper)
                      .getDebtLoanByTransactionId(transaction.getTrans_id());
            if (debtLoan.getStatus() == 1) {
                return;
            }
            
            if (transaction.getType().equals("income")) {
                WalletLocalService.getInstance(mDatabaseHelper)
                          .takeOutWallet(transaction.getWallet().getWalletid(),
                                    transaction.getAmount()).call();
            } else if (transaction.getType().equals("expense")) {
                WalletLocalService.getInstance(mDatabaseHelper)
                          .takeInWallet(transaction.getWallet().getWalletid(),
                                    transaction.getAmount()).call();
            }
        }
        
    }
    
    private void updateMoneyWalletWhenUpdateTransaction(Transaction transaction) throws Exception {
        Transaction transactionBeforeUpdate = getTransactionById(transaction.getTrans_id());
        
        if (!transactionBeforeUpdate.getWallet().getWalletid()
                  .equals(transaction.getWallet().getWalletid())) {
            WalletLocalService.getInstance(mDatabaseHelper)
                      .takeInWallet(transactionBeforeUpdate.getWallet().getWalletid(),
                                transaction.getAmount()).call();
            WalletLocalService.getInstance(mDatabaseHelper)
                      .takeOutWallet(transaction.getWallet().getWalletid(),
                                transaction.getAmount()).call();
        }
    }
    
    private void updateOrDeleteDebtLoanWhenUpdateTransaction(String method,
              Transaction transaction) {
        StringBuilder query = new StringBuilder();
        if (method.equals("update")) {
            query.append("Update tbl_debtloan set type = ? where trans_id = ?");
            String typeDebtLoan = "";
            if (transaction.getType().equals("income")) {
                typeDebtLoan = "debt";
            } else if (transaction.getType().equals("expense")) {
                typeDebtLoan = "loan";
            }
            
            mDatabase.rawQuery(query.toString(),
                      new String[]{typeDebtLoan, transaction.getTrans_id()});
        } else if (method.equals("delete")) {
            DebtLoanLocalService.getInstance(mDatabaseHelper)
                      .deleteDebtLoanByTransaction(transaction.getTrans_id());
        }
    }
    
    
    private String makeSelectionTransaction(String[] columns) {
        StringBuilder builder = new StringBuilder();
        int lengthArr = columns.length;
        
        for (int i = 0; i < lengthArr; i++) {
            if (i == lengthArr - 1) {
                builder.append(TABLE_NAME)
                          .append(".")
                          .append(columns[i]);
            } else {
                builder.append(TABLE_NAME)
                          .append(".")
                          .append(columns[i])
                          .append(", ");
            }
        }
        return builder.toString();
    }
    
}
