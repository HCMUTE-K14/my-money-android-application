package com.vn.hcmute.team.cortana.mymoney.ui.tools.calculator;

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
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.vn.hcmute.team.cortana.mymoney.R;
import com.vn.hcmute.team.cortana.mymoney.model.Currencies;
import com.vn.hcmute.team.cortana.mymoney.ui.currencies.CurrenciesActivity;
import com.vn.hcmute.team.cortana.mymoney.utils.NumberUtil;
import com.vn.hcmute.team.cortana.mymoney.utils.validate.TextUtil;

/**
 * Created by kunsubin on 10/9/2017.
 */

class DialogFragmentTransferCurrencies extends DialogFragment {
    
    @BindView(R.id.txt_money)
    TextView txt_money;
    @BindView(R.id.txt_currency_from)
    TextView txt_currency_from;
    @BindView(R.id.txt_currency_to)
    TextView txt_currency_to;
    @BindView(R.id.edit_money_from)
    EditText edit_money_from;
    
    DialogCallback mDialogCallback;
    private Currencies mCurrencies;
    private String mCodeCurrenciesTo;
    
    public DialogFragmentTransferCurrencies(String currencies) {
        this.mCodeCurrenciesTo = currencies;
    }
    
    public DialogFragment setCallBack(DialogCallback dialogCallback) {
        this.mDialogCallback = dialogCallback;
        return this;
    }
    
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return super.onCreateDialog(savedInstanceState);
    }
    
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
              Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_dialog_convert_currencies, container, false);
        ButterKnife.bind(this, view);
        return view;
    }
    
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        
        txt_currency_to.setText(mCodeCurrenciesTo);
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
        if (requestCode == 50) {
            if (resultCode == Activity.RESULT_OK) {
                mCurrencies = data.getParcelableExtra("currency");
                txt_currency_from.setText(mCurrencies.getCurCode());
            }
        }
        
    }
    
    /*Area OnClick*/
    @OnClick(R.id.linear_cancel)
    public void onClickCancel(View view) {
        getDialog().dismiss();
    }
    
    @OnClick(R.id.linear_transfer)
    public void onClickTransfer(View view) {
        if (!txt_money.getText().toString().trim().equals("")) {
            mDialogCallback.getResults(txt_money.getText().toString());
            getDialog().dismiss();
        } else {
            alertDiaglog(getActivity().getString(R.string.txt_money_empty));
        }
    }
    
    @OnClick(R.id.linear_currency_from)
    public void onClickSelectCurrencies(View view) {
        Intent intent = new Intent(getActivity(), CurrenciesActivity.class);
        getActivity().startActivityForResult(intent, 50);
    }
    
    @OnClick(R.id.image_sync)
    public void onClickImageSync(View view) {
        try {
            double moneyFrom = Double.parseDouble(edit_money_from.getText().toString().trim());
            double exchangeMoney = NumberUtil.exchangeMoney(getActivity(), String.valueOf(moneyFrom),
                      txt_currency_from.getText().toString(), txt_currency_to.getText().toString());
            
            txt_money.setText(TextUtil.doubleToString(exchangeMoney));
        } catch (Exception e) {
            alertDiaglog(getActivity().getString(R.string.txt_invalid_input));
        }
    }
    
    /*Area Function*/
    public void alertDiaglog(String message) {
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
