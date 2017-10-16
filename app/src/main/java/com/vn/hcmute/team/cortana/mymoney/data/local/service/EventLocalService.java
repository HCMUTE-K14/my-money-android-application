package com.vn.hcmute.team.cortana.mymoney.data.local.service;

import android.content.ContentValues;
import android.database.Cursor;
import com.vn.hcmute.team.cortana.mymoney.data.local.base.DatabaseHelper;
import com.vn.hcmute.team.cortana.mymoney.data.local.base.DbContentProvider;
import com.vn.hcmute.team.cortana.mymoney.model.Currencies;
import com.vn.hcmute.team.cortana.mymoney.model.Event;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;


/**
 * Created by kunsubin on 10/16/2017.
 */

public class EventLocalService extends DbContentProvider<Event> implements LocalService.EventLocalService{
    public static final String TAG = EventLocalService.class.getSimpleName();
    private final String TABLE_NAME = "tbl_event";
    private final String ID="event_id";
    private final String NAME="name";
    private final String MONEY="money";
    private final String DATE="date";
    private final String WALLET_ID = "wallet_id";
    private final String CUR_ID = "cur_id";
    private final String STATUS = "status";
    private final String USER_ID = "user_id";
    private final String ICON = "icon";
    
    private CurrencyLocalService mCurrencyLocalService;
    
    
    public EventLocalService(DatabaseHelper mDatabaseHelper) {
        super(mDatabaseHelper);
        mCurrencyLocalService = new CurrencyLocalService(mDatabaseHelper);
        
    }
    
    @Override
    protected String[] getAllColumns() {
        return new String[]{ID, NAME,MONEY, DATE, WALLET_ID,
                  CUR_ID, STATUS, USER_ID, ICON};
    }
    
    @Override
    protected ContentValues createContentValues(Event values) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("event_id", values.getEventid());
        contentValues.put("name", values.getName());
        contentValues.put("money",values.getMoney());
        contentValues.put("date", values.getDate());
        contentValues.put("wallet_id", values.getIdWallet());
        contentValues.put("cur_id", values.getCurrencies().getCurId());
        contentValues.put("status", values.getStatus());
        contentValues.put("user_id", values.getUserid());
        contentValues.put("icon", values.getIcon());
        return contentValues;
    }
    
    @Override
    public Callable<List<Event>> getListEvent(final String userId) {
        return new Callable<List<Event>>() {
            @Override
            public List<Event> call() throws Exception {
                String selection = "user_id=?";
                String[] selectionArg = new String[]{userId};
                Cursor cursor = EventLocalService.this
                          .query(TABLE_NAME, getAllColumns(), selection, selectionArg, null);
                if (cursor == null) {
                    return null;
                }
                List<Event> events = new ArrayList<>();
                while (cursor.moveToNext()) {
                    Event event = new Event();
                    event.setEventid(cursor.getString(0));
                    event.setName(cursor.getString(1));
                    event.setMoney(cursor.getString(2));
                    event.setDate(cursor.getString(3));
                    
                    if(cursor.getString(4)==null){
                        event.setIdWallet("");
                    }else {
                        event.setIdWallet(cursor.getString(4));
                    }
                    //currencies
                    Currencies currencies = getCurrencies(cursor.getString(5));
                    event.setCurrencies(currencies);
                    
                    event.setStatus(cursor.getString(6));
                    if(cursor.getString(7)!=null){
                        event.setUserid(cursor.getString(7));
                    }
                    event.setIcon(cursor.getString(8));
                    
                    events.add(event);
                }
                cursor.close();
                return events;
            }
        };
    }
    
    @Override
    public Callable<Long> addEvent(final Event event) {
        return new Callable<Long>() {
            @Override
            public Long call() throws Exception {
                ContentValues contentValues = createContentValues(event);
                return mDatabase.insert(TABLE_NAME, null, contentValues);
            }
        };
    }
    
    @Override
    public Callable<Integer> updateEvent(final Event event) {
        return new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                ContentValues contentValues = createContentValues(event);
                String selection = "event_id=?";
                String[] selectionArg = new String[]{event.getEventid()};
                return mDatabase.update(TABLE_NAME, contentValues, selection, selectionArg);
            }
        };
    }
    
    @Override
    public Callable<Integer> deleteEvent(final String idEvent) {
        return new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                String whereClause = "event_id=?";
                return mDatabase.delete(TABLE_NAME, whereClause, new String[]{idEvent});
            }
        };
    }
    public Currencies getCurrencies(String idCurrencies) {
        return mCurrencyLocalService.getCurrency(idCurrencies);
    }
}
