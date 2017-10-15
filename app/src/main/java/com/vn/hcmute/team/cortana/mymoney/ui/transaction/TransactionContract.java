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
        
        void onAddSuccessTransaction(String message);
        
        void onUpdateSuccessTransaction(String message);
    }
    interface Presenter {
        
        void addTransaction(Transaction transaction,List<ImageGallery> galleryList);
        
        void updateTransaction(Transaction transaction);
        
        void getAllTransaction();
        
        void unSubscribe();
        
    }
}
