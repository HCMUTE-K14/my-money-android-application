package com.vn.hcmute.team.cortana.mymoney.ui.wallet;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.OnClick;
import com.vn.hcmute.team.cortana.mymoney.MyMoneyApplication;
import com.vn.hcmute.team.cortana.mymoney.R;
import com.vn.hcmute.team.cortana.mymoney.di.component.ApplicationComponent;
import com.vn.hcmute.team.cortana.mymoney.di.component.DaggerWalletComponent;
import com.vn.hcmute.team.cortana.mymoney.di.component.WalletComponent;
import com.vn.hcmute.team.cortana.mymoney.di.module.ActivityModule;
import com.vn.hcmute.team.cortana.mymoney.di.module.WalletModule;
import com.vn.hcmute.team.cortana.mymoney.event.ActivityResultEvent;
import com.vn.hcmute.team.cortana.mymoney.model.Category;
import com.vn.hcmute.team.cortana.mymoney.model.Wallet;
import com.vn.hcmute.team.cortana.mymoney.ui.base.BaseActivity;
import com.vn.hcmute.team.cortana.mymoney.ui.tools.calculator.CalculatorActivity;
import com.vn.hcmute.team.cortana.mymoney.ui.view.CardViewActionBar;
import com.vn.hcmute.team.cortana.mymoney.utils.Constraints.RequestCode;
import com.vn.hcmute.team.cortana.mymoney.utils.Constraints.ResultCode;
import com.vn.hcmute.team.cortana.mymoney.utils.DateUtil;
import com.vn.hcmute.team.cortana.mymoney.utils.DrawableUtil;
import com.vn.hcmute.team.cortana.mymoney.utils.GlideImageLoader;
import com.vn.hcmute.team.cortana.mymoney.utils.NumberUtil;
import com.vn.hcmute.team.cortana.mymoney.utils.TextUtil;
import java.util.Calendar;
import java.util.List;
import javax.inject.Inject;
import org.greenrobot.eventbus.EventBus;

/**
 * Created by infamouSs on 11/6/17.
 */

public class TransferMoneyActivity extends BaseActivity implements WalletContract.View {
    
    public final Category WITH_DRAW_CATEGORY = new Category("48", "Withdrawal",
              "icon_withdrawal", "expense", "expense");
    public final Category GIFT_CATEGORY = new Category("53", "Gift", "ic_category_give",
              "income", "income");
    @BindView(R.id.card_view_action_bar)
    CardViewActionBar mCardViewActionBar;
    @BindView(R.id.txt_money)
    TextView mTextViewMoney;
    @BindView(R.id.txt_name_wallet_from)
    EditText mTextViewNameWalletFrom;
    @BindView(R.id.txt_name_wallet_to)
    EditText mTextViewNameWalletTo;
    @BindView(R.id.txt_category)
    EditText mTextViewNameCategory;
    @BindView(R.id.image_icon_category)
    ImageView mImageViewIconCategory;
    @BindView(R.id.txt_note)
    EditText mTextViewNote;
    @BindView(R.id.txt_date_start)
    EditText mTextViewDateStart;
    @Inject
    WalletPresenter mWalletPresenter;
    private Wallet mWalletFrom;
    private Wallet mWalletTo;
    private String mAmount = "0";
    
    private Category mCategory;
    
    
    private int mYear_StartTime, mMonth_StartTime, mDayOfMonth_StartTime;
    private DatePickerDialog mDatePickerStartTime;
    
    private ProgressDialog mProgressDialog;
    
    private OnDateSetListener mOnDateSetListenerStartTime = new OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            mYear_StartTime = year;
            mMonth_StartTime = month;
            mDayOfMonth_StartTime = dayOfMonth;
            
            mDatePickerStartTime
                      .updateDate(mYear_StartTime, mMonth_StartTime, mDayOfMonth_StartTime);
            
