package com.vn.hcmute.team.cortana.mymoney.ui.transaction;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;
import butterknife.BindView;
import com.vn.hcmute.team.cortana.mymoney.R;
import com.vn.hcmute.team.cortana.mymoney.data.cache.PreferencesHelper;
import com.vn.hcmute.team.cortana.mymoney.ui.base.BaseFragment;
import com.vn.hcmute.team.cortana.mymoney.ui.view.calendview.CalendarTransactionView;
import com.vn.hcmute.team.cortana.mymoney.ui.view.calendview.CalendarTransactionView.Listener;

/**
 * Created by infamouSs on 11/3/17.
 */

public class TransactionMainFragment extends BaseFragment {
    
    private final int VIEW_BY_TRANS = 0;
    private final int VIEW_BY_CATEGORY = 1;
    
    @BindView(R.id.calendar_view)
    LinearLayout mView;
    
    
    PreferencesHelper mPreferencesHelper;
    
    private boolean mIsViewByCategory = false;
    
    private CalendarTransactionView mCalendarTransactionView;
    
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_main_transaction;
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
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPreferencesHelper = PreferencesHelper.getInstance(this.getContext());
    }
    
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_main_transaction, menu);
        MenuItem menuItem = menu.findItem(R.id.action_view_by_transaction_or_category);
        int viewBy = mPreferencesHelper.getTransactionViewBy();
        if (viewBy == 0) {
            menuItem.setTitle(R.string.action_view_by_transaction);
        } else if (viewBy == 1) {
            menuItem.setTitle(R.string.action_view_by_category);
        }
        
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        
        switch (item.getItemId()) {
            case R.id.action_jump_to_today:
                mCalendarTransactionView.jump2ToDay();
                return true;
            case R.id.action_view_by_transaction_or_category:
                if (mIsViewByCategory) {
                    mPreferencesHelper.putTransactionViewBy(VIEW_BY_CATEGORY);
                    item.setTitle(R.string.action_view_by_transaction);
                } else {
                    mPreferencesHelper.putTransactionViewBy(VIEW_BY_TRANS);
                    item.setTitle(R.string.action_view_by_category);
                }
                return true;
            case R.id.action_view_by_day:
                mPreferencesHelper.putTransactionTimeRange(CalendarTransactionView.MODE_DATE);
                mCalendarTransactionView.init(CalendarTransactionView.MODE_DATE);
                return true;
            case R.id.action_view_by_week:
                mPreferencesHelper.putTransactionTimeRange(CalendarTransactionView.MODE_WEEK);
                mCalendarTransactionView.init(CalendarTransactionView.MODE_WEEK);
                return true;
            case R.id.action_view_by_month:
                mPreferencesHelper.putTransactionTimeRange(CalendarTransactionView.MODE_MONTH);
                mCalendarTransactionView.init(CalendarTransactionView.MODE_MONTH);
                return true;
            case R.id.action_view_by_custom:
                mPreferencesHelper.putTransactionTimeRange(CalendarTransactionView.MODE_CUSTOM);
                mCalendarTransactionView.init(CalendarTransactionView.MODE_CUSTOM);
                return true;
            case R.id.action_view_by_all_trans:
                mPreferencesHelper.putTransactionTimeRange(CalendarTransactionView.MODE_ALL_TRANS);
                mCalendarTransactionView.init(CalendarTransactionView.MODE_ALL_TRANS);
                return true;
            default:
                return false;
        }
    }
    
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mCalendarTransactionView = new CalendarTransactionView(this.getContext());
        mCalendarTransactionView.init(mPreferencesHelper.getTransactionTimeRange());
        
        mCalendarTransactionView.setListener(new Listener() {
            @Override
            public void onClickTab(String data) {
                Toast.makeText(TransactionMainFragment.this.getContext(), data, Toast.LENGTH_SHORT)
                          .show();
            }
        });
        
        mView.addView(mCalendarTransactionView);
    }
}
