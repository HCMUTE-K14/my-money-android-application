package com.vn.hcmute.team.cortana.mymoney.ui.category;

import static android.app.Activity.RESULT_OK;

import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.OnClick;
import com.vn.hcmute.team.cortana.mymoney.MyMoneyApplication;
import com.vn.hcmute.team.cortana.mymoney.R;
import com.vn.hcmute.team.cortana.mymoney.di.component.ApplicationComponent;
import com.vn.hcmute.team.cortana.mymoney.di.component.CategoryComponent;
import com.vn.hcmute.team.cortana.mymoney.di.component.DaggerCategoryComponent;
import com.vn.hcmute.team.cortana.mymoney.di.module.ActivityModule;
import com.vn.hcmute.team.cortana.mymoney.di.module.CategoryModule;
import com.vn.hcmute.team.cortana.mymoney.di.module.GlideApp;
import com.vn.hcmute.team.cortana.mymoney.model.Category;
import com.vn.hcmute.team.cortana.mymoney.model.Icon;
import com.vn.hcmute.team.cortana.mymoney.ui.base.BaseFragment;
import com.vn.hcmute.team.cortana.mymoney.ui.category.CategoryContract.AddEditView;
import com.vn.hcmute.team.cortana.mymoney.ui.iconshop.SelectIconActivity;
import com.vn.hcmute.team.cortana.mymoney.ui.view.CardViewActionBar;
import com.vn.hcmute.team.cortana.mymoney.usecase.base.Action;
import com.vn.hcmute.team.cortana.mymoney.utils.Constraints;
import com.vn.hcmute.team.cortana.mymoney.utils.Constraints.RequestCode;
import com.vn.hcmute.team.cortana.mymoney.utils.DrawableUtil;
import com.vn.hcmute.team.cortana.mymoney.utils.validate.TextUtil;
import javax.inject.Inject;

/**
 * Created by infamouSs on 9/18/17.
 */

public class ManagerCategoryFragment extends BaseFragment implements AddEditView {
    
    public static final String TAG = ManagerCategoryFragment.class.getSimpleName();
    
    private final String INCOME = "income";
    private final String EXPENSE = "expense";
    
    @BindView(R.id.card_view_action_bar)
    CardViewActionBar mCardViewActionBar;
    
    @BindView(R.id.txt_name_category)
    EditText mEditTextNameCategory;
    
    @BindView(R.id.image_view_icon)
    ImageView mImageViewIcon;
    
    @BindView(R.id.radio_income)
    RadioButton mRadioButtonIncome;
    
    @BindView(R.id.radio_expense)
    RadioButton mRadioButtonExpense;
    
    @BindView(R.id.radio_group)
    RadioGroup mRadioGroup;
    
    @BindView(R.id.txt_parent_category)
    EditText mEditTextParentCategory;
    
    @Inject
    CategoryPresenter mCategoryPresenter;
    
    
    private String mAction;
    private String mTypeCategory;
    private Category mCurrentCategory;
    private Category mCurrentParentCategory;
    private String mIcon;
    private boolean needChooseIcon = true;
    private String mTransType;
    
    public ManagerCategoryFragment() {
        
    }
    
