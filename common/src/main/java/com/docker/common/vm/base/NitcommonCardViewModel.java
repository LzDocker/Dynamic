package com.docker.common.vm.base;

import android.text.TextUtils;

import androidx.hilt.lifecycle.ViewModelInject;
import androidx.lifecycle.LiveData;

import com.docker.common.model.BaseItemModel;
import com.docker.core.di.module.net.repository.CommonRepository;
import com.docker.core.di.module.net.response.ApiResponse;
import com.docker.core.di.module.net.response.BaseResponse;
import com.docker.design.stateview.EmptyStatus;

import java.util.Collection;
import java.util.HashMap;

public class NitcommonCardViewModel extends NitCommonListVm {


    @ViewModelInject
    public NitcommonCardViewModel(CommonRepository commonRepository) {
        super(commonRepository);
    }


    @Override
    public void loadData() {
//        mItems.clear();
        mIsfirstLoad = false;
        mEmptycommand.set(EmptyStatus.BdHiden);
        mCompleteCommand.set(true);
        mCompleteCommand.notifyChange();


//      /*
//       响应链
//       旧值回传
//
//
//      ObservableInt age = new ObservableInt();
//        ObservableInt age2 = new ObservableInt();
//
//        AddAddressVo addressVo = new AddAddressVo(0,0);
//
//
//        ObservableInt visibility = new ObservableInt(age,age2,addressVo){
//            @Override
//            public int get() {
//                if(age.get()>13 && age2.get()==12 && addressVo.isNoNetNeed){
//                    return View.GONE;
//                }else {
//                    return View.VISIBLE;
//                }
//            }
//        };
//*/
//
//
//        MediatorLiveData<UserInfoVo> userInfoVoLiveData = new MediatorLiveData<>();
//        MediatorLiveData<String> stringMediatorLiveData = new MediatorLiveData<>();
//        MediatorLiveData<String> stringMediatorLiveData1 = new MediatorLiveData<>();
//        MediatorLiveData<String> stringMediatorLiveData2 = new MediatorLiveData<>();
//
//        MediatorLiveData<Object> mediatorLiveData = new MediatorLiveData();
//
//
//        mediatorLiveData.addSource(userInfoVoLiveData, userInfoVo -> {
//
//        });
//
//        mediatorLiveData.addSource(stringMediatorLiveData, s -> {
//
//        });
//        mediatorLiveData.addSource(stringMediatorLiveData1, s -> {
//
//        });
//        mediatorLiveData.addSource(stringMediatorLiveData2, s -> {
//
//        });
//
//
//        // 全局单利 mediatorLiveData
//        // addSource 系统变量
//        // 任一变量发生改变  把改变转换为事件  通知  所有 observer 即 ui
//        // ui 收到通知 检测是否有注册该事件的card  有则通知card  调用card的command 方法
//
//        LiveData<Object> objectLiveData = Transformations.map(userInfoVoLiveData, (Function<UserInfoVo, Object>) input -> input.uid);
//
//        Transformations.switchMap(userInfoVoLiveData, new Function<UserInfoVo, LiveData<ApiResponse<BaseResponse>>>() {
//            @Override
//            public LiveData<ApiResponse<BaseResponse>> apply(UserInfoVo input) {
//                return getServicefun(input.logoUrl, null);
//            }
//        });


    }


    @Override
    public BaseItemModel formatData(BaseItemModel data) {
        return null;
    }

    @Override
    public Collection<? extends BaseItemModel> formatListData(Collection data) {
        return null;
    }

    @Override
    public LiveData<ApiResponse<BaseResponse>> getServicefun(String apiurl, HashMap param) {
        return null;
    }

    public void onCardFrameVisible() {
        if (!TextUtils.isEmpty((CharSequence) mCommonListReq.externs.get("NeedVisibleRefresh"))) {
            onJustRefresh();
        }
    }

}
