package com.docker.common.model.block;

import android.text.TextUtils;

import com.docker.common.model.apiconfig.BlockWebApiOptionsV2;
import com.docker.common.model.apiconfig.ItemApiOptions;
import com.docker.common.util.CacheUtils;

/*
 *
 * web block
 * */
public class NitWebBlockVo extends NitBaseBlockVo {


    public NitWebBlockVo(ItemApiOptions mDefBlockOptions) {
        super(mDefBlockOptions);
    }

    @Override
    public void initParam(ItemApiOptions mDefBlockOptions) {
        BlockWebApiOptionsV2 blockWebApiOptionsV2 = null;
        if (mDefBlockOptions == null) {
            blockWebApiOptionsV2 = new BlockWebApiOptionsV2();
        } else {
            blockWebApiOptionsV2 = (BlockWebApiOptionsV2) mDefBlockOptions;
        }
        this.mDefBlockOptions = blockWebApiOptionsV2;

        if (TextUtils.isEmpty(blockWebApiOptionsV2.url)) {
            blockWebApiOptionsV2.url = "https://www.jianshu.com/p/abf592a71d02";
        }
        if (CacheUtils.getUser() != null) {
            blockWebApiOptionsV2.mBlockReqParam.put("uuid", CacheUtils.getUser().uuid);
            blockWebApiOptionsV2.mBlockReqParam.put("memberid", CacheUtils.getUser().uid);
        }
    }
}
