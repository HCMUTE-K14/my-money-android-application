package com.vn.hcmute.team.cortana.mymoney.ui.statistics.transaction;

import android.content.Context;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.Toast;
import butterknife.BindView;
import com.vn.hcmute.team.cortana.mymoney.R;
import com.vn.hcmute.team.cortana.mymoney.model.Category;
import com.vn.hcmute.team.cortana.mymoney.model.Transaction;
import com.vn.hcmute.team.cortana.mymoney.ui.base.BaseFragment;
import com.vn.hcmute.team.cortana.mymoney.ui.base.ExpandableListEmptyAdapter;
import com.vn.hcmute.team.cortana.mymoney.ui.event.adapter.DateObjectTransaction;
import com.vn.hcmute.team.cortana.mymoney.ui.statistics.transaction.adapter.TransactionByCategoryAdapter;
import com.vn.hcmute.team.cortana.mymoney.ui.statistics.transaction.adapter.TransactionByCategoryAdapter.ClickChildView;
import com.vn.hcmute.team.cortana.mymoney.utils.DateUtil;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

/**
 * Created by kunsubin on 11/7/2017.
 */

public class FragmentTransactionByCategory extends BaseFragment {
    
    @BindView(R.id.list_transaction_by_category)
    ExpandableListView mExpandableListView;
    
    private final String INCOME = "income";
    private final String EXPENSE = "expense";
    private Context mContext;
    private List<Transaction> mTransactions;
    private TransactionByCategoryAdapter mTransactionByCategoryAdapter;
    private ExpandableListEmptyAdapter mBaseEmptyAdapter;
    private List<Category> mCategories;
    private HashMap<Category, List<DateObjectTransaction>> mListDataChild;
    
    public FragmentTransactionByCategory(Context context,List<Transaction> list) {
        this.mContext=context;
        this.mTransactions=list;
    }
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_transaction_by_category;
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
        init();
        setData();
    }
    private void init(){
        mCategories=new ArrayList<>();
        mListDataChild=new HashMap<>();
    }
    TransactionByCategoryAdapter.ClickChildView mClickChildView=new ClickChildView() {
        @Override
        public void onClickChild(DateObjectTransaction dateObjectTransaction) {
            Toast.makeText(mContext,dateObjectTransaction.getDate(), Toast.LENGTH_SHORT).show();
        }
    };
    private void setData(){
        if(mTransactions!=null&&!mTransactions.isEmpty()){
            setDataAdapter();
    
            mTransactionByCategoryAdapter = new TransactionByCategoryAdapter(getActivity(), mCategories,
                      mListDataChild);
            mTransactionByCategoryAdapter.setClickChildView(mClickChildView);
            //setDataHeader();
            mExpandableListView.setAdapter(mTransactionByCategoryAdapter);
            mExpandableListView.setGroupIndicator(null);
            
        }else {
            mBaseEmptyAdapter = new ExpandableListEmptyAdapter(getActivity(),
                      getActivity().getString(R.string.txt_no_transaction));
            mExpandableListView.setAdapter(mBaseEmptyAdapter);
        }
    }
    
    private void setDataAdapter() {
        for (Transaction transaction:mTransactions){
            mCategories.add(transaction.getCategory());
        }
        mCategories = new ArrayList<Category>(new HashSet<Category>(mCategories));
        if(mCategories!=null&&!mCategories.isEmpty()){
            
            for (Category category:mCategories){
                List<DateObjectTransaction> list=new ArrayList<>();
                for (Transaction transaction:mTransactions){
                    if(transaction.getCategory().equals(category)){
                        list.add(convertDateObjectTransaction(transaction));
                    }
                }
                list=distinctObject(list);
                mListDataChild.put(category,list);
            }
        }
    }
    
    private List<DateObjectTransaction> distinctObject(List<DateObjectTransaction> list) {
        List<String> stringDates=new ArrayList<>();
        List<DateObjectTransaction> listObject=new ArrayList<>();
        for(DateObjectTransaction dateObjectTransaction:list){
            stringDates.add(dateObjectTransaction.getDate());
        }
        stringDates = new ArrayList<String>(new HashSet<String>(stringDates));
        for(String date:stringDates) {
            for(DateObjectTransaction dateObjectTransaction:list) {
                if(date.equals(dateObjectTransaction.getDate())) {
                    listObject.add(dateObjectTransaction);
                    break;
                }
            }
        }
        for(DateObjectTransaction dateObject1:listObject) {
            double money=0;
            for(DateObjectTransaction dateObject2:list) {
                if(dateObject1.getDate().equals(dateObject2.getDate())) {
                    money+=Double.parseDouble(dateObject2.getMoney());
                }
            }
            dateObject1.setMoney(money+"");
        }
        return listObject;
    }
    
    private DateObjectTransaction convertDateObjectTransaction(Transaction transaction) {
        String millisecond=transaction.getDate_created();
        
        String dayOfWeek = DateUtil
                  .getDayOfWeek(getActivity(), DateUtil.getDayOfWeek(millisecond.trim()));
        String dayOfMonth = DateUtil.getDayOfMonth(millisecond.trim()) + "";
        String monthOfYear = DateUtil
                  .getMonthOfYear(getActivity(), DateUtil.getMonthOfYear(millisecond.trim()));
        String year = DateUtil.getYear(millisecond.trim()) + "";
        String date=DateUtil.convertTimeMillisToDate(millisecond);
        
        DateObjectTransaction dateObject = new DateObjectTransaction();
        dateObject.setDate(date);
        dateObject.setDayOfWeek(dayOfWeek);
        dateObject.setDayOfMonth(dayOfMonth);
        dateObject.setMonthOfYear(monthOfYear);
        dateObject.setYear(year);
        dateObject.setCurrencies(transaction.getWallet().getCurrencyUnit().getCurSymbol());
        dateObject.setMoney(transaction.getAmount());
        
        return dateObject;
    }
    
}
