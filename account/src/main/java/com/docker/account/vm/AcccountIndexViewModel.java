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

    @Override
    protected void onCleared() {
        super.onCleared();
        Log.d("TAG", "onCleared: ==========indexccircle=============");
    }
}
