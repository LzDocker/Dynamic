package com.docker.circle.di;

import android.util.Log;

import com.docker.circle.api.CircleService;
import com.docker.core.di.module.net.interceptor.CommonInterceptor;
import com.docker.core.utils.HttpRequestHandler;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.components.ActivityComponent;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;

@Module
@InstallIn(ActivityComponent.class)
public class CircleNetConfig {


    @Provides
    public CircleService provideCircleService(@CircleRetorfitQuali Retrofit retrofit) {
        return retrofit.create(CircleService.class);
    }

    @CircleRetorfitQuali
    @Provides
    public static Retrofit providerCircleRetorfit(Retrofit.Builder builder, @CircleClinetQuali OkHttpClient client) {
        return builder.client(client).baseUrl(HttpUrl.parse("http://htj.wgc360.com/")).build();
    }

    @CircleClinetQuali
    @Provides
    public static OkHttpClient providerCircleclient(OkHttpClient.Builder builder, @CircleInterceptorQuali Interceptor interceptor) {

        if(!builder.interceptors().contains(interceptor)){
            builder.addInterceptor(interceptor);
        }
        return builder.build();
    }


    @CircleInterceptorQuali
    @Provides
    public static Interceptor providerIntercept() {
        return new CommonInterceptor(new HttpRequestHandler() {

            @Override
            public Response onHttpResultResponse(String httpResult, Interceptor.Chain chain, Response response) {
//                Log.d("TAG", "onHttpResultResponse: ===========circle====");
                return response;
            }

            @Override
            public Request onHttpRequestBefore(Interceptor.Chain chain, Request request) {
                Log.d("TAG", "onHttpRequestBefore: ==========circle=====");
                return request;
            }
        });
    }

//    @CircleInterceptorQuali
//    @Provides
//    public static List<Interceptor> provideIntercept() {
//        List<Interceptor> interceptors = new ArrayList<>();
////        interceptors.add(new CommonInterceptor(new HttpRequestHandler() {
////            @Override
////            public Response onHttpResultResponse(String httpResult, Interceptor.Chain chain, Response response) {
////                return response;
////            }
////
////            @Override
////            public Request onHttpRequestBefore(Interceptor.Chain chain, Request request) {
////                return request;
////            }
////        }));
//        return interceptors;
//    }
}
