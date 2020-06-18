package com.docker.core.di.module.cache;

import android.app.Application;

import androidx.room.Room;

import com.docker.core.base.BaseAppliction;

import javax.inject.Inject;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.components.ApplicationComponent;
import dagger.hilt.android.qualifiers.ApplicationContext;

/**
 * Created by zhangxindang on 2018/11/21.
 */

@Module
@InstallIn(ApplicationComponent.class)
public class CacheModule {

    @Provides
    @Singleton
    CacheDatabase provideCacheDatabase(Application application) {
        return Room.databaseBuilder(application, CacheDatabase.class, "Cache.db").build();
    }

}
