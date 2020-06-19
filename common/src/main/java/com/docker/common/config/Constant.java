package com.docker.common.config;

public class Constant {

    // 页面传值key nitlistactivity  /  nitfragment
    public static final String CommonListParam = "CommonListParam";

    public final static String ParamTrans = "ParamTrans";

    public final static String COMMONH5 = "/COMMON/h5";
    public final static String COMMON_SAMPLE_BLOCK_ITEM_LIST = "/COMMON/BLOCK_ITEM_LIST";
    public static final String mCOMMON_COUTAINER_PATH = "/COMMON/coutainer";



    //--------------------nitcommonlist-------------
    /*
     *    0  内部控制 （多用在 viewpager 嵌套）
     *
     *   1  禁用刷新 启用加载更多 （多用在首页 详情页）
     *
     *   2  禁用smart
     *
     *   3  smartc纯滚动
     * */
    public static final int KEY_REFRESH_OWNER = 0;
    public static final int KEY_REFRESH_ONLY_LOADMORE = 1;
    public static final int KEY_REFRESH_NOUSE = 2;
    public static final int KEY_REFRESH_PURSE = 3;

    //recycleview 布局管理器
    /*
     *  0  线性
     *  1  grid 2  每行两个
     *  2  横向
     * */
    public static final int KEY_RVUI_LINER = 0;
    public static final int KEY_RVUI_GRID2 = 1;
    public static final int KEY_RVUI_HOR = 2;
    public static final int KEY_RVUI_GRID5 = 5;
    public static final int KEY_RVUI_GRID3 = 3;
    public static final int KEY_RVUI_GRID4 = 4;
    public static final int KEY_RVUI_STAGE = 6;


    public static final String mBLOCK_TYPE_CARD = "card";
    public static final String mBLOCK_TYPE_WEB = "block_web";
    public static final String mBLOCK_TYPE_LIST = "block_list";
    public static final String mBLOCK_TYPE_TAB = "block_tab";



}
