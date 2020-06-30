package com.docker.live;

import com.alibaba.android.arouter.launcher.ARouter;
import com.blankj.utilcode.util.Utils;
import com.docker.core.base.BaseAppliction;

import dagger.hilt.android.HiltAndroidApp;

@HiltAndroidApp
public class LiveApp extends BaseAppliction {

    @Override
    public void onCreate() {
        super.onCreate();
        ARouter.openLog();     // Print log
        ARouter.openDebug();
        ARouter.init(this);
        Utils.init(this);

    }


}
