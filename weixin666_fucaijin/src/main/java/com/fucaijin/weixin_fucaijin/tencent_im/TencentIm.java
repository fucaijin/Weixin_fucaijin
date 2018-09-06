package com.fucaijin.weixin_fucaijin.tencent_im;

import android.util.Log;

import com.fucaijin.weixin_fucaijin.global.WeixinApplication;
import com.tencent.imsdk.TIMCallBack;
import com.tencent.imsdk.TIMConnListener;
import com.tencent.imsdk.TIMConversation;
import com.tencent.imsdk.TIMFriendshipSettings;
import com.tencent.imsdk.TIMGroupEventListener;
import com.tencent.imsdk.TIMGroupMemberInfo;
import com.tencent.imsdk.TIMGroupSettings;
import com.tencent.imsdk.TIMGroupTipsElem;
import com.tencent.imsdk.TIMLogLevel;
import com.tencent.imsdk.TIMManager;
import com.tencent.imsdk.TIMMessage;
import com.tencent.imsdk.TIMMessageListener;
import com.tencent.imsdk.TIMRefreshListener;
import com.tencent.imsdk.TIMSNSChangeInfo;
import com.tencent.imsdk.TIMSdkConfig;
import com.tencent.imsdk.TIMUserConfig;
import com.tencent.imsdk.TIMUserProfile;
import com.tencent.imsdk.TIMUserStatusListener;
import com.tencent.imsdk.ext.group.TIMGroupAssistantListener;
import com.tencent.imsdk.ext.group.TIMGroupCacheInfo;
import com.tencent.imsdk.ext.group.TIMUserConfigGroupExt;
import com.tencent.imsdk.ext.message.TIMMessageLocator;
import com.tencent.imsdk.ext.message.TIMMessageRevokedListener;
import com.tencent.imsdk.ext.message.TIMUserConfigMsgExt;
import com.tencent.imsdk.ext.sns.TIMFriendGroup;
import com.tencent.imsdk.ext.sns.TIMFriendshipProxyListener;
import com.tencent.imsdk.ext.sns.TIMUserConfigSnsExt;

import java.util.List;

public class TencentIm {

    /**
     * 初始化腾讯imSDK
     */
    public static void init() {
        //初始化 SDK 基本配置
        TIMSdkConfig config = new TIMSdkConfig(WeixinApplication.TENCENT_IM_SDK_APP_ID)
                .enableCrashReport(false)//是否要Crash上报到腾讯云平台
                .enableLogPrint(true)//是否要输出腾讯im的Log
                .setLogLevel(TIMLogLevel.DEBUG);//输出设置log等级
//        .setLogPath(Environment.getExternalStorageDirectory().getPath() + "/justfortest/")  //可以设置输出log的路径，ImSDK 默认日志存储路径为：SD 卡下，/tencent/imsdklogs/(your app package name)/

        //初始化 SDK
        TIMManager.getInstance().init(WeixinApplication.mContext, config);
    }

