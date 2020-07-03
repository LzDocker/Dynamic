package com.docker.common.vm.base;

import android.text.TextUtils;
import android.view.View;

import androidx.databinding.ObservableArrayList;
import androidx.databinding.ObservableBoolean;
import androidx.databinding.ObservableList;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;

import com.blankj.utilcode.util.CollectionUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.docker.common.BR;
import com.docker.common.R;
import com.docker.common.command.ReponseCommand;
import com.docker.common.config.Constant;
import com.docker.common.model.BaseItemModel;
import com.docker.common.model.BaseSampleItem;
import com.docker.common.model.card.BaseCardVo;
import com.docker.common.model.card.TypeMakerInterface;
import com.docker.common.model.list.CommonListOptions;
import com.docker.core.command.ReplyCommand;
import com.docker.core.di.module.net.repository.CommonRepository;
import com.docker.core.di.module.net.repository.NitBoundCallback;
import com.docker.core.di.module.net.repository.NitNetBoundObserver;
import com.docker.core.di.module.net.repository.Resource;
import com.docker.core.di.module.net.response.ApiResponse;
import com.docker.core.di.module.net.response.BaseResponse;
import com.docker.design.stateview.EmptyStatus;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import me.tatarka.bindingcollectionadapter2.ItemBinding;
import me.tatarka.bindingcollectionadapter2.OnItemBind;
import retrofit2.Call;

public abstract class NitCommonListVm<T> extends NitCommonVm {

    public int mPage = 1;
    public int mPageSize = 20;

    public boolean isTest = false;

    public NitCommonListVm(CommonRepository commonRepository) {
        super(commonRepository);
    }

    public void setTest(boolean test) {
        isTest = test;
        ObservableList<BaseItemModel> mItemscopy = new ObservableArrayList<>();
        mItemscopy.addAll(mItems);
        mItems.clear();
        mItems.addAll(mItemscopy);
    }

    public ArrayList<BaseItemModel> mProvideredCardVos = new ArrayList<>();
    /*
     * vm 数据源
     * */
    public final MediatorLiveData<Object> mServerLiveData = new MediatorLiveData<Object>();

    public CommonListOptions mCommonListReq;
    public ObservableBoolean bdenable = new ObservableBoolean(false);
    public ObservableBoolean bdenablemore = new ObservableBoolean(false);
    public ObservableBoolean bdenablenodata = new ObservableBoolean(false);
    public ObservableBoolean bdenablerefresh = new ObservableBoolean(true);
    public ObservableBoolean loadingOV = new ObservableBoolean(false);
    public LiveData<ApiResponse<BaseResponse<T>>> servicefun = null;

    public void initParam(CommonListOptions commonListReq) {
        this.mCommonListReq = commonListReq;
    }

    @Override
    public void initCommand() {
        mCommand.OnRefresh(() -> {
            mPage = 1;
            loadData();
            refresh();
        });
        mCommand.OnLoadMore(() -> {
            loadData();
        });
        mCommand.OnRetryLoad(() -> {
            mEmptycommand.set(EmptyStatus.BdLoading);
            loadData();
        });
    }

    // 列表数据源
    public ObservableList<BaseItemModel> mItems = new ObservableArrayList<>();


    // 多类型条目适配
    public OnItemBind<BaseItemModel> mutipartItemsBinding = (itemBinding, position, item) -> {
        if (item instanceof BaseSampleItem) {
            ((BaseSampleItem) item).index = position;
        }
        if (isTest) {
            itemBinding.set(BR.item, R.layout.common_block_eg_item);
        } else {
            itemBinding.set(BR.item, item.getItemLayout());
        }

    };

    // itembinding
    public ItemBinding<BaseItemModel> itemBinding = ItemBinding.of(mutipartItemsBinding).bindExtra(BR.viewmodel, this);

    public void setLoadControl(boolean enable) {
        switch (mCommonListReq.refreshState) {
            case Constant.KEY_REFRESH_OWNER:
                bdenable.set(enable);
                bdenablemore.set(enable);
                bdenable.notifyChange();
                bdenablemore.notifyChange();
                break;
            case Constant.KEY_REFRESH_ONLY_LOADMORE:
                bdenablemore.set(enable);
                bdenablemore.notifyChange();
                bdenablerefresh.set(false);
                bdenablerefresh.notifyChange();
                break;
            case Constant.KEY_REFRESH_NOUSE:
                bdenablemore.set(false);
                bdenablemore.notifyChange();
                bdenablerefresh.set(false);
                bdenablerefresh.notifyChange();
                break;
        }
    }

