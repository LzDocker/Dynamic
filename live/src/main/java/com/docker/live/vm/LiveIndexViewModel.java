package com.docker.live.vm;

import androidx.hilt.lifecycle.ViewModelInject;

import com.docker.common.vm.base.NitCommonVm;
import com.docker.core.di.module.net.repository.CommonRepository;
import com.docker.live.api.LiveService;

public class LiveIndexViewModel extends NitCommonVm {

    private LiveService liveService;

    @ViewModelInject
    public LiveIndexViewModel(CommonRepository commonRepository, LiveService liveService) {
        super(commonRepository);
        this.liveService = liveService;
    }
    
}
