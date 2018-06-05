package com.fucaijin.weixin_fucaijin.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.fucaijin.weixin_fucaijin.R;
import com.fucaijin.weixin_fucaijin.adapter.RecentContactAdapter;
import com.fucaijin.weixin_fucaijin.data.MessageListItem;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import static com.fucaijin.weixin_fucaijin.global.WeixinApplication.mAddressListItem;

/**
 * Created by fucaijin on 2018/5/9.
 */

public class HomeWechatFragment extends Fragment {

    public static final int PESONAL_CHAT_TYPE = 1;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return initUI(inflater,container);
    }

    private View initUI(LayoutInflater inflater, ViewGroup container) {
        View rootView = inflater.inflate(R.layout.home_fragment_wechat, container, false);
        ListView listView = rootView.findViewById(R.id.home_wechat_fragment_list_view);
        ArrayList list = new ArrayList();


//        int[] headSculptureList = {R.drawable.head_sculpture_1,
//                R.drawable.head_sculpture_2,
//                R.drawable.head_sculpture_3,
//                R.drawable.head_sculpture_4,
//                R.drawable.head_sculpture_5,
//                R.drawable.head_sculpture_6,
//                R.drawable.head_sculpture_7,
//                R.drawable.head_sculpture_8,
//                R.drawable.head_sculpture_9,
//                R.drawable.head_sculpture_10,
//                R.drawable.head_sculpture_11,
//                R.drawable.head_sculpture_12,
//                R.drawable.head_sculpture_13,
//                R.drawable.head_sculpture_14,
//                R.drawable.head_sculpture_15,
//                R.drawable.head_sculpture_16,
//                R.drawable.head_sculpture_17,
//                R.drawable.head_sculpture_18,
//                R.drawable.head_sculpture_19,
//                R.drawable.head_sculpture_20,
//                R.drawable.head_sculpture_21,
//                R.drawable.head_sculpture_22,
//                R.drawable.head_sculpture_23,
//                R.drawable.head_sculpture_24,
//                R.drawable.head_sculpture_25,
//                R.drawable.head_sculpture_26,
//                R.drawable.head_sculpture_27,
//                R.drawable.head_sculpture_28,
//                R.drawable.head_sculpture_29,
//                R.drawable.head_sculpture_30,
//                R.drawable.head_sculpture_31,
//                R.drawable.head_sculpture_32,
//                R.drawable.head_sculpture_33,
//                R.drawable.head_sculpture_34,
//                R.drawable.head_sculpture_35,
//        };
//
//        String[] nickNameList = {"HelloKitty", "阿拉蕾", "白娘子", "白头发少年", "白头发鸣人",
//                "白雪公主", "超级玛红", "超级玛绿", "大白鹅", "丁丁",
//                "猥琐海盗", "黑猫警长", "葫芦娃", "加肥猫", "胖小弟",
//                "不平易近人的警察", "啃德鸡老爷爷", "我是小新", "灌篮小胖子", "雷神",
//                "大龙猫", "路灰", "满头绿", "卖兜", "超级无敌美少女",
//                "真的鸣人", "派派派派派大星", "瞧吧", "圣诞老爷爷", "不认识这是谁",
//                "无脸人", "绿了全身的猪", "小熊维尼", "可爱的小樱桃", "猥琐小胖子", };


        for (int i = 0;i < 35 ;i++){
            MessageListItem item = new MessageListItem();

            item.headSculpture = mAddressListItem.get(i).getHeadSculpture();
            item.nickName = mAddressListItem.get(i).getNickName();
            item.lastMessage = mAddressListItem.get(i).getNickName() + "给你发来了问候，祝你身体健康工作顺利，妹子多多，帅锅多多";
            item.messageType = PESONAL_CHAT_TYPE;

//        生成随机要减去/退回的毫秒数，并生成时间，然后格式化成字符串
            long randombackTime = (long) (Math.random() * 24 * 60 * 60 * 1000);
            Date date = new Date(System.currentTimeMillis() - randombackTime);
            SimpleDateFormat format = new SimpleDateFormat("HH:mm");
            item.time = format.format(date);

            list.add(item);
        }

        listView.setAdapter(new RecentContactAdapter(list));
        return rootView;
    }

}
