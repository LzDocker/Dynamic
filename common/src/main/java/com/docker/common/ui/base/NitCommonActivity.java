//package com.docker.common.ui.base;
//
//import android.os.Bundle;
//import android.util.Log;
//
//import androidx.annotation.Nullable;
//import androidx.databinding.ViewDataBinding;
//import androidx.lifecycle.ViewModelProvider;
//import com.docker.common.util.command.NitDelegetCommand;
//import com.docker.core.base.BaseActivity;
//import com.docker.core.base.BaseAppliction;
//
//import java.util.Map;
//
//public abstract class NitCommonActivity<VM extends NitCommonBaseVm, VB extends ViewDataBinding> extends BaseActivity<VM, VB> {
//
//    private DialogWait dialogWait;
//
//    public ViewModelProvider.Factory factory;
//
//    @Override
//    protected void onCreate(@Nullable Bundle savedInstanceState) {
//        factory = ((BaseAppliction) getApplication()).factory;
//        super.onCreate(savedInstanceState);
//        initView();
//        initObserver();
//        initRouter();
//        for (int i = 0; i < NitEventMessageManager.getInstance().mEvents.size(); i++) {
//            for (Map.Entry<String, Class> entry : NitEventMessageManager.getInstance().mEvents.entrySet()) {
//                LiveEventBus.get(entry.getKey(), entry.getValue())
//                        .observe(NitCommonActivity.this, (Object o) -> {
//                            processSysMessage(entry.getKey(), o);
//                            Log.d("sss", "onChanged: =============entry.getKey()=========" + NitCommonActivity.this.getComponentName());
//                        });
//            }
//        }
//
//    }
//
//    public abstract void initView();
//
//    public abstract void initObserver();
//
//    public abstract void initRouter();
//
//
//    public void showWaitDialog(String message) {
//        if (dialogWait == null) {
//            dialogWait = new DialogWait(this);
//        }
//        dialogWait.setMessage(message);
//        dialogWait.show();
//    }
//
//    public void hidWaitDialog() {
//        if (this != null && dialogWait != null) {
//            dialogWait.dismiss();
//        }
//    }
//
//    /*
//     *
//     * 提供给内层 listfragment vm代理
//     * */
//    public NitDelegetCommand providerNitDelegetCommand(int flag) {
//        return null;
//    }
//
//    public void processSysMessage(String key, Object value) {
//
//    }
//
//
//}
