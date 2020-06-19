package com.docker.commonapi.api;

import androidx.lifecycle.LiveData;

import com.docker.commonapi.vo.RoutersServerVo;
import com.docker.core.di.module.net.response.ApiResponse;
import com.docker.core.di.module.net.response.BaseResponse;

import java.util.HashMap;

import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface CommonService {

    /*
     * 路由
     * */
    @POST("api.php?m=publics.getRoute")
    @FormUrlEncoded
    LiveData<ApiResponse<BaseResponse<RoutersServerVo>>> fetchRouter(@FieldMap HashMap<String, String> paramMap);


}
