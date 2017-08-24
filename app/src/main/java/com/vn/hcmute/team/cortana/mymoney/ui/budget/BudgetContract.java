package com.vn.hcmute.team.cortana.mymoney.ui.budget;

import com.vn.hcmute.team.cortana.mymoney.model.Budget;
import com.vn.hcmute.team.cortana.mymoney.ui.base.view.BaseView;
import java.util.List;

/**
 * Created by kunsubin on 8/23/2017.
 */

public interface BudgetContract {
    
    interface View extends BaseView {
        
        void onSuccessGetListBudget(List<Budget> list);
        
        void onSuccessCreateBudget(String message);
        
        void onSuccessUpdateBudget(String message);
        
        void onSucsessDeleteBudget(String message);
        
        void onFailure(String message);
        
        void loading(boolean isLoading);
    }
    
    interface Presenter {
        
        void getBudget();
        
        void createBudget(Budget budget);
        
        void updateBudget(Budget budget);
        
        void deleteBudget(String budgetId);
    }
}
