//package com.docker.circle.vm;
//
//import android.graphics.Color;
//import android.text.TextUtils;
//import android.util.Log;
//import android.view.View;
//
//import androidx.fragment.app.FragmentActivity;
//import androidx.lifecycle.LiveData;
//import androidx.lifecycle.MediatorLiveData;
//
//import com.alibaba.android.arouter.launcher.ARouter;
//import com.blankj.utilcode.util.ActivityUtils;
//import com.blankj.utilcode.util.GsonUtils;
//import com.blankj.utilcode.util.KeyboardUtils;
//import com.blankj.utilcode.util.ToastUtils;
//import com.docker.circle.R;
//import com.docker.circle.vo.ServiceDataBean;
//import com.docker.common.BR;
//import com.docker.common.model.OnItemClickListener;
//import com.docker.common.util.CacheUtils;
//import com.docker.common.vm.base.NitCommonContainerViewModel;
//import com.docker.common.vo.UserInfoVo;
//import com.docker.commonapi.vo.base.DynamicDataBase;
//import com.docker.commonapi.vo.base.DynamicResource;
//import com.docker.commonapi.vo.base.ExtDataBase;
//import com.docker.core.di.module.net.repository.NitBoundCallback;
//import com.docker.core.di.module.net.repository.NitNetBoundObserver;
//import com.docker.core.di.module.net.repository.Resource;
//import com.docker.core.di.module.net.response.ApiResponse;
//import com.docker.core.di.module.net.response.BaseResponse;
//import com.google.gson.reflect.TypeToken;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//import javax.inject.Inject;
//public class CircleDynamicListViewModel extends NitCommonContainerViewModel {
//
//
//    public int flag = 0;
//    public final MediatorLiveData<CommentRstVo> mCommentVoMLiveData = new MediatorLiveData<>();
//    public final MediatorLiveData<String> mCollectLv = new MediatorLiveData<>();
//    public final MediatorLiveData<String> mAttenLv = new MediatorLiveData<>();
//
//    @Inject
//    CircleApiService circleApiService;
//
//    @Inject
//    public CircleDynamicListViewModel() {
//
//    }
//
//
//    @Override
//    public LiveData<ApiResponse<BaseResponse<List<DynamicDataBase>>>> getServicefun(String apiurl, HashMap param) {
//
//        if (!TextUtils.isEmpty(apiurl)) {
//            return circleApiService.fechCircleInfoList(apiurl, param);
//        }
//        return circleApiService.fechCircleInfoList(param);
//    }
//
//    // 点赞
//    public OnItemClickListener onItemDz() {
//        return (item, view) -> {
//
//        };
//    }
//
//
//    @Override
//    public void formartData(Resource resource) {
//        super.formartData(resource);
//        ArrayList<ExtDataBase> extDataBases = new ArrayList<>();
//        if (resource.data != null) {
//            for (int i = 0; i < ((List<DynamicDataBase>) resource.data).size(); i++) {
//                if (((List<DynamicDataBase>) resource.data).get(i) != null
//                        && ((List<DynamicDataBase>) resource.data).get(i).extData != null
//                        && ((List<DynamicDataBase>) resource.data).get(i).extData.parent == null) {
//                    ((List<DynamicDataBase>) resource.data).get(i).extData.parent = ((List<DynamicDataBase>) resource.data).get(i);
//                    if (flag == 1) {
//                        ((List<DynamicDataBase>) resource.data).get(i).extData.parent.scope = flag;
//                    }
//                }
//                extDataBases.add(((List<DynamicDataBase>) resource.data).get(i).extData);
//            }
//            resource.data = extDataBases;
//        }
//
//        Log.d("sss", "formartData: ===================="+extDataBases.size());
//    }
//
//    // 动态点击
//    public void ItemDynamicClick(ServiceDataBean item, View view) {
//        ARouter.getInstance().build(AppRouter.CIRCLE_dynamic_v2_detail).withString("dynamicId", item.getDynamicid()).navigation();
////        ARouter.getInstance().build(AppRouter.CIRCLE_DETAIL).withString("dynamicid", item.getDynamicid()).navigation();
//    }
//
//    // 头像点击
//    public void ItemAvaterClick(ServiceDataBean item, View view) {
//        if (CacheUtils.getUser() == null) {
//            ARouter.getInstance().build(AppRouter.ACCOUNT_LOGIN).withBoolean("isFoceLogin", true).navigation();
//            return;
//        }
//        if (item != null) {
//            StaPersionDetail staPersionDetail = new StaPersionDetail();
//            staPersionDetail.name = item.getExtData().linkman;
//            staPersionDetail.uuid = item.getUuid();
//            staPersionDetail.uid = item.getMemberid();
//
//            ARouter.getInstance().build(AppRouter.CIRCLE_person_info).withString("memberid2", item.getMemberid()).withString("uuid2", item.getUuid()).navigation();
//
////            ARouter.getInstance().build(AppRouter.CIRCLE_persion_v2_detail).withSerializable("mStartParam", staPersionDetail).navigation();
//        }
//
//    }
//
//    // 头像点击
//    public void ItemAvaterClick(DynamicDataBase item, View view) {
//        if (CacheUtils.getUser() == null) {
//            ARouter.getInstance().build(AppRouter.ACCOUNT_LOGIN).withBoolean("isFoceLogin", true).navigation();
//            return;
//        }
//        if (item != null) {
//            StaPersionDetail staPersionDetail = new StaPersionDetail();
//            staPersionDetail.name = item.getExtData().linkman;
//            staPersionDetail.uuid = item.getUuid();
//            staPersionDetail.uid = item.getMemberid();
//            ARouter.getInstance().build(AppRouter.CIRCLE_person_info).withString("memberid2", item.getMemberid()).withString("uuid2", item.getUuid()).navigation();
//
////            ARouter.getInstance().build(AppRouter.CIRCLE_persion_v2_detail).withSerializable("mStartParam", staPersionDetail).navigation();
//        }
//
//    }
//
//
//    // 关注
//    public void attention(ServiceDataBean serviceDataBean, View view) {
//        if (CacheUtils.getUser() == null) {
//            ARouter.getInstance().build(AppRouter.ACCOUNT_LOGIN).withBoolean("isFoceLogin", true).navigation();
//            return;
//        }
//        HashMap<String, String> params = new HashMap<>();
//        UserInfoVo userInfoVo = CacheUtils.getUser();
//        params.put("memberid", userInfoVo.uid);
//        params.put("memberid2", serviceDataBean.getMemberid());
//        params.put("uuid2", serviceDataBean.getUuid());
//        params.put("uuid", userInfoVo.uuid);
//        if (!TextUtils.isEmpty(userInfoVo.nickname)) {
//            params.put("nickname", userInfoVo.nickname);
//        } else {
//            params.put("nickname", "匿名");
//        }
//        if (serviceDataBean.getIsFocus() == 1) {
//            params.put("status", "0");
//        } else {
//            params.put("status", "1");
//        }
//        mAttenLv.addSource(
//                RequestServer(
//                        circleApiService.attention(params)), new NitNetBoundObserver<>(new NitBoundCallback<String>() {
//                    @Override
//                    public void onComplete(Resource<String> resource) {
//                        super.onComplete(resource);
//                        hideDialogWait();
//                        if (serviceDataBean.getIsFocus() == 1) {
//                            serviceDataBean.setIsFocus(0);
//                        } else {
//                            serviceDataBean.setIsFocus(1);
//                        }
//                        serviceDataBean.notifyPropertyChanged(BR.isFocus);
//                    }
//
//                    @Override
//                    public void onComplete() {
//                        super.onComplete();
//                        hideDialogWait();
//                    }
//                }));
//    }
//
//
//    // 单一图片或视频点击事件
//    public void singleVideoOrImgClick(ServiceDataBean serviceDataBean, View view) {
//        if (serviceDataBean.getExtData() != null && serviceDataBean.getExtData().getResource() != null && serviceDataBean.getExtData().getResource().get(0) != null) {
//            if (serviceDataBean.getExtData().getResource().get(0).getT() == 2) {
//                String videoUrl = BdUtils.getImgUrl(serviceDataBean.getExtData().getResource().get(0).getUrl());
//                String thumbUrl = BdUtils.getImgUrl(serviceDataBean.getExtData().getResource().get(0).getImg());
//                ARouter.getInstance().build(AppRouter.VIDEOSINGLE).withString("videoUrl", videoUrl).withString("thumbUrl", thumbUrl).navigation();
//            } else {
//                List<ServiceDataBean.ResourceBean> resourceBeans = null;
//                int position = 0;
//                List<LocalMedia> localMediaList = new ArrayList<>();
//                resourceBeans = serviceDataBean.getExtData().getResource();
//                if (resourceBeans != null && resourceBeans.size() > 0) {
//                    for (int i = 0; i < resourceBeans.size(); i++) {
//                        LocalMedia localMedia = new LocalMedia();
//                        localMedia.setPictureType("1");
//                        if (!TextUtils.isEmpty(resourceBeans.get(i).getImg())) {
//                            localMedia.setPath(BdUtils.getImgUrl(resourceBeans.get(i).getImg()));
//                        } else {
//                            localMedia.setPath(BdUtils.getImgUrl(resourceBeans.get(i).getUrl()));
//                        }
//                        localMediaList.add(localMedia);
//                    }
//                }
//                PictureSelector
//                        .create(ActivityUtils.getTopActivity())
//                        .themeStyle(R.style.picture_default_style)
//                        .openExternalPreview(position, localMediaList);
//            }
//        }
//    }
//
//    public void singleVideoOrImgClick(ExtDataBase item, View view) {
//        if (item.resource != null && item.resource.get(0) != null) {
//            if (item.resource.get(0).getT() == 2) {
//                String videoUrl = BdUtils.getImgUrl(item.resource.get(0).getUrl());
//                String thumbUrl = BdUtils.getImgUrl(item.resource.get(0).getImg());
//                ARouter.getInstance().build(AppRouter.VIDEOSINGLE).withString("videoUrl", videoUrl).withString("thumbUrl", thumbUrl).navigation();
//            } else {
//                List<DynamicResource> resourceBeans = null;
//                int position = 0;
//                List<LocalMedia> localMediaList = new ArrayList<>();
//                resourceBeans = item.resource;
//                if (resourceBeans != null && resourceBeans.size() > 0) {
//                    for (int i = 0; i < resourceBeans.size(); i++) {
//                        LocalMedia localMedia = new LocalMedia();
//                        localMedia.setPictureType("1");
//                        if (!TextUtils.isEmpty(resourceBeans.get(i).getImg())) {
//                            localMedia.setPath(BdUtils.getImgUrl(resourceBeans.get(i).getImg()));
//                        } else {
//                            localMedia.setPath(BdUtils.getImgUrl(resourceBeans.get(i).getUrl()));
//                        }
//                        localMediaList.add(localMedia);
//                    }
//                }
//                PictureSelector
//                        .create(ActivityUtils.getTopActivity())
//                        .themeStyle(R.style.picture_default_style)
//                        .openExternalPreview(position, localMediaList);
//            }
//        }
//    }
//
//    //
//    public void ItemZFClick(ServiceDataBean item, View view) {
//        if (CacheUtils.getUser() == null) {
//            ARouter.getInstance().build(AppRouter.ACCOUNT_LOGIN).withBoolean("isFoceLogin", true).navigation();
//            return;
//        }
//        if (item != null) {
//            HashMap<String, String> params = new HashMap<>();
//            UserInfoVo userInfoVo = CacheUtils.getUser();
//            params.put("type", item.getType());
//            params.put("dynamicid", item.getDynamicid());
//            params.put("circleid", item.getCircleid());
//            params.put("utid", item.getUtid());
//            params.put("memberid", userInfoVo.uid);
//            params.put("uuid", userInfoVo.uuid);
//            params.put("dataid", item.getDataid());
//            getShareData(params);
//        }
//    }
//
//    public void ItemZFClick(DynamicDataBase item, View view) {
//        if (CacheUtils.getUser() == null) {
//            ARouter.getInstance().build(AppRouter.ACCOUNT_LOGIN).withBoolean("isFoceLogin", true).navigation();
//            return;
//        }
//        if (item != null) {
//            HashMap<String, String> params = new HashMap<>();
//            UserInfoVo userInfoVo = CacheUtils.getUser();
//            params.put("type", item.getType());
//            params.put("dynamicid", item.getDynamicid());
//            params.put("circleid", item.getCircleid());
//            params.put("utid", item.getUtid());
//            params.put("memberid", userInfoVo.uid);
//            params.put("uuid", userInfoVo.uuid);
//            params.put("dataid", item.getDataid());
//            getShareData(params);
//        }
//    }
//
//    public void getShareData(HashMap<String, String> params) {
////        showDialogWait("加载中...", false);
//        mServerLiveData.addSource(
//                RequestServer(circleApiService.share(params))
//                , new NitNetBoundObserver(new NitBoundCallback<ShareBean>() {
//                    @Override
//                    public void onComplete(Resource<ShareBean> resource) {
//                        super.onComplete(resource);
//                        hideDialogWait();
//                        showShare(resource.data);
//                    }
//
//                    @Override
//                    public void onComplete() {
//                        super.onComplete();
//                        hideDialogWait();
//                    }
//                }));
//
//    }
//
//    public void showShare(ShareBean shareBean) {
//        if (shareBean == null) {
//            return;
//        }
//        ShareBoardConfig config = new ShareBoardConfig();//新建ShareBoardConfig
//        config.setMenuItemBackgroundShape(ShareBoardConfig.BG_SHAPE_CIRCULAR);
//        config.setTitleVisibility(false);
//        config.setIndicatorVisibility(false);
//        config.setCancelButtonVisibility(false);
//        config.setCancelButtonVisibility(false);
//        config.setShareboardBackgroundColor(Color.WHITE);
//        UMImage image = new UMImage(ActivityUtils.getTopActivity(), shareBean.getShareImg());//网络图片
//        image.compressStyle = UMImage.CompressStyle.SCALE;//大小压缩，默认为大小压缩，适合普通很大的图
//        image.compressStyle = UMImage.CompressStyle.QUALITY;//质量压缩，适合长图的分
//        UMWeb web = new UMWeb(shareBean.getShareUrl());
//        web.setTitle(shareBean.getShareTit());//标题
//        web.setThumb(image);  //缩略图
//        web.setDescription(shareBean.getShareDesc());//描述
//        new ShareAction(ActivityUtils.getTopActivity()).withMedia(web)
//                .setDisplayList(SHARE_MEDIA.WEIXIN, SHARE_MEDIA.WEIXIN_CIRCLE, SHARE_MEDIA.WEIXIN_FAVORITE, SHARE_MEDIA.QQ, SHARE_MEDIA.QZONE)
//                .setCallback(new UMShareListener() {
//                    @Override
//                    public void onStart(SHARE_MEDIA share_media) {
//
//                    }
//
//                    @Override
//                    public void onResult(SHARE_MEDIA share_media) {
//
//                    }
//
//                    @Override
//                    public void onError(SHARE_MEDIA share_media, Throwable throwable) {
//                        ToastUtils.showShort("分享失败请重试");
//                    }
//
//                    @Override
//                    public void onCancel(SHARE_MEDIA share_media) {
//
//                    }
//                }).open(config);
//    }
//
//    CommentDialogFragment dialogFragment;
//
//    //
//    public void ItemPLClick(ServiceDataBean serviceDataBean, View view) {
//        if (CacheUtils.getUser() == null) {
//            ARouter.getInstance().build(AppRouter.ACCOUNT_LOGIN).withBoolean("isFoceLogin", true).navigation();
//            return;
//        }
//        if (serviceDataBean == null) {
//            return;
//        }
//        if (serviceDataBean.getType().equals("answer")) { // 问答
//            ARouter.getInstance().build(CIRCLE_comment_v2_ANSWER).withSerializable("datasource", serviceDataBean).navigation();
//        } else {
//            if (dialogFragment == null) {
//                dialogFragment = new CommentDialogFragment();
//            }
//            dialogFragment.setDataCallback(new CommentDialogFragment.DialogFragmentDataCallback() {
//                @Override
//                public void setCommentText(String commentTextTemp) {
//                    KeyboardUtils.hideSoftInput(ActivityUtils.getTopActivity());
//                }
//
//                @Override
//                public void sendComment(String commentTextTemp) {
//                    KeyboardUtils.hideSoftInput(ActivityUtils.getTopActivity());
//                    HashMap<String, String> params = new HashMap<>();
//                    UserInfoVo userInfoVo = CacheUtils.getUser();
//                    params.put("circleid", serviceDataBean.getCircleid());
//                    params.put("utid", serviceDataBean.getUtid());
//                    params.put("dynamicid", serviceDataBean.getDynamicid());
//                    params.put("push_uuid", serviceDataBean.getUuid());
//                    params.put("push_memberid", serviceDataBean.getMemberid());
//                    params.put("memberid", userInfoVo.uid);
//                    params.put("uuid", userInfoVo.uuid);
//                    if (TextUtils.isEmpty(userInfoVo.avatar)) {
//                        userInfoVo.avatar = "/var/upload/image/section/logo3.png";
//                    }
//                    params.put("avatar", userInfoVo.avatar);
//                    if (TextUtils.isEmpty(userInfoVo.nickname)) {
//                        params.put("nickname", "匿名");
//                    } else {
//                        params.put("nickname", userInfoVo.nickname);
//                    }
//                    params.put("content", commentTextTemp);
//                    params.put("cid", "0");
//                    params.put("reply_memberid", "");
//                    params.put("reply_uuid", "");
//                    params.put("reply_nickname", "");
//                    commentDynamic(params);
//                }
//            });
//            dialogFragment.setText("", "发表评论..");
//            dialogFragment.show(((FragmentActivity) ActivityUtils.getTopActivity()).getSupportFragmentManager(), "CommentDialogFragment");
//        }
//    }
//
//    public void ItemPLClick(DynamicDataBase serviceDataBean, View view) {
//        if (CacheUtils.getUser() == null) {
//            ARouter.getInstance().build(AppRouter.ACCOUNT_LOGIN).withBoolean("isFoceLogin", true).navigation();
//            return;
//        }
//        if (serviceDataBean == null) {
//            return;
//        }
//        if (serviceDataBean.getType().equals("answer")) { // 问答 todo
//            ARouter.getInstance().build(CIRCLE_comment_v2_ANSWER).withSerializable("datasource", serviceDataBean).navigation();
//        } else {
//            if (dialogFragment == null) {
//                dialogFragment = new CommentDialogFragment();
//            }
//            dialogFragment.setDataCallback(new CommentDialogFragment.DialogFragmentDataCallback() {
//                @Override
//                public void setCommentText(String commentTextTemp) {
//                    KeyboardUtils.hideSoftInput(ActivityUtils.getTopActivity());
//                }
//
//                @Override
//                public void sendComment(String commentTextTemp) {
//                    KeyboardUtils.hideSoftInput(ActivityUtils.getTopActivity());
//                    HashMap<String, String> params = new HashMap<>();
//                    UserInfoVo userInfoVo = CacheUtils.getUser();
//                    params.put("circleid", serviceDataBean.getCircleid());
//                    params.put("utid", serviceDataBean.getUtid());
//                    params.put("dynamicid", serviceDataBean.getDynamicid());
//                    params.put("push_uuid", serviceDataBean.getUuid());
//                    params.put("push_memberid", serviceDataBean.getMemberid());
//                    params.put("memberid", userInfoVo.uid);
//                    params.put("uuid", userInfoVo.uuid);
//                    if (TextUtils.isEmpty(userInfoVo.avatar)) {
//                        userInfoVo.avatar = "/var/upload/image/section/logo3.png";
//                    }
//                    params.put("avatar", userInfoVo.avatar);
//                    if (TextUtils.isEmpty(userInfoVo.nickname)) {
//                        params.put("nickname", "匿名");
//                    } else {
//                        params.put("nickname", userInfoVo.nickname);
//                    }
//                    params.put("content", commentTextTemp);
//                    params.put("cid", "0");
//                    params.put("reply_memberid", "");
//                    params.put("reply_uuid", "");
//                    params.put("reply_nickname", "");
//                    commentDynamic(params);
//                }
//            });
//            dialogFragment.setText("", "发表评论..");
//            dialogFragment.show(((FragmentActivity) ActivityUtils.getTopActivity()).getSupportFragmentManager(), "CommentDialogFragment");
//        }
//    }
//
//
//    List<ServiceDataBean.ResourceBean> resourcelist = null;
//
//    public void commentDynamic(HashMap<String, String> params) {
//        if (CacheUtils.getUser() == null) {
//            ARouter.getInstance().build(AppRouter.ACCOUNT_LOGIN).withBoolean("isFoceLogin", true).navigation();
//            return;
//        }
//        if (params.containsKey("resource")) {
//            if (resourcelist != null) {
//                resourcelist.clear();
//            }
//            resourcelist = GsonUtils.fromJson(params.get("resource"), new TypeToken<List<ServiceDataBean.ResourceBean>>() {
//            }.getType());
//            params.remove("resource");
//        }
////        showDialogWait("发送中...", false);
//        mCommentVoMLiveData.addSource(
//                RequestServer(
//                        circleApiService.commentDynamic(params)), new NitNetBoundObserver(new NitBoundCallback<CommentRstVo>() {
//                    @Override
//                    public void onComplete(Resource<CommentRstVo> resource) {
//                        super.onComplete(resource);
//                        hideDialogWait();
//                        if (resource.data != null) {
//                            mCommentVoMLiveData.setValue(resource.data);
////                            mVmEventSouce.setValue(new ViewEventResouce(211, "", resource.data));
//                            KeyboardUtils.hideSoftInput(ActivityUtils.getTopActivity());
//                            CommentVo commentVo = new CommentVo();
//                            commentVo.setCommentid(((CommentRstVo) resource.data).commentid);
//                            UserInfoVo userInfoVo = CacheUtils.getUser();
//                            commentVo.setMemberid(userInfoVo.uid);
//                            commentVo.setUuid(userInfoVo.uuid);
//                            commentVo.setNickname(TextUtils.isEmpty(userInfoVo.nickname) ? "匿名" : userInfoVo.nickname);
//                            commentVo.setAvatar(userInfoVo.avatar);
//                            commentVo.setContent(params.get("content"));
//                            commentVo.setPraiseNum("0");
//                            if (resourcelist != null) {
//                                commentVo.setResource(resourcelist);
//                            }
//                            if (params.containsKey("audio")) {
//                                commentVo.setAudio(params.get("audio"));
//                            }
//                            if (params.containsKey("audio_duration")) {
//                                commentVo.setAudio_duration(params.get("audio_duration"));
//                            }
//                            commentVo.setInputtime(String.valueOf(System.currentTimeMillis()).substring(0, String.valueOf(System.currentTimeMillis()).length() - 3));
//                            RxBus.getDefault().post(new RxEvent("comment", commentVo));
//                        }
//                    }
//
//                    @Override
//                    public void onComplete() {
//                        super.onComplete();
//                        hideDialogWait();
//                    }
//                }));
//    }
//
//
//    public final MediatorLiveData<String> mOrderAddLv = new MediatorLiveData<>();
//
//    // 立刻购买
//    public void ShopingToBuy(ServiceDataBean item, View view) {
//        if (CacheUtils.getUser() == null) {
//            ARouter.getInstance().build(AppRouter.ACCOUNT_LOGIN).withBoolean("isFoceLogin", true).navigation();
//            return;
//        }
//        ARouter.getInstance().build(AppRouter.ORDER_MAKER).withSerializable("ServiceDataBean", item).navigation();
////        HashMap<String, String> param = new HashMap<>();
////        param.put("memberid", CacheUtils.getUser().uid);
////        param.put("goodsid", item.getDataid());
////        param.put("operation", "1");
////        mOrderAddLv.addSource(RequestServer(circleApiService.shoppingCarAdd(param)), new NitNetBoundObserver<String>(new NitBoundCallback<String>() {
////            @Override
////            public void onComplete(Resource<String> resource) {
////                super.onComplete(resource);
////                ARouter.getInstance().build(AppRouter.ORDER_MAKER).navigation();
////            }
////        }));
//
//    } // 立刻购买
//
//    public void ShopingToBuy(DynamicDataBase item, View view) {
//        if (CacheUtils.getUser() == null) {
//            ARouter.getInstance().build(AppRouter.ACCOUNT_LOGIN).withBoolean("isFoceLogin", true).navigation();
//            return;
//        }
//        ARouter.getInstance().build(AppRouter.ORDER_MAKER).withSerializable("ServiceDataBean", item).navigation();
////        HashMap<String, String> param = new HashMap<>();
////        param.put("memberid", CacheUtils.getUser().uid);
////        param.put("goodsid", item.getDataid());
////        param.put("operation", "1");
////        mOrderAddLv.addSource(RequestServer(circleApiService.shoppingCarAdd(param)), new NitNetBoundObserver<String>(new NitBoundCallback<String>() {
////            @Override
////            public void onComplete(Resource<String> resource) {
////                super.onComplete(resource);
////                ARouter.getInstance().build(AppRouter.ORDER_MAKER).navigation();
////            }
////        }));
//
//    }
//
//
//    // 收藏
//    public void ItemStoreClick(ServiceDataBean item, View view) {
//        if (item == null) {
//            return;
//        }
//        if (CacheUtils.getUser() == null) {
//            ARouter.getInstance().build(AppRouter.ACCOUNT_LOGIN).withBoolean("isFoceLogin", true).navigation();
//            return;
//        }
////        showDialogWait("加载中...", false);
//        UserInfoVo userInfoVo = CacheUtils.getUser();
//        HashMap<String, String> paramap = new HashMap<>();
//        paramap.put("memberid", userInfoVo.uid);
//        paramap.put("uuid", userInfoVo.uuid);
//        paramap.put("dataid", item.getDataid());
//        paramap.put("dynamicid", item.getDynamicid());
//        paramap.put("type", item.getType());
//        paramap.put("push_uuid", item.getUuid());
//        paramap.put("push_memberid", item.getMemberid());
//        if (!TextUtils.isEmpty(userInfoVo.nickname)) {
//            paramap.put("nickname", userInfoVo.nickname);
//        } else {
//            paramap.put("nickname", "匿名");
//        }
//        if (item.getIsCollect() == 1) {
//            paramap.put("status", "0");
//        } else {
//            paramap.put("status", "1");
//        }
//        mCollectLv.addSource(RequestServer(circleApiService.collect(paramap)),
//                new NitNetBoundObserver<>(new NitBoundCallback<String>() {
//                    @Override
//                    public void onComplete(Resource<String> resource) {
//                        super.onComplete(resource);
//                        this.onComplete();
//                        mCollectLv.setValue(resource.data);
////                        hideDialogWait();
//                        if (item.getIsCollect() == 1) {
//                            item.setIsCollect(0);
//                        } else {
//                            item.setIsCollect(1);
//                        }
//                        item.notifyPropertyChanged(BR.isCollect);
//                        RxBus.getDefault().post(new RxEvent<>("refresh_collect", item));
//                    }
//
//                    @Override
//                    public void onComplete() {
//                        super.onComplete();
////                        hideDialogWait();
//                    }
//
//                    @Override
//                    public void onNetworkError(Resource<String> resource) {
////                        super.onNetworkError(resource);
////                        hideDialogWait();
//                        if (item.getIsCollect() == 1) {
//                            item.setIsCollect(0);
//                        } else {
//                            item.setIsCollect(1);
//                        }
//                        item.notifyPropertyChanged(BR.isCollect);
//                    }
//                }));
//        //
//    }
//
//    public void ItemStoreClick(DynamicDataBase item, View view) {
//        if (item == null) {
//            return;
//        }
//        if (CacheUtils.getUser() == null) {
//            ARouter.getInstance().build(AppRouter.ACCOUNT_LOGIN).withBoolean("isFoceLogin", true).navigation();
//            return;
//        }
////        showDialogWait("加载中...", false);
//        UserInfoVo userInfoVo = CacheUtils.getUser();
//        HashMap<String, String> paramap = new HashMap<>();
//        paramap.put("memberid", userInfoVo.uid);
//        paramap.put("uuid", userInfoVo.uuid);
//        paramap.put("dataid", item.getDataid());
//        paramap.put("dynamicid", item.getDynamicid());
//        paramap.put("type", item.getType());
//        paramap.put("push_uuid", item.getUuid());
//        paramap.put("push_memberid", item.getMemberid());
//        if (!TextUtils.isEmpty(userInfoVo.nickname)) {
//            paramap.put("nickname", userInfoVo.nickname);
//        } else {
//            paramap.put("nickname", "匿名");
//        }
//        if (item.getIsCollect() == 1) {
//            paramap.put("status", "0");
//        } else {
//            paramap.put("status", "1");
//        }
//        mCollectLv.addSource(RequestServer(circleApiService.collect(paramap)),
//                new NitNetBoundObserver<>(new NitBoundCallback<String>() {
//                    @Override
//                    public void onComplete(Resource<String> resource) {
//                        super.onComplete(resource);
//                        this.onComplete();
//                        mCollectLv.setValue(resource.data);
////                        hideDialogWait();
//                        if (item.getIsCollect() == 1) {
//                            item.setIsCollect(0);
//                        } else {
//                            item.setIsCollect(1);
//                        }
//                        item.notifyPropertyChanged(BR.isCollect);
//                        RxBus.getDefault().post(new RxEvent<>("refresh_collect", item));
//                    }
//
//                    @Override
//                    public void onComplete() {
//                        super.onComplete();
////                        hideDialogWait();
//                    }
//
//                    @Override
//                    public void onNetworkError(Resource<String> resource) {
////                        super.onNetworkError(resource);
////                        hideDialogWait();
//                        if (item.getIsCollect() == 1) {
//                            item.setIsCollect(0);
//                        } else {
//                            item.setIsCollect(1);
//                        }
//                        item.notifyPropertyChanged(BR.isCollect);
//                    }
//                }));
//        //
//    }
//
//
//    // 条目中的评论 进入详情
//    public void ItemPLClick(ServiceDataBean item) {
//        ARouter.getInstance().build(AppRouter.CIRCLE_dynamic_v2_detail).withString("dynamicId", item.getDynamicid()).navigation();
//    }
//
//    //
//    public void ItemDZClick(ServiceDataBean item, View view) {
//        if (item == null) {
//            return;
//        }
//        if (CacheUtils.getUser() == null) {
//            ARouter.getInstance().build(AppRouter.ACCOUNT_LOGIN).withBoolean("isFoceLogin", true).navigation();
//            return;
//        }
////        showDialogWait("加载中...", false);
//        UserInfoVo userInfoVo = CacheUtils.getUser();
//        Map<String, String> params = new HashMap<>();
//        params.put("memberid", userInfoVo.uid);
//        params.put("uuid", userInfoVo.uuid);
//        params.put("dynamicid", item.getDynamicid());
//        params.put("status", item.getIsFav() == 1 ? "0" : "1");
//        params.put("push_uuid", item.getUuid());
//        params.put("push_memberid", item.getMemberid());
//        if (!TextUtils.isEmpty(userInfoVo.nickname)) {
//            params.put("nickname", "匿名");
//        }
//        mServerLiveData.addSource(
//                RequestServer(circleApiService.dynamicParise(params))
//                , new NitNetBoundObserver(new NitBoundCallback<ServiceDataBean.ShareBean>() {
//                    @Override
//                    public void onComplete(Resource<ServiceDataBean.ShareBean> resource) {
//                        super.onComplete(resource);
//                        hideDialogWait();
//                        item.notifyPropertyChanged(BR.isFav);
//                        item.notifyPropertyChanged(BR.favourNum);
//                        if (item.getIsFav() == 1) { //1赞 0取消赞
//                            item.setIsFav(0);
//                            item.setFavourNum((item.getFavourNum()) - 1);
//                        } else {
//                            showDzGoodsView(view);
//                            item.setIsFav(1);
//                            item.setFavourNum(item.getFavourNum() + 1);
//                        }
//                        RxBus.getDefault().post(new RxEvent<>("dz", item));
//                    }
//
//                    @Override
//                    public void onComplete() {
//                        super.onComplete();
//                        hideDialogWait();
//                    }
//                }));
//    }
//
//    public void ItemDZClick(DynamicDataBase item, View view) {
//        if (item == null) {
//            return;
//        }
//        if (CacheUtils.getUser() == null) {
//            ARouter.getInstance().build(AppRouter.ACCOUNT_LOGIN).withBoolean("isFoceLogin", true).navigation();
//            return;
//        }
////        showDialogWait("加载中...", false);
//        UserInfoVo userInfoVo = CacheUtils.getUser();
//        Map<String, String> params = new HashMap<>();
//        params.put("memberid", userInfoVo.uid);
//        params.put("uuid", userInfoVo.uuid);
//        params.put("dynamicid", item.getDynamicid());
//        params.put("status", item.getIsFav() == 1 ? "0" : "1");
//        params.put("push_uuid", item.getUuid());
//        params.put("push_memberid", item.getMemberid());
//        if (!TextUtils.isEmpty(userInfoVo.nickname)) {
//            params.put("nickname", "匿名");
//        }
//        mServerLiveData.addSource(
//                RequestServer(circleApiService.dynamicParise(params))
//                , new NitNetBoundObserver(new NitBoundCallback<ServiceDataBean.ShareBean>() {
//                    @Override
//                    public void onComplete(Resource<ServiceDataBean.ShareBean> resource) {
//                        super.onComplete(resource);
//                        hideDialogWait();
//                        item.notifyPropertyChanged(BR.isFav);
//                        item.notifyPropertyChanged(BR.favourNum);
//                        if (item.getIsFav() == 1) { //1赞 0取消赞
//                            item.setIsFav(0);
//                            item.setFavourNum((item.getFavourNum()) - 1);
//                        } else {
//                            showDzGoodsView(view);
//                            item.setIsFav(1);
//                            item.setFavourNum(item.getFavourNum() + 1);
//                        }
//                        RxBus.getDefault().post(new RxEvent<>("dz", item));
//                    }
//
//                    @Override
//                    public void onComplete() {
//                        super.onComplete();
//                        hideDialogWait();
//                    }
//                }));
//    }
//
//    //
//    public void ItemDZGoodsClick(ServiceDataBean item, View view) {
//        if (item == null) {
//            return;
//        }
//        if (CacheUtils.getUser() == null) {
//            ARouter.getInstance().build(AppRouter.ACCOUNT_LOGIN).withBoolean("isFoceLogin", true).navigation();
//            return;
//        }
////        showDialogWait("加载中...", false);
//        UserInfoVo userInfoVo = CacheUtils.getUser();
//        Map<String, String> params = new HashMap<>();
//        params.put("memberid", userInfoVo.uid);
//        params.put("uuid", userInfoVo.uuid);
//        params.put("dynamicid", item.getDynamicid());
//        params.put("status", item.getIsFav() == 1 ? "0" : "1");
//        params.put("push_uuid", item.getUuid());
//        params.put("push_memberid", item.getMemberid());
//        if (!TextUtils.isEmpty(userInfoVo.nickname)) {
//            params.put("nickname", "匿名");
//        }
//        mServerLiveData.addSource(
//                RequestServer(circleApiService.dynamicParise(params))
//                , new NitNetBoundObserver(new NitBoundCallback<ServiceDataBean.ShareBean>() {
//                    @Override
//                    public void onComplete(Resource<ServiceDataBean.ShareBean> resource) {
//                        super.onComplete(resource);
//                        hideDialogWait();
//                        item.notifyPropertyChanged(BR.isFav);
//                        item.notifyPropertyChanged(BR.favourNum);
//                        if (item.getIsFav() == 1) { //1赞 0取消赞
//                            item.setIsFav(0);
//                            item.setFavourNum((item.getFavourNum()) - 1);
//                        } else {
//                            showDzGoodsView2(view);
//                            item.setIsFav(1);
//                            item.setFavourNum(item.getFavourNum() + 1);
//                        }
//                        RxBus.getDefault().post(new RxEvent<>("dz", item));
//                    }
//
//                    @Override
//                    public void onComplete() {
//                        super.onComplete();
//                        hideDialogWait();
//                    }
//                }));
//    }
//
//    public void ItemDZGoodsClick(DynamicDataBase item, View view) {
//        if (item == null) {
//            return;
//        }
//        if (CacheUtils.getUser() == null) {
//            ARouter.getInstance().build(AppRouter.ACCOUNT_LOGIN).withBoolean("isFoceLogin", true).navigation();
//            return;
//        }
////        showDialogWait("加载中...", false);
//        UserInfoVo userInfoVo = CacheUtils.getUser();
//        Map<String, String> params = new HashMap<>();
//        params.put("memberid", userInfoVo.uid);
//        params.put("uuid", userInfoVo.uuid);
//        params.put("dynamicid", item.getDynamicid());
//        params.put("status", item.getIsFav() == 1 ? "0" : "1");
//        params.put("push_uuid", item.getUuid());
//        params.put("push_memberid", item.getMemberid());
//        if (!TextUtils.isEmpty(userInfoVo.nickname)) {
//            params.put("nickname", "匿名");
//        }
//        mServerLiveData.addSource(
//                RequestServer(circleApiService.dynamicParise(params))
//                , new NitNetBoundObserver(new NitBoundCallback<ServiceDataBean.ShareBean>() {
//                    @Override
//                    public void onComplete(Resource<ServiceDataBean.ShareBean> resource) {
//                        super.onComplete(resource);
//                        hideDialogWait();
//                        item.notifyPropertyChanged(BR.isFav);
//                        item.notifyPropertyChanged(BR.favourNum);
//                        if (item.getIsFav() == 1) { //1赞 0取消赞
//                            item.setIsFav(0);
//                            item.setFavourNum((item.getFavourNum()) - 1);
//                        } else {
//                            showDzGoodsView2(view);
//                            item.setIsFav(1);
//                            item.setFavourNum(item.getFavourNum() + 1);
//                        }
//                        RxBus.getDefault().post(new RxEvent<>("dz", item));
//                    }
//
//                    @Override
//                    public void onComplete() {
//                        super.onComplete();
//                        hideDialogWait();
//                    }
//                }));
//    }
//
//
//    private void showDzGoodsView(View view) {
//        GoodView goodView = new GoodView(view.getContext());
//        goodView.setDistance(100);
//        goodView.setImage(R.mipmap.circle_icon_dz2);
//        GoodView goodView1 = new GoodView(view.getContext());
//        goodView1.setDistance(110);
//        goodView1.setImage(R.mipmap.circle_icon_dz2);
//        GoodView goodView2 = new GoodView(view.getContext());
//        goodView2.setDistance(140);
//        goodView2.setImage(R.mipmap.circle_icon_dz2);
//        goodView.show(view);
//        goodView1.show(view);
//        goodView2.show(view);
//    }
//
//    private void showDzGoodsView2(View view) {
//
//        GoodView goodView = new GoodView(view.getContext());
//        goodView.setDistance(100);
//        goodView.setImage(R.mipmap.circle_icon_hd_dz2);
//        GoodView goodView1 = new GoodView(view.getContext());
//        goodView.setDistance(110);
//        goodView1.setImage(R.mipmap.circle_icon_hd_dz2);
//        GoodView goodView2 = new GoodView(view.getContext());
//        goodView2.setDistance(140);
//        goodView2.setImage(R.mipmap.circle_icon_hd_dz2);
//        goodView.show(view);
//        goodView1.show(view);
//        goodView2.show(view);
//    }
//
//
//    // 内部九宫格点击
//    public static void imgclick(ServiceDataBean.ResourceBean item, View view, ServiceDataBean
//            serviceDataBean) {
//        if (serviceDataBean != null && serviceDataBean.getExtData() != null && serviceDataBean.getExtData().getResource() != null) {
//            int index = serviceDataBean.getInnerResource().get().indexOf(item);
//            if (serviceDataBean.getExtData().getResource().get(index).getT() == 2) {
//                String videoUrl = BdUtils.getImgUrl(serviceDataBean.getExtData().getResource().get(index).getUrl());
//                String thumbUrl = BdUtils.getImgUrl(serviceDataBean.getExtData().getResource().get(index).getImg());
//                ARouter.getInstance().build(AppRouter.VIDEOSINGLE).withString("videoUrl", videoUrl).withString("thumbUrl", thumbUrl).navigation();
//            } else {
//                List<ServiceDataBean.ResourceBean> resourceBeans = null;
//                List<LocalMedia> localMediaList = new ArrayList<>();
//                resourceBeans = serviceDataBean.getExtData().getResource();
//                if (resourceBeans != null && resourceBeans.size() > 0) {
//                    for (int i = 0; i < resourceBeans.size(); i++) {
//                        LocalMedia localMedia = new LocalMedia();
//                        localMedia.setPictureType("1");
//                        if (!TextUtils.isEmpty(resourceBeans.get(i).getImg())) {
//                            localMedia.setPath(BdUtils.getImgUrl(resourceBeans.get(i).getImg()));
//                        } else {
//                            localMedia.setPath(BdUtils.getImgUrl(resourceBeans.get(i).getUrl()));
//                        }
//                        localMediaList.add(localMedia);
//                    }
//                }
//                PictureSelector
//                        .create(ActivityUtils.getTopActivity())
//                        .themeStyle(R.style.picture_default_style)
//                        .openExternalPreview(index, localMediaList);
//            }
//        } else {
//            List<LocalMedia> localMediaList = new ArrayList<>();
//            LocalMedia localMedia = new LocalMedia();
//            if (!TextUtils.isEmpty(item.getImg())) {
//                localMedia.setPath(BdUtils.getImgUrl(item.getImg()));
//            } else {
//                localMedia.setPath(BdUtils.getImgUrl(item.getUrl()));
//            }
//            localMediaList.add(localMedia);
//            PictureSelector
//                    .create(ActivityUtils.getTopActivity())
//                    .themeStyle(R.style.picture_default_style)
//                    .openExternalPreview(0, localMediaList);
//        }
//    }// 内部九宫格点击
//
//    public static void imgclick(DynamicResource item, View view, ExtDataBase extDataBase) {
//        if (item != null && extDataBase != null) {
//            int index = extDataBase.resource.indexOf(item);
//            if (item.getT() == 2) {
//                String videoUrl = BdUtils.getImgUrl(item.getUrl());
//                String thumbUrl = BdUtils.getImgUrl(item.getImg());
//                ARouter.getInstance().build(AppRouter.VIDEOSINGLE).withString("videoUrl", videoUrl).withString("thumbUrl", thumbUrl).navigation();
//            } else {
//                List<DynamicResource> resourceBeans = null;
//                List<LocalMedia> localMediaList = new ArrayList<>();
//                resourceBeans = extDataBase.resource;
//                if (resourceBeans != null && resourceBeans.size() > 0) {
//                    for (int i = 0; i < resourceBeans.size(); i++) {
//                        LocalMedia localMedia = new LocalMedia();
//                        localMedia.setPictureType("1");
//                        if (!TextUtils.isEmpty(resourceBeans.get(i).getImg())) {
//                            localMedia.setPath(BdUtils.getImgUrl(resourceBeans.get(i).getImg()));
//                        } else {
//                            localMedia.setPath(BdUtils.getImgUrl(resourceBeans.get(i).getUrl()));
//                        }
//                        localMediaList.add(localMedia);
//                    }
//                }
//                PictureSelector
//                        .create(ActivityUtils.getTopActivity())
//                        .themeStyle(R.style.picture_default_style)
//                        .openExternalPreview(index, localMediaList);
//            }
//        } else {
////            List<LocalMedia> localMediaList = new ArrayList<>();
////            LocalMedia localMedia = new LocalMedia();
////            if (!TextUtils.isEmpty(item.getImg())) {
////                localMedia.setPath(BdUtils.getImgUrl(item.getImg()));
////            } else {
////                localMedia.setPath(BdUtils.getImgUrl(item.getUrl()));
////            }
////            localMediaList.add(localMedia);
////            PictureSelector
////                    .create(ActivityUtils.getTopActivity())
////                    .themeStyle(R.style.picture_default_style)
////                    .openExternalPreview(0, localMediaList);
//        }
//    }
//
//}
