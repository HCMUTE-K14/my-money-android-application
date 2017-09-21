package com.vn.hcmute.team.cortana.mymoney.data.local.service;

import com.vn.hcmute.team.cortana.mymoney.data.local.base.DatabaseHelper;
import com.vn.hcmute.team.cortana.mymoney.data.local.base.DbContentProvider;
import com.vn.hcmute.team.cortana.mymoney.model.Category;
import java.util.List;
import java.util.concurrent.Callable;

/**
 * Created by infamouSs on 9/15/17.
 */

public class CategoryLocalService extends DbContentProvider {
    
    private final String TABLE_NAME = "tbl_category";
    private final String COLUMN_ID = "categoryId";
    private final String COLUMN_IMAGE = "categoryImage";
    private final String COLUMN_NAME = "categoryName";
    private final String COLUMN_PARENT = "categoryParent";
    private final String COLUMN_TYPE = "categoryType";
    private final String COLUMN_TRANSACTION_TYPE = "transactionType";
    private final String COLUMN_USER_ID = "userId";
    
    public CategoryLocalService(DatabaseHelper databaseHelper) {
        super(databaseHelper);
    }
    
    @Override
    protected String[] getAllColumns() {
        return new String[]{COLUMN_ID, COLUMN_IMAGE, COLUMN_NAME, COLUMN_PARENT, COLUMN_TYPE,
                  COLUMN_TRANSACTION_TYPE, COLUMN_USER_ID};
    }
    
    
    public Callable<List<Category>> getListCategory() {
        return null;
    }
    
}
