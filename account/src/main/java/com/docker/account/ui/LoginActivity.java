package com.docker.account.ui;

import android.text.TextUtils;
import android.util.Log;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.blankj.utilcode.util.EncryptUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.docker.account.R;
import com.docker.account.databinding.AccountActivityLoginBinding;
import com.docker.account.vm.AccountViewModel;
import com.docker.account.vo.RegistParamTransVo;
import com.docker.common.ui.base.NitCommonActivity;
import com.docker.common.util.CacheUtils;
import com.docker.commonapi.anno.PagerPrivoderKeys;
import com.docker.commonapi.router.ResultParam;
import com.docker.commonapi.router.RouterConstKey;
import com.docker.commonapi.router.RouterManager;
import com.docker.commonapi.router.RouterParam;
import com.docker.commonapi.service.JpushService;
import com.docker.commonapi.service.ThirdShareAndLoginService;
import com.docker.core.command.ReplyCommandParam;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

import static com.docker.account.service.AccountRouterConstantService.ACCOUNT_LOGIN;
import static com.docker.account.service.AccountRouterConstantService.ACCOUNT_REGISTER;
import static com.docker.commonapi.router.RouterConstKey.PUSH_SERVER;
import static com.docker.commonapi.router.RouterConstKey.UMENG_SERVER;
import static com.docker.push.JpushRouterConstant.JPUSH_SERVICE;

@PagerPrivoderKeys(routerName = RouterConstKey.ACCOUNT_LOGIN, providerKeysArr = {"userName", "pwd", "areacode"}, providerObj = {})
@Route(path = ACCOUNT_LOGIN)
@AndroidEntryPoint
public class LoginActivity extends NitCommonActivity<AccountViewModel, AccountActivityLoginBinding> {

    private String area_code = "+86";

    private HashMap<String, String> wechatInfo = null;

    private ThirdShareAndLoginService thirdShareAndLoginService;

    @Inject
    RouterManager routerManager;

    @Override
    protected int getLayoutId() {
        return R.layout.account_activity_login;
    }

    @Override
    public AccountViewModel getmViewModel() {
        return new ViewModelProvider(this).get(AccountViewModel.class);
    }

