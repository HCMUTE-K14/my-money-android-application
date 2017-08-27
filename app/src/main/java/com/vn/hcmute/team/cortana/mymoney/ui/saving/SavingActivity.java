package com.vn.hcmute.team.cortana.mymoney.ui.saving;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;
import butterknife.BindView;
import com.vn.hcmute.team.cortana.mymoney.MyMoneyApplication;
import com.vn.hcmute.team.cortana.mymoney.R;
import com.vn.hcmute.team.cortana.mymoney.di.component.ApplicationComponent;
import com.vn.hcmute.team.cortana.mymoney.di.component.DaggerSavingComponent;
import com.vn.hcmute.team.cortana.mymoney.di.component.SavingComponent;
import com.vn.hcmute.team.cortana.mymoney.di.module.ActivityModule;
import com.vn.hcmute.team.cortana.mymoney.di.module.SavingModule;
import com.vn.hcmute.team.cortana.mymoney.model.Saving;
import com.vn.hcmute.team.cortana.mymoney.ui.base.BaseActivity;
import com.vn.hcmute.team.cortana.mymoney.ui.saving.adapter.MyRecyclerViewSavingAdapter;
import java.util.List;
import javax.inject.Inject;

/**
 * Created by kunsubin on 8/23/2017.
 */

public class SavingActivity extends BaseActivity implements SavingContract.View,MyRecyclerViewSavingAdapter.ItemClickListener {
    
    @Inject
    SavingPresenter mSavingPresenter;
    
  
    @BindView(R.id.recyclerViewSaving)
    RecyclerView mRecyclerViewSaving;
    
    MyRecyclerViewSavingAdapter mMyRecyclerViewSavingAdapter;
    
    List<Saving> mSavings;
    
    
    @Override
    public int getLayoutId() {
        return R.layout.activity_saving;
    }
    
    @Override
    protected void initializeDagger() {
        ApplicationComponent applicationComponent = ((MyMoneyApplication) this.getApplication())
                  .getAppComponent();
        SavingComponent savingComponent = DaggerSavingComponent
                  .builder()
                  .applicationComponent(applicationComponent)
                  .activityModule(new ActivityModule(this))
                  .savingModule(new SavingModule())
                  .build();
        savingComponent.inject(this);
    }
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSavingPresenter.getSaving();
    
    }
    
    @Override
    protected void initializePresenter() {
        this.mPresenter = mSavingPresenter;
        mSavingPresenter.setView(this);
    }
    
    @Override
    protected void initializeActionBar(View rootView) {
        
    }
    
    @Override
    public void showListSaving(List<Saving> savings) {
        mSavings=savings;
        mMyRecyclerViewSavingAdapter=new MyRecyclerViewSavingAdapter(this,savings);
        mRecyclerViewSaving.setLayoutManager(new GridLayoutManager(this,1));
        mMyRecyclerViewSavingAdapter.setClickListener(this);
        mRecyclerViewSaving.setAdapter(mMyRecyclerViewSavingAdapter);
    }
    
    @Override
    public void showSaving() {
        
    }
    
    @Override
    public void onSuccessCreateSaving() {
        mSavingPresenter.getSaving();
    }
    
    @Override
    public void onSuccessDeleteSaving() {
        mSavingPresenter.getSaving();
    }
    
    @Override
    public void onSuccessUpdateSaving() {
        mSavingPresenter.getSaving();
    }
    
    @Override
    public void onSuccessTakeIn() {
        mSavingPresenter.getSaving();
    }
    
    @Override
    public void onSuccessTakeOut() {
        mSavingPresenter.getSaving();
    }
    
    @Override
    public void showError(String message) {
        Toast.makeText(this,message+"",Toast.LENGTH_LONG).show();
    }
    
    @Override
    public void loading(boolean isLoading) {
        
    }
    
    @Override
    public void onItemClick(View view, List<Saving> savingList, int position) {
        
    }
}
