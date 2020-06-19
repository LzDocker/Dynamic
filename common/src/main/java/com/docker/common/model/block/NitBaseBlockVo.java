package com.docker.common.model.block;

import com.docker.common.R;
import com.docker.common.model.BaseSampleItem;
import com.docker.common.model.OnItemClickListener;
import com.docker.common.model.apiconfig.ItemApiOptions;

/*
 * block
 *
 * xml 展示
 *
 * */
public abstract class NitBaseBlockVo extends BaseSampleItem {

    @Override
    public int getItemLayout() {
        return R.layout.common_block_eg_item;
    }

    @Override
    public OnItemClickListener getOnItemClickListener() {
        return (item, view) -> {
            // 点击进入block测试界面
        };
    }

    /*
     * block 配置
     * */
    public ItemApiOptions mDefBlockOptions;


    public NitBaseBlockVo(ItemApiOptions mDefBlockOptions) {
        this.mDefBlockOptions = mDefBlockOptions;
        initParam(mDefBlockOptions);
    }

    public abstract void initParam(ItemApiOptions mDefBlockOptions);


}
