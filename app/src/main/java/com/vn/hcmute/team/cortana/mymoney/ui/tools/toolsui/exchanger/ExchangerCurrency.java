package com.vn.hcmute.team.cortana.mymoney.ui.tools.toolsui.exchanger;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.vn.hcmute.team.cortana.mymoney.R;
import com.vn.hcmute.team.cortana.mymoney.model.Currencies;
import com.vn.hcmute.team.cortana.mymoney.ui.currencies.CurrenciesActivity;
import com.vn.hcmute.team.cortana.mymoney.utils.DrawableUtil;
import com.vn.hcmute.team.cortana.mymoney.utils.GlideImageLoader;
import com.vn.hcmute.team.cortana.mymoney.utils.NumberUtil;
import com.vn.hcmute.team.cortana.mymoney.utils.TextUtil;

/**
 * Created by kunsubin on 12/29/2017.
 */

public class ExchangerCurrency extends DialogFragment {
    
    @BindView(R.id.edit_money_from)
    EditText edit_money_from;
    @BindView(R.id.txt_currency_from)
    TextView txt_currency_from;
    @BindView(R.id.image_from)
    ImageView image_from;
    @BindView(R.id.txt_currency_to)
    TextView txt_currency_to;
    @BindView(R.id.image_to)
    ImageView image_to;
    @BindView(R.id.txt_money)
    TextView txt_money;
    
    private Currencies mCurrenciesFrom;
    private Currencies mCurrenciesTo;
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return super.onCreateDialog(savedInstanceState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
              Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_exchanger_currency, container, false);
        ButterKnife.bind(this, view);
        return view;
    }
    
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    
        setDefaultCurrencies();
        showCurrencies();
        edit_money_from.setRawInputType(Configuration.KEYBOARD_12KEY);
    }
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    
    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 150) {
            if (resultCode == Activity.RESULT_OK) {
                mCurrenciesFrom = data.getParcelableExtra("currency");
                showCurrenciesFrom();
            }
        }
        if (requestCode == 151) {
            if (resultCode == Activity.RESULT_OK) {
                mCurrenciesTo = data.getParcelableExtra("currency");
                showCurrenciesTo();
            }
        }
    }
    
    @OnClick(R.id.linear_exchange)
    public void onClickImageSync(View view){
        if(edit_money_from.getText().toString().trim().equals("")){
            alertDialog(getString(R.string.txt_enter_money));
        }
        if(mCurrenciesFrom.getCurCode().equals(mCurrenciesTo.getCurCode())){
            txt_money.setText(edit_money_from.getText());
            return;
        }
        try {
            double moneyFrom = Double.parseDouble(edit_money_from.getText().toString().trim());
            double exchangeMoney = NumberUtil
                      .exchangeMoney(getActivity(), String.valueOf(moneyFrom),
                                mCurrenciesFrom.getCurCode(),
                                mCurrenciesTo.getCurCode());
            txt_money.setText(TextUtil.doubleToString(exchangeMoney));
        } catch (Exception e) {
            alertDialog(getActivity().getString(R.string.txt_invalid_input));
        }
    }
    @OnClick(R.id.linear_transfer)
    public void onClickOK(View view){
        getDialog().dismiss();
    }
    @OnClick(R.id.linear_currency_from)
    public void onClickLinearFrom(View view){
        Intent intent=new Intent(getActivity(), CurrenciesActivity.class);
        getActivity().startActivityForResult(intent,150);
    }
    @OnClick(R.id.linear_currency_to)
    public void onClickTo(View view){
        Intent intent=new Intent(getActivity(), CurrenciesActivity.class);
        getActivity().startActivityForResult(intent,151);
    }
    @OnClick(R.id.img_switch)
    public void onClickSwitch(View view){
        swapCurrencies();
        showCurrencies();
    }
    private void setDefaultCurrencies(){
        mCurrenciesFrom=new Currencies();
        mCurrenciesFrom.setCurCode("USD");
        mCurrenciesFrom.setCurDisplayType("0");
        mCurrenciesFrom.setCurId("1");
        mCurrenciesFrom.setCurName("United States Dollar");
        mCurrenciesFrom.setCurSymbol("$");
        
        mCurrenciesTo=new Currencies();
        mCurrenciesTo.setCurCode("VND");
        mCurrenciesTo.setCurDisplayType("1");
        mCurrenciesTo.setCurId("4");
        mCurrenciesTo.setCurName("Việt Nam Đồng");
        mCurrenciesTo.setCurSymbol("₫");
    }
    private void showCurrencies(){
        showCurrenciesFrom();
        showCurrenciesTo();
    }
    private void showCurrenciesFrom(){
        txt_currency_from.setText(mCurrenciesFrom.getCurCode());
        String iconStringFrom = "ic_currency_" + mCurrenciesFrom.getCurCode().toLowerCase();
        GlideImageLoader.load(getActivity(), DrawableUtil.getDrawable(getActivity(), iconStringFrom),
                  image_from);
    }
    private void showCurrenciesTo(){
        txt_currency_to.setText(mCurrenciesTo.getCurCode());
        String iconStringTo = "ic_currency_" + mCurrenciesTo.getCurCode().toLowerCase();
        GlideImageLoader.load(getActivity(), DrawableUtil.getDrawable(getActivity(), iconStringTo),
                  image_to);
    }
    private void swapCurrencies(){
        Currencies currencies=mCurrenciesFrom;
        mCurrenciesFrom=mCurrenciesTo;
        mCurrenciesTo=currencies;
    }
    public void alertDialog(String message) {
        AlertDialog.Builder builder1 = new AlertDialog.Builder(getActivity());
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
   
}
