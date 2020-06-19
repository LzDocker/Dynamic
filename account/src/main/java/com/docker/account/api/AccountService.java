package com.docker.account.api;

import androidx.lifecycle.LiveData;

import com.docker.core.di.module.net.response.ApiResponse;
import com.docker.core.di.module.net.response.BaseResponse;

import java.util.List;
import java.util.Map;

import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface AccountService {

    /*
     * 圈子列表
     * */
    @POST("api.php?m=circle.getlist")
    @FormUrlEncoded
    LiveData<ApiResponse<BaseResponse<String>>> fechCircleList(@FieldMap Map<String, String> params);


    /*
     * 圈子详情tab列表数据
     * */
    @POST("api.php?m=dynamic.getList")
    @FormUrlEncoded
    LiveData<ApiResponse<BaseResponse<List<Object>>>> fechCircleInfoList(@FieldMap Map<String, String> params);

}
