package com.docker.commonapi.router;

import android.app.Activity;
import android.text.TextUtils;
import android.util.Log;
import androidx.lifecycle.LifecycleOwner;
import com.alibaba.android.arouter.facade.Postcard;
import com.alibaba.android.arouter.launcher.ARouter;
import com.docker.common.util.ParamUtils;
import com.docker.commonapi.api.CommonService;
import com.docker.commonapi.cache.DbCacheUtils;
import com.docker.common.config.Constant;
import com.docker.commonapi.service.RouterConstantService;
import com.docker.commonapi.vo.Router;
import com.docker.commonapi.vo.RoutersServerVo;
import com.docker.core.command.ReplyCommand;
import com.docker.core.command.ReplyCommandParam;
import com.docker.core.di.module.cache.CacheDatabase;
import com.docker.core.di.module.cache.CacheEntity;
import com.docker.core.utils.AppExecutors;
import com.docker.core.utils.IOUtils;
import com.google.gson.internal.LinkedTreeMap;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.ServiceLoader;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class RouterManager {


    CommonService commonService;

    AppExecutors appExecutors;

    DbCacheUtils dbCacheUtils;

    CacheDatabase cacheDatabase;


    String mtime;

//    private volatile static RouterManager mRouterManager = null;

    private Map<String, String> memoryRouterMap = new LinkedTreeMap();

    private HashMap<String, Router> routerMap = new HashMap<>();

    private RoutersServerVo routersServerVo = new RoutersServerVo();

    @Inject
    public RouterManager(AppExecutors appExecutors, CacheDatabase cacheDatabase, DbCacheUtils dbCacheUtils) {
        this.appExecutors = appExecutors;
        this.cacheDatabase = cacheDatabase;
        this.dbCacheUtils = dbCacheUtils;

    }

    public RouterManager initService(CommonService commonService) {
        this.commonService = commonService;
        return this;
    }

//    public RouterManager() {
//    }
//
//    public static RouterManager getInstance() {
//        if (mRouterManager == null) {
//            synchronized (RouterManager.class) {
//                if (mRouterManager == null) {
//                    mRouterManager = new RouterManager();
//                }
//            }
//        }
//        return mRouterManager;
//    }


    /*
     * key  RouterConstantService子类的属性名
     * 获取内存中path
     * */
    public String getMemoryRouterPath(String key) {
        String path = "";
        if (memoryRouterMap.size() > 0) {
            if (memoryRouterMap.containsKey(key)) {
                path = memoryRouterMap.get(key);
            }
        } else {
            ServiceLoader<RouterConstantService> routerConstantServices = ServiceLoader.load(RouterConstantService.class);
            for (RouterConstantService routerConstantService : routerConstantServices) {
                memoryRouterMap.putAll(ParamUtils.objectToMap(routerConstantService, false));
            }
            if (memoryRouterMap.size() > 0) {
                if (memoryRouterMap.containsKey(key)) {
                    path = memoryRouterMap.get(key);
                }
            }
        }
        return path;
    }


    private void initKeysSet() {
        if (memoryRouterMap.size() == 0) {
            ServiceLoader<RouterConstantService> routerConstantServices = ServiceLoader.load(RouterConstantService.class);
            for (RouterConstantService routerConstantService : routerConstantServices) {
                memoryRouterMap.putAll(ParamUtils.objectToMap(routerConstantService, false));
            }
        }
    }


    public void initRouterData(LifecycleOwner lifecycleOwner, ReplyCommandParam replyCommand) {
        initKeysSet();
        dbCacheUtils.loadFromDb("router_version").observe(lifecycleOwner, o -> {
            mtime = (String) o;
            HashMap<String, String> param = new HashMap<>();
            if (TextUtils.isEmpty(mtime)) {
                param.put("mtime", "1");
            } else {
                param.put("mtime", mtime);
            }
            commonService.fetchRouter(param).observe(lifecycleOwner, baseResponseApiResponse -> {
                if (baseResponseApiResponse.isSuccessful() && baseResponseApiResponse.body != null
                        && "0".equals(baseResponseApiResponse.body.getErrno())) {
                    RoutersServerVo routersServerVo = baseResponseApiResponse.body.getRst();
                    dbCacheUtils.save("router_db", routersServerVo, () -> {
                        dbCacheUtils.save("router_version", routersServerVo.mtime, null);
                        initData(() -> {
                            Log.d("sss", "onComplete:路由初始化成功");
                            if (replyCommand != null) {
                                replyCommand.exectue(true);
                            }
                        }, false);
                    });
                } else {
                    initData(() -> {
                        Log.d("sss", "onComplete:路由初始化成功");
                        if (replyCommand != null) {
                            replyCommand.exectue(true);
                        }
                    }, true);
                }
            });
        });
    }

    // 初始化路由数据
    public void initData(ReplyCommand replyCommand, boolean ischeck) {
        initKeysSet();
        if (ischeck) {
            if (routerMap.size() > 0) {
                return;
            }
        }
        appExecutors.diskIO().execute(() -> {
            CacheEntity souce = cacheDatabase.cacheEntityDao().LoadCacheSync("router_db");
            if (souce != null && souce.getData() != null) {
                routersServerVo = (RoutersServerVo) IOUtils.toObject(souce.getData());
            }
            if (routersServerVo.routes == null || routersServerVo.routes.size() == 0) {
                Map parammap = memoryRouterMap;
                Iterator iter = parammap.entrySet().iterator();
                while (iter.hasNext()) {
                    Map.Entry entry = (Map.Entry) iter.next();
                    String key = (String) entry.getKey();
                    String val = (String) entry.getValue();
                    Router routerEntry = new Router();
                    routerEntry.androidRoute = val;
                    routerEntry.isdefault = "1";
                    routerEntry.type = "1";
                    routerEntry.paramStr = null;
                    routerEntry.key = key;
                    routersServerVo.routes.add(routerEntry);
                }
            }
            for (int i = 0; i < routersServerVo.routes.size(); i++) {
                routerMap.put(routersServerVo.routes.get(i).key, routersServerVo.routes.get(i));
            }
            if (replyCommand != null) {
                appExecutors.mainThread().execute(() -> replyCommand.exectue());
            }
        });
    }

    /*
     * key  路由注册的key  对应 routerinfo 中的属性  必须参数
     *
     * param 跳转界面传递的参数 非必填  在到达的界面 使用  Constant.ParamTrans 作为key 来获取
     *
     * ispush 是否推送跳转 必传参数
     *
     * content code 非必须参数   传入则是 forResult 的跳转
     *
     * */
    public void Jump(RouterParam routerParam) {
        initKeysSet();
        if (routerMap.size() == 0) {
            initData(() -> {
                if (routerMap.size() == 0) {
                    return;
                }
                if (routerMap.containsKey(routerParam.key)) {
                    processJump(routerParam);
                } else {
//                    ToastUtils.showShort("功能暂未开放，敬请期待...");
                }
            }, true);
        } else {
            if (routerMap.containsKey(routerParam.key)) {
                processJump(routerParam);
            } else {
//                ToastUtils.showShort("功能暂未开放，敬请期待...");
            }
        }
    }


    private void processJump(RouterParam routerParam) {
        Router routerEntry = routerMap.get(routerParam.key);
        Object param = routerParam.param;
        boolean isPush = routerParam.ispush;
        boolean isfromH5 = routerParam.isFormH5;
        Activity content = routerParam.context;
        int code = routerParam.code;
        Postcard postcard;
        if (isPush) {
            if (param != null) {
                postcard = ARouter.getInstance().build(routerEntry.androidRoute)
                        .withSerializable(Constant.ParamTrans, (Serializable) param);
            } else {
                postcard = ARouter.getInstance().build(routerEntry.androidRoute);
            }
            postcard.navigation();
            return;
        }
        if (isfromH5) {  // h5--->原生
            if (param != null) {
                postcard = ARouter.getInstance().build(routerEntry.androidRoute)
                        .withSerializable(Constant.ParamTrans, (Serializable) param);
            } else {
                postcard = ARouter.getInstance().build(routerEntry.androidRoute);
            }
            postcard.navigation();
            return;
        }

        if ("1".equals(routerEntry.type)) {  // app 原生跳转
            if ("0".equals(routerEntry.isdefault)) {   // 后端指定参数
                if (!TextUtils.isEmpty(routerEntry.paramStr)) {
                    HashMap<String, String> parammap = new HashMap<>();
                    String[] parmarr = routerEntry.paramStr.split("&");
                    for (int i = 0; i < parmarr.length; i++) {
                        String[] paramone = parmarr[i].split("=");
                        parammap.put(paramone[0], paramone[1]);
                    }
                    postcard = ARouter.getInstance().build(routerEntry.androidRoute)
                            .withSerializable(Constant.ParamTrans, parammap);
//                        .withSerializable(Constant.ParamTrans, (Serializable) ParamUtils.mapToObject(parammap, new Object(), false));
                } else {
                    postcard = ARouter.getInstance().build(routerEntry.androidRoute);
                }
                if (content != null && code > 0) {
                    postcard.navigation(content, code);
                } else {
                    postcard.navigation();
                }
            } else {
                if (param != null) {
                    postcard = ARouter.getInstance().build(routerEntry.androidRoute)
                            .withSerializable(Constant.ParamTrans, (Serializable) param);
                } else {
                    postcard = ARouter.getInstance().build(routerEntry.androidRoute);
                }
                if (content != null && code > 0) {
                    postcard.navigation(content, code);
                } else {
                    postcard.navigation();
                }
            }
        } else {
            ARouter.getInstance().build(Constant.COMMONH5).withString("weburl", routerEntry.httpurl).withString("title", routerEntry.name).navigation();
        }
    }

    // 更新内存中的 路由数据
    public void updateData(ReplyCommand replyCommand) {
        appExecutors.diskIO().execute(() -> {
            initKeysSet();
            CacheEntity souce = cacheDatabase.cacheEntityDao().LoadCacheSync("router_db");
            if (souce != null) {
                routersServerVo = (RoutersServerVo) IOUtils.toObject(souce.getData());
            }
            if (routersServerVo.routes.size() == 0) {
                Map parammap = memoryRouterMap;
                Iterator iter = parammap.entrySet().iterator();
                while (iter.hasNext()) {
                    Map.Entry entry = (Map.Entry) iter.next();
                    String key = (String) entry.getKey();
                    String val = (String) entry.getValue();
                    Router routerEntry = new Router();
                    routerEntry.androidRoute = val;
                    routerEntry.isdefault = "1";
                    routerEntry.type = "1";
                    routerEntry.paramStr = null;
                    routerEntry.key = key;
                    routersServerVo.routes.add(routerEntry);
                }
            }
            for (int i = 0; i < routersServerVo.routes.size(); i++) {
                routerMap.put(routersServerVo.routes.get(i).key, routersServerVo.routes.get(i));
            }
            if (replyCommand != null) {
                appExecutors.mainThread().execute(() -> replyCommand.exectue());
            }
        });
    }

}
