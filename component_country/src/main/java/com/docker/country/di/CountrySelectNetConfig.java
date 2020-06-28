package com.docker.country.di;

import com.docker.country.api.CountrySelectService;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.components.ActivityComponent;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;

@Module
@InstallIn(ActivityComponent.class)
public class CountrySelectNetConfig {

    @Provides
    public CountrySelectService provideCountrySelectService(@CountryRetorfitQuali Retrofit retrofit) {
        return retrofit.create(CountrySelectService.class);
    }

    @CountryRetorfitQuali
    @Provides
    public static Retrofit providerCountryRetorfit(Retrofit.Builder builder, OkHttpClient client) {
        return builder.client(client).baseUrl(HttpUrl.parse("http://tygs.wgc360.com/")).build();
    }
}
