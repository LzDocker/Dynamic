package com.docker.common.model.apiconfig;

import java.util.ArrayList;
import java.util.HashMap;

// 后端存储的card配置
public class CardApiOptions extends ItemApiOptions {

    public int position;

    public int style;
    /*
     * 请求参数
     * */
    public HashMap<String, String> mApiOptions = new HashMap<>();

    /*
    * 是否悬停  filter 过滤器等一般使用
    * */
    public boolean isStick = false;

    /*
    * 是否关联 block
    *
    * eg: footer
    *
    * */
    public boolean isActionCard = false;


    /*
    * 关联的block
    * */
    public ArrayList<String> actionForBlocks = new ArrayList<>();
}