    public static ManagerCategoryFragment newInstance(String action, String transType,
              Category category) {
        ManagerCategoryFragment managerCategoryFragment = new ManagerCategoryFragment();
        Bundle bundle = new Bundle();
        switch (action) {
            case Action.ACTION_ADD_CATEGORY:
                bundle.putString("action", action);
                bundle.putParcelable("category", category);
                bundle.putString("trans_type", transType);
                break;
            case Action.ACTION_UPDATE_CATEGORY:
                bundle.putString("action", action);
                bundle.putParcelable("category", category);
                bundle.putString("trans_type", transType);
                break;
            default:
                throw new RuntimeException("Wrong type");
        }
        
        managerCategoryFragment.setArguments(bundle);
        
        return managerCategoryFragment;
    }
    
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_manager_cateogry;
    }
    
    @Override
    protected void initializeDagger() {
        ApplicationComponent applicationComponent = ((MyMoneyApplication) this.getActivity()
                  .getApplication()).getAppComponent();
        
        CategoryComponent categoryComponent = DaggerCategoryComponent.builder()
                  .applicationComponent(applicationComponent)
                  .activityModule(new ActivityModule(this.getActivity()))
                  .categoryModule(new CategoryModule())
                  .build();
        
        categoryComponent.inject(this);
    }
    
    @Override
    protected void initializePresenter() {
        this.mPresenter = mCategoryPresenter;
        mCategoryPresenter.setView(this);
    }
    
    @Override
    protected void initializeActionBar(View rootView) {
        mCardViewActionBar.setTextAction(getString(R.string.txt_done));
        mCardViewActionBar.setIconBack(R.drawable.ic_back);
        switch (mAction) {
            case Action.ACTION_ADD_CATEGORY:
                mCardViewActionBar.setTitle(getString(R.string.txt_add_category));
                mCardViewActionBar.setOnClickBack(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showConfirmQuitDialog();
                    }
                });
                mCardViewActionBar.setOnClickAction(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        addCategory();
                    }
                });
                break;
            case Action.ACTION_UPDATE_CATEGORY:
                mCardViewActionBar.setTitle(getString(R.string.txt_edit_category));
                mCardViewActionBar.setOnClickBack(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showConfirmQuitDialog();
                    }
                });
                mCardViewActionBar.setOnClickAction(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        updateCategory();
                    }
                });
                break;
            default:
                break;
        }
        
        
    }
    
    
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        if (getArguments() != null) {
            Bundle bundle = getArguments();
            
            mAction = bundle.getString("action");
            mCurrentCategory = bundle.getParcelable("category");
            mTransType = bundle.getString("trans_type");
        }
    }
    
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        
        initializeView();
    }
    
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == RequestCode.SELECT_ICON_REQUEST_CODE && data != null) {
                Icon icon = data.getParcelableExtra("icon");
                if (icon != null) {
                    GlideApp.with(this.getContext())
                              .load(DrawableUtil.getDrawable(this.getContext(), icon.getImage()))
                              .placeholder(R.drawable.folder_placeholder)
                              .error(R.drawable.folder_placeholder)
                              .into(mImageViewIcon);
                    
                    mIcon = icon.getImage();
                    needChooseIcon = false;
                }
            } else if (requestCode == RequestCode.CHOOSE_PARENT_CATEGORY_REQUEST_CODE &&
                       data != null) {
                
                mCurrentParentCategory = data.getParcelableExtra("category_parent");
                if (mCurrentParentCategory != null) {
                    mEditTextParentCategory.setText(mCurrentParentCategory.getName());
                }
            }
        }
    }
    
    
    @OnClick({R.id.parent_text_2, R.id.image_view_parent, R.id.txt_parent_category})
    public void onClickChooseParent() {
        openListParentActivity();
    }
    
    
    @OnClick(R.id.image_view_icon)
    public void onClickChooseIcon() {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(ManagerCategoryFragment.this.getContext(),
                          SelectIconActivity.class);
                getActivity().startActivityForResult(intent, RequestCode.SELECT_ICON_REQUEST_CODE);
            }
        }, 150);
        
    }
    
    @Override
    public void initializeView() {
        mRadioGroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                mCurrentParentCategory = null;
                mEditTextParentCategory.setText("");
                if (checkedId == R.id.radio_income) {
                    mTypeCategory = INCOME;
                } else if (checkedId == R.id.radio_expense) {
                    mTypeCategory = EXPENSE;
                }
            }
        });
        
        if (mAction.equals(Action.ACTION_UPDATE_CATEGORY)) {
            initViewWhenActionIsUpdateCategory();
        }
    }
    
    @Override
    public void onFailure(String message) {
        showDialogError(message);
    }
    
    @Override
    public void loading(boolean isLoading) {
        
    }
    
    @Override
    public void onAddSuccessCategory(String message, Category category) {
        getActivity().setResult(RESULT_OK);
        getActivity().finish();
    }
    
    @Override
    public void onEditSuccessCategory(String message, Category category) {
        getActivity().setResult(RESULT_OK);
        getActivity().finish();
    }
    
    private void initViewWhenActionIsUpdateCategory() {
        if (mCurrentCategory == null) {
            return;
        }
        
        mEditTextNameCategory.setText(mCurrentCategory.getName());
        GlideApp.with(this.getContext())
                  .load(DrawableUtil.getDrawable(this.getContext(), mCurrentCategory.getIcon()))
                  .placeholder(R.drawable.folder_placeholder)
                  .error(R.drawable.folder_placeholder)
                  .into(mImageViewIcon);
        mIcon = mCurrentCategory.getIcon();
        
        mTypeCategory = mCurrentCategory.getType();
        switch (mTypeCategory) {
            case INCOME:
                mRadioButtonIncome.setChecked(true);
                break;
            case EXPENSE:
                mRadioButtonExpense.setChecked(true);
                break;
        }
        
        if (mAction.equals(Action.ACTION_UPDATE_CATEGORY)) {
            mRadioButtonExpense.setEnabled(false);
            mRadioButtonIncome.setEnabled(false);
        }
        
        if (mCurrentCategory.getParent() != null) {
            if (mCurrentCategory.getId().equals(mCurrentCategory.getParent().getId())) {
                mCurrentCategory.setParent(null);
                mEditTextParentCategory.setEnabled(false);
                return;
            }
            mEditTextParentCategory.setText(mCurrentCategory.getParent().getName());
        }
    }
    
    private void openListParentActivity() {
        
        if (TextUtil.isEmpty(mTypeCategory)) {
            Toast.makeText(this.getContext(), R.string.message_warning_choose_type_category,
                      Toast.LENGTH_SHORT).show();
            return;
        }
        
        Intent intent = new Intent(this.getContext(), ListParentCategoryActivity.class);
        intent.putExtra("type", mTypeCategory);
        intent.putExtra("trans_type", mTransType);
        if (mCurrentParentCategory != null) {
            intent.putExtra("selected_cate_id", mCurrentParentCategory.getId());
        }
        
        getActivity().startActivityForResult(intent,
                  Constraints.RequestCode.CHOOSE_PARENT_CATEGORY_REQUEST_CODE);
    }
    
    private void showConfirmQuitDialog() {
        Builder doneDialog = new Builder(this.getActivity());
        doneDialog.setMessage(getString(R.string.message_finish));
        doneDialog.setNegativeButton(getString(R.string.txt_yes),
                  new DialogInterface.OnClickListener() {
                      @Override
                      public void onClick(DialogInterface dialog, int which) {
                          getActivity().finish();
                      }
                  });
        doneDialog.setPositiveButton(getString(R.string.txt_no),
                  new DialogInterface.OnClickListener() {
                      @Override
                      public void onClick(DialogInterface dialog, int which) {
                          dialog.cancel();
                          dialog.dismiss();
                      }
                  });
        doneDialog.create().show();
        
        
    }
    
    private void addCategory() {
        if (needChooseIcon) {
            Toast.makeText(this.getContext(), R.string.message_warning_choose_icon,
                      Toast.LENGTH_SHORT).show();
            return;
        }
        
        Category category = new Category();
        if (mCurrentParentCategory != null) {
            category.setParent(mCurrentParentCategory);
        }
        category.setTransType(mTransType);
        category.setType(mTypeCategory);
        category.setName(mEditTextNameCategory.getText().toString().trim());
        category.setIcon(mIcon);
        
        mCategoryPresenter.addCategory(category);
    }
    
    private void updateCategory() {
        mCurrentCategory.setIcon(mIcon);
        mCurrentCategory.setName(mEditTextNameCategory.getText().toString().trim());
        String oldParentId = null;
        String newParentId = null;
        
        if (mCurrentCategory.getParent() != null) {
            oldParentId = mCurrentCategory.getParent().getId();
        }
        
        if (mCurrentParentCategory != null) {
            newParentId = mCurrentParentCategory.getId();
        }
        
        mCategoryPresenter.updateCategory(mCurrentCategory, oldParentId, newParentId);
    }
    
    private void showDialogError(String message) {
        Builder builder = new Builder(this.getActivity());
        builder.setTitle(R.string.txt_warning);
        builder.setMessage(message);
        builder.setPositiveButton(R.string.action_ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                dialog.dismiss();
            }
        });
        
        builder.create().show();
    }
}
