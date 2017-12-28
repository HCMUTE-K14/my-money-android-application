package com.vn.hcmute.team.cortana.mymoney.ui.budget;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.OnClick;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.vn.hcmute.team.cortana.mymoney.MyMoneyApplication;
import com.vn.hcmute.team.cortana.mymoney.R;
import com.vn.hcmute.team.cortana.mymoney.di.component.ApplicationComponent;
import com.vn.hcmute.team.cortana.mymoney.di.component.BudgetComponent;
import com.vn.hcmute.team.cortana.mymoney.di.component.DaggerBudgetComponent;
import com.vn.hcmute.team.cortana.mymoney.di.module.ActivityModule;
import com.vn.hcmute.team.cortana.mymoney.di.module.BudgetModule;
import com.vn.hcmute.team.cortana.mymoney.model.Budget;
import com.vn.hcmute.team.cortana.mymoney.ui.base.BaseActivity;
import com.vn.hcmute.team.cortana.mymoney.utils.DateUtil;
import com.vn.hcmute.team.cortana.mymoney.utils.DrawableUtil;
import com.vn.hcmute.team.cortana.mymoney.utils.GlideImageLoader;
import com.vn.hcmute.team.cortana.mymoney.utils.NumberUtil;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;

/**
 * Created by kunsubin on 9/16/2017.
 */

