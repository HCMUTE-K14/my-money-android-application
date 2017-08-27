package com.vn.hcmute.team.cortana.mymoney.ui.transaction;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.vn.hcmute.team.cortana.mymoney.R;
import com.vn.hcmute.team.cortana.mymoney.model.Person;
import com.vn.hcmute.team.cortana.mymoney.ui.person.PersonActivity;
import com.vn.hcmute.team.cortana.mymoney.utils.logger.MyLogger;
import java.util.List;

/**
 * Created by infamouSs on 8/26/17.
 */

public class Transaction extends AppCompatActivity {
    
    @BindView(R.id.button)
    Button mButton;
    
    public Transaction() {
        
    }
    
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction);
        ButterKnife.bind(this);
    }
    
    @OnClick(R.id.button)
    public void onClick() {
        Intent intent = new Intent(this, PersonActivity.class);
        
        startActivityForResult(intent, 1);
    }
    
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1 && resultCode == RESULT_OK && data != null) {
            List<Person> list = data
                      .getParcelableArrayListExtra(PersonActivity.EXTRA_SELECTED_PERSON);
            
            MyLogger.d(list.size());
        }
    }
}
