package com.docker.account.di;

import android.util.Log;

import com.docker.account.api.AccountService;
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
public class AccountNetConfig {


    @Provides
    public AccountService provideAccountService(@AccountRetorfitQuali Retrofit retrofit) {
        return retrofit.create(AccountService.class);
    }

    @AccountRetorfitQuali
    @Provides
    public static Retrofit providerAccountRetorfit(Retrofit.Builder builder, OkHttpClient client) {
        return builder.client(client).baseUrl(HttpUrl.parse("http://tygs.wgc360.com/")).build();
    }

}
