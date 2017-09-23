package com.vn.hcmute.team.cortana.mymoney.ui.base.listener;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

public abstract class EndlessScrollListener extends RecyclerView.OnScrollListener {
    
    public static String TAG = EndlessScrollListener.class.getSimpleName();
    int firstVisibleItem, visibleItemCount, totalItemCount;
    private int previousTotal = 0;
    private boolean loading = true;
    private int visibleThreshold = 5;
    private int currentPage = 1;
    
    private RecyclerView.LayoutManager mLayoutManager;
    private boolean isUseLinearLayoutManager;
    private boolean isUseGridLayoutManager;
    
    public EndlessScrollListener(LinearLayoutManager linearLayoutManager) {
        this.mLayoutManager = linearLayoutManager;
        isUseLinearLayoutManager = true;
        
    }
    
    public EndlessScrollListener(GridLayoutManager gridLayoutManager) {
        this.mLayoutManager = gridLayoutManager;
        isUseGridLayoutManager = true;
    }
    
    public EndlessScrollListener(LinearLayoutManager linearLayoutManager, int visibleThreshold) {
        this(linearLayoutManager);
        this.visibleItemCount = visibleThreshold;
    }
    
    public EndlessScrollListener(GridLayoutManager gridLayoutManager, int visibleThreshold) {
        this(gridLayoutManager);
        this.visibleItemCount = visibleThreshold;
    }
    
    
    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);
        
        visibleItemCount = recyclerView.getChildCount();
        totalItemCount = mLayoutManager.getItemCount();
        
        if (isUseLinearLayoutManager && mLayoutManager instanceof LinearLayoutManager) {
            firstVisibleItem = ((LinearLayoutManager) mLayoutManager)
                      .findFirstVisibleItemPosition();
        }
        
        if (isUseGridLayoutManager && mLayoutManager instanceof GridLayoutManager) {
            firstVisibleItem = ((GridLayoutManager) mLayoutManager).findFirstVisibleItemPosition();
        }
        
        if (loading) {
            if (totalItemCount > previousTotal) {
                loading = false;
                previousTotal = totalItemCount;
            }
        }
        if (!loading && (totalItemCount - visibleItemCount)
                        <= (firstVisibleItem + visibleThreshold)) {
            // End has been reached
            
            // Do something
            currentPage++;
            
            onLoadMore(currentPage);
            
            loading = true;
        }
    }
    
    public abstract void onLoadMore(int currentPage);
}