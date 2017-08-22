package com.vn.hcmute.team.cortana.mymoney.ui.base;

import android.os.Bundle;
import com.vn.hcmute.team.cortana.mymoney.ui.base.view.BaseView;
import java.lang.ref.WeakReference;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by infamouSs on 8/11/17.
 */

public abstract class BasePresenter<T extends BaseView> {
    
    protected AtomicBoolean isViewAlive = new AtomicBoolean();
    
    private WeakReference<T> view;
    
    public T getView() {
        return view.get();
    }
    
    public void setView(T view) {
        this.view = new WeakReference<>(view);
    }
    
    public void initialize(Bundle extras) {
    }
    
    public void start() {
        isViewAlive.set(true);
    }
    
    public void finalizeView() {
        isViewAlive.set(false);
    }
    
}
