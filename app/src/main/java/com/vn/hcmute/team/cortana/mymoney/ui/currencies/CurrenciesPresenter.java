package com.vn.hcmute.team.cortana.mymoney.ui.currencies;

import com.vn.hcmute.team.cortana.mymoney.model.Currencies;
import com.vn.hcmute.team.cortana.mymoney.ui.base.BasePresenter;
import com.vn.hcmute.team.cortana.mymoney.ui.base.listener.BaseCallBack;
import com.vn.hcmute.team.cortana.mymoney.usecase.base.Action;
import com.vn.hcmute.team.cortana.mymoney.usecase.remote.CurrenciesUseCase;
import com.vn.hcmute.team.cortana.mymoney.usecase.remote.CurrenciesUseCase.CurrenciesRequest;
import java.util.List;
import javax.inject.Inject;

/**
 * Created by kunsubin on 8/22/2017.
 */

public class CurrenciesPresenter extends BasePresenter<CurrenciesContract.View> implements
                                                                                CurrenciesContract.Presenter {
    
    CurrenciesUseCase mCurrenciesUseCase;
    
    @Inject
    public CurrenciesPresenter(
              CurrenciesUseCase currenciesUseCase) {
        mCurrenciesUseCase = currenciesUseCase;
    }
    
    @Override
    public void getCurrencies() {
        BaseCallBack<Object> mObjectBaseCallBack = new BaseCallBack<Object>() {
        
            @Override
            public void onSuccess(Object value) {
                getView().onSuccessGetCurrencies((List<Currencies>) value);
                // getView().onSuccessGetWallet();
            }
        
            @Override
            public void onFailure(Throwable throwable) {
                getView().onFailureGetCurrencies(throwable.getMessage());
            }
        
            @Override
            public void onLoading() {
            
            }
        };
        CurrenciesRequest currenciesRequest=new CurrenciesRequest(Action.ACCTION_GET_CURRENCIES,mObjectBaseCallBack,null,null);
        mCurrenciesUseCase.subscribe(currenciesRequest);
    }
}
