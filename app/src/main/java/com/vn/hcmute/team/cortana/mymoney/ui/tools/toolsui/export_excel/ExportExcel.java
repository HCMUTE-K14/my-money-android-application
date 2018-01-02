package com.vn.hcmute.team.cortana.mymoney.ui.tools.toolsui.export_excel;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.borax12.materialdaterangepicker.date.DatePickerDialog;
import com.borax12.materialdaterangepicker.date.DatePickerDialog.OnDateSetListener;
import com.vn.hcmute.team.cortana.mymoney.MyMoneyApplication;
import com.vn.hcmute.team.cortana.mymoney.R;
import com.vn.hcmute.team.cortana.mymoney.data.DataRepository;
import com.vn.hcmute.team.cortana.mymoney.model.Person;
import com.vn.hcmute.team.cortana.mymoney.model.Transaction;
import com.vn.hcmute.team.cortana.mymoney.utils.DateUtil;
import com.vn.hcmute.team.cortana.mymoney.utils.MyMoneyUtil;
import com.vn.hcmute.team.cortana.mymoney.utils.permission.PermissionCallBack;
import com.vn.hcmute.team.cortana.mymoney.utils.permission.PermissionHelper;
import com.vn.hcmute.team.cortana.mymoney.utils.permission.PermissionHelper.Permission;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.format.Alignment;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.Colour;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

/**
 * Created by kunsubin on 12/29/2017.
 */

public class ExportExcel extends DialogFragment implements OnDateSetListener {
    
