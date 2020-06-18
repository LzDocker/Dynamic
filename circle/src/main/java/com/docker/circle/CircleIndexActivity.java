package com.docker.circle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.util.Log;

import com.docker.circle.api.CircleService;
import com.docker.circle.di.CircleClinetQuali;
import com.docker.circle.di.CircleRetorfitQuali;
import com.docker.circle.vm.CircleIndexViewModel;
import com.docker.core.di.module.net.repository.CommonRepository;
import com.docker.core.di.module.net.repository.NitBoundCallback;
import com.docker.core.di.module.net.repository.NitNetBoundObserver;
import com.docker.core.di.module.net.repository.Resource;

import java.util.HashMap;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;

@AndroidEntryPoint
public class CircleIndexActivity extends AppCompatActivity {

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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_circle_index);

        CircleIndexViewModel circleIndexViewModel = new ViewModelProvider(this).get(CircleIndexViewModel.class);


        Log.d("TAG", "onCreate: =====CircleIndexActivity============" + retrofit.hashCode());
        Log.d("TAG", "onCreate: =====CircleIndexActivity============" + retrofit.baseUrl());
        Log.d("TAG", "onCreate:====555== " + retrofit.callAdapterFactories().size());

        Log.d("TAG", "onCreate:====555== " + okHttpClient.interceptors().size());
        Log.d("TAG", "onCreate:====666== " + commonokHttpClient.interceptors().size());

        for (int i = 0; i < retrofit.callAdapterFactories().size(); i++) {
            Log.d("TAG", "onCreate: ===" + retrofit.callAdapterFactories().get(i).toString());
        }


//        circleService.fechCircleList(param).observe(this, new Observer<ApiResponse<BaseResponse<String>>>() {
//            @Override
//            public void onChanged(ApiResponse<BaseResponse<String>> baseResponseApiResponse) {
//
//            }
//        });


        circleIndexViewModel.liveData.observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                Log.d("TAG", "onChanged: =============" + s);
            }
        });

        circleIndexViewModel.fetchCircleList();
    }


}