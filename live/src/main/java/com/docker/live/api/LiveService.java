package com.docker.live.api;

import androidx.lifecycle.LiveData;

import com.docker.core.di.module.net.response.ApiResponse;
import com.docker.live.vo.LiveUserInfoVo;

import java.util.HashMap;

import retrofit2.http.GET;
import retrofit2.http.QueryMap;

public interface LiveService {

    /*
     * 获取用户信息
     * */
    @GET("weapp/utils/get_login_info")
    LiveData<ApiResponse<LiveUserInfoVo>> fetchLoginInfo(@QueryMap HashMap<String, String> paramMap);

}
