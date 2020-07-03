package com.docker.common.model.form.input;

import com.docker.common.R;
import com.docker.common.model.apiconfig.FormApiOptions;
import com.docker.common.model.form.BaseFormVo;
import com.docker.common.model.form.FormMarkService;
import com.google.auto.service.AutoService;

@AutoService(FormMarkService.class)
public class InputFormVo extends BaseFormVo implements FormMarkService {

    @Override
    public int getItemLayout() {
        return R.layout.common_form_input;
    }

    @Override
    public Object getFormVlaue() {
        return mFormApiOptions.FormDataKey;
    }

    @Override
    public BaseFormVo BindApiOptions(FormApiOptions formApiOptions) {
        this.mFormApiOptions = formApiOptions;
        return this;
    }

}
