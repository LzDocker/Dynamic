package com.docker.commonapi.service;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;

import com.blankj.utilcode.util.ActivityUtils;
import com.docker.common.ui.base.NitCommonActivity;
import com.docker.commonapi.anno.PagerPrivoderKeys;
import com.docker.commonapi.router.RouterManager;
import com.docker.core.base.BaseAppliction;
import com.docker.core.command.ReplyCommand;
import com.docker.core.service.ApplicationTaskInitService;
import com.google.auto.service.AutoService;

import java.util.HashMap;

@AutoService(ApplicationTaskInitService.class)
public class CommonApiInitService implements ApplicationTaskInitService {


    public CommonApiInitService(String name) {
        Log.d("TAG", "CommonApiInitService: =====================");
    }

    @Override
    public int getInitLevel() {
        return 0;
    }

    @Override
    public void dispatcherApplication(BaseAppliction application) {
        application.registerActivityLifecycleCallbacks(new Application.ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(@NonNull Activity activity, @Nullable Bundle savedInstanceState) {
                Log.d("Activity", activity.getLocalClassName() + "onActivityCreated: ===============");
                handActivity(activity);
                if (activity instanceof NitCommonActivity && activity.getClass().isAnnotationPresent(PagerPrivoderKeys.class)) {
                    PagerPrivoderKeys pagerPrivoderKeys = activity.getClass().getAnnotation(PagerPrivoderKeys.class);
                    if (pagerPrivoderKeys != null) {
                        NitCommonActivity commonActivity = (NitCommonActivity) activity;
                        commonActivity.mRouterName = pagerPrivoderKeys.routerName();
                    }
                }
            }

            @Override
            public void onActivityStarted(@NonNull Activity activity) {
                Log.d("Activity", activity.getLocalClassName() + "onActivityStarted: ===============");
            }

            @Override
            public void onActivityResumed(@NonNull Activity activity) {
                Log.d("Activity", activity.getLocalClassName() + "onActivityResumed: ===============");
            }

            @Override
            public void onActivityPaused(@NonNull Activity activity) {
                Log.d("Activity", activity.getLocalClassName() + "onActivityPaused: ===============");
            }

            @Override
            public void onActivityStopped(@NonNull Activity activity) {
                Log.d("Activity", activity.getLocalClassName() + "onActivityStopped: ===============");
            }

            @Override
            public void onActivitySaveInstanceState(@NonNull Activity activity, @NonNull Bundle outState) {
                Log.d("Activity", activity.getLocalClassName() + "onActivitySaveInstanceState: ===============");
            }

            @Override
            public void onActivityDestroyed(@NonNull Activity activity) {
                Log.d("Activity", activity.getLocalClassName() + "onActivityDestroyed: ===============");
                if (activity instanceof NitCommonActivity) {
                    NitCommonActivity nitCommonActivity = (NitCommonActivity) activity;
                    RouterManager routerManager = RouterManager.getInstance();
                    if (routerManager.mProcessStack.peek().commandkey.equals(nitCommonActivity.mDefaultParam)) {
                        routerManager.mProcessStack.pop();
                        Log.d("TAG", "onActivityDestroyed: =====pop==========");
                    }
                }
            }
        });
    }

    private void handActivity(Activity activity) {
        if (activity instanceof FragmentActivity) {
            ((FragmentActivity) activity).getSupportFragmentManager()
                    .registerFragmentLifecycleCallbacks(
                            new FragmentManager.FragmentLifecycleCallbacks() {
                                @Override
                                public void onFragmentAttached(@NonNull FragmentManager fm, @NonNull Fragment f, @NonNull Context context) {
                                    super.onFragmentAttached(fm, f, context);
                                    Log.d("Fragment", f.getClass().getSimpleName() + "onFragmentAttached: ==============");
                                }

                                @Override
                                public void onFragmentViewDestroyed(@NonNull FragmentManager fm, @NonNull Fragment f) {
                                    super.onFragmentViewDestroyed(fm, f);
                                    Log.d("Fragment", f.getClass().getSimpleName() + "onFragmentViewDestroyed: ==============");
                                }

                                @Override
                                public void onFragmentDestroyed(FragmentManager fm, Fragment f) {
                                    super.onFragmentDestroyed(fm, f);
                                    Log.d("Fragment", f.getClass().getSimpleName() + "onFragmentDestroyed: ==============");
                                }
                            }, true);
        }
    }

    @Override
    public void Start() {

    }

    @Override
    public ReplyCommand GetReplyCommand() {
        return () -> {
            Log.d("TAG", "GetReplyCommand: =====================account 初始化完成");
        };
    }
}
