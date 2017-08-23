package com.vn.hcmute.team.cortana.mymoney.tools.galleryloader.ui;

import com.vn.hcmute.team.cortana.mymoney.tools.galleryloader.helper.imageloader.ImageFileLoader;
import com.vn.hcmute.team.cortana.mymoney.tools.galleryloader.listener.ImageLoaderListener;
import com.vn.hcmute.team.cortana.mymoney.tools.galleryloader.model.ReturnGalleryValue;
import com.vn.hcmute.team.cortana.mymoney.tools.galleryloader.ui.base.GalleryBasePresenter;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by infamouSs on 8/23/17.
 */

public class GalleryLoaderPresenter extends GalleryBasePresenter<GalleryLoaderContract.View> implements GalleryLoaderContract.Presenter {
    
    private ImageFileLoader mImageFileLoader;
    
    public GalleryLoaderPresenter(ImageFileLoader imageFileLoader){
        this.mImageFileLoader=imageFileLoader;
        mCompositeDisposable=new CompositeDisposable();
    }
    private CompositeDisposable mCompositeDisposable;
    @Override
    public void loadImages(boolean isFolderMode){
        
        if(getView() == null){
            return;
        }
        
        getView().loading(true);
        
        mImageFileLoader.loadDeviceImages(isFolderMode, new ImageLoaderListener() {
            @Override
            public void onLoaded(Observable<ReturnGalleryValue> result) {
                
                if(!mCompositeDisposable.isDisposed()){
                    Disposable disposable=result.subscribeOn(Schedulers.io())
                              .observeOn(AndroidSchedulers.mainThread())
                              .singleOrError()
                              .subscribeWith(new DisposableSingleObserver<ReturnGalleryValue>() {
                                  @Override
                                  public void onSuccess(
                                            @NonNull ReturnGalleryValue returnGalleryValue) {
                                      final boolean isEmpty = returnGalleryValue != null
                                                ? returnGalleryValue.getFolders().isEmpty()
                                                : returnGalleryValue.getImages().isEmpty();
    
                                      if(isEmpty){
                                          getView().showEmpty();
                                      }else{
                                          getView().showResult(returnGalleryValue);
                                          getView().loading(false);
                                      }
                                      
                                  }
    
                                  @Override
                                  public void onError(@NonNull Throwable e) {
                                        getView().showEmpty();
                                  }
                              });
                    
                    mCompositeDisposable.add(disposable);
                }
                
                
            }
    
            @Override
            public void onFailure(Throwable throwable) {
                    getView().showError(throwable.getMessage());
            }
        });
    
    
    }
    
    
}
