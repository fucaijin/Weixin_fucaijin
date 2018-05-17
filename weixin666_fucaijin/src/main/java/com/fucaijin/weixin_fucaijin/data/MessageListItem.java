package com.fucaijin.weixin_fucaijin.data;

/**
 * Created by fucaijin on 2018/5/15.
 */

public class MessageListItem {
    public int messageType;//判断是哪种类型，是聊天还是系统的，是群聊还是什么
    public int headSculpture;
    public String nickName;
    public String lastMessage;
    public String time;//时间
    public int Mute;//如果是群聊，要判断是否设置了消息静音
    public boolean isStick;//是否置顶

}
