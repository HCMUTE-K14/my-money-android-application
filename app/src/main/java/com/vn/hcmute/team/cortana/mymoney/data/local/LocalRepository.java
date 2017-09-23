package com.vn.hcmute.team.cortana.mymoney.data.local;

import android.content.Context;
import com.vn.hcmute.team.cortana.mymoney.R;
import com.vn.hcmute.team.cortana.mymoney.data.local.base.DatabaseHelper;
import com.vn.hcmute.team.cortana.mymoney.data.local.service.CategoryLocalService;
import com.vn.hcmute.team.cortana.mymoney.data.local.service.CurrencyLocalService;
import com.vn.hcmute.team.cortana.mymoney.data.local.service.ImageLocalService;
import com.vn.hcmute.team.cortana.mymoney.model.Category;
import com.vn.hcmute.team.cortana.mymoney.model.Currencies;
import com.vn.hcmute.team.cortana.mymoney.model.Icon;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;
import java.util.List;
import java.util.concurrent.Callable;
import javax.inject.Inject;

/**
 * Created by infamouSs on 8/10/17.
 */

public class LocalRepository implements LocalTask.IconTask, LocalTask.CurrencyTask,
                                        LocalTask.CategoryTask {
    
    public static final String TAG = LocalRepository.class.getSimpleName();
    
    private DatabaseHelper mDatabaseHelper;
    private Context mContext;
    
    @Inject
    public LocalRepository(Context context) {
        mContext = context.getApplicationContext();
        this.mDatabaseHelper = DatabaseHelper.getInstance(mContext.getApplicationContext());
    }
    
    @Override
    public Observable<List<Icon>> getListIcon() {
        ImageLocalService imageLocalService = new ImageLocalService(mDatabaseHelper);
        
        Callable<List<Icon>> callable = imageLocalService.getListIcon();
        
        return makeObservable(callable);
    }
    
    @Override
    public Observable<List<Currencies>> getListCurrency() {
        CurrencyLocalService currencyLocalService = new CurrencyLocalService(mDatabaseHelper);
        
        Callable<List<Currencies>> callable = currencyLocalService.getListCurrency();
        
        return makeObservable(callable);
    }
    
    private <T> Observable<T> makeObservable(final Callable<T> callable) {
        return Observable.create(new ObservableOnSubscribe<T>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<T> e) throws Exception {
                try {
                    e.onNext(callable.call());
                    e.onComplete();
                } catch (Exception ex) {
                    e.onError(ex);
                }
            }
        });
    }
    
    @Override
    public Observable<List<Category>> getListCategory(String transType) {
        CategoryLocalService categoryLocalService = new CategoryLocalService(mDatabaseHelper);
        
        String selection = "trans_type = ?";
        String[] selectionArg = new String[]{transType};
        
        Callable<List<Category>> callable = categoryLocalService
                  .getListCategory(selection, selectionArg);
        
        return makeObservable(callable);
    }
    
    @Override
    public Observable<List<Category>> getListCategoryByType(String type, String transType) {
        CategoryLocalService categoryLocalService = new CategoryLocalService(mDatabaseHelper);
        
        String selection = "type = ? and trans_type = ?";
        String[] selectionArg = new String[]{type, transType};
        
        Callable<List<Category>> callable = categoryLocalService
                  .getListCategory(selection, selectionArg);
        
        return makeObservable(callable);
    }
    
    @Override
    public Observable<String> addCategory(Category category) {
        CategoryLocalService categoryLocalService = new CategoryLocalService(mDatabaseHelper);
        
        Callable<Long> callable = categoryLocalService
                  .addCategory(category);
        
        return makeObservable(callable).map(new Function<Long, String>() {
            @Override
            public String apply(@NonNull Long aLong) throws Exception {
                if (aLong > 0) {
                    return mContext.getString(R.string.message_add_category_success);
                } else {
                    return mContext.getString(R.string.message_category_fail);
                }
            }
        });
    }
    
    @Override
    public Observable<String> updateCategory(Category category) {
        CategoryLocalService categoryLocalService = new CategoryLocalService(mDatabaseHelper);
        
        Callable<Integer> callable = categoryLocalService
                  .updateCategory(category);
        
        return makeObservable(callable).map(new Function<Integer, String>() {
            @Override
            public String apply(@NonNull Integer aLong) throws Exception {
                if (aLong > 0) {
                    return mContext.getString(R.string.message_update_category_success);
                } else {
                    return mContext.getString(R.string.message_category_fail);
                }
            }
        });
    }
    
    @Override
    public Observable<String> deleteCategory(Category category) {
        CategoryLocalService categoryLocalService = new CategoryLocalService(mDatabaseHelper);
        
        Callable<Integer> callable = categoryLocalService
                  .deleteCategory(category);
        
        return makeObservable(callable).map(new Function<Integer, String>() {
            @Override
            public String apply(@NonNull Integer aLong) throws Exception {
                if (aLong > 0) {
                    return mContext.getString(R.string.message_delete_category_success);
                } else {
                    return mContext.getString(R.string.message_category_fail);
                }
            }
        });
    }
}
