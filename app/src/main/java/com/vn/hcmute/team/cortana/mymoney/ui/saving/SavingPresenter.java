package com.vn.hcmute.team.cortana.mymoney.ui.saving;

import com.vn.hcmute.team.cortana.mymoney.model.Saving;
import com.vn.hcmute.team.cortana.mymoney.ui.base.BasePresenter;
import com.vn.hcmute.team.cortana.mymoney.ui.base.listener.BaseCallBack;
import com.vn.hcmute.team.cortana.mymoney.usecase.base.Action;
import com.vn.hcmute.team.cortana.mymoney.usecase.remote.SavingUseCase;
import com.vn.hcmute.team.cortana.mymoney.usecase.remote.SavingUseCase.SavingRequest;
import com.vn.hcmute.team.cortana.mymoney.utils.logger.MyLogger;
import java.util.List;
import javax.inject.Inject;

/**
 * Created by kunsubin on 8/23/2017.
 */

public class SavingPresenter extends BasePresenter<SavingContract.View> implements SavingContract.Presenter {
    
    SavingUseCase mSavingUseCase;
    
    @Inject
    public SavingPresenter(
              SavingUseCase savingUseCase) {
        mSavingUseCase = savingUseCase;
    }
    BaseCallBack<Object> mObjectBaseCallBack = new BaseCallBack<Object>() {
        
        @Override
        public void onSuccess(Object value) {
            getView().onSuccess((String) value);
        }
        
        @Override
        public void onFailure(Throwable throwable) {
            getView().onFailure(throwable.getMessage());
        }
        
        @Override
        public void onLoading() {
            
        }
    };
    
    @Override
    public void getSaving() {
        BaseCallBack<Object> callBack = new BaseCallBack<Object>() {
        
            @Override
            public void onSuccess(Object value) {
                getView().onSuccessGetSaving((List<Saving>) value);
            }
        
            @Override
            public void onFailure(Throwable throwable) {
                getView().onFailure(throwable.getMessage());
            }
        
            @Override
            public void onLoading() {
            
            }
        };
        SavingRequest savingRequest=new SavingRequest(Action.ACTION_GET_SAVING,callBack, null,null);
        mSavingUseCase.subscribe(savingRequest);
    }
    
    @Override
    public void createSaving(Saving saving) {
        SavingRequest savingRequest=new SavingRequest(Action.ACTION_CREATE_SAVING,mObjectBaseCallBack, saving,null);
        mSavingUseCase.subscribe(savingRequest);
    }
    
    @Override
    public void updateSaving(Saving saving) {
        MyLogger.d("update saving preseter");
        SavingRequest savingRequest=new SavingRequest(Action.ACTION_UPDATE_SAVING,mObjectBaseCallBack,saving,null);
        mSavingUseCase.subscribe(savingRequest);
        MyLogger.d("update saving preseter");
    }
    
    @Override
    public void deleteSaving(String idSaving) {
        String[] params={idSaving};
        SavingRequest savingRequest=new SavingRequest(Action.ACTION_DELETE_SAVING,mObjectBaseCallBack,null,params);
        mSavingUseCase.subscribe(savingRequest);
    }
    
    @Override
    public void takeIn(String idWallet, String idSaving, String money) {
        String[] params={idWallet,idSaving,money};
        SavingRequest savingRequest=new SavingRequest(Action.ACTION_TAKE_IN_SAVING,mObjectBaseCallBack,null,params);
        mSavingUseCase.subscribe(savingRequest);
    }
    
    @Override
    public void takeOut(String idWallet, String idSaving, String money) {
        String[] params={idWallet,idSaving,money};
        SavingRequest savingRequest=new SavingRequest(Action.ACTION_TAKE_OUT_SAVING,mObjectBaseCallBack,null,params);
        mSavingUseCase.subscribe(savingRequest);
    }
}
