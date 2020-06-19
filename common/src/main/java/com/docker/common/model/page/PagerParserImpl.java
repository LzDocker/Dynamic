//package com.docker.common.model.page;
//
//import android.content.Context;
//import android.util.Log;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.FrameLayout;
//import android.widget.LinearLayout;
//
//import androidx.databinding.DataBindingUtil;
//import androidx.databinding.ViewDataBinding;
//import androidx.fragment.app.Fragment;
//import androidx.viewpager.widget.ViewPager;
//
//import com.alibaba.android.arouter.launcher.ARouter;
//import com.blankj.utilcode.util.FragmentUtils;
//import com.blankj.utilcode.util.ToastUtils;
//import com.docker.common.BR;
//import com.docker.common.R;
//import com.docker.common.config.Constant;
//import com.docker.common.databinding.CommonBlockTabV2Binding;
//import com.docker.common.databinding.CommonCoutainerConsecutiveFragmentV2Binding;
//import com.docker.common.event.NitEventMessageManager;
//import com.docker.common.model.apiconfig.BlockListApiOptionsV2;
//import com.docker.common.model.apiconfig.BlockTabApiOptions;
//import com.docker.common.model.apiconfig.BlockWebApiOptionsV2;
//import com.docker.common.model.apiconfig.CardApiOptions;
//import com.docker.common.model.apiconfig.CommonApiData;
//import com.docker.common.model.block.NitBaseBlockVo;
//import com.docker.common.model.card.BaseCardVo;
//import com.docker.common.model.list.CommonListOptions;
//import com.docker.common.ui.base.NitCommonFragment;
//import com.docker.common.ui.base.block.NitBaseBlockListFragment;
//import com.docker.common.ui.base.block.NitSampleBlockListFragment;
//import com.docker.common.vm.base.NitCommonContainerViewModel;
//import com.docker.common.vm.base.NitCommonListVm;
//import com.docker.core.utils.AppExecutors;
//import com.docker.design.viewpager.NitViewPager;
//import com.donkingliang.consecutivescroller.ConsecutiveScrollerLayout;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.Map;
//
//import javax.inject.Inject;
//
//
//public class PagerParserImpl implements PageParser {
//
//    public PagerParserImpl() {
//        mPageID = "mPage_" + BlockManager.getInstance().getUniqueID(this);
//    }
//
//    /*
//     * 只存储平级card 用于响应刷新事件
//     * */
//    public HashMap<Integer, BaseCardVo> mCardListMap = new HashMap<>();
//
//    private HashMap<String, NitCommonFragment> mFrameLists = new HashMap<>();
//
//    public String mPageID;
//
//    private CommonCoutainerConsecutiveFragmentV2Binding mBinding;
//
//    private PageOptions pageOptions;
//
//    @Override
//    public String getPageID() {
//        return mPageID;
//    }
//
//
//    @Override
//    public void processPager(PageOptions pageOptions, NitCommonFragment nitCommonFragment) {
//        this.pageOptions = pageOptions;
//        if (pageOptions == null || pageOptions.mItemListOptions == null || pageOptions.mItemListOptions.size() == 0) {
//            return;
//        }
//        mBinding = (CommonCoutainerConsecutiveFragmentV2Binding) nitCommonFragment.mBinding.get();
//
//        for (int i = 0; i < pageOptions.mItemListOptions.size(); i++) {
//            CommonApiData commonApiData = pageOptions.mItemListOptions.get(i);
//            if (commonApiData.itemApiOptions == null) {
//                return;
//            }
//            switch (commonApiData.mType) {
//                case "card":
//                    BaseCardVo baseCardVo = BlockManager.getInstance().findApiCardByName(((CardApiOptions) commonApiData.itemApiOptions).mUniqueName, null, null);
//                    processCard(baseCardVo, (CardApiOptions) commonApiData.itemApiOptions, nitCommonFragment, i);
//                    break;
//                case "block_web":
//                    processWeb((BlockWebApiOptionsV2) commonApiData.itemApiOptions, nitCommonFragment, i, false);
//                    break;
//                case "block_list":
//                    processList((BlockListApiOptionsV2) commonApiData.itemApiOptions, nitCommonFragment, i, false);
//                    break;
//                case "block_tab":
//                    processTab((BlockTabApiOptions) commonApiData.itemApiOptions, nitCommonFragment, i);
//                    break;
//            }
//        }
//    }
//
//    @Override
//    public void processTestPager(String name, NitCommonFragment nitCommonFragment) {
//        mBinding = (CommonCoutainerConsecutiveFragmentV2Binding) nitCommonFragment.mBinding.get();
//        pageOptions = new PageOptions();
//
//        NitBaseBlockVo baseBlockVo = BlockManager.getInstance().finBlockByName(name, null);
//        if (baseBlockVo != null) {
//            baseBlockVo.initParam(null);
//            baseBlockVo.mDefBlockOptions.mUniqueName = name;
//            CommonApiData commonApiData = new CommonApiData();
//            if (baseBlockVo.mDefBlockOptions instanceof BlockWebApiOptionsV2) {
//                processWeb((BlockWebApiOptionsV2) baseBlockVo.mDefBlockOptions, nitCommonFragment, 0, false);
//                commonApiData.mType = Constant.mBLOCK_TYPE_WEB;
//
//            }
//
//            if (baseBlockVo.mDefBlockOptions instanceof BlockListApiOptionsV2) {
//                processList((BlockListApiOptionsV2) baseBlockVo.mDefBlockOptions, nitCommonFragment, 0, false);
//                commonApiData.mType = Constant.mBLOCK_TYPE_LIST;
//            }
//
//            if (baseBlockVo.mDefBlockOptions instanceof BlockTabApiOptions) {
//                processTab((BlockTabApiOptions) baseBlockVo.mDefBlockOptions, nitCommonFragment, 0);
//                commonApiData.mType = Constant.mBLOCK_TYPE_TAB;
//            }
//
//            commonApiData.itemApiOptions = baseBlockVo.mDefBlockOptions;
//            pageOptions.mItemListOptions.add(commonApiData);
//
//        } else {
//            BaseCardVo baseCardVo = BlockManager.getInstance().findApiCardByName(name, null, null);
//            Log.d("TAG", "processTestPager: ======" + name);
//            if (baseCardVo == null) {
//                ToastUtils.showShort(name + "-- 未提供，请检查");
//                Log.d("TAG", "processTestPager: ====找不到==" + name);
//                return;
//            }
//            CardApiOptions cardApiOptionsV2 = new CardApiOptions();
//            processCard(baseCardVo, cardApiOptionsV2, nitCommonFragment, 0);
//        }
//
//    }
//
//
//    private void processCard(BaseCardVo baseCardVo, CardApiOptions cardApiOptionsV2, NitCommonFragment nitCommonFragment, int position) {
//        if (baseCardVo == null) {
//            Log.e("card", "processCard: =====exception=====" + cardApiOptionsV2.mUniqueName);
//            return;
//        }
//        baseCardVo.mPageID = mPageID;
//        NitBaseProviderCard.providerCard(null, baseCardVo, nitCommonFragment);
//        mCardListMap.put(position, baseCardVo);
//        ViewDataBinding cardbind = DataBindingUtil.inflate(nitCommonFragment.getLayoutInflater(), baseCardVo.getItemLayout(), null, false);
//        cardbind.setVariable(BR.item, baseCardVo);
//        cardbind.executePendingBindings();
//        mBinding.fullscroll.addView(cardbind.getRoot(), position, geneWrapLinerLayoutparams(cardApiOptionsV2.isStick));
//        baseCardVo.mCardVoLiveData.observe(nitCommonFragment, o -> {
//            if (o != null) {
//                mBinding.fullscroll.getChildAt(position).setVisibility(View.VISIBLE);
//            } else {
//                if (baseCardVo.isNoNetNeed) {
//                    mBinding.fullscroll.getChildAt(position).setVisibility(View.GONE);
//                }
//            }
//        });
//    }
//
//    private NitCommonFragment processWeb(BlockWebApiOptionsV2 blockWebApiOptionsV2, NitCommonFragment nitCommonFragment, int position, boolean isneedReturn) {
//
//        ConsecutiveScrollerLayout layout = null;
//        if (!isneedReturn) {
//            layout = geneLayoutParam(nitCommonFragment.getActivity());
//            mBinding.fullscroll.addView(layout, position, genematchLayoutparams());
//        }
//        NitBaseBlockVo nitBaseBlockVo = BlockManager.getInstance().finBlockByName(blockWebApiOptionsV2.mUniqueName, blockWebApiOptionsV2);
//        nitBaseBlockVo.mPageID = mPageID;
//
//        //AppRouter.CIRCLE_WEB_ITEM_VIEW
//        String completeUrl = null;
//        StringBuilder stringBuilder = new StringBuilder(blockWebApiOptionsV2.url + "?");
//        for (String param : blockWebApiOptionsV2.mBlockReqParam.keySet()) {
//            stringBuilder.append(param).append("=").append(blockWebApiOptionsV2.mBlockReqParam.get(param)).append("&");
//        }
//        if (stringBuilder.length() > 1) {
//            completeUrl = stringBuilder.substring(0, stringBuilder.length() - 1);
//        }
//        NitCommonFragment nitwebFragment = (NitCommonFragment) ARouter
//                .getInstance()
//                .build(AppRouter.CIRCLE_WEB_ITEM_VIEW)
//                .withString("weburl", completeUrl)
//                .navigation();
//        blockWebApiOptionsV2.mFragment = nitwebFragment;
//        if (!isneedReturn) {
//            FragmentUtils.add(nitCommonFragment.getChildFragmentManager(), nitwebFragment, layout.getId());
//        }
//        Log.d("TAG", "processWeb: ================" + completeUrl);
//        return nitwebFragment;
//    }
//
//    private NitCommonFragment processList(BlockListApiOptionsV2 blockListApiOptionsV2, NitCommonFragment nitCommonFragment, int position, boolean isneedReturn) {
//        ConsecutiveScrollerLayout layout = null;
//        if (!isneedReturn) {
//            layout = geneLayoutParam(nitCommonFragment.getActivity());
//            mBinding.fullscroll.addView(layout, position, geneCommonLayoutparams());
//            if (blockListApiOptionsV2.isMainBlock) {
//                mBinding.refresh.setmFooterTranslationViewId(layout.getId());
//            }
//        }
//        NitBaseBlockVo nitBaseBlockVo = BlockManager.getInstance().finBlockByName(blockListApiOptionsV2.mUniqueName, blockListApiOptionsV2);
//        nitBaseBlockVo.mPageID = mPageID;
//        CommonListOptions commonListOptions = new CommonListOptions();
//        if (blockListApiOptionsV2.isMainBlock) {
//            commonListOptions.refreshState = Constant.KEY_REFRESH_OWNER;
//        }
//        commonListOptions.mBlockVm = nitBaseBlockVo.mVmClazz;
//        commonListOptions.mBlockVmPath = nitBaseBlockVo.mVmPath;
//        commonListOptions.isActParent = false;
//        commonListOptions.RvUi = blockListApiOptionsV2.RvUi;
//        commonListOptions.ReqParam = blockListApiOptionsV2.mBlockReqParam;
//        blockListApiOptionsV2.blockFrameId = commonListOptions.frameItemblockId;
//        if (isneedReturn) {
//            blockListApiOptionsV2.blockFlag = position;
//            commonListOptions.falg = blockListApiOptionsV2.blockFlag;
//        }
//        NitCommonFragment nitFragment = (NitCommonFragment) ARouter
//                .getInstance()
//                .build(AppRouter.COMMON_SAMPLE_BLOCK_ITEM_LIST)
//                .withSerializable(Constant.CommonListParam, commonListOptions)
//                .navigation();
//        blockListApiOptionsV2.mFragment = nitFragment;
//        mFrameLists.put(blockListApiOptionsV2.mUniqueName, nitFragment);
//        if (!isneedReturn) {
//            FragmentUtils.add(nitCommonFragment.getChildFragmentManager(), nitFragment, layout.getId());
//        }
//        return nitFragment;
//    }
//
//    private void processTab(BlockTabApiOptions blockTabApiOptions, NitCommonFragment nitCommonFragment, int position) {
//
//
//        //common_block_tab_v2.xml
//        CommonBlockTabV2Binding blockbind = DataBindingUtil.inflate(nitCommonFragment.getLayoutInflater(), R.layout.common_block_tab_v2, null, false);
//        mBinding.fullscroll.addView(blockbind.getRoot(), position, genematchLayoutparams());
//        if (blockTabApiOptions.isMainBlock) {
//            mBinding.refresh.setmFooterTranslationViewId(blockbind.viewpager.getId());
//        }
//
//        NitBaseBlockVo nitBaseBlockVo = BlockManager.getInstance().finBlockByName(blockTabApiOptions.mUniqueName, blockTabApiOptions);
//        nitBaseBlockVo.mPageID = mPageID;
//        ArrayList<Fragment> fragments = new ArrayList<>();
//        String[] mTitles = new String[blockTabApiOptions.mBlockApiDatas.size()];
//        for (int i = 0; i < blockTabApiOptions.mBlockApiDatas.size(); i++) {
//            switch (blockTabApiOptions.mBlockApiDatas.get(i).mType) {
//                case Constant.mBLOCK_TYPE_LIST:
//                    ((BlockListApiOptionsV2) blockTabApiOptions.mBlockApiDatas.get(i).itemApiOptions).isMainBlock = blockTabApiOptions.isMainBlock;
//                    mTitles[i] = ((BlockListApiOptionsV2) blockTabApiOptions.mBlockApiDatas.get(i).itemApiOptions).mTitle;
//                    fragments.add(processList((BlockListApiOptionsV2) blockTabApiOptions.mBlockApiDatas.get(i).itemApiOptions, nitCommonFragment, i, true));
//                    break;
//                case Constant.mBLOCK_TYPE_WEB:
//                    mTitles[i] = ((BlockWebApiOptionsV2) blockTabApiOptions.mBlockApiDatas.get(i).itemApiOptions).mTitle;
//                    fragments.add(processWeb((BlockWebApiOptionsV2) blockTabApiOptions.mBlockApiDatas.get(i).itemApiOptions, nitCommonFragment, i, true));
//                    break;
//            }
//        }
//
//        /*for (int i = 0; i < blockTabApiOptions.mBlockEntity.size(); i++) {
//            blockTabApiOptions.mBlockEntity.get(i).mPageID = mPageID;
//            switch (blockTabApiOptions.mBlockEntity.get(i).mBlockType) {
//                case "block_web":
//
//
//                    String completeUrl = null;
//                    StringBuilder stringBuilder = new StringBuilder(blockTabApiOptions.mBlockEntity.get(i).url + "?");
//                    for (String param : blockTabApiOptions.mBlockEntity.get(i).mReqParam.keySet()) {
//                        stringBuilder.append(param).append("=").append(blockTabApiOptions.mBlockEntity.get(i).mReqParam.get(param)).append("&");
//                    }
//                    if (stringBuilder.length() > 1) {
//                        completeUrl = stringBuilder.substring(0, stringBuilder.length() - 1);
//                    }
//                    NitCommonFragment nitwebFragment = (NitCommonFragment) ARouter
//                            .getInstance()
//                            .build(AppRouter.CIRCLE_WEB_ITEM_VIEW)
//                            .withString("url", completeUrl)
//                            .navigation();
//                    fragments.add(nitwebFragment);
//
//                    break;
//                case "block_list":
//
//                    CommonListOptions commonListOptions = new CommonListOptions();
//                    blockTabApiOptions.mBlockEntity.get(i).blockFlag = commonListOptions.falg = i;
//                    blockTabApiOptions.mBlockEntity.get(i).blockFrameId = commonListOptions.frameItemblockId;
//                    commonListOptions.mBlockVmPath = blockTabApiOptions.mBlockEntity.get(i).mVmPath;
//                    commonListOptions.isActParent = false;
//                    commonListOptions.RvUi = blockTabApiOptions.mBlockEntity.get(i).RvUi;
//                    commonListOptions.ReqParam = blockTabApiOptions.mBlockEntity.get(i).mReqParam;
//                    if (blockTabApiOptions.isMainBlock) {
//                        commonListOptions.refreshState = Constant.KEY_REFRESH_OWNER;
//                    }
//                    NitCommonFragment nitFragment = (NitCommonFragment) ARouter
//                            .getInstance()
//                            .build(AppRouter.COMMON_SAMPLE_BLOCK_ITEM_LIST)
//                            .withSerializable(Constant.CommonListParam, commonListOptions)
//                            .navigation();
//                    fragments.add(nitCommonFragment);
//
//                    break;
//            }
//            mTitles[i] = blockTabApiOptions.mBlockEntity.get(i).mTitle;
//        }*/
//
//        blockTabApiOptions.mFragments = fragments;
//        blockbind.viewpager.setAdapter(new DynamicFragmentAdapter(nitCommonFragment.getChildFragmentManager(), fragments));
//        CommonIndector commonIndector = new CommonIndector();
//        commonIndector.initMagicIndicatorScroll(mTitles, blockbind.viewpager, blockbind.magic, nitCommonFragment.getActivity());
////        blockbind.magic.post(() -> blockbind.viewpager.setAdjustHeight(blockbind.magic.getHeight()));
//        blockbind.viewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
//            @Override
//            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//
//            }
//
//            @Override
//            public void onPageSelected(int position) {
//                if (blockTabApiOptions.isMainBlock) {
//                    NitBaseBlockListFragment nitAbsFragmentItemList = (NitBaseBlockListFragment) blockTabApiOptions.mFragments.get(position);
//                    setViewmModel((NitCommonListVm) nitAbsFragmentItemList.mViewModel, nitAbsFragmentItemList);
//                    mBinding.refresh.finishLoadMore();
//                    mBinding.refresh.finishRefresh();
//                }
//            }
//
//            @Override
//            public void onPageScrollStateChanged(int state) {
//
//            }
//        });
//    }
//
//    @Inject
//    AppExecutors appExecutors;
//
//    @Override
//    public void processSysMessage(String key, Object value) {
//        if (mCardListMap.size() > 0) {
//            for (Map.Entry<Integer, BaseCardVo> entry : mCardListMap.entrySet()) {
//                if (entry.getValue().mEventMap.containsKey(key)) { // 注册的有事件，则去响应该事件
//                    NitEventMessageManager.getInstance().ProcessMessage(entry.getValue(), key, value, appExecutors);
////                    if (value instanceof EventParam) {
////                        EventParam eventParam = (EventParam) value;
////                        if (!TextUtils.isEmpty(eventParam.mPageID) && (eventParam.mPageID).equals(entry.getValue().mPageID)) {
////                            ReplyCommand command = (ReplyCommand) entry.getValue().mEventMap.get(key);
////                            command.exectue();
////                            entry.getValue().onEventTouchIng(key, value);
////                        }
////                    } else {
////                        ReplyCommand command = (ReplyCommand) entry.getValue().mEventMap.get(key);
////                        command.exectue();
////                        entry.getValue().onEventTouchIng(key, value);
////                    }
//                }
//            }
//        }
//    }
//
//    @Override
//    public void onBlockVmCreated(NitCommonContainerViewModel nitCommonContainerViewModel, String frameitemblockid, NitSampleBlockListFragment nitSampleBlockList, int flag) {
//        for (int i = 0; i < pageOptions.mItemListOptions.size(); i++) {
//            CommonApiData commonApiData = pageOptions.mItemListOptions.get(i);
//            switch (commonApiData.mType) {
//                case Constant.mBLOCK_TYPE_LIST:
//                    BlockListApiOptionsV2 blockListApiOptionsV2 = (BlockListApiOptionsV2) pageOptions.mItemListOptions.get(i).itemApiOptions;
//                    if (frameitemblockid.equals(blockListApiOptionsV2.blockFrameId)) {
//                        if (blockListApiOptionsV2.isMainBlock) {
//                            setViewmModel(nitCommonContainerViewModel, nitSampleBlockList);
//                        } else {
//                            processBlockRefresh(nitCommonContainerViewModel, i, nitSampleBlockList);
//                        }
//                        if (blockListApiOptionsV2.mBlockCardOpList.size() != 0) {
//                            processBlockInnerCards(nitCommonContainerViewModel, blockListApiOptionsV2.mBlockCardOpList, nitSampleBlockList);
//                        }
//                    }
//                    break;
//                case "block_tab":
//                    BlockTabApiOptions blockTabApiOptions = (BlockTabApiOptions) pageOptions.mItemListOptions.get(i).itemApiOptions;
//                    switch (blockTabApiOptions.mBlockApiDatas.get(0).mType) {
//                        case Constant.mBLOCK_TYPE_LIST:
//                            if (frameitemblockid.equals(((BlockListApiOptionsV2) blockTabApiOptions.mBlockApiDatas.get(0).itemApiOptions).blockFrameId)) {
//                                if (blockTabApiOptions.isMainBlock) {
//                                    if (flag == 0) {
//                                        setViewmModel(nitCommonContainerViewModel, nitSampleBlockList);
//                                    }
//                                } else {
//                                    processBlockRefresh(nitCommonContainerViewModel, i, nitSampleBlockList);
//                                }
//
//                                for (int j = 0; j < blockTabApiOptions.mBlockApiDatas.size(); j++) {
//                                    if (blockTabApiOptions.mBlockApiDatas.get(j).itemApiOptions instanceof BlockListApiOptionsV2
//                                            && flag == ((BlockListApiOptionsV2) blockTabApiOptions.mBlockApiDatas.get(j).itemApiOptions).blockFlag
//                                            && ((BlockListApiOptionsV2) blockTabApiOptions.mBlockApiDatas.get(j).itemApiOptions).mBlockCardOpList != null) {
//                                        processBlockInnerCards(nitCommonContainerViewModel, ((BlockListApiOptionsV2) blockTabApiOptions.mBlockApiDatas.get(j).itemApiOptions).mBlockCardOpList, nitSampleBlockList);
//                                    }
//                                }
//                            }
//                            break;
//                    }
//                    break;
//            }
//        }
//
//    }
//
//    /*
//     * 处理block 内包含的card
//     * */
//    private void processBlockInnerCards(NitCommonContainerViewModel nitCommonContainerViewModel, ArrayList<CardApiOptions> cardApiOptions, NitCommonFragment nitCommonFragment) {
//        if (cardApiOptions == null || cardApiOptions.size() == 0) {
//            return;
//        }
//        for (int i = 0; i < cardApiOptions.size(); i++) {
//            // todo cardApiOptions.get(i)
//            BaseCardVo baseCardVo = BlockManager.getInstance().findApiCardByName(cardApiOptions.get(i).mUniqueName, null, null);
//            baseCardVo.mPageID = mPageID;
//            NitBaseProviderCard.providerCard(nitCommonContainerViewModel, baseCardVo, nitCommonFragment);
//        }
//
//
//    }
//
//
//    @Override
//    public void addOptions(CommonApiData commonApiData) {
//
//    }
//
//    @Override
//    public void delOptions(CommonApiData commonApiData) {
//
//    }
//
//    @Override
//    public void updateOptions(CommonApiData commonApiData) {
//
//    }
//
//    @Override
//    public void recycle() {
//        if (pageOptions != null) {
//            pageOptions.mItemListOptions.clear();
//            pageOptions.mItemListOptions = null;
//            pageOptions = null;
//            System.gc();
//        }
//    }
//
//
//    private void setViewmModel(NitCommonListVm nitCommonListVm, NitCommonFragment nitblockFragment) {
//        mBinding.setViewmodel(nitCommonListVm);
//        nitCommonListVm.initCommand();
//        nitCommonListVm.setNotifyRefreshingCommand(() -> {
//            for (int i = 0; i < pageOptions.mItemListOptions.size(); i++) {
//                CommonApiData commonApiData = pageOptions.mItemListOptions.get(i);
//                if (commonApiData.itemApiOptions.isMainBlock) {
//                    return;
//                }
//                switch (commonApiData.mType) {
//                    case "block_list":
//                        if (commonApiData.itemApiOptions instanceof BlockListApiOptionsV2
//                                && ((BlockListApiOptionsV2) commonApiData.itemApiOptions).mFragment != null) {
//                            ((BlockListApiOptionsV2) commonApiData.itemApiOptions).mFragment.onReFresh(mBinding.refresh);
//                        }
//                        break;
//                    case "block_tab":
//                        ViewGroup viewGroup = (ViewGroup) mBinding.fullscroll.getChildAt(i);
//                        NitViewPager nitViewPager = viewGroup.findViewById(R.id.viewpager);
//                        if (nitViewPager != null && pageOptions.mItemListOptions.get(i).itemApiOptions instanceof BlockTabApiOptions) {
//                            ((NitCommonFragment) (((BlockTabApiOptions) pageOptions.mItemListOptions.get(i).itemApiOptions).mFragments.get(nitViewPager.getCurrentItem()))).onReFresh(mBinding.refresh);
//                        }
//                        break;
//                    case "block_web":
//                        if (commonApiData.itemApiOptions instanceof BlockWebApiOptionsV2
//                                && ((BlockWebApiOptionsV2) commonApiData.itemApiOptions).mFragment != null) {
//                            ((BlockWebApiOptionsV2) commonApiData.itemApiOptions).mFragment.onReFresh(mBinding.refresh);
//                        }
//                        break;
//                    case "card":
//                        if (mCardListMap.containsKey(i)) {
//                            mCardListMap.get(i).mNitcommonCardViewModel.loadCardData(mCardListMap.get(i));
//                        }
//                        break;
//                }
//            }
//        });
//    }
//
//    private void processBlockRefresh(NitCommonContainerViewModel nitCommonContainerViewModel, int i, NitCommonFragment nitSampleBlockList) {
//        // 非主block在请求数据出错的时候隐藏
//        nitCommonContainerViewModel.mServerLiveData.removeObservers(nitSampleBlockList);
//        nitCommonContainerViewModel.mServerLiveData.observe(nitSampleBlockList, o -> {
//            if (nitCommonContainerViewModel.mItems.size() == 0) {
//                mBinding.fullscroll.getChildAt(i).setVisibility(View.GONE);
//            } else {
//                mBinding.fullscroll.getChildAt(i).setVisibility(View.VISIBLE);
//            }
//        });
//    }
//
//    protected ConsecutiveScrollerLayout geneLayoutParam(Context context) {
////        NitFrameLayout frame = new NitFrameLayout(context);
//        ConsecutiveScrollerLayout frame = new ConsecutiveScrollerLayout(context);
//        int id = View.generateViewId();
//        frame.setId(id);
//        return frame;
//    }
//
//    private ConsecutiveScrollerLayout.LayoutParams geneWrapLinerLayoutparams(boolean isStick) {
//        LinearLayout.LayoutParams linerParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//        ConsecutiveScrollerLayout.LayoutParams lineParamsParams = new ConsecutiveScrollerLayout.LayoutParams(linerParams);
//        if (isStick) {
//            lineParamsParams.isSticky = true;
//        }
//        return lineParamsParams;
//    }
//
//    private ConsecutiveScrollerLayout.LayoutParams geneCommonLayoutparams() {
//        ViewGroup.LayoutParams stickParams = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//        ConsecutiveScrollerLayout.LayoutParams stickParamsParams = new ConsecutiveScrollerLayout.LayoutParams(stickParams);
//        return stickParamsParams;
//    }
//
//    private ConsecutiveScrollerLayout.LayoutParams genematchLayoutparams() {
//        ViewGroup.LayoutParams stickParams = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
//        ConsecutiveScrollerLayout.LayoutParams stickParamsParams = new ConsecutiveScrollerLayout.LayoutParams(stickParams);
//        return stickParamsParams;
//    }
//
//    private ConsecutiveScrollerLayout.LayoutParams geneWrapStickLayoutparams() {
//        FrameLayout.LayoutParams stickParams = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//        ConsecutiveScrollerLayout.LayoutParams stickParamsParams = new ConsecutiveScrollerLayout.LayoutParams(stickParams);
//        stickParamsParams.isSticky = true;
//        return stickParamsParams;
//    }
//
//}
