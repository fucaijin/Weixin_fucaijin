package com.fucaijin.weixin_fucaijin.global;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Handler;

import com.fucaijin.weixin_fucaijin.R;
import com.fucaijin.weixin_fucaijin.data.AddressListItemData;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by fucaijin on 2018/5/1.
 */

public class WeixinApplication extends Application {

    public static final int TENCENT_IM_SDK_APP_ID = 1400116236;
    public static Context mContext;
    private static Handler mHandler;
    private static int mainThreadId;
    private static boolean isLogined = true;       //设定默认是已登录状态
    private static boolean isFirstRun = false;    //设定默认不是第一次登录
    private static SharedPreferences user;
    private int MyTIMSdkAppId = 1400116435;        //我的腾讯im sdk AppId

    //    模拟的用户信息（头像和昵称）
    public static List<AddressListItemData> mAddressListItem = new ArrayList<>();

    //    官方的头像和昵称
    public static List<AddressListItemData> mAddressListOfficialItem = new ArrayList<>();

    public static int mAddressListOfficialItemSize = 0;

    //    模拟的用户头像
    private int[] headSculptureList;
//    = {
//        R.drawable.head_sculpture_1, R.drawable.head_sculpture_2,
//        R.drawable.head_sculpture_3, R.drawable.head_sculpture_4,
//        R.drawable.head_sculpture_5, R.drawable.head_sculpture_6,
//        R.drawable.head_sculpture_7, R.drawable.head_sculpture_8,
//        R.drawable.head_sculpture_9, R.drawable.head_sculpture_10,
//        R.drawable.head_sculpture_11, R.drawable.head_sculpture_12,
//        R.drawable.head_sculpture_13, R.drawable.head_sculpture_14,
//        R.drawable.head_sculpture_15, R.drawable.head_sculpture_16,
//        R.drawable.head_sculpture_17, R.drawable.head_sculpture_18,
//        R.drawable.head_sculpture_19, R.drawable.head_sculpture_20,
//        R.drawable.head_sculpture_21, R.drawable.head_sculpture_22,
//        R.drawable.head_sculpture_23, R.drawable.head_sculpture_24,
//        R.drawable.head_sculpture_25, R.drawable.head_sculpture_26,
//        R.drawable.head_sculpture_27, R.drawable.head_sculpture_28,
//        R.drawable.head_sculpture_29, R.drawable.head_sculpture_30,
//        R.drawable.head_sculpture_31, R.drawable.head_sculpture_32,
//        R.drawable.head_sculpture_33, R.drawable.head_sculpture_34,
//        R.drawable.head_sculpture_35,
//    };

    //    模拟的用户昵称
    private String[] nickNameList;
//    = {
//        "HelloKitty", "阿拉蕾",
//        "白娘子", "白头发少年",
//        "白头发鸣人", "白雪公主",
//        "超级玛红", "超级玛绿",
//        "大白鹅", "丁丁",
//        "猥琐海盗", "黑猫警长",
//        "葫芦娃", "加肥猫",
//        "胖小弟", "不平易近人的警察",
//        "啃德鸡老爷爷", "我是小新",
//        "灌篮小胖子", "雷神",
//        "大龙猫", "路灰",
//        "满头绿", "卖兜",
//        "超级无敌美少女", "真的鸣人",
//        "派派派派派大星", "瞧吧",
//        "圣诞老爷爷", "不认识这是谁",
//        "无脸人", "绿了全身的猪",
//        "小熊维尼", "可爱的小樱桃",
//        "猥琐小胖子"
//    };

