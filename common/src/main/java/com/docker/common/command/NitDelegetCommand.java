package com.docker.common.command;

import com.docker.common.ui.base.NitCommonFragment;
import com.docker.common.vm.base.NitCommonListVm;

/*
 *
 *
 *
 *
 * */
public interface NitDelegetCommand {

    /*
     *提供外部 listvm
     * */
    Class providerOuterVm();


//    /*
//     * 初始化完成 所有的vm 后给外部调用
//     * */
//    void next(NitCommonListVm commonListVm, NitCommonListFragment nitCommonListFragment);

    /*
     * 初始化完成 所有的vm 后给外部调用
     * */
    void next(NitCommonListVm commonListVm, NitCommonFragment nitCommonFragment);

}
