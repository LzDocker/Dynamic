package com.docker.commonapi.converter;

import androidx.annotation.Nullable;


import com.docker.commonapi.vo.base.DynamicDataBase;
import com.docker.core.di.module.net.response.BaseResponse;
import com.google.gson.reflect.TypeToken;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;

public class DynamicConverterFactory extends Converter.Factory {

    @Nullable
    @Override
    public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations, Retrofit retrofit) {
        if (type.toString().equals(new TypeToken<BaseResponse<List<DynamicDataBase>>>() {
        }.getType().toString())
                || type.toString().equals(new TypeToken<BaseResponse<DynamicDataBase>>() {
        }.getType().toString())) {
            /*
            解析不同类型的extdata
            */
            return new MultiTypeJsonResponseBodyConverter();
        }
        return null;
    }

    public static Converter.Factory create() {

        return new DynamicConverterFactory();
    }

}
