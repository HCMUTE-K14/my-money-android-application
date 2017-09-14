package com.vn.hcmute.team.cortana.mymoney.data.local;

import android.content.Context;
import com.vn.hcmute.team.cortana.mymoney.data.local.base.DatabaseHelper;
import com.vn.hcmute.team.cortana.mymoney.data.local.service.CurrencyService;
import com.vn.hcmute.team.cortana.mymoney.data.local.service.ImageLocalService;
import com.vn.hcmute.team.cortana.mymoney.model.Currencies;
import com.vn.hcmute.team.cortana.mymoney.model.Icon;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.annotations.NonNull;
import java.util.List;
import java.util.concurrent.Callable;
import javax.inject.Inject;

/**
 * Created by infamouSs on 8/10/17.
 */

public class LocalRepository implements LocalTask.IconTask, LocalTask.CurrencyTask {
    
    public static final String TAG = LocalRepository.class.getSimpleName();
    
    private DatabaseHelper mDatabaseHelper;
    
    @Inject
    public LocalRepository(Context context) {
        this.mDatabaseHelper = DatabaseHelper.getInstance(context.getApplicationContext());
    }
    
    @Override
    public Observable<List<Icon>> getListIcon() {
        ImageLocalService imageLocalService = new ImageLocalService(mDatabaseHelper);
        
        Callable<List<Icon>> callable = imageLocalService.getListIcon();
        
        return makeObservable(callable);
    }
    
    @Override
    public Observable<List<Currencies>> getListCurrency() {
        CurrencyService currencyService = new CurrencyService(mDatabaseHelper);
        
        Callable<List<Currencies>> callable = currencyService.getListCurrency();
        
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
}
