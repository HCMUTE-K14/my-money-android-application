package com.vn.hcmute.team.cortana.mymoney.ui.settings.restoredatabase;

import android.Manifest.permission;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.vn.hcmute.team.cortana.mymoney.ApplicationConfig;
import com.vn.hcmute.team.cortana.mymoney.R;
import com.vn.hcmute.team.cortana.mymoney.ui.base.EmptyAdapter;
import com.vn.hcmute.team.cortana.mymoney.ui.settings.restoredatabase.FilesAdapter.ItemClickListener;
import com.vn.hcmute.team.cortana.mymoney.utils.MyMoneyUtil;
import com.vn.hcmute.team.cortana.mymoney.utils.permission.PermissionCallBack;
import com.vn.hcmute.team.cortana.mymoney.utils.permission.PermissionHelper;
import com.vn.hcmute.team.cortana.mymoney.utils.permission.PermissionHelper.Permission;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.channels.FileChannel;

/**
 * Created by kunsubin on 1/4/2018.
 */

public class RestoreDatabaseActivity extends AppCompatActivity {

    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;
    private FilesAdapter mFilesAdapter;
    private EmptyAdapter mEmptyAdapter;
    private ProgressDialog mProgressDialog;
    private PermissionCallBack mPermissionCallBack = new PermissionCallBack() {
        @Override
        public void onPermissionGranted() {
            getData();
        }
        
        @Override
        public void onPermissionDenied() {
        
        }
    };
    
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restore_database);
        ButterKnife.bind(this);
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 1));
        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setCanceledOnTouchOutside(false);
        mProgressDialog.setMessage(this.getString(R.string.txt_progress));
        
        if (MyMoneyUtil.isMarshmallow() ||
            !MyMoneyUtil.isHasReadPermission(RestoreDatabaseActivity.this)) {
            requirePermission();
        } else {
            getData();
        }
    }
    
    
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
              @NonNull int[] grantResults) {
        PermissionHelper.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
    @OnClick(R.id.cancel_button)
    public void onClickCancel(View view){
        finish();
    }
    public void requirePermission() {
        if (PermissionHelper
                  .isHasPermission(this,
                            new String[]{permission.READ_EXTERNAL_STORAGE,
                                      permission.WRITE_EXTERNAL_STORAGE})) {
            getData();
        } else {
            if (PermissionHelper.shouldShowRequestPermissionRationale(this,
                      PermissionHelper.Permission.READ_EXTERNAL_STORAGE) &&
                PermissionHelper.shouldShowRequestPermissionRationale(this,
                          Permission.WRITE_EXTERNAL_STORAGE)) {
                
                Snackbar.make(this.findViewById(android.R.id.content),
                          getString(R.string.message_permission_read_write_request),
                          Snackbar.LENGTH_INDEFINITE)
                          .setAction(getString(R.string.action_ok),
                                    new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            PermissionHelper
                                                      .askForPermission(
                                                                RestoreDatabaseActivity.this,
                                                                new String[]{
                                                                          permission.READ_EXTERNAL_STORAGE,
                                                                          permission.WRITE_EXTERNAL_STORAGE},
                                                                mPermissionCallBack);
                                        }
                                    })
                          .show();
            } else {
                PermissionHelper
                          .askForPermission(RestoreDatabaseActivity.this,
                                    new String[]{permission.READ_EXTERNAL_STORAGE,
                                              permission.WRITE_EXTERNAL_STORAGE},
                                    mPermissionCallBack);
            }
        }
    }
    FilesAdapter.ItemClickListener mItemClickListener=new ItemClickListener() {
        @Override
        public void onItemClick(File file) {
            alertDialogRestoreDatabase(file);
        }
    };
    public void getData() {
        String dirPath= ApplicationConfig.STORAGE_DIRECTION+"/backup";
        File dir = new File(dirPath);
        File[] files = dir.listFiles();
        if(files!=null&&files.length>0){
            mFilesAdapter=new FilesAdapter(this,files);
            mFilesAdapter.setItemClickListener(mItemClickListener);
            mRecyclerView.setAdapter(mFilesAdapter);
        }else {
            mEmptyAdapter=new EmptyAdapter(this,getString(R.string.txt_no_file));
            mRecyclerView.setAdapter(mEmptyAdapter);
        }
      

    }
    public void alertDialogRestoreDatabase( final File file){
        
        final AlertDialog.Builder adb = new AlertDialog.Builder(this);
        
        adb.setTitle(getString(R.string.txt_alert_restore_database));
    
        adb.setIcon(android.R.drawable.ic_dialog_alert);
        
        
        adb.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                
                restoreDatabase(file);
                
            } });
    
    
        adb.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            } });
        
        adb.show();
    }
    public void restoreDatabase(File file){
        mProgressDialog.show();
        try {
            File sd = Environment.getExternalStorageDirectory();
            if (sd.canWrite()) {
                String currentDBPath = ApplicationConfig.PATH_DATABASE_APP;
                File currentDB = new File(currentDBPath);
                File backupDB = file;
            
                if (currentDB.exists()) {
                    FileChannel src = new FileInputStream(backupDB).getChannel();
                    FileChannel dst = new FileOutputStream(currentDB).getChannel();
                    dst.transferFrom(src, 0, src.size());
                    src.close();
                    dst.close();
                    alertDialog(getString(R.string.txt_success_restore),true);
                }
            }
            mProgressDialog.dismiss();
        } catch (Exception e) {
            mProgressDialog.dismiss();
            alertDialog(getString(R.string.txt_fail_restore),false);
        }
    }
    public void alertDialog(String message, final boolean flag) {
        AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
        builder1.setMessage(message);
        builder1.setCancelable(true);
        builder1.setPositiveButton(
                  getString(R.string.txt_ok),
                  new DialogInterface.OnClickListener() {
                      public void onClick(DialogInterface dialog, int id) {
                          if(flag){
                              Intent i = getBaseContext().getPackageManager()
                                        .getLaunchIntentForPackage( getBaseContext().getPackageName() );
                              i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                              startActivity(i);
                          }else {
                              dialog.cancel();
                          }
                      }
                  });
        AlertDialog alert11 = builder1.create();
        alert11.show();
    }
}
