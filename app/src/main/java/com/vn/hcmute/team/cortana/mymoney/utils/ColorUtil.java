package com.vn.hcmute.team.cortana.mymoney.utils;

import java.util.Random;

/**
 * Created by infamouSs on 8/25/17.
 */

public class ColorUtil {
    
    public static String[] COLOR
              = {"#F44336", "#E91E63", "#9C27B0", "#673AB7", "#3F51B5", "#2196F3", "#03A9F4",
              "#00BCD4", "#009688", "#4CAF50", "#8BC34A", "#CDDC39", "#FFEB3B", "#FFC107",
              "#FF9800", "#FF5722", "#795548", "#9E9E9E", "#607D8B","#FFFFFF"};
    
    public static String getRandomColor() {
        int max = COLOR.length-1;
        int min = 0;
        
        Random random = new Random();
        
        int randomNum = random.nextInt((max - min) + 1) + min;
        
        return COLOR[randomNum];
    }
    
    public static String getMyColor(int index) {
        return COLOR[index];
    }
}
