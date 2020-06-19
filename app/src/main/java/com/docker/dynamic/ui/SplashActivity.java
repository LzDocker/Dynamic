package com.docker.dynamic.ui;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import com.docker.circle.vm.CircleIndexViewModel;
import com.docker.common.ui.base.NitCommonActivity;
import com.docker.common.vm.EmptyViewModel;
import com.docker.core.base.BaseActivity;
import com.docker.dynamic.MainActivity;
import com.docker.dynamic.R;
import com.docker.dynamic.databinding.ActivitySplashBinding;
import com.gyf.immersionbar.ImmersionBar;

public class SplashActivity extends NitCommonActivity<EmptyViewModel, ActivitySplashBinding> {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        isOverrideContentView = true;
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_splash;
    }

    @Override
    public EmptyViewModel getmViewModel() {
        return new ViewModelProvider(this).get(EmptyViewModel.class);
    }

    @Override
    public void initView() {

        mBinding.tv.setOnClickListener(v -> {
            Intent intent = new Intent(SplashActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        });
    }

    @Override
    public void initObserver() {

    }

    @Override
    public void initImmersionBar() {
        ImmersionBar.with(this).fullScreen(true).init();
    }
}