package com.docker.country.ui;

import android.content.Intent;
import android.view.View;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.docker.common.adapter.CommonpagerAdapter;
import com.docker.common.databinding.CommonTabViewpageBinding;
import com.docker.common.ui.base.NitCommonFragment;
import com.docker.country.R;
import com.docker.country.vm.CountrySelectViewModel;
import com.docker.country.vo.WorldNumList;
import com.docker.design.indexctor.CommonIndector;

import java.util.ArrayList;
import java.util.List;

import dagger.hilt.android.AndroidEntryPoint;

/**
 *
 */
@AndroidEntryPoint
public class CountryCoutainerFragment extends NitCommonFragment<CountrySelectViewModel, CommonTabViewpageBinding> {

    CountryNumListFragment accountCountryNumListFragment1;
    CountryNumListFragment accountCountryNumListFragment2;
    ArrayList<Fragment> fragments = new ArrayList<>();

    @Override
    protected int getLayoutId() {
        return R.layout.common_tab_viewpage;
    }

    @Override
    public CountrySelectViewModel getViewModel() {
        return new ViewModelProvider(this).get(CountrySelectViewModel.class);
    }

    @Override
    protected void initView(View var1) {
        accountCountryNumListFragment1 = CountryNumListFragment.newinstance("1");
        accountCountryNumListFragment2 = CountryNumListFragment.newinstance("2");

        fragments.add(accountCountryNumListFragment1);
        fragments.add(accountCountryNumListFragment2);
        String[] title = new String[]{"国内(含港澳台)", "国际"};
        mBinding.get().viewpager.setAdapter(new CommonpagerAdapter(getChildFragmentManager(), fragments, title));

        CommonIndector commonIndector = new CommonIndector();
        commonIndector.initMagicIndicator(title, mBinding.get().viewpager, mBinding.get().magic, this.getActivity());

    }

    public List<WorldNumList.WorldEnty> getList() {
        return ((CountryNumListFragment) fragments.get(mBinding.get().viewpager.getCurrentItem())).list;
    }

    public int getCurrentPosition() {
        return mBinding.get().viewpager.getCurrentItem();
    }

    public void processLocation(WorldNumList.WorldEnty worldEnty, String type) {
        boolean isFind = false;
        if (worldEnty != null && accountCountryNumListFragment1.list != null) {
            for (int i = 0; i < accountCountryNumListFragment1.list.size(); i++) {
                if (worldEnty.name.equals(accountCountryNumListFragment1.list.get(i).name)) {
                    Intent intent = new Intent();
                    WorldNumList.WorldEnty curwo = accountCountryNumListFragment1.list.get(i);
                    curwo.curtype = String.valueOf(getCurrentPosition());
                  /*  intent.putExtra("data", curwo.id);
                    intent.putExtra("num", curwo.global_num);
                    intent.putExtra("name", curwo.name);
                    intent.putExtra("datasource", curwo);
                    getHoldingActivity().setResult(Activity.RESULT_OK, intent);
                    getHoldingActivity().finish();*/

                    ((CountrySelectActivity) getActivity()).onHanleData(curwo);
                    isFind = true;
                    break;
                }
            }
        }
        if (worldEnty != null && accountCountryNumListFragment2.list != null && !isFind) {
            for (int i = 0; i < accountCountryNumListFragment2.list.size(); i++) {
                if (worldEnty.name.equals(accountCountryNumListFragment2.list.get(i).name)) {
                    Intent intent = new Intent();
                    WorldNumList.WorldEnty curwo = accountCountryNumListFragment2.list.get(i);
                    curwo.curtype = String.valueOf(getCurrentPosition());
                    /*intent.putExtra("data", curwo.id);
                    intent.putExtra("num", curwo.global_num);
                    intent.putExtra("name", curwo.name);
                    intent.putExtra("datasource", curwo);
                    getHoldingActivity().setResult(Activity.RESULT_OK, intent);
                    getHoldingActivity().finish();*/
                    ((CountrySelectActivity) getActivity()).onHanleData(curwo);
                    isFind = true;
                    break;
                }
            }
        }

    }

    @Override
    public void initImmersionBar() {

    }
}
