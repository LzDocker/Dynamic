package com.docker.common.util;

import com.blankj.utilcode.util.ToastUtils;
import com.docker.common.vm.base.NitCommonVm;
import com.docker.core.di.module.net.repository.NitBoundCallback;
import com.docker.core.di.module.net.repository.Resource;

import retrofit2.Call;

public abstract class NitCommonBoundCallBack<T> extends NitBoundCallback<T> {

    public NitCommonBoundCallBack() {

    }

    private NitCommonVm nitCommonVm;

    public NitCommonBoundCallBack(NitCommonVm nitCommonVm) {
        this.nitCommonVm = nitCommonVm;
    }

    /*
     * 缓存读取成功
     * */
    public void onCacheComplete(T Result) {

    }

    public void onComplete(Resource<T> resource) {

    }

    public void onComplete() {
        if (nitCommonVm != null) {
            nitCommonVm.hideDialogWait();
        }
    }

    public void onBusinessError(Resource<T> resource) {
        if (resource.message != null) {
            ToastUtils.showShort(resource.message);
        }
    }

    public void onNetworkError(Resource<T> resource) {
        if (resource.message != null) {
            ToastUtils.showShort(resource.message);
        } else {
            ToastUtils.showShort("网络失败请重试");
        }
    }

    public void onLoading() {
        if (nitCommonVm != null) {
            nitCommonVm.showDialogWait("加载中...", false);
        }
    }

    public void onLoading(Call call) {

    }

}
