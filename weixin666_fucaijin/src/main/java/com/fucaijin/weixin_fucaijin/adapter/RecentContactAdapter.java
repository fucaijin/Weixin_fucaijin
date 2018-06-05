package com.fucaijin.weixin_fucaijin.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.fucaijin.weixin_fucaijin.R;
import com.fucaijin.weixin_fucaijin.data.MessageListItem;

import java.util.List;

import static com.fucaijin.weixin_fucaijin.fragment.HomeWechatFragment.PESONAL_CHAT_TYPE;
import static com.fucaijin.weixin_fucaijin.global.WeixinApplication.mContext;

/**
 * Created by fucaijin on 2018/5/14.
 */
public class RecentContactAdapter extends BaseAdapter {
    List list;
    public RecentContactAdapter(List list) {
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
//        TODO ListView复用优化未完成
        View inflate = View.inflate(mContext, R.layout.home_fragment_wechat_recent_contact_list_item, null);
        ImageView headSculpture = inflate.findViewById(R.id.home_wechat_fragment_list_iv_item_head_sculpture);
        TextView unreadMessage = inflate.findViewById(R.id.home_wechat_fragment_list_tv_item_unread_message);
        TextView nickName = inflate.findViewById(R.id.home_wechat_fragment_list_tv_item_nick_name);
        TextView recentMessage = inflate.findViewById(R.id.home_wechat_fragment_list_tv_item_recent_message);
        TextView time = inflate.findViewById(R.id.home_wechat_fragment_list_tv_item_last_time);
        ImageView groupChatMute = inflate.findViewById(R.id.home_wechat_fragment_list_item_iv_group_mute);

        MessageListItem item = (MessageListItem) list.get(i);
        headSculpture.setImageResource(item.headSculpture);
        nickName.setText(item.nickName);
        recentMessage.setText(item.lastMessage);
        time.setText(item.time);

        if(item.messageType == PESONAL_CHAT_TYPE){
            unreadMessage.setText("" + (35 - i));
            unreadMessage.setVisibility(View.VISIBLE);
            groupChatMute.setVisibility(View.INVISIBLE);
        }else {
            unreadMessage.setVisibility(View.INVISIBLE);
        }

//        TODO 未完成
        if(i == 5 || i == 9 || i ==21){
        }

        return inflate;
    }


}
