package com.fucaijin.weixin_fucaijin.utils;

import android.text.TextUtils;
import android.widget.EditText;

/**
 * 这是一个判断工具类，用来判断输入框是否为空等功能
 * Created by fucaijin on 2018/5/12.
 */

public class JudgementUtils {

    /**
     * @param et 要判断的EditText对象
     * @return 返回判断输入框对象是否为空
     */
    public static boolean editTextEmpty(EditText et){
        return TextUtils.isEmpty(et.getText().toString().trim());
    }

}
