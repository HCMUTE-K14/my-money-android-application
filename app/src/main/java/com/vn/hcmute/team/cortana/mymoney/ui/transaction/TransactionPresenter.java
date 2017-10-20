package com.vn.hcmute.team.cortana.mymoney.ui.transaction;

import com.vn.hcmute.team.cortana.mymoney.model.Image;
import com.vn.hcmute.team.cortana.mymoney.model.Transaction;
import com.vn.hcmute.team.cortana.mymoney.ui.base.BasePresenter;
import com.vn.hcmute.team.cortana.mymoney.ui.base.listener.BaseCallBack;
import com.vn.hcmute.team.cortana.mymoney.ui.tools.galleryloader.model.ImageGallery;
import com.vn.hcmute.team.cortana.mymoney.ui.transaction.TransactionContract.AddUpdateView;
import com.vn.hcmute.team.cortana.mymoney.usecase.base.Action;
import com.vn.hcmute.team.cortana.mymoney.usecase.base.TypeRepository;
import com.vn.hcmute.team.cortana.mymoney.usecase.remote.ImageUseCase;
import com.vn.hcmute.team.cortana.mymoney.usecase.remote.ImageUseCase.ImageRequest;
import com.vn.hcmute.team.cortana.mymoney.usecase.remote.TransactionUseCase;
import com.vn.hcmute.team.cortana.mymoney.usecase.remote.TransactionUseCase.TransactionRequest;
import com.vn.hcmute.team.cortana.mymoney.utils.logger.MyLogger;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;

/**
 * Created by infamouSs on 9/28/17.
 */

public class TransactionPresenter extends BasePresenter<TransactionContract.View> implements
                                                                                  TransactionContract.Presenter {
    
    public static final String TAG = TransactionPresenter.class.getSimpleName();
    
    private TransactionUseCase mTransactionUseCase;
    private ImageUseCase mImageUseCase;
    
    @Inject
    public TransactionPresenter(TransactionUseCase transactionUseCase, ImageUseCase imageUseCase) {
        this.mTransactionUseCase = transactionUseCase;
        this.mImageUseCase = imageUseCase;
    }
    
    @Override
    public void addTransaction(final Transaction transaction, List<ImageGallery> galleryList) {
        final TransactionRequest request = new TransactionRequest(Action.ACTION_ADD_TRANSACTION,
                  new BaseCallBack<Object>() {
                      @Override
                      public void onSuccess(Object value) {
                          getView().loading(false);
                          ((AddUpdateView) getView()).onAddSuccessTransaction((String) value);
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
                  }, transaction, null, TypeRepository.REMOTE);
        
        if (galleryList != null && !galleryList.isEmpty()) {
            List<String> paths = new ArrayList<>();
            for (ImageGallery img : galleryList) {
                paths.add(img.getPath());
            }
            ImageRequest imageRequest = new ImageRequest(Action.ACTION_UPLOAD_IMAGE,
                      new BaseCallBack<Object>() {
                          @Override
                          public void onSuccess(Object value) {
                              getView().loading(false);
                              List<Image> images = (List<Image>) value;
                              transaction.setImage(images);
                              
                              mTransactionUseCase.subscribe(request);
                          }
                          
                          @Override
                          public void onFailure(Throwable throwable) {
                              getView().loading(false);
                          }
                          
                          @Override
                          public void onLoading() {
                              getView().loading(true);
                          }
                      }, new String[]{"detail"}, paths.toArray(new String[paths.size()]));
            mImageUseCase.subscribe(imageRequest);
        } else {
            MyLogger.d("NO GALLERY");
            mTransactionUseCase.subscribe(request);
        }
        
    }
    
    @Override
    public void updateTransaction(Transaction transaction) {
        
        TransactionRequest request = new TransactionRequest(
                  Action.ACTION_UPDATE_TRANSACTION,
                  new BaseCallBack<Object>() {
                      @Override
                      public void onSuccess(Object value) {
                          getView().loading(false);
                          ((AddUpdateView) getView()).onUpdateSuccessTransaction((String) value);
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
                  }, transaction, null, TypeRepository.REMOTE);
        
        mTransactionUseCase.subscribe(request);
    }
    
    @Override
    public void getAllTransaction() {
        TransactionRequest request = new TransactionRequest(
                  Action.ACTION_GET_ALL_TRANSACTION,
                  new BaseCallBack<Object>() {
                      @Override
                      public void onSuccess(Object value) {
                          getView().loading(false);
                          getView().showAllListTransaction((List<Transaction>)value);
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
                  }, null, null, TypeRepository.REMOTE);
    
        mTransactionUseCase.subscribe(request);
    }
    
    @Override
    public void getTransactionByEvent(String eventId) {
        TransactionRequest request = new TransactionRequest(
                  Action.ACTION_GET_TRANSACTION_BY_EVENT,
                  new BaseCallBack<Object>() {
                      @Override
                      public void onSuccess(Object value) {
                          getView().loading(false);
                          getView().showAllListTransaction((List<Transaction>)value);
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
                  }, null, new String[]{eventId}, TypeRepository.REMOTE);
    
        mTransactionUseCase.subscribe(request);
    }
    
    @Override
    public void unSubscribe() {
        mTransactionUseCase.unSubscribe();
    }
}
