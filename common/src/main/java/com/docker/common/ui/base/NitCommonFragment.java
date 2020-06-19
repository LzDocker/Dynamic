package com.docker.common.ui.base;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.ViewModelProvider;

import com.docker.common.command.NitDelegetCommand;
import com.docker.common.event.NitEventMessageManager;
import com.docker.common.vm.base.NitCommonVm;
import com.docker.core.base.BaseFragment;
import com.docker.core.base.BaseViewModel;
import com.docker.design.dialog.DialogWait;
import com.docker.design.refresh.SmartRefreshLayout;
import com.jeremyliao.liveeventbus.LiveEventBus;

import java.util.Map;

import javax.inject.Inject;

public abstract class NitCommonFragment<VM extends BaseViewModel, VB extends ViewDataBinding> extends BaseFragment<VM, VB> {
    public DialogWait dialogWait;
    @Inject
    public ViewModelProvider.Factory factory;

    public void onLoadMore(SmartRefreshLayout refreshLayout) {
    }

    public void onReFresh(SmartRefreshLayout refreshLayout) {
    }

    public void showWaitDialog(String message) {
        if (dialogWait == null) {
            dialogWait = new DialogWait(getHoldingActivity());
        }
        dialogWait.setMessage(message);
        dialogWait.show();
    }

    public void hidWaitDialog() {
        if (getHoldingActivity() != null && dialogWait != null) {
            dialogWait.dismiss();
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        for (int i = 0; i < NitEventMessageManager.getInstance().mEvents.size(); i++) {
            for (Map.Entry<String, Class> entry : NitEventMessageManager.getInstance().mEvents.entrySet()) {
                LiveEventBus
                        .get(entry.getKey(), entry.getValue())
                        .observe(NitCommonFragment.this, (Object o) -> {
                            processSysMessage(entry.getKey(), o);
                            Log.d("sss", "onChanged: ======message================" + NitCommonFragment.this.getTag());
                        });
            }
        }
    }

    public NitDelegetCommand providerNitDelegetCommand(int flag) {
        return null;
    }

    public void processSysMessage(String key, Object value) {

    }
}
