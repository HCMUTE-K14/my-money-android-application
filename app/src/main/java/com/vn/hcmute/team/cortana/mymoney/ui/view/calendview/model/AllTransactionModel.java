package com.vn.hcmute.team.cortana.mymoney.ui.view.calendview.model;

import android.content.Context;
import com.vn.hcmute.team.cortana.mymoney.R;
import java.util.Calendar;

/**
 * Created by infamouSs on 11/3/17.
 */

public class AllTransactionModel extends BaseModel {
    
    public AllTransactionModel(Context context) {
        super(context, BaseModel.TYPE_ALL_TRANS);
        this.startDate = 0L;
        final int day_of_month = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
        final int month = Calendar.getInstance().get(Calendar.MONTH);
        final int year = Calendar.getInstance().get(Calendar.YEAR);
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day_of_month);
        
        this.endDate = calendar.getTimeInMillis();
    }
    
    @Override
    public void buildData() {
        this.data.put(mContext.getString(R.string.txt_all_trans),
                  this.startDate + "-" + this.endDate);
    }
}
