package com.docker.push;

import com.docker.commonapi.service.RouterConstantService;
import com.google.auto.service.AutoService;

@AutoService(RouterConstantService.class)
public class JpushRouterConstant implements RouterConstantService {


    ///JPUSH/jpush_service

    public static final String Group = "/JPUSH/";

    public static final String JPUSH_SERVICE = Group + "jpush_service";

}
