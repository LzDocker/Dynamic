package com.docker.core.di.module.net.repository;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import retrofit2.Call;

import static com.docker.core.di.module.net.repository.Status.BUSSINESSERROR;
import static com.docker.core.di.module.net.repository.Status.ERROR;
import static com.docker.core.di.module.net.repository.Status.LOADING;
import static com.docker.core.di.module.net.repository.Status.SUCCESS;


/**
 * Created by zhangxindang on 2018/12/24.
 */

public class Resource<T> {

    @NonNull
    public final Status status;
    @Nullable
    public  T data;
    @Nullable
    public final String message;

    public Call call;

    private Resource(@NonNull Status status, Call call) {
        this.call = call;
        this.status = status;
        this.data = null;
        this.message = null;
    }

    private Resource(@NonNull Status status, @Nullable T data, @Nullable String message) {
        this.status = status;
        this.data = data;
        this.message = message;
    }

    public static <T> Resource<T> success(@NonNull T data) {
        return new Resource<>(SUCCESS, data, null);
    }

    public static <T> Resource<T> error(String msg, @Nullable T data) {
        return new Resource<>(ERROR, data, msg);
    }

    public static <T> Resource<T> loading(@Nullable String msg, @Nullable T data) {
        return new Resource<>(LOADING, data, msg);
    }

    public static Resource loading(@Nullable Call call) {
        return new Resource<>(LOADING, call);
    }

    public static <T> Resource<T> bussinessError(String msg, @Nullable T data) {
        return new Resource<>(BUSSINESSERROR, data, msg);
    }

    public static <T> Resource<T> success(@NonNull T data, String message) {
        return new Resource<>(SUCCESS, data, message);
    }

}