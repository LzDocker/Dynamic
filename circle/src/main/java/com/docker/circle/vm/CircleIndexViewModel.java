package com.docker.circle.vm;

import android.util.Log;

import androidx.hilt.lifecycle.ViewModelInject;
import androidx.lifecycle.MediatorLiveData;

import com.docker.circle.api.CircleService;
import com.docker.core.base.BaseViewModel;
import com.docker.core.di.module.net.repository.CommonRepository;
import com.docker.core.di.module.net.repository.NitBoundCallback;
import com.docker.core.di.module.net.repository.NitNetBoundObserver;
import com.docker.core.di.module.net.repository.Resource;

import java.util.HashMap;

public class CircleIndexViewModel extends BaseViewModel {

    CommonRepository commonRepository;

    CircleService circleService;

    @ViewModelInject
    public CircleIndexViewModel(CommonRepository commonRepository, CircleService circleService) {
        this.circleService = circleService;
        this.commonRepository = commonRepository;
    }

    public final MediatorLiveData<String> liveData = new MediatorLiveData<>();

    public void fetchCircleList() {

        HashMap<String, String> param = new HashMap<>();
        param.put("111", "111");
        param.put("222", "111");
        param.put("333", "111");

        liveData.addSource(commonRepository.noneChache(circleService.fechCircleList(param)), new NitNetBoundObserver<String>(new NitBoundCallback<String>() {
            @Override
            public void onComplete(Resource<String> resource) {
                super.onComplete(resource);
                Log.d("TAG", "onComplete: ================");
            }

            @Override
            public void onComplete() {
                super.onComplete();
                Log.d("TAG", "onComplete: ================");
            }

            @Override
            public void onBusinessError(Resource<String> resource) {
                super.onBusinessError(resource);
                Log.d("TAG", "onBusinessError: ================");
            }

            @Override
            public void onNetworkError(Resource<String> resource) {
                super.onNetworkError(resource);
                Log.d("TAG", "onNetworkError: ================");
            }
        }));
    }


}
