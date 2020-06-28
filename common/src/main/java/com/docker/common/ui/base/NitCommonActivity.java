package com.docker.common.ui.base;

import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;

import androidx.annotation.Nullable;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.Fragment;

import com.blankj.utilcode.util.AppUtils;
import com.blankj.utilcode.util.FragmentUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.docker.common.R;
import com.docker.common.command.NitDelegetCommand;
import com.docker.common.config.Constant;
import com.docker.common.event.NitEventMessageManager;
import com.docker.common.vm.base.NitCommonBaseVm;
import com.docker.common.vm.base.NitCommonListVm;
import com.docker.core.base.BaseActivity;
import com.docker.core.base.BaseViewModel;
import com.docker.design.dialog.DialogWait;
import com.jeremyliao.liveeventbus.LiveEventBus;
import com.lzf.easyfloat.EasyFloat;
import com.lzf.easyfloat.enums.SidePattern;
import com.lzf.easyfloat.interfaces.OnInvokeView;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

public abstract class NitCommonActivity<VM extends BaseViewModel, VB extends ViewDataBinding> extends BaseActivity<VM, VB> {

    private DialogWait dialogWait;

    /*
     * 页面默认参数
     * */
    public String mDefaultParam;

    public String mRouterName;

    /*
     * 页面能够提供的KEY
     * */
    public HashMap<String, String> mPageProviderKeys = new HashMap<>();
    /*
    *
    * */
    public HashMap<String, Object> mPageProviderObjs = new HashMap<>();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDefaultParam = getIntent().getStringExtra(Constant.ParamDefTrans);
        initView();
        initObserver();
        for (int i = 0; i < NitEventMessageManager.getInstance().mEvents.size(); i++) {
            for (Map.Entry<String, Class> entry : NitEventMessageManager.getInstance().mEvents.entrySet()) {
                LiveEventBus.get(entry.getKey(), entry.getValue())
                        .observe(NitCommonActivity.this, (Object o) -> {
                            processSysMessage(entry.getKey(), o);
                            Log.d("common", "onChanged: =============entry.getKey()=========" + NitCommonActivity.this.getComponentName());
                        });
            }
        }
        if (AppUtils.isAppDebug()) {
            showFloatView();
        }
    }

    public abstract void initView();

    public abstract void initObserver();


    public void showWaitDialog(String message) {
        if (dialogWait == null) {
            dialogWait = new DialogWait(this);
        }
        dialogWait.setMessage(message);
        dialogWait.show();
    }

    public void hidWaitDialog() {
        if (this != null && dialogWait != null) {
            dialogWait.dismiss();
        }
    }

    /*
     *
     * 提供给内层 listfragment vm代理
     * */
    public NitDelegetCommand providerNitDelegetCommand(int flag) {
        return null;
    }

    public void processSysMessage(String key, Object value) {

    }

    public void showFloatView() {
        EasyFloat.with(this)
                // 设置浮窗xml布局文件，并可设置详细信息
                .setLayout(R.layout.common_float_window_testmode, (OnInvokeView) view -> view.setOnClickListener(v -> {

                    Fragment fragment = FragmentUtils.getTopShow(getSupportFragmentManager());
                    if (fragment != null) {
                        if (fragment instanceof NitCommonFragment) {
                            if (((NitCommonFragment) fragment).mViewModel instanceof NitCommonListVm) {
                                ((NitCommonListVm) ((NitCommonFragment) fragment).mViewModel).setTest(!((NitCommonListVm) ((NitCommonFragment) fragment).mViewModel).isTest);
                            }
                        }
                    } else {
                        ToastUtils.showShort("未找到NitCommonFragment");
                    }
                }))
                // 设置吸附方式，共15种模式，详情参考SidePattern
                .setSidePattern(SidePattern.RESULT_HORIZONTAL)
                // 设置浮窗的标签，用于区分多个浮窗
                .setTag("testFloat")
                // 设置浮窗是否可拖拽，默认可拖拽
                .setDragEnable(true)
                // 系统浮窗是否包含EditText，仅针对系统浮窗，默认不包含
                .hasEditText(false)
                // 设置浮窗固定坐标，ps：设置固定坐标，Gravity属性和offset属性将无效
//                .setLocation(100, 200)
                // 设置浮窗的对齐方式和坐标偏移量
                .setGravity(Gravity.RIGHT | Gravity.BOTTOM, 0, -200)
                // 设置宽高是否充满父布局，直接在xml设置match_parent属性无效
                .setMatchParent(false, false)
                // 设置Activity浮窗的出入动画，可自定义，实现相应接口即可（策略模式），无需动画直接设置为null
                .setAnimator(null)
                .show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }


}
