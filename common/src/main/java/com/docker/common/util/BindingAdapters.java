package com.docker.common.util;

import androidx.databinding.BindingAdapter;

import com.docker.core.command.ReplyCommand;
import com.docker.core.command.ReplyCommandParam;
import com.docker.design.refresh.SmartRefreshLayout;


/**
 * SmartRefreshLayout
 */
public class BindingAdapters {
    @BindingAdapter({"onRefreshCommand"})
    public static void setonRefreshCommand(SmartRefreshLayout smartRefreshLayout, final ReplyCommand onRefreshCommand) {
        smartRefreshLayout.setOnRefreshListener(refreshLayout -> onRefreshCommand.exectue());
    }

    @BindingAdapter({"onloadmoreCommand"})
    public static void setonloadmoreCommand(SmartRefreshLayout smartRefreshLayout, final ReplyCommand onloadmoreCommand) {
        smartRefreshLayout.setOnLoadMoreListener(refreshLayout -> onloadmoreCommand.exectue());
    }

    @BindingAdapter({"oncompleteCommand"})
    public static void setoncompleteCommand(SmartRefreshLayout smartRefreshLayout, final ReplyCommandParam oncompleteCommand) {
        oncompleteCommand.exectue(smartRefreshLayout);
    }
}
