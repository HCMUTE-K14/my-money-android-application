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
        SavingRequest savingRequest = new SavingRequest(Action.ACTION_GET_SAVING,
                  new BaseCallBack<Object>() {
                      @Override
                      public void onSuccess(Object value) {
                          getView().loading(false);
                          getView().showListSaving((List<Saving>) value);
                          
                      }
                      
                      @Override
                      public void onFailure(Throwable throwable) {
                          getView().loading(false);
                          getView().showError(throwable.getMessage());
                      }
                      
                      @Override
                      public void onLoading() {
                          getView().loading(true);
                      }
                  }, null, null);
        mSavingUseCase.subscribe(savingRequest);
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
    
    @Override
    public void unSubscribe() {
        mSavingUseCase.unSubscribe();
    }
}
