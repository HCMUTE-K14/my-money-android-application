package com.vn.hcmute.team.cortana.mymoney.ui.person;

import com.vn.hcmute.team.cortana.mymoney.model.Person;

/**
 * Created by infamouSs on 8/25/17.
 */

public interface OnPersonClickListener {
    boolean onPersonClick(int position,boolean isSelected);
    
    void onLongPersonClick(int position,Person person);
}
