package com.docker.commonapi.di;

import android.util.Log;

import com.docker.commonapi.api.CommonService;
import com.docker.core.utils.HttpRequestHandler;

import java.util.ArrayList;
import java.util.List;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.components.ActivityComponent;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Converter;
import retrofit2.Retrofit;

@Module
@InstallIn(ActivityComponent.class)
public class CommonApiNetConfig {

    @Provides
    public static CommonService provideCommonService(@CommonApiRetorfitQuali Retrofit retrofit) {
        return retrofit.create(CommonService.class);
    }

    @CommonApiRetorfitQuali
    @Provides
    public static Retrofit providerCommonApiRetorfit(Retrofit.Builder builder, OkHttpClient client) {
        return builder.client(client).baseUrl(HttpUrl.parse("http://test.cyn6.com/")).build();
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
                Log.d("TAG", "onHttpRequestBefore: ====account======");
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
