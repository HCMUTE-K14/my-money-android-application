package com.vn.hcmute.team.cortana.mymoney.ui.transaction;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.BindView;
import com.vn.hcmute.team.cortana.mymoney.MyMoneyApplication;
import com.vn.hcmute.team.cortana.mymoney.R;
import com.vn.hcmute.team.cortana.mymoney.di.component.ApplicationComponent;
import com.vn.hcmute.team.cortana.mymoney.model.Transaction;
import com.vn.hcmute.team.cortana.mymoney.ui.base.BaseActivity;
import com.vn.hcmute.team.cortana.mymoney.ui.transaction.TransactionContract.DeleteView;
import com.vn.hcmute.team.cortana.mymoney.utils.DrawableUtil;
import com.vn.hcmute.team.cortana.mymoney.utils.GlideImageLoader;
import java.util.List;
import javax.inject.Inject;

/**
 * Created by infamouSs on 10/20/17.
 */

public class InfoTransactionActivity extends BaseActivity implements DeleteView {
    
    public static final String TAG = InfoTransactionActivity.class.getSimpleName();
    
    @BindView(R.id.image_icon_category)
    ImageView mImageViewIconCategory;
    
    @BindView(R.id.txt_name_category)
    TextView mTextViewNameCategory;
    
    @BindView(R.id.txt_amount)
    TextView mTextViewAmount;
    
    @BindView(R.id.txt_note)
    TextView mTextViewNote;
    
    @BindView(R.id.txt_date)
    TextView mTextViewDate;
    
    @BindView(R.id.txt_wallet)
    TextView mTextViewNameWallet;
    
    @BindView(R.id.txt_type)
    TextView mTextViewType;
    
    @BindView(R.id.txt_amount_start)
    TextView mTextViewAmountStart;
    
    @BindView(R.id.txt_amount_end)
    TextView mTextViewAmountEnd;
    
    @Inject
    TransactionPresenter mTransactionPresenter;
    
    private ProgressDialog mProgressDialog;
    private Transaction mTransaction;
    
    @Override
    public int getLayoutId() {
        return R.layout.activity_info_transaction;
    }
    
    @Override
    protected void initializeDagger() {
        ApplicationComponent applicationComponent = ((MyMoneyApplication) this.getApplication())
                  .getAppComponent();
    }
    
    @Override
    protected void initializePresenter() {
        this.mPresenter = mTransactionPresenter;
        mTransactionPresenter.setView(this);
    }
    
    @Override
    protected void initializeActionBar(View rootView) {
        
    }
    
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mTransaction = getIntent().getParcelableExtra("transaction");
        
    }
    
    
    @Override
    public void showAllListTransaction(List<Transaction> list) {
        
    }
    
    @Override
    public void onFailure(String message) {
        
    }
    
    @Override
    public void loading(boolean isLoading) {
        
    }
    
    @Override
    public void onDeleteSuccessTransaction(String message) {
        
    }
    
    private void initializeView() {
        if (mTransaction == null) {
            finish();
        }
        
        GlideImageLoader
                  .load(this, DrawableUtil.getDrawable(this, mTransaction.getCategory().getIcon()),
                            mImageViewIconCategory);
        mTextViewNameCategory.setText(mTransaction.getCategory().getName());
        
        //  String formatAmount = NumberUtil.formatAmount(mTransaction.getAmount());
    }
}
