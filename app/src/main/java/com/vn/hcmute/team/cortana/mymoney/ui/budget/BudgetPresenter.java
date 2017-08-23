package com.vn.hcmute.team.cortana.mymoney.ui.budget;

import com.vn.hcmute.team.cortana.mymoney.model.Budget;
import com.vn.hcmute.team.cortana.mymoney.ui.base.BasePresenter;
import com.vn.hcmute.team.cortana.mymoney.ui.base.listener.BaseCallBack;
import com.vn.hcmute.team.cortana.mymoney.usecase.base.Action;
import com.vn.hcmute.team.cortana.mymoney.usecase.remote.BudgetUseCase;
import com.vn.hcmute.team.cortana.mymoney.usecase.remote.BudgetUseCase.BudgetRequest;
import javax.inject.Inject;

/**
 * Created by kunsubin on 8/23/2017.
 */

public class BudgetPresenter extends BasePresenter<BudgetContract.View> implements
                                                                         BudgetContract.Presenter {
    
    BudgetUseCase mBudgetUseCase;
    
    @Inject
    public BudgetPresenter(
              BudgetUseCase budgetUseCase) {
        mBudgetUseCase = budgetUseCase;
    }
    
    BaseCallBack<Object> mObjectBaseCallBack = new BaseCallBack<Object>() {
        @Override
        public void onSuccess(Object value) {
            getView().onSuccess(value);
        }
        
        @Override
        public void onFailure(Throwable throwable) {
            getView().onFailure(throwable.getMessage());
        }
        
        @Override
        public void onLoading() {
            
        }
    };
    
    
    @Override
    public void getBudget() {
        BudgetRequest budgetRequest = new BudgetRequest(Action.ACTION_GET_BUDGET,
                  mObjectBaseCallBack, null, null);
        mBudgetUseCase.subscribe(budgetRequest);
    }
    
    @Override
    public void createBudget(Budget budget) {
        BudgetRequest budgetRequest = new BudgetRequest(Action.ACTION_CREATE_BUDGET,
                  mObjectBaseCallBack, budget, null);
        mBudgetUseCase.subscribe(budgetRequest);
    }
    
    @Override
    public void updateBudget(Budget budget) {
        BudgetRequest budgetRequest = new BudgetRequest(Action.ACTION_UPDATE_BUDGET,
                  mObjectBaseCallBack, budget, null);
        mBudgetUseCase.subscribe(budgetRequest);
    }
    
    @Override
    public void deleteBudget(String budgetId) {
        String[] params = {budgetId};
        BudgetRequest budgetRequest = new BudgetRequest(Action.ACTION_DELETE_BUDGET,
                  mObjectBaseCallBack, null, params);
        mBudgetUseCase.subscribe(budgetRequest);
    }
}
