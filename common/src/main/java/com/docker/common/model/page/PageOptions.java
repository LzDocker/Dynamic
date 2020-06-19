package com.docker.common.model.page;
import com.docker.common.model.apiconfig.CommonApiData;

import java.io.Serializable;
import java.util.ArrayList;

// 页面配置
public class
PageOptions implements Serializable {

    // 页面类型
    /*
    * type = "detail"  // 详情
    * type =  "list"   // 列表
    * type = "index"   // 首页
    * type = "manager" // 管理界面
    * */
    public String mPageType;

    // 页面id
    public String mPageID;


    // 页面包含的card block
    public ArrayList<CommonApiData> mItemListOptions = new ArrayList<>();




}
