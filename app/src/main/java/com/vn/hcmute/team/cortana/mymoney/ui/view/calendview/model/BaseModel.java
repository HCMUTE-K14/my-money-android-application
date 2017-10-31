package com.vn.hcmute.team.cortana.mymoney.ui.view.calendview.model;

import java.util.List;

/**
 * Created by infamouSs on 10/31/17.
 */

public abstract class BaseModel {
    
    public static final int LIMIT_DATE = 15;
    public static final int LIMIT_WEEK = 15;
    public static final int LIMIT_MONTH = 15;
    
    public static final int TYPE_DATE = 0;
    public static final int TYPE_WEEK = 1;
    public static final int TYPE_MONTH = 2;
    public static String DEFAULT_PATTERN_TYPE_DATE = "%s %s %s";
    private static String DEFAULT_PATTERN = "%s/%s"; //Day/Month
    public static String DEFAULT_PATTERN_TYPE_WEEK = DEFAULT_PATTERN + " - " + DEFAULT_PATTERN;
    public static String DEFAULT_PATTERN_TYPE_MOTH = DEFAULT_PATTERN;
    
    protected int type;
    protected long startDate;
    protected long endDate;
    protected String patternDate;
    
    public BaseModel(int type) {
        this.type = type;
        
        if (type == TYPE_DATE) {
            this.patternDate = DEFAULT_PATTERN_TYPE_DATE;
        } else if (type == TYPE_WEEK) {
            this.patternDate = DEFAULT_PATTERN_TYPE_WEEK;
        } else if (type == TYPE_MONTH) {
            this.patternDate = DEFAULT_PATTERN_TYPE_MOTH;
        }
    }
    
    protected List<String> getData() {
        return buildData();
    }
    
    public abstract List<String> buildData();
    
    
    public int getType() {
        return type;
    }
    
    public void setType(int type) {
        this.type = type;
    }
    
    public long getStartDate() {
        return startDate;
    }
    
    public void setStartDate(long startDate) {
        this.startDate = startDate;
    }
    
    public long getEndDate() {
        return endDate;
    }
    
    public void setEndDate(long endDate) {
        this.endDate = endDate;
    }
}
