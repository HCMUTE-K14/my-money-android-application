package com.vn.hcmute.team.cortana.mymoney.ui.saving;

import com.vn.hcmute.team.cortana.mymoney.model.Saving;
import com.vn.hcmute.team.cortana.mymoney.ui.base.BasePresenter;
import com.vn.hcmute.team.cortana.mymoney.usecase.remote.SavingUseCase;
import javax.inject.Inject;

/**
 * Created by kunsubin on 8/23/2017.
 */

public class SavingPresenter extends BasePresenter<SavingContract.View> implements
                                                                        SavingContract.Presenter {
    
    private SavingUseCase mSavingUseCase;
    
    @Inject
    public SavingPresenter(
              SavingUseCase savingUseCase) {
        mSavingUseCase = savingUseCase;
    }
    
    @Override
    public void getSaving() {
        
    }
    
    @Override
    public void getSavingById(String id) {
        
    }
    
    @Override
    public void createSaving(Saving saving) {
        
    }
    
    @Override
    public void updateSaving(Saving saving) {
        
    }
    
    @Override
    public void deleteSaving(String idSaving) {
        
    }
    
    @Override
    public void takeIn(String idWallet, String idSaving, String money) {
        
    }
    
    @Override
    public void takeOut(String idWallet, String idSaving, String money) {
        
    }
}
