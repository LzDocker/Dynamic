package com.docker.commonapi.converter;

import com.docker.commonapi.service.DynamicConverMappingService;
import com.docker.commonapi.vo.base.DynamicDataBase;
import com.docker.commonapi.vo.base.ExtDataBase;
import com.docker.core.di.module.net.response.BaseResponse;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.ServiceLoader;

public class DynamicConverMappingManager {

    public HashMap<String, String> getMappingMap() {
        HashMap<String, String> mappingMap = new HashMap<>();
        ServiceLoader<DynamicConverMappingService> dynamicConverMappingServices = ServiceLoader.load(DynamicConverMappingService.class);
        for (DynamicConverMappingService dynamicConverMappingService : dynamicConverMappingServices) {
            mappingMap.putAll(dynamicConverMappingService.getMappingMap());
        }
        return mappingMap;
    }

    public Gson getGson() {
        MultiTypeJsonParser.Builder<ExtDataBase> builder = new MultiTypeJsonParser.Builder();
        try {
            Iterator<Map.Entry<String, String>> it = getMappingMap().entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry<String, String> entry = it.next();
                Class clazz = Class.forName(entry.getValue());
                builder.registerTypeElementValueWithClassType(entry.getKey(), clazz);
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        MultiTypeJsonParser<ExtDataBase> multiTypeJsonParser =
                builder.forceUseUpperTypeValue(true)
                        .registerTypeElementName("type")
                        .registerTargetClass(ExtDataBase.class)
                        .registerTargetUpperLevelClass(DynamicDataBase.class)
                        .build();
        return multiTypeJsonParser.getParseGson();
    }

    public TypeAdapter getTypeAdapter() {
        TypeAdapter<BaseResponse<List<DynamicDataBase>>> adapter
                = getGson().getAdapter(new TypeToken<BaseResponse<List<DynamicDataBase>>>() {
        });
        return adapter;
    }
}
