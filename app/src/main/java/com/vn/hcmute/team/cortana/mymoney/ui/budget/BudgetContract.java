package com.vn.hcmute.team.cortana.mymoney.ui.budget;

import com.vn.hcmute.team.cortana.mymoney.model.Budget;
import com.vn.hcmute.team.cortana.mymoney.ui.base.view.BaseView;

/**
 * Created by kunsubin on 8/23/2017.
 */

public interface BudgetContract {
    interface View extends BaseView {
        
        void onSuccess(Object object);
        
        
        
        void onFailure(String message);
    }
    
    interface Presenter {
        void getBudget();
        void createBudget(Budget budget);
        void updateBudget(Budget budget);
        void deleteBudget(String budgetId);
    }
}
