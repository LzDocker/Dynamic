package com.docker.common.ui.base.block;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.docker.common.command.NitDelegetCommand;
import com.docker.common.config.Constant;
import com.docker.common.vm.base.NitCoutainerBaseFragmentV2;
import com.docker.common.model.list.CommonListOptions;
import com.docker.common.ui.base.NitCommonActivity;
import com.docker.common.ui.base.NitCommonFragment;
import com.docker.common.vm.base.NitCommonContainerViewModel;

/*
 * */
@Route(path = Constant.COMMON_SAMPLE_BLOCK_ITEM_LIST)
public class NitSampleBlockListFragment extends NitBaseBlockListFragment<NitCommonContainerViewModel> {

    private NitDelegetCommand delegetCommand;

    public static NitSampleBlockListFragment newinstance(CommonListOptions commonListReq) {
        NitSampleBlockListFragment nitCommonContainerFragment = new NitSampleBlockListFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(Constant.CommonListParam, commonListReq);
        nitCommonContainerFragment.setArguments(bundle);
        return nitCommonContainerFragment;
    }


    @Override
    protected NitCommonContainerViewModel getViewModel() {
        return ViewModelProviders.of(this, factory).get(NitCommonContainerViewModel.class);
    }

    @Override
    public CommonListOptions getArgument() {
        return null;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ARouter.getInstance().inject(this);
        //
        if (commonListReq.isActParent) {
            delegetCommand = ((NitCommonActivity) getHoldingActivity()).providerNitDelegetCommand(commonListReq.falg);
        } else {
            if (NitSampleBlockListFragment.this.getParentFragment() instanceof NitCoutainerBaseFragmentV2) {
                NitCoutainerBaseFragmentV2 mNitParent = (NitCoutainerBaseFragmentV2) NitSampleBlockListFragment.this.getParentFragment();
                if (mNitParent.getParentFragment() != null) {
                    delegetCommand = ((NitCommonFragment) (mNitParent).getParentFragment()).providerNitDelegetCommand(commonListReq.falg);
                }
            } else {
                delegetCommand = ((NitCommonFragment) (NitSampleBlockListFragment.this.getParentFragment())).providerNitDelegetCommand(commonListReq.falg);
            }
        }
        if (!TextUtils.isEmpty(commonListReq.mBlockVmPath)) {
            Class clazz = null;
            try {
                clazz = Class.forName(commonListReq.mBlockVmPath);
                mViewModel = (NitCommonContainerViewModel) ViewModelProviders.of(this, factory).get(clazz);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        } else {
            if (commonListReq.mBlockVm != null) {
                mViewModel = (NitCommonContainerViewModel) ViewModelProviders.of(this, factory).get(commonListReq.mBlockVm);
            } else {
                mViewModel = (NitCommonContainerViewModel) ViewModelProviders.of(this, factory).get(delegetCommand.providerOuterVm());
            }
        }
        this.getLifecycle().addObserver(mViewModel);
        mViewModel.mContainerLiveData.observe(this.getViewLifecycleOwner(), o -> {

        });
        mViewModel.initParam(commonListReq);
        mViewModel.initCommand();
        mBinding.get().setViewmodel(mViewModel);
        initRvUi();
        initListener();
        if (delegetCommand != null) {
            super.onVisible();
            delegetCommand.next(mViewModel, this);
        }
        if (NitSampleBlockListFragment.this.getParentFragment() instanceof NitCoutainerBaseFragmentV2) {
            ((NitCoutainerBaseFragmentV2) NitSampleBlockListFragment.this.getParentFragment()).onBlockVmCreated(mViewModel, commonListReq.frameItemblockId, this, commonListReq.falg);
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void initImmersionBar() {

    }

    @Override
    public void onVisible() {
        super.onVisible();
    }

}
