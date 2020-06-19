package com.docker.design.dialog;

import android.content.Context;
import android.content.DialogInterface;

import com.docker.design.R;
import com.docker.design.databinding.DesignDialogWaitingBinding;

public class DialogWait extends BaseDialog<DesignDialogWaitingBinding> {
    public DialogWait(Context context) {
        super(context, R.style.DialogStyle);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.design_dialog_waiting;
    }

    @Override
    public void setMessage(String message) {
        mBinding.setMessage(message);
    }

    @Override
    public void onDismiss(DialogInterface dialogInterface) {
    }


}
