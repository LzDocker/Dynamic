package com.docker.dynamic.ui.test;

import android.util.Log;
import android.view.View;

import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import com.alibaba.android.arouter.launcher.ARouter;
import com.blankj.utilcode.util.FragmentUtils;
import com.blankj.utilcode.util.GsonUtils;
import com.docker.common.command.NitDelegetCommand;
import com.docker.common.config.Constant;
import com.docker.common.model.apiconfig.BlockListApiOptionsV2;
import com.docker.common.model.apiconfig.BlockTabApiOptions;
import com.docker.common.model.apiconfig.BlockWebApiOptionsV2;
import com.docker.common.model.apiconfig.CardApiOptions;
import com.docker.common.model.apiconfig.CommonApiData;
import com.docker.common.model.card.BaseCardVo;
import com.docker.common.model.page.PageOptions;
import com.docker.common.ui.base.NitCommonFragment;
import com.docker.common.util.CacheUtils;
import com.docker.common.vm.EmptyViewModel;
import com.docker.common.vm.base.NitCommonListVm;
import com.docker.common.vm.base.NitCoutainerBaseFragmentV2;
import com.docker.dynamic.R;
import com.docker.dynamic.databinding.AppFragmentFrameBinding;

public class IndexBlockSampleFragmentV2 extends NitCommonFragment<EmptyViewModel, AppFragmentFrameBinding> {


    public static IndexBlockSampleFragmentV2 getInstance() {
        return new IndexBlockSampleFragmentV2();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.app_fragment_frame;
    }

    @Override
    protected EmptyViewModel getViewModel() {
        return new ViewModelProvider(this).get(EmptyViewModel.class);
    }

