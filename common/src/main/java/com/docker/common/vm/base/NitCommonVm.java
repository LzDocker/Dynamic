package com.docker.common.vm.base;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;

import com.blankj.utilcode.util.LogUtils;
import com.docker.core.di.module.net.repository.CommonRepository;
import com.docker.core.di.module.net.repository.Resource;
import com.docker.core.di.module.net.response.ApiResponse;
import com.docker.core.di.module.net.response.BaseResponse;

public abstract class NitCommonVm<T> extends NitCommonBaseVm {

    protected CommonRepository commonRepository;

    public NitCommonVm(CommonRepository commonRepository) {
        this.commonRepository = commonRepository;
    }

    @Override
    public void initCommand() {

    }

    public MediatorLiveData<Resource<T>> RequestServer(LiveData<ApiResponse<BaseResponse<T>>> servicefun) {
        if (servicefun == null) {
            LogUtils.e("servicefun == null");
            return null;
        }
        return commonRepository.noneChache(servicefun);
    }

    public MediatorLiveData<Resource<T>> RequestSpeicalServer(LiveData<ApiResponse<T>> servicefun) {
        if (servicefun == null) {
            LogUtils.e("servicefun == null");
            return null;
        }
        return commonRepository.SpecialFeatch(servicefun);
    }

}
