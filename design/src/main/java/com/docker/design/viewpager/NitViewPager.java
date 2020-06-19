package com.docker.design.viewpager;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewParent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;
import com.donkingliang.consecutivescroller.ConsecutiveScrollerLayout;
import com.donkingliang.consecutivescroller.IConsecutiveScroller;

import java.util.ArrayList;
import java.util.List;

public class NitViewPager extends ViewPager implements IConsecutiveScroller {

    private int mAdjustHeight;

    public NitViewPager(@NonNull Context context) {
        super(context);
    }

    public NitViewPager(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (isConsecutiveParent() && mAdjustHeight > 0) {
            ConsecutiveScrollerLayout layout = (ConsecutiveScrollerLayout) getParent();
            int parentHeight = layout.getMeasuredHeight();
            int height = Math.min(parentHeight - mAdjustHeight, getDefaultSize(0, heightMeasureSpec));
            super.onMeasure(widthMeasureSpec,
                    MeasureSpec.makeMeasureSpec(height, MeasureSpec.getMode(heightMeasureSpec)));
        } else {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        }
    }

    private boolean isConsecutiveParent() {
        ViewParent parent = getParent();
        if (parent instanceof ConsecutiveScrollerLayout) {
            ConsecutiveScrollerLayout layout = (ConsecutiveScrollerLayout) parent;
            return layout.indexOfChild(this) == layout.getChildCount() - 1;
        }
        return false;
    }

    public int getAdjustHeight() {
        return mAdjustHeight;
    }

    public void setAdjustHeight(int adjustHeight) {
        if (mAdjustHeight != adjustHeight) {
            mAdjustHeight = adjustHeight;
            requestLayout();
        }
    }

    /**
     * 返回当前需要滑动的view。
     *
     * @return
     */
    @Override
    public View getCurrentScrollerView() {
        int count = getChildCount();
        for (int i = 0; i < count; i++) {
            View view = getChildAt(i);
            if (view.getX() == getScrollX() + getPaddingLeft()) {
                return view;
            }
        }
        return this;
    }

    /**
     * 返回全部需要滑动的下级view
     *
     * @return
     */
    @Override
    public List<View> getScrolledViews() {
        List<View> views = new ArrayList<>();
        int count = getChildCount();
        if (count > 0) {
            for (int i = 0; i < count; i++) {
                views.add(getChildAt(i));
            }
        }
        return views;
    }
}
