package com.vn.hcmute.team.cortana.mymoney.ui.person;

import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AlertDialog.Builder;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.LayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.OnClick;
import com.vn.hcmute.team.cortana.mymoney.MyMoneyApplication;
import com.vn.hcmute.team.cortana.mymoney.R;
import com.vn.hcmute.team.cortana.mymoney.di.component.ApplicationComponent;
import com.vn.hcmute.team.cortana.mymoney.di.component.DaggerPersonComponent;
import com.vn.hcmute.team.cortana.mymoney.di.component.PersonComponent;
import com.vn.hcmute.team.cortana.mymoney.di.module.ActivityModule;
import com.vn.hcmute.team.cortana.mymoney.di.module.PersonModule;
import com.vn.hcmute.team.cortana.mymoney.model.Person;
import com.vn.hcmute.team.cortana.mymoney.ui.base.BaseActivity;
import com.vn.hcmute.team.cortana.mymoney.ui.base.EmptyAdapter;
import com.vn.hcmute.team.cortana.mymoney.ui.view.DividerItemDecoration;
import com.vn.hcmute.team.cortana.mymoney.utils.sort.SortType;
import java.util.List;
import javax.inject.Inject;

/**
 * Created by kunsubin on 8/23/2017.
 */

public class PersonActivity extends BaseActivity implements PersonContract.View {
    
    public static final String EXTRA_SELECTED_PERSON = "extra_selected_person";
    
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    
    @BindView(R.id.progress_bar)
    ProgressBar mProgressBar;
    
    @BindView(R.id.swipeRefresh)
    SwipeRefreshLayout mSwipeRefreshLayout;
    
    @BindView(R.id.btn_add_person)
    FloatingActionButton mButtonAddPerson;
    
    @Inject
    PersonPresenter mPersonPresenter;
    
    private EditText mEditTextNamePerson;
    private EditText mEditTextDescribePerson;
    
    private ActionBar mActionBar;
    
    private LayoutManager mLayoutManager;
    
    private AlertDialog mAddPersonDialog;
    
    private PersonAdapter mPersonAdapter;
    private EmptyAdapter mEmptyAdapter;
    
