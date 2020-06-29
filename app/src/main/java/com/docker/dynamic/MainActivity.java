package com.docker.dynamic;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.lifecycle.ViewModelProvider;

import com.alibaba.android.arouter.launcher.ARouter;
import com.docker.common.ui.base.NitCommonActivity;
import com.docker.core.base.BaseActivity;
import com.docker.core.di.test.AnalyticsService;
import com.docker.core.utils.FragmentUtils;
import com.docker.dynamic.databinding.ActivityMainBinding;
import com.docker.dynamic.ui.test.IndexBlockSampleFragmentV2;
import com.docker.dynamic.vm.MainViewModel;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;

@AndroidEntryPoint
public class MainActivity extends NitCommonActivity<MainViewModel, ActivityMainBinding> {


    @Inject
    OkHttpClient okHttpClient;

    @Inject
    AnalyticsService analyticsService;

    @Inject
    Retrofit retrofit;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public MainViewModel getmViewModel() {
        return new ViewModelProvider(this).get(MainViewModel.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        retrofit = retrofit.newBuilder().baseUrl(HttpUrl.parse("http://www.test.com")).build();
        Log.d("TAG", "onCreate:====== " + okHttpClient);
        Log.d("TAG", "onCreate:====3333== " + retrofit.hashCode());
        Log.d("TAG", "onCreate:====4444== " + retrofit.baseUrl());
        Log.d("TAG", "onCreate:====555== " + retrofit.callAdapterFactories().size());

        for (int i = 0; i < retrofit.callAdapterFactories().size(); i++) {
            Log.d("TAG", "onCreate: ===" + retrofit.callAdapterFactories().get(i).toString());
        }

        analyticsService.analyticsMethods();

        findViewById(R.id.tv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                ARouter.getInstance().build("/ACCOUNT/index").navigation();
                ARouter.getInstance().build("/CIRCLE/index").navigation();
//                ARouter.getInstance().build(RouterManager.getInstance().getMemoryRouterPath("CIRCLE_INDEX")).navigation();

        /*        ARouter.getInstance().build("/circle/index").navigation(MainActivity.this, new NavCallback() {
                    @Override
                    public void onArrival(Postcard postcard) {
                        Log.d("TAG", "onArrival: ");
                    }

                    @Override
                    public void onFound(Postcard postcard) {
                        super.onFound(postcard);
                        Log.d("TAG", "onFound: ");
                    }

                    @Override
                    public void onLost(Postcard postcard) {
                        super.onLost(postcard);
                        Log.d("TAG", "onLost: ");
                    }
                });*/

//                Intent intent = new Intent(MainActivity.this, CircleIndexActivity.class);
//                startActivity(intent);
            }
        });
    }

    @Override
    public void initView() {

        FragmentUtils.add(getSupportFragmentManager(), IndexBlockSampleFragmentV2.getInstance(), R.id.frame);
    }

    @Override
    public void initObserver() {

    }
}
