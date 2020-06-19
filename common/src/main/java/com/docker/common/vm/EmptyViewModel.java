package com.docker.common.vm;

import androidx.hilt.lifecycle.ViewModelInject;

import com.docker.common.vm.base.NitCommonVm;
import com.docker.core.base.BaseViewModel;
import com.docker.core.di.module.net.repository.CommonRepository;

public class EmptyViewModel extends BaseViewModel {

    @ViewModelInject
    public EmptyViewModel() {

    }


}
