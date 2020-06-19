package com.docker.common.model.apiconfig;



import com.docker.common.config.Constant;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

public class BlockTabEntity implements Serializable {

    /*
    * web
    *
    * list
    *
    * tab 暂不支持
    * */
    public String mType;

    /*
     * 后端存储唯一ID
     * */
    public String mUniqueID;


    /*
     * 唯一名字
     * */
    public String mUniqueName;


    // 是否是主block  一个界面只能存在一个
    public boolean isMainBlock;


    /*
    * 在tab 中展示的名字
    * */
    public String mTitle;

    /*
    * block 中包含的card 列表
    * */
    public ArrayList<CardApiOptions> mCards = new ArrayList<>();

    /*
    * block 请求参数
    * */
    public HashMap<String, String> mReqParam = new HashMap<>();

    /*
    * block 布局方式
    * */
    public int RvUi = Constant.KEY_RVUI_LINER;

    /*
    *
    * */
    public String mVmPath;

    /*
    * web url
    * */
    public String url;


    // 前端标记使用
    public String mPageID;


}
