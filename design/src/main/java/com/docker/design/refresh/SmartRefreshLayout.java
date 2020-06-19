package com.docker.design.refresh;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;

import androidx.databinding.BindingAdapter;
import androidx.databinding.InverseBindingAdapter;
import androidx.databinding.InverseBindingListener;

import com.docker.design.R;
import com.scwang.smartrefresh.layout.constant.RefreshState;

/**
 * 智能刷新布局
 * Intelligent RefreshLayout
 * Created by scwang on 2017/5/26.
 */
@SuppressLint("RestrictedApi")
@SuppressWarnings({"unused"})
public class SmartRefreshLayout extends com.scwang.smartrefresh.layout.SmartRefreshLayout {

    /*
     * binding 属性
     * */
    private boolean bdcomplete;
    private boolean bdenable;
    private boolean bdenablemore;
    private boolean bdenablenodata;


    public void setmFooterTranslationViewId(int viewid) {
        this.mFooterTranslationViewId = viewid;
        notifyStateChanged(RefreshState.None);
    }

    //<editor-fold desc="构造方法 construction methods">
    public SmartRefreshLayout(Context context) {
        this(context, null);
    }

    public SmartRefreshLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.SmartRefreshLayout);
        bdcomplete = ta.getBoolean(R.styleable.design_SmartRefreshLayout_bdcomplete, false);
        bdenable = ta.getBoolean(R.styleable.design_SmartRefreshLayout_bdenable, false);
        bdenablemore = ta.getBoolean(R.styleable.design_SmartRefreshLayout_bdenablemore, false);
        bdenablenodata = ta.getBoolean(R.styleable.design_SmartRefreshLayout_bdenablenodata, false);
        ta.recycle();
    }

    ///////////////////=============================

    public boolean getmEnableRefresh() {
        return mEnableRefresh;
    }

    public void setmEnableRefresh(boolean mEnableRefresh) {
        this.mEnableRefresh = mEnableRefresh;
    }

    @BindingAdapter("srlEnableRefreshAttrChanged")
    public static void setSrlEnableRefreshAttrChanged(SmartRefreshLayout view, InverseBindingListener inverseBindingListener) {
        if (inverseBindingListener == null) {
            view.setListener(null);
        } else {
            view.setListener(inverseBindingListener::onChange);
        }
    }

    @BindingAdapter(value = "srlEnableRefresh")
    public static void setmEnableRefresh(SmartRefreshLayout view, boolean srlEnableRefresh) {
        view.setmEnableRefresh(srlEnableRefresh);
    }

    @InverseBindingAdapter(attribute = "srlEnableRefresh", event = "srlEnableRefreshAttrChanged")
    public static boolean getSrlEnableRefreshAttrChanged(SmartRefreshLayout view) {
        return view.getmEnableRefresh();
    }


    //===================================
    public boolean getmEnableLoadMore() {
        return mEnableLoadMore;
    }

    public void setmEnableLoadMore(boolean mEnableLoadMore) {
        this.mEnableLoadMore = mEnableLoadMore;
    }


    @BindingAdapter("SrlEnableLoadMoreAttrChanged")
    public static void setSrlEnableLoadMoreAttrChanged(SmartRefreshLayout view, InverseBindingListener inverseBindingListener) {
        if (inverseBindingListener == null) {
            view.setListener(null);
        } else {
            view.setListener(inverseBindingListener::onChange);
        }
    }

    @BindingAdapter(value = "SrlEnableLoadMore")
    public static void setmEnableLoadMore(SmartRefreshLayout view, boolean setSrlEnableLoadMore) {
        view.setmEnableLoadMore(setSrlEnableLoadMore);
    }

    @InverseBindingAdapter(attribute = "SrlEnableLoadMore", event = "SrlEnableLoadMoreAttrChanged")
    public static boolean getmEnableLoadMoreAttrChanged(SmartRefreshLayout view) {
        return view.getmEnableLoadMore();
    }


    ////==============================
    public boolean getBdenablemore() {
        return bdenablemore;
    }

    public void setBdenablemore(boolean bdenablemore) {
        this.bdenablemore = bdenablemore;
    }

    @BindingAdapter("bdenablemoreAttrChanged")
    public static void setRefresbdenablemoreAttrChanged(SmartRefreshLayout view, InverseBindingListener inverseBindingListener) {
        if (inverseBindingListener == null) {
            view.setListener(null);
        } else {
            view.setListener(inverseBindingListener::onChange);
        }
    }

    @BindingAdapter(value = "bdenablemore", requireAll = false)
    public static void setbdenablemore(SmartRefreshLayout view, boolean bdenablemore) {
        view.setBdenablemore(bdenablemore);
        view.setEnableLoadMore(bdenablemore);
    }

    @InverseBindingAdapter(attribute = "bdenablemore", event = "bdenablemoreAttrChanged")
    public static boolean getBdenablemore(SmartRefreshLayout view) {
        return view.getBdenablemore();
    }
    //=======没有更多数据======

    public boolean isBdenablenodata() {
        return bdenablenodata;
    }

    public void setBdenablenodata(boolean bdenablenodata) {
        this.bdenablenodata = bdenablenodata;
    }


    @BindingAdapter("bdenablenodataAttrChanged")
    public static void setbdenablenodataAttrChanged(SmartRefreshLayout view, InverseBindingListener inverseBindingListener) {
        if (inverseBindingListener == null) {
            view.setListener(null);
        } else {
            view.setListener(inverseBindingListener::onChange);
        }
    }

    @BindingAdapter(value = "bdenablenodata", requireAll = false)
    public static void setbdenablenodata(SmartRefreshLayout view, boolean bdenable) {
        view.setNoMoreData(bdenable);
    }

    @InverseBindingAdapter(attribute = "bdenablenodata", event = "bdenablenodataAttrChanged")
    public static boolean getbdenablenodata(SmartRefreshLayout view) {
        return view.isBdenablenodata();
    }

    //============


    ///////
    public boolean getBdenable() {
        return bdenable;
    }

    public void setBdenable(boolean bdenable) {
        this.bdenable = bdenable;
    }

    @BindingAdapter("bdenableAttrChanged")
    public static void setRefreshbdenableAttrChanged(SmartRefreshLayout view, InverseBindingListener inverseBindingListener) {
        if (inverseBindingListener == null) {
            view.setListener(null);
        } else {
            view.setListener(inverseBindingListener::onChange);
        }
    }

    @BindingAdapter(value = "bdenable", requireAll = false)
    public static void setbdenable(SmartRefreshLayout view, boolean bdenable) {
        view.setBdenable(bdenable);
        view.setEnableLoadMore(bdenable);
    }

    @InverseBindingAdapter(attribute = "bdenable", event = "bdenableAttrChanged")
    public static boolean getbdenable(SmartRefreshLayout view) {
        return view.getBdenable();
    }

    //------------------------------------
    public boolean getBdcomplete() {
        return bdcomplete;
    }

    public void setBdcomplete(boolean bdcomplete) {
        this.bdcomplete = bdcomplete;
    }

    @BindingAdapter("bdcompleteAttrChanged")
    public static void setRefreshcompleteAttrChanged(SmartRefreshLayout view, InverseBindingListener inverseBindingListener) {
        if (inverseBindingListener == null) {
            view.setListener(null);
        } else {
            view.setListener(inverseBindingListener::onChange);
        }
    }

    @BindingAdapter(value = "bdcomplete", requireAll = false)
    public static void setcomplete(SmartRefreshLayout view, boolean bdcomplete) {
        view.setBdcomplete(bdcomplete);
        if (!bdcomplete) {
            view.finishRefresh(bdcomplete);
            view.finishLoadMore(bdcomplete);
        } else {
            view.finishRefresh();
            view.finishLoadMore();
        }
    }

    @InverseBindingAdapter(attribute = "bdcomplete", event = "bdcompleteAttrChanged")
    public static boolean getcomplete(SmartRefreshLayout view) {
        return view.getBdcomplete();
    }

    private OnValueChangedListener listener;

    public interface OnValueChangedListener {
        void onValueChanged();
    }

    public void setListener(OnValueChangedListener listener) {
        this.listener = listener;
    }
}
