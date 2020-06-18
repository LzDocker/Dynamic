package com.docker.dynamic;

import android.util.Log;

import com.docker.core.utils.HttpRequestHandler;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.components.ActivityComponent;
import dagger.hilt.android.components.ApplicationComponent;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Converter;

@Module
@InstallIn(ActivityComponent.class)
public class NetConfigModule {

    @Provides
    public HttpUrl provideBaseUrl() {
        return HttpUrl.parse("http://www.nit.com/");
    }

    @Provides
    public HttpRequestHandler providerHttpRequestHandler() {
        return new HttpRequestHandler() {
            @Override
            public Response onHttpResultResponse(String httpResult, Interceptor.Chain chain, Response response) {
                return response;
            }

            @Override
            public Request onHttpRequestBefore(Interceptor.Chain chain, Request request) {
                Log.d("TAG", "onHttpRequestBefore: ====app======");
                return request;
            }
        };
    }

    @Provides
    public List<Interceptor> providerInterceptors() {
        List<Interceptor> interceptors = new ArrayList<>();

        return interceptors;
    }
    
    @Provides
    public List<Converter.Factory> providerConvers() {
        List<Converter.Factory> mConverters = new ArrayList<>();
        return mConverters;
    }
}
