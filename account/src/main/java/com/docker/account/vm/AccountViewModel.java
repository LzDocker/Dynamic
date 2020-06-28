package com.docker.account.vm;

import android.text.TextUtils;

import androidx.hilt.lifecycle.ViewModelInject;
import androidx.lifecycle.MediatorLiveData;

import com.blankj.utilcode.util.AppUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.docker.account.api.AccountService;
import com.docker.account.vo.RegistParamTransVo;
import com.docker.account.vo.RegistVo;
import com.docker.common.util.CacheUtils;
import com.docker.common.util.NitCommonBoundCallBack;
import com.docker.common.vm.base.NitCommonVm;
import com.docker.common.vo.UserInfoVo;
import com.docker.commonapi.router.ParamSample;
import com.docker.commonapi.vo.RstVo;
import com.docker.core.di.module.net.repository.CommonRepository;
import com.docker.core.di.module.net.repository.NitBoundCallback;
import com.docker.core.di.module.net.repository.NitNetBoundObserver;
import com.docker.core.di.module.net.repository.Resource;

import java.util.HashMap;

public class AccountViewModel extends NitCommonVm {

    AccountService accountService;

    @ViewModelInject
    public AccountViewModel(CommonRepository commonRepository, AccountService accountService) {
        super(commonRepository);
        this.accountService = accountService;
    }

    public final MediatorLiveData<UserInfoVo> mLoginLivedata = new MediatorLiveData<>();
    public final MediatorLiveData<String> mThiredLoginLivedata = new MediatorLiveData<>();
    public final MediatorLiveData<String> mResetLivedata = new MediatorLiveData<>();
    public final MediatorLiveData<RstVo> mSmsLivedata = new MediatorLiveData<>();
    public final MediatorLiveData<RegistVo> mRegLivedata = new MediatorLiveData<>();


    public void LoginByAccount(ParamSample paramSample) {
        LoginByAccount(paramSample.mGotMap);
    }

    /*
     *
     *
     * 账号密码登录
     * */
    public void LoginByAccount(HashMap<String, String> param) {

        param.put("clientType", "2");
        param.put("version", AppUtils.getAppVersionCode() + "");
        mLoginLivedata.addSource(commonRepository.noneChache(accountService.login(param)),
                new NitNetBoundObserver<>(new NitBoundCallback<UserInfoVo>() {
                    @Override
                    public void onLoading() {
                        super.onLoading();
                        showDialogWait("登录中...", false);
                    }

                    @Override
                    public void onComplete(Resource<UserInfoVo> resource) {
                        super.onComplete(resource);
                        mLoginLivedata.setValue(resource.data);
                        if (resource.data != null) {
                            CacheUtils.saveUser(resource.data);
                        } else {
                            ToastUtils.showShort("账号异常");
                        }
                    }

                    @Override
                    public void onComplete() {
                        super.onComplete();
                        hideDialogWait();
                    }

                    @Override
                    public void onBusinessError(Resource<UserInfoVo> resource) {
                        super.onBusinessError(resource);
                        mLoginLivedata.setValue(null);
                        if (!TextUtils.isEmpty(resource.message)) {
                            ToastUtils.showShort(resource.message);
                        }

                        if (param.get("t") != null) {
                            mThiredLoginLivedata.setValue(param.get("t"));
                        }
                    }

                    @Override
                    public void onNetworkError(Resource<UserInfoVo> resource) {
                        super.onNetworkError(resource);
                        ToastUtils.showShort("网络失败请重试");
                    }
                }));
    }

    /*
     *
     *重置密码
     * */
    public void ResetPwd(HashMap<String, String> param) {
        mResetLivedata.addSource(commonRepository.noneChache(accountService.resetPwd(param)),
                new NitNetBoundObserver<>(new NitCommonBoundCallBack<String>() {
                    @Override
                    public void onLoading() {
                        super.onLoading();
                        showDialogWait("加载中...", false);
                    }

                    @Override
                    public void onComplete(Resource<String> resource) {
                        super.onComplete(resource);
                        mResetLivedata.setValue(resource.data);
                    }

                    @Override
                    public void onComplete() {
                        super.onComplete();
                        hideDialogWait();
                    }

                }));
    }

    /*
     *
     *发送验证码
     * */
    public void SendSmsCode(HashMap<String, String> param) {
        mSmsLivedata.addSource(commonRepository.noneChache(accountService.sendSmsCode(param)),
                new NitNetBoundObserver<>(new NitCommonBoundCallBack<RstVo>() {
                    @Override
                    public void onLoading() {
                        super.onLoading();
                        showDialogWait("加载中...", false);
                    }

                    @Override
                    public void onComplete(Resource<RstVo> resource) {
                        super.onComplete(resource);
                        mSmsLivedata.setValue(resource.data);
                    }

                    @Override
                    public void onComplete() {
                        super.onComplete();
                        hideDialogWait();
                    }

                }));
    }


    public void Register(HashMap<String, String> param, RegistParamTransVo registParamTransVo) {
        if (registParamTransVo != null) {
            param.put("reg_type", registParamTransVo.bindType);
            if ("1".equals(registParamTransVo.bindType)) {
                param.put("nickname", registParamTransVo.wechatInfo.get("name"));
                param.put("avatar", registParamTransVo.wechatInfo.get("iconurl"));
                param.put("wxUid", registParamTransVo.wechatInfo.get("unionid"));
            }
            if ("2".equals(registParamTransVo.bindType)) {
                param.put("nickname", registParamTransVo.wechatInfo.get("name"));
                param.put("avatar", registParamTransVo.wechatInfo.get("iconurl"));
                param.put("qqUid", registParamTransVo.wechatInfo.get("uid"));
            }
        }
        mRegLivedata.addSource(commonRepository.noneChache(accountService.register(param)),
                new NitNetBoundObserver<>(new NitCommonBoundCallBack<RegistVo>() {
                    @Override
                    public void onLoading() {
                        super.onLoading();
                        showDialogWait("加载中...", false);
                    }

                    @Override
                    public void onComplete(Resource<RegistVo> resource) {
                        super.onComplete(resource);
                        mRegLivedata.setValue(resource.data);
                    }

                    @Override
                    public void onComplete() {
                        super.onComplete();
                        hideDialogWait();
                    }
                }));

    }


}
