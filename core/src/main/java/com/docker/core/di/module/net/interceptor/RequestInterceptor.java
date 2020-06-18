//package com.docker.core.di.module.net.interceptor;
//
//import android.text.TextUtils;
//import android.util.Log;
//
//import com.docker.core.di.module.net.vo.Mheader;
//import com.docker.core.utils.HttpRequestHandler;
//
//import java.io.IOException;
//
//import javax.inject.Inject;
//
//import okhttp3.HttpUrl;
//import okhttp3.Interceptor;
//import okhttp3.MediaType;
//import okhttp3.Protocol;
//import okhttp3.Request;
//import okhttp3.RequestBody;
//import okhttp3.Response;
//import okhttp3.ResponseBody;
//import okio.Buffer;
//
///**
// * Created by zxd on 17/5/15.
// */
//public class RequestInterceptor implements Interceptor {
//    private HttpRequestHandler mHttpRequestHandler;
//    private Mheader mHeader;
//
//    @Inject
//    public RequestInterceptor(HttpRequestHandler httpRequestHandler, Mheader mHeader) {
//        this.mHttpRequestHandler = httpRequestHandler;
//        this.mHeader = mHeader;
//    }
//
//    public Response intercept(Chain chain) throws IOException {
//
//        Request originrequest = chain.request();
//        HttpUrl originHttpUrl = originrequest.url();
//        Request.Builder newBuilder;
//        HttpUrl newBaseUrl;
//        if (!TextUtils.isEmpty(mHeader.getServerUrl())
//                && !TextUtils.isEmpty(mHeader.getBaseUrl())
//                && !mHeader.getBaseUrl().equals(mHeader.getServerUrl())
//                && !originHttpUrl.toString().startsWith(mHeader.getServerUrl())) {
//            newBuilder = originrequest.newBuilder();
//            newBaseUrl = HttpUrl.parse(originHttpUrl.toString().replace(mHeader.getBaseUrl(), mHeader.getServerUrl()));
//            newBuilder.url(newBaseUrl);
//            Request request = newBuilder.build();
//            if (mHttpRequestHandler != null) {
//                // do something before http request like adding specific headers.
//                request = mHttpRequestHandler.onHttpRequestBefore(chain, request);
//            }
//            Response originalResponse;
//            if (request != null) {
//                originalResponse = chain.proceed(request);
//
//            } else {
//                originalResponse = new Response.Builder()
//                        .code(505) // 其实code可以随便给
//                        .protocol(Protocol.HTTP_2)
//                        .message("Network is closed by login")
//                        .body(ResponseBody.create(MediaType.parse("text/html; charset=utf-8"), "")) // 返回空页面
//                        .request(chain.request())
//                        .build();
//            }
//            if (mHttpRequestHandler != null) {
//                originalResponse = mHttpRequestHandler.onHttpResultResponse(originalResponse.body().toString(), chain, originalResponse);
//            }
//            Log.e("core", "==========Request========" + request.toString());
//            Log.e("core", "==========param========" + bodyToString(request.body()));
//            return originalResponse;
//        } else {
//            if (mHttpRequestHandler != null) {
//                originrequest = mHttpRequestHandler.onHttpRequestBefore(chain, originrequest);
//            }
//            Response originalResponse;
//            if (originrequest == null) {
//                originalResponse = new Response.Builder()
//                        .code(505) // 其实code可以随便给
//                        .protocol(Protocol.HTTP_2)
//                        .message("Network is closed by login")
//                        .body(ResponseBody.create(MediaType.parse("text/html; charset=utf-8"), "")) // 返回空页面
//                        .request(chain.request())
//                        .build();
//            } else {
//                originalResponse = chain.proceed(originrequest);
//                Log.e("core", "==========Request========" + originrequest.toString());
//                Log.e("core", "==========param========" + bodyToString(originrequest.body()));
//            }
//            if (mHttpRequestHandler != null) {
//                originalResponse = mHttpRequestHandler.onHttpResultResponse(originalResponse.body().toString(), chain, originalResponse);
//            }
//            return originalResponse;
//        }
//    }
//
//
//    private static String getUserAgent() {
//        String userAgent = "";
//        StringBuffer sb = new StringBuffer();
//        userAgent = System.getProperty("http.agent");//Dalvik/2.1.0 (Linux; U; Android 6.0.1; vivo X9L Build/MMB29M)
//        for (int i = 0, length = userAgent.length(); i < length; i++) {
//            char c = userAgent.charAt(i);
//            if (c <= '\u001f' || c >= '\u007f') {
//                sb.append(String.format("\\u%04x", (int) c));
//            } else {
//                sb.append(c);
//            }
//        }
////        LogUtils.tag("xxx").e("User-Agent","User-Agent: "+ sb.toString());
//        return sb.toString();
//    }
//
//
//    private static String bodyToString(RequestBody request) {
//        try {
//            Buffer buffer = new Buffer();
//            if (request != null) {
//                request.writeTo(buffer);
//                return buffer.readUtf8();
//            } else {
//                return "";
//            }
//        } catch (IOException var3) {
//            return "did not work";
//        }
//    }
//
//}
