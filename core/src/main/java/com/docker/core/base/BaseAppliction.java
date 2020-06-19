package com.docker.core.base;

import android.app.Application;
import android.content.res.Resources;
import android.util.Log;

import com.docker.core.service.ApplicationTaskInitService;

import org.checkerframework.checker.units.qual.A;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.ServiceLoader;

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
        ServiceLoader<ApplicationTaskInitService> initServices = ServiceLoader.load(ApplicationTaskInitService.class);
        List<ApplicationTaskInitService> tasklist = new ArrayList<>();
        for (ApplicationTaskInitService initService : initServices) {
            tasklist.add(initService);
        }
        Collections.sort(tasklist, (o1, o2) -> o1.getInitLevel() - o2.getInitLevel());
        for (int i = 0; i < tasklist.size(); i++) {
            tasklist.get(i).Start();
            tasklist.get(i).GetReplyCommand().exectue();
        }
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
