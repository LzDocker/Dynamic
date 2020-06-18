package com.docker.core.utils;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Glooory on 17/5/15.
 */

public abstract class HttpRequestHandler {

    public abstract Response onHttpResultResponse(String httpResult, Interceptor.Chain chain, Response response);

    public abstract Request onHttpRequestBefore(Interceptor.Chain chain, Request request);
    
}
