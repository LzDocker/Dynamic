package com.docker.core.widget;

import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.Toolbar;

import com.docker.core.R;

public class ToolBar {

    private ActionBar mActionBar;
    private Toolbar mToolbar;
    private TextView mTvTitle;
    private TextView mTvRight;
    private ImageView mIvRight;

    public ToolBar(Toolbar toolbar, ActionBar actionBar) {
        this.mToolbar = toolbar;
        this.mActionBar = actionBar;
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
        this.mTvTitle = (TextView)this.mToolbar.findViewById(R.id.tv_toolbar_title);
        this.mTvRight = (TextView)this.mToolbar.findViewById(R.id.btn_toolbar_right);
        this.mIvRight = (ImageView)this.mToolbar.findViewById(R.id.iv_toolbar_right);
        actionBar.setHomeAsUpIndicator(R.mipmap.ic_back);
    }

    public ToolBar(Toolbar toolbar, ActionBar actionBar, int themeColor) {
        this(toolbar, actionBar);
        if (themeColor != -1) {
            if (this.mTvTitle != null) {
                this.mTvTitle.setTextColor(-1);
            }
            if (this.mTvRight != null) {
                this.mTvRight.setTextColor(-1);
            }

            this.mActionBar.setHomeAsUpIndicator(R.mipmap.ic_toolbar_back);
        }

    }

    public View getToolBar() {
        return this.mToolbar;
    }

    public void show() {
        this.mToolbar.setVisibility(View.VISIBLE);
    }

    public void hide() {
        this.mToolbar.setVisibility(View.GONE);
    }

    public TextView setTitle(CharSequence charSequence) {
        if (this.mTvTitle != null) {
            this.mTvTitle.setText(charSequence);
        }

        return this.mTvTitle;
    }

    public TextView setTitle(CharSequence charSequence, int color) {
        if (this.mTvTitle != null) {
            this.mTvTitle.setText(charSequence);
            this.mTvTitle.setTextColor(color);
        }

        return this.mTvTitle;
    }

    public TextView setTvRight(CharSequence charSequence, View.OnClickListener onClickListener) {
        if (this.mTvRight != null) {
            this.mTvRight.setText(charSequence);
            this.mTvRight.setOnClickListener(onClickListener);
            this.mTvRight.setVisibility(View.VISIBLE);
        }

        return this.mTvRight;
    }

    public TextView setIvRight(int resId, View.OnClickListener onClickListener) {
        if (this.mIvRight != null) {
            this.mIvRight.setImageResource(resId);
            this.mIvRight.setOnClickListener(onClickListener);
            this.mIvRight.setVisibility(View.VISIBLE);
        }

        return this.mTvRight;
    }

    public void setNavigation(boolean enabled) {
        if (enabled) {
            this.mActionBar.setHomeAsUpIndicator(R.mipmap.ic_back);
        } else {
            this.mToolbar.setNavigationIcon((Drawable)null);
        }

    }

    public void setNavigationIndicator(int resId) {
        this.mActionBar.setHomeAsUpIndicator(resId);
    }

    public void setNavigationOnClickListener(View.OnClickListener onClickListener) {
        this.mToolbar.setNavigationOnClickListener(onClickListener);
    }

    public void setBackgroundColor(int color) {
        this.mToolbar.setBackgroundColor(color);
    }

    private ActionBar getActionBar() {
        return this.mActionBar;
    }

    private Toolbar getToolbar() {
        return this.mToolbar;
    }

    public TextView getTvTitle() {
        return this.mTvTitle;
    }

    public TextView getTvRight() {
        return this.mTvRight;
    }

    public ImageView getIvRight() {
        return this.mIvRight;
    }




}
