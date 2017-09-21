package com.vn.hcmute.team.cortana.mymoney.ui.category;

import com.vn.hcmute.team.cortana.mymoney.model.Category;
import com.vn.hcmute.team.cortana.mymoney.ui.base.BasePresenter;
import com.vn.hcmute.team.cortana.mymoney.ui.base.listener.BaseCallBack;
import com.vn.hcmute.team.cortana.mymoney.ui.category.CategoryContract.AddEditView;
import com.vn.hcmute.team.cortana.mymoney.ui.category.CategoryContract.DeleteView;
import com.vn.hcmute.team.cortana.mymoney.ui.category.CategoryContract.ShowView;
import com.vn.hcmute.team.cortana.mymoney.usecase.base.Action;
import com.vn.hcmute.team.cortana.mymoney.usecase.base.TypeRepository;
import com.vn.hcmute.team.cortana.mymoney.usecase.remote.CategoryUseCase;
import com.vn.hcmute.team.cortana.mymoney.usecase.remote.CategoryUseCase.CategoryRequest;
import com.vn.hcmute.team.cortana.mymoney.utils.logger.MyLogger;
import java.util.List;
import javax.inject.Inject;

/**
 * Created by infamouSs on 9/15/17.
 */

public class CategoryPresenter extends BasePresenter<CategoryContract.View> implements
                                                                            CategoryContract.Presenter {
    
    public static final String TAG = CategoryPresenter.class.getSimpleName();
    
    private CategoryUseCase mCategoryUseCase;
    
    @Inject
    public CategoryPresenter(CategoryUseCase categoryUseCase) {
        this.mCategoryUseCase = categoryUseCase;
    }
    
    
    @Override
    public void getCategory(String action) {
        CategoryRequest categoryRequest = new CategoryRequest(action, new BaseCallBack<Object>() {
            @Override
            public void onSuccess(Object value) {
                getView().loading(false);
                List<Category> lists = (List<Category>) value;
                
                if (lists != null && lists.size() == 0) {
                    ((ShowView) getView()).showEmpty();
                } else if (lists != null) {
                    ((ShowView) getView()).showCategory(lists);
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
        }, null, null, TypeRepository.REMOTE);
        
        mCategoryUseCase.subscribe(categoryRequest);
    }
    
    @Override
    public void getCategoryByType(String type, String transType) {
        MyLogger.d(TAG, type + transType);
        CategoryRequest categoryRequest = new CategoryRequest(Action.ACTION_GET_CATEGORY_BY_TYPE,
                  new BaseCallBack<Object>() {
                      @Override
                      public void onSuccess(Object value) {
                          getView().loading(false);
                          List<Category> lists = (List<Category>) value;
                          if (lists != null && lists.size() == 0) {
                              ((ShowView) getView()).showEmpty();
                          } else if (lists != null) {
                              ((ShowView) getView()).showCategory(lists);
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
                  }, null, new String[]{type, transType}, TypeRepository.REMOTE);
        
        mCategoryUseCase.subscribe(categoryRequest);
    }
    
    @Override
    public void addCategory(final Category category) {
        String parentId;
        if (category.getParent() == null) {
            parentId = null;
        } else {
            parentId = category.getParent().getId();
        }
        CategoryRequest categoryRequest = new CategoryRequest(Action.ACTION_ADD_CATEGORY,
                  new BaseCallBack<Object>() {
                      @Override
                      public void onSuccess(Object value) {
                          getView().loading(false);
                          ((AddEditView) getView()).onAddSuccessCategory((String) value, category);
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
                  }, category, new String[]{parentId}, TypeRepository.REMOTE);
        
        mCategoryUseCase.subscribe(categoryRequest);
    }
    
    @Override
    public void updateCategory(final Category category, String oldParentId, String newParentId) {
        CategoryRequest categoryRequest = new CategoryRequest(Action.ACTION_UPDATE_CATEGORY,
                  new BaseCallBack<Object>() {
                      @Override
                      public void onSuccess(Object value) {
                          getView().loading(false);
                          ((AddEditView) getView()).onEditSuccessCategory((String) value, category);
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
                  }, category, new String[]{oldParentId, newParentId}, TypeRepository.REMOTE);
        
        mCategoryUseCase.subscribe(categoryRequest);
    }
    
    @Override
    public void deleteCategory(final Category category) {
        String parentId;
        if (category.getParent() == null) {
            parentId = null;
        } else {
            parentId = category.getParent().getId();
        }
        CategoryRequest categoryRequest = new CategoryRequest(Action.ACTION_DELETE_CATEGORY,
                  new BaseCallBack<Object>() {
                      @Override
                      public void onSuccess(Object value) {
                          getView().loading(false);
                          ((DeleteView) getView())
                                    .onDeleteSuccessCategory((String) value, category);
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
                  }, category, new String[]{parentId}, TypeRepository.REMOTE);
        
        mCategoryUseCase.subscribe(categoryRequest);
    }
    
    @Override
    public void unSubscribe() {
        mCategoryUseCase.unSubscribe();
    }
}
