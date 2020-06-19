package com.docker.common.ui.base.block;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Pair;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.Observable;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.alibaba.android.arouter.launcher.ARouter;
import com.blankj.utilcode.util.GsonUtils;
import com.docker.common.R;
import com.docker.common.config.Constant;
import com.docker.common.databinding.CommonFragmentListV2Binding;
import com.docker.common.event.NitEventMessageManager;
import com.docker.common.model.card.BaseCardVo;
import com.docker.common.model.list.CommonListOptions;
import com.docker.common.ui.base.NitCommonFragment;
import com.docker.common.util.LayoutManager;
import com.docker.common.vm.base.NitCommonListVm;
import com.docker.core.utils.AppExecutors;
import com.docker.design.recycledrg.ItemTouchHelperAdapter;
import com.docker.design.recycledrg.ItemTouchHelperCallback;
import com.docker.design.refresh.SmartRefreshLayout;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import static com.docker.common.config.Constant.KEY_RVUI_GRID2;
import static com.docker.common.config.Constant.KEY_RVUI_GRID3;
import static com.docker.common.config.Constant.KEY_RVUI_GRID4;
import static com.docker.common.config.Constant.KEY_RVUI_GRID5;
import static com.docker.common.config.Constant.KEY_RVUI_HOR;
import static com.docker.common.config.Constant.KEY_RVUI_LINER;
import static com.docker.common.config.Constant.KEY_RVUI_STAGE;


public abstract class NitBaseBlockListFragment<VM extends NitCommonListVm> extends NitCommonFragment<VM, CommonFragmentListV2Binding> implements ItemTouchHelperAdapter {


    protected CommonListOptions commonListReq;
    private SmartRefreshLayout refreshLayout;

    private ItemTouchHelperCallback itemTouchHelperCallback;
    private ItemTouchHelper touchHelper;

    @Override
    protected int getLayoutId() {
        return R.layout.common_fragment_list_v2;
    }

    @Override
    protected void initView(View var1) {
//        mRvshareViewpoolvm = ViewModelProviders.of(null == getParentFragment() ? this : getParentFragment()).get(ShareViewPoolVm.class);
//        this.getLifecycle().addObserver(mRvshareViewpoolvm);
//        mBinding.get().recyclerView.setRecycledViewPool(mRvshareViewpoolvm.getRvSharePool());

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        try {
            commonListReq = (CommonListOptions) getArguments().getSerializable(Constant.CommonListParam);
        } catch (Exception e) {
        }

        if (commonListReq == null) {
            commonListReq = getArgument();
        }
        if (commonListReq != null) {
            ARouter.getInstance().inject(this);
            (mViewModel).initParam(commonListReq);
            mBinding.get().setViewmodel(mViewModel);
            initRvUi();
            initListener();
        }
    }

