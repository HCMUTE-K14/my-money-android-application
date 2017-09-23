package com.vn.hcmute.team.cortana.mymoney.ui.category;

import com.vn.hcmute.team.cortana.mymoney.model.Category;
import com.vn.hcmute.team.cortana.mymoney.ui.base.view.BaseView;
import java.util.List;

/**
 * Created by infamouSs on 9/15/17.
 */

public interface CategoryContract {
    
    interface View extends BaseView {
        
        void initializeView();
        
        void onFailure(String message);
        
        void loading(boolean isLoading);
        
    }
    
    interface ShowView extends View {
        
        void showCategory(List<Category> categories);
        
        void showEmpty();
    }
    
    interface AddEditView extends View {
        
        void onAddSuccessCategory(String message, Category category);
        
        void onEditSuccessCategory(String message, Category category);
    }
    
    interface DeleteView extends View {
        
        void onDeleteSuccessCategory(String message, Category category);
    }
    
    interface Presenter {
        
        void getCategory(String action);
        
        void getCategoryByType(String type, String transType);
        
        void addCategory(Category category);
        
        void updateCategory(Category category, String oldParentId, String newParentId);
        
        void deleteCategory(Category category);
        
        void unSubscribe();
    }
}
