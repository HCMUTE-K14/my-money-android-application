package com.vn.hcmute.team.cortana.mymoney.ui.budget;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import butterknife.BindView;
import com.vn.hcmute.team.cortana.mymoney.MyMoneyApplication;
import com.vn.hcmute.team.cortana.mymoney.R;
import com.vn.hcmute.team.cortana.mymoney.di.component.ApplicationComponent;
import com.vn.hcmute.team.cortana.mymoney.di.component.BudgetComponent;
import com.vn.hcmute.team.cortana.mymoney.di.component.DaggerBudgetComponent;
import com.vn.hcmute.team.cortana.mymoney.di.module.ActivityModule;
import com.vn.hcmute.team.cortana.mymoney.di.module.BudgetModule;
import com.vn.hcmute.team.cortana.mymoney.model.Budget;
import com.vn.hcmute.team.cortana.mymoney.model.Category;
import com.vn.hcmute.team.cortana.mymoney.model.Wallet;
import com.vn.hcmute.team.cortana.mymoney.ui.base.BaseFragment;
import com.vn.hcmute.team.cortana.mymoney.ui.base.listener.BaseCallBack;
import com.vn.hcmute.team.cortana.mymoney.usecase.base.Action;
import com.vn.hcmute.team.cortana.mymoney.usecase.remote.CategoryUseCase;
import com.vn.hcmute.team.cortana.mymoney.usecase.remote.WalletUseCase;
import com.vn.hcmute.team.cortana.mymoney.usecase.remote.WalletUseCase.WalletRequest;
import com.vn.hcmute.team.cortana.mymoney.utils.logger.MyLogger;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;

/**
 * Created by kunsubin on 9/24/2017.
 */

public class FragmentBudgetRunning extends BaseFragment implements BudgetContract.View{
    @BindView(R.id.recycler_view_budget_running)
    RecyclerView recycler_view_budget_running;
    
    private List<Budget> mBudgetList;
    private List<Wallet> mWalletList;
    private List<Category> mCategoryList;
    
    
    
    @Inject
    BudgetPresenter mBudgetPresenter;
    @Inject
    WalletUseCase mWalletUseCase;
    @Inject
    CategoryUseCase mCategoryUseCase;
    
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_budget_running;
    }
    
    @Override
    protected void initializeDagger() {
        ApplicationComponent applicationComponent = ((MyMoneyApplication) getActivity().getApplication())
                  .getAppComponent();
        BudgetComponent budgetComponent = DaggerBudgetComponent
                  .builder()
                  .applicationComponent(applicationComponent)
                  .activityModule(new ActivityModule(getActivity()))
                  .budgetModule(new BudgetModule())
                  .build();
        budgetComponent.inject(this);
    }
    
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        mBudgetList=new ArrayList<>();
        getList();
        mBudgetPresenter.getBudget();
    }
    
    @Override
    protected void initializePresenter() {
        mPresenter=mBudgetPresenter;
        mBudgetPresenter.setView(this);
    }
    
    @Override
    protected void initializeActionBar(View rootView) {
        
    }
    public void getList(){
        mWalletList = new ArrayList<>();
        WalletUseCase.WalletRequest savingRequest = new WalletRequest(Action.ACTION_GET_WALLET,
                  new BaseCallBack<Object>() {
                      @Override
                      public void onSuccess(Object value) {
                          List<Wallet> wallets = (List<Wallet>) value;
                          mWalletList.addAll(wallets);
                      }
                  
                      @Override
                      public void onFailure(Throwable throwable) {
                          MyLogger.d("erro get wallet");
                      }
                  
                      @Override
                      public void onLoading() {
                      
                      }
                  }, null, null);
        mWalletUseCase.subscribe(savingRequest);
        
        
    }
    
    
    @Override
    public void onSuccessGetListBudget(List<Budget> list) {
        MyLogger.d("myBudget",list.size());
        MyLogger.d("myWallet",mBudgetList.size());
    }
    
    @Override
    public void onSuccessCreateBudget(String message) {
        
    }
    
    @Override
    public void onSuccessUpdateBudget(String message) {
        
    }
    
    @Override
    public void onSucsessDeleteBudget(String message) {
        
    }
    
    @Override
    public void onFailure(String message) {
        
    }
    
    @Override
    public void loading(boolean isLoading) {
        
    }
}
