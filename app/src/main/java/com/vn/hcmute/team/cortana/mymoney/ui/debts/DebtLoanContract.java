package com.vn.hcmute.team.cortana.mymoney.ui.debts;

import com.vn.hcmute.team.cortana.mymoney.model.DebtLoan;
import com.vn.hcmute.team.cortana.mymoney.ui.base.view.BaseView;
import java.util.List;

/**
 * Created by infamouSs on 10/22/17.
 */

public interface DebtLoanContract {
    
    public interface View extends BaseView {
        
        void onFailure(String message);
        
        void loading(boolean isLoading);
    }
    
    public interface ShowView extends View {
        
        void showList(List<DebtLoan> list);
        
        void showEmpty();
    }
    
    public interface AddEditView extends View {
        
        void addDebtLoanSuccessful(DebtLoan debtLoan, String message);
        
        void editDebtLoanSuccessful(DebtLoan debtLoan, String message);
    }
    
    public interface Presenter {
        
        void getDebtLoanByType(String type);
        
        void addDebtLoan(DebtLoan debtLoan);
        
        void unSubscribe();
    }
}
