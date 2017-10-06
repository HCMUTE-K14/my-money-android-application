package com.vn.hcmute.team.cortana.mymoney;

import static junit.framework.Assert.assertEquals;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Instrumentation item_recycler_view_selected_image, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    
    @Test
    public void useAppContext() throws Exception {
        // Context of the app under item_recycler_view_selected_image.
        Context appContext = InstrumentationRegistry.getTargetContext();
        
        assertEquals("com.vn.hcmute.team.cortana.mymoney", appContext.getPackageName());
    }
}
