package com.docker.core.di.test;

import android.app.Application;
import android.util.Log;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class AnalyticsServiceImpl implements AnalyticsService {

    @Inject
    public AnalyticsServiceImpl(/*two t*/ Application application) {
        Log.d("TAG", "AnalyticsServiceImpl: ==================" + this.hashCode()+application.getPackageName());


    }

    @Override
    public void analyticsMethods() {
        Log.d("TAG", "analyticsMethods: ==============11111========222===="+this.hashCode());
    }
}
