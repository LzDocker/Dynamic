package com.docker.country.api;

import androidx.lifecycle.LiveData;

import com.docker.core.di.module.net.response.ApiResponse;
import com.docker.core.di.module.net.response.BaseResponse;
import com.docker.country.vo.WorldNumListHot;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface CountrySelectService {

    /*
     * 获取国家列表--wj
     * */
    @POST("api.php?m=get.getWorldList")
    @FormUrlEncoded
    LiveData<ApiResponse<BaseResponse<WorldNumListHot>>> featchWorldList(@Field("type") String type);


}
