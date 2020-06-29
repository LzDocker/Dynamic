package com.docker.commonapi.service.impl;

import com.docker.commonapi.service.DynamicConverMappingService;
import com.google.auto.service.AutoService;

import java.util.HashMap;

@AutoService(DynamicConverMappingService.class)
public class CommonApiMappingImpl implements DynamicConverMappingService {

    @Override
    public HashMap<String, String> getMappingMap() {

        HashMap<String, String> param = new HashMap<>();
        /*
         * 文章
         * */
        param.put("news", "com.docker.cirlev2.vo.pro.News");
        /*
         * 动态
         * */
        param.put("dynamic", "com.docker.cirlev2.vo.pro.Dynamic");

        /*
         * 问答
         * */
        param.put("answer", "com.docker.cirlev2.vo.pro.Answer");

        /*
         * 商品
         * */
        param.put("goods", "com.docker.cirlev2.vo.pro.Goods");

        /*
         * 活动
         * */
        param.put("activity", "com.docker.active.vo.ActiveVo");

        return param;
    }
}
