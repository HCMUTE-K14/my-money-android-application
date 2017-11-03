package com.vn.hcmute.team.cortana.mymoney.ui.transaction;

import com.vn.hcmute.team.cortana.mymoney.model.Transaction;
import com.vn.hcmute.team.cortana.mymoney.ui.base.view.BaseView;
import com.vn.hcmute.team.cortana.mymoney.ui.tools.galleryloader.model.ImageGallery;
import java.util.List;

/**
 * Created by infamouSs on 9/28/17.
 */

public interface TransactionContract {
    
    interface View extends BaseView {
        
        void showAllListTransaction(List<Transaction> list);
        
        
        void onFailure(String message);
        
        void loading(boolean isLoading);
    }
    
    interface AddUpdateView extends View {
        
        void onAddSuccessTransaction(Transaction transaction, String message);
        
        void onUpdateSuccessTransaction(Transaction transaction, String message);
    }
    
    
    interface DeleteView extends View {
        
        void onDeleteSuccessTransaction(String message);
    }
    
    interface Presenter {
        
        void addTransaction(Transaction transaction, List<ImageGallery> galleryList);
        
        void updateTransaction(Transaction transaction);
        
        void getAllTransaction();
        
        void getTransactionByEvent(String eventId);
        
        void getTransactionByBudget(String startDate, String endDate, String categoryId,
                  String walletId);
        
        void deleteTransaction(Transaction transaction);
        
        void unSubscribe();
        
    }
}
