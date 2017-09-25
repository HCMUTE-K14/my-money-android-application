package com.vn.hcmute.team.cortana.mymoney.di.component;

import com.vn.hcmute.team.cortana.mymoney.di.module.ActivityModule;
import com.vn.hcmute.team.cortana.mymoney.di.module.CategoryModule;
import com.vn.hcmute.team.cortana.mymoney.di.scope.PerActivity;
import com.vn.hcmute.team.cortana.mymoney.ui.category.CategoryByTypeFragment;
import com.vn.hcmute.team.cortana.mymoney.ui.category.ListParentCategoryActivity;
import com.vn.hcmute.team.cortana.mymoney.ui.category.ManagerCategoryFragment;
import dagger.Component;

/**
 * Created by infamouSs on 9/16/17.
 */
@PerActivity
@Component(modules = {ActivityModule.class,
          CategoryModule.class}, dependencies = ApplicationComponent.class)
public interface CategoryComponent {
    
    void inject(CategoryByTypeFragment categoryByTypeFragment);
    
    void inject(ManagerCategoryFragment managerCategoryFragment);
    
    void inject(ListParentCategoryActivity listParentCategoryFragment);
}
