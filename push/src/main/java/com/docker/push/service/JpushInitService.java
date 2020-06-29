package com.docker.push.service;

import android.app.Application;

import com.docker.core.base.BaseAppliction;
import com.docker.core.service.ApplicationTaskInitService;
import com.google.auto.service.AutoService;

@AutoService(ApplicationTaskInitService.class)
public class JpushInitService implements ApplicationTaskInitService {


    @Override
    public int getInitLevel() {
        return 0;
    }

    @Override
    public void dispatcherApplication(BaseAppliction application) {
        // 极光推送
       /* JPushInterface.setDebugMode(true);
        JPushInterface.init(application);
        BasicPushNotificationBuilder builder = new BasicPushNotificationBuilder(application);
        builder.statusBarDrawable = R.mipmap.ic_toolbar_back;
        builder.notificationFlags = Notification.FLAG_AUTO_CANCEL
                | Notification.FLAG_SHOW_LIGHTS;  //设置为自动消失和呼吸灯闪烁
        builder.notificationDefaults = Notification.DEFAULT_SOUND
                | Notification.DEFAULT_VIBRATE
                | Notification.DEFAULT_LIGHTS;  // 设置为铃声、震动、呼吸灯闪烁都要
        JPushInterface.setDefaultPushNotificationBuilder(builder);*/
    }

    @Override
    public void Start() {

    }
}
