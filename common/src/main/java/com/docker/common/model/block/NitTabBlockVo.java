package com.docker.common.model.block;
import com.docker.common.config.Constant;
import com.docker.common.model.apiconfig.BlockListApiOptionsV2;
import com.docker.common.model.apiconfig.BlockTabApiOptions;
import com.docker.common.model.apiconfig.BlockTabEntityOptions;
import com.docker.common.model.apiconfig.BlockWebApiOptionsV2;
import com.docker.common.model.apiconfig.CommonApiData;
import com.docker.common.model.apiconfig.ItemApiOptions;

import java.util.ArrayList;

/*
 *
 * 动态 tab block
 * */
public class NitTabBlockVo extends NitBaseBlockVo {


    public NitTabBlockVo(ItemApiOptions mDefBlockOptions) {
        super(mDefBlockOptions);
    }


    private BlockTabApiOptions blockTabEntity2Real(ArrayList<BlockTabEntityOptions> blockTabEntityOptions, BlockTabApiOptions blockTabApiOptions) {
        for (int i = 0; i < blockTabEntityOptions.size(); i++) {
            BlockTabEntityOptions blockTabEntityOption = blockTabEntityOptions.get(i);
            CommonApiData commonApiData = new CommonApiData();
            commonApiData.mType = blockTabEntityOption.mType;
            ItemApiOptions itemApiOptions = null;
            switch (blockTabEntityOption.mType) {
                case Constant.mBLOCK_TYPE_LIST:
                    itemApiOptions = new BlockListApiOptionsV2();
                    ((BlockListApiOptionsV2) itemApiOptions).mTitle = blockTabEntityOption.mTitle;
                    ((BlockListApiOptionsV2) itemApiOptions).RvUi = blockTabEntityOption.RvUi;
                    ((BlockListApiOptionsV2) itemApiOptions).mBlockReqParam = blockTabEntityOption.mBlockReqParam;
                    ((BlockListApiOptionsV2) itemApiOptions).mBlockCardOpList = blockTabEntityOption.mBlockCardOpList;
                    ((BlockListApiOptionsV2) itemApiOptions).mUniqueID = blockTabEntityOption.mUniqueID;
                    ((BlockListApiOptionsV2) itemApiOptions).mUniqueName = blockTabEntityOption.mUniqueName;
                    ((BlockListApiOptionsV2) itemApiOptions).isMainBlock = blockTabEntityOption.isMainBlock;

                    break;
                case Constant.mBLOCK_TYPE_WEB:
                    itemApiOptions = new BlockWebApiOptionsV2();
                    ((BlockWebApiOptionsV2) itemApiOptions).mTitle = blockTabEntityOption.mTitle;
                    ((BlockWebApiOptionsV2) itemApiOptions).mBlockReqParam = blockTabEntityOption.mBlockReqParam;
//                    ((BlockWebApiOptionsV2)itemApiOptions).mBlockCardOpList = blockTabEntityOption.mBlockCardOpList;
                    ((BlockWebApiOptionsV2) itemApiOptions).mUniqueID = blockTabEntityOption.mUniqueID;
                    ((BlockWebApiOptionsV2) itemApiOptions).mUniqueName = blockTabEntityOption.mUniqueName;
                    ((BlockWebApiOptionsV2) itemApiOptions).isMainBlock = blockTabEntityOption.isMainBlock;
                    ((BlockWebApiOptionsV2) itemApiOptions).url = blockTabEntityOption.url;
                    break;
            }
            commonApiData.itemApiOptions = itemApiOptions;
            blockTabApiOptions.mBlockApiDatas.add(commonApiData);
        }
        return blockTabApiOptions;
    }

    @Override
    public void initParam(ItemApiOptions mDefBlockOptions) {
        BlockTabApiOptions blockTabApiOptions = null;
        if(mDefBlockOptions == null){
            blockTabApiOptions = new BlockTabApiOptions();
        }else {
            blockTabApiOptions= (BlockTabApiOptions) mDefBlockOptions;
            blockTabApiOptions= blockTabEntity2Real(blockTabApiOptions.mBlockEntity, blockTabApiOptions);
        }
        this.mDefBlockOptions = blockTabApiOptions;

        if (blockTabApiOptions.mBlockApiDatas.size() == 0) {
            CommonApiData commonApiData2 = new CommonApiData();
            commonApiData2.mType = Constant.mBLOCK_TYPE_LIST;
            BlockListApiOptionsV2 blockListApiOptionsV2 = new BlockListApiOptionsV2();
            blockListApiOptionsV2.mUniqueName = "NitDynamicListBlockVo";
            blockListApiOptionsV2.mTitle = "动态";
            commonApiData2.itemApiOptions = blockListApiOptionsV2;
            blockTabApiOptions.mBlockApiDatas.add(commonApiData2);

            CommonApiData commonApiData4 = new CommonApiData();
            commonApiData4.mType = Constant.mBLOCK_TYPE_LIST;
            BlockListApiOptionsV2 blockListApiOptionsV4 = new BlockListApiOptionsV2();
            blockListApiOptionsV4.mUniqueName = "NitDynamicListBlockVo";
            blockListApiOptionsV4.mBlockReqParam.put("t", "goods");
            blockListApiOptionsV4.mTitle = "动态";
            commonApiData4.itemApiOptions = blockListApiOptionsV4;
            blockTabApiOptions.mBlockApiDatas.add(commonApiData4);

            CommonApiData commonApiData3 = new CommonApiData();
            commonApiData3.mType = Constant.mBLOCK_TYPE_LIST;
            BlockListApiOptionsV2 blockListApiOptionsV3 = new BlockListApiOptionsV2();
            blockListApiOptionsV3.mUniqueName = "NitDynamicListBlockVo";
            blockListApiOptionsV3.mBlockReqParam.put("t", "idle");
            blockListApiOptionsV3.mTitle = "动态";
            commonApiData3.itemApiOptions = blockListApiOptionsV3;
            blockTabApiOptions.mBlockApiDatas.add(commonApiData3);


//            CommonApiData commonApiData1 = new CommonApiData();
//            commonApiData1.mType = Constant.mBLOCK_TYPE_WEB;
//            BlockWebApiOptionsV2 webApiOptionsV2 = new BlockWebApiOptionsV2();
//            webApiOptionsV2.mUniqueName = "NitWebBlockVo";
//            webApiOptionsV2.url = "https://www.jianshu.com/p/abf592a71d02";
//            webApiOptionsV2.mTitle = "简单";
//            commonApiData1.itemApiOptions = webApiOptionsV2;
//            blockTabApiOptions.mBlockApiDatas.add(commonApiData1);
        }

//
//        if (blockTabApiOptions.mBlockEntity.size() == 0) {
//            BlockTabEntity blockTabEntity = new BlockTabEntity();
//            blockTabEntity.mBlockType = Constant.mBLOCK_TYPE_LIST;
//            blockTabEntity.mReqParam.put("t", "dynamic");
//            blockTabEntity.mTitle = "动态";
//            blockTabEntity.mVmPath = "com.docker.cirlev2.vm.CircleDynamicListViewModel";
//            blockTabApiOptions.mBlockEntity.add(blockTabEntity);
//        }
//
//        for (int i = 0; i < blockTabApiOptions.mBlockEntity.size(); i++) {
//            blockTabApiOptions.mBlockEntity.get(i).mReqParam.put("uuid", CacheUtils.getUser().uuid);
//            blockTabApiOptions.mBlockEntity.get(i).mReqParam.put("memberid", CacheUtils.getUser().uid);
//        }
    }
}
