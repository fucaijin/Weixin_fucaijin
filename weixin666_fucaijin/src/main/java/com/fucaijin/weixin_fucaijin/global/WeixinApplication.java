package com.fucaijin.weixin_fucaijin.global;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Handler;

/**
 * Created by fucaijin on 2018/5/1.
 */

public class WeixinApplication extends Application {

    //    Sign up   注册
    //    Sign in   登录
    //    Sign out  退出

    private static Context mContext;
    private static Handler mHandler;
    private static int mainThreadId;
    private static boolean signIn = true;       //设定默认是已登录状态
    private static boolean firstRun = false;    //设定默认不是第一次登录
    private static SharedPreferences user;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();
        mHandler = new Handler();
        mainThreadId = android.os.Process.myTid();
        user = mContext.getSharedPreferences("user", MODE_PRIVATE);
        LoginStatus();
    }

    private void LoginStatus() {
        //获取账号、登录状态、首次使用(未登录过)
        String account = user.getString("account", "");
        String login = user.getString("login", "");
        String first_run = user.getString("first_run", "");

        if(first_run.equals("")){
//            判断是否是首次运行
            firstRun = true;
            signIn = false;
        }else if(login.equals("false")){
//            判断是否是注销状态
            firstRun = false;
            signIn = false;
        }else {
//            如果不是首次运行，也不是注销状态，那就是登录状态
            firstRun = false;
            signIn = true;
        }
    }

    public static Context getmContext() {
        return mContext;
    }

    public static Handler getmHandler() {
        return mHandler;
    }

    public static int getMainThreadId() {
        return mainThreadId;
    }

    public static boolean isSignIn() {
        return signIn;
    }

    public static boolean isFirstRun() {
        return firstRun;
    }

    /**
     * @param key 要查询配置的条目
     * @return 返回查询的结果
     */
    public static String getConfig(String key) {
        return user.getString(key, "");
    }

    /**
     * @param key 要存储到配置文件中的项目
     * @param value 要存储到配置文件中的值
     * @return 返回是否存储成功的结果，如果不需要结果，可以改用apply()代替commit(),前者是异步，不占用主线程资源
     */
    public static boolean setConfig(String key,String value) {
        return user.edit().putString(key,value).commit();
    }
}
