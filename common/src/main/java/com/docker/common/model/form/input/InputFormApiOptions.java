package com.docker.common.model.form.input;

import com.docker.common.model.apiconfig.FormApiOptions;
import com.docker.common.model.form.basic.edit.EditFormApioptions;
import com.docker.common.model.form.basic.img.ImgFormApiOptions;
import com.docker.common.model.form.basic.lable.LableFormApioptions;

public class InputFormApiOptions extends FormApiOptions {

    // input_left_img  isshow  imgurl
    // inputl_able     isshow  name
    // input_editor    isenable  inputtype  maxcount  maxlines  autocompletedata
    // input_arrow_right_img  isshow  imgurl
    // input_right_msg_img  ishow  num  bindapp bindkey

    // jumptype  routerurl  param


    public ImgFormApiOptions input_left_img;

    public LableFormApioptions inputl_lable;

    public EditFormApioptions input_editor;

    public ImgFormApiOptions input_arrow_right_img;

    public ImgFormApiOptions input_right_msg_img;

    public int Jumptype ;

    public String routerurl;

    public String routerparam;

}
