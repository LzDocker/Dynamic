package com.docker.account.api;

import androidx.lifecycle.LiveData;

import com.docker.account.vo.RegistVo;
import com.docker.common.vo.UserInfoVo;
import com.docker.commonapi.vo.RstVo;
import com.docker.core.di.module.net.response.ApiResponse;
import com.docker.core.di.module.net.response.BaseResponse;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface AccountService {

    /*
     * 登录
     * */
    @POST("api.php?m=login")
    @FormUrlEncoded
    LiveData<ApiResponse<BaseResponse<UserInfoVo>>> login(@FieldMap HashMap<String, String> paramMap);

    /* 重置密码
     * */
    @POST("api.php?m=user.resetPassword")
    @FormUrlEncoded
    LiveData<ApiResponse<BaseResponse<String>>> resetPwd(@FieldMap HashMap<String, String> paramMap);

    /*
     * 发送验证码
     * */
    @POST("api.php?m=login.send_pcode")
    @FormUrlEncoded
    LiveData<ApiResponse<BaseResponse<RstVo>>> sendSmsCode(@FieldMap HashMap<String, String> paramMap);


    /*
     * 注册
     * */
    @POST("api.php?m=login.register")
    @FormUrlEncoded
    LiveData<ApiResponse<BaseResponse<RegistVo>>> register(@FieldMap HashMap<String, String> paramMap);


}
