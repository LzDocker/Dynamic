package com.docker.live.ui;

import android.content.Intent;
import android.os.Bundle;

import androidx.lifecycle.ViewModelProvider;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.docker.common.ui.base.NitCommonActivity;
import com.docker.live.R;
import com.docker.live.databinding.LiveActivityIndexBinding;
import com.docker.live.vm.LiveIndexViewModel;
import com.tencent.liteav.demo.lvb.liveroom.ui.LiveRoomActivity;

import dagger.hilt.android.AndroidEntryPoint;

import static com.docker.live.service.LiveRouterConstService.LIVE_INDEX;

@Route(path = LIVE_INDEX)
@AndroidEntryPoint
public class LiveIndexActivity extends NitCommonActivity<LiveIndexViewModel, LiveActivityIndexBinding> {

    @Override
    protected int getLayoutId() {
        return R.layout.live_activity_index;
    }

    @Override
    public LiveIndexViewModel getmViewModel() {
        return new ViewModelProvider(this).get(LiveIndexViewModel.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void initView() {

        mBinding.tvEnter.setOnClickListener(v -> {
            Intent intent = new Intent(LiveIndexActivity.this, LiveRoomActivity.class);
            startActivity(intent);
        });
    }


    @Override
    public void initObserver() {

    }
}