package com.vn.hcmute.team.cortana.mymoney.ui.statistics;

import android.view.View;
import com.vn.hcmute.team.cortana.mymoney.R;
import com.vn.hcmute.team.cortana.mymoney.model.Transaction;
import com.vn.hcmute.team.cortana.mymoney.ui.base.BaseFragment;
import com.vn.hcmute.team.cortana.mymoney.utils.DateUtil;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * Created by kunsubin on 11/3/2017.
 */

public class ChartFragment extends BaseFragment {
    
    private int mIdCategory;
    private List<Transaction> mTransactions;
    private List<ObjectBarChart> mObjectCharts;

    
    private List<String> mStrings;
    
    public ChartFragment(int idCategory,List<Transaction> list) {
        this.mIdCategory=idCategory;
        this.mTransactions=list;
    }
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_chart;
    }
    
    @Override
    protected void initializeDagger() {
    
    }
    
    @Override
    protected void initializePresenter() {
    
    }
    
    @Override
    protected void initializeActionBar(View rootView) {
    
    }
    
    @Override
    protected void initialize() {
        mStrings=new ArrayList<>();
    }
    public void getDataByTime(){
        if(mTransactions!=null&&!mTransactions.isEmpty()){
            for (Transaction transaction:mTransactions) {
                mStrings.add(DateUtil.convertTimeMillisToMonthAnhYear(transaction.getDate_created()));
            }
        }
        if(!mStrings.isEmpty()){
            mStrings = new ArrayList<String>(new HashSet<String>(mStrings));
        }
        
    }
    
}
