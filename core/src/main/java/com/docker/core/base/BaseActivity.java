package com.docker.core.base;

import android.content.Context;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;

import com.docker.core.R;
import com.docker.core.widget.ToolBar;
import com.gyf.immersionbar.ImmersionBar;

public abstract class BaseActivity<VM extends BaseViewModel, VB extends ViewDataBinding> extends AppCompatActivity {
    protected VB mBinding;
    public VM mViewModel;

    protected ToolBar mToolbar;
    private int mThemeColor = -1;

    protected abstract int getLayoutId();

    public abstract VM getmViewModel();

    private InputMethodManager mInputMethodManager;

    protected boolean isOverrideContentView = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (isOverrideContentView) {
            mBinding = DataBindingUtil.setContentView(this, getLayoutId());
        } else {
            if (this.mThemeColor == -1) {
                TypedValue typedValue = new TypedValue();
                this.getTheme().resolveAttribute(R.attr.colorPrimary, typedValue, true);
                this.mThemeColor = typedValue.data;
            }
            setContentView(R.layout.activity_base);
            LinearLayout rootView = findViewById(R.id.root_layout);
            mBinding = DataBindingUtil.inflate(getLayoutInflater(), getLayoutId(), rootView, false);
            initToolBar(rootView);
            if (this.mBinding != null) {
                rootView.addView(this.mBinding.getRoot(), new ViewGroup.LayoutParams(-1, -1));
            } else {
                rootView.addView(this.getLayoutInflater().inflate(this.getLayoutId(), (ViewGroup) null));
            }
        }
        initImmersionBar();
        mViewModel = getmViewModel();
        getLifecycle().addObserver(mViewModel);
    }

    /*
     *
     * 未覆盖父布局的默认包含一个toolbar
     * */
    protected void initToolBar(ViewGroup rootView) {
        View toolBar = this.getToolBar();
        if (toolBar != null) {
            rootView.addView(toolBar);
            Toolbar toolbar = (Toolbar) this.findViewById(R.id.toolbar);
            if (toolbar != null) {
                this.setSupportActionBar(toolbar);
            }
            this.mToolbar = new ToolBar(toolbar, this.getSupportActionBar(), this.mThemeColor);
            this.mToolbar.setTitle(String.valueOf(this.getTitle()));
            toolbar.setNavigationOnClickListener((v) -> {
                this.finish();
            });
        }

    }

    protected View getToolBar() {
        return this.getLayoutInflater().inflate(R.layout.toolbar, (ViewGroup) null);
    }

    public void initImmersionBar() {
        if (isOverrideContentView) {
            ImmersionBar.with(this).statusBarDarkFont(true).navigationBarDarkIcon(true).navigationBarColor("#ffffff").init();
        } else {
            ImmersionBar.with(this).fitsSystemWindows(true).navigationBarDarkIcon(true).navigationBarColor("#ffffff").statusBarDarkFont(true).titleBar(getToolBar()).statusBarColor(R.color.colorPrimary).init();
        }
    }


    @Override
    public void finish() {
        super.finish();
        hideSoftKeyBoard();
    }

    public void hideSoftKeyBoard() {
        View localView = getCurrentFocus();
        if (this.mInputMethodManager == null) {
            this.mInputMethodManager = ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE));
        }
        if ((localView != null) && (this.mInputMethodManager != null)) {
            this.mInputMethodManager.hideSoftInputFromWindow(localView.getWindowToken(), 2);
        }
    }

}
