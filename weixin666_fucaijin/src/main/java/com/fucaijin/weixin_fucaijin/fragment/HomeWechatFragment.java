package com.fucaijin.weixin_fucaijin.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.fucaijin.weixin_fucaijin.R;
import com.fucaijin.weixin_fucaijin.activity.ChatActivity;
import com.fucaijin.weixin_fucaijin.adapter.RecentContactAdapter;
import com.fucaijin.weixin_fucaijin.data.MessageListItem;

import java.util.ArrayList;

import static com.fucaijin.weixin_fucaijin.global.WeixinApplication.lastMessageList;
import static com.fucaijin.weixin_fucaijin.global.WeixinApplication.lastMessageTimeList;
import static com.fucaijin.weixin_fucaijin.global.WeixinApplication.mAddressListItem;
import static com.fucaijin.weixin_fucaijin.global.WeixinApplication.mContext;

/**
 * Created by fucaijin on 2018/5/9.
 */

public class HomeWechatFragment extends Fragment implements AdapterView.OnItemClickListener {

    public static final int PERSONAL_CHAT_TYPE = 1;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return initUI(inflater,container);
    }

    private View initUI(LayoutInflater inflater, ViewGroup container) {
        View rootView = inflater.inflate(R.layout.home_fragment_wechat, container, false);
        ListView listView = rootView.findViewById(R.id.home_wechat_fragment_list_view);
        ArrayList list = new ArrayList();

        for (int i = 0;i < 35 ;i++){
            MessageListItem item = new MessageListItem();

            item.headSculpture = mAddressListItem.get(i).getHeadSculpture();
            item.nickName = mAddressListItem.get(i).getNickName();
            item.lastMessage = lastMessageList[i];
            item.messageType = PERSONAL_CHAT_TYPE;

//        暂时注销随机生成的模拟时间：生成随机要减去/退回的毫秒数，并生成时间，然后格式化成字符串
//            long randomBackTime = (long) (Math.random() * 24 * 60 * 60 * 1000);
//            Date date = new Date(System.currentTimeMillis() - randomBackTime);
//            SimpleDateFormat format = new SimpleDateFormat("HH:mm");

//            使用自定义的时间
            item.time = lastMessageTimeList[i];

            list.add(item);
        }

        listView.setAdapter(new RecentContactAdapter(list));
        listView.setOnItemClickListener(this);
        return rootView;
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//        将当前条目的昵称传递到要ChatActivity
        TextView nickNameTv = view.findViewById(R.id.home_wechat_fragment_list_tv_item_nick_name);
        String nickName = nickNameTv.getText().toString().trim();
        Intent intent = new Intent(mContext, ChatActivity.class);
        intent.putExtra("nickName",nickName);
        startActivity(intent);
    }
}
