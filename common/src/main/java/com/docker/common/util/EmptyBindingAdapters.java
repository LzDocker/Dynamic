package com.docker.common.util;

import androidx.databinding.BindingAdapter;

import com.docker.core.command.EmptyCommand;
import com.docker.design.stateview.EmptyLayout;

public class EmptyBindingAdapters {
    @BindingAdapter(value = {"onRetryCommand"})
    public static void setonRetryCommand(EmptyLayout view, final EmptyCommand onRetryCommand) {
        view.setOnretryListener(() -> onRetryCommand.exectue());
    }
}
