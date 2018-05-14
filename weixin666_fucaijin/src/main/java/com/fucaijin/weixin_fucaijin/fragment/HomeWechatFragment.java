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

import java.util.ArrayList;

/**
 * Created by fucaijin on 2018/5/9.
 */

public class HomeWechatFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return initUI(inflater,container);
    }

    private View initUI(LayoutInflater inflater, ViewGroup container) {
        View rootView = inflater.inflate(R.layout.home_fragment_wechat, container, false);
        ListView listView = rootView.findViewById(R.id.home_wechat_fragment_list_view);
        ArrayList list = new ArrayList();
        for (int i = 0;i < 50 ;i++){
            list.add("这是第 " + i + "个条目");
        }
        listView.setAdapter(new RecentContactAdapter(list));
        return rootView;
    }


}
