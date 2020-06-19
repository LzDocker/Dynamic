package com.docker.common.event;

import android.text.TextUtils;

import com.docker.common.model.card.BaseCardVo;
import com.docker.core.command.ReplyCommand;
import com.docker.core.utils.AppExecutors;
import com.jeremyliao.liveeventbus.LiveEventBus;

import java.util.HashMap;

/*
 * 全局系统消息  下发至 card
 * */
public class NitEventMessageManager {

    private NitEventMessageManager() {
    }

    private static class Holder {
        public static final NitEventMessageManager INSTANCE = new NitEventMessageManager();
    }

    public static NitEventMessageManager getInstance() {
        return Holder.INSTANCE;
    }

    public HashMap<String, Class> mEvents = new HashMap<>();

    /*
     * 事件发生调用这里用来通知全局
     * */
    public void SendSystemEventMessage(String eventType, Object o) {
        LiveEventBus
                .get(eventType)
                .post(o);
    }

    /*
     * 事件类型
     * 事件改变的对象类型
     *
     * 例如 0 UserInfoVo.class
     * */
    public void registerSystemEvent(String eventType, Class ResultType) {
        mEvents.put(eventType, ResultType);
    }


    public void ProcessMessage(BaseCardVo baseCardVo, String key, Object value, AppExecutors appExecutors) {
        if (baseCardVo.mEventMap.containsKey(key)) {
            appExecutors.mainThread().execute(() -> {
                if (value instanceof EventParam) {
                    EventParam eventParam = (EventParam) value;
                    if (TextUtils.isEmpty(eventParam.mPageID)) {  // 不包含pageid 全局响应
                        ReplyCommand command = (ReplyCommand) baseCardVo.mEventMap.get(key);
                        command.exectue();
                        baseCardVo.onEventTouchIng(key, value);
                    } else {  // 包含pageid 则页面响应
                        if (!TextUtils.isEmpty(eventParam.mPageID) && (eventParam.mPageID).equals(baseCardVo.mPageID)) {
                            ReplyCommand command = (ReplyCommand) baseCardVo.mEventMap.get(key);
                            command.exectue();
                            baseCardVo.onEventTouchIng(key, value);
                        }
                    }
                } else {
                    ReplyCommand command = (ReplyCommand) baseCardVo.mEventMap.get(key);
                    command.exectue();
                    baseCardVo.onEventTouchIng(key, value);
                }
            });
        }
    }
}
