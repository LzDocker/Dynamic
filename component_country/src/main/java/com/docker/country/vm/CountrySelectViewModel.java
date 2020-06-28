package com.docker.country.vm;

import androidx.hilt.lifecycle.ViewModelInject;
import androidx.lifecycle.MediatorLiveData;

import com.docker.common.util.NitCommonBoundCallBack;
import com.docker.common.vm.base.NitCommonVm;
import com.docker.core.di.module.net.repository.CommonRepository;
import com.docker.core.di.module.net.repository.NitNetBoundObserver;
import com.docker.core.di.module.net.repository.Resource;
import com.docker.country.api.CountrySelectService;
import com.docker.country.vo.WorldNumListHot;

public class CountrySelectViewModel extends NitCommonVm {

    CountrySelectService countrySelectService;

    @ViewModelInject
    public CountrySelectViewModel(CommonRepository commonRepository, CountrySelectService countrySelectService) {
        super(commonRepository);
        this.countrySelectService = countrySelectService;
    }

    public final MediatorLiveData<WorldNumListHot> mCountryLisliveData = new MediatorLiveData<>();

    public void getCountryList(String type) {
        mCountryLisliveData.addSource(commonRepository.noneChache(countrySelectService.featchWorldList(type)),
                new NitNetBoundObserver<>(new NitCommonBoundCallBack<WorldNumListHot>(this) {
                    @Override
                    public void onComplete(Resource<WorldNumListHot> resource) {
                        super.onComplete(resource);
                        mCountryLisliveData.setValue(resource.data);
                    }
                }));
    }

}
