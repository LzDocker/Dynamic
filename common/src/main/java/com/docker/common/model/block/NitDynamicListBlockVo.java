package com.docker.common.model.block;
import com.docker.common.model.apiconfig.BlockListApiOptionsV2;
import com.docker.common.model.apiconfig.ItemApiOptions;
import com.docker.common.util.CacheUtils;

/*
 *
 * 动态 block
 *
 * 只需要提供 vm 和 参数 即可
 * */
public class NitDynamicListBlockVo extends NitBaseBlockVo {


    public NitDynamicListBlockVo(ItemApiOptions mDefBlockOptions) {
        super(mDefBlockOptions);
    }

    @Override
    public void initParam(ItemApiOptions mDefBlockOptions) {
        BlockListApiOptionsV2 blockListApiOptionsV2 = null;
        if (mDefBlockOptions == null) {
            blockListApiOptionsV2 = new BlockListApiOptionsV2();
        } else {
            blockListApiOptionsV2 = (BlockListApiOptionsV2) mDefBlockOptions;
        }
        this.mDefBlockOptions = blockListApiOptionsV2;
        mVmPath = "com.docker.cirlev2.vm.CircleDynamicListViewModel";
        if (blockListApiOptionsV2.mBlockReqParam.size() == 0) {
            blockListApiOptionsV2.mBlockReqParam.put("t", "dynamic");
        }
        if (CacheUtils.getUser() != null) {
            blockListApiOptionsV2.mBlockReqParam.put("uuid", CacheUtils.getUser().uuid);
            blockListApiOptionsV2.mBlockReqParam.put("memberid", CacheUtils.getUser().uid);
        }
    }

}
