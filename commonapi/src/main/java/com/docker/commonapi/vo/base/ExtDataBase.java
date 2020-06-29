package com.docker.commonapi.vo.base;
import com.docker.common.model.BaseSampleItem;
import com.docker.common.model.OnItemClickListener;

import java.util.List;

public class ExtDataBase extends BaseSampleItem {


    // 0  默认
    // 1  section rv
    public int style = 0;

    public String uuid;
    public String updatetime;
    public String title;
    public String linkman;
    public String content;

    public List<DynamicResource> resource;

    public DynamicDataBase parent;

    @Override
    public int getItemLayout() {
        return 0;
    }

    @Override
    public OnItemClickListener getOnItemClickListener() {
        return parent.getOnItemClickListener();
    }

    public DynamicDataBase getParent() {
        return parent;
    }

    public void setParent(DynamicDataBase parent) {
        this.parent = parent;
    }
}
