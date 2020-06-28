package com.docker.country.service;

import com.docker.commonapi.service.RouterConstantService;
import com.google.auto.service.AutoService;

@AutoService(RouterConstantService.class)
public class ComponentRouterConstantService implements RouterConstantService {


    public static final String Group = "/COMPONENTS_COUNTRY/";

    public static final String COMPONENTS_COUNTRY_INDEX = Group + "index";


}
