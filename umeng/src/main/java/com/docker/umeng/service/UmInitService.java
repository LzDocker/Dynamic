package com.docker.umeng.service;

import android.app.Application;

import com.docker.core.base.BaseAppliction;
import com.docker.core.service.ApplicationTaskInitService;
import com.google.auto.service.AutoService;
import com.umeng.commonsdk.UMConfigure;
import com.umeng.socialize.PlatformConfig;

import static com.docker.commonapi.config.ThiredPartConfig.QQID;
import static com.docker.commonapi.config.ThiredPartConfig.QQKey;
import static com.docker.commonapi.config.ThiredPartConfig.Umeng;
import static com.docker.commonapi.config.ThiredPartConfig.WxAppid;
import static com.docker.commonapi.config.ThiredPartConfig.Wxsecret;

@AutoService(ApplicationTaskInitService.class)
public class UmInitService implements ApplicationTaskInitService {

    @Override
    public int getInitLevel() {
        return 0;
    }

    @Override
    public void dispatcherApplication(BaseAppliction application) {
        UMConfigure.init(application, Umeng
                , "umeng", UMConfigure.DEVICE_TYPE_PHONE, "");//58edcfeb310c93091c000be2 5965ee00734be40b580001a0
        UMConfigure.setLogEnabled(true);

        // 微信
        PlatformConfig.setWeixin(WxAppid, Wxsecret);
        // QQ
        PlatformConfig.setQQZone(QQID, QQKey);
    }

    @Override
    public void Start() {

    }
}
