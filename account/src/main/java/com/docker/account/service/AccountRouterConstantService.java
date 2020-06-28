package com.docker.account.service;

import com.docker.commonapi.service.RouterConstantService;
import com.google.auto.service.AutoService;

@AutoService(RouterConstantService.class)
public class AccountRouterConstantService implements RouterConstantService {

    public static final String Group = "/ACCOUNT/";

    public static final String ACCOUNT_INDEX = Group + "index";

    public static final String ACCOUNT_LOGIN = Group + "login";

    public static final String ACCOUNT_RESET = Group + "reset";

    public static final String ACCOUNT_REGISTER = Group + "register";

}