    public void loadData() {
        mCommonListReq.ReqParam.put("page", String.valueOf(mPage));
        servicefun = getServicefun(mCommonListReq.ApiUrl, mCommonListReq.ReqParam);
        if (servicefun == null) {
            LogUtils.e("必须提供请求server");
            return;
        }
        mServerLiveData.addSource(
                commonRepository.noneChache(
                        servicefun), new NitNetBoundObserver<>(new NitBoundCallback<T>() {
                    @Override
                    public void onLoading(Call call) {
                        super.onLoading(call);
                    }

                    @Override
                    public void onLoading() {
                        super.onLoading();
                        loadingState = true;
                        loadingOV.set(true);
                        loadingOV.notifyChange();
                        if (mPage == 1) {
                            if (mIsfirstLoad) {
                                mEmptycommand.set(EmptyStatus.BdLoading);
                                setLoadControl(false);
                            } else {
                                mEmptycommand.set(EmptyStatus.BdHiden);
                                setLoadControl(true);
                            }
                        }
                    }

                    @Override
                    public void onComplete(Resource<T> resource) {
                        super.onComplete(resource);
                        loadingOV.set(false);
                        loadingOV.notifyChange();
                        mCompleteCommand.set(true);
                        mCompleteCommand.notifyChange();
                        loadingState = false;
                        setLoadControl(true);
                        mEmptycommand.set(EmptyStatus.BdHiden);
                        mEmptycommand.notifyChange();
                        formartData(resource);
                        if (resource.data != null && mPage == 1) {
                            mItems.clear();
                        }
                        if (resource.data instanceof List) {
                            if (((List) resource.data).size() == 0 || ((List) resource.data).size() < mPageSize) {
                                bdenablenodata.set(true);
                            } else {
                                bdenablenodata.set(false);
                            }
                            bdenablenodata.notifyChange();
                            if (!CollectionUtils.isEmpty((Collection) resource.data)) {
                                mItems.addAll(formatListData((Collection<? extends BaseItemModel>) resource.data));
                            }
                        } else {
                            if (resource.data != null) {
                                mItems.add(formatData((BaseItemModel) resource.data));
                            }
                        }
                        if (mProvideredCardVos.size() > 0 && mPage == 1) {
                            onOuterVmRefresh();
                        }
                        mIsfirstLoad = false;
                        // 添加card
                        addRealCard();
                        //
                        if (resource.data != null) {
                            mPage++;
                        }
                        if (mItems.size() == 0) { // 暂无数据
                            mEmptycommand.set(EmptyStatus.BdEmpty);
                            mEmptycommand.notifyChange();
                            setLoadControl(false);
                        } else {
                            mEmptycommand.set(EmptyStatus.BdHiden);
                            mEmptycommand.notifyChange();
                            setLoadControl(true);
                        }
                        mServerLiveData.setValue("suc");
                    }

                    @Override
                    public void onComplete() {
                        super.onComplete();
                        loadingState = false;
                        loadingOV.set(false);
                        loadingOV.notifyChange();
                        mCompleteCommand.set(true);
                        mCompleteCommand.notifyChange();

                    }

                    @Override
                    public void onBusinessError(Resource<T> resource) {
//                        super.onBusinessError(resource);
//                        setLoadControl(false); --by_zxd
                        mServerLiveData.setValue(null);
                    }

                    @Override
                    public void onNetworkError(Resource<T> resource) {
//                        super.onNetworkError(resource);
                        ToastUtils.showShort("网络失败请重试...");
//                        setLoadControl(false);--by_zxd
                        if (mPage == 1 && mItems.size() == 0) {
                            mEmptycommand.set(EmptyStatus.BdError);
                        } else {
                            mEmptycommand.set(EmptyStatus.BdHiden);
                        }
                        mEmptycommand.notifyChange();
                        mServerLiveData.setValue(null);
                    }
                }));
    }


    public void addRealCard() {
        mEmptycommand.set(EmptyStatus.BdHiden);
        mEmptycommand.notifyChange();

        if (mCardTreeMap.size() > 0) {
            ArrayList<Integer> keysAddList = new ArrayList();
            Iterator<Integer> treemap = mCardTreeMap.keySet().iterator();
            while (treemap.hasNext()) {
                int position = treemap.next();
                BaseItemModel sampleItem = mCardTreeMap.get(position);
                if (mItems.size() > 0 && mItems.size() >= position) {
                    mItems.add(position, sampleItem);
                    keysAddList.add(position);
                } else {
                    if (mItems.size() == 0) {
                        mItems.add(0, sampleItem);
                        keysAddList.add(0);
                    }
                }
            }
            for (int i = 0; i < keysAddList.size(); i++) {
                mCardTreeMap.remove(keysAddList.get(i));
            }
        }
    }

