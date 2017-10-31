package com.vn.hcmute.team.cortana.mymoney.ui.view.calendview.model;

import static org.junit.Assert.assertEquals;

import java.util.Collections;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by infamouSs on 10/31/17.
 */
public class DateModelTest {
    
    DateModel mDateModel;
    
    @Before
    public void init() {
        mDateModel = new DateModel();
    }
    
    
    @Test
    public void equalsEmpty() {
        assertEquals(Collections.EMPTY_LIST, mDateModel.getData());
    }
}