package com.vn.hcmute.team.cortana.mymoney.ui.debts;

import com.vn.hcmute.team.cortana.mymoney.model.DebtLoan;
import com.vn.hcmute.team.cortana.mymoney.ui.base.BasePresenter;
import com.vn.hcmute.team.cortana.mymoney.ui.base.listener.BaseCallBack;
import com.vn.hcmute.team.cortana.mymoney.ui.debts.DebtLoanContract.AddEditView;
import com.vn.hcmute.team.cortana.mymoney.ui.debts.DebtLoanContract.ShowView;
import com.vn.hcmute.team.cortana.mymoney.usecase.base.Action;
import com.vn.hcmute.team.cortana.mymoney.usecase.base.TypeRepository;
import com.vn.hcmute.team.cortana.mymoney.usecase.remote.DebtLoanUseCase;
import com.vn.hcmute.team.cortana.mymoney.usecase.remote.DebtLoanUseCase.DebtLoanRequest;
import java.util.List;
import javax.inject.Inject;

/**
 * Created by infamouSs on 10/22/17.
 */

public class DebtLoanPresenter extends BasePresenter<DebtLoanContract.View> implements
                                                                            DebtLoanContract.Presenter {
    
    private DebtLoanUseCase mDebtLoanUseCase;
    
    @Inject
    public DebtLoanPresenter(DebtLoanUseCase debtLoanUseCase) {
        this.mDebtLoanUseCase = debtLoanUseCase;
    }
    
    @Override
    public void getDebtLoanByType(String type) {
        DebtLoanRequest request = new DebtLoanRequest(Action.ACTION_GET_DEBT_LOAN_BY_TYPE,
                  new BaseCallBack<Object>() {
                      @Override
                      public void onSuccess(Object value) {
                          getView().loading(false);
                          
                          List<DebtLoan> list = (List<DebtLoan>) value;
                          
                          if (list != null && !list.isEmpty()) {
                              ((ShowView) getView()).showList(list);
                          } else {
                              ((ShowView) getView()).showEmpty();
                          }
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
                  }, null, new String[]{type}, TypeRepository.REMOTE);
        
        mDebtLoanUseCase.subscribe(request);
    }
    
    @Override
    public void addDebtLoan(final DebtLoan debtLoan) {
        DebtLoanRequest request = new DebtLoanRequest(Action.ACTION_ADD_DEBT_LOAN,
                  new BaseCallBack<Object>() {
                      @Override
                      public void onSuccess(Object value) {
                          getView().loading(false);
                          ((AddEditView) getView()).addDebtLoanSuccessful(debtLoan, (String) value);
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
                  }, debtLoan, null, TypeRepository.REMOTE);
        
        mDebtLoanUseCase.subscribe(request);
    }
    
    @Override
    public void unSubscribe() {
        mDebtLoanUseCase.unSubscribe();
    }
}
