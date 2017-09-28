package com.vn.hcmute.team.cortana.mymoney.ui.transaction;

import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.OnClick;
import com.vn.hcmute.team.cortana.mymoney.R;
import com.vn.hcmute.team.cortana.mymoney.data.cache.PreferencesHelper;
import com.vn.hcmute.team.cortana.mymoney.di.module.GlideApp;
import com.vn.hcmute.team.cortana.mymoney.model.Category;
import com.vn.hcmute.team.cortana.mymoney.model.Person;
import com.vn.hcmute.team.cortana.mymoney.model.Wallet;
import com.vn.hcmute.team.cortana.mymoney.ui.base.BaseActivity;
import com.vn.hcmute.team.cortana.mymoney.ui.category.CategoryActivity;
import com.vn.hcmute.team.cortana.mymoney.ui.person.PersonActivity;
import com.vn.hcmute.team.cortana.mymoney.ui.tools.calculator.CalculatorActivity;
import com.vn.hcmute.team.cortana.mymoney.ui.tools.galleryloader.GalleryLoader;
import com.vn.hcmute.team.cortana.mymoney.ui.view.CardViewActionBar;
import com.vn.hcmute.team.cortana.mymoney.ui.wallet.MyWalletActivity;
import com.vn.hcmute.team.cortana.mymoney.utils.Constraints.RequestCode;
import com.vn.hcmute.team.cortana.mymoney.utils.DrawableUtil;
import com.vn.hcmute.team.cortana.mymoney.utils.TextUtil;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by infamouSs on 9/27/17.
 */

public class AddTransactionActivity extends BaseActivity {
    
    @BindView(R.id.card_view_action_bar)
    CardViewActionBar mCardViewActionBar;
    
    @BindView(R.id.image_icon_category)
    ImageView mImageViewIconCategory;
    
    @BindView(R.id.txt_category)
    TextView mTextViewCategory;
    
    @BindView(R.id.txt_money)
    TextView mTextViewMoney;
    
    @BindView(R.id.txt_with)
    TextView mTextViewContacts;
    
    @BindView(R.id.txt_location)
    TextView mTextViewLocation;
    
    @BindView(R.id.txt_remind)
    TextView mTextViewRemind;
    
    @BindView(R.id.txt_wallet)
    TextView mTextViewNameWallet;
    
    private DatePickerDialog mDatePickerStartTime;
    private DatePickerDialog mDatePickerNotify;
    
    private int mYear, mMonth, mDayOfMonth;
    
    private OnDateSetListener mOnDateSetListenerStartTime = new OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            mYear = year;
            mMonth = month;
            mDayOfMonth = dayOfMonth;
            
