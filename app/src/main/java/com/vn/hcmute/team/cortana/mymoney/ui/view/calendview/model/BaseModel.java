package com.vn.hcmute.team.cortana.mymoney.ui.view.calendview.model;

import android.content.Context;
import java.util.LinkedHashMap;
import java.util.Map;

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
    public static final int TYPE_CUSTOM = 3;
    public static final int TYPE_ALL_TRANS = 4;
    
    public static String DEFAULT_PATTERN = "%s/%s"; //Day/Month
    
    public static String DEFAULT_PATTERN_TYPE_DATE = "%s %s %s";
    public static String DEFAULT_PATTERN_TYPE_WEEK = DEFAULT_PATTERN + " - " + DEFAULT_PATTERN;
    public static String DEFAULT_PATTERN_TYPE_MOTH = DEFAULT_PATTERN;
    
    protected Context mContext;
    protected int type;
    protected long startDate;
    protected long endDate;
    protected String patternDate;
    protected LinkedHashMap<String, String> data;
    
    public BaseModel(Context context, int type) {
        this.type = type;
        this.mContext = context;
        if (type == TYPE_DATE) {
            this.patternDate = DEFAULT_PATTERN_TYPE_DATE;
        } else if (type == TYPE_WEEK) {
            this.patternDate = DEFAULT_PATTERN_TYPE_WEEK;
        } else if (type == TYPE_MONTH) {
            this.patternDate = DEFAULT_PATTERN_TYPE_MOTH;
        }
        this.data = new LinkedHashMap<>();
    }
    
    
    public Map<String, String> getData() {
        return this.data;
    }
    
    public abstract void buildData();
    
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
        buildData();
    }
    
    public long getEndDate() {
        return endDate;
    }
    
    public void setData(LinkedHashMap<String, String> data) {
        this.data = data;
    }
}