    private List<Person> mSelectedsPerons;
    
    
    private DialogInterface.OnClickListener mOnClickPosition = new OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            addPerson();
        }
    };
    
    private OnPersonClickListener mPersonClickListener = new OnPersonClickListener() {
        @Override
        public boolean onPersonClick(int position, boolean isSelected) {
            return true;
        }
        
        @Override
        public void onLongPersonClick(int position, Person person) {
            mPersonPresenter.removePerson(position, person);
        }
    };
    
    @Override
    public int getLayoutId() {
        return R.layout.activity_person;
    }
    
    @Override
    protected void initializeDagger() {
        ApplicationComponent applicationComponent = ((MyMoneyApplication) this.getApplication())
                  .getAppComponent();
        PersonComponent personComponent = DaggerPersonComponent
                  .builder()
                  .applicationComponent(applicationComponent)
                  .activityModule(new ActivityModule(this))
                  .personModule(new PersonModule())
                  .build();
        personComponent.inject(this);
    }
    
    @Override
    protected void initializePresenter() {
        this.mPresenter = mPersonPresenter;
        mPersonPresenter.setView(this);
    }
    
    @Override
    protected void initializeActionBar(View rootView) {
        setSupportActionBar(mToolbar);
        mActionBar = getSupportActionBar();
        
        if (mActionBar != null) {
            mActionBar.setTitle(getString(R.string.txt_contact));
            mActionBar.setDisplayHomeAsUpEnabled(true);
            mActionBar.setDisplayShowTitleEnabled(true);
        }
    }
    
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuItem menuDone = menu.findItem(R.id.action_done);
        if (menuDone != null) {
            //menuDone.setVisible(mRecyclerViewManager.isShowDoneButton());
        }
        
        return super.onPrepareOptionsMenu(menu);
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_person, menu);
        return super.onCreateOptionsMenu(menu);
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return false;
    }
    
    private void onDone() {
    }
    
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        mSelectedsPerons = getListPerson();
        
        initializeView();
        
        getData();
    }
    
    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPersonPresenter.unSubscribe();
    }
    
    @OnClick(R.id.btn_add_person)
    public void onClickAddPerson() {
        mAddPersonDialog.show();
    }
    
    private void addPerson() {
        Person person = new Person();
        
        person.setName(mEditTextNamePerson.getText().toString());
        person.setDescribe(mEditTextDescribePerson.getText().toString());
        
        mPersonPresenter.addPerson(person);
    }
    
    private Builder createAddPersonDialog() {
        final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        
        LayoutInflater inflater = this.getLayoutInflater();
        
        View dialogView = inflater.inflate(R.layout.layout_dialog_add_person, null);
        
        dialogBuilder.setView(dialogView);
        
        dialogBuilder.setTitle(getString(R.string.txt_add_person));
        
        dialogBuilder.setNegativeButton(getString(R.string.action_ok),
                  mOnClickPosition);
        dialogBuilder.setPositiveButton(getString(R.string.txt_cancel),
                  new DialogInterface.OnClickListener() {
                      @Override
                      public void onClick(DialogInterface dialog, int which) {
                          dialog.dismiss();
                      }
                  });
        
        mEditTextNamePerson = (EditText) dialogView.findViewById(R.id.txt_person_name);
        mEditTextDescribePerson = (EditText) dialogView.findViewById(R.id.txt_person_describe);
        
        return dialogBuilder;
    }
    
    private List<Person> getListPerson() {
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle == null) {
            return null;
        }
        return bundle.getParcelableArrayList(EXTRA_SELECTED_PERSON);
    }
    
    private void getData() {
        mPersonPresenter.getPerson();
    }
    
    
    @Override
    public void initializeView() {
        
        mSwipeRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                Toast.makeText(PersonActivity.this, "swipe", Toast.LENGTH_SHORT).show();
                //getData();
            }
        });
        mAddPersonDialog = createAddPersonDialog().create();
        
        mLayoutManager = new LinearLayoutManager(this);
        
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, null));
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                int topRowVerticalPosition =
                          (recyclerView == null || recyclerView.getChildCount() == 0) ? 0
                                    : recyclerView.getChildAt(0).getTop();
                mSwipeRefreshLayout.setEnabled(topRowVerticalPosition >= 0);
                
            }
            
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }
        });
        
        mEmptyAdapter = new EmptyAdapter(this, getString(R.string.message_empty_person));
        mPersonAdapter = new PersonAdapter(this, mSelectedsPerons, mPersonClickListener);
    }
    
    @Override
    public void showListPerson(List<Person> list) {
        
        mPersonAdapter.setData(list);
        mRecyclerView.setAdapter(mPersonAdapter);
    }
    
    @Override
    public void showEmpty() {
        Toast.makeText(this, "EMPTY", Toast.LENGTH_SHORT).show();
        mRecyclerView.setAdapter(mEmptyAdapter);
    }
    
    @Override
    public void onSuccessAddPerson(String message, Person person) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
        mEditTextNamePerson.setText("");
        mEditTextDescribePerson.setText("");
        if (mRecyclerView.getAdapter() instanceof EmptyAdapter) {
            mRecyclerView.setAdapter(mPersonAdapter);
        }
        mPersonAdapter.add(person);
    }
    
    @Override
    public void onSuccessRemovePerson(String message, int position, Person person) {
        Toast.makeText(this, "Remove successful", Toast.LENGTH_LONG).show();
        mPersonAdapter.remove(position, person);
        if (mPersonAdapter.isEmpty()) {
            mRecyclerView.setAdapter(mEmptyAdapter);
        }
    }
    
    @Override
    public void onFailure(String message) {
        
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }
    
    @Override
    public void loading(boolean isLoading) {
        mProgressBar.setVisibility(isLoading ? View.VISIBLE : View.GONE);
    }
}