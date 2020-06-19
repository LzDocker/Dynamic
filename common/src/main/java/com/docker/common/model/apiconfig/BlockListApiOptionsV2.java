package com.docker.common.model.apiconfig;

import com.docker.common.config.Constant;
import com.docker.common.ui.base.NitCommonFragment;
import java.util.ArrayList;
import java.util.HashMap;

public class BlockListApiOptionsV2 extends ItemApiOptions {


    // block 内包含的card
    public ArrayList<CardApiOptions> mBlockCardOpList = new ArrayList<>();

    /*
    * block
    * */
    public HashMap<String, String> mBlockReqParam = new HashMap<>();


    public int RvUi = Constant.KEY_RVUI_LINER;


    public String mTitle;


    /*
    * 前端标记
    * */
    public transient String blockFrameId;

    /*
    * 前端标记
    * */
    public transient int blockFlag;


    public transient NitCommonFragment mFragment;



}
