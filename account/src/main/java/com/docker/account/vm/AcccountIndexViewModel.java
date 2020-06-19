package com.docker.account.vm;

import android.util.Log;

import androidx.hilt.lifecycle.ViewModelInject;
import androidx.lifecycle.MediatorLiveData;

import com.docker.account.api.AccountService;
import com.docker.commonapi.card.CardFactory;
import com.docker.core.base.BaseViewModel;
import com.docker.core.di.module.cache.CacheStrategy;
import com.docker.core.di.module.net.repository.CommonRepository;
import com.docker.core.di.module.net.repository.NitBoundCallback;
import com.docker.core.di.module.net.repository.NitNetBoundObserver;
import com.docker.core.di.module.net.repository.Resource;

import java.util.HashMap;
import java.util.List;

import retrofit2.Call;

public class AcccountIndexViewModel extends BaseViewModel {

    CommonRepository commonRepository;
    AccountService circleService;
    CardFactory cardFactory;

    @ViewModelInject
    public AcccountIndexViewModel(CommonRepository commonRepository, AccountService circleService, CardFactory cardFactory) {
        this.circleService = circleService;
        this.commonRepository = commonRepository;
        this.cardFactory = cardFactory;
    }
    public final MediatorLiveData<String> liveData = new MediatorLiveData<>();
    public void test() {
        cardFactory.getCardList();
    }
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
    public void fetchDynamicList() {
        //fechCircleInfoList
        HashMap<String, String> param = new HashMap<>();     //t=goods&index_bottom_catid=4&page=1
        param.put("t", "goods");
        param.put("index_bottom_catid", "4");
        param.put("page", "1");
        liveData.addSource(commonRepository.ChacheFeatch(circleService.fechCircleInfoList(param),
                CacheStrategy.FIRST_CACHE_THEN_REQUEST, "111"),
                new NitNetBoundObserver<>(new NitBoundCallback<List<Object>>() {
//        liveData.addSource(commonRepository.noneChache(circleService.fechCircleInfoList(param)), new NitNetBoundObserver<List<ServiceDataBean>>(new NitBoundCallback<List<ServiceDataBean>>() {
                    @Override
                    public void onLoading(Call call) {
                        super.onLoading(call);
                        Log.d("TAG", "onLoading: ===============real=====");
                    }

                    @Override
                    public void onLoading() {
                        super.onLoading();
                        Log.d("TAG", "onLoading: ====================");
                    }
                    @Override
                    public void onCacheComplete(List<Object> Result) {
                        super.onCacheComplete(Result);
                        Log.d("TAG", "onCacheComplete: ===============" + Result);
//                        Log.d("TAG", Result.get(0).getInputtime() + "onCacheComplete: ===============" + Result);
                    }
                    @Override
                    public void onComplete(Resource<List<Object>> resource) {
                        super.onComplete(resource);
                        Log.d("TAG", "onComplete: ================");
                    }
                    @Override
                    public void onComplete() {
                        super.onComplete();
                        Log.d("TAG", "onComplete: ================");
                    }
                    @Override
                    public void onBusinessError(Resource<List<Object>> resource) {
                        super.onBusinessError(resource);
                        Log.d("TAG", "onBusinessError: ================");
                    }
                    @Override
                    public void onNetworkError(Resource<List<Object>> resource) {
                        super.onNetworkError(resource);
                        Log.d("TAG", "onNetworkError: ================");
                    }
                }));
    }
    @Override
    protected void onCleared() {
        super.onCleared();
        Log.d("TAG", "onCleared: ==========indexccircle=============");
    }
}
