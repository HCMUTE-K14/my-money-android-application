package com.vn.hcmute.team.cortana.mymoney.ui.tools.toolsui;

import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import butterknife.BindView;
import com.vn.hcmute.team.cortana.mymoney.R;
import com.vn.hcmute.team.cortana.mymoney.ui.base.BaseFragment;
import com.vn.hcmute.team.cortana.mymoney.ui.tools.calculator.CalculatorActivity;
import com.vn.hcmute.team.cortana.mymoney.ui.tools.toolsui.ToolsAdapter.ItemClickListener;
import com.vn.hcmute.team.cortana.mymoney.ui.tools.toolsui.exchanger.ExchangerCurrency;
import com.vn.hcmute.team.cortana.mymoney.ui.tools.toolsui.export_excel.ExportExcel;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by kunsubin on 12/28/2017.
 */

public class ToolsMainFragment extends BaseFragment {
    
    @BindView(R.id.recycler_view_tools)
    RecyclerView mRecyclerView;
    List<ItemTool> mItemTools;
    private ToolsAdapter mToolsAdapter;
    private ExchangerCurrency mExchangerCurrency;
    private ExportExcel mExportExcel;
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_main_tools;
    }
    
    @Override
    protected void initializeDagger() {
    
    }
    
    @Override
    protected void initializePresenter() {
    
    }
    
    @Override
    protected void initialize() {
       init();
       showListTools();
    }
    @Override
    protected void initializeActionBar(View rootView) {
        getActivity().setTitle(getString(R.string.txt_navigation_tools));
    }
    
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode==150||requestCode==151){
            mExchangerCurrency.onActivityResult(requestCode,resultCode,data);
        }
    }
    
    private ToolsAdapter.ItemClickListener mItemClickListener=new ItemClickListener() {
        @Override
        public void onItemClick(ItemTool itemTool) {
            switch (itemTool.getType()){
                case Type.CALCULATOR:
                    Intent intent=new Intent(getActivity(), CalculatorActivity.class);
                    intent.putExtra("flag",true);
                    startActivity(intent);
                    break;
                case Type.CONVERT_CURRENCY:
                    mExchangerCurrency=new ExchangerCurrency();
                    mExchangerCurrency.show(getFragmentManager(),"");
                    break;
                case Type.EXPORT_EXCEL:
                    mExportExcel=new ExportExcel();
                    mExportExcel.show(getFragmentManager(),"");
                    break;
                default:
                    break;
            }
        }
    };
    private void showListTools() {
        mToolsAdapter=new ToolsAdapter(getActivity(),mItemTools);
        mToolsAdapter.setItemClickListener(mItemClickListener);
        mRecyclerView.setAdapter(mToolsAdapter);
}
    private void init(){
        mRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 1));
        mItemTools=this.getListItemTools();
    }
    //setup tool
    public List<ItemTool> getListItemTools(){
        
        List<ItemTool> itemTools=new ArrayList<>();
        
        //calculator
        ItemTool itemCalculator=new ItemTool();
        itemCalculator.setType(Type.CALCULATOR);
        itemCalculator.setImage("calculator_icon");
        itemCalculator.setName(this.getString(R.string.txt_calculator));
        itemTools.add(itemCalculator);
        //convert currency
        ItemTool itemCurrency=new ItemTool();
        itemCurrency.setType(Type.CONVERT_CURRENCY);
        itemCurrency.setImage("ic_exchanger");
        itemCurrency.setName(this.getString(R.string.txt_convert_currency));
        itemTools.add(itemCurrency);
        //export pdf
        ItemTool itemPdf=new ItemTool();
        itemPdf.setType(Type.EXPORT_EXCEL);
        itemPdf.setImage("ic_export_excel");
        itemPdf.setName(this.getString(R.string.txt_export_excel));
        itemTools.add(itemPdf);
        
        return itemTools;
    }
}