            mDatePickerStartTime.updateDate(mYear, mMonth, mDayOfMonth);
        }
    };
    
    
    @Override
    public int getLayoutId() {
        return R.layout.activity_add_transaction;
    }
    
    @Override
    protected void initializeDagger() {
        
    }
    
    @Override
    protected void initializePresenter() {
        
    }
    
    @Override
    protected void initializeActionBar(View rootView) {
        mCardViewActionBar.setOnClickAction(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(AddTransactionActivity.this, "Done", Toast.LENGTH_SHORT).show();
            }
        });
        mCardViewActionBar.setOnClickBack(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(AddTransactionActivity.this, "Cancel", Toast.LENGTH_SHORT).show();
            }
        });
    }
    
    
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initializeView();
        
        mPreferencesHelper = PreferencesHelper.getInstance(this);
        mWallet = mPreferencesHelper.getCurrentWallet();
    }
    
    private Category mCategory;
    private Wallet mWallet;
    private List<Person> mPersonList;
    private PreferencesHelper mPreferencesHelper;
    
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case RequestCode.CALCULATOR_REQUEST_CODE:
                    String result = data.getStringExtra("result");
                    if (!TextUtil.isEmpty(result)) {
                        mTextViewMoney.setText(result);
                    }
                    break;
                case RequestCode.CHOOSE_CATEGORY_REQUEST_CODE:
                    mCategory = data.getParcelableExtra("category");
                    if (mCategory != null) {
                        mTextViewCategory.setText(mCategory.getName());
                        GlideApp.with(this)
                                  .load(DrawableUtil.getDrawable(this, mCategory.getIcon()))
                                  .placeholder(R.drawable.folder_placeholder)
                                  .error(R.drawable.folder_placeholder)
                                  .into(mImageViewIconCategory);
                    }
                    break;
                case RequestCode.CHOOSE_WALLET_REQUEST_CODE:
                    mWallet = data.getParcelableExtra("wallet");
                    if (mWallet != null) {
                        mTextViewNameWallet.setText(mWallet.getWalletName());
                    }
                    break;
                case RequestCode.CHOOSE_CONTACT_REQUEST_CODE:
                    mPersonList = data
                              .getParcelableArrayListExtra(PersonActivity.EXTRA_SELECTED_PERSON);
                    if (mPersonList != null) {
                        StringBuilder stringBuilder = new StringBuilder();
                        for (Person person : mPersonList) {
                            stringBuilder.append(person.getName().trim())
                                      .append(", ");
                        }
                        mTextViewContacts.setText(stringBuilder.toString());
                    }
                    break;
                case RequestCode.CHOOSE_IMAGE_REQUEST_CODE:
                    break;
            }
        }
        
    }
    
    public void initializeView() {
        Calendar calendar = Calendar.getInstance();
        
        mYear = calendar.get(Calendar.YEAR);
        mMonth = calendar.get(Calendar.MONTH);
        mDayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
        
        mDatePickerStartTime = new DatePickerDialog(this, mOnDateSetListenerStartTime, mYear,
                  mMonth,
                  mDayOfMonth);
    }
    
    
    @OnClick({R.id.btn_input_value, R.id.btn_choose_category, R.id.btn_choose_date,
              R.id.btn_choose_wallet, R.id.btn_choose_contact, R.id.btn_choose_location,
              R.id.btn_choose_event, R.id.btn_choose_date_notify, R.id.btn_choose_image_gallery})
    public void onClickInputMoney(View view) {
        switch (view.getId()) {
            case R.id.btn_input_value:
                openCalculatorActivity();
                break;
            case R.id.btn_choose_category:
                openCategoryActivity();
                break;
            case R.id.btn_choose_date:
                openDatePickerDialog();
                break;
            case R.id.btn_choose_wallet:
                openMyWalletActivity();
                break;
            case R.id.btn_choose_contact:
                openContactActivity();
                break;
            case R.id.btn_choose_location:
                openSelectLocationActivity();
                break;
            case R.id.btn_choose_event:
                openEventActivity();
                break;
            case R.id.btn_choose_date_notify:
                openDatePickerDialog();
                break;
            case R.id.btn_choose_image_gallery:
                openGalleryPickerActivity();
                break;
            default:
                break;
        }
    }
    
    
    private void openCalculatorActivity() {
        Intent intent = new Intent(this, CalculatorActivity.class);
        startActivityForResult(intent, RequestCode.CALCULATOR_REQUEST_CODE);
    }
    
    private void openCategoryActivity() {
        Intent intent = new Intent(this, CategoryActivity.class);
        startActivityForResult(intent, RequestCode.CHOOSE_CATEGORY_REQUEST_CODE);
    }
    
    private void openMyWalletActivity() {
        Intent intent = new Intent(this, MyWalletActivity.class);
        startActivityForResult(intent, RequestCode.CHOOSE_WALLET_REQUEST_CODE);
    }
    
    private void openContactActivity() {
        Intent intent = new Intent(this, PersonActivity.class);
        intent.putExtra("mode", PersonActivity.MODE_SELECT_MULTIPLE);
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList(PersonActivity.EXTRA_SELECTED_PERSON,
                  (ArrayList<? extends Parcelable>) mPersonList);
        intent.putExtras(bundle);
        startActivityForResult(intent, RequestCode.CHOOSE_CONTACT_REQUEST_CODE);
    }
    
    private void openEventActivity() {
        
    }
    
    private void openSelectLocationActivity() {
        
    }
    
    private void openDatePickerDialog() {
        mDatePickerStartTime.show();
    }
    
    private void openGalleryPickerActivity() {
        GalleryLoader.create(this)
                  .setMode(GalleryLoader.MODE_SINGLE)
                  .start(RequestCode.CHOOSE_IMAGE_REQUEST_CODE);
    }
}
