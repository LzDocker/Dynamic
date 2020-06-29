package com.docker.common.vm.base;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.docker.common.R;
import com.docker.common.config.Constant;
import com.docker.common.databinding.CommonCoutainerConsecutiveFragmentV2Binding;
import com.docker.common.model.page.PageOptions;
import com.docker.common.model.page.PageParser;
import com.docker.common.ui.base.NitCommonFragment;
import com.docker.common.ui.base.block.NitSampleBlockListFragment;

/*
 *
 *  通过 后端下发的api 解析card block  然后展示
 *
 *  */
@Route(path = Constant.mCOMMON_COUTAINER_PATH)
public class NitCoutainerBaseFragmentV2 extends NitCommonFragment<NitcommonCardViewModel, CommonCoutainerConsecutiveFragmentV2Binding> {

    public String mPageID;

    private PageParser pageParser;

    public PageParser getPageParser() {
        return pageParser;
    }

    public void setPageParser(PageParser pageParser) {
        this.pageParser = pageParser;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.common_coutainer_consecutive_fragment_v2;
    }

    @Override
    protected NitcommonCardViewModel getViewModel() {
        return new ViewModelProvider(this).get(NitcommonCardViewModel.class);
    }

    @Override
    protected void initView(View var1) {

        mBinding.get().fullscroll.setOnVerticalScrollChangeListener((v, scrollY, oldScrollY) -> {
//            Log.d("sss", "initView: ================NitCoutainerBaseFragmentV2===============" + scrollY);
        });
    }

    @Override
    public void initImmersionBar() {
//        ImmersionBar.with(this)
//                .fullScreen(true)
//                .init();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
//        setPageParser(new PagerParserImpl());
        mPageID = getPageParser().getPageID();
        PageOptions pageOptions = (PageOptions) getArguments().getSerializable("PageOptions");

        if (pageOptions == null) {
            String name = getArguments().getString("baseSampleItem");
            pageParser.processTestPager(name, this);
        } else {
            pageParser.processPager(pageOptions, this);
        }
        super.onActivityCreated(savedInstanceState);
    }

    public void onBlockVmCreated(NitCommonContainerViewModel nitCommonContainerViewModel, String frameitemblockid, NitSampleBlockListFragment nitSampleBlockList, int flag) {
        pageParser.onBlockVmCreated(nitCommonContainerViewModel, frameitemblockid, nitSampleBlockList, flag);
    }


    @Override
    public void processSysMessage(String key, Object value) {
        super.processSysMessage(key, value);
        pageParser.processSysMessage(key, value);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        pageParser.recycle();
    }
}
