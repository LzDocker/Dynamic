package com.docker.design.indexctor;

import android.content.Context;

import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.SimplePagerTitleView;

/**
 * Created by hackware on 2016/7/24.
 */

public class ColorFlipPagerTitleView extends SimplePagerTitleView {
    private float mChangePercent = 0.5f;

    private float nomalSize = 0;
    private float selectSize = 0;

    public ColorFlipPagerTitleView(Context context) {
        super(context);
    }

    @Override
    public void onLeave(int index, int totalCount, float leavePercent, boolean leftToRight) {
        if (leavePercent >= mChangePercent) {
            setTextColor(mNormalColor);
            if (nomalSize > 0) {
                setTextSize(nomalSize);
            }
        } else {
            setTextColor(mSelectedColor);
            if (selectSize > 0) {
                setTextSize(selectSize);
            }
        }
    }

    @Override
    public void onEnter(int index, int totalCount, float enterPercent, boolean leftToRight) {
        if (enterPercent >= mChangePercent) {
            setTextColor(mSelectedColor);
            if (selectSize > 0) {
                setTextSize(selectSize);
            }
        } else {
            setTextColor(mNormalColor);
            if (nomalSize > 0) {
                setTextSize(nomalSize);
            }
        }
    }

    @Override
    public void onSelected(int index, int totalCount) {
    }

    @Override
    public void onDeselected(int index, int totalCount) {
    }

    public float getChangePercent() {
        return mChangePercent;
    }

    public void setChangePercent(float changePercent) {
        mChangePercent = changePercent;
    }


    public void setSelectTextSize(Float size) {
        this.selectSize = size;
    }

    public void setNomalTextSize(Float size) {
        this.nomalSize = size;
    }
}
