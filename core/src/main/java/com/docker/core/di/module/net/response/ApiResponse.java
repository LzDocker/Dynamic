/*
 * Copyright (C) 2017 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.docker.core.di.module.net.response;

import android.util.Log;

import androidx.annotation.Nullable;

import java.io.IOException;
import java.io.Serializable;

import retrofit2.Call;
import retrofit2.Response;

/**
 * Common class used by API responses.
 *
 * @param <T>
 */
public class ApiResponse<T> implements Serializable {

    public static String TAG = "ApiResponse";

    public final int code;
    @Nullable
    public final T body;
    @Nullable
    public final String errorMessage;

    public Call call;

    public static String getTAG() {
        return TAG;
    }

    public static void setTAG(String TAG) {
        ApiResponse.TAG = TAG;
    }

    public int getCode() {
        return code;
    }

    @Nullable
    public T getBody() {
        return body;
    }

    @Nullable
    public String getErrorMessage() {
        return errorMessage;
    }


    public ApiResponse(Call call) {
        this.call = call;
        code = 200;
        body = null;
        errorMessage = "realload";
    }

    public ApiResponse(Throwable error) {
        code = 500;
        body = null;
        errorMessage = error.getMessage();
    }

    public ApiResponse(T t) {
        code = 200;
        body = t;
        errorMessage = null;
    }

    public ApiResponse(Response<T> response) {
//       String contentType =  header.get("Content-Type");
        code = response.code();
        if (response.isSuccessful()) {
            body = response.body();
            errorMessage = null;
        } else {
            String message = null;
            if (response.errorBody() != null) {
                try {
                    message = response.errorBody().string();
                } catch (IOException ignored) {
                    Log.e(TAG, "error while parsing response" + ignored.getMessage());
                }
            }
            if (message == null || message.trim().length() == 0) {
                message = response.message();
            }
            errorMessage = message;
            body = null;
        }
    }

    public boolean isSuccessful() {
        return this.code >= 200 && this.code < 300;
    }
}
