package com.vn.hcmute.team.cortana.mymoney.ui.person;

import com.vn.hcmute.team.cortana.mymoney.model.Person;

/**
 * Created by infamouSs on 8/25/17.
 */

public interface OnPersonClickListener {
    
    boolean onPersonClick(int position, boolean isSelected);
    
    void onRemoveClick(int position, Person person);
    
    void onEditClick(int position,Person person);
}
