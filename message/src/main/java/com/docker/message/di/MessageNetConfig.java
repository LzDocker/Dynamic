package com.docker.message.di;


import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.components.ActivityComponent;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;

@Module
@InstallIn(ActivityComponent.class)
public class MessageNetConfig {



    @MessageRetorfitQuali
    @Provides
    public static Retrofit providerAccountRetorfit(Retrofit.Builder builder, OkHttpClient client) {
        return builder.client(client).baseUrl(HttpUrl.parse("http://tygs.wgc360.com/")).build();
    }

}