    public void formartData(Resource<T> resource) {

    }

    public void addData(BaseItemModel baseItemModel) {
        mItems.add(baseItemModel);
    }

    public void removeData(BaseItemModel baseItemModel) {
        mItems.remove(baseItemModel);
    }

    public void updateData(int position, BaseItemModel baseItemModel) {
        mItems.set(position, baseItemModel);
    }

    public List<BaseItemModel> searchData(ReponseCommand<List<BaseItemModel>> reponseCommand) {
        return reponseCommand.exectue();
    }


    /*
     * 服务端接口
     * */
    public LiveData<ApiResponse<BaseResponse<T>>> getServicefun(String apiurl, HashMap<String, String> param) {
        return null;
    }

    /*
     * 条目点击事件
     * */
    public void ItemClick(BaseItemModel item, View view) {

    }


    public ReplyCommand NotifyRefreshingCommand;

    public ReplyCommand getNotifyRefreshingCommand() {
        return NotifyRefreshingCommand;
    }

    public void setNotifyRefreshingCommand(ReplyCommand notifyRefreshingCommand) {
        NotifyRefreshingCommand = notifyRefreshingCommand;
    }

    /*
     * 刷新事件
     * */
    public void refresh() {
        if (getNotifyRefreshingCommand() != null) {
            getNotifyRefreshingCommand().exectue();
        }
        if (this instanceof NitcommonCardViewModel) {
            onOuterVmRefresh();
        }
    }

    /*
     *
     *格式化list类型数据
     * */
    public abstract Collection<? extends BaseItemModel> formatListData(Collection<? extends BaseItemModel> data);

    /*
     *格式化单个数据
     * */
    public abstract BaseItemModel formatData(BaseItemModel data);


    public HashMap<Integer, BaseItemModel> mCardTreeMap = new HashMap<>();

    public void addCardVo(BaseItemModel sampleItem, int position, boolean isadd) {
        if (isadd) { // 备份card
            mProvideredCardVos.add(sampleItem);
        }
        if (this instanceof NitcommonCardViewModel) { // 不需要网络请求
            mItems.add(sampleItem);
            mIsfirstLoad = false;
        } else {   // 需要网络请求
            addToCardBuffer(sampleItem, position);
        }
    }

    public void addToCardBuffer(BaseItemModel sampleItem, int position) {
        if (sampleItem instanceof TypeMakerInterface && mCardTreeMap.containsValue(sampleItem)) {
            return;
        }
        if (mCardTreeMap.containsKey(position)) {
            position++;
            addToCardBuffer(sampleItem, position);
        } else {
            mCardTreeMap.put(position, sampleItem);
        }
    }

    public void loadCardData(BaseCardVo baseCardVo) {

    }


    public void onOuterVmRefresh() {
        for (int i = 0; i < mProvideredCardVos.size(); i++) {
            if (mProvideredCardVos.get(i) instanceof BaseCardVo) {
                if (!(this instanceof NitcommonCardViewModel) && !mIsfirstLoad) {
                    addCardVo(mProvideredCardVos.get(i), ((BaseCardVo) mProvideredCardVos.get(i)).position, false);
                }
                if (!TextUtils.isEmpty(((BaseCardVo) mProvideredCardVos.get(i)).mVmPath) && ((BaseCardVo) mProvideredCardVos.get(i)).mNitcommonCardViewModel != null) {
                    ((BaseCardVo) mProvideredCardVos.get(i)).mNitcommonCardViewModel.loadCardData(((BaseCardVo) mProvideredCardVos.get(i)));
                }
            } else if (mProvideredCardVos.get(i) instanceof TypeMakerInterface) {

                addToCardBuffer(mProvideredCardVos.get(i), ((TypeMakerInterface) mProvideredCardVos.get(i)).getPosition());
            }
        }
    }

    // 只刷新不添加
    public void onJustRefresh() {
        for (int i = 0; i < mProvideredCardVos.size(); i++) {
            if (mProvideredCardVos.get(i) instanceof BaseCardVo) {
                if (!TextUtils.isEmpty(((BaseCardVo) mProvideredCardVos.get(i)).mVmPath) && ((BaseCardVo) mProvideredCardVos.get(i)).mNitcommonCardViewModel != null) {
                    ((BaseCardVo) mProvideredCardVos.get(i)).mNitcommonCardViewModel.loadCardData(((BaseCardVo) mProvideredCardVos.get(i)));
                }
            }
        }
    }

    public void refreshCard() {

    }

    @Override
    protected void onCleared() {
        super.onCleared();
        mProvideredCardVos.clear();
        mProvideredCardVos = null;
        mItems.clear();
        mItems = null;
    }
}
