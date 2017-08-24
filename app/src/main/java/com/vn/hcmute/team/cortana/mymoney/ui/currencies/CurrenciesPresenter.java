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
    
    private CurrenciesUseCase mCurrenciesUseCase;
    
    @Inject
    public CurrenciesPresenter(
              CurrenciesUseCase currenciesUseCase) {
        mCurrenciesUseCase = currenciesUseCase;
    }
    
    @Override
    public void getCurrencies() {
        
        CurrenciesRequest request = new CurrenciesRequest(Action.ACCTION_GET_CURRENCIES,
                  new BaseCallBack<Object>() {
                      
                      @Override
                      public void onSuccess(Object value) {
                          getView().loading(false);
                          if (value == null) {
                              getView().showEmpty();
                          } else {
                              getView().showCurrencies((List<Currencies>) value);
                          }
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
                  });
        
        mCurrenciesUseCase.subscribe(request);
    }
    
    @Override
    public void unSubscribe() {
        mCurrenciesUseCase.unSubscribe();
    }
}