    @Override
    protected void initView(View var1) {
        ARouter.getInstance().inject(this);

        PageOptions pageOptions = new PageOptions();
        CommonApiData commonApiData = new CommonApiData();
        commonApiData.mType = Constant.mBLOCK_TYPE_CARD;
        CardApiOptions cardApiOptionsV2 = new CardApiOptions();
//        cardApiOptionsV2.isStick = true;
        cardApiOptionsV2.mUniqueName = "AppRecycleHorizontalCardVo2";
        commonApiData.itemApiOptions = cardApiOptionsV2;
        pageOptions.mItemListOptions.add(commonApiData);


        CommonApiData commonApiData1 = new CommonApiData();
        commonApiData1.mType = Constant.mBLOCK_TYPE_WEB;
        BlockWebApiOptionsV2 webApiOptionsV2 = new BlockWebApiOptionsV2();
        webApiOptionsV2.mUniqueName = "NitWebBlockVo";
        webApiOptionsV2.url = "https://www.baidu.com";
        commonApiData1.itemApiOptions = webApiOptionsV2;
        pageOptions.mItemListOptions.add(commonApiData1);


        CommonApiData commonApiData2 = new CommonApiData();
        commonApiData2.mType = Constant.mBLOCK_TYPE_LIST;
        BlockListApiOptionsV2 blockListApiOptionsV2 = new BlockListApiOptionsV2();
//        blockListApiOptionsV2.isMainBlock = true;
        blockListApiOptionsV2.mUniqueName = "NitDynamicListBlockVo";
        commonApiData2.itemApiOptions = blockListApiOptionsV2;
        pageOptions.mItemListOptions.add(commonApiData2);


        CommonApiData commonApiData3 = new CommonApiData();
        commonApiData3.mType = Constant.mBLOCK_TYPE_TAB;
        BlockTabApiOptions blockTabApiOptions = new BlockTabApiOptions();
        blockTabApiOptions.isMainBlock = true;
        blockTabApiOptions.mUniqueName = "NitTabBlockVo";
        commonApiData3.itemApiOptions = blockTabApiOptions;
        pageOptions.mItemListOptions.add(commonApiData3);

//        Log.d("TAG", "initView: =========pageoptions==========1=====" + GsonUtils.toJson(pageOptions));

//        Log.d("TAG", "initView: =========pageoptions==========1=====" + CacheUtils.GetTestPageConfig());


//        String str = GsonUtils.toJson(CacheUtils.GetTestPageConfig());
//
//        MultiTypeJsonParser<ItemApiOptions> multiTypeJsonParser = new MultiTypeJsonParser.Builder<ItemApiOptions>()
//                .registerTypeElementName("mType")
//                .registerTargetClass(ItemApiOptions.class)
//                .registerTargetUpperLevelClass(CommonApiData.class)
//                .registerTypeElementValueWithClassType("card", CardApiOptionsV2.class)
//                .registerTypeElementValueWithClassType("block_list", BlockListApiOptionsV2.class)
//                .registerTypeElementValueWithClassType("block_tab", BlockTabApiOptions.class)
//                .registerTypeElementValueWithClassType("block_web", BlockWebApiOptionsV2.class)
//                .build();
//
////
//        PageOptions pageOptions2 = multiTypeJsonParser.fromJson(str, PageOptions.class);

        fragment = (NitCoutainerBaseFragmentV2) ARouter.getInstance().build(Constant.mCOMMON_COUTAINER_PATH).withSerializable("PageOptions", pageOptions).navigation();
        FragmentUtils.add(getChildFragmentManager(), fragment, R.id.frame);

        Log.d("TAG", "initView: =========" + GsonUtils.toJson(pageOptions));


//        mBinding.get().tv.setOnClickListener(v -> {
//            String strs = GsonUtils.toJson(pageOptions);
//            Log.d("TAG", "initView: ====pageOptions====="+ GsonUtils.toJson(pageOptions));
//
////            String strs2 = GsonUtils.toJson(pageOptions2);
//
//
////            CacheUtils.SaveTestPageConfig(strs2);
//
//            mBinding.get().tv.setText(strs + "==================");
//
//            MultiTypeJsonParser<ItemApiOptions> multiTypeJsonParser2 = new MultiTypeJsonParser.Builder<ItemApiOptions>()
//                    .registerTypeElementName("mType")
//                    .registerTargetClass(ItemApiOptions.class)
//                    .registerTargetUpperLevelClass(CommonApiData.class)
//                    .registerTypeElementValueWithClassType("card", CardApiOptionsV2.class)
//                    .registerTypeElementValueWithClassType("block_list", BlockListApiOptionsV2.class)
//                    .registerTypeElementValueWithClassType("block_tab", BlockTabApiOptions.class)
//                    .registerTypeElementValueWithClassType("block_web", BlockWebApiOptionsV2.class)
//                    .build();
//
//            PageOptions pageOptions3 = multiTypeJsonParser2.fromJson(strs, PageOptions.class);
//            FragmentUtils.remove(fragment);
//            fragment = (NitCoutainerBaseFragmentV2) ARouter.getInstance().build(AppRouter.COMMON_BLOCK_COUTAINERV2).withSerializable("PageOptions", pageOptions3).navigation();
//            FragmentUtils.add(getChildFragmentManager(), fragment, R.id.frame);
//            String strs3 = GsonUtils.toJson(pageOptions3);
//            Log.d("sss", "initView: ====mo=" + strs3.equals(strs));
//            Log.d("sss", "initView: ====mo=" + strs3);
//            CacheUtils.SaveTestPageConfig(strs3);
//
//        });
    }


    NitCoutainerBaseFragmentV2 fragment;

    public NitCoutainerBaseFragmentV2 getCoutainerFragment() {
        return fragment;
    }

