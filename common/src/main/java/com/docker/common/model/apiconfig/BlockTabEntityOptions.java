package com.docker.common.model.apiconfig;


import com.docker.common.config.Constant;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

public class BlockTabEntityOptions extends  ItemApiOptions implements Serializable {

    /*
     * web
     *
     * list
     *
     * tab 暂不支持
     * */
    public String mType;



    /*
     * 在tab 中展示的名字
     * */
    public String mTitle;


    /*
     * block 布局方式
     * */
    public int RvUi = Constant.KEY_RVUI_LINER;


    // block 内包含的card
    public ArrayList<CardApiOptions> mBlockCardOpList = new ArrayList<>();

    /*
     * block
     * */
    public HashMap<String, String> mBlockReqParam = new HashMap<>();


    /*
     * block
     *
     * key:   url
     *
     * value: url 携带的参数
     *
     * */
    public String url;


}
