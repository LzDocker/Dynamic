package com.docker.core.service;

import android.util.Log;

import com.docker.core.command.ReplyCommand;

/*
 * application 初始化服务
 * */
public interface ApplicationTaskInitService {

    /*
     * 初始化等级 越小越先初始化
     * */
    public int getInitLevel();


    /*
     * 开始初始化
     * */
    public void Start();

    /*
     * 初始化结束回调*/
    public default ReplyCommand GetReplyCommand() {
        return () -> {
            Log.d("core", "GetReplyCommand: ==============" + getInitLevel() + "==初始化完成");
        };
    }
}
