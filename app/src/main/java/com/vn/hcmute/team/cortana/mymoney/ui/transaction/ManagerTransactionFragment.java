package com.vn.hcmute.team.cortana.mymoney.ui.transaction;

import static android.app.Activity.RESULT_OK;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
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
import com.vn.hcmute.team.cortana.mymoney.data.cache.PreferencesHelper;
import com.vn.hcmute.team.cortana.mymoney.di.component.ApplicationComponent;
import com.vn.hcmute.team.cortana.mymoney.di.component.DaggerTransactionComponent;
import com.vn.hcmute.team.cortana.mymoney.di.component.TransactionComponent;
import com.vn.hcmute.team.cortana.mymoney.di.module.ActivityModule;
import com.vn.hcmute.team.cortana.mymoney.di.module.TransactionModule;
import com.vn.hcmute.team.cortana.mymoney.model.Category;
import com.vn.hcmute.team.cortana.mymoney.model.Event;
import com.vn.hcmute.team.cortana.mymoney.model.Image;
import com.vn.hcmute.team.cortana.mymoney.model.Person;
import com.vn.hcmute.team.cortana.mymoney.model.Saving;
import com.vn.hcmute.team.cortana.mymoney.model.Transaction;
import com.vn.hcmute.team.cortana.mymoney.model.Wallet;
import com.vn.hcmute.team.cortana.mymoney.ui.base.BaseFragment;
import com.vn.hcmute.team.cortana.mymoney.ui.category.CategoryActivity;
import com.vn.hcmute.team.cortana.mymoney.ui.category.ManagerCategoryFragment;
import com.vn.hcmute.team.cortana.mymoney.ui.event.ActivitySelectEvent;
import com.vn.hcmute.team.cortana.mymoney.ui.person.PersonActivity;
import com.vn.hcmute.team.cortana.mymoney.ui.tools.calculator.CalculatorActivity;
import com.vn.hcmute.team.cortana.mymoney.ui.tools.galleryloader.GalleryLoader;
import com.vn.hcmute.team.cortana.mymoney.ui.tools.galleryloader.model.ImageGallery;
import com.vn.hcmute.team.cortana.mymoney.ui.transaction.SelectedImageAdapter.RemoveImageSelectedListener;
import com.vn.hcmute.team.cortana.mymoney.ui.transaction.TransactionContract.AddUpdateView;
import com.vn.hcmute.team.cortana.mymoney.ui.view.CardViewActionBar;
import com.vn.hcmute.team.cortana.mymoney.ui.wallet.MyWalletActivity;
import com.vn.hcmute.team.cortana.mymoney.usecase.base.Action;
import com.vn.hcmute.team.cortana.mymoney.utils.Constraints.RequestCode;
import com.vn.hcmute.team.cortana.mymoney.utils.DateUtil;
import com.vn.hcmute.team.cortana.mymoney.utils.DrawableUtil;
import com.vn.hcmute.team.cortana.mymoney.utils.GlideImageLoader;
import com.vn.hcmute.team.cortana.mymoney.utils.TextUtil;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.inject.Inject;

/**
 * Created by infamouSs on 9/27/17.
 */

public class ManagerTransactionFragment extends BaseFragment implements AddUpdateView {
    
    public static final String TAG = ManagerCategoryFragment.class.getSimpleName();
    
    @BindView(R.id.card_view_action_bar)
    CardViewActionBar mCardViewActionBar;
    
    @BindView(R.id.txt_note)
    EditText mEditTextNote;
    
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
    
    @BindView(R.id.txt_date_start)
    TextView mTextViewDateStart;
    
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerViewImageSelected;
    
    @BindView(R.id.txt_event)
    TextView mTextViewEvent;
    
    @Inject
    PreferencesHelper mPreferencesHelper;
    
    @Inject
    TransactionPresenter mTransactionPresenter;
    
    private DatePickerDialog mDatePickerStartTime;
    private DatePickerDialog mDatePickerEndTime;
    
    private int mYear_StartTime, mMonth_StartTime, mDayOfMonth_StartTime;
    private int mYear_EndTime, mMonth_EndTime, mDayOfMonth_EndTime;
    
    private Category mCategory;
    private Wallet mWallet;
    private List<Person> mPersonList;
    private String mAction;
    private Transaction mCurrentTransaction;
    
