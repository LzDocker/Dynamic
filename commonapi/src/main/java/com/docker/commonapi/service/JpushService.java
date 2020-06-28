package com.docker.commonapi.service;

import com.alibaba.android.arouter.facade.template.IProvider;

public interface JpushService extends IProvider {

    /*
     * 是否设置极光
     * */
    void setAlias(boolean isFocus);


}
