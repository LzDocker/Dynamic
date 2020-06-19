package com.docker.core.base;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.Fragment;

import com.docker.core.utils.AutoClearedValue;
import com.gyf.immersionbar.components.ImmersionFragment;

public abstract class BaseFragment<VM extends BaseViewModel, VB extends ViewDataBinding> extends ImmersionFragment {

    public AutoClearedValue<VB> mBinding;
    public VM mViewModel;

    public BaseFragment() {
    }

    protected abstract int getLayoutId();

    protected abstract VM getViewModel();

    protected abstract void initView(View var1);

    @Nullable
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        VB dataBinding = DataBindingUtil.inflate(inflater, this.getLayoutId(), container, false);
        this.mBinding = new AutoClearedValue(this, dataBinding);
        this.initView(mBinding.get().getRoot());
        return mBinding.get().getRoot();
    }

    @Override
    public void onAttach(Context activity) {
        super.onAttach(activity);
        this.mViewModel = this.getViewModel();
        if (this.mViewModel != null) {
            this.getLifecycle().addObserver(this.mViewModel);
        }
    }

    protected BaseActivity getHoldingActivity() {
        return (BaseActivity) this.getActivity();
    }
}
