package com.vn.hcmute.team.cortana.mymoney.data.local.service;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.vn.hcmute.team.cortana.mymoney.data.local.base.DatabaseHelper;
import com.vn.hcmute.team.cortana.mymoney.data.local.base.DbContentProvider;
import com.vn.hcmute.team.cortana.mymoney.model.Person;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by infamouSs on 11/17/17.
 */

public class TransPersonService extends DbContentProvider<String[]> implements
                                                                    LocalService.TransPersonLocalService {
    
    public static final String TAG = TransPersonService.class.getSimpleName();
    
    private static TransPersonService sInstance;
    
    private final String TABLE_NAME = "tbl_trans_person";
    private final String TRANS_ID = "trans_id";
    private final String PERSON_ID = "person_id";
    
    
    private TransPersonService(DatabaseHelper databaseHelper) {
        super(databaseHelper);
    }
    
    public static TransPersonService getInstance(DatabaseHelper databaseHelper) {
        if (sInstance == null) {
            synchronized (TransPersonService.class) {
                if (sInstance == null) {
                    sInstance = new TransPersonService(databaseHelper);
                }
            }
        }
        return sInstance;
    }
    
    
    @Override
    protected String[] getAllColumns() {
        return new String[]{TRANS_ID, PERSON_ID};
    }
    
    @Override
    protected ContentValues createContentValues(String[] values) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(TRANS_ID, values[0]);
        contentValues.put(PERSON_ID, values[1]);
        
        return contentValues;
    }
    
    @Override
    protected List<String[]> makeListObjectFromCursor(Cursor cursor) {
        return null;
    }
    
    @Override
    protected String[] makeSingleObjectFromCursor(Cursor cursor) {
        return new String[0];
    }
    
    @Override
    public List<Person> getPersonByTransactionId(String trans_id) {
        String selection = "trans_id = ?";
        String[] selectionArg = new String[]{trans_id};
        
        Cursor cursor = this.query(TABLE_NAME, getAllColumns(), selection, selectionArg, null);
        if (cursor == null) {
            return null;
        }
        List<Person> persons = new ArrayList<>();
        
        while (cursor.moveToNext()) {
            String personId = cursor.getString(1);
            Person person = PersonLocalService.getInstance(mDatabaseHelper).getPersonById(personId);
            
            persons.add(person);
        }
        cursor.close();
        return persons;
    }
    
    @Override
    public long add(String[] values) {
        ContentValues contentValues = createContentValues(values);
        return this.mDatabase.insert(TABLE_NAME, null, contentValues);
    }
    
    @Override
    public int update(String[] values) {
        ContentValues contentValues = createContentValues(values);
        String selection = "trans_id = ?";
        String[] selectionArg = new String[]{values[0]};
        
        long result = this.mDatabase.insertWithOnConflict(TABLE_NAME, null, contentValues,
                  SQLiteDatabase.CONFLICT_REPLACE);
        
        return result >= 0 ? 1 : -1;
    }
    
    @Override
    public int delete(String[] values) {
        String selection = "trans_id = ? and person_id = ?";
        String[] selectionArg = new String[]{values[0], values[1]};
        
        return mDatabase.delete(TABLE_NAME, selection, selectionArg);
    }
    
    public int delete(String trans_id) {
        String selection = "trans_id = ?";
        String[] selectionArg = new String[]{trans_id};
        
        return mDatabase.delete(TABLE_NAME, selection, selectionArg);
    }
}
