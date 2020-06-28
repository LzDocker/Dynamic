package com.docker.circle.service;

import android.app.Application;
import android.util.Log;

import com.docker.core.command.ReplyCommand;
import com.docker.core.service.ApplicationTaskInitService;
import com.google.auto.service.AutoService;

@AutoService(ApplicationTaskInitService.class)
public class CircleInitService implements ApplicationTaskInitService {

    @Override
    public int getInitLevel() {
        return 1;
    }

    @Override
    public void dispatcherApplication(Application application) {

    }

    @Override
    public void Start() {

    }

    @Override
    public ReplyCommand GetReplyCommand() {
        return () -> Log.d("TAG", "exectue: ====================circle 初始化完成");
    }
}
