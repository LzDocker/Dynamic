package com.docker.account.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.util.Log;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.docker.account.R;
import com.docker.account.vm.AcccountIndexViewModel;
import com.docker.commonapi.router.RouterConstKey;
import com.docker.commonapi.router.RouterManager;
import com.docker.commonapi.router.RouterParam;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

import static com.docker.account.service.AccountRouterConstantService.ACCOUNT_INDEX;

@Route(path = ACCOUNT_INDEX)
@AndroidEntryPoint
public class AccountIndexActivity extends AppCompatActivity {

    @Inject
    RouterManager routerManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_index);
        AcccountIndexViewModel model = new ViewModelProvider(this).get(AcccountIndexViewModel.class);
        findViewById(R.id.tv).setOnClickListener(v -> {
//            model.fetchDynamicList();
            routerManager.Jump(new RouterParam.RouterBuilder(RouterConstKey.ACCOUNT_LOGIN).create());
        });

        model.liveData.observe(this, s -> Log.d("TAG", "onChanged: =================="));

//        testaccount tt = new testaccount();
//        tt.tedt();
    }
}