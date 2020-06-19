package com.docker.circle;

import android.os.Bundle;
import android.util.Log;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.docker.circle.api.CircleService;
import com.docker.circle.databinding.ActivityCircleIndexBinding;
import com.docker.circle.di.CircleClinetQuali;
import com.docker.circle.di.CircleRetorfitQuali;
import com.docker.circle.service.CircleRouterConstantService;
import com.docker.circle.vm.CircleIndexViewModel;
import com.docker.commonapi.api.CommonService;
import com.docker.commonapi.router.RouterManager;
import com.docker.commonapi.router.RouterParam;
import com.docker.core.base.BaseActivity;
import com.docker.core.command.ReplyCommandParam;
import com.docker.core.di.module.net.repository.CommonRepository;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;

@Route(path = CircleRouterConstantService.CIRCLE_INDEX)
@AndroidEntryPoint
public class CircleIndexActivity extends BaseActivity<CircleIndexViewModel, ActivityCircleIndexBinding> {

    @CircleRetorfitQuali
    @Inject
    Retrofit retrofit;

    @CircleClinetQuali
    @Inject
    OkHttpClient okHttpClient;

    @Inject
    OkHttpClient commonokHttpClient;

    @Inject
    CommonRepository commonRepository;

    @Inject
    CircleService circleService;

    @Inject
    RouterManager routerManager;

    @Inject
    CommonService commonService;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_circle_index;
    }

    @Override
    public CircleIndexViewModel getmViewModel() {
        return new ViewModelProvider(this).get(CircleIndexViewModel.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


//        Log.d("TAG", "onCreate: =====CircleIndexActivity============" + retrofit.hashCode());
//        Log.d("TAG", "onCreate: =====CircleIndexActivity============" + retrofit.baseUrl());
//        Log.d("TAG", "onCreate:====555== " + retrofit.callAdapterFactories().size());
//
//        Log.d("TAG", "onCreate:====555== " + okHttpClient.interceptors().size());
//        Log.d("TAG", "onCreate:====666== " + commonokHttpClient.interceptors().size());
//
//        for (int i = 0; i < retrofit.callAdapterFactories().size(); i++) {
//            Log.d("TAG", "onCreate: ===" + retrofit.callAdapterFactories().get(i).toString());
//        }

//        circleService.fechCircleList(param).observe(this, new Observer<ApiResponse<BaseResponse<String>>>() {
//            @Override
//            public void onChanged(ApiResponse<BaseResponse<String>> baseResponseApiResponse) {
//
//            }
//        });


        mViewModel.liveData.observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                Log.d("TAG", "onChanged: =============" + s);
            }
        });

        findViewById(R.id.tv_click).setOnClickListener(v -> {
//            mViewModel.fetchDynamicList();
//            cardFactory.getCardList();
//            mViewModel.test();

//            ARouter.getInstance().build(RouterManager.getInstance().getMemoryRouterPath("ACCOUNT_INDEX")).navigation();

            routerManager.Jump(new RouterParam.RouterBuilder("ACCOUNT_INDEX").create());

//            routerManager.initService(commonService).initRouterData(CircleIndexActivity.this, (ReplyCommandParam) o -> Log.d("TAG", "exectue: =======初始化路由==============="));
        });
    }


}