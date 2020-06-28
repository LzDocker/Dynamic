package com.docker.commonapi.router;

import android.app.Activity;

import com.docker.design.recycledrg.ReponseReplayCommand;
import com.docker.design.recycledrg.ReponseReplayCommandV2;

public class RouterParam {


    /*
     * key  路由注册的key  对应 /routerinfo/ 改为Approuter 中的属性  必须参数
     *
     * param 跳转界面传递的参数 非必填  在到达的界面 使用  Constant.ParamTrans 作为key 来获取
     *
     * ispush 是否推送跳转 必传参数
     *
     * content code 非必须参数   传入则是 forResult 的跳转
     *
     * form  跳转前的标记 一般为跳转前的路由 key
     * */

    public String key;

    public Object param;

    public boolean ispush;

    public boolean isFormH5;

    public Activity context;

    public int code;

    public String from;

    public RouterCommand mRouterCommand;

    public RouterParam(RouterBuilder routerBuilder) {
        this.key = routerBuilder.key;
        this.param = routerBuilder.param;
        this.ispush = routerBuilder.ispush;
        this.context = routerBuilder.context;
        this.code = routerBuilder.code;
        this.isFormH5 = routerBuilder.isFormH5;
        this.from = routerBuilder.from;
        RouterCommand routerCommand = new RouterCommand();
        routerCommand.commandkey = routerBuilder.from + "@nit@" + routerBuilder.key;
        routerCommand.reponseReplayCommand = routerBuilder.mRosponseCommand;
        this.mRouterCommand = routerCommand;
    }


    public static class RouterBuilder {

        private String key;

        private Object param = null;

        private boolean ispush = false;

        private Activity context = null;

        private boolean isFormH5 = false;

        private int code = 0;

        public String from;

        public ReponseReplayCommandV2 mRosponseCommand;

        public RouterBuilder(String key) {
            this.key = key;
        }

        public RouterBuilder withParam(Object param) {
            this.param = param;
            return this;
        }

        public RouterBuilder withPush(boolean ispush) {
            this.ispush = ispush;
            return this;
        }

        public RouterBuilder withContext(Activity context, int code) {
            this.context = context;
            this.code = code;
            return this;
        }

        public RouterBuilder withFromH5() {
            this.isFormH5 = true;
            return this;
        }

        public RouterBuilder withFrom(String from) {
            this.from = from;
            return this;
        }

        public RouterBuilder withmRosponseCommand(ReponseReplayCommandV2 mRosponseCommand) {
            this.mRosponseCommand = mRosponseCommand;
            return this;
        }

        public RouterParam create() {
            return new RouterParam(this);
        }
    }

}
