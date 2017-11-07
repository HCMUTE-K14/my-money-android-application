package com.vn.hcmute.team.cortana.mymoney.ui.statistics;

import android.graphics.Color;
import android.view.View;
import butterknife.BindView;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.Legend.LegendPosition;
import com.github.mikephil.charting.components.XAxis.XAxisPosition;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.vn.hcmute.team.cortana.mymoney.R;
import com.vn.hcmute.team.cortana.mymoney.ui.base.BaseActivity;
import java.util.ArrayList;

/**
 * Created by kunsubin on 11/6/2017.
 */

public class BarCharActivity extends BaseActivity {
    
    @BindView(R.id.bar_chart)
    BarChart mBarChart;
    
    
    @Override
    public int getLayoutId() {
        return R.layout.item_header_bar_chart;
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
    protected void initialize() {
        initPieChart();
        showBar();
    }
    
    private void showBar() {
        ArrayList<BarEntry> yEntrys = new ArrayList<>();
        yEntrys.add(new BarEntry(0, 16));
        yEntrys.add(new BarEntry(1, -5));
        yEntrys.add(new BarEntry(2, 18));
        yEntrys.add(new BarEntry(3, 12));
        
        final ArrayList<String> xLabel = new ArrayList<>();
        xLabel.add("1");
        xLabel.add("2");
        xLabel.add("3");
        xLabel.add("4");
        
        BarDataSet barDataSet = new BarDataSet(yEntrys, "bom");
        
        barDataSet.setColor(Color.GREEN);
        
        //add legend to chart
        Legend legend = mBarChart.getLegend();
        legend.setForm(Legend.LegendForm.CIRCLE);
        legend.setPosition(LegendPosition.BELOW_CHART_LEFT);
        
        //create pie data object
        BarData barData = new BarData(barDataSet);
        mBarChart.setData(barData);
        //set label
        mBarChart.getXAxis().setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return xLabel.get((int) value);
            }
        });
        mBarChart.invalidate();
        
        
    }
    
    public void initPieChart() {
        mBarChart.setDescription(null);
        mBarChart.setFitBars(false);
        mBarChart.setDoubleTapToZoomEnabled(false);
        
        mBarChart.getXAxis().setDrawGridLines(false);
        mBarChart.getAxisRight().setEnabled(false);
        mBarChart.getAxisLeft().setDrawGridLines(false);
        mBarChart.getXAxis().setPosition(XAxisPosition.BOTTOM);
        
    }
}