            mTextViewDateStart.setText(DateUtil
                      .formatDate(mYear_StartTime, mMonth_StartTime, mDayOfMonth_StartTime));
        }
    };
    
    @Override
    public int getLayoutId() {
        return R.layout.activity_transfer_money;
    }
    
    @Override
    protected void initializeDagger() {
        ApplicationComponent applicationComponent = ((MyMoneyApplication) this.getApplication())
                  .getAppComponent();
        WalletComponent walletComponent = DaggerWalletComponent
                  .builder()
                  .applicationComponent(applicationComponent)
                  .activityModule(new ActivityModule(this))
                  .walletModule(new WalletModule())
                  .build();
        
        walletComponent.inject(this);
    }
    
    @Override
    protected void initializePresenter() {
        mPresenter = mWalletPresenter;
        mWalletPresenter.setView(this);
    }
    
    @Override
    protected void initializeActionBar(View rootView) {
        mCardViewActionBar.setOnClickAction(new OnClickListener() {
            @Override
            public void onClick(View v) {
                transferMoney(mWalletFrom, mWalletTo, mAmount);
            }
        });
    }
    
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        mWalletFrom = getIntent().getParcelableExtra("wallet_from");
        initializeView();
    }
    
    
    @OnClick({R.id.btn_input_value, R.id.btn_choose_wallet_from, R.id.txt_name_wallet_from,
              R.id.btn_choose_wallet_to, R.id.txt_name_wallet_to,
              R.id.btn_choose_category, R.id.txt_category,
              R.id.btn_choose_date, R.id.txt_date_start})
    public void onCLick(View view) {
        switch (view.getId()) {
            case R.id.btn_input_value:
                openCalculatorActivity();
                break;
            case R.id.btn_choose_wallet_from:
            case R.id.txt_name_wallet_from:
                openMyWalletActivity(RequestCode.WALLET_FROM_REQUEST_CODE);
                break;
            case R.id.btn_choose_wallet_to:
            case R.id.txt_name_wallet_to:
                openMyWalletActivity(RequestCode.WALLET_TO_REQUEST_CODE);
                break;
            case R.id.btn_choose_category:
            case R.id.txt_category:
                break;
            case R.id.btn_choose_date:
            case R.id.txt_date_start:
                openDatePickerDialogStartTime();
                break;
            default:
                break;
        }
    }
    
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case RequestCode.CALCULATOR_REQUEST_CODE:
                    mAmount = data.getStringExtra("result");
                    if (mAmount.equals("0")) {
                        Toast.makeText(this, R.string.message_warning_fill_amount,
                                  Toast.LENGTH_SHORT).show();
                        return;
                    }
                    String result2Show = data.getStringExtra("result_view");
                    if (!TextUtil.isEmpty(result2Show)) {
                        mTextViewMoney.setText(result2Show);
                    }
                    break;
                case RequestCode.WALLET_FROM_REQUEST_CODE:
                    mWalletFrom = data.getParcelableExtra("wallet");
                    if (mWalletFrom != null) {
                        mTextViewNameWalletFrom.setText(mWalletFrom.getWalletName());
                    }
                    resetWithAmount(TextUtil.isEmpty(mAmount) ? "0" : mAmount);
                    resetWallet();
                    break;
                case RequestCode.WALLET_TO_REQUEST_CODE:
                    mWalletTo = data.getParcelableExtra("wallet");
                    if (mWalletTo != null) {
                        mTextViewNameWalletTo.setText(mWalletTo.getWalletName());
                    }
                    break;
                default:
                    break;
            }
        }
    }
    
    private void openCalculatorActivity() {
        Intent intent = new Intent(this, CalculatorActivity.class);
        intent.putExtra("goal_money", mAmount);
        intent.putExtra("currencies", mWalletFrom != null ? mWalletFrom.getCurrencyUnit() : null);
        startActivityForResult(intent, RequestCode.CALCULATOR_REQUEST_CODE);
    }
    
    
    private void openMyWalletActivity(int requestCode) {
        Intent intent = new Intent(this, MyWalletActivity.class);
        if (requestCode == RequestCode.WALLET_TO_REQUEST_CODE) {
            intent.putExtra("except_wallet", mWalletFrom.getWalletid());
        }
        startActivityForResult(intent, requestCode);
    }
    
    private void openDatePickerDialogStartTime() {
        mDatePickerStartTime.show();
    }
    
    @Override
    public void initializeView() {
        if (mWalletFrom != null) {
            resetWithAmount("0");
        }
        
        mTextViewDateStart.setText(R.string.txt_today);
        
        Calendar calendar = Calendar.getInstance();
        
        mYear_StartTime = calendar.get(Calendar.YEAR);
        mMonth_StartTime = calendar.get(Calendar.MONTH);
        mDayOfMonth_StartTime = calendar.get(Calendar.DAY_OF_MONTH);
        
        mDatePickerStartTime = new DatePickerDialog(this, mOnDateSetListenerStartTime,
                  mYear_StartTime, mMonth_StartTime, mDayOfMonth_StartTime);
        
        mCategory = WITH_DRAW_CATEGORY;
        
        GlideImageLoader.load(this, DrawableUtil.getDrawable(this, mCategory.getIcon()),
                  mImageViewIconCategory);
        mTextViewNameCategory.setText(mCategory.getName());
        
        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setCanceledOnTouchOutside(false);
        mProgressDialog.setMessage(getString(R.string.txt_pls_wait));
    }
    
    @Override
    public void onMoveMoneySuccess(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        EventBus.getDefault().post(new ActivityResultEvent(
                  ResultCode.NEED_UPDATE_CURRENT_WALLET_RESULT_CODE, null));
        finish();
    }
    
    @Override
    public void onFailure(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
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
    public void showEmpty() {
    
    }
    
    @Override
    public void showListWallet(List<Wallet> wallets) {
    
    }
    
    @Override
    public void onAddWalletSuccess(String message, Wallet wallet) {
    
    }
    
    @Override
    public void onUpdateWalletSuccess(String message, int position, Wallet wallet) {
    
    }
    
    @Override
    public void onRemoveWalletSuccess(String message, int position, Wallet wallet) {
    
    }
    
    private void resetWithAmount(String amount) {
        String defaultMoney = amount + " " + mWalletFrom.getCurrencyUnit().getCurSymbol();
        mTextViewMoney.setText(defaultMoney);
        mTextViewNameWalletFrom.setText(mWalletFrom.getWalletName());
    }
    
    private void transferMoney(Wallet walletFrom, Wallet walletTo, String amount) {
        if (amount.equals("0")) {
            Toast.makeText(this, R.string.message_warning_fill_amount, Toast.LENGTH_SHORT).show();
            return;
        }
        if (walletFrom == null || walletTo == null) {
            Toast.makeText(this, R.string.message_warning_choose_wallet, Toast.LENGTH_SHORT).show();
            return;
        }
        String curFrom = walletFrom.getCurrencyUnit().getCurCode();
        String curTo = walletTo.getCurrencyUnit().getCurCode();
        String amountPlus = NumberUtil.exchangeMoney(this, amount, curFrom, curTo) + "";
        String date_created = String.valueOf(DateUtil
                  .getLongAsDate(mDayOfMonth_StartTime, mMonth_StartTime, mYear_StartTime));
        
        double amountWallet = Double.valueOf(walletFrom.getMoney());
        double amountTrans = Double.valueOf(amount);
        if (amountWallet < amountTrans) {
            showDialogWarningAmount(walletFrom.getWalletid(), mWalletTo.getWalletid(), amount,
                      amountPlus,
                      date_created);
            return;
        }
        
        moveMoney(walletFrom.getWalletid(), mWalletTo.getWalletid(), amount, amountPlus,
                  date_created);
    }
    
    private void moveMoney(String walletFrom, String walletTo, String moneyMinus, String moneyPlus,
              String dateCreated) {
        mWalletPresenter
                  .moveWallet(walletFrom, walletTo, moneyMinus, moneyPlus,
                            dateCreated);
    }
    
    
    private void showDialogWarningAmount(final String walletFrom, final String walletTo,
              final String moneyMinus,
              final String moneyPlus,
              final String dateCreated) {
        AlertDialog.Builder dialog = new Builder(this);
        
        dialog.setTitle(R.string.txt_warning);
        dialog.setMessage(getString(R.string.message_warning_amount_too_big));
        
        dialog.setNegativeButton(R.string.txt_yes, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                moveMoney(walletFrom, walletTo, moneyMinus, moneyPlus,
                          dateCreated);
            }
        });
        dialog.setPositiveButton(R.string.txt_im_wrong, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                dialog.dismiss();
            }
        });
        
        dialog.create().show();
    }
    
    private void resetWallet() {
        String wallet_id_from = mWalletFrom.getWalletid();
        String wallet_id_to = mWalletTo.getWalletid();
        
        if (!TextUtil.isEmpty(wallet_id_from) &&
            !TextUtil.isEmpty(wallet_id_to) &&
            wallet_id_from.equals(wallet_id_to)) {
            mWalletTo = null;
            mTextViewNameWalletTo.setText("");
        }
    }
    
    
}
