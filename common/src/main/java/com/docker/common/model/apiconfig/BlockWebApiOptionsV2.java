package com.docker.common.model.apiconfig;
import com.docker.common.ui.base.NitCommonFragment;

import java.util.HashMap;

public class BlockWebApiOptionsV2 extends ItemApiOptions {

    /*
     * block
     *
     * key:   url
     *
     * value: url 携带的参数
     *
     * */
    public String url;


    public HashMap<String, String> mBlockReqParam = new HashMap<>();


    public String mTitle;


    /*
     * 前端标记
     * */
    public transient int blockFlag;

    public transient NitCommonFragment mFragment;

}
