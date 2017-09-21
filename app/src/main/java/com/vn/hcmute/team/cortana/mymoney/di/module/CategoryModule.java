package com.vn.hcmute.team.cortana.mymoney.di.module;

import android.content.Context;
import com.vn.hcmute.team.cortana.mymoney.data.DataRepository;
import com.vn.hcmute.team.cortana.mymoney.ui.category.CategoryPresenter;
import com.vn.hcmute.team.cortana.mymoney.usecase.remote.CategoryUseCase;
import dagger.Module;
import dagger.Provides;

/**
 * Created by infamouSs on 9/16/17.
 */
@Module
public class CategoryModule {
    
    public CategoryModule() {
        
    }
    
    @Provides
    CategoryUseCase provideCategoryUseCase(Context context, DataRepository dataRepository) {
        return new CategoryUseCase(context.getApplicationContext(), dataRepository);
    }
    
    @Provides
    CategoryPresenter provideCategoryPresenter(CategoryUseCase categoryUseCase) {
        return new CategoryPresenter(categoryUseCase);
    }
    
}
