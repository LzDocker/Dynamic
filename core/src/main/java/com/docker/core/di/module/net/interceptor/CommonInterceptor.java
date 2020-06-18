package com.docker.core.di.module.net.interceptor;

import android.util.Log;

import com.docker.core.utils.HttpRequestHandler;

import java.io.IOException;

import javax.inject.Inject;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Protocol;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;

/**
 * Created by zxd on 17/5/15.
 */
public class CommonInterceptor implements Interceptor {
    private HttpRequestHandler mHttpRequestHandler;

    @Inject
    public CommonInterceptor(HttpRequestHandler httpRequestHandler) {
        this.mHttpRequestHandler = httpRequestHandler;
    }

    public Response intercept(Chain chain) throws IOException {
        Request originrequest = chain.request();
        if (mHttpRequestHandler != null) {
            originrequest = mHttpRequestHandler.onHttpRequestBefore(chain, originrequest);
        }
        Response originalResponse;
        if (originrequest != null) {
            originalResponse = chain.proceed(originrequest);
        } else {
            originalResponse = new Response.Builder()
                    .code(505) // 其实code可以随便给
                    .protocol(Protocol.HTTP_2)
                    .message("Network is closed by login")
                    .body(ResponseBody.create(MediaType.parse("text/html; charset=utf-8"), "")) // 返回空页面
                    .request(chain.request())
                    .build();
        }
        if (mHttpRequestHandler != null) {
            originalResponse = mHttpRequestHandler.onHttpResultResponse(originalResponse.body().toString(), chain, originalResponse);
        }
        Log.e("core", "==========Request========" + originrequest.toString());
        Log.e("core", "==========param========" + bodyToString(originrequest.body()));
        return originalResponse;
    }


    private static String bodyToString(RequestBody request) {
        try {
            Buffer buffer = new Buffer();
            if (request != null) {
                request.writeTo(buffer);
                return buffer.readUtf8();
            } else {
                return "";
            }
        } catch (IOException var3) {
            return "did not work";
        }
    }

}
