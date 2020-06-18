package com.docker.core.di.module.cookie;

import dagger.Binds;
import dagger.Module;
import dagger.hilt.InstallIn;
import dagger.hilt.android.components.ApplicationComponent;
import okhttp3.CookieJar;

@Module
@InstallIn(ApplicationComponent.class)
public abstract class CookieModule {

    @Binds
    public abstract CookieJar providerCookieJar(CookieJarImpl cookieJar);

    @Binds
    public abstract CookieStore providerCookieStore(PersistentCookieStore cookieStore);


}