    //    模拟的最后一条消息
    public static String[] lastMessageList;
//    = {
//        "[文件]", "一会儿去车行弄一下导航",
//        "好", "[链接]",
//        "[ok]", "[语音]",
//        "这么晚", "嗯，好",
//        "[语音]", "丁丁领取了你的红包",
//        "哈喽，朋友！本人下个月1号更改电话号码为173228", "[哦]",
//        "好的", "[链接]",
//        "骗人", "好的,谢谢~",
//        "[图片]", "一个就不错了",
//        "你也是", "[语音通话]",
//        "不在哦", "赶快识别，真能领到",
//        "赶紧领，是真的", "谢谢啦，等着你换",
//        "[图片]", "[语音通话]",
//        "你领取了派派派派派大星的红包", "收到",
//        "好的老大", "赶快回来",
//        "纳尼？你等一下哈", "那我找一找就换上",
//        "没问题", "可爱的小樱桃领取了你的红包",
//        "猥琐小胖子领取了你的红包"
//    };

    //    模拟的最后一条消息的时间
    public static String[] lastMessageTimeList;
//    = {
//            "16:46", "16:46",
//            "16:46", "16:46",
//            "16:46", "16:46",
//            "16:46", "16:46",
//            "16:46", "16:46",
//            "16:46", "16:46",
//            "16:46", "16:46",
//            "16:46", "16:46",
//            "16:46", "16:46",
//            "16:46", "16:46",
//            "16:46", "16:46",
//            "16:46", "16:46",
//            "16:46", "16:46",
//            "16:46", "16:46",
//            "16:46", "16:46",
//            "16:46", "16:46",
//            "16:46", "16:46",
//            "16:46"
//    };

    //    模拟的官方头像（通讯录顶部的四个：新朋友、群聊、标签、公众号）
    private int[] OfficialHeadSculptureList = {R.drawable.address_list_new_friends, R.drawable.address_list_group_chat,
            R.drawable.address_list_tags, R.drawable.address_list_public_account};

    //    模拟的用户昵称
    private String[] OfficialNickNameList = {"新朋友", "群聊", "标签", "公众号"};

//    public static final String HTTP_HOST_URL = "http://192.168.1.105:8000/";//请求网络主机
    public static final String HTTP_HOST_URL = "http://www.fucaijin.cn/";//请求网络主机

    @Override
    public void onCreate() {
        super.onCreate();
//        initTIMSdk();
        initVirtualData();
        mContext = getApplicationContext();
        mHandler = new Handler();
        mainThreadId = android.os.Process.myTid();//获取主线程Id
        user = mContext.getSharedPreferences("user", MODE_PRIVATE);
        queryLoginStatus();
        initData();
    }

