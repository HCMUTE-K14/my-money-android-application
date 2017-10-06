package com.vn.hcmute.team.cortana.mymoney.ui.saving;

import com.vn.hcmute.team.cortana.mymoney.model.Saving;
import com.vn.hcmute.team.cortana.mymoney.ui.base.view.BaseView;
import java.util.List;

/**
 * Created by kunsubin on 8/23/2017.
 */

public interface SavingContract {
    
    interface View extends BaseView {
        
        void showListSaving(List<Saving> savings);
        
        void showSaving();
        
        void onSuccessCreateSaving();
        
        void onSuccessDeleteSaving();
        
        void onSuccessUpdateSaving();
        
        void onSuccessTakeIn();
        
        void onSuccessTakeOut();
        
        void showError(String message);
        
        void loading(boolean isLoading);
        
    }
    
    interface Presenter {
        
        void getSaving();
        
        void getSavingById(String id);
        
        void createSaving(Saving saving);
        
        void updateSaving(Saving saving);
        
        void deleteSaving(String idSaving);
        
        void takeIn(String idWallet, String idSaving, String moneyUpdateWallet,
                  String moneyUpdateSaving);
        
        void takeOut(String idWallet, String idSaving, String moneyUpdateWallet,
                  String moneyUpdateSaving);
        
        void unSubscribe();
        
    }
}
