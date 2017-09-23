package com.vn.hcmute.team.cortana.mymoney.ui.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.vn.hcmute.team.cortana.mymoney.R;

/**
 * Created by infamouSs on 9/18/17.
 */

public class CardViewActionBar extends RelativeLayout {
    
    public static final String DEFAULT_TITLE = "Title";
    public static final int DEFAULT_BACK_ACTION = R.drawable.ic_cancel;
    public static final String DEFAULT_TEXT_ACTION = "Done";
    
    View mRootView;
    
    ImageView mImageViewBackAction;
    TextView mTextViewTitle;
    TextView mTextViewAction;
    LinearLayout mBackButton;
    String mTitleText = DEFAULT_TITLE;
    String mActionText = DEFAULT_TEXT_ACTION;
    Drawable mIconBack;
    boolean isHideAction;
    
    public CardViewActionBar(Context context) {
        super(context);
        init(null, 0);
    }
    
    public CardViewActionBar(Context context,
              @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0);
    }
    
    public CardViewActionBar(Context context,
              @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs, defStyleAttr);
    }
    
    public ImageView getImageViewBackAction() {
        return mImageViewBackAction;
    }
    
    public TextView getTextViewTitle() {
        return mTextViewTitle;
    }
    
    public TextView getTextViewAction() {
        return mTextViewAction;
    }
    
    void init(AttributeSet attrs, int defStyle) {
        
        mRootView = inflate(getContext(), R.layout.layout_card_view_action_bar, this);
        
        mImageViewBackAction = (ImageView) mRootView.findViewById(R.id.image_view);
        mTextViewTitle = (TextView) mRootView.findViewById(R.id.txt_title);
        mTextViewAction = (TextView) mRootView.findViewById(R.id.txt_action);
        mBackButton = (LinearLayout) mRootView.findViewById(R.id.btn_close);
        
        final TypedArray a = getContext().obtainStyledAttributes(
                  attrs, R.styleable.CardViewActionBar, defStyle, 0);
        
        if (a.hasValue(R.styleable.CardViewActionBar_textTitle)) {
            mTitleText = a.getString(R.styleable.CardViewActionBar_textTitle);
        }
        if (a.hasValue(R.styleable.CardViewActionBar_textAction)) {
            mActionText = a.getString(R.styleable.CardViewActionBar_textAction);
        }
        if (a.hasValue(R.styleable.CardViewActionBar_iconBack)) {
            mIconBack = a.getDrawable(R.styleable.CardViewActionBar_iconBack);
        }
        if (a.hasValue(R.styleable.CardViewActionBar_hideAction)) {
            isHideAction = a.getBoolean(R.styleable.CardViewActionBar_hideAction, false);
        }
        a.recycle();
        
        updateView();
    }
    
    public void setTitle(String title) {
        mTitleText = title;
        updateView();
    }
    
    public void setTextAction(String action) {
        mActionText = action;
        updateView();
    }
    
    public void setIconBack(int icon) {
        mIconBack = ContextCompat.getDrawable(getContext(), icon);
        updateView();
    }
    
    public void setOnClickAction(OnClickListener onClickAction) {
        mTextViewAction.setOnClickListener(onClickAction);
    }
    
    public void setOnClickBack(OnClickListener onClickBack) {
        mBackButton.setOnClickListener(onClickBack);
    }
    
    public void hideAction(boolean ishide) {
        isHideAction = ishide;
        updateView();
    }
    
    
    void updateView() {
        mImageViewBackAction.setImageDrawable(mIconBack);
        mTextViewTitle.setText(mTitleText);
        mTextViewAction.setText(mActionText);
        mTextViewAction.setVisibility(isHideAction ? View.GONE : View.VISIBLE);
    }
}
