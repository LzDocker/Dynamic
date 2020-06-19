package com.docker.common.vm.base;

import androidx.databinding.ObservableBoolean;
import androidx.databinding.ObservableInt;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.OnLifecycleEvent;
import com.blankj.utilcode.util.ActivityUtils;
import com.docker.core.base.BaseViewModel;
import com.docker.core.command.RefreshCommand;
import com.docker.design.dialog.DialogWait;
import com.docker.design.stateview.EmptyStatus;

public abstract class NitCommonBaseVm extends BaseViewModel {

    private DialogWait dialogWait;

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    public void create() {
        initCommand();
        mEmptycommand.set(EmptyStatus.BdLoading);
    }

    public abstract void initCommand();

    public ObservableInt mEmptycommand = new ObservableInt();

    public boolean mIsfirstLoad = true;

    public ObservableBoolean mCompleteCommand = new ObservableBoolean();

    public RefreshCommand mCommand = new RefreshCommand();

    public boolean loadingState = false;

    public void showDialogWait(String message, boolean cancelable) {
        if (dialogWait == null) {
            dialogWait = new DialogWait(ActivityUtils.getTopActivity());
        }
        dialogWait.setMessage(message);
        dialogWait.show();
    }

    public void hideDialogWait() {
        if (dialogWait != null) {
            dialogWait.dismiss();
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    public void destory() {
        if (dialogWait != null) {
            dialogWait.dismiss();
        }
    }
}
