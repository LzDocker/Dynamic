package com.docker.live.di;


import com.docker.live.api.LiveService;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.components.ActivityComponent;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;

@Module
@InstallIn(ActivityComponent.class)
public class LiveNetConfig {


    @Provides
    public LiveService provideLiveService(@LiveRetorfitQuali Retrofit retrofit) {
        return retrofit.create(LiveService.class);
    }

    @LiveRetorfitQuali
    @Provides
    public static Retrofit providerLiveServiceRetorfit(Retrofit.Builder builder, OkHttpClient client) {
        return builder.client(client).baseUrl(HttpUrl.parse("https://room.qcloud.com/")).build();
    }

}
