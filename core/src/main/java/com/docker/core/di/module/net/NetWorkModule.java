package com.docker.core.di.module.net;

import com.docker.core.di.module.net.adapter.LiveDataCallAdapterFactory;
import com.docker.core.di.module.net.converter.GsonConverterFactory;
import com.docker.core.di.module.net.interceptor.CommonInterceptor;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.components.ActivityComponent;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import retrofit2.Converter;
import retrofit2.Retrofit;

@Module
@InstallIn(ActivityComponent.class)
public class NetWorkModule {

    @Provides
    public static Retrofit.Builder provideRetrofitBuilder(OkHttpClient client, List<Converter.Factory> mConverters) {
        Retrofit.Builder builder = new Retrofit.Builder().client(client)
                .addCallAdapterFactory(new LiveDataCallAdapterFactory())
                .addConverterFactory(GsonConverterFactory.create());
        for (int i = 0; i < mConverters.size(); i++) {
            builder.addConverterFactory(mConverters.get(i));
        }
        return builder;
    }

    @Provides
    public static Retrofit provideRetrofit(Retrofit.Builder builder, HttpUrl baseurl) {
        return builder.baseUrl(baseurl).build();
    }


    @Provides
    public static OkHttpClient.Builder provideCommonClientBuilder(Interceptor intercept
            , List<Interceptor> interceptors, CookieJar cookieJar) {

        OkHttpClient.Builder okHttpClient = new OkHttpClient.Builder();
        OkHttpClient.Builder builder = okHttpClient
                .connectTimeout(60000L, TimeUnit.MILLISECONDS)
                .readTimeout(60000L, TimeUnit.MILLISECONDS)
                .cookieJar(cookieJar)
                .retryOnConnectionFailure(true)
                .addInterceptor(intercept)
                .sslSocketFactory(createSSLSocketFactory(), new MyTrustManager())
                .hostnameVerifier(new HostnameVerifier() {
                    @Override
                    public boolean verify(String hostname, SSLSession session) {
                        return true;
                    }
                });
        if (interceptors != null && interceptors.size() > 0) {
            for (Interceptor interceptor : interceptors) {
                builder.addInterceptor(interceptor);
            }
        }
        return builder;
    }


    @Provides
    public static OkHttpClient provideCommonClient(OkHttpClient.Builder okHttpClient) {
        return okHttpClient.build();
    }


    private static SSLSocketFactory createSSLSocketFactory() {
        SSLSocketFactory ssfFactory = null;
        try {
            MyTrustManager mMyTrustManager = new MyTrustManager();
            SSLContext sc = SSLContext.getInstance("TLS");
            sc.init(null, new TrustManager[]{mMyTrustManager}, new SecureRandom());
            ssfFactory = sc.getSocketFactory();
        } catch (Exception ignored) {
            ignored.printStackTrace();
        }

        return ssfFactory;
    }

    //实现X509TrustManager接口
    public static class MyTrustManager implements X509TrustManager {
        @Override
        public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
        }

        @Override
        public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
        }

        @Override
        public X509Certificate[] getAcceptedIssuers() {
            return new X509Certificate[0];
        }
    }


    @Provides
    public static Gson provideGson() {
        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
        return gson;
    }

    @Provides
    public static Interceptor provideIntercept(CommonInterceptor interceptor) {
        return interceptor;
    }


}
