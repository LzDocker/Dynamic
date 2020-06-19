package com.docker.common.model.apiconfig;

import java.io.Serializable;

public class ItemApiOptions implements Serializable {

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


}
