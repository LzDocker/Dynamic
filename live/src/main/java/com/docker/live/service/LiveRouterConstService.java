package com.docker.live.service;

import com.docker.commonapi.service.RouterConstantService;
import com.google.auto.service.AutoService;

@AutoService(RouterConstantService.class)
public class LiveRouterConstService implements RouterConstantService {

    public static final String Group = "/LIVE/";

    public static final String LIVE_INDEX = Group + "live_index";

}
