package com.docker.circle.api;

import androidx.lifecycle.LiveData;

import com.docker.core.di.module.net.response.ApiResponse;
import com.docker.core.di.module.net.response.BaseResponse;

import java.util.Map;

import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface CircleService {

    /*
     * 圈子列表
     * */
    @POST("api.php?m=circle.getlist")
    @FormUrlEncoded
    LiveData<ApiResponse<BaseResponse<String>>> fechCircleList(@FieldMap Map<String, String> params);

}
