package com.docker.common.model.card;

import android.view.View;

import androidx.databinding.Bindable;
import androidx.lifecycle.MediatorLiveData;

import com.blankj.utilcode.util.AppUtils;
import com.docker.common.model.apiconfig.CardApiOptions;
import com.docker.common.vm.base.NitCommonListVm;
import com.docker.common.model.BaseSampleItem;
import com.docker.common.model.OnItemClickListener;

import java.util.HashMap;

/*
 *
 * card 基类
 * */
public abstract class BaseCardVo<T> extends BaseSampleItem {

    /*
     * 默认card 配置
     * */
    public CardApiOptions mDefcardApiOptions;

    public transient NitCommonListVm mNitcommonCardViewModel;


    /*
     * 请求失败默认remove掉card    false
     *
     * 请求失败也展示card
     * */
    public boolean isNoNetNeed = true;



    public void upDateParam(HashMap<String, String> RepParamMap, boolean isAllReplace) {
        if (isAllReplace) {
            mRepParamMap.clear();
            mRepParamMap.putAll(RepParamMap);
        } else {
            mRepParamMap.putAll(RepParamMap);
        }
        if (mNitcommonCardViewModel != null) {
            mNitcommonCardViewModel.loadCardData(this);
        }
    }

    /*
     * 服务端数据存储的lv
     * */
    public transient MediatorLiveData<T> mCardVoLiveData = new MediatorLiveData<>();


    public BaseCardVo(int style, int position) {
        this.style = style;
        this.position = position;
    }


    public BaseCardVo(CardApiOptions cardApiOptions) {

        this.style = cardApiOptions.style;
        this.position = cardApiOptions.position;
        this.mDefcardApiOptions = cardApiOptions;

    }

    public int style = 0;

    public int position = 0;

    public int maxSupport = 1;


    public void onChangeStyleClick(BaseCardVo item, View view, NitCommonListVm viewModel) {
        if (AppUtils.isAppDebug()) {
            int style = item.style;
            if (maxSupport - 1 > style) {
                style++;
            } else {
                style--;
            }
            item.setStyle(style);
        }
    }

    @Override
    public OnItemClickListener getOnItemClickListener() {
        return (item, view) -> {
            BaseCardVo.this.onItemClick((BaseCardVo) item, view);
//            onChangeStyleClick((BaseCardVo) item, view, null);
        };
    }

    public abstract void onItemClick(BaseCardVo item, View view);

    @Bindable
    public int getStyle() {
        return style;
    }

    public void setStyle(int style) {
        this.style = style;
        notifyChange();
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public int getMaxSupport() {
        return maxSupport;
    }

    public void setMaxSupport(int maxSupport) {
        this.maxSupport = maxSupport;
    }

    public void initParam(Object o) {
    };
}
