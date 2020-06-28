package com.docker.country.ui;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;

import androidx.lifecycle.ViewModelProvider;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.blankj.utilcode.util.FragmentUtils;
import com.docker.common.ui.base.NitCommonActivity;
import com.docker.commonapi.anno.PagerPrivoderKeys;
import com.docker.commonapi.router.RouterConstKey;
import com.docker.commonapi.router.RouterManager;
import com.docker.country.R;
import com.docker.country.databinding.ComponentActivityCountrySelectBinding;
import com.docker.country.vm.CountrySelectViewModel;
import com.docker.country.vo.WorldNumList;

import dagger.hilt.android.AndroidEntryPoint;

import static com.docker.country.service.ComponentRouterConstantService.COMPONENTS_COUNTRY_INDEX;

@PagerPrivoderKeys(routerName = RouterConstKey.COMPONENTS_COUNTRY_INDEX, providerKeysArr = {}, providerObj = {WorldNumList.WorldEnty.class})
@Route(path = COMPONENTS_COUNTRY_INDEX)
@AndroidEntryPoint
public class CountrySelectActivity extends NitCommonActivity<CountrySelectViewModel, ComponentActivityCountrySelectBinding> {
    private CountryCoutainerFragment countryNumListFragment;
    private CountrySearchFragment countrySearchFragment;

    @Override
    protected int getLayoutId() {
        return R.layout.component_activity_country_select;
    }

    @Override
    public CountrySelectViewModel getmViewModel() {
        return new ViewModelProvider(this).get(CountrySelectViewModel.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void initView() {
        mToolbar.setTitle("城市选择");
        mBinding.editQuery.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (countryNumListFragment.getList() == null && countryNumListFragment.getList().size() == 0) {
                    return;
                }
                if (!TextUtils.isEmpty(mBinding.editQuery.getText().toString().trim()) && countryNumListFragment.getList().size() > 0) {
                    if (countrySearchFragment == null) {
                        countrySearchFragment = new CountrySearchFragment();
                        FragmentUtils.add(getSupportFragmentManager(), countrySearchFragment, R.id.frame, "CountrySearch");
                    } else {
                        FragmentUtils.show(FragmentUtils.findFragment(getSupportFragmentManager(), "CountrySearch"));
                    }
                    FragmentUtils.hide(FragmentUtils.findFragment(getSupportFragmentManager(), "countryNumList"));
                    countrySearchFragment.bindDatas(countryNumListFragment.getList(), countryNumListFragment.getCurrentPosition());
                    countrySearchFragment.bindQueryText(mBinding.editQuery.getText().toString().trim());
                } else {
                    if (countrySearchFragment != null) {
                        Boolean isVisibleToUser = countrySearchFragment.isResumed() && countrySearchFragment.getUserVisibleHint();
                        if (isVisibleToUser) {
                            FragmentUtils.hide(FragmentUtils.findFragment(getSupportFragmentManager(), "CountrySearch"));
                            FragmentUtils.show(FragmentUtils.findFragment(getSupportFragmentManager(), "countryNumList"));
                        }
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        countryNumListFragment = new CountryCoutainerFragment();
        FragmentUtils.add(getSupportFragmentManager(), countryNumListFragment, R.id.frame, "countryNumList");
        mBinding.tvCancel.setOnClickListener(v -> {
            finish();
        });
    }

    @Override
    public void initObserver() {

    }

    /*
     * 选择完成数据
     * */
    public void onHanleData(WorldNumList.WorldEnty worldEnty) {
        mPageProviderObjs.put("WorldNumList.WorldEnty", worldEnty);
        RouterManager.getInstance().processRouterTask(mDefaultParam, mPageProviderKeys, mPageProviderObjs);
        finish();
    }
}