    private Event mEvent;
    private List<Image> mImages;
    private List<ImageGallery> mImageGalleries = new ArrayList<>();
    private SelectedImageAdapter mSelectedImageAdapter;
    private Saving mSaving;
    
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
    
    private OnDateSetListener mOnDateSetListenerNotify = new OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            mYear_EndTime = year;
            mMonth_EndTime = month;
            mDayOfMonth_EndTime = dayOfMonth;
            
            mDatePickerEndTime.updateDate(mYear_EndTime, mMonth_EndTime, mDayOfMonth_EndTime);
            
            mTextViewRemind.setText(DateUtil
                      .formatDate(mYear_EndTime, mMonth_EndTime, mDayOfMonth_EndTime));
        }
    };
    
    public static ManagerTransactionFragment newInstance(String action, Transaction transaction,
              Saving saving) {
        ManagerTransactionFragment fragment = new ManagerTransactionFragment();
        Bundle bundle = new Bundle();
        switch (action) {
            case Action.ACTION_ADD_TRANSACTION:
                bundle.putString("action", action);
                bundle.putParcelable("transaction", transaction);
                bundle.putParcelable("saving", saving);
                break;
            case Action.ACTION_UPDATE_TRANSACTION:
                bundle.putString("action", action);
                bundle.putParcelable("transaction", transaction);
                bundle.putParcelable("saving", saving);
                break;
            default:
                break;
        }
        
        fragment.setArguments(bundle);
        
        return fragment;
    }
    
    @Override
    public int getLayoutId() {
        return R.layout.fragment_manager_transaction;
    }
    
    @Override
    protected void initializeDagger() {
        ApplicationComponent applicationComponent = ((MyMoneyApplication) this.getActivity()
                  .getApplication()).getAppComponent();
        TransactionComponent transactionComponent = DaggerTransactionComponent.builder()
                  .applicationComponent(applicationComponent)
                  .activityModule(new ActivityModule(this.getActivity()))
                  .transactionModule(new TransactionModule())
                  .build();
        
        transactionComponent.inject(this);
    }
    
    @Override
    protected void initializePresenter() {
        mPresenter = mTransactionPresenter;
        mTransactionPresenter.setView(this);
    }
    
    @Override
    protected void initializeActionBar(View rootView) {
        mCardViewActionBar.setOnClickAction(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mAction.equals(Action.ACTION_ADD_TRANSACTION)) {
                    addTransaction();
                } else if (mAction.equals(Action.ACTION_UPDATE_TRANSACTION)) {
                    updateTransaction();
                }
            }
        });
        mCardViewActionBar.setOnClickBack(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ManagerTransactionFragment.this.getContext(), "Cancel",
                          Toast.LENGTH_SHORT).show();
            }
        });
    }
    
    
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        
        if (bundle != null) {
            mAction = bundle.getString("action");
            mCurrentTransaction = bundle.getParcelable("transaction");
            mSaving = bundle.getParcelable("saving");
        }
    }
    
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mWallet = mPreferencesHelper.getCurrentWallet();
        initializeView();
        
        if (mCurrentTransaction != null) {
            initializeWhenUpdateTransaction();
        }
    }
    
    @Override
    public void onDestroyView() {
        mTransactionPresenter.unSubscribe();
        super.onDestroyView();
    }
    
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case RequestCode.CALCULATOR_REQUEST_CODE:
                    //TODO: View, Result
                    String result = data.getStringExtra("result");
                    if (!TextUtil.isEmpty(result)) {
                        mTextViewMoney.setText(result);
                    }
                    break;
                case RequestCode.CHOOSE_CATEGORY_REQUEST_CODE:
                    mCategory = data.getParcelableExtra("category");
                    if (mCategory != null) {
                        mTextViewCategory.setText(mCategory.getName());
                        GlideImageLoader.load(this.getContext(), DrawableUtil
                                            .getDrawable(this.getContext(), mCategory.getIcon()),
                                  mImageViewIconCategory);
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
                        mTextViewContacts.setText(getTextNamePerson(mPersonList));
                    }
                    break;
                case RequestCode.CHOOSE_IMAGE_REQUEST_CODE:
                    mImageGalleries = data
                              .getParcelableArrayListExtra(GalleryLoader.EXTRA_SELECTED_IMAGES);
                    if (mImageGalleries != null) {
                        mSelectedImageAdapter.setData(mImageGalleries);
                        mRecyclerViewImageSelected.setAdapter(mSelectedImageAdapter);
                    }
                case RequestCode.CHOOSE_EVENT_REQUEST_CODE:
                    this.mEvent = data.getParcelableExtra("event");
                    if (mEvent != null) {
                        mTextViewEvent.setText(this.mEvent.getName());
                    }
                    break;
            }
        }
        
    }
    
    
    @Override
    public void onAddSuccessTransaction(String message) {
        
    }
    
    @Override
    public void onUpdateSuccessTransaction(String message) {
        Toast.makeText(this.getContext(), "Updated", Toast.LENGTH_SHORT).show();
    }
    
    
    @Override
    public void onFailure(String message) {
        Toast.makeText(this.getContext(), message, Toast.LENGTH_SHORT).show();
    }
    
    @Override
    public void loading(boolean isLoading) {
        if (isLoading) {
            mProgressDialog.show();
            return;
        }
        mProgressDialog.dismiss();
    }
    
    
    @OnClick({R.id.btn_input_value, R.id.btn_choose_category, R.id.btn_choose_date,
              R.id.txt_date_start, R.id.txt_category, R.id.txt_wallet, R.id.txt_with,
              R.id.txt_event, R.id.txt_remind,
              R.id.btn_choose_wallet, R.id.btn_choose_contact, R.id.btn_choose_location,
              R.id.btn_choose_event, R.id.btn_choose_date_notify, R.id.btn_choose_image_gallery})
    public void onClickInputMoney(View view) {
        switch (view.getId()) {
            case R.id.btn_input_value:
                openCalculatorActivity();
                break;
            case R.id.btn_choose_category:
            case R.id.txt_category:
                openCategoryActivity();
                break;
            case R.id.btn_choose_date:
            case R.id.txt_date_start:
                openDatePickerDialogStartTime();
                break;
            case R.id.btn_choose_wallet:
            case R.id.txt_wallet:
                openMyWalletActivity();
                break;
            case R.id.btn_choose_contact:
            case R.id.txt_with:
                openContactActivity();
                break;
            case R.id.btn_choose_location:
                openSelectLocationActivity();
                break;
            case R.id.btn_choose_event:
            case R.id.txt_event:
                openEventActivity();
                break;
            case R.id.btn_choose_date_notify:
            case R.id.txt_remind:
                openDatePickerDialogEndTime();
                break;
            case R.id.btn_choose_image_gallery:
                if (mPreferencesHelper.getCurrentUser() == null) {
                    showDialogConfirmChooseImage();
                    return;
                }
                openGalleryPickerActivity();
                break;
            default:
                break;
        }
    }
    
    
    private void openCalculatorActivity() {
        Intent intent = new Intent(this.getActivity(), CalculatorActivity.class);
        startActivityForResult(intent, RequestCode.CALCULATOR_REQUEST_CODE);
    }
    
    private void openCategoryActivity() {
        Intent intent = new Intent(this.getActivity(), CategoryActivity.class);
        startActivityForResult(intent, RequestCode.CHOOSE_CATEGORY_REQUEST_CODE);
    }
    
    private void openMyWalletActivity() {
        Intent intent = new Intent(this.getActivity(), MyWalletActivity.class);
        startActivityForResult(intent, RequestCode.CHOOSE_WALLET_REQUEST_CODE);
    }
    
    private void openContactActivity() {
        Intent intent = new Intent(this.getActivity(), PersonActivity.class);
        intent.putExtra("mode", PersonActivity.MODE_SELECT_MULTIPLE);
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList(PersonActivity.EXTRA_SELECTED_PERSON,
                  (ArrayList<? extends Parcelable>) mPersonList);
        intent.putExtras(bundle);
        startActivityForResult(intent, RequestCode.CHOOSE_CONTACT_REQUEST_CODE);
    }
    
    private void openEventActivity() {
        Intent intent = new Intent(this.getActivity(), ActivitySelectEvent.class);
        
        startActivityForResult(intent, RequestCode.CHOOSE_EVENT_REQUEST_CODE);
    }
    
    private void openSelectLocationActivity() {
        
    }
    
    private void openDatePickerDialogStartTime() {
        mDatePickerStartTime.show();
    }
    
    private void openDatePickerDialogEndTime() {
        mDatePickerEndTime.show();
    }
    
    private void openGalleryPickerActivity() {
        GalleryLoader.create(this)
                  .setSelectedImage(mImageGalleries)
                  .setLimit(6)
                  .start(RequestCode.CHOOSE_IMAGE_REQUEST_CODE);
    }
    
    private void initializeView() {
        Calendar calendar = Calendar.getInstance();
        
        mYear_StartTime = calendar.get(Calendar.YEAR);
        mMonth_StartTime = calendar.get(Calendar.MONTH);
        mDayOfMonth_StartTime = calendar.get(Calendar.DAY_OF_MONTH);
        
        mYear_EndTime = mYear_StartTime;
        mMonth_EndTime = mMonth_StartTime;
        mDayOfMonth_EndTime = mMonth_StartTime;
        
        mDatePickerStartTime = new DatePickerDialog(this.getActivity(), mOnDateSetListenerStartTime,
                  mYear_StartTime, mMonth_StartTime, mDayOfMonth_StartTime);
        
        mDatePickerEndTime = new DatePickerDialog(this.getActivity(), mOnDateSetListenerNotify,
                  mYear_EndTime, mMonth_EndTime, mDayOfMonth_EndTime);
        
        mCardViewActionBar.setTitle(getTitle());
        mTextViewDateStart.setText(DateUtil
                  .formatDate(mYear_StartTime, mMonth_StartTime, mDayOfMonth_StartTime));
        
        mSelectedImageAdapter = new SelectedImageAdapter(this.getContext(), null,
                  new RemoveImageSelectedListener() {
                      @Override
                      public void onClick(ImageGallery imageGallery, int position) {
                          mImageGalleries.remove(imageGallery);
                          mSelectedImageAdapter.remove(imageGallery, position);
                      }
                  });
        
        mRecyclerViewImageSelected.setLayoutManager(new GridLayoutManager(this.getActivity(), 3));
        
        mProgressDialog = new ProgressDialog(this.getActivity());
        mProgressDialog.setCanceledOnTouchOutside(false);
        mProgressDialog.setMessage(getTitle() + "...");
    }
    
    
    private void initializeWhenUpdateTransaction() {
        mCategory = mCurrentTransaction.getCategory();
        mEvent = mCurrentTransaction.getEvent();
        mPersonList = mCurrentTransaction.getPerson();
        mImages = mCurrentTransaction.getImage();
        mWallet = mCurrentTransaction.getWallet();
        
        mTextViewMoney.setText(mCurrentTransaction.getAmount());
        mTextViewCategory.setText(mCategory.getName());
        
        GlideImageLoader.load(this.getContext(),
                  DrawableUtil.getDrawable(this.getContext(), mCategory.getIcon()),
                  mImageViewIconCategory);
        
        mEditTextNote.setText(mCurrentTransaction.getNote());
        mTextViewNameWallet.setText(mWallet != null ? mWallet.getWalletName() : "");
        mTextViewContacts.setText(getTextNamePerson(mPersonList));
        Date startTime = DateUtil.timeMilisecondsToDate(mCurrentTransaction.getDate_created());
        mTextViewDateStart.setText(DateUtil.formatDate(startTime));
        
        Date endTime = DateUtil.timeMilisecondsToDate(mCurrentTransaction.getDate_end());
        mTextViewRemind.setText(DateUtil.formatDate(endTime));
    }
    
    private void addTransaction() {
        mCurrentTransaction = new Transaction();
        if (mCategory == null) {
            Toast.makeText(this.getContext(), R.string.txt_please_choose_category,
                      Toast.LENGTH_SHORT).show();
            return;
        }
        String date_created = String.valueOf(DateUtil
                  .getLongAsDate(mDayOfMonth_StartTime, mMonth_StartTime, mYear_StartTime));
        String date_end = String.valueOf(DateUtil
                  .getLongAsDate(mDayOfMonth_EndTime, mMonth_EndTime, mYear_EndTime));
        
        if (Long.valueOf(date_created) > Long.valueOf(date_end)) {
            Toast.makeText(this.getContext(), R.string.txt_warning_date_created, Toast.LENGTH_SHORT)
                      .show();
            return;
        }
        Wallet wallet = mWallet;
        if (wallet == null || TextUtil.isEmpty(mTextViewNameWallet.getText().toString())) {
            Toast.makeText(this.getContext(), R.string.txt_please_choose_wallet, Toast.LENGTH_SHORT)
                      .show();
            return;
        }
        
        mCategory.setSubcategories(null);
        
        String amount = mTextViewMoney.getText().toString().trim();
        Category category = mCategory;
        String note = mEditTextNote.getText().toString().trim();
        
        List<Person> persons = mPersonList;
        Event event = mEvent;
        Saving saving = mSaving;
        
        mCurrentTransaction.setAmount(amount);
        mCurrentTransaction.setNote(note);
        mCurrentTransaction.setCategory(category);
        mCurrentTransaction.setWallet(wallet);
        mCurrentTransaction.setDate_created(date_created);
        mCurrentTransaction.setPerson(persons);
        mCurrentTransaction.setEvent(event);
        mCurrentTransaction.setSaving(saving);
        mCurrentTransaction.setDate_end(date_end);
        
        mTransactionPresenter.addTransaction(mCurrentTransaction, mImageGalleries);
    }
    
    private void updateTransaction() {
        if (mCategory == null) {
            Toast.makeText(this.getContext(), R.string.txt_please_choose_category,
                      Toast.LENGTH_SHORT).show();
            return;
        }
        String date_created = String.valueOf(DateUtil
                  .getLongAsDate(mDayOfMonth_StartTime, mMonth_StartTime, mYear_StartTime));
        String date_end = String.valueOf(DateUtil
                  .getLongAsDate(mDayOfMonth_EndTime, mMonth_EndTime, mYear_EndTime));
        
        if (Long.valueOf(date_created) > Long.valueOf(date_end)) {
            Toast.makeText(this.getContext(), R.string.txt_warning_date_created, Toast.LENGTH_SHORT)
                      .show();
            return;
        }
        Wallet wallet = mWallet;
        if (wallet == null || TextUtil.isEmpty(mTextViewNameWallet.getText().toString())) {
            Toast.makeText(this.getContext(), R.string.txt_please_choose_wallet, Toast.LENGTH_SHORT)
                      .show();
            return;
        }
        
        mCategory.setSubcategories(null);
        
        String amount = mTextViewMoney.getText().toString().trim();
        Category category = mCategory;
        String note = mEditTextNote.getText().toString().trim();
        
        List<Person> persons = mPersonList;
        Event event = mEvent;
        Saving saving = mSaving;
        
        mCurrentTransaction.setAmount(amount);
        mCurrentTransaction.setNote(note);
        mCurrentTransaction.setCategory(category);
        mCurrentTransaction.setWallet(wallet);
        mCurrentTransaction.setDate_created(date_created);
        mCurrentTransaction.setPerson(persons);
        mCurrentTransaction.setEvent(event);
        mCurrentTransaction.setSaving(saving);
        mCurrentTransaction.setDate_end(date_end);
        
        mTransactionPresenter.updateTransaction(mCurrentTransaction);
    }
    
    private String getTextNamePerson(List<Person> list) {
        List<String> namePersons = new ArrayList<>();
        if (list == null) {
            return "";
        }
        for (Person person : list) {
            namePersons.add(person.getName().trim());
        }
        return TextUtils.join(", ", namePersons.toArray());
    }
    
    private String getTitle() {
        if (mAction.equals(Action.ACTION_ADD_TRANSACTION)) {
            return getString(R.string.txt_add_transaction);
        }
        return getString(R.string.txt_edit_transaction);
    }
    
    private void showDialogConfirmChooseImage() {
        AlertDialog.Builder builder = new Builder(this.getActivity());
        builder.setTitle(R.string.txt_wait_a_second);
        builder.setMessage(R.string.message_you_must_login_first);
        builder.setNegativeButton(getString(R.string.txt_ok), new AlertDialog.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                dialog.dismiss();
            }
        });
        
        builder.create().show();
        
    }
}
