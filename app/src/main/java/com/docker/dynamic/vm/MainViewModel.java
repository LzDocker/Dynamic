package com.docker.dynamic.vm;

import androidx.hilt.lifecycle.ViewModelInject;

import com.docker.common.model.BaseItemModel;
import com.docker.common.vm.base.NitCommonListVm;
import com.docker.core.base.BaseViewModel;
import com.docker.core.di.module.net.repository.CommonRepository;

import java.util.Collection;

public class MainViewModel extends NitCommonListVm {

    @ViewModelInject
    public MainViewModel(CommonRepository commonRepository) {
        super(commonRepository);
    }


    @Override
    public Collection<? extends BaseItemModel> formatListData(Collection data) {
        return null;
    }

    @Override
    public BaseItemModel formatData(BaseItemModel data) {
        return null;
    }
}
