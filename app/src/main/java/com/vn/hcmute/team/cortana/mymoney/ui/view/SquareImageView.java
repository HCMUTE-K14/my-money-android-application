package com.vn.hcmute.team.cortana.mymoney.ui.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import com.vn.hcmute.team.cortana.mymoney.ui.base.view.BaseView;

/**
 * Created by infamouSs on 7/29/17.
 */

public class SquareImageView extends android.support.v7.widget.AppCompatImageView implements
                                                                                  BaseView {
    
    
    public SquareImageView(Context context) {
        super(context);
    }
    
    public SquareImageView(
              Context context,
              @Nullable AttributeSet attrs) {
        super(context, attrs);
    }
    
    public SquareImageView(
              Context context,
              @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
    
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = getMeasuredWidth();
        setMeasuredDimension(width, width);
    }
}
