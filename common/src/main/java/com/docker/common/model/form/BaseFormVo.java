package com.docker.common.model.form;

import com.docker.common.model.BaseSampleItem;
import com.docker.common.model.apiconfig.FormApiOptions;

public abstract class BaseFormVo extends BaseSampleItem {

    public FormApiOptions mFormApiOptions;

    public abstract Object getFormVlaue();

}
