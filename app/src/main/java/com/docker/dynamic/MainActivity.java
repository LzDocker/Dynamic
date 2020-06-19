package com.docker.dynamic;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.alibaba.android.arouter.facade.Postcard;
import com.alibaba.android.arouter.facade.callback.NavCallback;
import com.alibaba.android.arouter.launcher.ARouter;
import com.docker.circle.CircleIndexActivity;
import com.docker.core.di.module.cookie.CookieJarImpl;
import com.docker.core.di.module.net.qualifier.Client;
import com.docker.core.di.test.AnalyticsService;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;

@AndroidEntryPoint
public class MainActivity extends AppCompatActivity {


    @Inject
    OkHttpClient okHttpClient;

    @Inject
    AnalyticsService analyticsService;

    @Inject
    Retrofit retrofit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        retrofit = retrofit.newBuilder().baseUrl(HttpUrl.parse("http://www.test.com")).build();
        Log.d("TAG", "onCreate:====== " + okHttpClient);
        Log.d("TAG", "onCreate:====3333== " + retrofit.hashCode());
        Log.d("TAG", "onCreate:====4444== " + retrofit.baseUrl());
        Log.d("TAG", "onCreate:====555== " +  retrofit.callAdapterFactories().size());

        for (int i = 0; i < retrofit.callAdapterFactories().size(); i++) {
            Log.d("TAG", "onCreate: ==="+retrofit.callAdapterFactories().get(i).toString());
        }

        analyticsService.analyticsMethods();

        findViewById(R.id.tv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ARouter.getInstance().build("/circle/index").navigation(MainActivity.this, new NavCallback() {
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
                });

//                Intent intent = new Intent(MainActivity.this, CircleIndexActivity.class);
//                startActivity(intent);
            }
        });
    }
}
