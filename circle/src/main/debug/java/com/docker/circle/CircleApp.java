package com.docker.circle;

import com.alibaba.android.arouter.launcher.ARouter;
import com.docker.core.base.BaseAppliction;

import dagger.hilt.android.HiltAndroidApp;

@HiltAndroidApp
public class CircleApp extends BaseAppliction {

    @Override
    public void onCreate() {
        super.onCreate();
        ARouter.openLog();     // Print log
        ARouter.openDebug();
        ARouter.init(this);
    }


}
