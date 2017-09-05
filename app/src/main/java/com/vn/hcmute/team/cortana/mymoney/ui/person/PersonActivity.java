package com.vn.hcmute.team.cortana.mymoney.ui.person;

import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AlertDialog.Builder;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.LayoutManager;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.SearchView.OnQueryTextListener;
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
import com.vn.hcmute.team.cortana.mymoney.utils.sort.SortUtil;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;

/**
 * Created by infamouSs on 8/23/2017.
 */

public class PersonActivity extends BaseActivity implements PersonContract.View {
    
    public static final String TAG = PersonActivity.class.getSimpleName();
    
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
    
    private EditText mEditTextNamePerson_Add;
    private EditText mEditTextDescribePerson_Add;
    
    private EditText mEditTextNamePerson_Edit;
    private EditText mEditTextDescribePerson_Edit;
    
    private AlertDialog.Builder mAddPersonDialog;
    private AlertDialog.Builder mEditPersonDialog;
    
    private PersonAdapter mPersonAdapter;
    private EmptyAdapter mEmptyAdapter;
    
    private List<Person> mSelectedPersons;
    
    
    private DialogInterface.OnClickListener mOnClickAddPersonDialog = new OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            addPerson();
        }
    };
    
    
    private OnPersonClickListener mPersonItemClickListener = new OnPersonClickListener() {
        @Override
        public boolean onPersonClick(int position, boolean isSelected) {
            return true;
        }
        
        
        @Override
        public void onRemoveClick(int position, Person person) {
            mPersonPresenter.removePerson(position, person);
        }
        
        @Override
        public void onEditClick(final int position, final Person person) {
            
            mEditPersonDialog = createEditPersonDialog();
            
            mEditTextNamePerson_Edit.setText(person.getName());
            mEditTextDescribePerson_Edit.setText(person.getDescribe());
            
            mEditPersonDialog
                      .setPositiveButton(getString(R.string.action_ok), new OnClickListener() {
                          @Override
                          public void onClick(DialogInterface dialog, int which) {
                              editPerson(position, person);
                          }
                      });
            mEditPersonDialog.show();
        }
        
    };
    
    private OnQueryTextListener mQueryTextListener = new OnQueryTextListener() {
        @Override
        public boolean onQueryTextSubmit(String query) {
            return false;
        }
        
        @Override
        public boolean onQueryTextChange(String newText) {
            searchPerson(newText);
            return true;
        }
    };
    
    
    public PersonActivity() {
        
    }
    
    /*-----------------*/
    /*Base Method      */
    /*-----------------*/
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
        ActionBar actionBar = getSupportActionBar();
        
        if (actionBar != null) {
            actionBar.setTitle(getString(R.string.txt_person));
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowTitleEnabled(true);
        }
    }
    
    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        getMenuInflater().inflate(R.menu.menu_person, menu);
        final MenuItem searchItem = menu.findItem(R.id.action_search);
        MenuItemCompat.setOnActionExpandListener(searchItem,
                  new MenuItemCompat.OnActionExpandListener() {
                      @Override
                      public boolean onMenuItemActionExpand(MenuItem item) {
                          setItemsVisibility(menu, false);
                          return true;
                      }
                      
                      @Override
                      public boolean onMenuItemActionCollapse(MenuItem item) {
                          setItemsVisibility(menu, true);
                          return true;
                      }
                  });
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setOnQueryTextListener(mQueryTextListener);
        return super.onCreateOptionsMenu(menu);
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                showConfirmDoneDialog();
                return true;
            case R.id.action_done:
                onDone();
                return true;
            case R.id.action_sort_a_z:
                sortPersons(SortType.A_Z);
                return true;
            case R.id.action_sort_z_a:
                sortPersons(SortType.Z_A);
                return true;
            case R.id.action_checked_all_person:
                mPersonAdapter.addAllSelected();
                return true;
            case R.id.action_unchecked_all_person:
                mPersonAdapter.removeAllSelected();
                return true;
        }
        return false;
    }
    
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        mSelectedPersons = getListPerson();
        
        initializeView();
        
        getData();
    }
    
    
    @Override
    public void onBackPressed() {
        showConfirmDoneDialog();
    }
    
    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPersonPresenter.unSubscribe();
    }
    
    @OnClick(R.id.btn_add_person)
    public void onClickAddPerson() {
        mEditTextDescribePerson_Add.setText("");
        mEditTextDescribePerson_Add.setText("");
        
        mAddPersonDialog = createAddPersonDialog();
        mAddPersonDialog.show();
    }
    
    /*-----------------*/
    /*Task View         */
    /*-----------------*/
    @Override
    public void initializeView() {
        
        mSwipeRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                getData();
            }
        });
        
        mAddPersonDialog = createAddPersonDialog();
        mEditPersonDialog = createEditPersonDialog();
        
        LayoutManager layoutManager = new LinearLayoutManager(this);
        
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, null));
        mRecyclerView.setLayoutManager(layoutManager);
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
        mPersonAdapter = new PersonAdapter(this, mSelectedPersons, mPersonItemClickListener);
    }
    
    
    @Override
    public void showListPerson(List<Person> list) {
        if (mSwipeRefreshLayout.isRefreshing()) {
            mSwipeRefreshLayout.setRefreshing(false);
        }
        
        mPersonAdapter.setData(list);
        mRecyclerView.setAdapter(mPersonAdapter);
    }
    
    @Override
    public void showEmpty() {
        if (mSwipeRefreshLayout.isRefreshing()) {
            mSwipeRefreshLayout.setRefreshing(false);
        }
        
        mRecyclerView.setAdapter(mEmptyAdapter);
    }
    
    @Override
    public void onSuccessAddPerson(String message, Person person) {
        if (mRecyclerView.getAdapter() instanceof EmptyAdapter) {
            mRecyclerView.setAdapter(mPersonAdapter);
        }
        
        mPersonAdapter.add(person);
    }
    
    @Override
    public void onSuccessUpdatePerson(String message, int position, Person person) {
        if (mRecyclerView.getAdapter() instanceof EmptyAdapter) {
            mRecyclerView.setAdapter(mPersonAdapter);
        }
        
        mPersonAdapter.update(position, person);
    }
    
    @Override
    public void onSuccessRemovePerson(String message, int position, Person person) {
        mPersonAdapter.remove(position, person);
        
        if (mPersonAdapter.isEmptyData()) {
            mRecyclerView.setAdapter(mEmptyAdapter);
        }
    }
    
    @Override
    public void onFailure(String message) {
        if (mSwipeRefreshLayout.isRefreshing()) {
            mSwipeRefreshLayout.setRefreshing(false);
        }
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        
        if (mPersonAdapter.isEmptyData()) {
            mEmptyAdapter.setMessage(message);
            mRecyclerView.setAdapter(mEmptyAdapter);
        }
    }
    
    @Override
    public void onDoneChoosePerson(final List<Person> selectedPerson) {
        
        Runnable doInBackGround = new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent();
                intent.putParcelableArrayListExtra(PersonActivity.EXTRA_SELECTED_PERSON,
                          (ArrayList<? extends Parcelable>) selectedPerson);
                setResult(RESULT_OK, intent);
                finish();
            }
        };
        
        doInBackGround.run();
    }
    
    @Override
    public void loading(boolean isLoading) {
        if (mSwipeRefreshLayout.isRefreshing()) {
            return;
        }
        
        mProgressBar.setVisibility(isLoading ? View.VISIBLE : View.GONE);
    }
    
    /*-----------------*/
    /*Helper Method    */
    /*-----------------*/
    
    
    private void addPerson() {
        Person person = new Person();
        
        person.setName(mEditTextNamePerson_Add.getText().toString());
        person.setDescribe(mEditTextDescribePerson_Add.getText().toString());
        
        mPersonPresenter.addPerson(person);
    }
    
    private void editPerson(int position, Person person) {
        person.setName(mEditTextNamePerson_Edit.getText().toString());
        person.setDescribe(mEditTextDescribePerson_Edit.getText().toString());
        
        mPersonPresenter.updatePerson(position, person);
    }
    
    private Builder createAddPersonDialog() {
        final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        
        LayoutInflater inflater = this.getLayoutInflater();
        View view = inflater.inflate(R.layout.layout_dialog_add_person, null);
        mEditTextNamePerson_Add = (EditText) view.findViewById(R.id.txt_person_name);
        mEditTextDescribePerson_Add = (EditText) view.findViewById(R.id.txt_person_describe);
        
        dialogBuilder.setView(view);
        dialogBuilder.setTitle(getString(R.string.txt_add_person));
        dialogBuilder.setNegativeButton(getString(R.string.action_ok),
                  mOnClickAddPersonDialog);
        dialogBuilder.setPositiveButton(getString(R.string.txt_cancel),
                  new DialogInterface.OnClickListener() {
                      @Override
                      public void onClick(DialogInterface dialog, int which) {
                          dialog.cancel();
                          dialog.dismiss();
                      }
                  });
        
        return dialogBuilder;
    }
    
    private Builder createEditPersonDialog() {
        final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        
        LayoutInflater inflater = this.getLayoutInflater();
        View view = inflater.inflate(R.layout.layout_dialog_edit_person, null);
        mEditTextNamePerson_Edit = (EditText) view.findViewById(R.id.txt_person_name);
        mEditTextDescribePerson_Edit = (EditText) view.findViewById(R.id.txt_person_describe);
        
        dialogBuilder.setView(view);
        dialogBuilder.setTitle(getString(R.string.txt_edit_person));
        dialogBuilder.setPositiveButton(getString(R.string.txt_cancel),
                  new DialogInterface.OnClickListener() {
                      @Override
                      public void onClick(DialogInterface dialog, int which) {
                          dialog.cancel();
                          dialog.dismiss();
                      }
                  });
        
        return dialogBuilder;
    }
    
    private void showConfirmDoneDialog() {
        Builder doneDialog = new Builder(this);
        doneDialog.setMessage(getString(R.string.message_finish_choosing_person));
        doneDialog.setNegativeButton(getString(R.string.txt_yes), new OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                onDone();
            }
        });
        doneDialog.setPositiveButton(getString(R.string.txt_no), new OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                dialog.dismiss();
            }
        });
        
        doneDialog.create().show();
    }
    
    private void setItemsVisibility(Menu menu, boolean visible) {
        for (int i = 0; i < menu.size(); ++i) {
            MenuItem item = menu.getItem(i);
            int id = item.getItemId();
            if (id != R.id.action_search && id != R.id.action_done) {
                item.setVisible(visible);
            }
        }
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
    
    private void sortPersons(SortType sortType) {
        if (mPersonAdapter == null || mPersonAdapter.isEmptyData()) {
            return;
        }
        List<Person> persons = mPersonAdapter.getData();
        
        if (persons.size() == 1) {
            return;
        }
        
        List<Person> sort = SortUtil.sort(persons, Person.class, "name", sortType);
        
        mPersonAdapter.setData(sort);
        mPersonAdapter.notifyDataSetChanged();
    }
    
    private void onDone() {
        mPersonPresenter.finishChoosePerson(mPersonAdapter.getSelectedPersons());
    }
    
    private void searchPerson(String text) {
        
        if (mPersonAdapter == null || mPersonAdapter.isEmptyData()) {
            return;
        }
        mPersonAdapter.filter(text);
    }
    
}