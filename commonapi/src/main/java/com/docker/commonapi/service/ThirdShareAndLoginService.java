package com.docker.commonapi.service;

import android.content.Intent;

import com.alibaba.android.arouter.facade.template.IProvider;
import com.docker.core.command.ReplyCommandParam;

public interface ThirdShareAndLoginService extends IProvider {


    /*
     * 0 微信
     * 1 QQ
     * */
    public void thiredLogin(int plantform, ReplyCommandParam replyCommandParam);

    public void onHandResult(int requestCode, int resultCode, Intent data);


}
