package com.vn.hcmute.team.cortana.mymoney.ui.view;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by infamouSs on 7/29/17.
 */

public class SpacesItemDecoration extends RecyclerView.ItemDecoration {
    
    private int mSpace;
    
    public SpacesItemDecoration(int space) {
        this.mSpace = space;
    }
    
    @Override
    public void getItemOffsets(
              Rect outRect, View view,
              RecyclerView parent, RecyclerView.State state) {
        outRect.top = mSpace;
        outRect.bottom = mSpace;
    }
}
