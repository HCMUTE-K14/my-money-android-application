package com.vn.hcmute.team.cortana.mymoney.data.local.service;

import android.content.ContentValues;
import android.database.Cursor;
import com.vn.hcmute.team.cortana.mymoney.data.local.base.DatabaseHelper;
import com.vn.hcmute.team.cortana.mymoney.data.local.base.DbContentProvider;
import com.vn.hcmute.team.cortana.mymoney.data.remote.serivce.PersonService;
import com.vn.hcmute.team.cortana.mymoney.model.Person;
import com.vn.hcmute.team.cortana.mymoney.utils.logger.MyLogger;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

/**
 * Created by kunsubin on 10/16/2017.
 */

public class PersonLocalService extends DbContentProvider<Person> implements LocalService.PersonLocalService{
    public static final String TAG = PersonService.class.getSimpleName();
    private final String TABLE_NAME = "tbl_person";
    private final String ID="person_id";
    private final String NAME="name";
    private final String DESCRIBE="describe";
    private final String USER_ID="user_id";
    
    public PersonLocalService(DatabaseHelper mDatabaseHelper) {
        super(mDatabaseHelper);
    }
    
    @Override
    protected String[] getAllColumns() {
        return new String[]{ID,NAME,DESCRIBE,USER_ID};
    }
    
    @Override
    protected ContentValues createContentValues(Person values) {
        ContentValues contentValues=new ContentValues();
        contentValues.put("person_id",values.getPersonid());
        contentValues.put("name",values.getName());
        contentValues.put("describe",values.getDescribe());
        contentValues.put("user_id",values.getUserid());
        return contentValues;
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
                List<Person> persons = new ArrayList<>();
                while (cursor.moveToNext()) {
                    Person person = new Person();
                 
                    person.setPersonid(cursor.getString(0));
                    person.setName(cursor.getString(1));
                    person.setDescribe(cursor.getString(2));
                    person.setUserid(cursor.getString(3));
                
                    persons.add(person);
                }
                cursor.close();
                return persons;
            }
        };
    }
    
    @Override
    public Callable<Long> addPerson(final Person person) {
        MyLogger.d("lanngthang",person.getName());
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
}
