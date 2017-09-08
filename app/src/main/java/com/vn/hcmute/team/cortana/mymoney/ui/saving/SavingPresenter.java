package com.vn.hcmute.team.cortana.mymoney.ui.saving;

import com.vn.hcmute.team.cortana.mymoney.model.Saving;
import com.vn.hcmute.team.cortana.mymoney.ui.base.BasePresenter;
import com.vn.hcmute.team.cortana.mymoney.ui.base.listener.BaseCallBack;
import com.vn.hcmute.team.cortana.mymoney.usecase.base.Action;
import com.vn.hcmute.team.cortana.mymoney.usecase.remote.SavingUseCase;
import com.vn.hcmute.team.cortana.mymoney.usecase.remote.SavingUseCase.SavingRequest;
import java.util.List;
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
        SavingRequest savingRequest = new SavingRequest(Action.ACTION_CREATE_SAVING,
                  new BaseCallBack<Object>() {
                      @Override
                      public void onSuccess(Object value) {
                          getView().loading(false);
                          getView().onSuccessCreateSaving();
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
                  }, saving, null);
        mSavingUseCase.subscribe(savingRequest);
    }
    
    @Override
    public void updateSaving(Saving saving) {
        SavingRequest savingRequest = new SavingRequest(Action.ACTION_UPDATE_SAVING,
                  new BaseCallBack<Object>() {
                      @Override
                      public void onSuccess(Object value) {
                          getView().loading(false);
                          getView().onSuccessUpdateSaving();
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
                  }, saving, null);
        mSavingUseCase.subscribe(savingRequest);
    }
    
    @Override
    public void deleteSaving(String idSaving) {
        String[] params = {idSaving};
        SavingRequest savingRequest = new SavingRequest(Action.ACTION_DELETE_SAVING,
                  new BaseCallBack<Object>() {
                      @Override
                      public void onSuccess(Object value) {
                          getView().loading(false);
                          getView().onSuccessDeleteSaving();
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
                  }, null, params);
        mSavingUseCase.subscribe(savingRequest);
    }
    
    @Override
    public void takeIn(String idWallet, String idSaving, String money) {
        String[] params = {idWallet, idSaving, money};
        SavingRequest savingRequest = new SavingRequest(Action.ACTION_TAKE_IN_SAVING,
                  new BaseCallBack<Object>() {
                      @Override
                      public void onSuccess(Object value) {
                          getView().loading(false);
                          getView().onSuccessDeleteSaving();
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
                  }, null, params);
        mSavingUseCase.subscribe(savingRequest);
    }
    
    @Override
    public void takeOut(String idWallet, String idSaving, String money) {
        String[] params = {idWallet, idSaving, money};
        SavingRequest savingRequest = new SavingRequest(Action.ACTION_TAKE_OUT_SAVING,
                  new BaseCallBack<Object>() {
                      @Override
                      public void onSuccess(Object value) {
                          getView().loading(false);
                          getView().onSuccessDeleteSaving();
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
                  }, null, params);
        mSavingUseCase.subscribe(savingRequest);
    }
    
    @Override
    public void unSubscribe() {
        mSavingUseCase.unSubscribe();
    }
}