    public void initListener() {
        try {
            mViewModel.mServerLiveData.observe(this.getViewLifecycleOwner(), o -> {
            });
        } catch (Exception e) {

        }
        mViewModel.loadingOV.addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable sender, int propertyId) {
                if (refreshLayout != null) {
                    refreshLayout.finishRefresh();
                }
            }
        });
    }

    public CommonListOptions getArgument() {
        return null;
    }

    protected void initRvUi() {
        switch (commonListReq.RvUi) {
            case KEY_RVUI_LINER:
                (mBinding.get()).recyclerView.setLayoutManager(LayoutManager.linear().create((mBinding.get()).recyclerView));
                break;

            case KEY_RVUI_GRID2:
                (mBinding.get()).recyclerView.setLayoutManager(LayoutManager
                        .grid(2)
                        .create((mBinding.get()).recyclerView));
                break;
            case KEY_RVUI_HOR:
                (mBinding.get()).recyclerView.setLayoutManager(LayoutManager
                        .linear(LinearLayoutManager.HORIZONTAL, false)
                        .create((mBinding.get()).recyclerView));
                break;
            case KEY_RVUI_GRID5:
                (mBinding.get()).recyclerView.setLayoutManager(LayoutManager
                        .grid(5)
                        .create((mBinding.get()).recyclerView));
                break;
            case KEY_RVUI_GRID3:
                (mBinding.get()).recyclerView.setLayoutManager(LayoutManager
                        .grid(3)
                        .create((mBinding.get()).recyclerView));
                break;
            case KEY_RVUI_GRID4:
                (mBinding.get()).recyclerView.setLayoutManager(LayoutManager
                        .grid(4)
                        .create((mBinding.get()).recyclerView));
            case KEY_RVUI_STAGE:
                (mBinding.get()).recyclerView.setLayoutManager(LayoutManager
                        .staggeredGrid(2, LinearLayoutManager.VERTICAL)
                        .create((mBinding.get()).recyclerView));
                break;
        }

        if (commonListReq.RvDrg) {
            itemTouchHelperCallback = new ItemTouchHelperCallback(this);
            touchHelper = new ItemTouchHelper(itemTouchHelperCallback);
            itemTouchHelperCallback.setLongPressDragEnabled(true);
            touchHelper.attachToRecyclerView(mBinding.get().recyclerView);
        }
    }


    // 外部更改请求接口的参数
    // isall 是否全量更改
    public void UpdateReqParam(boolean isAll, Pair<String, String> pair) {
        mViewModel.loadingState = false;
        if (isAll) {
            (mViewModel).mCommonListReq.ReqParam.clear();
            if (!TextUtils.isEmpty(pair.first) && !TextUtils.isEmpty(pair.second)) {
                (mViewModel).mCommonListReq.ReqParam.put(pair.first, pair.second);
            }
        } else {
            if (!TextUtils.isEmpty(pair.first) && !TextUtils.isEmpty(pair.second)) {
                (mViewModel).mCommonListReq.ReqParam.put(pair.first, pair.second);
            }
            if (!TextUtils.isEmpty(pair.first) && TextUtils.isEmpty(pair.second)) {
                (mViewModel).mCommonListReq.ReqParam.remove(pair.first);
            }
        }
        (mViewModel).mPage = 1;
        (mViewModel).mItems.clear();
    }


    // 外部更改请求接口的参数
    // isall 是否全量更改
    public void UpdateReqParam(boolean isAll, ArrayList<Pair<String, String>> pairList) {
        (mViewModel).loadingState = false;
        if (isAll) {
            (mViewModel).mCommonListReq.ReqParam.clear();
            for (int i = 0; i < pairList.size(); i++) {
                if (!TextUtils.isEmpty(pairList.get(i).first) && !TextUtils.isEmpty(pairList.get(i).second)) {
                    (mViewModel).mCommonListReq.ReqParam.put(pairList.get(i).first, pairList.get(i).second);
                }
            }
        } else {
            if (pairList != null && pairList.size() > 0) {
                for (int i = 0; i < pairList.size(); i++) {
                    if (!TextUtils.isEmpty(pairList.get(i).first) && !TextUtils.isEmpty(pairList.get(i).second)) {
                        (mViewModel).mCommonListReq.ReqParam.put(pairList.get(i).first, pairList.get(i).second);
                    }
                }
            }
        }
        (mViewModel).mPage = 1;
        (mViewModel).mItems.clear();
    }


    /*
     * 新接口规范刷新
     *
     *
     *
     * */
    public void updateReqParamV2(boolean isAll, ArrayList<Pair<String, String>> pairArrayList) {
        (mViewModel).loadingState = false;
        if (isAll) {
            (mViewModel).mCommonListReq.ReqParam.clear();
            for (int i = 0; i < pairArrayList.size(); i++) {
                if (!TextUtils.isEmpty(pairArrayList.get(i).first) && !TextUtils.isEmpty(pairArrayList.get(i).second)) {
                    (mViewModel).mCommonListReq.ReqParam.put(pairArrayList.get(i).first, pairArrayList.get(i).second);
                }
            }
        } else {
            if (pairArrayList != null && pairArrayList.size() > 0) {
                for (int i = 0; i < pairArrayList.size(); i++)
                    if (!TextUtils.isEmpty(pairArrayList.get(i).first) && !TextUtils.isEmpty(pairArrayList.get(i).second)) {
                        if ((mViewModel).mCommonListReq.ReqParam.containsKey(pairArrayList.get(i).first)) {
                            Map<String, String> willupparam = GsonUtils.fromJson((mViewModel).mCommonListReq.ReqParam.get(pairArrayList.get(i).first), Map.class);
                            willupparam.put(pairArrayList.get(i).first, pairArrayList.get(i).second);
                        } else {
                            (mViewModel).mCommonListReq.ReqParam.put(pairArrayList.get(i).first, pairArrayList.get(i).second);
                        }
                    }
            }
        }
        (mViewModel).mPage = 1;
        (mViewModel).mItems.clear();
    }

    /*
     * 新接口规范刷新
     *
     * 只提供增量更新（追加更新）
     *
     * */
    public void updateReqParamV2(String onelevelKey, ArrayList<Pair<String, String>> pairArrayList) {
        (mViewModel).loadingState = false;
        if (TextUtils.isEmpty(onelevelKey)) {
            for (int i = 0; i < pairArrayList.size(); i++) {
                try {
                    Map<String, String> oldparam = GsonUtils.fromJson((mViewModel).mCommonListReq.ReqParam.get(pairArrayList.get(i).first), Map.class);
                    Map<String, String> newparam = GsonUtils.fromJson(pairArrayList.get(i).second, Map.class);
                    for (Map.Entry<String, String> entry : newparam.entrySet()) {
                        oldparam.put(entry.getKey(), entry.getValue());
                    }
                    mViewModel.mCommonListReq.ReqParam.put(pairArrayList.get(i).first, GsonUtils.toJson(oldparam));
                } catch (Exception e) {
                    if (!TextUtils.isEmpty(pairArrayList.get(i).first) && !TextUtils.isEmpty(pairArrayList.get(i).second)) {
                        (mViewModel).mCommonListReq.ReqParam.put(pairArrayList.get(i).first, pairArrayList.get(i).second);
                    }
                }
            }
        } else {
            Map<String, String> oldTotalparam = new HashMap<>();
            for (int i = 0; i < pairArrayList.size(); i++) {
                try {

                    oldTotalparam = GsonUtils.fromJson((mViewModel).mCommonListReq.ReqParam.get(onelevelKey), Map.class);
                    Map<String, String> oldparam = GsonUtils.fromJson(oldTotalparam.get(pairArrayList.get(i).first), Map.class);
                    Map<String, String> newparam = GsonUtils.fromJson(pairArrayList.get(i).second, Map.class);

                    for (Map.Entry<String, String> entry : newparam.entrySet()) {
                        oldparam.put(entry.getKey(), entry.getValue());
                    }
                    oldTotalparam.put(pairArrayList.get(i).first, GsonUtils.toJson(oldparam));

                } catch (Exception e) {
                    if (oldTotalparam == null) {
                        oldTotalparam = new HashMap<>();
                    }
                    oldTotalparam.put(pairArrayList.get(i).first, pairArrayList.get(i).second);
                }
            }
            mViewModel.mCommonListReq.ReqParam.put(onelevelKey, GsonUtils.toJson(oldTotalparam));
        }
        (mViewModel).mPage = 1;
        (mViewModel).mItems.clear();
    }


    @Override
    public void onVisible() {
        super.onVisible();
        if (mViewModel == null) {
            mViewModel = getViewModel();
        }
        if ((mViewModel).mItems.size() == 0 && (mViewModel).mPage == 1 && !(mViewModel).loadingState) {
            (mViewModel).loadData();
        }
    }

    @Override
    public void onReFresh(SmartRefreshLayout refreshLayout) {
        super.onReFresh(refreshLayout);
        this.refreshLayout = refreshLayout;
        (mViewModel).mPage = 1;
        mViewModel.loadData();
    }

    @Override
    public void onLoadMore(SmartRefreshLayout refreshLayout) {
        super.onLoadMore(refreshLayout);
        this.refreshLayout = refreshLayout;
        mViewModel.loadData();
    }

    @Override
    public boolean onItemMove(int fromPosition, int toPosition) {
        Collections.swap(mViewModel.mItems, fromPosition, toPosition);
        mBinding.get().recyclerView.getAdapter().notifyItemMoved(fromPosition, toPosition);
        mBinding.get().recyclerView.getAdapter().notifyItemRangeChanged(0, mViewModel.mItems.size());
        return true;
    }

    @Override
    public void onItemDismiss(int position) {
        mViewModel.mItems.remove(position);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }


    @Inject
    AppExecutors appExecutors;

    @Override
    public void processSysMessage(String key, Object value) {
        super.processSysMessage(key, value);
        appExecutors.diskIO().execute(() -> {
            for (int i = 0; i < mViewModel.mItems.size(); i++) {
                if (mViewModel.mItems.get(i) instanceof BaseCardVo) {
                    BaseCardVo baseCardVo = (BaseCardVo) mViewModel.mItems.get(i);
                    if (baseCardVo.mEventMap.containsKey(key)) {
                        appExecutors.mainThread().execute(() -> {
                            NitEventMessageManager.getInstance().ProcessMessage(baseCardVo,key,value,appExecutors);
//                            if (value instanceof EventParam) {
//
//                                EventParam eventParam = (EventParam) value;
//                                if (!TextUtils.isEmpty(eventParam.mPageID) && (eventParam.mPageID).equals(baseCardVo.mPageID)) {
//                                    Log.d("sss", "processSysMessage: =============page=============" + baseCardVo.mPageID + "=======" + eventParam.mPageID);
//                                    ReplyCommand command = (ReplyCommand) baseCardVo.mEventMap.get(key);
//                                    command.exectue();
//                                    baseCardVo.onEventTouchIng(key, value);
//                                }
//                            } else {
//                                ReplyCommand command = (ReplyCommand) baseCardVo.mEventMap.get(key);
//                                command.exectue();
//                                baseCardVo.onEventTouchIng(key, value);
//                            }
                        });
                    }
                }
            }
        });
    }
}
