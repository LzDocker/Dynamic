package com.docker.account.ui;

import android.os.CountDownTimer;
import android.text.TextUtils;
import android.util.Log;

import androidx.lifecycle.ViewModelProvider;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.blankj.utilcode.util.EncryptUtils;
import com.blankj.utilcode.util.ReflectUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.docker.account.R;
import com.docker.account.databinding.AccountActivityResetPwdBinding;
import com.docker.account.vm.AccountViewModel;
import com.docker.common.config.Constant;
import com.docker.common.ui.base.NitCommonActivity;
import com.docker.common.util.CacheUtils;
import com.docker.common.vo.UserInfoVo;
import com.docker.commonapi.anno.PagerPrivoderKeys;
import com.docker.commonapi.router.RouterConstKey;
import com.docker.commonapi.router.RouterManager;
import com.docker.commonapi.router.RouterParam;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

import static com.docker.account.service.AccountRouterConstantService.ACCOUNT_RESET;

@PagerPrivoderKeys(routerName = RouterConstKey.ACCOUNT_RESET, providerKeysArr = {"userName", "pwdnomal","pwd", "areacode"}, providerObj = {UserInfoVo.class})
@Route(path = ACCOUNT_RESET)
@AndroidEntryPoint
public class ResetPwdActivity extends NitCommonActivity<AccountViewModel, AccountActivityResetPwdBinding> {
    @Inject
    RouterManager routerManager;

    private String title;
    private String area_code = "+86";
    private CountDownTimer timer;


    @Override
    protected int getLayoutId() {
        return R.layout.account_activity_reset_pwd;
    }

    @Override
    public AccountViewModel getmViewModel() {
        return new ViewModelProvider(this).get(AccountViewModel.class);
    }

    @Override
    public void initView() {

        title = getIntent().getStringExtra(Constant.ParamTrans);


        for (Map.Entry<String, String> entry : mPageProviderKeys.entrySet()) {
            String mapKey = entry.getKey();
            String mapValue = entry.getValue();
            Log.d("TAG", mRouterName + "========" + mapKey + ":" + mapValue);
        }

        if (!TextUtils.isEmpty(title)) {
            mToolbar.setTitle(title);
        }
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

        // 重置
        mBinding.accountSubmint.setOnClickListener(v -> {
            if (checkInput()) {
                HashMap<String, String> param = new HashMap<>();
                param.put("mobile", mBinding.edUsername.getText().toString().trim());
                param.put("smsCode", mBinding.edSmsCode.getText().toString().trim());
                param.put("newpsw", EncryptUtils.encryptMD5ToString(mBinding.edPassword.getText().toString().trim()));
                mViewModel.ResetPwd(param);
            }
        });

        // 选择区域
        mBinding.rlNumSelect.setOnClickListener(v -> {
            routerManager.Jump(new RouterParam.RouterBuilder(RouterConstKey.COMPONENTS_COUNTRY_INDEX).withFrom(RouterConstKey.ACCOUNT_RESET).withmRosponseCommand((o, o2) -> {
                area_code = routerManager.getReturnResult((HashMap<String, Object>) o2, "WorldNumList.WorldEnty", "global_num");
                mBinding.tvNum.setText(area_code);
                return null;
            }).create());

        });
    }

    @Override
    public void initObserver() {

        // 重置
        mViewModel.mResetLivedata.observe(this, s -> {
            ToastUtils.showShort("密码重置成功");
            mPageProviderKeys.put(("userName"), mBinding.edUsername.getText().toString().trim());
            mPageProviderKeys.put(("pwd"), EncryptUtils.encryptMD5ToString(mBinding.edPassword.getText().toString()));
            mPageProviderKeys.put(("pwdnomal"), mBinding.edPassword.getText().toString());
            mPageProviderKeys.put(("areacode"), area_code);
            mPageProviderObjs.put("UserInfoVo", CacheUtils.getUser());
            routerManager.processRouterTask(mDefaultParam, mPageProviderKeys,mPageProviderObjs);
            finish();
        });

        // smscode
        mViewModel.mSmsLivedata.observe(this, rstVo -> {
            verfi();
        });
    }

    private boolean checkInput() {
        if (TextUtils.isEmpty(mBinding.edUsername.getText().toString().trim())) {
            ToastUtils.showShort("用户名不能为空");
            return false;
        }
        if (TextUtils.isEmpty(mBinding.edPassword.getText().toString().trim())) {
            ToastUtils.showShort("密码不能为空");
            return false;
        }
        if (TextUtils.isEmpty(mBinding.edSmsCode.getText().toString().trim())) {
            ToastUtils.showShort("验证码不能为空");
            return false;
        }
        return true;
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