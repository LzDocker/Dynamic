package com.docker.account.ui;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.util.Log;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.docker.account.R;
import com.docker.account.service.AccountRouterConstantService;
import com.docker.account.vm.AcccountIndexViewModel;
import com.docker.commonapi.router.RouterManager;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;
@Route(path = AccountRouterConstantService.ACCOUNT_INDEX)
@AndroidEntryPoint
public class AccountIndexActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_index);
        AcccountIndexViewModel model = new ViewModelProvider(this).get(AcccountIndexViewModel.class);
        findViewById(R.id.tv).setOnClickListener(v -> {
//            model.fetchDynamicList();
        });

        model.liveData.observe(this, s -> Log.d("TAG", "onChanged: =================="));
    }
}