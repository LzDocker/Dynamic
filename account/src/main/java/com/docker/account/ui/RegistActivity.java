package com.docker.account.ui;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.TextUtils;

import androidx.lifecycle.ViewModelProvider;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.blankj.utilcode.util.EncryptUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.docker.account.R;
import com.docker.account.databinding.AccountActivityRegistBinding;
import com.docker.account.vm.AccountViewModel;
import com.docker.account.vo.RegistParamTransVo;
import com.docker.account.vo.RegistVo;
import com.docker.common.config.Constant;
import com.docker.common.ui.base.NitCommonActivity;
import com.docker.commonapi.anno.PagerPrivoderKeys;
import com.docker.commonapi.router.RouterConstKey;
import com.docker.commonapi.router.RouterManager;
import com.docker.commonapi.router.RouterParam;
import com.docker.commonapi.service.JpushService;

import java.util.HashMap;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

import static com.docker.account.service.AccountRouterConstantService.ACCOUNT_REGISTER;

@PagerPrivoderKeys(routerName = RouterConstKey.ACCOUNT_REGISTER, providerKeysArr = {"userName", "pwd", "areacode"}, providerObj = {RegistVo.class})
@Route(path = ACCOUNT_REGISTER)
@AndroidEntryPoint
public class RegistActivity extends NitCommonActivity<AccountViewModel, AccountActivityRegistBinding> {

    private String area_code = "+86";

    @Inject
    RouterManager routerManager;

    private String isCheckbox = "";

    private CountDownTimer timer;

    private RegistParamTransVo registParamTransVo;

    @Autowired
    JpushService jpushService;

    @Override
    protected int getLayoutId() {
        return R.layout.account_activity_regist;
    }

    @Override
    public AccountViewModel getmViewModel() {
        return new ViewModelProvider(this).get(AccountViewModel.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    private boolean checkInput() {
        if (TextUtils.isEmpty(mBinding.edUsername.getText().toString().trim())) {
            ToastUtils.showShort("手机号不能为空");
            return false;
        }
        if (TextUtils.isEmpty(mBinding.edSmsCode.getText().toString().trim())) {
            ToastUtils.showShort("验证码不能为空");
            return false;
        }
        if (TextUtils.isEmpty(mBinding.edPassword.getText().toString().trim())) {
            ToastUtils.showShort("密码不能为空");
            return false;
        }
        return true;
    }


    @Override
    public void initView() {
        mToolbar.setNavigation(false);
        registParamTransVo = (RegistParamTransVo) getIntent().getSerializableExtra(Constant.ParamTrans);
        if (registParamTransVo == null) {
            mToolbar.setTitle("注册");
        } else {
            mToolbar.setTitle("绑定手机号");
            mBinding.accountRegistNext.setText("绑定");
        }

        mBinding.accountRegistNext.setOnClickListener(v -> {
            if (TextUtils.isEmpty(isCheckbox)) {
                ToastUtils.showShort("请选择同意用户协议");
                return;
            }
            if (checkInput()) {

                mPageProviderKeys.put("userName", mBinding.edUsername.getText().toString().trim());
                mPageProviderKeys.put("pwd", mBinding.edPassword.getText().toString().trim());
                mPageProviderKeys.put("areacode", area_code);

                HashMap<String, String> param = new HashMap<>();
                param.put("mobile", mBinding.edUsername.getText().toString().trim());
                param.put("username", mBinding.edUsername.getText().toString().trim());
                param.put("smsCode", mBinding.edSmsCode.getText().toString().trim());
                param.put("password", mBinding.edPassword.getText().toString().trim());
                param.put("area_code", area_code);
                mViewModel.Register(param, registParamTransVo);
            }
        });

        mBinding.linCheckbox.setOnClickListener(v -> {
            if (mBinding.checkboxAgreement.isChecked()) {
                mBinding.checkboxAgreement.setChecked(false);
                isCheckbox = "1";
            } else {
                isCheckbox = "";
                mBinding.checkboxAgreement.setChecked(true);
            }
        });

        // 发送信息
        mBinding.tvSendCode.setOnClickListener(v -> {
            if (TextUtils.isEmpty(mBinding.edUsername.getText().toString())) {
                ToastUtils.showShort("手机号不能为空！");
                return;
            }
            HashMap<String, String> param = new HashMap<>();
            long aa = System.currentTimeMillis() / 1000;
            String timestamp = String.valueOf(aa);
            String sign = EncryptUtils.encryptMD5ToString(mBinding.edUsername.getText().toString().trim() + "_" + timestamp);
            param.put("area_code", area_code);
            param.put("mobile", mBinding.edUsername.getText().toString().trim());
            param.put("timestamp", timestamp);
            param.put("sign", sign);
            mViewModel.SendSmsCode(param);
        });

        // 去登录
        mBinding.accountToLogin.setOnClickListener(v -> {
            finish();
        });

        // 选择
        mBinding.rlNumSelect.setOnClickListener(v -> {
            routerManager.Jump(new RouterParam.RouterBuilder(RouterConstKey.COMPONENTS_COUNTRY_INDEX).withFrom(RouterConstKey.ACCOUNT_LOGIN).withmRosponseCommand((o, o2) -> {
                area_code = routerManager.getReturnResult((HashMap<String, Object>) o2, "WorldNumList.WorldEnty", "global_num");
                mBinding.tvNum.setText(area_code);
                return null;
            }).create());
        });

        mBinding.accountToUse.setOnClickListener(v -> {
            routerManager.Jump(new RouterParam.RouterBuilder(RouterConstKey.COMPONENTS_COUNTRY_INDEX).withParam("使用协议").create());
        });
        mBinding.accountToPrivate.setOnClickListener(v -> {
            routerManager.Jump(new RouterParam.RouterBuilder(RouterConstKey.COMPONENTS_COUNTRY_INDEX).withParam("隐私协议").create());
        });
    }

    @Override
    public void initObserver() {

        mViewModel.mSmsLivedata.observe(this, rstVo -> {
            verfi();
        });

        mViewModel.mRegLivedata.observe(this, registVo -> {
            if (registVo != null) {
                mPageProviderObjs.put("RegistVo", registVo);
                ToastUtils.showShort("注册成功");
                jpushService.setAlias(true);
            }
        });
    }

    private void verfi() {
        timer = new CountDownTimer(30000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                mBinding.tvSendCode.setEnabled(false);
                mBinding.tvSendCode.setText("已发送(" + millisUntilFinished / 1000 + ")");
            }

            @Override
            public void onFinish() {
                mBinding.tvSendCode.setEnabled(true);
                mBinding.tvSendCode.setText("重新获取验证码");
            }
        }.start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (timer != null) {
            timer.cancel();
        }
    }
}