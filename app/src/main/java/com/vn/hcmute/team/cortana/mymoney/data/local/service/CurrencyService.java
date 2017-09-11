package com.vn.hcmute.team.cortana.mymoney.data.local.service;

import android.database.Cursor;
import com.vn.hcmute.team.cortana.mymoney.data.local.base.DatabaseHelper;
import com.vn.hcmute.team.cortana.mymoney.data.local.base.DbContentProvider;
import com.vn.hcmute.team.cortana.mymoney.model.Currencies;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

/**
 * Created by infamouSs on 9/11/17.
 */

public class CurrencyService extends DbContentProvider implements
                                                       LocalService.CurrencyLocalRepository {
    
    public static final String TAG = CurrencyService.class.getSimpleName();
    
    private final String TABLE_NAME = "tbl_currency";
    private final String COLUMN_ID = "cur_id";
    private final String COLUMN_NAME = "cur_name";
    private final String COLUMN_SYMBOL = "cur_symbol";
    private final String COLUMN_DISPLAY_TYPE = "cur_display_type";
    private final String COLUMN_CODE = "cur_code";
    
    public CurrencyService(DatabaseHelper databaseHelper) {
        super(databaseHelper);
    }
    
    @Override
    public String[] getAllColumns() {
        return new String[]{COLUMN_ID, COLUMN_NAME, COLUMN_SYMBOL, COLUMN_DISPLAY_TYPE,
                  COLUMN_CODE};
    }
    
    @Override
    public Callable<List<Currencies>> getListCurrency() {
        try {
            this.open();
            return new Callable<List<Currencies>>() {
                @Override
                public List<Currencies> call() throws Exception {
                    Cursor cursor = CurrencyService.this
                              .query(TABLE_NAME, getAllColumns(), null, null, null);
                    if (cursor == null) {
                        return null;
                    }
                    List<Currencies> lists = new ArrayList<>();
                    
                    while (cursor.moveToNext()) {
                        
                        String id = cursor.getString(0);
                        String name = cursor.getString(1);
                        String symbol = cursor.getString(2);
                        String display_type = cursor.getString(3);
                        String code = cursor.getString(4);
                        
                        Currencies currencies = new Currencies(id, name, symbol, display_type,
                                  code);
                        
                        lists.add(currencies);
                    }
                    return lists;
                }
            };
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