    /**
     * 初始化虚拟数据
     */
    private void initVirtualData() {
        //    模拟的用户头像
        headSculptureList = new int[]{
                R.drawable.head_sculpture_1, R.drawable.head_sculpture_2,
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
        nickNameList = new String[]{
                "HelloKitty", "阿拉蕾",
                "白娘子", "白头发少年",
                "白头发鸣人", "白雪公主",
                "超级玛红", "超级玛绿",
                "大白鹅", "丁丁",
                "猥琐海盗", "黑猫警长",
                "葫芦娃", "加肥猫",
                "胖小弟", "不平易近人的警察",
                "啃德鸡老爷爷", "我是小新",
                "灌篮小胖子", "雷神",
                "大龙猫", "路灰",
                "满头绿", "卖兜",
                "超级无敌美少女", "真的鸣人",
                "派派派派派大星", "瞧吧",
                "圣诞老爷爷", "不认识这是谁",
                "无脸人", "绿了全身的猪",
                "小熊维尼", "可爱的小樱桃",
                "猥琐小胖子"
        };

//    模拟的最后一条消息
        lastMessageList = new String[]{
                "[文件]", "一会儿去车行弄一下导航",
                "好", "[链接]",
                "[ok]", "[语音]",
                "这么晚", "嗯，好",
                "[语音]", "丁丁领取了你的红包",
                "哈喽，朋友！本人下个月1号更改电话号码为173228", "[哦]",
                "好的", "[链接]",
                "骗人", "好的,谢谢~",
                "[图片]", "一个就不错了",
                "你也是", "[语音通话]",
                "不在哦", "赶快识别，真能领到",
                "赶紧领，是真的", "谢谢啦，等着你换",
                "[图片]", "[语音通话]",
                "你领取了派派派派派大星的红包", "收到",
                "好的老大", "赶快回来",
                "纳尼？你等一下哈", "那我找一找就换上",
                "没问题", "可爱的小樱桃领取了你的红包",
                "猥琐小胖子领取了你的红包"
        };

        //    模拟的最后一条消息的时间
        lastMessageTimeList = new String[]{
                "16:46", "16:45",
                "16:44", "16:43",
                "16:42", "16:41",
                "16:40", "16:39",
                "16:38", "16:37",
                "16:36", "16:35",
                "16:34", "16:33",
                "16:32", "16:31",
                "16:30", "16:29",
                "16:28", "16:27",
                "16:26", "16:25",
                "16:24", "16:23",
                "16:22", "16:21",
                "16:20", "16:19",
                "16:19", "16:17",
                "16:16", "16:15",
                "16:14", "16:13",
                "16:12"
        };
    }

    private void initData() {
//        封装官方的头像和昵称
        if (OfficialHeadSculptureList.length == OfficialNickNameList.length) {
            for (int i = 0; i < OfficialNickNameList.length; i++) {
                AddressListItemData addressListItemData = new AddressListItemData();
                addressListItemData.setHeadSculpture(OfficialHeadSculptureList[i]);
                addressListItemData.setNickName(OfficialNickNameList[i]);
                mAddressListOfficialItem.add(addressListItemData);
            }

            mAddressListOfficialItemSize = mAddressListOfficialItem.size();
        }

        Random random = new Random();//随机数，用于生成随机数来生成随机的性别
//        把通讯录的条目数据封装到addressListItemData里面，然后再把所有条目信息装到mAddressListItem
        if (nickNameList.length == headSculptureList.length) {
            for (int i = 0; i < nickNameList.length; i++) {
                AddressListItemData addressListItemData = new AddressListItemData();
                addressListItemData.setHeadSculpture(headSculptureList[i]);
                addressListItemData.setNickName(nickNameList[i]);
                addressListItemData.setMan(random.nextBoolean());
                mAddressListItem.add(addressListItemData);
            }
        }
    }

    private void queryLoginStatus() {
        //获取是否是首次首次运行(安装了以后没有登录过)，是不是登录，
        // 如果不是首次运行，也没有登录，那就是登录了已经注销登录了
        isFirstRun = user.getBoolean("is_first_run", true);
        isLogined = user.getBoolean("is_logined", false);
    }

//    把mContext改成了public static 因此就不需要此方法了
//    public static Context getmContext() {
//        return mContext;
//    }

    public static Handler getHandler() {
        return mHandler;
    }

    /**
     * @return 获取主线程的id，用于比较当前线程和主线程是不是相等，如果不相等则表示当前线程是子线程
     */
    public static int getMainThreadId() {
        return mainThreadId;
    }

    public static boolean isIsLogined() {
        return isLogined;
    }

    public static boolean isIsFirstRun() {
        return isFirstRun;
    }

    /**
     * @param key 要查询配置的条目
     * @return 返回查询的结果
     */
    public static String getConfig(String key) {
        return user.getString(key, "");
    }

    /**
     * @param key   要存储到配置文件中的项目
     * @param value 要存储到配置文件中的值
     * @return 返回是否存储成功的结果，如果不需要结果，可以改用apply()代替commit(),前者是异步，不占用主线程资源
     */
    public static boolean setConfigString(String key, String value) {
        return user.edit().putString(key, value).commit();
    }

    /**
     * @param key   要存储到配置文件中的项目
     * @param value 要存储到配置文件中的值
     * @return 返回是否存储成功的结果，如果不需要结果，可以改用apply()代替commit(),前者是异步，不占用主线程资源
     */
    public static boolean setConfigBoolean(String key, boolean value) {
        return user.edit().putBoolean(key, value).commit();
    }
}
