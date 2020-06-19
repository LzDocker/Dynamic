package com.docker.core.service;

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
    public ReplyCommand GetReplyCommand();

}
