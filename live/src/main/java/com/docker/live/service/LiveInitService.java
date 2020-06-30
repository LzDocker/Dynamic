package com.docker.live.service;

import com.docker.core.base.BaseAppliction;
import com.docker.core.service.ApplicationTaskInitService;
import com.google.auto.service.AutoService;
import com.tencent.rtmp.TXLiveBase;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

@AutoService(ApplicationTaskInitService.class)
public class LiveInitService implements ApplicationTaskInitService {


    @Override
    public int getInitLevel() {
        return 0;
    }

    @Override
    public void dispatcherApplication(BaseAppliction application) {
//        String licenceURL = "http://license.vod2.myqcloud.com/license/v1/10c3887da056d7a4557bd324aad2ee35/TXLiveSDK.licence"; // 获取到的 licence url
//        String licenceKey = "40c3a3f8b59a7c71be2d834c9520acf7"; // 获取到的 licence key

        String licenceURL = "http://license.vod2.myqcloud.com/license/v1/3da19626749dab03436693f76fd92519/TXLiveSDK.licence";
        String licenceKey = "a1533361f8e61a7676a06b79644adff7";

//        String licenceURL = "http://download-1252463788.cossh.myqcloud.com/xiaoshipin/licence_android/RDM_Enterprise.license";
//        String licenceKey = "9bc74ac7bfd07ea392e8fdff2ba5678a";


        TXLiveBase.getInstance().setLicence(application, licenceURL, licenceKey);
        closeAndroidPDialog();

    }

    @Override
    public void Start() {

    }


    private void closeAndroidPDialog() {
        try {
            Class aClass = Class.forName("android.content.pm.PackageParser$Package");
            Constructor declaredConstructor = aClass.getDeclaredConstructor(String.class);
            declaredConstructor.setAccessible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            Class cls = Class.forName("android.app.ActivityThread");
            Method declaredMethod = cls.getDeclaredMethod("currentActivityThread");
            declaredMethod.setAccessible(true);
            Object activityThread = declaredMethod.invoke(null);
            Field mHiddenApiWarningShown = cls.getDeclaredField("mHiddenApiWarningShown");
            mHiddenApiWarningShown.setAccessible(true);
            mHiddenApiWarningShown.setBoolean(activityThread, true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

