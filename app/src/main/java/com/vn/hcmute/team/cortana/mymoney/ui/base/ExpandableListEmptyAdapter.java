package com.vn.hcmute.team.cortana.mymoney.ui.base;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;
import com.vn.hcmute.team.cortana.mymoney.R;

/**
 * Created by kunsubin on 10/21/2017.
 */

public class ExpandableListEmptyAdapter extends BaseExpandableListAdapter {
    
    private Context mContext;
    private String mMessage;
    
    public ExpandableListEmptyAdapter(Context context, String message) {
        this.mContext = context;
        this.mMessage = message;
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
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this.mContext
                      .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.item_empty, null);
        }
        TextView message = (TextView) convertView.findViewById(R.id.txt_message);
        message.setText(mMessage);
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
}