    @BindView(R.id.txt_range_date)
    TextView mTextViewRangeDate;
    private int year;
    private int monthOfYear;
    private int dayOfMonth;
    private int yearEnd;
    private int monthOfYearEnd;
    private int dayOfMonthEnd;
    DataRepository mDataRepository;
    private ProgressDialog mProgressDialog;
    
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return super.onCreateDialog(savedInstanceState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
              Bundle savedInstanceState) {
        mDataRepository=((MyMoneyApplication) this.getActivity().getApplication()).getAppComponent().dataRepository();
        View view = inflater.inflate(R.layout.layout_export_excel, container, false);
        ButterKnife.bind(this, view);
        return view;
    }
    
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mProgressDialog = new ProgressDialog(getActivity());
        mProgressDialog.setCanceledOnTouchOutside(false);
        mProgressDialog.setMessage(this.getString(R.string.txt_progress));
    }
    
    @OnClick(R.id.linear_select_date)
    public void onClickSelectDate(View view){
        Calendar now = Calendar.getInstance();
        DatePickerDialog dpd = DatePickerDialog.newInstance(
                  ExportExcel.this,
                  now.get(Calendar.YEAR),
                  now.get(Calendar.MONTH),
                  now.get(Calendar.DAY_OF_MONTH)
        );
        dpd.show(getActivity().getFragmentManager(), "Datepickerdialog");
    }
    @OnClick(R.id.linear_cancel)
    public void onClickCancel(View view){
        getDialog().dismiss();
    }
    @OnClick(R.id.linear_export_excel)
    public void onClickExportExcel(View view){
        if(mTextViewRangeDate.getText().equals("")){
            alertDialog(getActivity().getString(R.string.select_date));
            return;
        }
        if (MyMoneyUtil.isMarshmallow() || !MyMoneyUtil.isHasWritePermission(getActivity())) {
            requirePermission();
        } else {
            getData();
        }
       
    }
    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth,
              int yearEnd, int monthOfYearEnd, int dayOfMonthEnd) {
        this.year=year;
        this.monthOfYear=monthOfYear+1;
        this.dayOfMonth=dayOfMonth;
        this.yearEnd=yearEnd;
        this.monthOfYearEnd=monthOfYearEnd+1;
        this.dayOfMonthEnd=dayOfMonthEnd;
    
        mTextViewRangeDate
                  .setText(this.dayOfMonth + "/" + this.monthOfYear + "/" + this.year + " - " +
                           this.dayOfMonthEnd +
                           "/" + this.monthOfYearEnd + "/" +
                           this.yearEnd);
    }
    private PermissionCallBack mPermissionCallBack = new PermissionCallBack() {
        @Override
        public void onPermissionGranted() {
            getData();
        }
        
        @Override
        public void onPermissionDenied() {
            getDialog().dismiss();
        }
    };
    public void requirePermission() {
        if (PermissionHelper
                  .isHasPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
           getData();
        } else {
            if (PermissionHelper.shouldShowRequestPermissionRationale(getActivity(),
                      Permission.WRITE_EXTERNAL_STORAGE)) {
                
                Snackbar.make(getActivity().findViewById(android.R.id.content),
                          getString(R.string.message_permission_read_request),
                          Snackbar.LENGTH_INDEFINITE)
                          .setAction(getString(R.string.action_ok),
                                    new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            PermissionHelper
                                                      .askForPermission(
                                                                getActivity(),
                                                                Permission.WRITE_EXTERNAL_STORAGE,
                                                                mPermissionCallBack);
                                        }
                                    })
                          .show();
            } else {
                PermissionHelper
                          .askForPermission(getActivity(),
                                    Permission.WRITE_EXTERNAL_STORAGE,
                                    mPermissionCallBack);
            }
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
              @NonNull int[] grantResults) {
        PermissionHelper.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
    public void exportExcel(List<Transaction> transactionList) {
        //filter data
        List<Transaction> transactionsExpense=new ArrayList<>();
        List<Transaction> transactionsIncome=new ArrayList<>();
        double sumExpense=0;
        double sumIncome=0;
        for(Transaction transaction:transactionList){
            if(transaction.getType().equals("expense")){
                transactionsExpense.add(transaction);
            }else {
                transactionsIncome.add(transaction);
            }
        }
        final String fileName = "MyMoney_" + DateUtil.dateStringForFile()+".xls";
        //Saving file in external storage
        File sdCard = Environment.getExternalStorageDirectory();
        File directory = new File(sdCard.getAbsolutePath() + "/MyMoney");
        //create directory if not exist
        if (!directory.isDirectory()) {
            directory.mkdirs();
        }
        File file = new File(directory, fileName);
        WorkbookSettings wbSettings = new WorkbookSettings();
        wbSettings.setLocale(new Locale("en", "EN"));
        WritableWorkbook workbook;
        try {
            workbook = Workbook.createWorkbook(file, wbSettings);
            //Excel sheet name. 0 represents first sheet
            WritableSheet sheet = workbook.createSheet("MyMoney", 0);
            int i=0;
            try {
    
                // Create cell font and format
                WritableFont cellFont = new WritableFont(WritableFont.TIMES, 12);
                cellFont.setColour(Colour.BLACK);
    
                WritableCellFormat cellFormat = new WritableCellFormat(cellFont);
                cellFormat.setBackground(Colour.YELLOW);
                cellFormat.setBorder(Border.ALL, BorderLineStyle.THIN);
    
                WritableCellFormat  cellFormatHeader=new WritableCellFormat();
                cellFormatHeader.setAlignment(Alignment.CENTRE);
                sheet.mergeCells(0, i, 6, i);
                Label label = new Label(0, i,
                          "Statistics",cellFormatHeader);
                sheet.addCell(label);
                
                i++;
                
               sheet.setColumnView(0,20);
                sheet.addCell(new Label(0, i, "Date",cellFormat));
               sheet.setColumnView(1,30);
                sheet.addCell(new Label(1, i, "Name",cellFormat));
               sheet.setColumnView(2,20);
                sheet.addCell(new Label(2, i, "Money",cellFormat));
               sheet.setColumnView(3,15);
                sheet.addCell(new Label(3, i, "Type",cellFormat));
                sheet.setColumnView(4,30);
                sheet.addCell(new Label(4, i, "Wallet",cellFormat));
                sheet.setColumnView(5,40);
                sheet.addCell(new Label(5, i, "Person",cellFormat));
                sheet.setColumnView(6,70);
                sheet.addCell(new Label(6, i, "Note",cellFormat));
                
                i++;
                for (Transaction transaction:transactionsExpense){
                    sheet.addCell(new Label(0, i, DateUtil.convertTimeMillisToDate(transaction.getDate_created())));
                    sheet.addCell(new Label(1, i, transaction.getCategory().getName()));
                    sheet.addCell(new Label(2, i,"-"+ transaction.getAmount()));
                    sheet.addCell(new Label(3, i,transaction.getType()));
                    sheet.addCell(new Label(4, i, transaction.getWallet().getWalletName()));
                    if(transaction.getPerson()!=null&&!transaction.getPerson().isEmpty()){
                        StringBuilder builder=new StringBuilder();
                        for (Person person:transaction.getPerson()){
                            builder.append(person.getName());
                            builder.append(";");
                        }
                        sheet.addCell(new Label(5, i, builder.toString()));
                    }else {
                        sheet.addCell(new Label(5, i,""));
                    }
                    sheet.addCell(new Label(6, i, transaction.getNote()));
                    
                    sumExpense+=Double.parseDouble(transaction.getAmount());
                    
                    i++;
                }
                for (Transaction transaction:transactionsIncome){
                    sheet.addCell(new Label(0, i, DateUtil.convertTimeMillisToDate(transaction.getDate_created())));
                    sheet.addCell(new Label(1, i, transaction.getCategory().getName()));
                    sheet.addCell(new Label(2, i, transaction.getAmount()));
                    sheet.addCell(new Label(3, i, transaction.getType()));
                    sheet.addCell(new Label(4, i, transaction.getWallet().getWalletName()));
                    if(transaction.getPerson()!=null&&!transaction.getPerson().isEmpty()){
                        StringBuilder builder=new StringBuilder();
                        for (Person person:transaction.getPerson()){
                            builder.append(person.getName());
                            builder.append(";");
                        }
                        sheet.addCell(new Label(5, i, builder.toString()));
                    }else {
                        sheet.addCell(new Label(5, i,""));
                    }
                    sheet.addCell(new Label(6, i, transaction.getNote()));
    
                    sumIncome+=Double.parseDouble(transaction.getAmount());
    
                    i++;
                }
                i++;
                sheet.addCell(new Label(1, i,"Expense: "+"-"+sumExpense));
                i++;
                sheet.addCell(new Label(1, i,"Income: "+sumIncome));
                
                
            } catch (RowsExceededException e) {
                e.printStackTrace();
            } catch (WriteException e) {
                e.printStackTrace();
            }
            workbook.write();
            try {
                workbook.close();
            } catch (WriteException e) {
                e.printStackTrace();
            }
            mProgressDialog.dismiss();
            alertDialog(getActivity().getString(R.string.txt_export_success));
            getDialog().dismiss();
        } catch (IOException e) {
            e.printStackTrace();
            mProgressDialog.dismiss();
        }
    }
    public void getData(){
        mProgressDialog.show();
        String startDate=String.valueOf(DateUtil.getLongAsDate(dayOfMonth,monthOfYear,year));
        String endDate=String.valueOf(DateUtil.getLongAsDate(dayOfMonthEnd,monthOfYearEnd,yearEnd));
        mDataRepository.getLocalTransactionByTime("",startDate,endDate,"")
                  .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).singleOrError().subscribe(
                  new Consumer<List<Transaction>>() {
                      @Override
                      public void accept(List<Transaction> transactions) throws Exception {
                            try{
                                if(transactions!=null&&!transactions.isEmpty()){
                                    exportExcel(transactions);
                                }else {
                                    Toast.makeText(getActivity(),getActivity().getString(R.string.txt_empty_transaction),Toast.LENGTH_LONG).show();
                                }
                                
                            }catch (Exception ex){
                                Toast.makeText(getActivity(),getActivity().getString(R.string.txt_error_excel),Toast.LENGTH_LONG).show();
                            }
                      }
                  });
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
