package com.vn.hcmute.team.cortana.mymoney.ui.budget;

import com.vn.hcmute.team.cortana.mymoney.model.Budget;
import com.vn.hcmute.team.cortana.mymoney.ui.base.BasePresenter;
import com.vn.hcmute.team.cortana.mymoney.ui.base.listener.BaseCallBack;
import com.vn.hcmute.team.cortana.mymoney.usecase.base.Action;
import com.vn.hcmute.team.cortana.mymoney.usecase.base.TypeRepository;
import com.vn.hcmute.team.cortana.mymoney.usecase.remote.BudgetUseCase;
import com.vn.hcmute.team.cortana.mymoney.usecase.remote.BudgetUseCase.BudgetRequest;
import java.util.List;
import javax.inject.Inject;

/**
 * Created by kunsubin on 8/23/2017.
 */

public class BudgetPresenter extends BasePresenter<BudgetContract.View> implements
                                                                        BudgetContract.Presenter {
    
    BudgetUseCase mBudgetUseCase;
    
    @Inject
    public BudgetPresenter(BudgetUseCase budgetUseCase) {
        mBudgetUseCase = budgetUseCase;
    }
    
    
    @Override
    public void getBudget() {
        BaseCallBack<Object> mObjectBaseCallBack = new BaseCallBack<Object>() {
            @Override
            public void onSuccess(Object value) {
                getView().loading(false);
                getView().onSuccessGetListBudget((List<Budget>) value);
            }
            
            @Override
            public void onFailure(Throwable throwable) {
                getView().loading(false);
                getView().onFailure(throwable.getMessage());
            }
            
            @Override
            public void onLoading() {
                getView().loading(true);
            }
        };
        
        BudgetRequest budgetRequest = new BudgetRequest(Action.ACTION_GET_BUDGET,
                  mObjectBaseCallBack, null, null);
        budgetRequest.setTypeRepository(TypeRepository.LOCAL);
        mBudgetUseCase.subscribe(budgetRequest);
    }
    
    @Override
    public void createBudget(Budget budget) {
        BaseCallBack<Object> mObjectBaseCallBack = new BaseCallBack<Object>() {
            @Override
            public void onSuccess(Object value) {
                getView().loading(false);
                getView().onSuccessCreateBudget((String) value);
            }
            
            @Override
            public void onFailure(Throwable throwable) {
                getView().loading(false);
                getView().onFailure(throwable.getMessage());
            }
            
            @Override
            public void onLoading() {
                getView().loading(true);
            }
        };
        BudgetRequest budgetRequest = new BudgetRequest(Action.ACTION_CREATE_BUDGET,
                  mObjectBaseCallBack, budget, null);
        budgetRequest.setTypeRepository(TypeRepository.LOCAL);
        mBudgetUseCase.subscribe(budgetRequest);
    }
    
    @Override
    public void updateBudget(Budget budget) {
        BaseCallBack<Object> mObjectBaseCallBack = new BaseCallBack<Object>() {
            @Override
            public void onSuccess(Object value) {
                getView().loading(false);
                getView().onSuccessUpdateBudget((String) value);
            }
            
            @Override
            public void onFailure(Throwable throwable) {
                getView().loading(false);
                getView().onFailure(throwable.getMessage());
            }
            
            @Override
            public void onLoading() {
                getView().loading(false);
            }
        };
        BudgetRequest budgetRequest = new BudgetRequest(Action.ACTION_UPDATE_BUDGET,
                  mObjectBaseCallBack, budget, null);
        budgetRequest.setTypeRepository(TypeRepository.LOCAL);
        mBudgetUseCase.subscribe(budgetRequest);
    }
    
    @Override
    public void updateStatusBudget(List<Budget> budgetList) {
        BaseCallBack<Object> mObjectBaseCallBack = new BaseCallBack<Object>() {
            @Override
            public void onSuccess(Object value) {
                getView().loading(false);
                getView().onSuccessUpdateBudget((String) value);
            }
            
            @Override
            public void onFailure(Throwable throwable) {
                getView().loading(false);
                getView().onFailure(throwable.getMessage());
            }
            
            @Override
            public void onLoading() {
                getView().loading(false);
            }
        };
        BudgetRequest budgetRequest = new BudgetRequest(Action.ACTION_UPDATE_STATUS_BUDGET,
                  mObjectBaseCallBack, null, null);
        budgetRequest.setBudgetList(budgetList);
        budgetRequest.setTypeRepository(TypeRepository.LOCAL);
        mBudgetUseCase.subscribe(budgetRequest);
    }
    
    @Override
    public void deleteBudget(String budgetId) {
        BaseCallBack<Object> mObjectBaseCallBack = new BaseCallBack<Object>() {
            @Override
            public void onSuccess(Object value) {
                getView().loading(false);
                getView().onSuccessDeleteBudget((String) value);
            }
            
            @Override
            public void onFailure(Throwable throwable) {
                getView().loading(false);
                getView().onFailure(throwable.getMessage());
            }
            
            @Override
            public void onLoading() {
                getView().loading(true);
            }
        };
        
        String[] params = {budgetId};
        BudgetRequest budgetRequest = new BudgetRequest(Action.ACTION_DELETE_BUDGET,
                  mObjectBaseCallBack, null, params);
        budgetRequest.setTypeRepository(TypeRepository.LOCAL);
        mBudgetUseCase.subscribe(budgetRequest);
    }
    
    @Override
    public void unSubscribe() {
        mBudgetUseCase.unSubscribe();
    }
}
