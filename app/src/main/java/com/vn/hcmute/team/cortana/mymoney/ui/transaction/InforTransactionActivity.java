package com.vn.hcmute.team.cortana.mymoney.ui.transaction;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import com.vn.hcmute.team.cortana.mymoney.R;

/**
 * Created by infamouSs on 10/20/17.
 */

public class InforTransactionActivity extends BaseInfoTransactionActivity {
    
    public static final String TAG = InforTransactionActivity.class.getSimpleName();
    
    
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        
    }
    
    @Override
    protected void initializeView() {
        super.initializeView();
        
        if (!mTransaction.getPerson().isEmpty()) {
            View listPersonView = LayoutInflater.from(this)
                      .inflate(R.layout.layout_list_person_trans, null, false);
            ((TextView) listPersonView.findViewById(R.id.txt_type)).setText(R.string.txt_with);
            mViewContainer.addView(listPersonView);
            
            RecyclerView recyclerView = (RecyclerView) listPersonView
                      .findViewById(R.id.recycler_view);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            PersonTransactionAdapter personTransactionAdapter = new PersonTransactionAdapter();
            personTransactionAdapter.setData(mTransaction.getPerson());
            
            recyclerView.setAdapter(personTransactionAdapter);
            
        }
        
    }
}
