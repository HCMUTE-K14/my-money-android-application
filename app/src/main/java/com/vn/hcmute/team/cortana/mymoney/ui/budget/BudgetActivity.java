package com.vn.hcmute.team.cortana.mymoney.ui.budget;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.vn.hcmute.team.cortana.mymoney.MyMoneyApplication;
import com.vn.hcmute.team.cortana.mymoney.R;
import com.vn.hcmute.team.cortana.mymoney.di.component.ApplicationComponent;
import com.vn.hcmute.team.cortana.mymoney.di.component.BudgetComponent;
import com.vn.hcmute.team.cortana.mymoney.di.component.DaggerBudgetComponent;
import com.vn.hcmute.team.cortana.mymoney.di.module.ActivityModule;
import com.vn.hcmute.team.cortana.mymoney.di.module.BudgetModule;
import com.vn.hcmute.team.cortana.mymoney.model.Budget;
import com.vn.hcmute.team.cortana.mymoney.ui.base.BaseActivity;
import com.vn.hcmute.team.cortana.mymoney.ui.budget.adapter.MyRecyclerViewBudgetAdapter;
import com.vn.hcmute.team.cortana.mymoney.utils.logger.MyLogger;
import java.util.List;
import javax.inject.Inject;

/**
 * Created by kunsubin on 8/23/2017.
 */

public class BudgetActivity extends BaseActivity implements BudgetContract.View,
                                                            MyRecyclerViewBudgetAdapter.ItemClickListener {
    
    @Inject
    BudgetPresenter mBudgetPresenter;
    
    @BindView(R.id.recyclerViewBudget)
    RecyclerView mRecyclerViewBudget;
    @BindView(R.id.buttonClick)
    Button mButton;
    
    MyRecyclerViewBudgetAdapter mMyRecyclerViewBudgetAdapter;
    List<Budget> mBudgetList;

    //boolean f=true;
    @OnClick(R.id.buttonClick)
    public void onClick(View view) {
        MyLogger.d("mmmmmmmm", "ksdgjdfkgjdf");

        mBudgetPresenter.getBudget();

    }
    
    
    @Override
    public int getLayoutId() {
        return R.layout.activity_budget;
    }
    
    @Override
    protected void initializeDagger() {
        ApplicationComponent applicationComponent = ((MyMoneyApplication) this.getApplication())
                  .getAppComponent();
        BudgetComponent budgetComponent = DaggerBudgetComponent
                  .builder()
                  .applicationComponent(applicationComponent)
                  .activityModule(new ActivityModule(this))
                  .budgetModule(new BudgetModule())
                  .build();
        budgetComponent.inject(this);
    }
    
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBudgetPresenter.getBudget();
        
    }
    
    @Override
    protected void initializePresenter() {
        this.mPresenter = mBudgetPresenter;
        mBudgetPresenter.setView(this);
    }
    
    @Override
    protected void initializeActionBar(View rootView) {
        
    }
    
    @Override
    public void onSuccessGetListBudget(List<Budget> list) {
        //Toast.makeText(this, list.get(0).getBudgetId(), Toast.LENGTH_LONG).show();
        mBudgetList = list;
        MyLogger.d("Deso", list.size() + "");
        MyLogger.d("sfd", "sdgdfwiruqo");
        // if (list != null) {
        mMyRecyclerViewBudgetAdapter = new MyRecyclerViewBudgetAdapter(this, list);
        mRecyclerViewBudget.setLayoutManager(new GridLayoutManager(this, 1));
        mMyRecyclerViewBudgetAdapter.setClickListener(this);
        mRecyclerViewBudget.setAdapter(mMyRecyclerViewBudgetAdapter);
        //}
        // MyLogger.d("Deso",list.size()+"");
    }
    
    @Override
    public void onSuccessCreateBudget(String message) {
        mBudgetPresenter.getBudget();
        
    }
    
    @Override
    public void onSuccessUpdateBudget(String message) {
        mBudgetPresenter.getBudget();
    }
    
    @Override
    public void onSucsessDeleteBudget(String message) {
        mBudgetPresenter.getBudget();
        
        
    }
    
    @Override
    public void onFailure(String message) {
        MyLogger.d("fail", "sdgdfwiruqo");
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }
    
    @Override
    public void loading(boolean isLoading) {
        
    }
    
    @Override
    public void onItemClick(View view, int position) {
        TextView textView = ButterKnife.findById(view, R.id.budgetId);
        mBudgetPresenter.deleteBudget(textView.getText().toString().trim());
        //Toast.makeText(this, textView.getText(), Toast.LENGTH_LONG).show();
    }
}
