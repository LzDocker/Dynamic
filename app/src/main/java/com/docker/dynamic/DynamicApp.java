package com.docker.dynamic;

import com.alibaba.android.arouter.launcher.ARouter;
import com.docker.core.base.BaseAppliction;

import java.util.HashMap;

import dagger.hilt.android.HiltAndroidApp;

@HiltAndroidApp
public class DynamicApp extends BaseAppliction {

    @Override
    public void onCreate() {
        super.onCreate();
        ARouter.openLog();     // Print log
        ARouter.openDebug();
        ARouter.init(this);
    }


}
