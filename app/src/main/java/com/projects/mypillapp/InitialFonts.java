package com.projects.mypillapp;

import android.app.Application;


import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

/**
 * Created by นนทรักษ์ on 1/11/2558.
 */
public class InitialFonts extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("font/CSPraJad.ttf").setFontAttrId(R.attr.fontPath)
                .build());
    }
}
