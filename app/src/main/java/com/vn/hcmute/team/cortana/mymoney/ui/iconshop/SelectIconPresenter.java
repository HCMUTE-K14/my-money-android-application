package com.vn.hcmute.team.cortana.mymoney.ui.iconshop;

import com.vn.hcmute.team.cortana.mymoney.model.Icon;
import com.vn.hcmute.team.cortana.mymoney.ui.base.BasePresenter;
import com.vn.hcmute.team.cortana.mymoney.ui.base.listener.BaseCallBack;
import com.vn.hcmute.team.cortana.mymoney.usecase.base.Action;
import com.vn.hcmute.team.cortana.mymoney.usecase.remote.ImageUseCase;
import com.vn.hcmute.team.cortana.mymoney.usecase.remote.ImageUseCase.ImageRequest;
import java.util.List;
import javax.inject.Inject;

/**
 * Created by infamouSs on 9/10/17.
 */

public class SelectIconPresenter extends BasePresenter<SelectIconContract.View> implements
                                                                                SelectIconContract.Presenter {
    
    private ImageUseCase mImageUseCase;
    
    @Inject
    public SelectIconPresenter(ImageUseCase imageUseCase) {
        this.mImageUseCase = imageUseCase;
    }
    
    @Override
    public void getListIcon() {
        ImageRequest imageRequest = new ImageRequest(Action.ACTION_GET_LIST_ICON,
                  new BaseCallBack<Object>() {
                      
                      @Override
                      public void onSuccess(Object value) {
                          getView().loading(false);
                          List<Icon> icons = (List<Icon>) value;
                          if (icons != null && icons.size() != 0) {
                              getView().showListIcon(icons);
                          } else {
                              getView().showEmpty("No data.");
                          }
                      }
                      
                      @Override
                      public void onFailure(Throwable throwable) {
                          getView().loading(false);
                          getView().onFailure(throwable.getMessage());
                      }
                      
                      @Override
                      public void onLoading() {
                          getView().loading(true);
                      }
                  }, null);
        
        mImageUseCase.subscribe(imageRequest);
    }
    
    @Override
    public void getListIcon(int page) {
        ImageRequest imageRequest = new ImageRequest(Action.ACTION_GET_LIST_ICON,
                  new BaseCallBack<Object>() {
                      @Override
                      public void onSuccess(Object value) {
                          getView().loading(false);
                          List<Icon> icons = (List<Icon>) value;
                          if (icons != null && icons.size() != 0) {
                              getView().showListIcon(icons);
                          } else {
                              getView().showEmpty("No data.");
                          }
                      }
                      
                      @Override
                      public void onFailure(Throwable throwable) {
                          getView().loading(false);
                          getView().onFailure(throwable.getMessage());
                      }
                      
                      @Override
                      public void onLoading() {
                          getView().loading(true);
                      }
                  }, new String[]{String.valueOf(page)});
        
        mImageUseCase.subscribe(imageRequest);
    }
    
    @Override
    public void unSubscribe() {
        mImageUseCase.unSubscribe();
    }
}
