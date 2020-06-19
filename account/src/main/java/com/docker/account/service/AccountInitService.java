package com.docker.account.service;

import android.util.Log;

import com.docker.core.command.ReplyCommand;
import com.docker.core.service.ApplicationTaskInitService;
import com.google.auto.service.AutoService;

@AutoService(ApplicationTaskInitService.class)
public class AccountInitService implements ApplicationTaskInitService {

    @Override
    public int getInitLevel() {
        return 0;
    }

    @Override
    public void Start() {

    }

    @Override
    public ReplyCommand GetReplyCommand() {
        return () -> {
            Log.d("TAG", "GetReplyCommand: =====================account 初始化完成");
        };
    }
}
