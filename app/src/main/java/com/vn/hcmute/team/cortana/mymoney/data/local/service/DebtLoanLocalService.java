package com.vn.hcmute.team.cortana.mymoney.data.local.service;

import android.content.ContentValues;
import android.database.Cursor;
import com.vn.hcmute.team.cortana.mymoney.data.local.base.DatabaseHelper;
import com.vn.hcmute.team.cortana.mymoney.data.local.base.DbContentProvider;
import com.vn.hcmute.team.cortana.mymoney.model.DebtLoan;
import com.vn.hcmute.team.cortana.mymoney.model.Transaction;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

/**
 * Created by infamouSs on 11/19/17.
 */

public class DebtLoanLocalService extends DbContentProvider<DebtLoan> implements
                                                                      LocalService.DebtLoanLocalService {
    
    public static final String TAG = DebtLoanLocalService.class.getSimpleName();
    private static DebtLoanLocalService sInstance;
    private final String TABLE_NAME = "tbl_debtloan";
    private final String ID = "debt_loan_id";
    private final String NOTE = "note";
    private final String TRANSACTION_ID = "trans_id";
    private final String TYPE = "type";
    private final String STATUS = "status";
    private final String USER_ID = "user_id";
    
    private DebtLoanLocalService(
              DatabaseHelper mDatabaseHelper) {
        super(mDatabaseHelper);
    }
    
    public static DebtLoanLocalService getInstance(DatabaseHelper databaseHelper) {
        if (sInstance == null) {
            synchronized (DebtLoanLocalService.class) {
                if (sInstance == null) {
                    sInstance = new DebtLoanLocalService(databaseHelper);
                }
            }
        }
        
        return sInstance;
    }
    
    @Override
    protected String[] getAllColumns() {
        return new String[]{ID, NOTE, TRANSACTION_ID, TYPE, STATUS, USER_ID};
    }
    
    @Override
    protected ContentValues createContentValues(DebtLoan values) {
        ContentValues contentValues = new ContentValues();
        
        contentValues.put(ID, values.getDebt_loan_id());
        contentValues.put(NOTE, values.getNote());
        contentValues.put(TRANSACTION_ID, values.getTransaction().getTrans_id());
        contentValues.put(TYPE, values.getType());
        contentValues.put(STATUS, values.getStatus());
        contentValues.put(USER_ID, values.getUser_id());
        
        return contentValues;
    }
    
    @Override
    protected List<DebtLoan> makeListObjectFromCursor(Cursor cursor) {
        List<DebtLoan> debtLoans = new ArrayList<>();
        while (cursor.moveToNext()) {
            DebtLoan debtLoan = makeSingleObjectFromCursor(cursor);
            debtLoans.add(debtLoan);
        }
        cursor.close();
        return debtLoans;
    }
    
    @Override
    protected DebtLoan makeSingleObjectFromCursor(Cursor cursor) {
        DebtLoan debtLoan = new DebtLoan();
        
        String id = cursor.getString(0);
        String note = cursor.getString(1);
        String tran_id = cursor.getString(2);
        
        Transaction transaction = TransactionLocalService.getInstance(mDatabaseHelper)
                  .getTransactionById(tran_id);
        String type = cursor.getString(3);
        int status = cursor.getInt(4);
        String user_id = cursor.getString(5);
        
        debtLoan.setDebt_loan_id(id);
        debtLoan.setNote(note);
        debtLoan.setType(type);
        debtLoan.setStatus(status);
        debtLoan.setUser_id(user_id);
        debtLoan.setTransaction(transaction);
        
        return debtLoan;
    }
    
    
    @Override
    public Callable<List<DebtLoan>> getDebtLoanByWalletId(final String wallet_id) {
        return new Callable<List<DebtLoan>>() {
            @Override
            public List<DebtLoan> call() throws Exception {
                StringBuilder query = new StringBuilder(
                          "Select tbl_debtloan.debt_loan_id, tbl_debtloan.note, tbl_debtloan.trans_id," +
                          " tbl_debtloan.type, tbl_debtloan.status, tbl_debtloan.user_id " +
                          "from tbl_debtloan, tbl_transaction " +
                          "where tbl_debtloan.trans_id = tbl_transaction.trans_id and tbl_transaction.wallet_id = ?");
                Cursor cursor = mDatabase.rawQuery(query.toString(), new String[]{wallet_id});
                
                if (cursor == null) {
                    return null;
                }
                
                return makeListObjectFromCursor(cursor);
            }
            
        };
    }
    
    @Override
    public Callable<List<DebtLoan>> getDebtLoanByType(final String wallet_id, final String type) {
        return new Callable<List<DebtLoan>>() {
            @Override
            public List<DebtLoan> call() throws Exception {
                StringBuilder query = new StringBuilder(
                          "Select tbl_debtloan.debt_loan_id, tbl_debtloan.note, tbl_debtloan.trans_id, " +
                          "tbl_debtloan.type, tbl_debtloan.status, tbl_debtloan.user_id " +
                          "from tbl_debtloan, tbl_transaction " +
                          "where tbl_debtloan.type = ? and tbl_debtloan.trans_id = tbl_transaction.trans_id and tbl_transaction.wallet_id = ?");
                Cursor cursor = mDatabase.rawQuery(query.toString(), new String[]{type, wallet_id});
                
                if (cursor == null) {
                    return null;
                }
                
                return makeListObjectFromCursor(cursor);
            }
        };
    }
    
    @Override
    public Callable<Long> addDebtLoan(final DebtLoan debtLoan) {
        return new Callable<Long>() {
            @Override
            public Long call() throws Exception {
                ContentValues contentValues = createContentValues(debtLoan);
                
                return mDatabase.insert(TABLE_NAME, null, contentValues);
            }
        };
    }
    
    @Override
    public Callable<Integer> updateDebtLoan(final DebtLoan debtLoan) {
        return new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                ContentValues contentValues = createContentValues(debtLoan);
                String selection = "debt_loan_id = ?";
                String[] selectionArg = new String[]{debtLoan.getDebt_loan_id()};
                int resultUpdate = mDatabase
                          .update(TABLE_NAME, contentValues, selection, selectionArg);
                
                return doneUpdateDebtLoanAfterUpdateWallet(resultUpdate, debtLoan);
            }
        };
    }
    
    
    @Override
    public Callable<Integer> deleteDebtLoan(final DebtLoan debtLoan) {
        return new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                int resultDeleteTrans = TransactionLocalService.getInstance(mDatabaseHelper)
                          .deleteTransaction(debtLoan.getTransaction()).call();
                
                if (resultDeleteTrans > 0) {
                    String selection = "debt_loan_id = ?";
                    String[] selectionArg = new String[]{debtLoan.getDebt_loan_id()};
                    
                    return mDatabase.delete(TABLE_NAME, selection, selectionArg);
                } else {
                    return 0;
                }
            }
        };
    }
    
    @Override
    public int deleteDebtLoanByTransaction(String trans_id) {
        String selection = "trans_id = ?";
        String[] selectionArg = new String[]{trans_id};
        
        return mDatabase.delete(TABLE_NAME, selection, selectionArg);
    }
    
    private DebtLoan getDebtLoanById(String id) {
        String selection = "debt_loan_id = ?";
        String[] selectionArg = new String[]{id};
        
        Cursor cursor = this.query(TABLE_NAME, getAllColumns(), selection, selectionArg, null);
        
        if (cursor == null) {
            return null;
        }
        
        DebtLoan debtLoan = null;
        
        if (cursor.moveToNext()) {
            debtLoan = makeSingleObjectFromCursor(cursor);
        }
        cursor.close();
        return debtLoan;
    }
    
    public DebtLoan getDebtLoanByTransactionId(String trans_id) {
        String selection = "trans_id = ?";
        String[] selectionArg = new String[]{trans_id};
        Cursor cursor = this.query(TABLE_NAME, getAllColumns(), selection, selectionArg, null);
        
        if (cursor == null) {
            return null;
        }
        
        DebtLoan debtLoan = null;
        
        if (cursor.moveToNext()) {
            debtLoan = makeSingleObjectFromCursor(cursor);
        }
        cursor.close();
        return debtLoan;
    }
    
    private int doneUpdateDebtLoanAfterUpdateWallet(int resultUpdateDebtLoan, DebtLoan debtLoan)
              throws Exception {
        if (resultUpdateDebtLoan > 0) {
            DebtLoan debtLoanAfterUpdate = getDebtLoanById(debtLoan.getDebt_loan_id());
            
            String wallet_id = debtLoanAfterUpdate.getTransaction().getWallet()
                      .getWalletid();
            String amount = debtLoanAfterUpdate.getTransaction().getAmount();
            if (debtLoanAfterUpdate.getStatus() == 1) {
                switch (debtLoanAfterUpdate.getType()) {
                    case "loan":
                        WalletLocalService.getInstance(mDatabaseHelper)
                                  .takeInWallet(wallet_id, amount).call();
                        return 1;
                    case "debt":
                        WalletLocalService.getInstance(mDatabaseHelper)
                                  .takeOutWallet(wallet_id, amount).call();
                        return 1;
                    default:
                        break;
                }
            }
            return 1;
        } else {
            return -1;
        }
    }
}
