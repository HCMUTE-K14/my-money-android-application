package com.vn.hcmute.team.cortana.mymoney.ui.saving;

import com.vn.hcmute.team.cortana.mymoney.model.Saving;
import com.vn.hcmute.team.cortana.mymoney.ui.base.view.BaseView;
import java.util.List;

/**
 * Created by kunsubin on 8/23/2017.
 */

public interface SavingContract {
    
    interface View extends BaseView {
    
        void onSuccessCreate(String message);
        void onSuccessUpdate(String message);
        void onSuccessDelete(String message);
        void onSuccessGetSaving(List<Saving> list);
        
        void onFailure(String message);
    }
    
    interface Presenter {
        
        void getSaving();
        
        void createSaving(Saving saving);
        
        void updateSaving(Saving saving);
        
        void deleteSaving(String idSaving);
        
        void takeIn(String idWallet, String idSaving, String money);
        
        void takeOut(String idWallet, String idSaving, String money);
        
    }
}
