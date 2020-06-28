package com.docker.commonapi.router;

import java.util.HashMap;

/*
 * 处理结果===》参数转接
 * */
public class ResultParam {

    public Object TargetVm;

    public String TargerMethod;

    public ResultParam(Object targetVm, String targerMethod) {
        TargetVm = targetVm;
        TargerMethod = targerMethod;
    }

    public ResultParam() {
    }

    public HashMap<String, String> TransPortPramMap = new HashMap<>();


}
