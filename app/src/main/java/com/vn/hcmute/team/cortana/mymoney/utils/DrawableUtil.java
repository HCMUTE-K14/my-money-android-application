package com.vn.hcmute.team.cortana.mymoney.utils;

import android.content.Context;
import android.content.res.Resources;

/**
 * Created by infamouSs on 8/31/17.
 */

public class DrawableUtil {
    
    public static int getDrawable(Context context, String name) {
        Resources resources = context.getResources();
        return resources.getIdentifier(name, "drawable",
                  context.getPackageName());
    }
    
}
