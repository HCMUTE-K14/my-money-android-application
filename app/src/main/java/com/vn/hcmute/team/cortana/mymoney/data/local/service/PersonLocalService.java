package com.vn.hcmute.team.cortana.mymoney.data.local.service;

import android.content.ContentValues;
import android.database.Cursor;
import com.vn.hcmute.team.cortana.mymoney.data.local.base.DatabaseHelper;
import com.vn.hcmute.team.cortana.mymoney.data.local.base.DbContentProvider;
import com.vn.hcmute.team.cortana.mymoney.data.remote.serivce.PersonService;
import com.vn.hcmute.team.cortana.mymoney.model.Person;
import com.vn.hcmute.team.cortana.mymoney.utils.Constraints;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

/**
 * Created by kunsubin on 10/16/2017.
 */

public class PersonLocalService extends DbContentProvider<Person> implements
                                                                  LocalService.PersonLocalService {
    
    public static final String TAG = PersonService.class.getSimpleName();
    private static PersonLocalService sInstance;
    private final String TABLE_NAME = "tbl_person";
    private final String ID = "person_id";
    private final String NAME = "name";
    private final String DESCRIBE = "describe";
    private final String USER_ID = "user_id";
    
    private PersonLocalService(DatabaseHelper mDatabaseHelper) {
        super(mDatabaseHelper);
    }
    
    public static PersonLocalService getInstance(DatabaseHelper databaseHelper) {
        if (sInstance == null) {
            synchronized (BudgetLocalService.class) {
                if (sInstance == null) {
                    sInstance = new PersonLocalService(databaseHelper);
                }
            }
        }
        return sInstance;
    }
    
    @Override
    protected String[] getAllColumns() {
        return new String[]{ID, NAME, DESCRIBE, USER_ID};
    }
    
    @Override
    protected ContentValues createContentValues(Person values) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("person_id", values.getPersonid());
        contentValues.put("name", values.getName());
        contentValues.put("describe", values.getDescribe());
        contentValues.put("user_id", values.getUserid());
        return contentValues;
    }
    
    @Override
    protected List<Person> makeListObjectFromCursor(Cursor cursor) {
        List<Person> persons = new ArrayList<>();
        while (cursor.moveToNext()) {
            Person person = makeSingleObjectFromCursor(cursor);
            persons.add(person);
        }
        cursor.close();
        return persons;
    }
    
    @Override
    protected Person makeSingleObjectFromCursor(Cursor cursor) {
        Person person = new Person();
        String id = cursor.getString(0);
        if (id.equals("some_one_id")) {
            person = Constraints.SOME_ONE_PERSON;
            
            return person;
        }
        person.setPersonid(id);
        person.setName(cursor.getString(1));
        person.setDescribe(cursor.getString(2));
        person.setUserid(cursor.getString(3));
        
        return person;
    }
    
    @Override
    public Callable<List<Person>> getListPerson(final String userId) {
        return new Callable<List<Person>>() {
            @Override
            public List<Person> call() throws Exception {
                String selection = "user_id=?";
                String[] selectionArg = new String[]{userId};
                Cursor cursor = PersonLocalService.this
                          .query(TABLE_NAME, getAllColumns(), selection, selectionArg, null);
                if (cursor == null) {
                    return null;
                }
                return makeListObjectFromCursor(cursor);
            }
        };
    }
    
    @Override
    public Callable<Long> addPerson(final Person person) {
        return new Callable<Long>() {
            @Override
            public Long call() throws Exception {
                ContentValues contentValues = createContentValues(person);
                return mDatabase.insert(TABLE_NAME, null, contentValues);
            }
        };
    }
    
    @Override
    public Callable<Integer> updatePerson(final Person person) {
        return new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                ContentValues contentValues = createContentValues(person);
                String selection = "person_id=?";
                String[] selectionArg = new String[]{person.getPersonid()};
                return mDatabase.update(TABLE_NAME, contentValues, selection, selectionArg);
            }
        };
    }
    
    @Override
    public Callable<Integer> deletePerson(final String idPerson) {
        return new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                String whereClause = "person_id=?";
                return mDatabase.delete(TABLE_NAME, whereClause, new String[]{idPerson});
            }
        };
    }
    
    @Override
    public Person getPersonById(String person_id) {
        String selection = "person_id = ?";
        String[] selectionArg = new String[]{person_id};
        Cursor cursor = this.query(TABLE_NAME, getAllColumns(), selection, selectionArg, null);
        if (cursor == null) {
            return null;
        }
        Person person = null;
        if (cursor.moveToNext()) {
            person = makeSingleObjectFromCursor(cursor);
        }
        cursor.close();
        
        return person;
    }
}