public class InfoBudgetActivity extends BaseActivity implements BudgetContract.View,
                                                                CompoundButton.OnCheckedChangeListener {
    
    @BindView(R.id.image_icon_category)
    ImageView image_icon_category;
    @BindView(R.id.txt_budget_name)
    TextView txt_budget_name;
    @BindView(R.id.txt_goal_money)
    TextView txt_goal_money;
    @BindView(R.id.txt_current_money)
    TextView txt_current_money;
    @BindView(R.id.txt_need_money)
    TextView txt_need_money;
    @BindView(R.id.seek_bar_saving_info)
    SeekBar seek_bar_saving_info;
    @BindView(R.id.txt_range_time)
    TextView txt_range_time;
    @BindView(R.id.txt_rest_time)
    TextView txt_rest_time;
    @BindView(R.id.txt_wallet)
    TextView txt_wallet;
    @BindView(R.id.piechart)
    PieChart mPieChart;
    @BindView(R.id.txt_spent)
    TextView txt_spent;
    @BindView(R.id.switch_notification)
    Switch mSwitch;
    @BindView(R.id.txt_turn_notification)
    TextView txt_turn_notification;
    
    @Inject
    BudgetPresenter mBudgetPresenter;
    
    private Budget mBudget;
    private ProgressDialog mProgressDialog;
    
    @Override
    public int getLayoutId() {
        return R.layout.activity_info_budget;
    }
    
    @Override
    protected void initializeDagger() {
        ApplicationComponent applicationComponent = ((MyMoneyApplication) this.getApplication())
                  .getAppComponent();
        BudgetComponent budgetComponent = DaggerBudgetComponent
                  .builder()
                  .applicationComponent(applicationComponent)
                  .activityModule(new ActivityModule(this))
                  .budgetModule(new BudgetModule())
                  .build();
        budgetComponent.inject(this);
    }
    
    @Override
    protected void initializePresenter() {
        mPresenter = mBudgetPresenter;
        mBudgetPresenter.setView(this);
    }
    
    @Override
    protected void initializeActionBar(View rootView) {
        
    }
    
    @Override
    protected void initialize() {
        getData();
        showData();
        initPieChart();
        showChart();
        mSwitch.setOnCheckedChangeListener(this);
        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setCanceledOnTouchOutside(false);
        mProgressDialog.setMessage(this.getString(R.string.txt_progress));
    }
    
    @Override
    protected void onDestroy() {
        mBudgetPresenter.unSubscribe();
        super.onDestroy();
    }
    
    @Override
    public void onBackPressed() {
        onClose();
        super.onBackPressed();
    }
    
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 36) {
            if (resultCode == Activity.RESULT_OK) {
                mBudget = data.getParcelableExtra("budget");
                showData();
            }
        }
    }
    
    @Override
    public void onSuccessGetListBudget(List<Budget> list) {
        
    }
    
    @Override
    public void onSuccessCreateBudget(String message) {
        
    }
    
    @Override
    public void onSuccessUpdateBudget(String message) {
        
    }
    
    @Override
    public void onSucsessDeleteBudget(String message) {
        Intent returnIntent = new Intent();
        returnIntent.putExtra("result", mBudget.getBudgetId());
        setResult(Activity.RESULT_OK, returnIntent);
        finish();
    }
    
    @Override
    public void onFailure(String message) {
        alertDiaglog(message);
    }
    
    @Override
    public void loading(boolean isLoading) {
        if (isLoading) {
            mProgressDialog.show();
        } else {
            mProgressDialog.dismiss();
        }
    }
    
    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (isChecked) {
            txt_turn_notification.setText(this.getString(R.string.turn_on_notification));
        } else {
            txt_turn_notification.setText(this.getString(R.string.turn_off_notification));
        }
    }
    
    /*Area OnClick*/
    @OnClick(R.id.image_view_cancel)
    public void onClickCancel(View view) {
        onClose();
    }
    
    @OnClick(R.id.image_view_edit)
    public void onClickEdit(View view) {
        if (checkEditBudget()) {
            Intent intent = new Intent(this, EditBudgetActivity.class);
            intent.putExtra("budget", mBudget);
            startActivityForResult(intent, 36);
        } else {
            alertDiaglog(getString(R.string.txt_check_edit_budget));
        }
        
    }
    
    @OnClick(R.id.image_view_delete)
    public void onClickDelete(View view) {
        final AlertDialog.Builder doneDialog = new AlertDialog.Builder(this);
        doneDialog.setMessage(getString(R.string.content_delete_saving));
        doneDialog.setNegativeButton(getString(R.string.txt_yes), new OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mBudgetPresenter.deleteBudget(mBudget.getBudgetId());
            }
        });
        doneDialog.setPositiveButton(getString(R.string.txt_no), new OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        
        doneDialog.create().show();
    }
    
    @OnClick(R.id.btn_list_transaction)
    public void onClickListTransaction(View view) {
        Intent intent = new Intent(this, TransactionBudgetActivity.class);
        intent.putExtra("budget", mBudget);
        startActivity(intent);
        
    }
    
    /*Area function*/
    public void getData() {
        Intent intent = getIntent();
        mBudget = intent.getParcelableExtra("budget");
    }
    
    public void showData() {
        GlideImageLoader.load(this, DrawableUtil.getDrawable(this, mBudget.getCategory().getIcon()),
                  image_icon_category);
        
        txt_budget_name.setText(mBudget.getCategory().getName());
        txt_goal_money.setText("+" + NumberUtil.formatAmount(mBudget.getMoneyGoal(),
                  mBudget.getWallet().getCurrencyUnit().getCurSymbol()));
        
        if (checkCurrentMoney()) {
            txt_current_money.setText("+" + NumberUtil.formatAmount(mBudget.getMoneyExpense(), ""));
            txt_need_money.setText(NumberUtil.formatAmount(getTextNeedMoney(), ""));
            
            seek_bar_saving_info.setProgress(getProgress());
            seek_bar_saving_info.setEnabled(false);
        } else {
            double money=Double.parseDouble(mBudget.getMoneyGoal())-Double.parseDouble(mBudget.getMoneyExpense());
            txt_current_money.setText(NumberUtil.formatAmount(String.valueOf(money), ""));
            txt_current_money.setTextColor(ContextCompat.getColor(this, R.color.color_red));
            if (money < 0) {
                txt_spent.setText(this.getString(R.string.txt_overspent));
            }
            
            txt_need_money.setText("0");
            seek_bar_saving_info.setProgress(100);
            seek_bar_saving_info.setEnabled(false);
            seek_bar_saving_info.setProgressDrawable(
                      ContextCompat.getDrawable(this, R.drawable.process_color_red));
        }
        txt_range_time.setText(getRangeDate());
        txt_rest_time.setText(this
                  .getString(R.string.days_left, getTimeRest(mBudget.getRangeDate()) + ""));
        txt_wallet.setText(mBudget.getWallet().getWalletName());
    }
    
    public String getRecommendedDailySpend() {
        double money = Double.parseDouble(getTextNeedMoney()) /
                       Double.parseDouble(getTimeRest(mBudget.getRangeDate()));
        return NumberUtil
                  .formatAmount(money + "", mBudget.getWallet().getCurrencyUnit().getCurSymbol());
    }
    
    public String getTimeRest(String rangeDate) {
        String[] arr = rangeDate.split("/");
        int result = DateUtil.getDateLeft(Long.parseLong(arr[1]));
        return String.valueOf(result);
    }
    
    public boolean checkCurrentMoney() {
        double currentMoney = Double.parseDouble(mBudget.getMoneyExpense());
        double goalMoney=Double.parseDouble(mBudget.getMoneyGoal());
        return currentMoney < goalMoney ? true : false;
    }
    
    public String getRangeDate() {
        String[] arr = mBudget.getRangeDate().split("/");
        String startDate = DateUtil.convertTimeMillisToDate(arr[0]);
        String endDate = DateUtil.convertTimeMillisToDate(arr[1]);
        return startDate + " - " + endDate;
    }
    
    public int getProgress() {
        double result = (Double.parseDouble(mBudget.getMoneyExpense()) /
                         Double.parseDouble(mBudget.getMoneyGoal())) * 100;
        return (int) result;
    }
    
    public String getTextNeedMoney() {
        double needMoney = Double.parseDouble(mBudget.getMoneyGoal()) -
                           Double.parseDouble(mBudget.getMoneyExpense());
        return needMoney + "";
    }
    
    private void onClose() {
        Intent returnIntent = new Intent();
        setResult(Activity.RESULT_CANCELED, returnIntent);
        finish();
    }
    
    public void alertDiaglog(String message) {
        AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
        builder1.setMessage(message);
        builder1.setCancelable(true);
        builder1.setPositiveButton(
                  getString(R.string.txt_ok),
                  new DialogInterface.OnClickListener() {
                      public void onClick(DialogInterface dialog, int id) {
                          dialog.cancel();
                      }
                  });
        
        AlertDialog alert11 = builder1.create();
        alert11.show();
        
    }
    
    public boolean checkEditBudget() {
        return mBudget.getMoneyExpense().equals("0") ? true : false;
    }
    
    
    public void showChart() {
        if (Double.parseDouble(mBudget.getMoneyExpense()) < 0 ||
            Double.parseDouble(mBudget.getMoneyExpense()) ==
            Double.parseDouble(mBudget.getMoneyGoal())) {
            addDataFullBudget();
        } else {
            addDataSet();
        }
    }
    
    public void addDataFullBudget() {
        ArrayList<PieEntry> yEntrys = new ArrayList<>();
        yEntrys.add(new PieEntry(100, 0));
        yEntrys.get(0).setLabel("Spent");
        
        String overBudget;
        if (Double.parseDouble(mBudget.getMoneyExpense()) ==
            Double.parseDouble(mBudget.getMoneyGoal())) {
            overBudget = "";
        } else {
            double moneyOver = Double.parseDouble(mBudget.getMoneyExpense());
            overBudget = "Over budget:" + mBudget.getMoneyExpense();
        }
        
        PieDataSet pieDataSet = new PieDataSet(yEntrys, overBudget);
        pieDataSet.setSliceSpace(2);
        pieDataSet.setValueTextSize(12);
        
        pieDataSet.setColors(Color.RED);
        
        //add legend to chart
        Legend legend = mPieChart.getLegend();
        legend.setForm(Legend.LegendForm.CIRCLE);
        legend.setPosition(Legend.LegendPosition.LEFT_OF_CHART);
        
        //create pie data object
        PieData pieData = new PieData(pieDataSet);
        mPieChart.setData(pieData);
        mPieChart.invalidate();
    }
    
    public void addDataSet() {
        float moneyTransaction = (float) (
                  (Double.parseDouble(mBudget.getMoneyExpense()) /
                   Double.parseDouble(mBudget.getMoneyGoal())) * 100);
        moneyTransaction = NumberUtil.format(moneyTransaction);
        float restMoney = 100 - moneyTransaction;
        
        ArrayList<PieEntry> yEntrys = new ArrayList<>();
        yEntrys.add(new PieEntry(moneyTransaction, 0));
        yEntrys.add(new PieEntry(restMoney, 1));
        
        yEntrys.get(0).setLabel("Spent");
        yEntrys.get(1).setLabel("Rest");
        
        //create the data set
        PieDataSet pieDataSet = new PieDataSet(yEntrys, "");
        pieDataSet.setSliceSpace(2);
        pieDataSet.setValueTextSize(12);
        
        //add colors to dataset
        ArrayList<Integer> colors = new ArrayList<>();
        colors.add(Color.RED);
        colors.add(Color.GREEN);
        pieDataSet.setColors(colors);
        
        //add legend to chart
        Legend legend = mPieChart.getLegend();
        legend.setForm(Legend.LegendForm.CIRCLE);
        legend.setPosition(Legend.LegendPosition.LEFT_OF_CHART);
        
        //create pie data object
        PieData pieData = new PieData(pieDataSet);
        mPieChart.setData(pieData);
        mPieChart.invalidate();
    }
    
    public void initPieChart() {
        mPieChart.setDescription(null);
        mPieChart.setRotationEnabled(false);
        mPieChart.setHoleRadius(25f);
        mPieChart.setTransparentCircleAlpha(0);
        mPieChart.setCenterText(null);
    }
    
    
}
