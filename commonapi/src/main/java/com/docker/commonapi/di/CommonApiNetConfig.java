package com.docker.commonapi.di;

import com.docker.commonapi.api.CommonService;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.components.ActivityComponent;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
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
}
