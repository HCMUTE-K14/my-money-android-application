package com.vn.hcmute.team.cortana.mymoney.data.local.service;

import android.content.ContentValues;
import android.database.Cursor;
import android.text.TextUtils;
import com.vn.hcmute.team.cortana.mymoney.data.local.base.DatabaseHelper;
import com.vn.hcmute.team.cortana.mymoney.data.local.base.DbContentProvider;
import com.vn.hcmute.team.cortana.mymoney.model.Category;
import com.vn.hcmute.team.cortana.mymoney.utils.TextUtil;
import com.vn.hcmute.team.cortana.mymoney.utils.logger.MyLogger;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

/**
 * Created by infamouSs on 9/15/17.
 */

public class CategoryLocalService extends DbContentProvider<Category> {
    
    public static final String TAG = CategoryLocalService.class.getSimpleName();
    
    private final String TABLE_NAME = "tbl_category";
    private final String COLUMN_ID = "cate_id";
    private final String COLUMN_IMAGE = "icon";
    private final String COLUMN_NAME = "name";
    private final String COLUMN_TYPE = "type";
    private final String COLUMN_TRANSACTION_TYPE = "trans_type";
    private final String COLUMN_USER_ID = "user_id";
    private final String COULMN_PARENT_ID = "parent_id";
    
    
    public CategoryLocalService(DatabaseHelper databaseHelper) {
        super(databaseHelper);
    }
    
    @Override
    protected String[] getAllColumns() {
        return new String[]{COLUMN_ID, COLUMN_IMAGE, COLUMN_NAME, COLUMN_TYPE,
                  COLUMN_TRANSACTION_TYPE, COLUMN_USER_ID, COULMN_PARENT_ID};
    }
    
    
    public Callable<List<Category>> getListCategory(final String selection,
              final String[] selectionArg) {
        
        return new Callable<List<Category>>() {
            @Override
            public List<Category> call() throws Exception {
                Cursor cursor = CategoryLocalService.this
                          .query(TABLE_NAME, getAllColumns(), selection, selectionArg, null);
                if (cursor == null) {
                    return null;
                }
                List<Category> lists = new ArrayList<>();
                
                while (cursor.moveToNext()) {
                    String parentId = cursor.getString(6);
                    if (!TextUtil.isEmpty(parentId)) {
                        continue;
                    }
                    String id = cursor.getString(0);
                    String icon = cursor.getString(1);
                    String name = cursor.getString(2);
                    String type = cursor.getString(3);
                    String trans_type = cursor.getString(4);
                    String userId = cursor.getString(5);
                    
                    List<Category> subCategories = new ArrayList<>();
                    
                    subCategories.clear();
                    List<Category> listSub = getListSubCategory(id);
                    
                    if (listSub != null) {
                        subCategories.addAll(listSub);
                    }
                    
                    Category category = new Category();
                    
                    category.setId(id);
                    category.setIcon(icon);
                    category.setName(name);
                    category.setType(type);
                    category.setTransType(trans_type);
                    category.setSubcategories(subCategories);
                    category.setUserid(userId);
                    
                    lists.add(category);
                }
                cursor.close();
                return lists;
            }
        };
    }
    
    public Callable<Long> addCategory(final Category category) {
        return new Callable<Long>() {
            @Override
            public Long call() throws Exception {
                MyLogger.d(TAG, category.getId() == null ? "Null"
                          : category.getId());
                if (category.getParent() != null) {
                    MyLogger.d(TAG, "add child");
                    return addSubCategory(category);
                }
                MyLogger.d(TAG, "add parent");
                ContentValues contentValues = createContentValues(category);
                
                return mDatabase.insert(TABLE_NAME, null, contentValues);
            }
        };
    }
    
    public Callable<Integer> updateCategory(final Category category) {
        return new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                if (category.getParent() != null) {
                    return updateSubCategory(category);
                }
                
                ContentValues contentValues = createContentValues(category);
                
                String selection = "cate_id = ?";
                String[] selectionArg = new String[]{category.getId()};
                
                return mDatabase.update(TABLE_NAME, contentValues, selection, selectionArg);
            }
        };
    }
    
    public Callable<Integer> deleteCategory(final Category category) {
        return new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                if (category.getParent() != null) {
                    return deleteSubCategory(category);
                }
                
                List<String> ids = new ArrayList<>();
                for (Category cate : category.getSubcategories()) {
                    MyLogger.d(TAG, cate.getName());
                    ids.add(cate.getId());
                }
                String[] selectionIds = ids.toArray(new String[ids.size()]);
                
                int resultDeleteSubCategory = deleteSubCategory(selectionIds);
                
                int result = -1;
                if (resultDeleteSubCategory >= 0) {
                    String selection = "cate_id = ?";
                    String[] selectionArg = new String[]{category.getId()};
                    
                    result = mDatabase.delete(TABLE_NAME, selection, selectionArg);
                }
                
                return result;
            }
        };
    }
    
    private List<Category> getListSubCategory(String parentId) {
        String selection = "parent_id = ?";
        String[] selectionArg = new String[]{parentId};
        Cursor cursor = CategoryLocalService.this
                  .query(TABLE_NAME, getAllColumns(), selection, selectionArg, null);
        if (cursor == null) {
            return null;
        }
        List<Category> lists = new ArrayList<>();
        
        while (cursor.moveToNext()) {
            
            String id = cursor.getString(0);
            String icon = cursor.getString(1);
            String name = cursor.getString(2);
            String type = cursor.getString(3);
            String trans_type = cursor.getString(4);
            String userId = cursor.getString(5);
            String _parentId = cursor.getString(6);
            
            Category category = new Category();
            
            category.setId(id);
            category.setIcon(icon);
            category.setName(name);
            category.setType(type);
            category.setTransType(trans_type);
            category.setUserid(userId);
            category.setParent(new Category(_parentId));
            
            lists.add(category);
        }
        cursor.close();
        return lists;
    }
    
    private long addSubCategory(Category category) {
        ContentValues contentValues = createContentValues(category);
        
        return mDatabase.insert(TABLE_NAME, null, contentValues);
    }
    
    private int updateSubCategory(Category category) {
        ContentValues contentValues = createContentValues(category);
        String cateId = category.getId();
        String parentId = category.getParent().getId();
        
        String selection = "cate_id = ? and parent_id = ?";
        String[] selectionArg = new String[]{cateId, parentId};
        
        return mDatabase.update(TABLE_NAME, contentValues, selection, selectionArg);
    }
    
    private int deleteSubCategory(String[] ids) {
        String selection = "cate_id IN (?)";
        String selectionArg = TextUtils.join(", ", ids);
        
        return mDatabase.delete(TABLE_NAME, selection, new String[]{selectionArg});
    }
    
    private int deleteSubCategory(Category category) {
        
        String selection = "cate_id = ? and parent_id = ?";
        String[] selectionArg = new String[]{category.getId(), category.getParent().getId()};
        
        return mDatabase.delete(TABLE_NAME, selection, selectionArg);
    }
    
    
    @Override
    protected ContentValues createContentValues(Category category) {
        ContentValues contentValues = new ContentValues();
        
        contentValues.put("cate_id", category.getId());
        contentValues.put("name", category.getName());
        contentValues.put("icon", category.getIcon());
        contentValues.put("type", category.getType());
        contentValues.put("trans_type", category.getTransType());
        contentValues.put("user_id", category.getUserid());
        contentValues.put("parent_id", category.getParent().getId());
        
        return contentValues;
    }
}
