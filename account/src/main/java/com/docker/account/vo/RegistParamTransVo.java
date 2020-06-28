package com.docker.account.vo;

import java.io.Serializable;
import java.util.HashMap;

public class RegistParamTransVo implements Serializable {

    public String bindType;

    public HashMap<String, String> wechatInfo = new HashMap<>();
}
