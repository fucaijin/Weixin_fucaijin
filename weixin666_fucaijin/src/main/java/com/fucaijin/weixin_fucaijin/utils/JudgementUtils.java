package com.fucaijin.weixin_fucaijin.utils;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.text.TextUtils;
import android.widget.EditText;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

    /**
     * 此方法用来判断是否有网络，如果网络可用就返回true，否则返回false
     * @param activity 上下文环境，一般传入当前类名.this即可，例如MainActivity.this
     * @return 网络是否可用，可用则返回true，否则返回false
     */
    public static boolean isNetworkAvailable(Activity activity) {
        Context context = activity.getApplicationContext();
        // 获取手机所有连接管理对象（包括对wi-fi,net等连接的管理）
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager == null) {
            return false;
        } else {
            Network[] networks = connectivityManager.getAllNetworks();
            NetworkInfo networkInfo;
            for (Network mNetwork : networks) {
                networkInfo = connectivityManager.getNetworkInfo(mNetwork);
                if (networkInfo.getState().equals(NetworkInfo.State.CONNECTED)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * @param str 要判断的字符串
     * @return 返回字符串是否包含英文字母的结果
     */
    public static boolean containLetter(String str) {
        Pattern pattern = Pattern.compile("^[a-zA-Z]$");
        Matcher matcher = pattern.matcher(str);
        return matcher.find();
    }

    /**
     * 判断字符串是否是一串数字
     * @param str 要判断的字符串
     * @return 返回判断结果，如果是字符串就返回ture
     */
    public static boolean isNumeric(String str){
        Pattern pattern = Pattern.compile("[0-9]*");
        return pattern.matcher(str).matches();
    }
}