    public static void userConfig() {
        //基本用户配置
        TIMUserConfig userConfig = new TIMUserConfig()
                //设置群组资料拉取字段
                .setGroupSettings(new TIMGroupSettings())
                //设置资料关系链拉取字段
                .setFriendshipSettings(new TIMFriendshipSettings() )
                //设置用户状态变更事件监听器，用户状态变更的时候，SDK 会有相应的通知
                .setUserStatusListener(new TIMUserStatusListener() {
                    String tag = "TencentImUserStatusListener";
                    @Override
                    public void onForceOffline() {
                        //被其他终端踢下线
                        Log.i(tag, "onForceOffline");
                    }

                    @Override
                    public void onUserSigExpired() {
                        //用户签名过期了，需要刷新 userSig 重新登录 SDK
                        Log.i(tag, "onUserSigExpired");
                    }
                })
                //设置连接状态事件监听器，仅指明 SDK 是否与 IM 云 Server 连接状态
                .setConnectionListener(new TIMConnListener() {
                    String tag = "TencentImConnectionListener";
                    @Override
                    public void onConnected() {
                        Log.i(tag, "onConnected");
                    }

                    @Override
                    public void onDisconnected(int code, String desc) {
                        Log.i(tag, "onDisconnected");
                    }

                    @Override
                    public void onWifiNeedAuth(String name) {
                        Log.i(tag, "onWifiNeedAuth");
                    }
                })
                //设置群组事件监听器
                .setGroupEventListener(new TIMGroupEventListener() {
                    @Override
                    public void onGroupTipsEvent(TIMGroupTipsElem elem) {
                        String tag = "TencentImGroupTipsEvent";
                        Log.i(tag, "onGroupTipsEvent, type: " + elem.getTipsType());
                    }
                })
                //设置会话刷新监听器
                .setRefreshListener(new TIMRefreshListener() {
                    String tag = "TencentImRefreshListener";
                    @Override
                    public void onRefresh() {
                        Log.i(tag, "onRefresh");
                    }

                    @Override
                    public void onRefreshConversation(List<TIMConversation> conversations) {
                        Log.i(tag, "onRefreshConversation, conversation size: " + conversations.size());
                    }
                });

//        消息扩展用户配置
        userConfig = new TIMUserConfigMsgExt(userConfig)
                //禁用消息存储，true - 开启， false - 不开启。默认情况 ImSDK 会进行消息的存储，如无需存储，可选择通过 TIMUserConfigMsgExt 关闭存储来提升处理性能。
                .enableStorage(false)
                //开启消息已读回执
                .enableReadReceipt(true)
                //                        设置消息撤回通知监听器
                .setMessageRevokedListener(new TIMMessageRevokedListener() {
                    @Override
                    public void onMessageRevoked(TIMMessageLocator timMessageLocator) {
                    }
                });

//        资料关系链扩展用户配置
        userConfig = new TIMUserConfigSnsExt(userConfig)
                //开启资料关系链本地存储
                .enableFriendshipStorage(true)
                //设置关系链变更事件监听器
                .setFriendshipProxyListener(new TIMFriendshipProxyListener() {
                    String tag = "TencentImFriendshipProxyListener";

                    @Override
                    public void OnAddFriends(List<TIMUserProfile> users) {
                        Log.i(tag, "OnAddFriends");
                    }

                    @Override
                    public void OnDelFriends(List<String> identifiers) {
                        Log.i(tag, "OnDelFriends");
                    }

                    @Override
                    public void OnFriendProfileUpdate(List<TIMUserProfile> profiles) {
                        Log.i(tag, "OnFriendProfileUpdate");
                    }

                    @Override
                    public void OnAddFriendReqs(List<TIMSNSChangeInfo> reqs) {
                        Log.i(tag, "OnAddFriendReqs");
                    }

                    public void OnAddFriendGroups(List<TIMFriendGroup> friendgroups) {
                        Log.i(tag, "OnAddFriendGroups");
                    }

                    public void OnDelFriendGroups(List<String> names) {
                        Log.i(tag, "OnDelFriendGroups");
                    }

                    public void OnFriendGroupUpdate(List<TIMFriendGroup> friendgroups) {
                        Log.i(tag, "OnFriendGroupUpdate");
                    }
                });

//        群组管理扩展用户配置
        userConfig = new TIMUserConfigGroupExt(userConfig)
                //开启群组资料本地存储
                .enableGroupStorage(true)
                //设置群组资料变更事件监听器
                .setGroupAssistantListener(new TIMGroupAssistantListener() {
                    String tag = "TencentImGroupAssistantListener";
                    @Override
                    public void onMemberJoin(String groupId, List<TIMGroupMemberInfo> memberInfos) {
                        Log.i(tag, "onMemberJoin");
                    }

                    @Override
                    public void onMemberQuit(String groupId, List<String> members) {
                        Log.i(tag, "onMemberQuit");
                    }

                    @Override
                    public void onMemberUpdate(String groupId, List<TIMGroupMemberInfo> memberInfos) {
                        Log.i(tag, "onMemberUpdate");
                    }

                    @Override
                    public void onGroupAdd(TIMGroupCacheInfo groupCacheInfo) {
                        Log.i(tag, "onGroupAdd");
                    }

                    @Override
                    public void onGroupDelete(String groupId) {
                        Log.i(tag, "onGroupDelete");
                    }

                    @Override
                    public void onGroupUpdate(TIMGroupCacheInfo groupCacheInfo) {
                        Log.i(tag, "onGroupUpdate");
                    }
                });

//        将用户配置与通讯管理器进行绑定
        TIMManager.getInstance().setUserConfig(userConfig);

        //设置消息监听器，收到新消息时，通过此监听器回调
        TIMManager.getInstance().addMessageListener(new TIMMessageListener() {//消息监听器
            @Override
            public boolean onNewMessages(List<TIMMessage> msgs) {//收到新消息
                //消息的内容解析请参考消息收发文档中的消息解析说明
                return true; //返回true将终止回调链，不再调用下一个新消息监听器
            }
        });
    }

    /**
     * 登录腾讯im的方法
     * @param phone 手机号，实际上就是用户名
     * @param userSig 服务器计算产生的userSig
     */
    public static void login(String phone, String userSig){
        // identifier为用户名，userSig 为用户登录凭证
        TIMManager.getInstance().login(phone, userSig, new TIMCallBack() {
            String tag = "TIMCallBack";
            @Override
            public void onError(int code, String desc) {
                //错误码 code 和错误描述 desc，可用于定位请求失败原因
                //错误码 code 列表请参见错误码表
                Log.d(tag, "TencentIm failed. code: " + code + " errmsg: " + desc);
            }

            @Override
            public void onSuccess() {
                Log.d(tag, "TencentIm succ");
            }
        });
    }

    /**
     * 退出登录方法
     */
    public static void logout(){
        //登出
        TIMManager.getInstance().logout(new TIMCallBack() {
            String tag = "TIMCallBack";
            @Override
            public void onError(int code, String desc) {

                //错误码 code 和错误描述 desc，可用于定位请求失败原因
                //错误码 code 列表请参见错误码表
                Log.d(tag, "logout failed. code: " + code + " errmsg: " + desc);
            }

            @Override
            public void onSuccess() {
                //登出成功
            }
        });
    }

    public static void sendMessage(String msg, String id){

    }
}
