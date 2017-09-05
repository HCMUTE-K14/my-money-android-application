package com.vn.hcmute.team.cortana.mymoney.ui.view.selectwallet;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import com.vn.hcmute.team.cortana.mymoney.R;
import com.vn.hcmute.team.cortana.mymoney.model.Wallet;
import com.vn.hcmute.team.cortana.mymoney.ui.base.EmptyAdapter;
import java.util.List;

/**
 * Created by infamouSs on 8/30/17.
 */

public class SelectWalletView extends RelativeLayout {
    
    private RecyclerView mRecyclerView;
    private ProgressBar mProgressBar;
    private SelectWalletAdapter mSelectWalletAdapter;
    private EmptyAdapter mEmptyAdapter;
    
    public SelectWalletView(Context context) {
        super(context);
        initializeView(context);
    }
    
    public SelectWalletView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initializeView(context);
    }
    
    public SelectWalletView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initializeView(context);
    }
    
    private void initializeView(Context context) {
        inflate(context, R.layout.layout_content_select_wallet, this);
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        mProgressBar = (ProgressBar) findViewById(R.id.progress_bar);
        
        mSelectWalletAdapter = new SelectWalletAdapter(context, null);
        mEmptyAdapter = new EmptyAdapter(context, "");
        
        mRecyclerView.setLayoutManager(new GridLayoutManager(context, 1));
    }
    
    public void setData(List<Wallet> wallets) {
        if (wallets != null) {
            
            if (wallets.isEmpty()) {
                mRecyclerView.setAdapter(mEmptyAdapter);
                return;
            }
            
            mSelectWalletAdapter.setData(wallets);
            mRecyclerView.setAdapter(mSelectWalletAdapter);
        }
        
    }
    
    public void setEmptyAdapter(String message) {
        mEmptyAdapter.setMessage(message);
        mRecyclerView.setAdapter(mEmptyAdapter);
    }
    
    public void setSelectWalletListener(SelectWalletListener listener) {
        mSelectWalletAdapter.setSelectWalletListener(listener);
    }
    
    public void removeWallet(int position, Wallet wallet) {
        mSelectWalletAdapter.removeWallet(position, wallet);
        if (mSelectWalletAdapter.isEmptyData()) {
            setEmptyAdapter("No Data");
        }
    }
    
    public void loading(boolean isLoading) {
        mProgressBar.setVisibility(isLoading ? View.VISIBLE : View.GONE);
    }
    
    public void updateArchiveWallet(int position, Wallet wallet) {
        mSelectWalletAdapter.updateArchiveWallet(position,wallet);
    }
}
