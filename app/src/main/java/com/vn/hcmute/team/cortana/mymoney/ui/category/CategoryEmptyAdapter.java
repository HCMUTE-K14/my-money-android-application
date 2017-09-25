package com.vn.hcmute.team.cortana.mymoney.ui.category;

import android.content.Context;
import android.database.DataSetObserver;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import com.vn.hcmute.team.cortana.mymoney.R;

/**
 * Created by infamouSs on 9/24/17.
 */

public class CategoryEmptyAdapter extends BaseExpandableListAdapter {
    
    private Context mContext;
    
    public CategoryEmptyAdapter(Context context) {
        this.mContext = context;
    }
    
    @Override
    public void registerDataSetObserver(DataSetObserver observer) {
        
    }
    
    @Override
    public void unregisterDataSetObserver(DataSetObserver observer) {
        
    }
    
    @Override
    public int getGroupCount() {
        return 1;
    }
    
    @Override
    public int getChildrenCount(int groupPosition) {
        return 0;
    }
    
    @Override
    public Object getGroup(int groupPosition) {
        return null;
    }
    
    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return null;
    }
    
    @Override
    public long getGroupId(int groupPosition) {
        return 0;
    }
    
    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return 0;
    }
    
    @Override
    public boolean hasStableIds() {
        return false;
    }
    
    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView,
              ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) mContext
                  .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater
                  .inflate(R.layout.item_empty, parent, false);
        
        return convertView;
    }
    
    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild,
              View convertView, ViewGroup parent) {
        return null;
    }
    
    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }
    
    @Override
    public boolean areAllItemsEnabled() {
        return false;
    }
    
    @Override
    public boolean isEmpty() {
        return false;
    }
    
    @Override
    public void onGroupExpanded(int groupPosition) {
        
    }
    
    @Override
    public void onGroupCollapsed(int groupPosition) {
        
    }
    
    @Override
    public long getCombinedChildId(long groupId, long childId) {
        return 0;
    }
    
    @Override
    public long getCombinedGroupId(long groupId) {
        return 0;
    }
}
