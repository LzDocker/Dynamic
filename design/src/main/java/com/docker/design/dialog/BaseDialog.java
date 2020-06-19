package com.docker.design.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;

import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;

public abstract class BaseDialog<VB extends ViewDataBinding> extends Dialog implements Dialog.OnDismissListener {
    protected VB mBinding;
    private Context mContext;
    public BaseDialog(Context context) {
        super(context);
        mContext = context;
        onCreateDialog();
    }
    public BaseDialog(Context context, int themeResId) {
        super(context, themeResId);
        mContext = context;
        onCreateDialog();
    }
    protected BaseDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        mContext = context;
        onCreateDialog();
    }
    protected abstract int getLayoutId();
    protected abstract void setMessage(String msg);
    protected void onCreateDialog() {
        mBinding = DataBindingUtil.inflate(LayoutInflater.from(mContext), getLayoutId(), null, false);
        setContentView(mBinding.getRoot());
        setOnDismissListener(this);
    }
    public void showDialog() {
        if (isShowing()) return;
        show();
    }
    public void closeDialog() {
        if (!isShowing()) return;
        dismiss();
    }
}