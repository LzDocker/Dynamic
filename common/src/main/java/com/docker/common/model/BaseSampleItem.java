package com.docker.common.model;

import androidx.databinding.ObservableInt;

import com.docker.common.model.card.BaseCardVo;
import com.docker.core.command.ReplyCommand;
import java.util.HashMap;

public abstract class BaseSampleItem extends BaseItem {

// 新增测试模式点击事件
    @Override
    public OnItemClickListener getOnItemClickListener() {

        return null;
    }

    public OnTestModeClickListener onTestModeClickListener;

    public OnTestModeClickListener getOnTestModeClickListener() {
        return onTestModeClickListener;
    }

    public void setOnTestModeClickListener(OnTestModeClickListener onTestModeClickListener) {
        this.onTestModeClickListener = onTestModeClickListener;
    }

    public void onTestModeClick(BaseItemModel item) {

        //ARouter.getInstance().build(AppRouter.BLOCK_INDEX_TEST).withString("baseSampleItem", ((BaseSampleItem) item).sampleName).navigation();
    }

    /*
     * card 请求服务端的参数
     * */
    public HashMap<String, String> mRepParamMap = new HashMap<>();


    public String mVmPath;

    public transient Class mVmClazz;


    public transient ObservableInt mStateLv = new ObservableInt(1);

    /*
     * 标示card 所在的界面id
     * */
    public String mPageID;

    /*
     * key 系统事件类型
     * value 响应系统事件的处理方法
     *
     * */
    public transient HashMap<String, ReplyCommand> mEventMap = new HashMap<>();


    /*
     * 有些事件需要特殊处理 重写此方法来处理 参数为 事件类型 事件入参
     * */
    public void onEventTouchIng(String eventType, Object param) {
        if (this instanceof BaseCardVo && ((BaseCardVo) this).mNitcommonCardViewModel != null) {
            ((BaseCardVo) this).mNitcommonCardViewModel.loadCardData((BaseCardVo) this);
        }
    }


}
