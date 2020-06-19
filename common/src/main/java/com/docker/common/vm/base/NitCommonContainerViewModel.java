package com.docker.common.vm.base;

import androidx.hilt.lifecycle.ViewModelInject;
import androidx.lifecycle.MediatorLiveData;

import com.docker.common.model.BaseItemModel;
import com.docker.core.di.module.net.repository.CommonRepository;

import java.util.Collection;

/*
 *
 * mark
 *
 * 只做继承使用
 * */
public class NitCommonContainerViewModel extends NitCommonListVm {

    /*
     * vm 数据源
     * */
    public final MediatorLiveData<Object> mContainerLiveData = new MediatorLiveData<Object>();

    @ViewModelInject
    public NitCommonContainerViewModel(CommonRepository commonRepository) {
        super(commonRepository);
    }

    @Override
    public Collection<? extends BaseItemModel> formatListData(Collection data) {
        return data;
    }

    @Override
    public BaseItemModel formatData(BaseItemModel data) {
        return data;
    }

    @Override
    public void refresh() {
        super.refresh();
    }
}
