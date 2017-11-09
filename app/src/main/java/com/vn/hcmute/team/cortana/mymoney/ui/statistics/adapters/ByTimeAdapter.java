package com.vn.hcmute.team.cortana.mymoney.ui.statistics.adapters;


import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.Legend.LegendForm;
import com.github.mikephil.charting.components.Legend.LegendPosition;
import com.github.mikephil.charting.components.XAxis.XAxisPosition;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.vn.hcmute.team.cortana.mymoney.R;
import com.vn.hcmute.team.cortana.mymoney.model.Wallet;
import com.vn.hcmute.team.cortana.mymoney.ui.statistics.Objects.ObjectByTime;
import com.vn.hcmute.team.cortana.mymoney.ui.statistics.fragment.FragmentByTime;
import com.vn.hcmute.team.cortana.mymoney.utils.DateUtil;
import com.vn.hcmute.team.cortana.mymoney.utils.NumberUtil;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


/**
 * Created by kunsubin on 11/5/2017.
 */

public class ByTimeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    
    private List<ObjectByTime> mData = Collections.emptyList();
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;
    private Context mContext;
    private int IdCategory;
    private Wallet mWallet;
    
    public ByTimeAdapter(Context context, int idCategory,
              List<ObjectByTime> data, Wallet wallet) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
        this.mContext = context;
        this.IdCategory = idCategory;
        this.mWallet = wallet;
    }
    
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        if (viewType == 0) {
            view = mInflater.inflate(R.layout.item_header_bar_chart, parent, false);
            return new HeaderViewHoder(view);
        } else {
            view = mInflater.inflate(R.layout.item_by_date, parent, false);
            return new ItemViewHolder(view);
        }
        
    }
    
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if (position == 0) {
            ((HeaderViewHoder) holder).bind();
        } else {
            
            ((ItemViewHolder) holder).bindView(getItem(position - 1));
        }
    }
    
    @Override
    public int getItemCount() {
        return mData.size() + 1;
    }
    
    public ObjectByTime getItem(int id) {
        
        return mData.get(id);
    }
    
    public void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }
    
    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return 0;
        }
        return position;
    }
    
    public interface ItemClickListener {
        
        void onItemClick(ObjectByTime objectByTime);
    }
    
    public class HeaderViewHoder extends RecyclerView.ViewHolder {
        
        @BindView(R.id.bar_chart)
        BarChart mBarChart;
        
        public HeaderViewHoder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
        
        public void bind() {
            initBarChart();
            if (IdCategory == FragmentByTime.ID_EXPENSE) {
                showBarCharExpenseAndInCome();
                return;
            }
            if (IdCategory == FragmentByTime.ID_INCOME) {
                showBarCharExpenseAndInCome();
                return;
            }
            if (IdCategory == FragmentByTime.ID_NETINCOME) {
                showBarChartNetIncome();
                return;
            }
        }
        
        public void initBarChart() {
            mBarChart.setDescription(null);
            mBarChart.setFitBars(true);
            mBarChart.setDoubleTapToZoomEnabled(false);
            mBarChart.getXAxis().setDrawGridLines(false);
            mBarChart.getAxisRight().setEnabled(false);
            mBarChart.getAxisLeft().setDrawGridLines(false);
            mBarChart.getXAxis().setPosition(XAxisPosition.BOTTOM);
            if (IdCategory == FragmentByTime.ID_NETINCOME) {
                return;
            }
            mBarChart.getAxisLeft().setAxisMinimum(0f);
            mBarChart.getAxisRight().setAxisMinimum(0f);
            
        }
        
        public void showBarCharExpenseAndInCome() {
            ArrayList<BarEntry> yEntrys = new ArrayList<>();
            final ArrayList<String> xLabel = new ArrayList<>();
            for (int i = 0; i < mData.size(); i++) {
                xLabel.add(mData.get(i).getDate().split("/")[0]);
                if (IdCategory == FragmentByTime.ID_EXPENSE) {
                    yEntrys.add(new BarEntry(i, Float.parseFloat(mData.get(i).getMoneyExpense())));
                } else if (IdCategory == FragmentByTime.ID_INCOME) {
                    yEntrys.add(new BarEntry(i, Float.parseFloat(mData.get(i).getMoneyIncome())));
                }
                
            }
            BarDataSet barDataSet;
            if (IdCategory == FragmentByTime.ID_EXPENSE) {
                barDataSet = new BarDataSet(yEntrys, mContext.getString(R.string.txt_expense));
                barDataSet.setColor(Color.RED);
            } else {
                barDataSet = new BarDataSet(yEntrys, mContext.getString(R.string.txt_income));
                barDataSet.setColor(Color.GREEN);
            }
            
            //add legend to chart
            Legend legend = mBarChart.getLegend();
            legend.setForm(Legend.LegendForm.CIRCLE);
            legend.setPosition(LegendPosition.BELOW_CHART_LEFT);
            
            //create bar data object
            BarData barData = new BarData(barDataSet);
            mBarChart.setData(barData);
            //set label
            IAxisValueFormatter iAxisValueFormatter = new IAxisValueFormatter() {
                @Override
                public String getFormattedValue(float value, AxisBase axis) {
                    axis.setLabelCount(mData.size());
                    return xLabel.get((int) value);
                }
            };
            mBarChart.getXAxis().setValueFormatter(iAxisValueFormatter);
            mBarChart.invalidate();
        }
        
        public void showBarChartNetIncome() {
            ArrayList<BarEntry> yEntrys = new ArrayList<>();
            final ArrayList<String> xLabel = new ArrayList<>();
            for (int i = 0; i < mData.size(); i++) {
                xLabel.add(mData.get(i).getDate().split("/")[0]);
                float value = Float.parseFloat(mData.get(i).getMoneyIncome()) -
                              Float.parseFloat(mData.get(i).getMoneyExpense());
                yEntrys.add(new BarEntry(i, value));
            }
            BarDataSet barDataSet = new BarDataSet(yEntrys, "");
            ArrayList<Integer> colors = new ArrayList<>();
            for (int i = 0; i < yEntrys.size(); i++) {
                if (yEntrys.get(i).getY() > 0) {
                    colors.add(Color.GREEN);
                } else {
                    colors.add(Color.RED);
                }
            }
            barDataSet.setColors(colors);
            //add legend to chart
            Legend legend = mBarChart.getLegend();
            legend.setForm(LegendForm.NONE);
            //create bar data object
            BarData barData = new BarData(barDataSet);
            mBarChart.setData(barData);
            //set label
            IAxisValueFormatter iAxisValueFormatter = new IAxisValueFormatter() {
                @Override
                public String getFormattedValue(float value, AxisBase axis) {
                    axis.setLabelCount(mData.size());
                    return xLabel.get((int) value);
                }
            };
            
            mBarChart.getXAxis().setValueFormatter(iAxisValueFormatter);
            mBarChart.invalidate();
        }
    }
    
    public class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        
        @BindView(R.id.linear_frame)
        LinearLayout linear_frame;
        @BindView(R.id.txt_month)
        TextView txt_month;
        @BindView(R.id.txt_year)
        TextView txt_year;
        @BindView(R.id.txt_money_expense)
        TextView txt_money_expense;
        @BindView(R.id.txt_money_income)
        TextView txt_money_income;
        @BindView(R.id.txt_net_income)
        TextView txt_net_income;
        
        public ItemViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            ButterKnife.bind(this, itemView);
        }
        
        public void bindView(ObjectByTime objectByTime) {
            String curSymbol = mWallet.getCurrencyUnit().getCurSymbol();
            if (IdCategory == FragmentByTime.ID_EXPENSE) {
                linear_frame.setGravity(Gravity.RIGHT);
                txt_net_income.setVisibility(View.GONE);
                txt_money_expense.setVisibility(View.VISIBLE);
                txt_money_income.setVisibility(View.GONE);
                String[] arr = objectByTime.getDate().split("/");
                txt_month.setText(
                          DateUtil.getMonthOfYear(mContext, (Integer.parseInt(arr[0])) - 1));
                txt_year.setText(arr[1]);
                
                if (!objectByTime.getMoneyExpense().equals("0.0")) {
                    txt_money_expense.setText("-" +
                                              NumberUtil.formatAmount(
                                                        objectByTime.getMoneyExpense(),
                                                        curSymbol));
                } else {
                    txt_money_expense
                              .setText(NumberUtil.formatAmount(objectByTime.getMoneyExpense(),
                                        curSymbol));
                }
                return;
            }
            if (IdCategory == FragmentByTime.ID_INCOME) {
                linear_frame.setGravity(Gravity.RIGHT);
                txt_net_income.setVisibility(View.GONE);
                txt_money_expense.setVisibility(View.GONE);
                txt_money_income.setVisibility(View.VISIBLE);
                String[] arr = objectByTime.getDate().split("/");
                txt_month.setText(
                          DateUtil.getMonthOfYear(mContext, (Integer.parseInt(arr[0])) - 1));
                txt_year.setText(arr[1]);
                txt_money_income.setText(NumberUtil.formatAmount(objectByTime.getMoneyIncome(),
                          curSymbol));
                return;
            }
            if (IdCategory == FragmentByTime.ID_NETINCOME) {
                linear_frame.setGravity(Gravity.CENTER);
                txt_net_income.setVisibility(View.VISIBLE);
                txt_money_expense.setVisibility(View.VISIBLE);
                txt_money_income.setVisibility(View.VISIBLE);
                String[] arr = objectByTime.getDate().split("/");
                txt_month.setText(
                          DateUtil.getMonthOfYear(mContext, (Integer.parseInt(arr[0])) - 1));
                txt_year.setText(arr[1]);
                
                if (!objectByTime.getMoneyExpense().equals("0.0")) {
                    txt_money_expense.setText("-" +
                                              NumberUtil.formatAmount(
                                                        objectByTime.getMoneyExpense(),
                                                        curSymbol));
                } else {
                    txt_money_expense
                              .setText(NumberUtil.formatAmount(objectByTime.getMoneyExpense(),
                                        curSymbol));
                }
                txt_money_income.setText(NumberUtil.formatAmount(objectByTime.getMoneyIncome(),
                          curSymbol));
                double moneyNetInCome = Double.parseDouble(objectByTime.getMoneyIncome()) -
                                        Double.parseDouble(objectByTime.getMoneyExpense());
                txt_net_income.setText(NumberUtil.formatAmount(moneyNetInCome + "", curSymbol));
                
                return;
            }
        }
        
        @Override
        public void onClick(View view) {
            if (mClickListener != null) {
                mClickListener.onItemClick(getItem(getAdapterPosition() - 1));
            }
        }
    }
    
    
}
