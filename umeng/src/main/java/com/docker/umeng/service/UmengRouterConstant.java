package com.docker.umeng.service;

import com.docker.commonapi.service.RouterConstantService;
import com.google.auto.service.AutoService;

@AutoService(RouterConstantService.class)
public class UmengRouterConstant implements RouterConstantService {


    //  /UMENG/umeng_service

    public static final String Group = "/UMENG/";

    public static final String UMENG_SERVICE = Group + "umeng_service";

}
