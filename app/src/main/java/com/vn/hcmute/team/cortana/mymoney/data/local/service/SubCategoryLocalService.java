package com.vn.hcmute.team.cortana.mymoney.data.local.service;

import android.content.ContentValues;
import android.database.Cursor;
import android.text.TextUtils;
import com.vn.hcmute.team.cortana.mymoney.data.local.base.DatabaseHelper;
import com.vn.hcmute.team.cortana.mymoney.data.local.base.DbContentProvider;
import com.vn.hcmute.team.cortana.mymoney.model.Category;
import com.vn.hcmute.team.cortana.mymoney.utils.logger.MyLogger;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by infamouSs on 9/23/17.
 */

class SubCategoryLocalService extends DbContentProvider<Category> {
    
    
    private final String TABLE_NAME = "tbl_sub_category";
    private final String COLUMN_PARENT_ID = "cate_id";
    private final String COLUMN_ID = "id";
    private final String COLUMN_IMAGE = "icon";
    private final String COLUMN_NAME = "name";
    private final String COLUMN_TYPE = "type";
    private final String COLUMN_TRANSACTION_TYPE = "trans_type";
    private final String COLUMN_USER_ID = "userId";
    
    public SubCategoryLocalService(
              DatabaseHelper mDatabaseHelper) {
        super(mDatabaseHelper);
    }
    
    @Override
    protected String[] getAllColumns() {
        return new String[]{
                  COLUMN_PARENT_ID,
                  COLUMN_ID, COLUMN_IMAGE, COLUMN_NAME, COLUMN_TYPE,
                  COLUMN_TRANSACTION_TYPE, COLUMN_USER_ID
        };
    }
    
    List<Category> getListSubCategory(String parentId) {
        
        String selection = "cate_id = ?";
        String[] selectionArg = new String[]{parentId};
        
        Cursor cursor = this
                  .query(TABLE_NAME, getAllColumns(), selection, selectionArg, null);
        if (cursor == null) {
            return null;
        }
        List<Category> lists = new ArrayList<>();
        
        while (cursor.moveToNext()) {
            
            String id = cursor.getString(1);
            String icon = cursor.getString(2);
            String name = cursor.getString(3);
            String type = cursor.getString(4);
            String trans_type = cursor.getString(5);
            String userId = cursor.getString(6);
            
            Category category = new Category();
            
            category.setId(id);
            category.setIcon(icon);
            category.setName(name);
            category.setType(type);
            category.setTransType(trans_type);
            category.setUserid(userId);
            category.setParent(new Category(id));
            
            lists.add(category);
        }
        cursor.close();
        return lists;
    }
    
    long addSubCategory(Category category) {
        ContentValues contentValues = createContentValues(category);
        
        return mDatabase.insert(TABLE_NAME, null, contentValues);
    }
    
    int updateSubCategory(Category category) {
        ContentValues contentValues = createContentValues(category);
        
        String selection = "id = ?";
        String[] selectionArg = new String[]{category.getId()};
        
        return mDatabase.update(TABLE_NAME, contentValues, selection, selectionArg);
    }
    
    int deleteSubCategory(String[] ids) {
        
        String selection = "id IN (?)";
        String selectionArg = TextUtils.join(", ", ids);
        
        return mDatabase.delete(TABLE_NAME, selection, new String[]{selectionArg});
    }
    
    int deleteSubCategory(Category category) {
        
        String selection = "id = ?";
        String[] selectionArg = new String[]{category.getId()};
        
        return mDatabase.delete(TABLE_NAME, selection, selectionArg);
    }
    
    @Override
    protected ContentValues createContentValues(Category category) {
        ContentValues contentValues = new ContentValues();
        
        contentValues.put("cate_id", category.getParent().getId());
        contentValues.put("id", category.getId());
        contentValues.put("name", category.getName());
        contentValues.put("icon", category.getIcon());
        contentValues.put("type", category.getType());
        contentValues.put("trans_type", category.getTransType());
        contentValues.put("userid", category.getUserid());
        
        return contentValues;
    }
}
