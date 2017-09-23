package com.vn.hcmute.team.cortana.mymoney.ui.view;

import android.content.Context;
import android.support.annotation.MenuRes;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.PopupMenu.OnMenuItemClickListener;
import android.view.View;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * Created by infamouSs on 9/4/17.
 */

public class MenuPopupWithIcon {
    
    private Context mContext;
    private OnMenuItemClickListener mMenuItemClickListener;
    private PopupMenu popupMenu;
    private View view;
    
    public MenuPopupWithIcon(Context context, View v) {
        this.mContext = context;
        this.view = v;
        init();
    }
    
    public void inflate(@MenuRes int menures) {
        
        popupMenu.inflate(menures);
    }
    
    public void setMenuItemCLickListener(OnMenuItemClickListener menuItemClickListener) {
        
        this.mMenuItemClickListener = menuItemClickListener;
        
        popupMenu.setOnMenuItemClickListener(menuItemClickListener);
    }
    
    public void show() {
        popupMenu.show();
    }
    
    public OnMenuItemClickListener getMenuItemClickListener() {
        return mMenuItemClickListener;
    }
    
    private void init() {
        popupMenu = new PopupMenu(mContext, view);
        
        try {
            Field[] fields = popupMenu.getClass().getDeclaredFields();
            for (Field field : fields) {
                if ("mPopup".equals(field.getName())) {
                    field.setAccessible(true);
                    Object menuPopupHelper = field.get(popupMenu);
                    Class<?> classPopupHelper = Class.forName(menuPopupHelper.getClass().getName());
                    Method setForceIcons = classPopupHelper
                              .getMethod("setForceShowIcon", boolean.class);
                    setForceIcons.invoke(menuPopupHelper, true);
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    
}
