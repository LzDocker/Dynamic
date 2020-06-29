package com.docker.commonapi.converter;

import androidx.annotation.NonNull;

import com.docker.commonapi.vo.base.DynamicDataBase;
import com.docker.core.di.module.net.response.BaseResponse;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;

import java.io.IOException;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Converter;

public class MultiTypeJsonResponseBodyConverter implements Converter<ResponseBody, BaseResponse<List<DynamicDataBase>>> {

    DynamicConverMappingManager dynamicConverMappingManager = new DynamicConverMappingManager();
    Gson gson = dynamicConverMappingManager.getGson();
    TypeAdapter<BaseResponse<List<DynamicDataBase>>> adapter = dynamicConverMappingManager.getTypeAdapter();

    @Override
    public BaseResponse<List<DynamicDataBase>> convert(@NonNull ResponseBody value) throws IOException {
        JsonReader jsonReader = gson.newJsonReader(value.charStream());
        try {
            return adapter.read(jsonReader);
        } finally {
            value.close();
        }
    }
}