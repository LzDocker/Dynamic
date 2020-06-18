package com.docker.core.di.test;

import dagger.Binds;
import dagger.Module;
import dagger.hilt.InstallIn;
import dagger.hilt.android.components.ActivityRetainedComponent;

@Module
@InstallIn(ActivityRetainedComponent.class)
public abstract class AnalyticsModule {

    @Binds
    public abstract AnalyticsService bindAnalyticsService(AnalyticsServiceImpl analyticsServiceImpl);


}
