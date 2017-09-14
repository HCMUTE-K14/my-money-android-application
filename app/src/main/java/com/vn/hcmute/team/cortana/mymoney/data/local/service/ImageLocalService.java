package com.vn.hcmute.team.cortana.mymoney.data.local.service;

import android.database.Cursor;
import com.vn.hcmute.team.cortana.mymoney.data.local.base.DatabaseHelper;
import com.vn.hcmute.team.cortana.mymoney.data.local.base.DbContentProvider;
import com.vn.hcmute.team.cortana.mymoney.model.Icon;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

/**
 * Created by infamouSs on 9/13/17.
 */

public class ImageLocalService extends DbContentProvider implements LocalService.ImageLocalService {
    
    private final String TABLE_NAME = "tbl_icon";
    private final String COLUMN_ID = "id";
    private final String COLUMN_IMAGE = "image";
    
    public ImageLocalService(DatabaseHelper databaseHelper) {
        super(databaseHelper);
    }
    
    @Override
    protected String[] getAllColumns() {
        return new String[]{COLUMN_ID, COLUMN_IMAGE};
    }
    
    @Override
    public Callable<List<Icon>> getListIcon() {
        try {
            this.open();
            return new Callable<List<Icon>>() {
                @Override
                public List<Icon> call() throws Exception {
                    Cursor cursor = ImageLocalService.this
                              .query(TABLE_NAME, getAllColumns(), null, null, null);
                    if (cursor == null) {
                        return null;
                    }
                    List<Icon> lists = new ArrayList<>();
                    
                    while (cursor.moveToNext()) {
                        
                        String id = cursor.getString(0);
                        String image = cursor.getString(1);
                        
                        Icon icon = new Icon(id, image);
                        lists.add(icon);
                    }
                    cursor.close();
                    ImageLocalService.this.close();
                    return lists;
                }
            };
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
