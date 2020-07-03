package com.docker.common.model.form.input;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import androidx.databinding.BindingAdapter;

public class InputBindAdapter {
    @SuppressWarnings("unchecked")
    @BindingAdapter(value = {"input_options"}, requireAll = false)
    public static <T> void setAdapter(EditText input, InputFormApiOptions inputFormApiOptions) {

        if (inputFormApiOptions == null) {
            return;
        }

     /*   // edittext  文字颜色  文字大小      style
        //           校验  是否必填
        //           自动补全
        //           行数*/


       input.addTextChangedListener(new TextWatcher() {
           @Override
           public void beforeTextChanged(CharSequence s, int start, int count, int after) {

           }

           @Override
           public void onTextChanged(CharSequence s, int start, int before, int count) {

           }

           @Override
           public void afterTextChanged(Editable s) {

           }
       });


     /*
     *
    // 是否校验必填
    public boolean IsNeed;

    // 电话（phone） 邮箱(email)  数字(number)  身份证号码(card)
    public String inputType;

    // 最多几个文字
    public String MaxCount;

    // 最多几行
    public String MaxLines;

    // 是否显示当前行数
    public boolean IsShowLineNum;

    // 存储数据的key
    public String DataInKeys;

    // 自动补全的数据选项
    public HashMap<String, String> AutoCompleteData;


    // 自动补全网络数据请求 参数
    public HashMap<String, String> MapiParamOptions = new HashMap<>();
     *
     * */


    }
}
