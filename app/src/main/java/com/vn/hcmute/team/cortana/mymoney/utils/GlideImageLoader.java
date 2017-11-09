package com.vn.hcmute.team.cortana.mymoney.utils;

import android.content.Context;
import android.widget.ImageView;
import com.vn.hcmute.team.cortana.mymoney.R;
import com.vn.hcmute.team.cortana.mymoney.di.module.GlideApp;

/**
 * Created by infamouSs on 10/20/17.
 */

public class GlideImageLoader {
    
    public static void load(Context context, Object obj, ImageView imageView) {
        GlideApp.with(context)
                  .load(obj)
                  .error(R.drawable.folder_placeholder)
                  .dontAnimate()
                  .into(imageView);
    }
}
