package com.smashingboxes.ga_wrapper_demo;

import android.app.Application;

import com.smashingboxes.ga_wrapper.TrackerManager;

public class MainApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        initSingletons();
    }

    private void initSingletons() {
        TrackerManager.init(this, R.xml.global_tracker);
    }
}
