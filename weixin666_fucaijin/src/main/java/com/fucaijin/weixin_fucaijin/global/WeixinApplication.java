package com.fucaijin.weixin_fucaijin.global;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Handler;

import com.fucaijin.weixin_fucaijin.R;
import com.fucaijin.weixin_fucaijin.data.AddressListItemData;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fucaijin on 2018/5/1.
 */

public class WeixinApplication extends Application {

    //    Sign up   注册
    //    Sign in   登录
    //    Sign out  退出

    public static Context mContext;
    private static Handler mHandler;
    private static int mainThreadId;
    private static boolean signIn = true;       //设定默认是已登录状态
    private static boolean firstRun = false;    //设定默认不是第一次登录
    private static SharedPreferences user;

//    模拟的用户信息（头像和昵称）
    public static List<AddressListItemData> mAddressListItem = new ArrayList<>();

//    官方的头像和昵称
    public static List<AddressListItemData> mAddressListOfficialItem = new ArrayList<>();

//    模拟的用户头像
    private int[] headSculptureList = {R.drawable.head_sculpture_1, R.drawable.head_sculpture_2,
            R.drawable.head_sculpture_3, R.drawable.head_sculpture_4,
            R.drawable.head_sculpture_5, R.drawable.head_sculpture_6,
            R.drawable.head_sculpture_7, R.drawable.head_sculpture_8,
            R.drawable.head_sculpture_9, R.drawable.head_sculpture_10,
            R.drawable.head_sculpture_11, R.drawable.head_sculpture_12,
            R.drawable.head_sculpture_13, R.drawable.head_sculpture_14,
            R.drawable.head_sculpture_15, R.drawable.head_sculpture_16,
            R.drawable.head_sculpture_17, R.drawable.head_sculpture_18,
            R.drawable.head_sculpture_19, R.drawable.head_sculpture_20,
            R.drawable.head_sculpture_21, R.drawable.head_sculpture_22,
            R.drawable.head_sculpture_23, R.drawable.head_sculpture_24,
            R.drawable.head_sculpture_25, R.drawable.head_sculpture_26,
            R.drawable.head_sculpture_27, R.drawable.head_sculpture_28,
            R.drawable.head_sculpture_29, R.drawable.head_sculpture_30,
            R.drawable.head_sculpture_31, R.drawable.head_sculpture_32,
            R.drawable.head_sculpture_33, R.drawable.head_sculpture_34,
            R.drawable.head_sculpture_35,
    };

//    模拟的用户昵称
    private String[] nickNameList = {"HelloKitty", "阿拉蕾", "白娘子", "白头发少年", "白头发鸣人",
            "白雪公主", "超级玛红", "超级玛绿", "大白鹅", "丁丁",
            "猥琐海盗", "黑猫警长", "葫芦娃", "加肥猫", "胖小弟",
            "不平易近人的警察", "啃德鸡老爷爷", "我是小新", "灌篮小胖子", "雷神",
            "大龙猫", "路灰", "满头绿", "卖兜", "超级无敌美少女",
            "真的鸣人", "派派派派派大星", "瞧吧", "圣诞老爷爷", "不认识这是谁",
            "无脸人", "绿了全身的猪", "小熊维尼", "可爱的小樱桃", "猥琐小胖子", };

//    模拟的官方头像（通讯录顶部的四个：新朋友、群聊、标签、公众号）
    private int[] OfficialHeadSculptureList = {R.drawable.address_list_new_friends, R.drawable.address_list_group_chat,
            R.drawable.address_list_tags, R.drawable.address_list_public_account};

    //    模拟的用户昵称
    private String[] OfficialNickNameList = {"新朋友", "群聊", "标签", "公众号"};

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();
        mHandler = new Handler();
        mainThreadId = android.os.Process.myTid();
        user = mContext.getSharedPreferences("user", MODE_PRIVATE);
        LoginStatus();
        initData();
    }

    private void initData() {
//        封装官方的头像和昵称
        if(OfficialHeadSculptureList.length == OfficialNickNameList.length){
            for (int i = 0 ; i < OfficialNickNameList.length ; i++){
                AddressListItemData addressListItemData = new AddressListItemData();
                addressListItemData.setHeadSculpture(OfficialHeadSculptureList[i]);
                addressListItemData.setNickName(OfficialNickNameList[i]);
                mAddressListOfficialItem.add(addressListItemData);
            }
        }

//        把通讯录的条目数据封装到addressListItemData里面，然后再把所有条目信息装到mAddressListItem
        if(nickNameList.length == headSculptureList.length){
            for (int i = 0 ; i < nickNameList.length ; i++){
                AddressListItemData addressListItemData = new AddressListItemData();
                addressListItemData.setHeadSculpture(headSculptureList[i]);
                addressListItemData.setNickName(nickNameList[i]);
                mAddressListItem.add(addressListItemData);
            }
        }
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

//    把mContext改成了public static 因此就不需要此方法了
//    public static Context getmContext() {
//        return mContext;
//    }

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
