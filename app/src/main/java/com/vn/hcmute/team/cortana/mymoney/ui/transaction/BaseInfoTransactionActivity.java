package com.vn.hcmute.team.cortana.mymoney.ui.transaction;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.OnClick;
import com.vn.hcmute.team.cortana.mymoney.MyMoneyApplication;
import com.vn.hcmute.team.cortana.mymoney.R;
import com.vn.hcmute.team.cortana.mymoney.di.component.ApplicationComponent;
import com.vn.hcmute.team.cortana.mymoney.di.component.DaggerTransactionComponent;
import com.vn.hcmute.team.cortana.mymoney.di.component.TransactionComponent;
import com.vn.hcmute.team.cortana.mymoney.di.module.ActivityModule;
import com.vn.hcmute.team.cortana.mymoney.di.module.TransactionModule;
import com.vn.hcmute.team.cortana.mymoney.event.ActivityResultEvent;
import com.vn.hcmute.team.cortana.mymoney.model.Transaction;
import com.vn.hcmute.team.cortana.mymoney.ui.base.BaseActivity;
import com.vn.hcmute.team.cortana.mymoney.ui.transaction.TransactionContract.DeleteView;
import com.vn.hcmute.team.cortana.mymoney.usecase.base.Action;
import com.vn.hcmute.team.cortana.mymoney.utils.Constraints;
import com.vn.hcmute.team.cortana.mymoney.utils.Constraints.ResultCode;
import com.vn.hcmute.team.cortana.mymoney.utils.DateUtil;
import com.vn.hcmute.team.cortana.mymoney.utils.DrawableUtil;
import com.vn.hcmute.team.cortana.mymoney.utils.GlideImageLoader;
import com.vn.hcmute.team.cortana.mymoney.utils.NumberUtil;
import java.util.List;
import javax.inject.Inject;
import org.greenrobot.eventbus.EventBus;

/**
 * Created by infamouSs on 10/25/17.
 */

public abstract class BaseInfoTransactionActivity extends BaseActivity implements DeleteView {
    
    public static final String TAG = BaseInfoTransactionActivity.class.getSimpleName();
    protected ProgressDialog mProgressDialog;
    protected Transaction mTransaction;
    protected TransactionComponent mTransactionComponent;
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
    @BindView(R.id.txt_remind)
    TextView mTextViewRemind;
    @BindView(R.id.layout_container)
    LinearLayout mViewContainer;
    @Inject
    TransactionPresenter mTransactionPresenter;
    
    @Override
    public void showAllListTransaction(List<Transaction> list) {
        
    }
    
    @Override
    public void onFailure(String message) {
        Toast.makeText(this, "failure", Toast.LENGTH_SHORT).show();
    }
    
    @Override
    public void loading(boolean isLoading) {
        if (isLoading) {
            mProgressDialog.show();
            return;
        }
        mProgressDialog.dismiss();
    }
    
    @Override
    public int getLayoutId() {
        return R.layout.activity_info_transaction;
    }
    
    @Override
    protected void initializeDagger() {
        ApplicationComponent applicationComponent = ((MyMoneyApplication) this.getApplication())
                  .getAppComponent();
        
        mTransactionComponent = DaggerTransactionComponent.builder()
                  .applicationComponent(applicationComponent)
                  .activityModule(new ActivityModule(this))
                  .transactionModule(new TransactionModule())
                  .build();
        
        mTransactionComponent.inject(this);
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
        
        initializeView();
    }
    
    @Override
    protected void onDestroy() {
        mTransactionPresenter.unSubscribe();
        super.onDestroy();
    }
    
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        
        if (requestCode == Constraints.RequestCode.UPDATE_TRANSACTION_REQUEST_CODE &&
            data != null) {
            mTransaction = data.getParcelableExtra("transaction");
            
            if (mTransaction != null) {
                showDataTransaction();
            }
        }
    }
    
    @OnClick(R.id.image_view_cancel)
    public void exit() {
        finish();
    }
    
    
    @OnClick(R.id.image_view_delete)
    public void deleteTrans() {
        showDialogConfirmDelete();
    }
    
    @OnClick(R.id.image_view_edit)
    public void editTrans() {
        Intent intent = new Intent(this, ManagerTransactionActivity.class);
        intent.putExtra("action", Action.ACTION_UPDATE_TRANSACTION);
        intent.putExtra("transaction", mTransaction);
        startActivityForResult(intent, Constraints.RequestCode.UPDATE_TRANSACTION_REQUEST_CODE);
    }
    
    protected void initializeView() {
        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setMessage(getString(R.string.txt_pls_wait));
        mProgressDialog.setCanceledOnTouchOutside(false);
        
        if (mTransaction == null) {
            finish();
        }
        showDataTransaction();
    }
    
    protected void showDataTransaction() {
        GlideImageLoader
                  .load(this, DrawableUtil.getDrawable(this, mTransaction.getCategory().getIcon()),
                            mImageViewIconCategory);
        mTextViewNameCategory.setText(mTransaction.getCategory().getName());
        
        String formatAmount = NumberUtil.formatAmount(mTransaction.getAmount(),
                  mTransaction.getWallet().getCurrencyUnit().getCurSymbol());
        mTextViewAmount.setText(formatAmount);
        
        mTextViewNote.setText(TextUtils.isEmpty(mTransaction.getNote()) ? getString(
                  R.string.txt_no_note) : mTransaction.getNote());
        
        String dateFormat = DateUtil
                  .formatDate(DateUtil.timeMilisecondsToDate(mTransaction.getDate_created()));
        mTextViewDate.setText(dateFormat);
        String dateRemind = DateUtil
                  .formatDate(DateUtil.timeMilisecondsToDate(mTransaction.getDate_created()));
        mTextViewDate.setText(dateFormat);
        mTextViewRemind.setText(dateRemind);
        
        mTextViewNameWallet.setText(mTransaction.getWallet().getWalletName());
    }
    
    @Override
    public void onDeleteSuccessTransaction(String message) {
        EventBus.getDefault()
                  .post(new ActivityResultEvent(ResultCode.REMOVE_TRANSACTION_RESULT_CODE,
                            message));
        EventBus.getDefault()
                  .post(new ActivityResultEvent(ResultCode.NEED_UPDATE_CURRENT_WALLET_RESULT_CODE,
                            null));
        finish();
    }
    
    private void showDialogConfirmDelete() {
        AlertDialog.Builder dialog = new Builder(this);
        dialog.setTitle(R.string.txt_wait_a_second);
        dialog.setMessage(R.string.message_warning_delete_transaction);
        dialog.setNegativeButton(R.string.txt_yes, new OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                deleteTransaction();
            }
        });
        dialog.setPositiveButton(R.string.txt_no, new OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                dialog.dismiss();
            }
        });
        dialog.create().show();
    }
    
    private void deleteTransaction() {
        mTransactionPresenter.deleteTransaction(mTransaction);
    }
}
