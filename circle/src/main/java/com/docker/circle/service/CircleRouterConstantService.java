package com.docker.circle.service;

import com.docker.commonapi.service.RouterConstantService;
import com.google.auto.service.AutoService;

@AutoService(RouterConstantService.class)
public class CircleRouterConstantService implements RouterConstantService {

    public static final String Group = "/CIRCLE/";

    public static final String CIRCLE_INDEX = Group + "index";


}
