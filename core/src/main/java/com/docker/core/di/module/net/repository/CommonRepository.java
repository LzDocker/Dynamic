package com.docker.core.di.module.net.repository;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;

import com.docker.core.di.module.cache.CacheDatabase;
import com.docker.core.di.module.cache.CacheStrategy;
import com.docker.core.di.module.net.response.ApiResponse;
import com.docker.core.di.module.net.response.BaseResponse;
import com.docker.core.utils.AppExecutors;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class CommonRepository {

    private final AppExecutors appExecutors;
    private final CacheDatabase cacheDatabase;

    @Inject
    public CommonRepository(AppExecutors appExecutors, CacheDatabase cacheDatabase) {
        this.appExecutors = appExecutors;
        this.cacheDatabase = cacheDatabase;
    }

    public <T> MediatorLiveData<Resource<T>> noneChache(LiveData<ApiResponse<BaseResponse<T>>> servicefun) {
        return new NetworkBoundResourceAuto<T>() {
            @NonNull
            @Override
            protected LiveData<ApiResponse<BaseResponse<T>>> createCall() {
                return servicefun;
            }
        }.asLiveData();
    }

    public <T> MediatorLiveData<Resource<T>> ChacheFeatch(LiveData<ApiResponse<BaseResponse<T>>> servicefun, CacheStrategy cacheStrategy, String cashkey) {
        return new NetworkBoundResourceAuto<T>(appExecutors, cacheStrategy, cacheDatabase, cashkey) {
            @NonNull
            @Override
            protected LiveData<ApiResponse<BaseResponse<T>>> createCall() {
                return servicefun;
            }
        }.asLiveData();
    }

    public <T> MediatorLiveData<Resource<T>> SpecialFeatch(LiveData<ApiResponse<T>> servicefun) {
        return new NetworkBoundResourceAuto<T>(0) {
            @NonNull
            @Override
            protected LiveData<ApiResponse<BaseResponse<T>>> createCall() {
                return null;
            }
            @NonNull
            @Override
            protected LiveData<ApiResponse<T>> createSpecCall() {
                return servicefun;
            }
        }.asLiveData();
    }
}
