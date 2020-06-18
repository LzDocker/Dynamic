package com.docker.core.di.module.net.response;

import java.io.Serializable;

/**
 * Created by zhangxindang on 2018/9/6.
 */

public class BaseResponse<T> implements Serializable {

    private T rst;
    private String errno;
    private String errmsg;

    public T getRst() {
        return rst;
    }

    public void setRst(T rst) {
        this.rst = rst;
    }

    public String getErrno() {
        return errno;
    }

    public void setErrno(String errno) {
        this.errno = errno;
    }

    public String getErrmsg() {
        return errmsg;
    }

    public void setErrmsg(String errmsg) {
        this.errmsg = errmsg;
    }
}
