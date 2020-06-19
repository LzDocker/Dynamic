package com.docker.common.model.page;
import com.docker.common.model.apiconfig.CommonApiData;
import com.docker.common.ui.base.NitCommonFragment;
import com.docker.common.ui.base.block.NitSampleBlockListFragment;
import com.docker.common.vm.base.NitCommonContainerViewModel;

/*
 *
 * 解析 api数据
 * */
public interface PageParser {

    public String getPageID();

    public void processPager(PageOptions pageOptions, NitCommonFragment nitCommonFragment);

    /*
     * 测试模式
     * */
    public void processTestPager(String name, NitCommonFragment nitCommonFragment);

    public void processSysMessage(String key, Object value);


    /*
     * vm 创建
     *
     * */
    public void onBlockVmCreated(NitCommonContainerViewModel nitCommonContainerViewModel, String frameitemblockid, NitSampleBlockListFragment nitSampleBlockList, int flag);


    /*
     * 增加
     * */
    public void addOptions(CommonApiData commonApiData);


    /*
     * 删除
     * */
    public void delOptions(CommonApiData commonApiData);


    /*
     * 修改
     * */
    public void updateOptions(CommonApiData commonApiData);


    public void recycle();

}