    @Override
    public void initView() {
        mToolbar.setNavigation(false);

        for (Map.Entry<String, String> entry : mPageProviderKeys.entrySet()) {
            String mapKey = entry.getKey();
            String mapValue = entry.getValue();
            Log.d("TAG", mRouterName + "========" + mapKey + ":" + mapValue);
        }

        Log.d("TAG", "initView: ======loginact=========" + mDefaultParam);

        // 登录
        mBinding.accountLoginButton.setOnClickListener(v -> {
            if (CheckInput()) {
                HashMap<String, String> param = new HashMap<>();
                param.put("username", mBinding.edUsername.getText().toString());
                param.put("password", EncryptUtils.encryptMD5ToString(mBinding.edPassword.getText().toString()));
                param.put("area_code", area_code);
                mViewModel.LoginByAccount(param);
            }
        });

        // 选择区域
        mBinding.tvNum.setOnClickListener(v -> {
            routerManager.Jump(new RouterParam.RouterBuilder(RouterConstKey.COMPONENTS_COUNTRY_INDEX).withFrom(RouterConstKey.ACCOUNT_LOGIN).withmRosponseCommand((o, o2) -> {
                area_code = routerManager.getReturnResult((HashMap<String, Object>) o2, "WorldNumList.WorldEnty", "global_num");
                mBinding.tvNum.setText(area_code);
                return null;
            }).create());
        });

        // 忘记密码
        mBinding.accountFoggetPwd.setOnClickListener(v -> {

            // 参数为 处理返回结果的对象 和 对象对应的函数  主： 函数接收数据类型为：ParamSample paramSample 包含一个map
            ResultParam resultParam = new ResultParam(LoginActivity.this.mViewModel, "LoginByAccount");
            /* 此map 在此处赋值的key 将会在目标界面返回，并赋valueh后提供给 上面的函数执行
             * key  当前界面需要的key
             * value reset界面能提供的key 值
             * */
            resultParam.TransPortPramMap.put("username", "userName");
            resultParam.TransPortPramMap.put("password", "pwd");
            resultParam.TransPortPramMap.put("area_code", "areacode");
//            resultParam.TransPortPramMap.put("uid", "UserInfoVo_uid");
//            resultParam.TransPortPramMap.put("uid2", "UserInfoVo_uid");
            routerManager.Jump(new RouterParam.RouterBuilder(RouterConstKey.ACCOUNT_RESET)
                    .withParam("找回密码").withFrom(mRouterName).withmRosponseCommand((o, o2) -> {
                        HashMap<String, String> result = (HashMap<String, String>) o;
                        mBinding.edUsername.setText(result.get("userName"));
                        mBinding.edPassword.setText(result.get("pwdnomal"));
                        area_code = result.get("areacode");
                        return resultParam;
                    }).create());
        });

        // 注册
        mBinding.accountToRegister.setOnClickListener(v -> {
            routerManager.Jump(new RouterParam.RouterBuilder(RouterConstKey.ACCOUNT_REGISTER).create());
        });

        // 微信
        mBinding.accountWechatLogin.setOnClickListener(v -> {
            thirdShareAndLoginService = (ThirdShareAndLoginService) ARouter.getInstance().build(UMENG_SERVER).navigation();
            thirdShareAndLoginService.thiredLogin(0, (ReplyCommandParam) o -> {
                wechatInfo = (HashMap<String, String>) o;
                HashMap<String, String> param = new HashMap<>();
                param.put("wxUid", wechatInfo.get("unionid"));
                param.put("nickname", wechatInfo.get("name"));
                param.put("avatar", wechatInfo.get("iconurl"));
                param.put("t", "wx");
                param.put("source", "1");
                param.put("udid", CacheUtils.getudid());
                mViewModel.LoginByAccount(param);
            });
        });

        // qq
        mBinding.accountQqLogin.setOnClickListener(v -> {
            thirdShareAndLoginService = (ThirdShareAndLoginService) ARouter.getInstance().build(UMENG_SERVER).navigation();
            thirdShareAndLoginService.thiredLogin(1, (ReplyCommandParam) o -> {
                wechatInfo = (HashMap<String, String>) o;
                HashMap<String, String> param = new HashMap<>();
                param.put("nickname", wechatInfo.get("name"));
                param.put("avatar", wechatInfo.get("iconurl"));
                param.put("qqUid", wechatInfo.get("uid"));
                param.put("t", "qq");
                param.put("source", "1");
                param.put("udid", CacheUtils.getudid());
                mViewModel.LoginByAccount(param);
            });
        });
    }


    private boolean CheckInput() {
        if (TextUtils.isEmpty(mBinding.edUsername.getText().toString().trim())) {
            ToastUtils.showShort("用户名不能为空");
            return false;
        }
        if (TextUtils.isEmpty(mBinding.edPassword.getText().toString().trim())) {
            ToastUtils.showShort("密码不能为空");
            return false;
        }
        return true;
    }


    @Override
    public void initObserver() {
        mViewModel.mLoginLivedata.observe(this, userInfoVo -> {
            if (userInfoVo != null) {
                JpushService jpushService = (JpushService) ARouter.getInstance().build(PUSH_SERVER).navigation();
                jpushService.setAlias(true);
            }
        });

        mViewModel.mThiredLoginLivedata.observe(this, s -> {
            RegistParamTransVo registParamTransVo = new RegistParamTransVo();
            registParamTransVo.wechatInfo = wechatInfo;
            switch (s) {
                case "wx":
                    registParamTransVo.bindType = "1";  //微信
                    break;
                case "qq":
                    registParamTransVo.bindType = "2";  //qq
                    break;
            }
            routerManager.Jump(new RouterParam.RouterBuilder(RouterConstKey.ACCOUNT_REGISTER).withParam(registParamTransVo).create());
        });
    }
}