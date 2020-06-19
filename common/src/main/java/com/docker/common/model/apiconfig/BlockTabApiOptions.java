package com.docker.common.model.apiconfig;

import androidx.fragment.app.Fragment;

import java.util.ArrayList;

public class BlockTabApiOptions extends ItemApiOptions {


    /*
    * 实际存储
    * */
    public ArrayList<BlockTabEntityOptions> mBlockEntity = new ArrayList<>();


    /*
    * 前端转换
    * */
    public  ArrayList<CommonApiData> mBlockApiDatas = new ArrayList<>();


    /*
    * 前端使用
    * */
    public transient ArrayList<Fragment> mFragments = new ArrayList<>();


}
