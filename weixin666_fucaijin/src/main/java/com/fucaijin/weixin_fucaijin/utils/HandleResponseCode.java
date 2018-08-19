package com.fucaijin.weixin_fucaijin.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * 用于处理错误码的类
 */

public class HandleResponseCode {
    public static void onHandle(Context mContext, int statusCode) {
        String str = "";
        switch (statusCode) {
            case 0:
                str = "成功";
                break;
            case 871101:
                str = "请求参数不合法";
                break;
            case 871102:
                str = "请求失败，请检查网络";
                break;
            case 871103:
                str = "服务器内部错误";
                break;
            case 871104:
                str = "服务器内部错误";
                break;
            case 871105:
                str = "请求的用户信息不存在";
                break;
            case 871201:
                str = "响应超时";
                break;
            case 871300:
                str = "api调用发起者尚未登录";
                break;
            case 871301:
                str = "api调用传入的参数不合法";
                break;
            case 871302:
                str = "发送消息的消息体过大，整个消息体大小不能超过4k";
                break;
            case 871303:
                str = "用户名不合法";
                break;
            case 871304:
                str = "密码不合法";
                break;
            case 871305:
                str = "名称不合法（包括nickname groupname notename）";
                break;
            case 871306:
                str = "其他输入不合法";
                break;
            case 871307:
                str = "添加或移除群成员时，传入的成员列表中有用户不存在";
                break;
            case 871308:
                str = "SDK尚未初始化";
                break;
            case 871309:
                str = "消息中包含的文件不存在";
                break;
            case 871310:
                str = "网络连接已断开，请检查网络";
                break;
            case 871311:
                str = "用户未设定头像，下载头像失败";
                break;
            case 871312:
                str = "创建ImageContent失败";
                break;
            case 871313:
                str = "消息解析出错，协议版本号不匹配";
                break;
            case 871314:
                str = "消息解析出错，缺少关键参数";
                break;
            case 871315:
                str = "消息解析出错";
                break;
            case 871317:
                str = "操作目标用户不能是自己";
                break;
            case 871318:
                str = "不合法的消息体，出现这个问题可能是由于上层没有参照集成文档进行混淆配置导致的，关于jmessage的混淆配置见集成指南";
                break;
            case 871319:
                str = "创建转发消息失败，具体原因见logcat打印";
                break;
            case 871320:
                str = "将消息标记为已读时出现问题，可能这条消息已经是已读状态，或者这条消息本身不是接受类型的消息";
                break;
            case 871321:
                str = "获取未回执详情失败，只有消息的发送者可以查询消息的未回执详情";
                break;
            case 871322:
                str = "获取未回执详情失败，这条消息尚未成功发送，只有成功发送的消息可以查询未回执详情";
                break;
            case 871323:
                str = "请求的聊天室信息未找到，该聊天室不存在";
                break;
            case 871324:
                str = "发送消息时消息体类型不合法，注意eventNotification和prompt类型的消息体不能发送";
                break;
            case 871402:
                str = "文件上传失败";
                break;
            case 871403:
                str = "文件上传失败";
                break;
            case 871404:
                str = "文件下载失败";
                break;
            case 871501:
                str = "appkey与包名不匹配或者token无效";
                break;
            case 871502:
                str = "appKey无效。请检查 AndroidManifest.xml 里的 appKey 配置，它必须是从 JPush 控制台创建应用得到的。";
                break;
            case 871503:
                str = "appKey与平台不匹配。有可能在 JPush 控制台上，未配置此 appKey 支持 Android 平台。";
                break;
            case 871504:
                str = "Push 注册未完成，请稍后重试。如果持续出现这个问题，可能你的 JPush 配置不正确。";
                break;
            case 871505:
                str = "Push 注册失败,对应包名在控制台上不存在。";
                break;
            case 871506:
                str = "Push 注册失败，设备IMEI不合法";
                break;

        }
        Toast.makeText(mContext, str, Toast.LENGTH_SHORT).show();
    }
}