    @Override
    public NitDelegetCommand providerNitDelegetCommand(int flag) {
        return new NitDelegetCommand() {
            @Override
            public Class providerOuterVm() {
                return null;
            }

            @Override
            public void next(NitCommonListVm commonListVm, NitCommonFragment nitCommonFragment) {

//                if (CacheUtils.getUser() == null) {
//                    ARouter.getInstance().build(AppRouter.ACCOUNT_LOGIN).navigation();
//                    return;
//                }

                Log.d("TAG", "IndexBlockSampleFragmentV2: ======providerNitDelegetCommand========IndexBlockSampleFragmentV2============");

//
                // banner
//                BaseCardVo AppBannerCardVo = BlockManager.getInstance().findCardByName("AppBannerCardVo", null);
//                NitBaseProviderCard.providerCard(commonListVm, AppBannerCardVo, nitCommonFragment);
//                AppBannerCardVo.mPageID = fragment.mPageID;

//                // nav
//                BaseCardVo AppRecycleCard2Vo = BlockManager.getInstance().findCardByName("AppRecycleCard2Vo", null);
//                NitBaseProviderCard.providerCard(commonListVm, AppRecycleCard2Vo, nitCommonFragment);
//                AppRecycleCard2Vo.mPageID = fragment.mPageID;
//
//                // banner
//                BaseCardVo AppBannerCardVo2 = CardManager.getInstance().findCardByName("AppBannerCardVo", null);
//                NitBaseProviderCard.providerCard(commonListVm, AppBannerCardVo2, nitCommonFragment);
//
//                // 星球达人
//                BaseCardVo AppRecycleHorizontalCardVo2 = BlockManager.getInstance().findCardByName("AppRecycleHorizontalCardVo2", null);
////                AppRecycleHorizontalCardVo2.mPageID = getCoutainerFragment().mPageID;
//                AppRecycleHorizontalCardVo2.mPageID = fragment.mPageID;
//                NitBaseProviderCard.providerCard(commonListVm, AppRecycleHorizontalCardVo2, nitCommonFragment);

//
//                // section
//                BaseCardVo AppSectionRvCardVo = CardManager.getInstance().findCardByName("AppSectionRvCardVo", null);
//                NitBaseProviderCard.providerCard(commonListVm, AppSectionRvCardVo, nitCommonFragment);
//
//


//                CardProviderService cardProviderService = (CardProviderService) ARouter.getInstance().build(AppRouter.APP_CARD_IMPL).navigation();
//                UserInfoVo userInfoVo = CacheUtils.getUser();


                // banner
//                BaseCardVo AppBannerCardVo = cardProviderService.ProviderCard("AppBannerCardVo", null);
//                AppBannerCardVo.mRepParamMap.put("cid", "1");
//                AppBannerCardVo.mRepParamMap.put("companyid", "1001");
//                AppBannerCardVo.position = 0;
//                NitBaseProviderCard.providerCard(commonListVm, AppBannerCardVo, nitCommonFragment);
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

//                // 导航
//                BaseCardVo AppRecycleCard2Vo = cardProviderService.ProviderCard("AppRecycleCard2Vo", null);
//                AppRecycleCard2Vo.mRepParamMap.put("keyid", "3471");
//                AppRecycleCard2Vo.position = 0;
//                AppRecycleCard2Vo.isNoNetNeed = true;
//                NitBaseProviderCard.providerCard(commonListVm, AppRecycleCard2Vo, nitCommonFragment);
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


                // banner
//                BaseCardVo AppBannerCardVo2 = cardProviderService.ProviderCard("AppBannerCardVo", null);
//                AppBannerCardVo2.mRepParamMap.put("cid", "1");
//                AppBannerCardVo2.mRepParamMap.put("companyid", "1001");
//                AppBannerCardVo2.position = 3;
//                NitBaseProviderCard.providerCard(commonListVm, AppBannerCardVo2, nitCommonFragment);

                ////////////////////////////////////////////////////////////////////////


//                        AppSectionRvCardVo appSectionRvCardVo = new AppSectionRvCardVo(0, 3);
//                        appSectionRvCardVo.mRepParamMap.put("t", "dynamic");
//                        appSectionRvCardVo.mRepParamMap.put("memberid", CacheUtils.getUser().uid);
//                        NitBaseProviderCard.providerCard(commonListVm, appSectionRvCardVo, nitCommonFragment);


//                // 星球达人
//                BaseCardVo AppRecycleHorizontalCardVo2 = cardProviderService.ProviderCard("AppRecycleHorizontalCardVo2", null);
//                if (userInfoVo != null) {
//                    AppRecycleHorizontalCardVo2.mRepParamMap.put("memberid", userInfoVo.uid);
//                    AppRecycleHorizontalCardVo2.mRepParamMap.put("uuid", userInfoVo.uuid);
//                }
//                AppRecycleHorizontalCardVo2.mRepParamMap.put("isrecommend", "1");
//                AppRecycleHorizontalCardVo2.position = 3;
//                NitBaseProviderCard.providerCard(commonListVm, AppRecycleHorizontalCardVo2, nitCommonFragment);
            }
        };
    }

    @Override
    public void initImmersionBar() {

    }


}