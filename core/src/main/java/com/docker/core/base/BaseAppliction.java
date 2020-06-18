package com.docker.core.base;


import android.app.Application;
import android.content.res.Resources;

import java.util.HashMap;

public abstract class BaseAppliction extends Application {

    private static BaseAppliction instance;

    public static BaseAppliction getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        init();
    }


    private void init() {
        instance = this;
    }


    //禁止app字体大小跟随系统字体大小调节
    @Override
    public Resources getResources() {
        Resources resources = super.getResources();
        if (resources != null && resources.getConfiguration().fontScale != 1.0f) {
            android.content.res.Configuration configuration = resources.getConfiguration();
            configuration.fontScale = 1.0f;
            resources.updateConfiguration(configuration, resources.getDisplayMetrics());
        }
        return resources;
    }

}
