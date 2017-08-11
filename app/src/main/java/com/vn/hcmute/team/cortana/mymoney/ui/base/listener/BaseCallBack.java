package com.vn.hcmute.team.cortana.mymoney.ui.base.listener;

/**
 * Created by infamouSs on 8/11/17.
 */

public interface BaseCallBack<T> {
    
    void onSuccess(T value);
    
    void onFailure(Throwable throwable);
    
    void onLoading();
    
}
