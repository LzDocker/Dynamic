package com.docker.umeng.service;

import android.content.Context;
import android.content.Intent;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.blankj.utilcode.util.ActivityUtils;
import com.docker.common.ui.base.NitCommonActivity;
import com.docker.commonapi.service.ThirdShareAndLoginService;
import com.docker.core.command.ReplyCommandParam;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;

import java.util.Map;

import static com.docker.umeng.service.UmengRouterConstant.UMENG_SERVICE;

@Route(path = UMENG_SERVICE, name = "umeng")
public class UmengPowerService implements ThirdShareAndLoginService {

    @Override
    public void init(Context context) {

    }


    @Override
    public void thiredLogin(int plantform, ReplyCommandParam replyCommandParam) {

        SHARE_MEDIA share_media = SHARE_MEDIA.WEIXIN;
        switch (plantform) {
            case 0:
                share_media = SHARE_MEDIA.WEIXIN;
                break;
            case 1:
                share_media = SHARE_MEDIA.QQ;
                break;
        }

        UMShareAPI.get(ActivityUtils.getTopActivity()).getPlatformInfo(ActivityUtils.getTopActivity(), share_media, new UMAuthListener() {
            @Override
            public void onStart(SHARE_MEDIA share_media) {
                if (ActivityUtils.getTopActivity() instanceof NitCommonActivity) {
                    NitCommonActivity nitCommonActivity = (NitCommonActivity) ActivityUtils.getTopActivity();
                    nitCommonActivity.showWaitDialog("请稍后...");
                }
            }

            @Override
            public void onComplete(SHARE_MEDIA share_media, int i, Map<String, String> map) {
                if (ActivityUtils.getTopActivity() instanceof NitCommonActivity) {
                    NitCommonActivity nitCommonActivity = (NitCommonActivity) ActivityUtils.getTopActivity();
                    nitCommonActivity.hidWaitDialog();
                }
                replyCommandParam.exectue(map);
            }

            @Override
            public void onError(SHARE_MEDIA share_media, int i, Throwable throwable) {
                if (ActivityUtils.getTopActivity() instanceof NitCommonActivity) {
                    NitCommonActivity nitCommonActivity = (NitCommonActivity) ActivityUtils.getTopActivity();
                    nitCommonActivity.hidWaitDialog();
                }
            }

            @Override
            public void onCancel(SHARE_MEDIA share_media, int i) {
                if (ActivityUtils.getTopActivity() instanceof NitCommonActivity) {
                    NitCommonActivity nitCommonActivity = (NitCommonActivity) ActivityUtils.getTopActivity();
                    nitCommonActivity.hidWaitDialog();
                }
            }
        });
    }

    @Override
    public void onHandResult(int requestCode, int resultCode, Intent data) {
        UMShareAPI.get(ActivityUtils.getTopActivity()).onActivityResult(requestCode, resultCode, data);
    }
}
