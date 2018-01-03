package com.vn.hcmute.team.cortana.mymoney.ui.settings;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.vn.hcmute.team.cortana.mymoney.R;
import com.vn.hcmute.team.cortana.mymoney.ui.settings.SettingsAdapter.SettingsViewHolder;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by infamouSs on 12/28/17.
 */

public class SettingsAdapter extends RecyclerView.Adapter<SettingsViewHolder> {
    
    private List<String> mData;
    private SettingsListener mSettingsListener;
    
    public SettingsAdapter(SettingsListener mSettingsListener) {
        this.mSettingsListener = mSettingsListener;
    }
    
    @Override
    public SettingsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                  .inflate(R.layout.item_recycler_view_text, parent, false);
        
        return new SettingsViewHolder(view);
    }
    
    @Override
    public void onBindViewHolder(SettingsViewHolder holder, final int position) {
        
        final String str = mData.get(position);
        holder.mTextView.setText(str);
        
        holder.itemView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (position) {
                    case 0:
                        mSettingsListener.onChooseLanguage();
                        break;
                    case 1:
                        mSettingsListener.onBackupDatabase();
                        break;
                    case 2:
                        mSettingsListener.onRestoreDatabase();
                        break;
                    case 3:
                        mSettingsListener.onShowAbout();
                        break;
                    default:
                        break;
                }
            }
        });
    }
    
    public void setData(List<String> data) {
        if (this.mData == null || this.mData.isEmpty()) {
            this.mData = new ArrayList<>();
            
        }
        mData.addAll(data);
        
        notifyDataSetChanged();
    }
    
    @Override
    public int getItemCount() {
        return mData.size();
    }
    
    public interface SettingsListener {

        void onChooseLanguage();

        void onBackupDatabase();

        void onRestoreDatabase();

        void onShowAbout();
    }
    
    class SettingsViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.text)
        TextView mTextView;

        @BindView(R.id.text_sub)
        TextView mTextViewSub;

        SettingsViewHolder(android.view.View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            mTextView.setTextColor(Color.BLACK);
        }
    }
}
