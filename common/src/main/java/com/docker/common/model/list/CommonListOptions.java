package com.docker.common.model.list;

import com.docker.common.config.Constant;

import java.io.Serializable;
import java.util.HashMap;

// 加载 commonlistfragment  入参
public class CommonListOptions implements Serializable {

    public CommonListOptions() {
        super();
        this.frameItemblockId = String.valueOf(System.currentTimeMillis());
    }

    /*
     *   0  内部控制 （多用在 viewpager 嵌套）
     *
     *   1  禁用刷新 启用加载更多 （多用在首页 详情页）
     *
     *   2  禁用smart
     *
     *   3  smartc纯滚动
     * */
    public int refreshState = Constant.KEY_REFRESH_PURSE;


    // 请求参数
    public HashMap<String, String> ReqParam = new HashMap<>();


    //recycleview 布局管理器
    /*
     *  0  线性
     *  1  流式布局
     * */
    public int RvUi = Constant.KEY_RVUI_LINER;


    //recycleview 条目拖拽
    /*
     * */
    public boolean RvDrg = false;


    /*
     *  后端传递url的时候，必传
     * */
    public String ApiUrl;


    /*
     * flag  标记coutainercommand
     * */
    public int falg = 0;


    // 参数
    public HashMap<String, Object> externs = new HashMap<>();


    // card 或 list fragment 使用的父容器是否是activity
    public boolean isActParent = false;


    // 随机生成 内部使用
    public String frameItemblockId;

    // vm class
    public transient Class mBlockVm;

    // vm path
    public String mBlockVmPath;

}