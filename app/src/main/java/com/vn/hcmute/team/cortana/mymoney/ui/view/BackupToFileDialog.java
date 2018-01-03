package com.vn.hcmute.team.cortana.mymoney.ui.view;

import android.Manifest;
import android.app.DialogFragment;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.vn.hcmute.team.cortana.mymoney.ApplicationConfig;
import com.vn.hcmute.team.cortana.mymoney.R;
import com.vn.hcmute.team.cortana.mymoney.utils.DatabaseUtil;
import com.vn.hcmute.team.cortana.mymoney.utils.DatabaseUtil.CallBack;
import com.vn.hcmute.team.cortana.mymoney.utils.DateUtil;
import com.vn.hcmute.team.cortana.mymoney.utils.MyMoneyUtil;
import com.vn.hcmute.team.cortana.mymoney.utils.permission.PermissionCallBack;
import com.vn.hcmute.team.cortana.mymoney.utils.permission.PermissionHelper;

/**
 * Created by infamouSs on 1/2/18.
 */

public class BackupToFileDialog extends DialogFragment {
    
    private EditText mEditTextFileName;
    private ProgressDialog mProgressDialog;
    private PermissionCallBack mPermissionCallBack = new PermissionCallBack() {
        @Override
        public void onPermissionGranted() {
            backup();
        }
        
        @Override
        public void onPermissionDenied() {
            BackupToFileDialog.this.dismiss();
        }
    };
    
    public static BackupToFileDialog newInstance() {
        
        return new BackupToFileDialog();
    }
    
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
              Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.layout_dialog_back_up_to_file, container, false);
        mProgressDialog = new ProgressDialog(this.getActivity());
        mProgressDialog.setMessage(getString(R.string.backup_file_name));
        mProgressDialog.setCanceledOnTouchOutside(false);
        
        mEditTextFileName = (EditText) v.findViewById(R.id.filename);
        Button mButtonOK = (Button) v.findViewById(R.id.btn_ok);
        Button mButtonCancel = (Button) v.findViewById(R.id.btn_cancel);
        
        mEditTextFileName.setText("Money_backup_" + DateUtil.dateStringForFile());
        
        mButtonOK.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (MyMoneyUtil.isMarshmallow() ||
                    !MyMoneyUtil.isHasWritePermission(BackupToFileDialog.this.getActivity())) {
                    requirePermission();
                } else {
                    backup();
                }
            }
        });
        
        mButtonCancel.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                BackupToFileDialog.this.dismiss();
            }
        });
        
        return v;
    }
    
    private void backup() {
        mProgressDialog.show();
        String fileName = mEditTextFileName.getText().toString() + ".db";
        DatabaseUtil.backup(this.getActivity(), new CallBack() {
            @Override
            public void onSuccess() {
                mProgressDialog.dismiss();
                Toast.makeText(getActivity(), getString(R.string.message_backup_successfully,
                          ApplicationConfig.STORAGE_DIRECTION + "/backup"),
                          Toast.LENGTH_SHORT).show();
                dismiss();
            }
            
            @Override
            public void onError(Exception ex) {
                mProgressDialog.dismiss();
                Toast.makeText(getActivity(), R.string.message_backup_failed, Toast.LENGTH_SHORT)
                          .show();
                dismiss();
            }
        }, fileName);
    }
    
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
              @NonNull int[] grantResults) {
        PermissionHelper.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
    
    public void requirePermission() {
        if (PermissionHelper
                  .isHasPermission(this.getActivity(),
                            Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            backup();
        } else {
            if (PermissionHelper.shouldShowRequestPermissionRationale(this.getActivity(),
                      PermissionHelper.Permission.WRITE_EXTERNAL_STORAGE)) {
                
                Snackbar.make(this.getActivity().findViewById(android.R.id.content),
                          getString(R.string.message_permission_read_request),
                          Snackbar.LENGTH_INDEFINITE)
                          .setAction(getString(R.string.action_ok),
                                    new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            PermissionHelper
                                                      .askForPermission(
                                                                BackupToFileDialog.this
                                                                          .getActivity(),
                                                                PermissionHelper.Permission.WRITE_EXTERNAL_STORAGE,
                                                                mPermissionCallBack);
                                        }
                                    })
                          .show();
            } else {
                PermissionHelper
                          .askForPermission(BackupToFileDialog.this
                                              .getActivity(),
                                    PermissionHelper.Permission.WRITE_EXTERNAL_STORAGE,
                                    mPermissionCallBack);
            }
        }
    }
    
    
}
